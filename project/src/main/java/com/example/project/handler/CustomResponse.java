package com.example.project.handler;


import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class CustomResponse {
    //GET -> statuscode, message & data
    public static ResponseEntity<Object> generate(HttpStatus httpStatus, String message,Object data){
        Map<String, Object> map = new HashMap<>();
        map.put("statusCode", httpStatus);
        map.put("Message", message);
        map.put("results", data);
        return new ResponseEntity<Object>(map, httpStatus);
    }

    //POST -> statuscode & message
    public static ResponseEntity<Object> generate(HttpStatus httpStatus, String message){
        Map<String, Object> map = new HashMap<>();
        map.put("statusCode", httpStatus);
        map.put("Message", message);
        return new ResponseEntity<Object>(map, httpStatus);
    }

}
