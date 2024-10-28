package com.example.demo.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.constant.TaskCondition;
import com.example.demo.constant.UrlConst;
import com.example.demo.form.AnswerForm;
import com.example.demo.form.RequestSearchModel;
import com.example.demo.form.TaskSearchModel;
import com.example.demo.repository.DepartmentInfoRepository;
import com.example.demo.service.AnswerService;
import com.example.demo.service.FileService;
import com.example.demo.service.ListService;

import lombok.RequiredArgsConstructor;


@Controller
@RequiredArgsConstructor
public class ListController {

    private final ListService listService;

    private final AnswerService answerService;

    private final DepartmentInfoRepository departmentInfoRepository;

    private final FileService fileService;


    @GetMapping(UrlConst.TASKLIST)
    public String taskListView(Model model, @AuthenticationPrincipal User user,
            @PageableDefault(page = 0, size = 10) Pageable pageable) {
        model.addAttribute("taskList", listService.getTaskListByDepartmnetId(user, pageable));
        model.addAttribute("target", new TaskSearchModel());
        model.addAttribute("departmentList", departmentInfoRepository.findAll());
        model.addAttribute("conditions", TaskCondition.values());
        return "tasklist";
    }

    @GetMapping(UrlConst.TASKSEARCH)
    public String taskSearchView(Model model, @AuthenticationPrincipal User user,
            @ModelAttribute("target") TaskSearchModel target, @PageableDefault(page = 0, size = 10) Pageable pageable) {
        model.addAttribute("taskList", listService.searchTaskList(user, target, pageable));
        return "tasklist";
    }

    @GetMapping(UrlConst.TASKDETAIL)
    public String taskDetailView(Model model, @PathVariable Long id, @AuthenticationPrincipal User user, AnswerForm form) {
        model.addAttribute("task", listService.getTaskById(id));
        model.addAttribute("userList", listService.getUserListByDepartmentId(user));
        return "taskdetail";
    }

    @GetMapping(UrlConst.REQUESTLIST)
    public String requestListView(Model model, @AuthenticationPrincipal User user,
            @PageableDefault(page = 0, size = 10) Pageable pageable) {
        model.addAttribute("requestList", listService.getRequestListByUserId(user, pageable));
        model.addAttribute("target", new RequestSearchModel());
        return "requestlist";
    }

    @GetMapping(UrlConst.REQUESTSEARCH)
    public String requestSearchView(Model model, @AuthenticationPrincipal User user,
            @ModelAttribute("target") RequestSearchModel target, @PageableDefault(page = 0, size = 10) Pageable pageable) {
        model.addAttribute("requestList", listService.searchRequestList(user, target, pageable));
        return "requestlist";
    }

    @GetMapping(UrlConst.REQUESTDETAIL)
    public String requestDetailView(Model model, @PathVariable Long id) {
        model.addAttribute("request", listService.getRequestById(id));
        model.addAttribute("answerList", answerService.getAnswerListByPostId(id));
        return "requestdetail";
    }

    @GetMapping(UrlConst.ANSWERDETAIL)
    public String answerDetailView(Model model, @PathVariable Long id) {
        model.addAttribute("answer", answerService.getAnswerById(id));
        return "answerdetail";
    }

    @GetMapping(UrlConst.ANSWERFILE)
    @ResponseBody
    public ResponseEntity<byte[]> serveAnswerFile(@PathVariable Long id) {
        return fileService.serveFile(id, "answer");
    }

    @GetMapping(UrlConst.REQUESTFILE)
    @ResponseBody
    public ResponseEntity<byte[]> serveRequestFile(@PathVariable Long id) {
        return fileService.serveFile(id, "request");
    }

}
