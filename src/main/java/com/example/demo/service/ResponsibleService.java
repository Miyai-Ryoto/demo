package com.example.demo.service;

import org.springframework.stereotype.Service;

import com.example.demo.entity.TaskInfo;
import com.example.demo.entity.UserInfo;
import com.example.demo.form.ResponsibleForm;
import com.example.demo.repository.TaskInfoRepository;
import com.example.demo.repository.UserInfoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ResponsibleService {

    private final UserInfoRepository userInfoRepository;

    private final TaskInfoRepository taskInfoRepository;

    public void resistResponsibleInfo(ResponsibleForm form, Long id){
        TaskInfo taskInfo = taskInfoRepository.findById(id).orElse(null);
        UserInfo userInfo = userInfoRepository.findById(form.getUserId()).orElse(null);
        taskInfo.setUserInfo(userInfo);
        taskInfoRepository.save(taskInfo);
    }

}
