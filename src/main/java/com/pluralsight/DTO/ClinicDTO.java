package com.pluralsight.DTO;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClinicDTO {
    private String clinicName;
    private String clinicDescription;
    private List<Long> doctorUserIds;
}
