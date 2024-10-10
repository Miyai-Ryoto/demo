package com.example.demo.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import com.example.demo.entity.AnswerInfo;
import com.example.demo.entity.PostsInfo;
import com.example.demo.entity.UserInfo;
import com.example.demo.form.AnswerForm;
import com.example.demo.repository.AnswerInfoRepository;
import com.example.demo.repository.PostsInfoRepository;
import com.example.demo.repository.UserInfoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AnswerService {

    private final AnswerInfoRepository answerInfoRepository;

    private final UserInfoRepository userInfoRepository;

    private final PostsInfoRepository postsInfoRepository;

    public void resistAnswerInfo(AnswerForm form, User user, Long id) {

        AnswerInfo answerInfo = new AnswerInfo();
        answerInfo.setContent(form.getContent());
        UserInfo userInfo = userInfoRepository.findByLoginId(user.getUsername()).orElse(null);
        answerInfo.setUserInfo(userInfo);
        PostsInfo postsInfo = postsInfoRepository.findById(id).orElse(null);
        answerInfo.setPostInfo(postsInfo.getPostInfo());
        answerInfoRepository.save(answerInfo);
    }

}
