package com.example.project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.project.DTO.LoginRequest;
import com.example.project.DTO.Register;
import com.example.project.model.Department;
import com.example.project.model.Employee;
import com.example.project.model.Role;
import com.example.project.model.User;
import com.example.project.repository.DepartmentRepository;
import com.example.project.repository.EmployeeRepository;
import com.example.project.repository.RoleRepository;
import com.example.project.repository.UserRepository;

@Controller
@CrossOrigin
@RequestMapping("account") // http://localhost:8089/account
public class AccountController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/login")
    public String ShowloginForm(Model model, LoginRequest loginRequest) {
        model.addAttribute("loginRequest", new LoginRequest());
        return "account/login";
    }

    @PostMapping("/authenticate")
    public String loginString(Model model, LoginRequest loginRequest) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return "account/loginsuccess";
    }

    @GetMapping("register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("accountRegisterDTO", new Register());
        model.addAttribute("roles", roleRepository.findAll());
        model.addAttribute("departments", departmentRepository.findAll());

        // Ambil daftar karyawan yang menjadi manajer (manager_id = null)
        List<Employee> managers = employeeRepository.findManagers();
        model.addAttribute("managers", managers);
        return "account/register";
    }

    @PostMapping("save")
    public String register(Register accountRegisterDTO) {

        Department department = departmentRepository.findById(accountRegisterDTO.getDepartment_id()).orElse(null);
        if (department == null) {
            return "redirect:/account/register";
        }
        Role role = new Role();
        role.setId(2);

        Employee employee = new Employee();
        employee.setDepartment(department);
        employee.setStatus("ACTIVE");
        employee.setName(accountRegisterDTO.getName());

        User user = new User();
        user.setRole(role);
        user.setEmail(accountRegisterDTO.getEmail());
        user.setPassword(passwordEncoder.encode(accountRegisterDTO.getPassword()));

        //Untuk mendaftarkan user biasa, pakai kode yang ini saja:
        // Set manager_id berdasarkan input dari form
        if (accountRegisterDTO.getManager_id() == null) {
            return "redirect:/account/register?error=managerRequired";
        } 
        // Jika manager dipilih, atur manager_id ke nilai yang sesuai
        Employee manager = employeeRepository.findById(accountRegisterDTO.getManager_id()).orElse(null);
        if (manager != null) {
            employee.setManager(manager);
        }

        //Untuk mendaftarkan Manager gunakan yang ini saja, yang kode untuk user biasa nonaktifkan dulu
        // Set manager_id berdasarkan input dari form
        // if (accountRegisterDTO.getManagerId() != null) {
        // // Jika manager dipilih, atur manager_id ke nilai yang sesuai
        //     Employee manager = employeeRepository.findById(accountRegisterDTO.getManagerId()).orElse(null);
        //         if (manager != null) {
        //             employee.setManager(manager);
        //         }
        // }


        // Simpan data user dan employee
        employeeRepository.save(employee);
        user.setId(employee.getId());
        userRepository.save(user);

        return "account/registersuccess";
    }

}
