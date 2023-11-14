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
import com.example.project.model.Role;
import com.example.project.repository.RoleRepository;


@CrossOrigin
@RestController
@RequestMapping("api")
public class RoleRestController {
    @Autowired
    private RoleRepository roleRepository;

    @GetMapping("role")//localhostL8088/api/role
    public ResponseEntity<Object> get(){
        List<Role> data = roleRepository.findAll();
        if(data.isEmpty()){
            return CustomResponse.generate(HttpStatus.OK, "Data tidak ditemukan", data);
        }
        return CustomResponse.generate(HttpStatus.OK, "Data ditemukan", data);
    }

    @PostMapping(value = {"role", "role/{id}"})
    public ResponseEntity<Object> save(@RequestBody Role role, @PathVariable(required = false) Integer id){

        if(role.getId() ==null){ 
            roleRepository.save(role);
            Boolean result = roleRepository.findById(role.getId()).isPresent();
            if(result){
                return CustomResponse.generate(HttpStatus.OK, "Data Berhasil di Disimpan");
        } 
    }
        roleRepository.findById(role.getId()).isPresent();
        roleRepository.save(role);
        return CustomResponse.generate(HttpStatus.OK, "Data Berhasil di edit");
        }
    
    @DeleteMapping("role/{id}")
    public ResponseEntity<Object> delete(@PathVariable(required = true) Integer id){
        Boolean result = roleRepository.findById(id).isPresent();
        if (result){
            roleRepository.deleteById(id);
        return CustomResponse.generate(HttpStatus.OK, "Berhasil Menghapus Data");
        }
        return CustomResponse.generate(HttpStatus.BAD_REQUEST, "Data yang ingin diHapus tidak ditemukan");
    }

    
    @GetMapping("role/{id}")
    public ResponseEntity<Object>get(@PathVariable(required = true) Integer id){
        Boolean result = roleRepository.findById(id).isPresent();
        if(result){
            Role role = roleRepository.findById(id).orElse(null);
            return CustomResponse.generate(HttpStatus.OK, "Data ditemukan", role);
        }
        return CustomResponse.generate(HttpStatus.BAD_REQUEST, "Data Tidak ditemukan", result);
        
    }

}

