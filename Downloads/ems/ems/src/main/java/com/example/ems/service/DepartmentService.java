package com.example.ems.service;



import com.example.ems.entity.*;
        import com.example.ems.repository.*;
        import com.example.ems.exceptions.*;
        import org.springframework.data.domain.*;
        import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class DepartmentService {
    private final DepartmentRepository deptRepo;
    private final EmployeeRepository empRepo;

    public DepartmentService(DepartmentRepository deptRepo, EmployeeRepository empRepo) {
        this.deptRepo = deptRepo; this.empRepo = empRepo;
    }

    public Department create(Department dept) {
        if (dept.getCreationDate() == null) dept.setCreationDate(LocalDate.now());
        return deptRepo.save(dept);
    }

    public Page<Department> listAll(Pageable pageable) { return deptRepo.findAll(pageable); }

    public Department getById(Long id) { return deptRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Department not found")); }

    @Transactional
    public Department update(Long id, Department req) {
        Department d = getById(id);
        if (req.getName() != null) d.setName(req.getName());
        if (req.getDepartmentHead() != null) {
            Long headId = req.getDepartmentHead().getId();
            Employee head = empRepo.findById(headId).orElseThrow(() -> new ResourceNotFoundException("Head employee not found"));
            d.setDepartmentHead(head);
        }
        return deptRepo.save(d);
    }

    @Transactional
    public void delete(Long id) {
        long count = empRepo.countByDepartmentId(id);
        if (count > 0) throw new BadRequestException("Cannot delete department with assigned employees");
        deptRepo.deleteById(id);
    }

    public List<Employee> employeesInDept(Long deptId) {
        Department d = getById(deptId);
        return d.getEmployees();
    }
}
