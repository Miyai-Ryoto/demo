package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.entity.PostInfo;
import com.example.demo.service.PostService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class PostController {

    private PostService postService;

    @GetMapping("/posts")
    public String showPosts(Model model) {
        model.addAttribute("posts", postService.findAll());
        return "posts"; // posts.html
    }

    @GetMapping("/posts/new")
    public String createPostForm(Model model) {
        model.addAttribute("post", new PostInfo());
        return "newPost"; // newPost.html
    }

    @PostMapping("/posts")
    public String createPost(@ModelAttribute PostInfo postInfo) {
        postService.save(postInfo);
        return "redirect:/posts";
    }

}
