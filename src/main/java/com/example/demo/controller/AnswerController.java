package com.example.demo.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;

import com.example.demo.form.AnswerForm;
import com.example.demo.service.AnswerService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;



@Controller
@RequiredArgsConstructor
public class AnswerController {

    private final AnswerService answerService;
    

    @PostMapping("list/{id}/answer")
    public String createAnswer(AnswerForm form, @AuthenticationPrincipal User user, @PathVariable Long id) {
        answerService.resistAnswerInfo(form, user, id);
        return "redirect:/list";
    }
    

}
