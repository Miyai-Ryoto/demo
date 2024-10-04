package com.example.demo.service;

import java.util.List;

import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;


import com.example.demo.entity.PostInfo;
import com.example.demo.entity.UserInfo;
import com.example.demo.form.PostForm;
import com.example.demo.repository.PostInfoRepository;
import com.example.demo.repository.UserInfoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostInfoRepository postInfoRepository;

    private final UserInfoRepository userInfoRepository;

    public List<PostInfo> findAll() {
        return postInfoRepository.findAll();
    }

    public void resistPostInfo(PostForm form, User user) {
        PostInfo postInfo = new PostInfo();
        UserInfo userInfo = userInfoRepository.findByLoginId(user.getUsername()).orElse(null);
        postInfo.setTitle(form.getTitle());
        postInfo.setContent(form.getContent());
        postInfo.setEventDate(form.getEventDate());
        postInfo.setUserInfo(userInfo);
        postInfoRepository.save(postInfo);
    }

}
