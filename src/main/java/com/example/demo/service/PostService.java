package com.example.demo.service;

import java.util.List;

import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entity.DepartmentInfo;
import com.example.demo.entity.RequestInfo;
import com.example.demo.entity.TaskInfo;
import com.example.demo.entity.UserInfo;
import com.example.demo.form.PostForm;
import com.example.demo.repository.DepartmentInfoRepository;
import com.example.demo.repository.RequestInfoRepository;
import com.example.demo.repository.TaskInfoRepository;
import com.example.demo.repository.UserInfoRepository;

import lombok.RequiredArgsConstructor;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class PostService {

    private final RequestInfoRepository requestInfoRepository;

    private final TaskInfoRepository taskInfoRepository;

    private final UserInfoRepository userInfoRepository;

    private final DepartmentInfoRepository departmentInfoRepository;

    @Transactional
    public void resistPostInfo(PostForm form, User user) throws IOException {
        RequestInfo postInfo = new RequestInfo();
        postInfo.setTitle(form.getTitle());
        postInfo.setContent(form.getContent());
        postInfo.setEventDate(form.getEventDate());
        postInfo.setFileName(form.getFile().getOriginalFilename());
        postInfo.setFileData(form.getFile().getBytes());
        UserInfo userInfo = userInfoRepository.findByLoginId(user.getUsername()).orElse(null);
        postInfo.setUserInfo(userInfo);
        RequestInfo saveInfo = requestInfoRepository.save(postInfo);

        List<Long> departmentId = form.getDepartmentId();
        for (Long departmentIds : departmentId) {
            TaskInfo taskInfo = new TaskInfo();
            DepartmentInfo departmentInfo = departmentInfoRepository.findById(departmentIds).orElse(null);
            taskInfo.setDepartmentInfo(departmentInfo);
            taskInfo.setRequestInfo(saveInfo);
            taskInfoRepository.save(taskInfo);
        }
    }

}
