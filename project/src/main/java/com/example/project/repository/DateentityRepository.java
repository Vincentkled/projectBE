package com.example.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.project.model.Dateentity;

public interface DateentityRepository extends JpaRepository<Dateentity, Integer>{
    
}
