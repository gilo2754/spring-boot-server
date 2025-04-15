# DocLib Python API

![Python](https://img.shields.io/badge/python-3.11-blue.svg)
![FastAPI](https://img.shields.io/badge/FastAPI-0.104.1-green.svg)
![SQLAlchemy](https://img.shields.io/badge/SQLAlchemy-2.0.23-red.svg)
![License](https://img.shields.io/badge/license-MIT-blue.svg)
![Build Status](https://github.com/gilo2754/doclib-python/workflows/Python%20application/badge.svg)

Una implementación moderna de la API de DocLib usando FastAPI y Python 3.11.

## 🚀 Características

- 🔐 Autenticación JWT
- 👥 Gestión de usuarios (Admin, Doctor, Paciente)
- 🏥 Gestión de clínicas
- 📅 Sistema de citas
- 📧 Notificaciones por email
- 📝 Documentación automática con Swagger/OpenAPI
- 🗄️ Base de datos MySQL
- 🐳 Docker support

## 📋 Requisitos

- Python 3.11+
- pip
- MySQL 8.0
- Docker (opcional)
- SMTP server para emails

## 🛠️ Instalación

1. Clonar el repositorio:
```bash
git clone https://github.com/gilo2754/doclib-python.git
cd doclib-python
```

2. Crear y activar entorno virtual:
```bash
python -m venv venv
# Windows
.\venv\Scripts\activate
# Linux/Mac
source venv/bin/activate
```

3. Instalar dependencias:
```bash
pip install -r requirements.txt
```

4. Configurar variables de entorno:
```bash
cp .env.example .env
# Editar .env con tus configuraciones
```

5. Inicializar la base de datos:
```bash
python init_db.py
```

## 🚀 Ejecución

### Desarrollo local
```bash
uvicorn app.main:app --reload
```

### Docker
```bash
docker-compose up --build
```

## 📚 Documentación

La documentación de la API está disponible en:
- Swagger UI: http://localhost:8000/docs
- ReDoc: http://localhost:8000/redoc

## 🔑 Endpoints principales

### Autenticación
- POST `/token` - Obtener token JWT
- POST `/token/refresh` - Refrescar token

### Usuarios
- POST `/person/add` - Crear usuario
- GET `/people` - Listar usuarios
- GET `/user-info` - Información del usuario actual

### Clínicas
- POST `/clinic/add` - Crear clínica
- GET `/clinic` - Listar clínicas
- GET `/clinic/{id}` - Obtener clínica
- PUT `/clinic/{id}` - Actualizar clínica
- DELETE `/clinic/{id}` - Eliminar clínica

### Citas
- POST `/appointment/create` - Crear cita
- GET `/appointment` - Listar citas
- GET `/appointment/{id}` - Obtener cita
- PUT `/appointment/update` - Actualizar cita
- POST `/appointment/reserve` - Reservar cita

## 🔒 Seguridad

- JWT para autenticación
- Roles de usuario (Admin, Doctor, Paciente)
- Validación de datos con Pydantic
- Protección CSRF
- CORS configurado

## 🤝 Contribuir

1. Fork el repositorio
2. Crear una rama para tu feature (`git checkout -b feature/AmazingFeature`)
3. Commit tus cambios (`git commit -m 'Add some AmazingFeature'`)
4. Push a la rama (`git push origin feature/AmazingFeature`)
5. Abrir un Pull Request

## 📝 Licencia

Este proyecto está bajo la Licencia MIT - ver el archivo [LICENSE](LICENSE) para más detalles.

## 👥 Autores

- Carlo Menjivar - [@gilo2754](https://github.com/gilo2754) 