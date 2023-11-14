package com.example.project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.project.handler.CustomResponse;
import com.example.project.model.Employee;
import com.example.project.repository.EmployeeRepository;

@RestController
@CrossOrigin
@RequestMapping("api")
public class EmployeeRestController {
    @Autowired
    private EmployeeRepository employeeRepository;

    @GetMapping("employee")
    public ResponseEntity<Object> get() {
        List<Employee> data = employeeRepository.findAll();
        if (data.isEmpty()) {
            return CustomResponse.generate(HttpStatus.OK, "Data tidak ditemukan", data);
        }
        return CustomResponse.generate(HttpStatus.OK, "Data ditemukan", data);
    }

    @PostMapping(value = { "employee", "employee/{id}" })
    public ResponseEntity<Object> save(@RequestBody Employee employee, @PathVariable(required = false) Integer id) {

        if (employee.getId() == null) {
            employeeRepository.save(employee);
            Boolean result = employeeRepository.findById(employee.getId()).isPresent();
            if (result) {
                return CustomResponse.generate(HttpStatus.OK, "Data Berhasil di Disimpan");
            }
        }
        employeeRepository.findById(employee.getId()).isPresent();
        employeeRepository.save(employee);
        return CustomResponse.generate(HttpStatus.OK, "Data Berhasil diubah");
    }

    @DeleteMapping("employee/{id}")
    public ResponseEntity<Object> delete(@PathVariable(required = true) Integer id) {
        Boolean result = employeeRepository.findById(id).isPresent();
        if (result) {
            employeeRepository.deleteById(id);
            return CustomResponse.generate(HttpStatus.OK, "Berhasil Menghapus Data");
        }
        return CustomResponse.generate(HttpStatus.BAD_REQUEST, "Data yang ingin dihapus tidak ditemukan");
    }

    @GetMapping("employee/{id}")
    public ResponseEntity<Object> get(@PathVariable(required = true) Integer id) {
        Boolean result = employeeRepository.findById(id).isPresent();
        if (result) {
            Employee employee = employeeRepository.findById(id).orElse(null);
            return CustomResponse.generate(HttpStatus.OK, "Data ditemukan", employee);
        }
        return CustomResponse.generate(HttpStatus.BAD_REQUEST, "Data Tidak ditemukan", result);

    }
    
    @GetMapping("manager")
    public ResponseEntity<Object> getManagers() {
        List<Employee> managers = employeeRepository.findManagers();
        if (managers.isEmpty()) {
            return CustomResponse.generate(HttpStatus.OK, "Data tidak ditemukan", managers);
        }
        return CustomResponse.generate(HttpStatus.OK, "Data ditemukan", managers);
    }
}