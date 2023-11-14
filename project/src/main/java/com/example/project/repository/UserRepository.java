package com.example.project.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import com.example.project.config.MyUserDetail;
import com.example.project.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

@Query("SELECT new com.example.project.config.MyUserDetail(u.email, u.password, e.name, e.id, r.name, e.manager.id) FROM User u JOIN u.employee e JOIN u.role r WHERE u.email = ?1")
public UserDetails login(String email);

@Query("SELECT new com.example.project.config.MyUserDetail(u.email, u.password, e.name, e.id, r.name, e.manager.id) FROM User u JOIN u.employee e JOIN u.role r WHERE u.email = ?1")
public MyUserDetail getData(String email);


}
