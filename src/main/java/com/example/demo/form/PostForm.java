package com.example.demo.form;

import java.time.LocalDate;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PostForm {

    @NotBlank
    @Size(min = 5, max = 40)
    private String title;

    @NotBlank
    @Size(min = 50, max = 1000)
    private String content;

    @NotNull
    @Future
    private LocalDate eventDate;

    private MultipartFile file;

    @NotEmpty
    private List<Long> departmentId;

}
