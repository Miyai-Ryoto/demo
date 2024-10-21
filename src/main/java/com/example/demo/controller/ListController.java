package com.example.demo.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.demo.constant.PostCondition;
import com.example.demo.constant.UrlConst;
import com.example.demo.form.AnswerForm;
import com.example.demo.form.SearchModel;
import com.example.demo.repository.DepartmentInfoRepository;
import com.example.demo.service.AnswerService;
import com.example.demo.service.ListService;

import lombok.RequiredArgsConstructor;



@Controller
@RequiredArgsConstructor
public class ListController {

    private final ListService listService;

    private final AnswerService answerService;

    private final DepartmentInfoRepository departmentInfoRepository;

    @GetMapping(UrlConst.LIST)
    public String view(Model model, @AuthenticationPrincipal User user,@PageableDefault(page = 0, size = 10) Pageable pageable) {
        model.addAttribute("postsList", listService.getPostListByDepartmnetId(user, pageable));
        model.addAttribute("target", new SearchModel());
        model.addAttribute("departments", departmentInfoRepository.findAll());
        model.addAttribute("conditions", PostCondition.values());
        return "list";
    }

    @GetMapping(UrlConst.SEARCH)
    public String searchView(Model model, @AuthenticationPrincipal User user,@ModelAttribute("target") SearchModel target) {
        model.addAttribute("postsList", listService.searchPostList(user, target));
        return "list";
    }
    

    @GetMapping(UrlConst.REQUEST)
    public String requestView(Model model, @AuthenticationPrincipal User user) {
        model.addAttribute("requestsList", listService.getPostListByUserId(user));
        return "request";
    }

    @GetMapping(UrlConst.REQUESTDETAIL)
    public String requestDetail(Model model, @PathVariable Long id) {
        model.addAttribute("request", listService.getPostById(id));
        model.addAttribute("answerList", answerService.getAnswerListByPostId(id));
        return "requestDetail";
    }
    

    @GetMapping(UrlConst.DETAIL)
    public String postView(Model model, @PathVariable Long id, @AuthenticationPrincipal User user, AnswerForm form) {
        model.addAttribute("post", listService.getPostsById(id));
        model.addAttribute("userLists", listService.getUserListByDepartmentId(user));
        return "postDetail";
    }

}
