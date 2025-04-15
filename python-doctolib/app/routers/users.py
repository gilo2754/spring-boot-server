from fastapi import APIRouter, Depends, HTTPException, status
from sqlalchemy.orm import Session
from typing import List, Optional
from app.models.base import get_db
from app.models.models import User, Role
from app.models.schemas import UserCreate, User as UserSchema
from app.routers.auth import get_password_hash, get_current_user

router = APIRouter()

@router.post("/person/add", response_model=UserSchema, status_code=status.HTTP_201_CREATED)
async def create_user(user: UserCreate, db: Session = Depends(get_db)):
    # Verificar si el username ya existe
    db_user = db.query(User).filter(User.username == user.username).first()
    if db_user:
        raise HTTPException(
            status_code=status.HTTP_400_BAD_REQUEST,
            detail="Username already registered"
        )
    
    # Verificar si el email ya existe
    db_user = db.query(User).filter(User.email == user.email).first()
    if db_user:
        raise HTTPException(
            status_code=status.HTTP_400_BAD_REQUEST,
            detail="Email already registered"
        )
    
    # Crear nuevo usuario
    hashed_password = get_password_hash(user.password)
    db_user = User(
        username=user.username,
        email=user.email,
        hashed_password=hashed_password,
        first_name=user.first_name,
        last_name=user.last_name,
        phone_number=user.phone_number,
        date_of_birth=user.date_of_birth,
        social_number=user.social_number,
        role=user.role
    )
    
    db.add(db_user)
    db.commit()
    db.refresh(db_user)
    return db_user

@router.get("/people", response_model=List[UserSchema])
async def get_people(
    role: Optional[Role] = None,
    db: Session = Depends(get_db),
    current_user: User = Depends(get_current_user)
):
    if role:
        users = db.query(User).filter(User.role == role).all()
    else:
        users = db.query(User).all()
    return users

@router.get("/user-info", response_model=UserSchema)
async def get_user_info(
    current_user: User = Depends(get_current_user)
):
    return current_user

@router.get("/clinic/{clinic_id}/doctors", response_model=List[UserSchema])
async def get_doctors_by_clinic(
    clinic_id: int,
    db: Session = Depends(get_db),
    current_user: User = Depends(get_current_user)
):
    # Aquí podrías implementar la lógica para obtener los doctores de una clínica específica
    # Por ahora, simplemente devolvemos todos los doctores
    doctors = db.query(User).filter(User.role == Role.DOCTOR).all()
    return doctors 