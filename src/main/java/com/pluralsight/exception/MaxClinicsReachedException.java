package com.pluralsight.exception;


import static com.pluralsight.security.ApplicationConfig.MAX_CLINICS_ALLOWED;

public class MaxClinicsReachedException extends RuntimeException {

    public MaxClinicsReachedException() {
        super("El doctor ha alcanzado el límite máximo de clínicas permitidas. Actualmente se permiten "+ MAX_CLINICS_ALLOWED +  "clinicas.");
    }

    // Puedes agregar constructores adicionales o métodos según tus necesidades

}
