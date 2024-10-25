package com.example.demo.controller;

import java.io.IOException;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;

import com.example.demo.constant.MessageConst;
import com.example.demo.constant.UrlConst;
import com.example.demo.form.AnswerForm;
import com.example.demo.service.AnswerService;
import com.example.demo.util.AppUtil;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class AnswerController {

    private final AnswerService answerService;

    @PostMapping(UrlConst.ANSWER)
    public String createAnswer(AnswerForm form, @AuthenticationPrincipal User user, @PathVariable Long id) {

        try {
            answerService.resistAnswerInfo(form, user, id);
            return "redirect:/list";
        } catch (IOException e) {
            // var message = AppUtil.getMessage(messageSource, MessageConst.IO_ERROR);
            // model.addAttribute("message", message);
            // model.addAttribute("departments", departmentInfoRepository.findAll());
            return "menu";
        }

    }

}
