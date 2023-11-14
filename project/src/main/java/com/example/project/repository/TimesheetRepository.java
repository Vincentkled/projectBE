package com.example.project.repository;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.project.model.Timesheet;
@Repository
public interface TimesheetRepository extends JpaRepository<Timesheet, Integer>{
    @Query(value ="SELECT tb_m_user.email FROM tb_tr_timesheet JOIN tb_m_user ON tb_tr_timesheet.employee_id = tb_m_user.user_id WHERE ts_id =?1", nativeQuery = true)
    public String findEmail(Integer id);
}
