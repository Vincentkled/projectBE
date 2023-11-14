package com.example.project.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.project.DTO.LoginRequest;
import com.example.project.DTO.Register;
import com.example.project.model.Employee;
import com.example.project.model.Role;
import com.example.project.model.User;
import com.example.project.repository.EmployeeRepository;
import com.example.project.repository.RoleRepository;
import com.example.project.repository.UserRepository;

@Controller
@RequestMapping("admin")
public class AdminController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/login")
    public String loginForm(Model model, LoginRequest loginDTO) {
        model.addAttribute("loginDTO", new LoginRequest());
        return "admin/login";
    }

    @PostMapping("/authenticate")
    public String login(Model model, LoginRequest loginDTO) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return "admin/loginsuccess";
    }

    @GetMapping("register")
    public String RegistrationForm(Model model) {
        model.addAttribute("adminRegisterDTO", new Register());
        model.addAttribute("roles", roleRepository.findAll());


        return "admin/register";
    }

    @PostMapping("save")
    public String register(Register adminRegisterDTO) {
        Role adminRole = new Role();
        adminRole.setId(1);


        Employee adminEmployee = new Employee();
        adminEmployee.setStatus("ACTIVE");
        adminEmployee.setName(adminRegisterDTO.getName());

        User adminUser = new User();
        adminUser.setRole(adminRole);
        adminUser.setEmail(adminRegisterDTO.getEmail());
        adminUser.setPassword(passwordEncoder.encode(adminRegisterDTO.getPassword()));

        // Simpan data user 
        employeeRepository.save(adminEmployee);
        adminUser.setId(adminEmployee.getId());
        userRepository.save(adminUser);

        return "admin/registersuccess";
    }

}


