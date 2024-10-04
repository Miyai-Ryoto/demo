package com.example.demo.service;


import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import com.example.demo.entity.DepartmentInfo;
import com.example.demo.entity.PostInfo;
import com.example.demo.entity.UserInfo;
import com.example.demo.form.PostForm;
import com.example.demo.repository.DepartmentInfoRepository;
import com.example.demo.repository.PostInfoRepository;
import com.example.demo.repository.UserInfoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostInfoRepository postInfoRepository;

    private final UserInfoRepository userInfoRepository;

    private final DepartmentInfoRepository departmentInfoRepository;


    public void resistPostInfo(PostForm form, User user) {
        for (Long departmentIds : form.getDepartmentId()){
            PostInfo postInfo = new PostInfo();
            postInfo.setTitle(form.getTitle());
            postInfo.setContent(form.getContent());
            postInfo.setEventDate(form.getEventDate());
            UserInfo userInfo = userInfoRepository.findByLoginId(user.getUsername()).orElse(null);
            postInfo.setUserInfo(userInfo);
            DepartmentInfo departmentInfo = departmentInfoRepository.findById(departmentIds).orElse(null);
            postInfo.setDepartmentInfo(departmentInfo);
            postInfoRepository.save(postInfo);
        }  
    }

}
