package com.example.demo.controller;

import org.springframework.context.MessageSource;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.constant.MessageConst;
import com.example.demo.constant.UrlConst;
import com.example.demo.form.PostForm;
import com.example.demo.repository.DepartmentInfoRepository;
import com.example.demo.service.PostService;
import com.example.demo.util.AppUtil;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class PostController {

    /** メッセージソース */
	private final MessageSource messageSource;

    private final PostService postService;

    /** 部署レポジトリー */
    private final DepartmentInfoRepository departmentInfoRepository;

    @GetMapping(UrlConst.POST)
    public String view(Model model, PostForm form) {
        model.addAttribute("departments", departmentInfoRepository.findAll());
        model.addAttribute("postForm", form); 
        return "post";
    }

    @PostMapping(UrlConst.POST)
    public String createPost(@Validated @ModelAttribute PostForm form, BindingResult bdResult, @AuthenticationPrincipal User user, Model model) {
        if(bdResult.hasErrors()){
            var message = AppUtil.getMessage(messageSource, MessageConst.FORM_ERROR);
            model.addAttribute("message", message);
            model.addAttribute("departments", departmentInfoRepository.findAll());
            return "post";
        }
        postService.resistPostInfo(form, user);
        return "redirect:/list";
    }

}
