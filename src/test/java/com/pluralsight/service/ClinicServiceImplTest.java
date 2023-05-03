package com.pluralsight.service;

import static org.mockito.Mockito.*;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.pluralsight.entity.Clinic;
import com.pluralsight.enums.Speciality;
import com.pluralsight.repository.ClinicRepository;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
//import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class ClinicServiceImplTest {

    @Mock
    private ClinicRepository clinicRepository;

    @InjectMocks
    private ClinicServiceImpl clinicService;

    @Before
    public void setUp() {
        // Any additional setup before the tests
        MockitoAnnotations.initMocks(this);

    }

    @Test
    public void testGetClinicsBySpeciality() {
        String speciality = "PEDIATRIA";

        // create some clinics with matching and non-matching specialities
        Clinic clinic1 = new Clinic("Clinic 1", "123 Main St", Speciality.PEDIATRIA);
        Clinic clinic2 = new Clinic("Clinic 2", "456 Elm St", Speciality.DERMATOLOGIA);
        Clinic clinic3 = new Clinic("Clinic 3", "789 Oak St", Speciality.PEDIATRIA);
        List<Clinic> clinics = Arrays.asList(clinic1, clinic2, clinic3);

        // mock the clinicRepository to return the clinics we created
        when(clinicRepository.findBySpeciality(anyString())).thenReturn(clinics);

        // call the method being tested
        List<Clinic> result = clinicService.getClinicsBySpeciality(speciality);

        // verify that the correct clinics were returned
        assertThat(result).containsExactly(clinic1, clinic3);

        // verify that the findBySpeciality method was called with the correct argument
        verify(clinicRepository).findBySpeciality(speciality);
    }

}
