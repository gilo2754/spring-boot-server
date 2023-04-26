INSERT INTO clinic (clinic_id, clinic_name, clinic_description, clinic_address, clinic_phone_number, clinic_state, speciality, opening_time, closing_time)
VALUES
(1, 'Clinica San Juan', 'Clinica especializada en medicina general', 'Calle San Juan 123', '+52 555-123-4567', 'ACTIVE', 'PEDIATRIA', '08:00:00', '18:00:00'),
(2, 'Clinica Santa Maria', 'Clinica especializada en pediatría y obstetricia', 'Avenida Santa Maria 456', '+52 555-234-5678', 'ACTIVE', 'OFTALMOLOGIA', '09:00:00', '17:00:00');


--Doctors:
INSERT INTO person (person_id, dtype, first_name, last_name, email, phone_number, date_of_birth,
                    speciality, clinic_id)
VALUES
    (1, 'Doctor', 'MarioDoc', 'Menji', 'mail@asd.de','45678932', '1990-01-02',  'NEUROLOGIA',  1);

--Patients:
INSERT INTO person (person_id, dtype, first_name, last_name, email, phone_number, date_of_birth, social_number, clinic_id)
VALUES      (2, 'Patient', 'CalinPatient', 'Menji', 'mail@asd.de', '45678932', '1990-01-02', '012345', 1);

--Appointments:
INSERT INTO appointment (appointment_id, clinic_id, patient_id, doctor_id, start_time, end_time, appointment_status)
VALUES (1, 1, 1, 1, '2023-04-01 09:00:00', '2023-04-01 10:00:00', 'PENDING'),
       (2, 2, 2, 2, '2023-04-02 11:00:00', '2023-04-02 12:00:00', 'COMPLETED');





