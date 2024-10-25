package com.example.demo.form;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class AnswerForm {

    private String content;

    private MultipartFile file;

}
