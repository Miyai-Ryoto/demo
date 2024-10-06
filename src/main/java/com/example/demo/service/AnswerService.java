package com.example.demo.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import com.example.demo.entity.AnswerInfo;
import com.example.demo.entity.PostInfo;
import com.example.demo.entity.UserInfo;
import com.example.demo.form.AnswerForm;
import com.example.demo.repository.AnwerInfoRepository;
import com.example.demo.repository.PostInfoRepository;
import com.example.demo.repository.UserInfoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AnswerService {

    private final AnwerInfoRepository anwerInfoRepository;

    private final UserInfoRepository userInfoRepository;

    private final PostInfoRepository postInfoRepository;

    public void resistAnswerInfo(AnswerForm form, User user, Long id){

        AnswerInfo answerInfo = new AnswerInfo();
        answerInfo.setContent(form.getContent());
        UserInfo userInfo = userInfoRepository.findByLoginId(user.getUsername()).orElse(null);
        answerInfo.setUserInfo(userInfo);
        PostInfo postInfo = postInfoRepository.findById(id).orElse(null);
        answerInfo.setPostInfo(postInfo);
        anwerInfoRepository.save(answerInfo);
    }

}
