package com.example.project.DTO;

public class AccountRegisterDTO {
	private String email;
	private String password;
    private Integer manager_id;
    private Integer department_id;
    public AccountRegisterDTO(String email, String password, Integer manager_id, Integer department_id) {
        this.email = email;
        this.password = password;
        this.manager_id = manager_id;
        this.department_id = department_id;
    }
    public AccountRegisterDTO(){
        
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
    public Integer getManager_id() {
        return manager_id;
    }
    public void setManager_id(Integer manager_id) {
        this.manager_id = manager_id;
    }
    public Integer getDepartment_id() {
        return department_id;
    }
    public void setDepartment_id(Integer department_id) {
        this.department_id = department_id;
    }
}
