package com.example.demo.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entity.TaskInfo;
import com.example.demo.entity.UserInfo;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.form.ResponsibleForm;
import com.example.demo.repository.TaskInfoRepository;
import com.example.demo.repository.UserInfoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ResponsibleService {

    private final UserInfoRepository userInfoRepository;

    private final TaskInfoRepository taskInfoRepository;

    @Transactional
    public void resistResponsibleInfo(ResponsibleForm form, Long id){
        TaskInfo taskInfo = taskInfoRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("task.not.found"));

        UserInfo userInfo = userInfoRepository.findById(form.getUserId())
        .orElseThrow(() -> new ResourceNotFoundException("user.not.found"));

        taskInfo.setUserInfo(userInfo);
        taskInfoRepository.save(taskInfo);
    }

}
