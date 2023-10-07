-- Insertar datos de la tabla Clinic
INSERT INTO clinic (clinic_id, clinic_name, clinic_description, clinic_address, clinic_phone_number, clinic_state, speciality, opening_time, closing_time)
VALUES
(1, 'Clinica San Juan', 'Clinica especializada en medicina general', 'Calle San Juan 123', '+52 555-123-4567', 'ACTIVE', 'PEDIATRIA', '08:00:00', '18:00:00'),
(2, 'Clinica Santa Maria', 'Clinica especializada en pediatría y obstetricia', 'Avenida Santa Maria 456', '+52 555-234-5678', 'ACTIVE', 'OFTALMOLOGIA', '09:00:00', '17:00:00');

-- Insertar datos de la tabla User (Pacientes y Doctores) con el atributo dui y dirección
INSERT INTO _user (user_id, role, first_name, last_name, email, phone_number, date_of_birth, social_number, username, password, speciality, clinic_id, dui, address_id)
VALUES
(1, 'PATIENT', 'CalinPatient', 'Menji', 'mail@asd.de', '45678932', '1990-01-02', '012345', 'calipa', '$2a$10$NsBJlyMzHNGRdADGhWPKZ.ovl5MDEgtsBMUZYHAigWKE4eASgCpVa', NULL, NULL, '0434565-5', 1),
(2, 'PATIENT', 'ConxnPatient', 'Ruiz', 'mail@asd.de', '45678932', '1990-01-02', '012345', 'conypa', '$2a$10$NsBJlyMzHNGRdADGhWPKZ.ovl5MDEgtsBMUZYHAigWKE4eASgCpVa', NULL, NULL, '0434565-6', 2),
(3, 'DOCTOR', 'MarioDoc', 'Menji', 'mail@asd.de', '45678932', '1990-01-02', NULL, 'mariodoc', '$2a$10$NsBJlyMzHNGRdADGhWPKZ.ovl5MDEgtsBMUZYHAigWKE4eASgCpVa', 'NEUROLOGIA', 1, '0434565-7', 3),
(4, 'DOCTOR', 'JuanitoDoc', 'Menji', 'mail@asd.de', '45678932', '1990-01-02', NULL, 'juanitodoc', '$2a$10$NsBJlyMzHNGRdADGhWPKZ.ovl5MDEgtsBMUZYHAigWKE4eASgCpVa', 'NEUROLOGIA', 1, '0434565-8', 4),
(5, 'DOCTOR', 'BobDOC', 'Johnson', 'bobjohnson@example.com', '+123456789', '1985-01-01', NULL, 'bobdoc', '$2a$10$NsBJlyMzHNGRdADGhWPKZ.ovl5MDEgtsBMUZYHAigWKE4eASgCpVa', 'DERMATOLOGIA', 2, '0434565-9', 5);

-- Insertar datos de la tabla Address
INSERT INTO address (id, street, neighborhood, city, department, postal_code, additional_info)
VALUES
(1, 'Calle San Juan 123', 'Colonia San Juan', 'San Salvador', 'San Salvador', '01101', 'Frente a la plaza'),
(2, 'Avenida Santa Maria 456', 'Colonia Santa Maria', 'Santa Tecla', 'La Libertad', '01102', 'Cerca del hospital');
