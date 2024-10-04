package com.example.demo.form;

import java.time.LocalDate;
import java.util.List;

import lombok.Data;

@Data
public class PostForm {

    private String title;

    private String content;

    private LocalDate eventDate;

    private List<Long> departmentId;

}
