package com.example.ems.repository;



import com.example.ems.entity.Employee;
import org.springframework.data.domain.*;
        import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Page<Employee> findAll(Pageable pageable);

    Page<Employee> findByDepartmentId(Long departmentId, Pageable pageable);

    long countByDepartmentId(Long departmentId);


    @Query("select e from Employee e")
    Page<Employee> findAllForLookup(Pageable pageable);
}
