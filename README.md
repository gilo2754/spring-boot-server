# Spring Boot for medical appointments

This is a Spring Boot application that provides CRUD endpoints for managing various entities. It utilizes the following technologies and frameworks:

- **Spring Security:** Provides authentication and authorization features.
- **JPA (Java Persistence API):** A standard API for accessing relational databases.
- **H2 Database:** An in-memory database for development and testing purposes.
- **SonarQube:** A static code analysis tool for maintaining code quality.

## Endpoints

The application exposes the following endpoints for CRUD operations:

### Clinic

- `GET /clinic`: Retrieve all clinics
- `GET /clinic/{id}`: Retrieve a clinic by ID
- `POST /clinic/add`: Create a new clinic
- `PUT /clinic/{id}`: Update a clinic
- `DELETE /clinic/{id}`: Delete a clinic

### User, Patient and Doctor

- `GET /people`: Retrieve all users
- `GET /people?role=DOCTOR`: Retrieve all doctors
- `GET /people?role=PATIENT`: Retrieve all patients

- `GET /users/{id}`: Retrieve a user by ID
- `POST /user/add`: Create a new user

### Appointment

- `GET /appointment`: Retrieve all appointments
- `POST /appointment/add`: Create a new appointment

## Future Plans

In the future, the application has the following planned enhancements:

- **Dockerizing the Frontend:** Integrate Angular frontend and containerize it using Docker.
https://github.com/gilo2754/DocLib2_Angular
- **Dockerizing the Backend:** Containerize the Spring Boot backend using Docker.
- **Cloud Hosting:** Deploy the application to AWS or Google Cloud for scalability and reliability.
- **Object Storage Service:** Utilize a Blob storage service for managing images and other objects.


