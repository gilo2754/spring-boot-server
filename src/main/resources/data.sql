INSERT INTO clinic (clinic_id, clinic_name, clinic_description, clinic_address, clinic_phone_number,
clinic_status)
VALUES (2, 'Clinica de la Sagrada','Donde se curan tood', 'call los peroles 3, Apopa', '22149233', 'IN_REVIEW');
INSERT INTO appointment (appointment_id, clinic_id, appointment_status)
VALUES (2, 2, 'CONFIRMED');
INSERT INTO patient (appointment_id, clinic_id, appointment_status)
VALUES (2, 2, 'CONFIRMED');
