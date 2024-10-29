package com.example.demo.service;

import java.util.List;
import java.io.IOException;

import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import com.example.demo.entity.AnswerInfo;
import com.example.demo.entity.RequestInfo;
import com.example.demo.entity.TaskInfo;
import com.example.demo.entity.UserInfo;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.form.AnswerForm;
import com.example.demo.repository.AnswerInfoRepository;
import com.example.demo.repository.RequestInfoRepository;
import com.example.demo.repository.TaskInfoRepository;
import com.example.demo.repository.UserInfoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AnswerService {

    private final AnswerInfoRepository answerInfoRepository;

    private final UserInfoRepository userInfoRepository;

    private final TaskInfoRepository taskInfoRepository;

    private final RequestInfoRepository requestInfoRepository;

    public void resistAnswerInfo(AnswerForm form, User user, Long id) throws IOException {

        AnswerInfo answerInfo = new AnswerInfo();
        answerInfo.setContent(form.getContent());
        answerInfo.setFileName(form.getFile().getOriginalFilename());
        answerInfo.setFileData(form.getFile().getBytes());

        UserInfo userInfo = userInfoRepository.findByLoginId(user.getUsername())
        .orElseThrow(() -> new ResourceNotFoundException("user.not.found"));

        answerInfo.setUserInfo(userInfo);

        TaskInfo taskInfo = taskInfoRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("task.not.found"));

        answerInfo.setRequestInfo(taskInfo.getRequestInfo());

        answerInfoRepository.save(answerInfo);
        
        taskInfo.setCondition(true);
        taskInfoRepository.save(taskInfo);
    }

    public List<AnswerInfo> getAnswerListByPostId(Long id) {
        RequestInfo requestInfo = requestInfoRepository.findById(id).orElse(null);
        if (requestInfo == null) {
            return null;
        }
        return answerInfoRepository.findByRequestInfo(requestInfo);
    }

    public AnswerInfo getAnswerById(Long id) {
        return answerInfoRepository.findById(id).orElse(null);
    }

}
