package com.example.demo.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.constant.UrlConst;
import com.example.demo.form.PostForm;
import com.example.demo.repository.DepartmentInfoRepository;
import com.example.demo.service.PostService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    /** 部署レポジトリー */
    private final DepartmentInfoRepository departmentInfoRepository;

    @GetMapping(UrlConst.POST)
    public String view(Model model, PostForm form) {
        model.addAttribute("departments", departmentInfoRepository.findAll());
        return "post";
    }

    @PostMapping(UrlConst.POST)
    public String createPost(PostForm form, @AuthenticationPrincipal User user) {
        postService.resistPostInfo(form, user);
        return "redirect:/list";
    }

}
