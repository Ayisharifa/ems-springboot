package com.example.ems.dto;

public class EmployeeRequestDto {
    private Long departmentId;  // must be Long
    public String firstName;
    public String lastName;
    public String dateOfBirth;
    public Double salary;
    public String address;
    public String roleTitle;
    public String joiningDate;
    public Double yearlyBonusPercentage;
    public Long reportingManagerId;
    public String email;


    public Long getDepartmentId() { return departmentId; }
    public void setDepartmentId(Long departmentId) { this.departmentId = departmentId; }


}


