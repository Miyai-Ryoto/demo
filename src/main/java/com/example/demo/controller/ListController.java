package com.example.demo.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.demo.constant.UrlConst;
import com.example.demo.form.AnswerForm;
import com.example.demo.service.ListService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ListController {

    private final ListService listService;

    @GetMapping(UrlConst.LIST)
    public String view(Model model, @AuthenticationPrincipal User user) {
        model.addAttribute("postsList", listService.getPostListByDepartmentId(user));
        return "list";
    }

    @GetMapping(UrlConst.REQUEST)
    public String requestView(Model model, @AuthenticationPrincipal User user) {
        model.addAttribute("requestsList", listService.getPostListByUserId(user));
        return "request";
    }

    @GetMapping(UrlConst.DETAIL)
    public String postView(Model model, @PathVariable Long id, @AuthenticationPrincipal User user, AnswerForm form) {
        model.addAttribute("post", listService.getPostById(id));
        model.addAttribute("userLists", listService.getUserListByDepartmentId(user));
        return "postDetail";
    }

}
