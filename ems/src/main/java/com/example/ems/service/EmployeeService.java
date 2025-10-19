package com.example.ems.service;

import com.example.ems.dto.EmployeeRequestDto;
import com.example.ems.entity.*;
        import com.example.ems.repository.*;
import com.example.ems.exceptions.*;
        import org.springframework.data.domain.*;
        import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.math.BigDecimal;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepo;
    private final DepartmentRepository deptRepo;

    public EmployeeService(EmployeeRepository employeeRepo, DepartmentRepository deptRepo) {
        this.employeeRepo = employeeRepo;
        this.deptRepo = deptRepo;
    }

    @Transactional
    public Employee create(EmployeeRequestDto req) {
        Employee e = new Employee();
        applyRequestToEntity(req, e);
        return employeeRepo.save(e);
    }

    @Transactional
    public Employee update(Long id, EmployeeRequestDto req) {
        Employee e = employeeRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Employee not found"));
        applyRequestToEntity(req, e);
        return employeeRepo.save(e);
    }

    private void applyRequestToEntity(EmployeeRequestDto req, Employee e) {
        if (req.firstName != null) e.setFirstName(req.firstName);
        if (req.lastName != null) e.setLastName(req.lastName);
        if (req.dateOfBirth != null) e.setDateOfBirth(LocalDate.parse(req.dateOfBirth));
        if (req.salary != null) e.setSalary(BigDecimal.valueOf(req.salary));
        if (req.getDepartmentId() != null) {
            Department d = deptRepo.findById(req.getDepartmentId()).orElseThrow(() -> new ResourceNotFoundException("Department not found"));
            e.setDepartment(d);
        }
        if (req.address != null) e.setAddress(req.address);
        if (req.roleTitle != null) e.setRoleTitle(req.roleTitle);
        if (req.joiningDate != null) e.setJoiningDate(LocalDate.parse(req.joiningDate));
        if (req.yearlyBonusPercentage != null) e.setYearlyBonusPercentage(req.yearlyBonusPercentage);
        if (req.reportingManagerId != null) {
            if (req.reportingManagerId.equals(e.getId())) throw new BadRequestException("Employee cannot be their own manager");
            Employee manager = employeeRepo.findById(req.reportingManagerId).orElseThrow(() -> new ResourceNotFoundException("Reporting manager not found"));
            e.setReportingManager(manager);
        }
        if (req.email != null) e.setEmail(req.email);
    }

    public Page<Employee> listAll(Pageable pageable) { return employeeRepo.findAll(pageable); }

    public Page<Employee> listLookup(Pageable pageable) { return employeeRepo.findAllForLookup(pageable); }

    public Employee getById(Long id) { return employeeRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Employee not found")); }

    @Transactional
    public Employee moveToDepartment(Long empId, Long newDeptId) {
        Employee e = getById(empId);
        Department d = deptRepo.findById(newDeptId).orElseThrow(() -> new ResourceNotFoundException("Department not found"));
        e.setDepartment(d);
        return employeeRepo.save(e);
    }
}

