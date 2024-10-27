package com.example.demo.form;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class RequestSearchModel {

    private String title;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate eventDate;
    
}
