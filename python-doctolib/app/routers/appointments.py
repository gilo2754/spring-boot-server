from fastapi import APIRouter, Depends, HTTPException, status
from sqlalchemy.orm import Session
from typing import List
from datetime import datetime
from app.models.base import get_db
from app.models.models import Appointment, User, Clinic
from app.models.schemas import AppointmentCreate, Appointment as AppointmentSchema
from app.routers.auth import get_current_user
from fastapi_mail import FastMail, MessageSchema, ConnectionConfig
from app.core.config import settings

router = APIRouter()

# Configuración de correo electrónico
conf = ConnectionConfig(
    MAIL_USERNAME=settings.SMTP_USERNAME,
    MAIL_PASSWORD=settings.SMTP_PASSWORD,
    MAIL_FROM=settings.SMTP_USERNAME,
    MAIL_PORT=settings.SMTP_PORT,
    MAIL_SERVER=settings.SMTP_SERVER,
    MAIL_STARTTLS=True,
    MAIL_SSL_TLS=False,
    USE_CREDENTIALS=True
)

@router.post("/appointment/create", response_model=AppointmentSchema)
async def create_appointment(
    appointment: AppointmentCreate,
    db: Session = Depends(get_db),
    current_user: User = Depends(get_current_user)
):
    # Verificar que la clínica existe
    clinic = db.query(Clinic).filter(Clinic.id == appointment.clinic_id).first()
    if not clinic:
        raise HTTPException(
            status_code=status.HTTP_404_NOT_FOUND,
            detail="Clinic not found"
        )
    
    # Verificar que el doctor existe
    doctor = db.query(User).filter(User.id == appointment.doctor_id).first()
    if not doctor:
        raise HTTPException(
            status_code=status.HTTP_404_NOT_FOUND,
            detail="Doctor not found"
        )
    
    # Verificar que el paciente existe
    patient = db.query(User).filter(User.id == appointment.patient_id).first()
    if not patient:
        raise HTTPException(
            status_code=status.HTTP_404_NOT_FOUND,
            detail="Patient not found"
        )
    
    # Crear la cita
    db_appointment = Appointment(**appointment.dict())
    db.add(db_appointment)
    db.commit()
    db.refresh(db_appointment)
    
    # Enviar correo de confirmación
    try:
        message = MessageSchema(
            subject="Nueva cita médica creada",
            recipients=[patient.email],
            body=f"""
            Hola {patient.first_name},
            
            Tu cita ha sido programada para el {appointment.appointment_date} con el Dr. {doctor.first_name} {doctor.last_name}
            en la clínica {clinic.clinic_name}.
            
            Saludos,
            El equipo de Doctolib
            """
        )
        
        fm = FastMail(conf)
        await fm.send_message(message)
    except Exception as e:
        # Log the error but don't fail the appointment creation
        print(f"Error sending email: {e}")
    
    return db_appointment

@router.get("/appointment", response_model=List[AppointmentSchema])
async def get_my_appointments(
    db: Session = Depends(get_db),
    current_user: User = Depends(get_current_user)
):
    appointments = db.query(Appointment).filter(
        (Appointment.doctor_id == current_user.id) |
        (Appointment.patient_id == current_user.id)
    ).all()
    return appointments

@router.get("/appointment/{id}", response_model=AppointmentSchema)
async def get_appointment(
    id: int,
    db: Session = Depends(get_db),
    current_user: User = Depends(get_current_user)
):
    appointment = db.query(Appointment).filter(Appointment.id == id).first()
    if not appointment:
        raise HTTPException(
            status_code=status.HTTP_404_NOT_FOUND,
            detail="Appointment not found"
        )
    return appointment

@router.put("/appointment/update", response_model=AppointmentSchema)
async def update_appointment(
    appointment: AppointmentCreate,
    db: Session = Depends(get_db),
    current_user: User = Depends(get_current_user)
):
    db_appointment = db.query(Appointment).filter(Appointment.id == appointment.id).first()
    if not db_appointment:
        raise HTTPException(
            status_code=status.HTTP_404_NOT_FOUND,
            detail="Appointment not found"
        )
    
    for key, value in appointment.dict().items():
        setattr(db_appointment, key, value)
    
    db.commit()
    db.refresh(db_appointment)
    return db_appointment

@router.post("/appointment/reserve")
async def reserve_appointment(
    appointment_id: int,
    patient_id: int,
    db: Session = Depends(get_db),
    current_user: User = Depends(get_current_user)
):
    appointment = db.query(Appointment).filter(Appointment.id == appointment_id).first()
    if not appointment:
        raise HTTPException(
            status_code=status.HTTP_404_NOT_FOUND,
            detail="Appointment not found"
        )
    
    if appointment.patient_id is not None:
        raise HTTPException(
            status_code=status.HTTP_400_BAD_REQUEST,
            detail="Appointment is already reserved"
        )
    
    patient = db.query(User).filter(User.id == patient_id).first()
    if not patient:
        raise HTTPException(
            status_code=status.HTTP_404_NOT_FOUND,
            detail="Patient not found"
        )
    
    appointment.patient_id = patient_id
    db.commit()
    
    return {"message": "Appointment reserved successfully"} 