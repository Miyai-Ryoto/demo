package com.example.demo.service;

import org.springframework.stereotype.Service;

import com.example.demo.entity.PostsInfo;
import com.example.demo.entity.UserInfo;
import com.example.demo.form.ResponsibleForm;
import com.example.demo.repository.PostsInfoRepository;
import com.example.demo.repository.UserInfoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ResponsibleService {

    private final UserInfoRepository userInfoRepository;

    private final PostsInfoRepository postsInfoRepository;

    public void resistResponsibleInfo(ResponsibleForm form, Long id){
        PostsInfo postsInfo = postsInfoRepository.findById(id).orElse(null);
        UserInfo userInfo = userInfoRepository.findById(form.getUserId()).orElse(null);
        postsInfo.setUserInfo(userInfo);
        postsInfoRepository.save(postsInfo);
    }

}
