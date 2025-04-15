from fastapi import APIRouter, Depends, HTTPException, status
from typing import List
from app.models.models import Speciality, Role
from app.routers.auth import get_current_user
from app.models.models import User

router = APIRouter()

async def get_current_admin(current_user: User = Depends(get_current_user)):
    if current_user.role != Role.ADMIN:
        raise HTTPException(
            status_code=status.HTTP_403_FORBIDDEN,
            detail="The user doesn't have enough privileges"
        )
    return current_user

@router.get("/")
async def get_default_message(current_user: User = Depends(get_current_admin)):
    return "Hello, World"

@router.get("/specialities", response_model=List[str])
async def get_specialities(current_user: User = Depends(get_current_admin)):
    return [speciality.value for speciality in Speciality]

@router.get("/server/status")
async def get_server_status(current_user: User = Depends(get_current_admin)):
    return "Server is running" 