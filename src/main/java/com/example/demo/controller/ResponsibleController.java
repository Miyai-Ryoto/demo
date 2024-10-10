package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.constant.UrlConst;
import com.example.demo.form.ResponsibleForm;
import com.example.demo.service.ResponsibleService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ResponsibleController {

    private final ResponsibleService responsibleService;

    @PostMapping(UrlConst.RESPONSIBLE)
    public String createResponsible(ResponsibleForm form, @PathVariable Long id) {
        responsibleService.resistResponsibleInfo(form, id);
        return "redirect:/list";
    }

}
