# Doctolib2 - Aplicación Spring Boot

Aplicación de gestión de citas médicas desarrollada con Spring Boot.

## Requisitos

- Java 17
- Maven 3.8+
- Docker y Docker Compose
- Portainer (para despliegue)

## Configuración para desarrollo local

1. Clona el repositorio
2. Copia el archivo `.env.example` a `.env` y configura las variables
3. Ejecuta `mvn clean install` para construir el proyecto
4. Ejecuta `docker-compose up` para iniciar la aplicación

## Despliegue en Portainer

### Preparación

1. Asegúrate de tener Portainer instalado y configurado
2. Crea un registro de Docker en Portainer si vas a usar una imagen privada
3. Configura las variables de entorno en Portainer

### Pasos para el despliegue

1. En Portainer, ve a "Stacks" y haz clic en "Add stack"
2. Asigna un nombre a tu stack (por ejemplo, "doctolib2")
3. Copia el contenido del archivo `docker-compose.prod.yml`
4. Configura las variables de entorno en la sección "Environment variables"
5. Haz clic en "Deploy the stack"

### Variables de entorno requeridas

```
DOCKER_IMAGE_NAME=doctolib2
DOCKER_IMAGE_TAG=latest
JWT_SECRET=your-jwt-secret-key
MAIL_FROM_PASSWORT=your-email-password
GOOGLE_CLIENT_ID=your-google-client-id
GOOGLE_CLIENT_SECRET=your-google-client-secret
GOOGLE_REDIRECT_URI=http://your-domain:8081/login/oauth2/code/google
```

### Monitoreo

La aplicación expone endpoints de Actuator para monitoreo:

- Health check: `http://your-domain:8081/actuator/health`
- Métricas: `http://your-domain:8081/actuator/metrics`
- Info: `http://your-domain:8081/actuator/info`

## Estructura del proyecto

```
.
├── src/
│   ├── main/
│   │   ├── java/
│   │   └── resources/
│   └── test/
├── Dockerfile
├── docker-compose.yml
├── docker-compose.prod.yml
├── .env.example
└── pom.xml
```

## Características

- Autenticación JWT
- Integración con Google OAuth2
- Envío de correos electrónicos
- API REST documentada con Swagger
- Monitoreo con Spring Boot Actuator
- Métricas con Prometheus

## Licencia

Este proyecto está bajo la Licencia MIT.

# Spring Boot for medical appointments

This is a Spring Boot application that provides CRUD endpoints for managing various entities. It utilizes the following technologies and frameworks:

## Technologies
- **Spring Boot 3.0**
- **JSON Web Tokens (JWT)**
- **BCrypt**
- **Maven**
- **Spring Security:** Provides authentication and authorization features.
- **JPA (Java Persistence API):** A standard API for accessing relational databases.
- **H2 Database:** An in-memory database for development and testing purposes.
- **SonarQube:** A static code analysis tool for maintaining code quality.

## Security Features
* User registration and login with JWT authentication
* Password encryption using BCrypt
* Role-based authorization with Spring Security (TODO)
* Customized access denied handling
* Logout mechanism(TODO)
* Refresh token (TODO)

## Endpoints
The application exposes the following endpoints for CRUD operations:

### Clinic

- `GET /clinic`: Retrieve all clinics
- `GET /clinic/{id}`: Retrieve a clinic by ID
- `POST /clinic/add`: Create a new clinic
- `PUT /clinic/{id}`: Update a clinic
- `DELETE /clinic/{id}`: Delete a clinic
- `GET /clinic/{id}/doctors`: Retrieve doctors for a specific clinic (e.g., clinic with ID 1)
- `GET /clinics?Speciality=CARDIOLOGIA`: Retrieve clinics by speciality (e.g., CARDIOLOGIA)


### User, Patient and Doctor

- `GET /people`: Retrieve all users
- `GET /people?role=DOCTOR`: Retrieve all doctors
- `GET /people?role=PATIENT`: Retrieve all patients
- `GET /user/{id}`: Retrieve a user by ID
- `POST /user/add`: Create a new user

### Appointment

- `GET /appointment`: Retrieve all appointments
- `POST /appointment/add`: Create a new appointment

### Additional Endpoints
- `GET /admin/api/v1/specialities`: Retrieve all specialities (admin access required)
- `GET /admin/api/v1/server/status`: Get the status of the server (admin access required)

## Future Plans
### Infrastructure
In the future, the application has the following planned enhancements:

- **Dockerizing the Frontend:** Integrate Angular frontend and containerize it using Docker.
https://github.com/gilo2754/DocLib2_Angular
- **Dockerizing the Backend:** Containerize the Spring Boot backend using Docker.
- **Cloud Hosting:** Deploy the application to AWS or Google Cloud for scalability and reliability.
- **Object Storage Service:** Utilize a Blob storage service for managing images and other objects.

### Upcoming Features

In the upcoming releases, the application will include the following features:

- **Limited Appointment Creation:** Users will be restricted to creating a limited number of appointments for a specific specialty. This limit will be configurable in the `applications.properties` file.

- **Monthly Appointment Limit:** Users will have a maximum limit on the number of appointments they can create within a month or a defined period. This limit will also be configurable in the `applications.properties` file.

- **Notification System:** The application will implement a notification system to send reminders, confirmations, and cancellations of appointments via SMS, email, or WhatsApp.

- **Google Maps Integration:** Integration with Google Maps will be added to provide location and directions for clinics.

- **Profile Picture Upload:** Users and clinics will have the ability to upload profile pictures for their profiles.

Please note that these features are planned for future development and may not be available in the current version of the application. Stay tuned for updates and enhancements!


-> The application will be available at http://localhost:8081.



