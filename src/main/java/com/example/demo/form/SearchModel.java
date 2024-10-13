package com.example.demo.form;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import com.example.demo.constant.PostCondition;

import lombok.Data;

@Data
public class SearchModel {

    private String title;

    private PostCondition condition;

    private Long departmentId;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate eventDate;

}
