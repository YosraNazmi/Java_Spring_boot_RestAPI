package com.example.springboot.controller;

import com.example.springboot.exceptions.resourceNotFoundException;
import com.example.springboot.model.employee;
import com.example.springboot.repository.employeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin (origins = "http://localhost:3000/")
@RestController
@RequestMapping("/api/v1/")
public class employeeController {

    @Autowired
    private employeeRepository employeeRepository;

    //Get All employees
    @GetMapping("/employees")
    public List<employee> getAllEmployees(){
        return employeeRepository.findAll();
    }

    // Create New Employee
    @PostMapping("/employees")
    public employee createEmployee( @RequestBody employee employee){
        return employeeRepository.save(employee);
    }

    //Get Employee by ID
    @GetMapping("/employees/{id}")
    public ResponseEntity< employee >getEmployeeById(@PathVariable Long id){
        employee employee = employeeRepository.findById(id).orElseThrow(() ->
                new resourceNotFoundException("Employee not Exist with ID :" + id));
        return ResponseEntity.ok(employee);
    }

    //Update Employee
    @PutMapping("/employees/{id}")
    public ResponseEntity<employee> updateEmployee(@PathVariable Long id, @RequestBody employee employeeDetails){
        employee employee = employeeRepository.findById(id).orElseThrow(() ->
                new resourceNotFoundException("Employee not Exist with ID :" + id));

        employee.setFirstName(employeeDetails.getFirstName());
        employee.setLastName(employeeDetails.getLastName());
        employee.setEmail(employeeDetails.getEmail());

        employee updateEmployee = employeeRepository.save(employee);
        return ResponseEntity.ok(updateEmployee);
    }

    //Delete Employee
    @DeleteMapping("/employees/{id}")
    public ResponseEntity<Map<String, Boolean> >deleteEmployee(@PathVariable Long id){
        employee employee = employeeRepository.findById(id).orElseThrow(() ->
                new resourceNotFoundException("Employee not Exist with ID :" + id));

        employeeRepository.delete(employee);
        Map<String, Boolean> response = new HashMap<>();
        response.put("delete", Boolean.TRUE);
        return ResponseEntity.ok(response);

    }



}
