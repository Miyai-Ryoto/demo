package com.example.demo.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.constant.UrlConst;
import com.example.demo.entity.DepartmentInfo;
import com.example.demo.entity.UserInfo;
import com.example.demo.repository.UserInfoRepository;
import com.example.demo.service.PostService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ListController {

    private final PostService postService;

    private final UserInfoRepository userInfoRepository;

    @GetMapping(UrlConst.LIST)
    public String view(Model model, @AuthenticationPrincipal User user) {
        UserInfo userInfo = userInfoRepository.findByLoginId(user.getUsername()).orElse(null);
        DepartmentInfo departmentInfo = userInfo.getDepartmentInfo();
        model.addAttribute("postsList", postService.getPostsByDepartmentId(departmentInfo));
        return "list";
    }

}
