from pydantic import BaseModel, EmailStr, constr, Field
from typing import Optional, List
from datetime import datetime
from .models import Role, Speciality
from enum import Enum

# User schemas
class AddressBase(BaseModel):
    street: str
    neighborhood: str
    city: str
    department: str
    postal_code: str
    additional_info: str

class AddressCreate(AddressBase):
    pass

class Address(AddressBase):
    id: int
    user_id: int

    class Config:
        from_attributes = True

class UserRole(str, Enum):
    ADMIN = "admin"
    DOCTOR = "doctor"
    PATIENT = "patient"

class UserBase(BaseModel):
    username: str
    email: EmailStr
    first_name: str
    last_name: str
    phone_number: str = Field(pattern=r'^\+?1?\d{9,15}$')
    date_of_birth: datetime
    social_number: str
    role: UserRole

class UserCreate(UserBase):
    password: str
    address: Optional[AddressCreate] = None

class User(UserBase):
    id: int
    is_active: bool
    created_at: datetime
    updated_at: datetime
    address: Optional[Address] = None

    class Config:
        from_attributes = True

# Clinic schemas
class ClinicBase(BaseModel):
    clinic_name: str
    speciality: Speciality
    address: str
    phone_number: str = Field(pattern=r'^\+?1?\d{9,15}$')
    email: EmailStr

class ClinicCreate(ClinicBase):
    pass

class Clinic(ClinicBase):
    id: int
    created_at: datetime
    updated_at: datetime

    class Config:
        from_attributes = True

# Appointment schemas
class AppointmentBase(BaseModel):
    clinic_id: int
    doctor_id: int
    patient_id: int
    appointment_date: datetime
    status: str

class AppointmentCreate(AppointmentBase):
    pass

class Appointment(AppointmentBase):
    id: int
    created_at: datetime
    updated_at: datetime
    clinic: Clinic
    doctor: User
    patient: User

    class Config:
        from_attributes = True

# Auth schemas
class Token(BaseModel):
    access_token: str
    token_type: str

class TokenData(BaseModel):
    username: Optional[str] = None
    role: Optional[Role] = None 