package com.example.project.model;



import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "tb_m_employee")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employee_id")
    private Integer id;
    
    private String name;
    
    @ManyToOne
    @JoinColumn(name="department_id")
    private Department department;
    
    private String status;
    
    @OneToOne(mappedBy  = "employee")
    @JsonIgnore
    private User user;

    @OneToMany(mappedBy = "employee")
    @JsonIgnore
    public Set<Timesheet> timesheets;

    @ManyToOne
    @JoinColumn(name = "manager_id")
    private Employee manager;
    

    public Employee(Employee manager) {
        this.manager = manager;
    }
    public Employee(Integer id, String name, Department department, String status, User user,
            Set<Timesheet> timesheets) {
        this.id = id;
        this.name = name;
        this.department = department;
        this.status = status;
        this.user = user;
        this.timesheets = timesheets;
    }
    public Employee(){
        
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<Timesheet> getTimesheets() {
        return timesheets;
    }

    public void setTimesheets(Set<Timesheet> timesheets) {
        this.timesheets = timesheets;
    }
    public Employee getManager() {
        return manager;
    }
    public void setManager(Employee manager) {
        this.manager = manager;
    }

  
}
