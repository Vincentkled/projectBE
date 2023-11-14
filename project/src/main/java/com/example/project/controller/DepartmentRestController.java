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
import com.example.project.model.Department;
import com.example.project.repository.DepartmentRepository;



@RestController
@CrossOrigin
@RequestMapping("api")
public class DepartmentRestController {
    @Autowired
    private DepartmentRepository departmentRepository;

    @GetMapping("department")//localhostL8088/api/department
    public ResponseEntity<Object> get(){
        List<Department> data = departmentRepository.findAll();
        if(data.isEmpty()){
            return CustomResponse.generate(HttpStatus.OK, "Data tidak ditemukan", data);
        }
        return CustomResponse.generate(HttpStatus.OK, "Data ditemukan", data);
    }

    @PostMapping(value = {"department", "department/{id}"})
    public ResponseEntity<Object> save(@RequestBody Department department, @PathVariable(required = false) Integer id){

        if(department.getId() ==null){ 
            departmentRepository.save(department);
            Boolean result = departmentRepository.findById(department.getId()).isPresent();
            if(result){
                return CustomResponse.generate(HttpStatus.OK, "Data Berhasil di Disimpan");
        } 
    }
        departmentRepository.findById(department.getId()).isPresent();
        departmentRepository.save(department);
        return CustomResponse.generate(HttpStatus.OK, "Data Berhasil di edit");
        }
    
    @DeleteMapping("department/{id}")
    public ResponseEntity<Object> delete(@PathVariable(required = true) Integer id){
        Boolean result = departmentRepository.findById(id).isPresent();
        if (result){
            departmentRepository.deleteById(id);
        return CustomResponse.generate(HttpStatus.OK, "Berhasil Menghapus Data");
        }
        return CustomResponse.generate(HttpStatus.BAD_REQUEST, "Data yang ingin diHapus tidak ditemukan");
    }

    
    @GetMapping("department/{id}")
    public ResponseEntity<Object>get(@PathVariable(required = true) Integer id){
        Boolean result = departmentRepository.findById(id).isPresent();
        if(result){
            Department department = departmentRepository.findById(id).orElse(null);
            return CustomResponse.generate(HttpStatus.OK, "Data ditemukan", department);
        }
        return CustomResponse.generate(HttpStatus.BAD_REQUEST, "Data Tidak ditemukan", result);
        
    }

}

