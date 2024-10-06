package com.example.demo.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.demo.constant.UrlConst;
import com.example.demo.entity.DepartmentInfo;
import com.example.demo.entity.PostInfo;
import com.example.demo.entity.UserInfo;
import com.example.demo.form.AnswerForm;
import com.example.demo.repository.UserInfoRepository;
import com.example.demo.service.ListService;

import lombok.RequiredArgsConstructor;


@Controller
@RequiredArgsConstructor
public class ListController {

    private final ListService listService;

    private final UserInfoRepository userInfoRepository;

    @GetMapping(UrlConst.LIST)
    public String view(Model model, @AuthenticationPrincipal User user) {
        UserInfo userInfo = userInfoRepository.findByLoginId(user.getUsername()).orElse(null);
        DepartmentInfo departmentInfo = userInfo.getDepartmentInfo();
        model.addAttribute("postsList", listService.getPostsByDepartmentId(departmentInfo));
        return "list";
    }

    @GetMapping(UrlConst.REQUEST)
    public String requestView(Model model, @AuthenticationPrincipal User user) {
        UserInfo userInfo = userInfoRepository.findByLoginId(user.getUsername()).orElse(null);
        model.addAttribute("requestsList", listService.getPostaByUserId(userInfo));
        return "request";
    }

    @GetMapping("/list/{id}")
    public String postView(Model model, @PathVariable Long id, AnswerForm answerForm) {
        PostInfo postInfo = listService.getPostById(id);
        model.addAttribute("postInfo", postInfo);
        return "postDetail";
    }
    

}
