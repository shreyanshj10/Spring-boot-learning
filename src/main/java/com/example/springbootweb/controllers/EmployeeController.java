package com.example.springbootweb.controllers;


import com.example.springbootweb.dto.EmployeeDTO;
import com.example.springbootweb.repositories.EmployeeRepository;
import com.example.springbootweb.services.EmployeeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/employees")
public class EmployeeController {

    private final EmployeeRepository employeeRepository;
    private final EmployeeService employeeService;

    public EmployeeController(EmployeeRepository employeeRepository, EmployeeService employeeService) {
        this.employeeRepository = employeeRepository;
        this.employeeService = employeeService;
    }

//    @GetMapping
//    public List<EmployeeEntity> getEmployees() {
//        return employeeRepository.findAll();
//    }

//    @GetMapping(path = "/{employeeId}")
//    public EmployeeDTO getEmployee(@PathVariable Long employeeId) {
//        return new EmployeeDTO(employeeId, "Shreyansh", "shreyansh@gmail.com", 23);
//    }
//
//    @GetMapping
//    public String getEmployeeNew(@RequestParam Long age) {
//        return "New employee with id: " + age;
//    }

//    @PostMapping
//    public String postEmployees() {
//        return "Employee added";
//    }

//    @PostMapping
//    public EmployeeDTO postEmployeesJson(@RequestBody EmployeeDTO input) {
//        input.setId(2L);
//        return input;
//    }

//    @PostMapping
//    public EmployeeEntity postEmployeesJson(@RequestBody EmployeeEntity input) {
//        return  employeeRepository.save(input);
//    }

//    MVC
    @GetMapping
    public ResponseEntity<List<EmployeeDTO>> getEmployees() {
        return ResponseEntity.ok().body(employeeService.getEmployees());
    }

    @GetMapping(path = "/{employeeId}")
    public ResponseEntity<EmployeeDTO> getEmployee(@PathVariable Long employeeId) {
        return ResponseEntity.ok().body(employeeService.getEmployee(employeeId));
    }

    @PostMapping
    public ResponseEntity<EmployeeDTO> postEmployeesJson(@RequestBody EmployeeDTO input) {
        return  ResponseEntity.status(201).body(employeeService.postEmployeesJson(input));
    }

    @DeleteMapping(path = "/{employeeId}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long employeeId) {
        Boolean result = employeeService.deleteEmployee(employeeId);
        if(!result) { return ResponseEntity.notFound().build(); }
        return ResponseEntity.ok().build();
    }

    @PutMapping(path = "/{employeeId}")
    public ResponseEntity<EmployeeDTO> updateEmployeeAll(@RequestBody EmployeeDTO input, @PathVariable Long employeeId) {
        EmployeeDTO result = employeeService.updateEmployeeAll(input, employeeId);;
        if (result == null ) return ResponseEntity.notFound().build();
        return ResponseEntity.ok().body(result);
    }

    @PatchMapping(path = "/{employeeId}")
    public ResponseEntity<EmployeeDTO> updateEmployee(@RequestBody Map<String, Object> input, @PathVariable Long employeeId) {
        EmployeeDTO result = employeeService.updateEmployee(input, employeeId);
        if (result == null ) return ResponseEntity.notFound().build();
        return ResponseEntity.ok().body(result);
    }
}
