# Doctolib API (Python Version)

Esta es una implementación en Python del sistema de gestión de citas médicas Doctolib, utilizando FastAPI como framework.

## Características

- Autenticación JWT
- Gestión de usuarios (pacientes, doctores, administradores)
- Gestión de clínicas
- Gestión de citas médicas
- Notificaciones por email
- API RESTful
- Documentación automática con Swagger UI

## Requisitos

- Python 3.8+
- pip
- SQLite (por defecto) o PostgreSQL
- Servidor SMTP para envío de emails
- Cuenta de Twilio para SMS (opcional)

## Instalación

1. Clonar el repositorio:
```bash
git clone <repository-url>
cd python-doctolib
```

2. Crear un entorno virtual:
```bash
python -m venv venv
source venv/bin/activate  # En Windows: venv\Scripts\activate
```

3. Instalar dependencias:
```bash
pip install -r requirements.txt
```

4. Crear archivo .env con las variables de entorno:
```env
DATABASE_URL=sqlite:///./doctolib.db
SECRET_KEY=your-secret-key-here
SMTP_SERVER=smtp.gmail.com
SMTP_PORT=587
SMTP_USERNAME=your-email@gmail.com
SMTP_PASSWORD=your-app-password
TWILIO_ACCOUNT_SID=your-twilio-sid
TWILIO_AUTH_TOKEN=your-twilio-token
TWILIO_PHONE_NUMBER=your-twilio-phone
```

## Ejecución

1. Iniciar el servidor:
```bash
uvicorn app.main:app --reload
```

2. Acceder a la documentación de la API:
```
http://localhost:8000/docs
```

## Endpoints Principales

### Autenticación
- POST /api/v1/login - Login de usuario

### Usuarios
- POST /api/v1/person/add - Crear nuevo usuario
- GET /api/v1/people - Listar usuarios
- GET /api/v1/user-info - Obtener información del usuario actual

### Clínicas
- POST /api/v1/clinic/add - Crear nueva clínica
- GET /api/v1/clinic - Listar clínicas
- GET /api/v1/clinic/{id} - Obtener clínica por ID
- PUT /api/v1/clinic/{id} - Actualizar clínica
- DELETE /api/v1/clinic/{id} - Eliminar clínica

### Citas
- POST /api/v1/appointment/create - Crear nueva cita
- GET /api/v1/appointment - Listar citas del usuario
- GET /api/v1/appointment/{id} - Obtener cita por ID
- PUT /api/v1/appointment/update - Actualizar cita
- POST /api/v1/appointment/reserve - Reservar cita

### Administración
- GET /admin/api/v1/specialities - Listar especialidades
- GET /admin/api/v1/server/status - Estado del servidor

## Seguridad

- Autenticación mediante JWT
- Roles de usuario (ADMIN, DOCTOR, PATIENT)
- Validación de datos con Pydantic
- Protección contra CSRF
- CORS configurado

## Contribución

1. Fork el repositorio
2. Crear una rama para tu feature (`git checkout -b feature/AmazingFeature`)
3. Commit tus cambios (`git commit -m 'Add some AmazingFeature'`)
4. Push a la rama (`git push origin feature/AmazingFeature`)
5. Abrir un Pull Request

## Licencia

Este proyecto está bajo la Licencia MIT. Ver el archivo `LICENSE` para más detalles. 