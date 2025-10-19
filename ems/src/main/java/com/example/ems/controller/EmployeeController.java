package com.example.ems.controller;



import com.example.ems.service.EmployeeService;
import com.example.ems.entity.Employee;
import com.example.ems.dto.*;
        import com.example.ems.mapper.Mapper;

import org.springframework.data.domain.*;
        import org.springframework.http.*;
        import org.springframework.web.bind.annotation.*;

        import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {
    private final EmployeeService svc;
    private static final int DEFAULT_SIZE = 20;

    public EmployeeController(EmployeeService svc) { this.svc = svc; }

    @PostMapping
    public ResponseEntity<EmployeeResponseDTO> create(@RequestBody EmployeeRequestDto req) {
        Employee e = svc.create(req);
        return ResponseEntity.status(HttpStatus.CREATED).body(Mapper.toEmployeeDTO(e));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeeResponseDTO> update(@PathVariable Long id, @RequestBody EmployeeRequestDto req) {
        Employee e = svc.update(id, req);
        return ResponseEntity.ok(Mapper.toEmployeeDTO(e));
    }

    @GetMapping
    public ResponseEntity<PagedResponse<?>> list(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", required = false) Integer size,
            @RequestParam(value = "lookup", defaultValue = "false") boolean lookup) {

        int s = (size == null) ? DEFAULT_SIZE : size;
        Pageable p = PageRequest.of(page, s);
        if (lookup) {
            Page<Employee> pageResult = svc.listLookup(p);
            var content = pageResult.getContent().stream()
                    .map(e -> new IdNameDTO(e.getId(), e.getFirstName() + (e.getLastName()==null?"":" " + e.getLastName())))
                    .collect(Collectors.toList());
            return ResponseEntity.ok(new PagedResponse<>(pageResult.getNumber(), pageResult.getSize(), pageResult.getTotalPages(), pageResult.getTotalElements(), content));
        } else {
            Page<Employee> pageResult = svc.listAll(p);
            var content = pageResult.getContent().stream().map(Mapper::toEmployeeDTO).collect(Collectors.toList());
            return ResponseEntity.ok(new PagedResponse<>(pageResult.getNumber(), pageResult.getSize(), pageResult.getTotalPages(), pageResult.getTotalElements(), content));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeResponseDTO> get(@PathVariable Long id) {
        Employee e = svc.getById(id);
        return ResponseEntity.ok(Mapper.toEmployeeDTO(e));
    }

    @PatchMapping("/{id}/move")
    public ResponseEntity<EmployeeResponseDTO> move(@PathVariable Long id, @RequestParam Long newDepartmentId) {
        Employee e = svc.moveToDepartment(id, newDepartmentId);
        return ResponseEntity.ok(Mapper.toEmployeeDTO(e));
    }

    @GetMapping("/{id}/reporting-chain")
    public ResponseEntity<?> reportingChain(@PathVariable Long id) {
        Employee e = svc.getById(id);
        java.util.List<IdNameDTO> chain = new java.util.ArrayList<>();
        Employee cur = e.getReportingManager();
        while (cur != null) {
            chain.add(new IdNameDTO(cur.getId(), cur.getFirstName() + (cur.getLastName()==null?"":" " + cur.getLastName())));
            cur = cur.getReportingManager();
        }
        return ResponseEntity.ok(chain);
    }
}
