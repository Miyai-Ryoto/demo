package com.example.demo.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.constant.UrlConst;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.form.ResponsibleForm;
import com.example.demo.service.ListService;
import com.example.demo.service.ResponsibleService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ResponsibleController {

    private final ResponsibleService responsibleService;

    private final ListService listService;

    @PostMapping(UrlConst.RESPONSIBLE)
    public String createResponsible(Model model, @AuthenticationPrincipal User user, ResponsibleForm form, @PathVariable Long id) {
        try {
            responsibleService.resistResponsibleInfo(form, id);
            return "redirect:/tasklist";
        } catch (ResourceNotFoundException ex) {
            model.addAttribute("message", ex.getMessage());
            model.addAttribute("task", listService.getTaskById(id));
            model.addAttribute("userList", listService.getUserListByDepartmentId(user));
            return "taskdetail";
        }
    }

}
