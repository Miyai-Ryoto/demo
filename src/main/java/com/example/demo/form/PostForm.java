package com.example.demo.form;

import java.time.LocalDate;
import java.util.List;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PostForm {

    @NotBlank
    private String title;

    @NotBlank
    private String content;

    @NotNull
    @Future
    private LocalDate eventDate;

    @NotEmpty
    private List<Long> departmentId;

}
