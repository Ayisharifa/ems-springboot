
package com.example.ems.dto;

public class EmployeeResponseDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String dateOfBirth;
    private Double salary;
    private IdNameDTO department;
    private String address;
    private String roleTitle;
    private String joiningDate;
    private Double yearlyBonusPercentage;
    private IdNameDTO reportingManager;
    private String email;

    public EmployeeResponseDTO() {}


    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getDateOfBirth() { return dateOfBirth; }
    public void setDateOfBirth(String dateOfBirth) { this.dateOfBirth = dateOfBirth; }

    public Double getSalary() { return salary; }
    public void setSalary(Double salary) { this.salary = salary; }

    public IdNameDTO getDepartment() { return department; }
    public void setDepartment(IdNameDTO department) { this.department = department; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getRoleTitle() { return roleTitle; }
    public void setRoleTitle(String roleTitle) { this.roleTitle = roleTitle; }

    public String getJoiningDate() { return joiningDate; }
    public void setJoiningDate(String joiningDate) { this.joiningDate = joiningDate; }

    public Double getYearlyBonusPercentage() { return yearlyBonusPercentage; }
    public void setYearlyBonusPercentage(Double yearlyBonusPercentage) { this.yearlyBonusPercentage = yearlyBonusPercentage; }

    public IdNameDTO getReportingManager() { return reportingManager; }
    public void setReportingManager(IdNameDTO reportingManager) { this.reportingManager = reportingManager; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}


