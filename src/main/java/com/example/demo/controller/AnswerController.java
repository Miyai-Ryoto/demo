package com.example.demo.controller;

import java.io.IOException;

import org.springframework.context.MessageSource;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import com.example.demo.constant.MessageConst;
import com.example.demo.constant.UrlConst;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.form.AnswerForm;
import com.example.demo.service.AnswerService;
import com.example.demo.service.ListService;
import com.example.demo.util.AppUtil;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class AnswerController {

    private final AnswerService answerService;

    private final ListService listService;

    private final MessageSource messageSource;

    @PostMapping(UrlConst.ANSWER)
    public String createAnswer(AnswerForm form, @AuthenticationPrincipal User user, @PathVariable Long id,
            Model model) {

        try {
            answerService.resistAnswerInfo(form, user, id);
            return "redirect:/tasklist";
        } catch (ResourceNotFoundException ex) {
            model.addAttribute("message", ex.getMessage());
            model.addAttribute("task", listService.getTaskById(id));
            model.addAttribute("userList", listService.getUserListByDepartmentId(user));
            return "taskdetail";
        } catch (IOException ex) {
            var message = AppUtil.getMessage(messageSource, MessageConst.IO_ERROR);
            model.addAttribute("message", message);
            model.addAttribute("task", listService.getTaskById(id));
            model.addAttribute("userList", listService.getUserListByDepartmentId(user));
            return "taskdetail";
        }

    }

}
