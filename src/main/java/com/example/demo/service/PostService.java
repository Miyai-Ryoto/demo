package com.example.demo.service;

import java.util.List;

import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entity.DepartmentInfo;
import com.example.demo.entity.RequestInfo;
import com.example.demo.entity.TaskInfo;
import com.example.demo.entity.UserInfo;
import com.example.demo.exception.ResourceNotFoundException;
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
        RequestInfo requestInfo = new RequestInfo();
        requestInfo.setTitle(form.getTitle());
        requestInfo.setContent(form.getContent());
        requestInfo.setEventDate(form.getEventDate());
        requestInfo.setFileName(form.getFile().getOriginalFilename());
        requestInfo.setFileData(form.getFile().getBytes());

        UserInfo userInfo = userInfoRepository.findByLoginId(user.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("user.not.found"));

        requestInfo.setUserInfo(userInfo);
        RequestInfo saveInfo = requestInfoRepository.save(requestInfo);

        List<Long> departmentId = form.getDepartmentId();
        for (Long departmentIds : departmentId) {
            DepartmentInfo departmentInfo = departmentInfoRepository.findById(departmentIds)
                    .orElseThrow(() -> new ResourceNotFoundException("department.not.found"));
                    
            TaskInfo taskInfo = new TaskInfo();
            taskInfo.setDepartmentInfo(departmentInfo);
            taskInfo.setRequestInfo(saveInfo);
            taskInfoRepository.save(taskInfo);
        }
    }

}
