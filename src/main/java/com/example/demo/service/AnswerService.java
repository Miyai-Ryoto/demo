package com.example.demo.service;

import java.util.List;

import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import com.example.demo.entity.AnswerInfo;
import com.example.demo.entity.PostInfo;
import com.example.demo.entity.PostsInfo;
import com.example.demo.entity.UserInfo;
import com.example.demo.form.AnswerForm;
import com.example.demo.repository.AnswerInfoRepository;
import com.example.demo.repository.PostInfoRepository;
import com.example.demo.repository.PostsInfoRepository;
import com.example.demo.repository.UserInfoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AnswerService {

    private final AnswerInfoRepository answerInfoRepository;

    private final UserInfoRepository userInfoRepository;

    private final PostsInfoRepository postsInfoRepository;

    private final PostInfoRepository postInfoRepository;

    public void resistAnswerInfo(AnswerForm form, User user, Long id) {

        AnswerInfo answerInfo = new AnswerInfo();
        answerInfo.setContent(form.getContent());
        UserInfo userInfo = userInfoRepository.findByLoginId(user.getUsername()).orElse(null);
        answerInfo.setUserInfo(userInfo);
        PostsInfo postsInfo = postsInfoRepository.findById(id).orElse(null);
        answerInfo.setPostInfo(postsInfo.getPostInfo());
        answerInfoRepository.save(answerInfo);
        postsInfo.setCondition(true);
        postsInfoRepository.save(postsInfo);
    }

    public List<AnswerInfo> getAnswerListByPostId(Long id){
        PostInfo postInfo = postInfoRepository.findById(id).orElse(null);
        return answerInfoRepository.findByPostInfo(postInfo);
    }

}
