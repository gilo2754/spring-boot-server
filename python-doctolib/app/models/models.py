from sqlalchemy import Column, Integer, String, DateTime, ForeignKey, Enum, Boolean
from sqlalchemy.orm import relationship
from datetime import datetime
import enum
from .base import Base

class Role(str, enum.Enum):
    ADMIN = "ADMIN"
    DOCTOR = "DOCTOR"
    PATIENT = "PATIENT"

class Speciality(str, enum.Enum):
    CARDIOLOGY = "CARDIOLOGY"
    DERMATOLOGY = "DERMATOLOGY"
    PEDIATRICS = "PEDIATRICS"
    NEUROLOGY = "NEUROLOGY"
    OPHTHALMOLOGY = "OPHTHALMOLOGY"
    # Añadir más especialidades según sea necesario

class User(Base):
    __tablename__ = "users"

    id = Column(Integer, primary_key=True, index=True)
    username = Column(String, unique=True, index=True)
    email = Column(String, unique=True, index=True)
    hashed_password = Column(String)
    first_name = Column(String)
    last_name = Column(String)
    phone_number = Column(String)
    date_of_birth = Column(DateTime)
    social_number = Column(String, unique=True)
    role = Column(Enum(Role))
    is_active = Column(Boolean, default=True)
    created_at = Column(DateTime, default=datetime.utcnow)
    updated_at = Column(DateTime, default=datetime.utcnow, onupdate=datetime.utcnow)

    # Relaciones
    address = relationship("Address", back_populates="user", uselist=False)
    appointments_as_doctor = relationship("Appointment", back_populates="doctor", foreign_keys="Appointment.doctor_id")
    appointments_as_patient = relationship("Appointment", back_populates="patient", foreign_keys="Appointment.patient_id")

class Address(Base):
    __tablename__ = "addresses"

    id = Column(Integer, primary_key=True, index=True)
    user_id = Column(Integer, ForeignKey("users.id"))
    street = Column(String)
    neighborhood = Column(String)
    city = Column(String)
    department = Column(String)
    postal_code = Column(String)
    additional_info = Column(String)

    user = relationship("User", back_populates="address")

class Clinic(Base):
    __tablename__ = "clinics"

    id = Column(Integer, primary_key=True, index=True)
    clinic_name = Column(String)
    speciality = Column(Enum(Speciality))
    address = Column(String)
    phone_number = Column(String)
    email = Column(String)
    created_at = Column(DateTime, default=datetime.utcnow)
    updated_at = Column(DateTime, default=datetime.utcnow, onupdate=datetime.utcnow)

    appointments = relationship("Appointment", back_populates="clinic")

class Appointment(Base):
    __tablename__ = "appointments"

    id = Column(Integer, primary_key=True, index=True)
    clinic_id = Column(Integer, ForeignKey("clinics.id"))
    doctor_id = Column(Integer, ForeignKey("users.id"))
    patient_id = Column(Integer, ForeignKey("users.id"))
    appointment_date = Column(DateTime)
    status = Column(String)  # SCHEDULED, COMPLETED, CANCELLED
    created_at = Column(DateTime, default=datetime.utcnow)
    updated_at = Column(DateTime, default=datetime.utcnow, onupdate=datetime.utcnow)

    clinic = relationship("Clinic", back_populates="appointments")
    doctor = relationship("User", back_populates="appointments_as_doctor", foreign_keys=[doctor_id])
    patient = relationship("User", back_populates="appointments_as_patient", foreign_keys=[patient_id]) 