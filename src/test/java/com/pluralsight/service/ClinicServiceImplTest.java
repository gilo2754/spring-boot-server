package com.pluralsight.service;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import com.pluralsight.entity.Clinic;
import com.pluralsight.enums.Speciality;
import com.pluralsight.repository.ClinicRepository;

import java.util.Arrays;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class ClinicServiceImplTest {

    @Mock
    private ClinicRepository clinicRepository;

    @InjectMocks
    private ClinicServiceImpl clinicService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetClinicsBySpeciality() {
        Speciality speciality = Speciality.PEDIATRIA;

        // create some clinics with matching and non-matching specialities
        Clinic clinic1 = new Clinic("Clinic 1", "123 Main St", speciality);
        Clinic clinic2 = new Clinic("Clinic 2", "456 Elm St", Speciality.DERMATOLOGIA);
        Clinic clinic3 = new Clinic("Clinic 3", "789 Oak St", speciality);
        List<Clinic> clinics = Arrays.asList(clinic1, clinic2, clinic3);

        // mock the clinicRepository to return the clinics we created
        when(clinicRepository.findBySpeciality(speciality.name())).thenReturn(clinics);

        // call the method being tested
        List<Clinic> result = clinicService.getClinicsBySpeciality(speciality);

        // verify that the correct clinics were returned
        assertThat(result).containsExactly(clinic1, clinic3);

        // verify that the findBySpeciality method was called with the correct argument
        verify(clinicRepository).findBySpeciality(speciality.name());
    }
}
