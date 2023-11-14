package com.example.project.model;

import java.time.LocalDate;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;



@Entity
@Table(name = "tb_tr_date")
public class Dateentity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "date_id")
    private Integer id;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "date")
    private LocalDate datetb;

    private String detail;
    private Boolean isholiday;

    @OneToMany(mappedBy = "dateentity")
    @JsonIgnore
    public Set<Timesheet> timesheets;

    public Dateentity(Integer id, LocalDate datetb, String detail, Boolean isholiday, Set<Timesheet> timesheets) {
        this.id = id;
        this.datetb = datetb;
        this.detail = detail;
        this.isholiday = isholiday;
        this.timesheets = timesheets;
    }
    public Dateentity(){
        
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDate getDatetb() {
        return datetb;
    }

    public void setDatetb(LocalDate datetb) {
        this.datetb = datetb;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public Boolean getIsholiday() {
        return isholiday;
    }

    public void setIsholiday(Boolean isholiday) {
        this.isholiday = isholiday;
    }

    public Set<Timesheet> getTimesheets() {
        return timesheets;
    }

    public void setTimesheets(Set<Timesheet> timesheets) {
        this.timesheets = timesheets;
    }

    
    
}
