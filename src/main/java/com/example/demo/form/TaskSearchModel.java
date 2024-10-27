package com.example.demo.form;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import com.example.demo.constant.TaskCondition;

import lombok.Data;

@Data
public class TaskSearchModel {

    private String title;

    private TaskCondition condition;

    private Long departmentId;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate eventDate;

}
