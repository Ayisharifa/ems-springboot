package com.example.ems.mapper;

import com.example.ems.entity.Employee;
import com.example.ems.dto.EmployeeResponseDTO;
import com.example.ems.dto.IdNameDTO;
import lombok.Getter;
import lombok.Setter;

import java.time.format.DateTimeFormatter;

@Getter
@Setter
public class Mapper {


    private static final DateTimeFormatter F = DateTimeFormatter.ISO_LOCAL_DATE;

    public static EmployeeResponseDTO toEmployeeDTO(Employee e) {
        if (e == null) return null;


        EmployeeResponseDTO r = new EmployeeResponseDTO();

        r.setId(e.getId());
        r.setFirstName(e.getFirstName());
        r.setLastName(e.getLastName());
        r.setDateOfBirth(e.getDateOfBirth() == null ? null : e.getDateOfBirth().format(F));
        r.setSalary(e.getSalary() == null ? null : e.getSalary().doubleValue());

        r.setAddress(e.getAddress());
        r.setRoleTitle(e.getRoleTitle());
        r.setJoiningDate(e.getJoiningDate() == null ? null : e.getJoiningDate().format(F));
        r.setYearlyBonusPercentage(e.getYearlyBonusPercentage());
        r.setEmail(e.getEmail());

        if (e.getDepartment() != null) {
            r.setDepartment(new IdNameDTO(e.getDepartment().getId(), e.getDepartment().getName()));
        }

        if (e.getReportingManager() != null) {
            String managerName = e.getReportingManager().getFirstName();
            if (e.getReportingManager().getLastName() != null) {
                managerName += " " + e.getReportingManager().getLastName();
            }
            r.setReportingManager(new IdNameDTO(e.getReportingManager().getId(), managerName));
        }

        return r;
    }
}


