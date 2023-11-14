package com.example.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.project.model.Department;
import com.example.project.repository.DepartmentRepository;

@Controller
@CrossOrigin
@RequestMapping("department")
public class DepartmentController {
    @Autowired
    private DepartmentRepository departmentRepository;

    @GetMapping String index(Model model){
        model.addAttribute("departments", departmentRepository.findAll());
        return "department/index";     
    }


    @GetMapping(value = {"form", "form/{id}"} )
    public String form(Model model, @PathVariable(required = false) Integer id){
        if (id != null){
            model.addAttribute("department", departmentRepository.findById(id));
            }
        else{
            model.addAttribute("department", new Department());
        }
        return "department/form";
            
        }
        @PostMapping("save")
        public String save(Department department){
            departmentRepository.save(department);
            Boolean result = departmentRepository.findById(department.getId()).isPresent();
            if(result){
                return "redirect:/department";
                }
                return "department/form";
        }
        @PostMapping("delete/{id}")
        public String delete(@PathVariable(required = true) Integer id){
            departmentRepository.deleteById(id);
            Boolean isDeleted = departmentRepository.findById(id).isEmpty();
            if(isDeleted){
                System.out.println("Data Telah Terhapus");
            return "redirect:/department";
            }else{
                System.out.println("Gagal Menghapus Data");
            }
            return "redirect:/department";
        }
}
