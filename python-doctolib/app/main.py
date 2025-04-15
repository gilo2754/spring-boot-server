from fastapi import FastAPI
from fastapi.middleware.cors import CORSMiddleware
from app.core.config import settings
from app.routers import auth, users, clinics, appointments, admin

app = FastAPI(
    title="Doctolib API",
    description="API para gestión de citas médicas",
    version="1.0.0"
)

# Configurar CORS
app.add_middleware(
    CORSMiddleware,
    allow_origins=["*"],
    allow_credentials=True,
    allow_methods=["*"],
    allow_headers=["*"],
)

# Incluir routers
app.include_router(auth.router, prefix="/api/v1", tags=["auth"])
app.include_router(users.router, prefix="/api/v1", tags=["users"])
app.include_router(clinics.router, prefix="/api/v1", tags=["clinics"])
app.include_router(appointments.router, prefix="/api/v1", tags=["appointments"])
app.include_router(admin.router, prefix="/admin/api/v1", tags=["admin"])

@app.get("/")
async def root():
    return {"message": "Bienvenido a la API de Doctolib"} 