from fastapi import APIRouter, Depends, HTTPException, status
from sqlalchemy.orm import Session
from typing import List, Optional
from app.models.base import get_db
from app.models.models import Clinic, Speciality
from app.models.schemas import User, ClinicCreate, Clinic as ClinicSchema
from app.routers.auth import get_current_user

router = APIRouter()

@router.post("/clinic/add", response_model=ClinicSchema, status_code=status.HTTP_201_CREATED)
async def create_clinic(
    clinic: ClinicCreate,
    db: Session = Depends(get_db),
    current_user: User = Depends(get_current_user)
):
    db_clinic = Clinic(**clinic.dict())
    db.add(db_clinic)
    db.commit()
    db.refresh(db_clinic)
    return db_clinic

@router.get("/clinic", response_model=List[ClinicSchema])
async def get_clinics(
    speciality: Optional[Speciality] = None,
    db: Session = Depends(get_db),
    current_user: User = Depends(get_current_user)
):
    if speciality:
        clinics = db.query(Clinic).filter(Clinic.speciality == speciality).all()
        if not clinics:
            raise HTTPException(
                status_code=status.HTTP_404_NOT_FOUND,
                detail=f"No clinics found for speciality: {speciality}"
            )
    else:
        clinics = db.query(Clinic).all()
    return clinics

@router.get("/clinic/{id}", response_model=ClinicSchema)
async def get_clinic(
    id: int,
    db: Session = Depends(get_db),
    current_user: User = Depends(get_current_user)
):
    clinic = db.query(Clinic).filter(Clinic.id == id).first()
    if not clinic:
        raise HTTPException(
            status_code=status.HTTP_404_NOT_FOUND,
            detail="Clinic not found"
        )
    return clinic

@router.put("/clinic/{clinic_id}", response_model=ClinicSchema)
async def update_clinic(
    clinic_id: int,
    clinic: ClinicCreate,
    db: Session = Depends(get_db),
    current_user: User = Depends(get_current_user)
):
    db_clinic = db.query(Clinic).filter(Clinic.id == clinic_id).first()
    if not db_clinic:
        raise HTTPException(
            status_code=status.HTTP_404_NOT_FOUND,
            detail="Clinic not found"
        )
    
    for key, value in clinic.dict().items():
        setattr(db_clinic, key, value)
    
    db.commit()
    db.refresh(db_clinic)
    return db_clinic

@router.delete("/clinic/{id}", status_code=status.HTTP_204_NO_CONTENT)
async def delete_clinic(
    id: int,
    db: Session = Depends(get_db),
    current_user: User = Depends(get_current_user)
):
    clinic = db.query(Clinic).filter(Clinic.id == id).first()
    if not clinic:
        raise HTTPException(
            status_code=status.HTTP_404_NOT_FOUND,
            detail="Clinic not found"
        )
    
    try:
        db.delete(clinic)
        db.commit()
    except Exception as e:
        db.rollback()
        raise HTTPException(
            status_code=status.HTTP_400_BAD_REQUEST,
            detail="No se pueden borrar clinics si estas contienen appointment"
        ) 