package com.example.demo.service;


import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entity.DepartmentInfo;
import com.example.demo.entity.PostInfo;
import com.example.demo.entity.PostsInfo;
import com.example.demo.entity.UserInfo;
import com.example.demo.form.PostForm;
import com.example.demo.repository.DepartmentInfoRepository;
import com.example.demo.repository.PostInfoRepository;
import com.example.demo.repository.PostsInfoRepository;
import com.example.demo.repository.UserInfoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostInfoRepository postInfoRepository;

    private final PostsInfoRepository postsInfoRepository;

    private final UserInfoRepository userInfoRepository;

    private final DepartmentInfoRepository departmentInfoRepository;


    @Transactional
    public void resistPostInfo(PostForm form, User user) {

        PostInfo postInfo = new PostInfo();
         postInfo.setTitle(form.getTitle());
         postInfo.setContent(form.getContent());
         postInfo.setEventDate(form.getEventDate());
        UserInfo userInfo = userInfoRepository.findByLoginId(user.getUsername()).orElse(null);
         postInfo.setUserInfo(userInfo);
        PostInfo saveInfo = postInfoRepository.save(postInfo);


        for (Long departmentIds : form.getDepartmentId()){
            PostsInfo postsInfo = new PostsInfo();
            DepartmentInfo departmentInfo = departmentInfoRepository.findById(departmentIds).orElse(null);
            postsInfo.setDepartmentInfo(departmentInfo);
            postsInfo.setPostInfo(saveInfo);
            postsInfoRepository.save(postsInfo);
        }  
    }

}
