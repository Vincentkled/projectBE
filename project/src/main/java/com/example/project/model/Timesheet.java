package com.example.project.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;



@Entity
@Table(name = "tb_tr_timesheet")
public class Timesheet {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ts_id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name="employee_id")
    private Employee employee;

    @ManyToOne
    @JoinColumn(name="date_id")
    private Dateentity dateentity;
    
    @DateTimeFormat(pattern ="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime start_time;

    @DateTimeFormat(pattern ="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime end_time;

    private String activity;
    private String attendance;
    private String status;
    public Timesheet(Integer id, Employee employee, Dateentity dateentity, LocalDateTime start_time,
            LocalDateTime end_time, String activity, String attendance, String status) {
        this.id = id;
        this.employee = employee;
        this.dateentity = dateentity;
        this.start_time = start_time;
        this.end_time = end_time;
        this.activity = activity;
        this.attendance = attendance;
        this.status = status;
    }
    public Timesheet(){

    }
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public Employee getEmployee() {
        return employee;
    }
    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
    public Dateentity getDateentity() {
        return dateentity;
    }
    public void setDateentity(Dateentity dateentity) {
        this.dateentity = dateentity;
    }
    public LocalDateTime getStart_time() {
        return start_time;
    }
    public void setStart_time(LocalDateTime start_time) {
        this.start_time = start_time;
    }
    public LocalDateTime getEnd_time() {
        return end_time;
    }
    public void setEnd_time(LocalDateTime end_time) {
        this.end_time = end_time;
    }
    public String getActivity() {
        return activity;
    }
    public void setActivity(String activity) {
        this.activity = activity;
    }
    public String getAttendance() {
        return attendance;
    }
    public void setAttendance(String attendance) {
        this.attendance = attendance;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

  
}
