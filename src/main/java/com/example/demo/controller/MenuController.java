package com.example.demo.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.constant.UrlConst;
import com.example.demo.service.ListService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class MenuController {

    private final ListService listService;

    @GetMapping(UrlConst.MENU)
    public String view(Model model, @AuthenticationPrincipal User user) {
        model.addAttribute("unread", listService.getPostListByReadAndDepartment(user));
        return "menu";
    }
}
