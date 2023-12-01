package com.pluralsight.DTO;

import java.time.LocalTime;
import java.util.List;

import com.pluralsight.enums.ClinicState;
import com.pluralsight.enums.Speciality;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClinicDTO {
    private Long clinic_id;
    private String clinic_name;
    private String clinic_description;
    private String clinic_address;
    private String clinic_phone_number;
    private ClinicState clinic_state = ClinicState.IN_REVIEW;
    private Speciality speciality;
    private LocalTime openingTime;
    private LocalTime closingTime;
    //private List<Long> doctorUserIds;
}
