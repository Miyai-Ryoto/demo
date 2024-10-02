package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.constant.UrlConst;
import com.example.demo.form.PostForm;
import com.example.demo.service.PostService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    // @GetMapping("/posts")
    // public String showPosts(Model model) {
    //     model.addAttribute("posts", postService.findAll());
    //     return "posts"; // posts.html
    // }

    @GetMapping(UrlConst.POST)
    public String view(Model model, PostForm form) {
        return "post";
    }

    @PostMapping(UrlConst.POST)
    public String createPost(PostForm form) {
        postService.resistPostInfo(form);
        return "redirect:/menu";
    }

}
