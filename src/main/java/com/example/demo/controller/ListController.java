package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.constant.UrlConst;
import com.example.demo.service.PostService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ListController {

    private final PostService postService;

    @GetMapping(UrlConst.LIST)
    public String postsList(Model model) {
        model.addAttribute("postsList", postService.findAll());
        return "list";
    }

}
