package com.example.project.controller;


import java.time.DayOfWeek;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.example.project.handler.CustomResponse;
import com.example.project.model.Dateentity;
import com.example.project.repository.DateentityRepository;




@RestController
@CrossOrigin
@RequestMapping("api")
public class DateRestController {
    @Autowired
    private DateentityRepository dateentityRepository;

    @GetMapping("dateentity")//localhostL8088/api/department
    public ResponseEntity<Object> get(){
        List<Dateentity> data = dateentityRepository.findAll();
        if(data.isEmpty()){
            return CustomResponse.generate(HttpStatus.OK, "Data tidak ditemukan", data);
        }
        return CustomResponse.generate(HttpStatus.OK, "Data ditemukan", data);
    }

    @PostMapping(value = {"dateentity", "dateentity/{id}"})
    public ResponseEntity<Object> save(@RequestBody Dateentity dateentity, @PathVariable(required = false) Integer id){

        if(dateentity.getId() ==null){ 
            dateentityRepository.save(dateentity);
            updateHoliday(dateentity);
            Boolean result = dateentityRepository.findById(dateentity.getId()).isPresent();
            if(result){
                return CustomResponse.generate(HttpStatus.OK, "Data Berhasil di Disimpan");
        } 
    }
    dateentityRepository.findById(dateentity.getId()).isPresent();
    dateentityRepository.save(dateentity);
        return CustomResponse.generate(HttpStatus.OK, "Data Berhasil di edit");
        }
        private void updateHoliday(Dateentity dateentity) {
         
                try {
                    String apiUrl = "https://api-harilibur.vercel.app/api?year=" + dateentity.getDatetb().getYear();
                    ResponseEntity<String> responseEntity = new RestTemplate().getForEntity(apiUrl, String.class);
                    boolean isholiday = responseEntity.getBody().contains(dateentity.getDatetb().toString());
                    DayOfWeek dayOfWeek = dateentity.getDatetb().getDayOfWeek();
                    boolean isWeekend = dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY;
            
                    dateentity.setIsholiday(isholiday || isWeekend);
                    dateentityRepository.save(dateentity);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            
        }
        
    @DeleteMapping("dateentity/{id}")
    public ResponseEntity<Object> delete(@PathVariable(required = true) Integer id){
        Boolean result = dateentityRepository.findById(id).isPresent();
        if (result){
            dateentityRepository.deleteById(id);
        return CustomResponse.generate(HttpStatus.OK, "Berhasil Menghapus Data");
        }
        return CustomResponse.generate(HttpStatus.BAD_REQUEST, "Data yang ingin diHapus tidak ditemukan");
    }

    
    @GetMapping("dateentity/{id}")
    public ResponseEntity<Object>get(@PathVariable(required = true) Integer id){
        Boolean result = dateentityRepository.findById(id).isPresent();
        if(result){
            Dateentity dateentity = dateentityRepository.findById(id).orElse(null);
            return CustomResponse.generate(HttpStatus.OK, "Data ditemukan", dateentity);
        }
        return CustomResponse.generate(HttpStatus.BAD_REQUEST, "Data Tidak ditemukan", result);
        
    }

}

