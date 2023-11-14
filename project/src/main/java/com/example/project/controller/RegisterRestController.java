package com.example.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.project.DTO.Register;
import com.example.project.handler.CustomResponse;
import com.example.project.model.Department;
import com.example.project.model.Employee;
import com.example.project.model.Role;
import com.example.project.model.User;
import com.example.project.repository.DepartmentRepository;
import com.example.project.repository.EmployeeRepository;
import com.example.project.repository.UserRepository;

@RestController
@CrossOrigin
@RequestMapping
public class RegisterRestController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private DepartmentRepository departmentRepository;

    @PostMapping("register")
    public ResponseEntity<Object> RegisterUser(@RequestBody Register accountRegisterDTO) {
        try {
            Department department = departmentRepository.findById(accountRegisterDTO.getDepartment_id()).orElse(null);
        if (department == null) {
            return CustomResponse.generate(HttpStatus.BAD_REQUEST, "Pilih departemen");
        }

        Role role = new Role();
        role.setId(2); // Atur role sesuai dengan role pengguna biasa

        Employee employee = new Employee();
        employee.setDepartment(department);
        employee.setStatus("ACTIVE");
        employee.setName(accountRegisterDTO.getName());

        User user = new User();
        user.setRole(role);
        user.setEmail(accountRegisterDTO.getEmail());
        user.setPassword(passwordEncoder.encode(accountRegisterDTO.getPassword()));

        // Validasi bahwa manager_id telah dipilih
        if (accountRegisterDTO.getManager_id() == null) {
            // Jika manager_id tidak dipilih, kirim tanggapan bahwa pemilihan manajer diperlukan
            return CustomResponse.generate(HttpStatus.BAD_REQUEST, "Pilih seorang manajer");
        }

        // Set manager_id berdasarkan input dari formulir
        Employee manager = employeeRepository.findById(accountRegisterDTO.getManager_id()).orElse(null);
        if (manager != null) {
            employee.setManager(manager);
        }

        // Simpan data user dan employee
        employeeRepository.save(employee);
        user.setId(employee.getId());
        userRepository.save(user);

            return CustomResponse.generate(HttpStatus.OK, "Registration successful");
        } catch (Exception e) {
            return CustomResponse.generate(HttpStatus.BAD_REQUEST, "Registration failed");
        }
    }
}
