package com.example.project.DTO;


public class Register {
    private String name;
    private String email;
    private String password;
    private String status;
    private Integer role_id;
    private Integer department_id;
    private Integer manager_id;
    public Register(String name, String email, String password, String status, Integer role_id, Integer department_id,
            Integer manager_id) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.status = status;
        this.role_id = role_id;
        this.department_id = department_id;
        this.manager_id = manager_id;
    }
    public Register(){
        
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public Integer getRole_id() {
        return role_id;
    }
    public void setRole_id(Integer role_id) {
        this.role_id = role_id;
    }
    public Integer getDepartment_id() {
        return department_id;
    }
    public void setDepartment_id(Integer department_id) {
        this.department_id = department_id;
    }
    public Integer getManager_id() {
        return manager_id;
    }
    public void setManager_id(Integer manager_id) {
        this.manager_id = manager_id;
    }
    
}