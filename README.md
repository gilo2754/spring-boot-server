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

In the future, the application has the following planned enhancements:

- **Dockerizing the Frontend:** Integrate Angular frontend and containerize it using Docker.
https://github.com/gilo2754/DocLib2_Angular
- **Dockerizing the Backend:** Containerize the Spring Boot backend using Docker.
- **Cloud Hosting:** Deploy the application to AWS or Google Cloud for scalability and reliability.
- **Object Storage Service:** Utilize a Blob storage service for managing images and other objects.

-> The application will be available at http://localhost:8081.



