package com.example.demo.form;

import java.time.LocalDate;

import lombok.Data;

@Data
public class PostForm {

    private String title;

    private String content;

    private LocalDate eventDate;

}
