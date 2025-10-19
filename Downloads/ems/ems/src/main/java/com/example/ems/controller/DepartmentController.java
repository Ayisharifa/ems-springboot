package com.example.ems.controller;

import com.example.ems.service.DepartmentService;
import com.example.ems.entity.Department;
import com.example.ems.entity.Employee;
import com.example.ems.dto.PagedResponse;
import com.example.ems.dto.IdNameDTO;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.*;
        import org.springframework.http.*;
        import org.springframework.web.bind.annotation.*;

        import java.util.stream.Collectors;
@Getter
@Setter
@RestController
@RequestMapping("/api/departments")
public class DepartmentController {
    private final DepartmentService svc;
    private static final int DEFAULT_SIZE = 20;

    public DepartmentController(DepartmentService svc) { this.svc = svc; }

    @PostMapping
    public ResponseEntity<Department> create(@RequestBody Department dept) {
        Department d = svc.create(dept);
        return ResponseEntity.status(HttpStatus.CREATED).body(d);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Department> update(@PathVariable Long id, @RequestBody Department dept) {
        Department d = svc.update(id, dept);
        return ResponseEntity.ok(d);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        svc.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<PagedResponse<?>> list(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", required = false) Integer size,
            @RequestParam(value = "expand", required = false) String expand) {

        int s = (size == null) ? DEFAULT_SIZE : size;
        Pageable p = PageRequest.of(page, s);
        Page<Department> pr = svc.listAll(p);

        if ("employee".equalsIgnoreCase(expand)) {
            var content = pr.getContent().stream().map(d -> {
                var map = new java.util.HashMap<String, Object>();
                map.put("id", d.getId());
                map.put("name", d.getName());
                map.put("creationDate", d.getCreationDate());
                if (d.getDepartmentHead() != null) map.put("head", new IdNameDTO(d.getDepartmentHead().getId(), d.getDepartmentHead().getFirstName() + (d.getDepartmentHead().getLastName()==null?"":" " + d.getDepartmentHead().getLastName())));
                var emps = d.getEmployees() == null ? java.util.List.of() : d.getEmployees().stream().map(emp -> {
                    var em = new java.util.HashMap<String,Object>();
                    em.put("id", emp.getId());
                    em.put("firstName", emp.getFirstName());
                    em.put("lastName", emp.getLastName());
                    em.put("roleTitle", emp.getRoleTitle());
                    return em;
                }).collect(Collectors.toList());
                map.put("employees", emps);
                return map;
            }).collect(Collectors.toList());

            return ResponseEntity.ok(new PagedResponse<>(pr.getNumber(), pr.getSize(), pr.getTotalPages(), pr.getTotalElements(), content));
        } else {
            return ResponseEntity.ok(new PagedResponse<>(pr.getNumber(), pr.getSize(), pr.getTotalPages(), pr.getTotalElements(), pr.getContent()));
        }
    }

    @GetMapping("/{id}/employees")
    public ResponseEntity<PagedResponse<?>> employeesInDept(
            @PathVariable Long id,
            @RequestParam(value="page", defaultValue="0") int page,
            @RequestParam(value="size", required=false) Integer size) {

        int s = (size == null) ? DEFAULT_SIZE : size;
        Pageable p = PageRequest.of(page, s);

        Department d = svc.getById(id);
        Page<Employee> pageEmps = org.springframework.data.domain.Page.empty();


        var list = d.getEmployees() == null ? java.util.List.of() : d.getEmployees();
        return ResponseEntity.ok(new PagedResponse<>(0, list.size(), 1, list.size(), list));
    }
}

