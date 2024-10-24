package com.example.demo.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.constant.PostCondition;
import com.example.demo.constant.UrlConst;
import com.example.demo.entity.PostInfo;
import com.example.demo.form.AnswerForm;
import com.example.demo.form.FindModel;
import com.example.demo.form.SearchModel;
import com.example.demo.repository.DepartmentInfoRepository;
import com.example.demo.repository.PostInfoRepository;
import com.example.demo.service.AnswerService;
import com.example.demo.service.ListService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ListController {

    private final ListService listService;

    private final AnswerService answerService;

    private final DepartmentInfoRepository departmentInfoRepository;

    private final PostInfoRepository postInfoRepository;

    @GetMapping(UrlConst.LIST)
    public String view(Model model, @AuthenticationPrincipal User user,
            @PageableDefault(page = 0, size = 10) Pageable pageable) {
        model.addAttribute("postsList", listService.getPostListByDepartmnetId(user, pageable));
        model.addAttribute("target", new SearchModel());
        model.addAttribute("departments", departmentInfoRepository.findAll());
        model.addAttribute("conditions", PostCondition.values());
        return "list";
    }

    @GetMapping(UrlConst.SEARCH)
    public String searchView(Model model, @AuthenticationPrincipal User user,
            @ModelAttribute("target") SearchModel target, @PageableDefault(page = 0, size = 10) Pageable pageable) {
        model.addAttribute("postsList", listService.searchPostList(user, target, pageable));
        return "list";
    }

    @GetMapping(UrlConst.REQUEST)
    public String requestView(Model model, @AuthenticationPrincipal User user,
            @PageableDefault(page = 0, size = 10) Pageable pageable) {
        model.addAttribute("requestsList", listService.getPostListByUserId(user, pageable));
        model.addAttribute("target", new FindModel());
        return "request";
    }

    @GetMapping(UrlConst.REQUESTSEARCH)
    public String requestsearchView(Model model, @AuthenticationPrincipal User user,
            @ModelAttribute("target") FindModel target, @PageableDefault(page = 0, size = 10) Pageable pageable) {
        model.addAttribute("requestsList", listService.searchRequestList(user, target, pageable));
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

    @GetMapping("/file/{id}")
    @ResponseBody
    public ResponseEntity<byte[]> serveFile(@PathVariable Long id) {
        PostInfo postInfo = postInfoRepository.findById(id).orElse(null);
        if (postInfo != null && postInfo.getFileData() != null) {
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + postInfo.getFileName() + "\"")
                    .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.wordprocessingml.document")) 
                    .body(postInfo.getFileData());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
