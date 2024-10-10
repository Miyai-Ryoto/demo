package com.example.demo.service;

import java.util.List;

import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import com.example.demo.entity.DepartmentInfo;
import com.example.demo.entity.PostInfo;
import com.example.demo.entity.PostsInfo;
import com.example.demo.entity.UserInfo;
import com.example.demo.repository.PostInfoRepository;
import com.example.demo.repository.PostsInfoRepository;
import com.example.demo.repository.UserInfoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ListService {

    private final PostInfoRepository postInfoRepository;

    private final PostsInfoRepository postsInfoRepository;

    private final UserInfoRepository userInfoRepository;

    public List<PostsInfo> getPostListByDepartmentId(User user) {
        UserInfo userInfo = userInfoRepository.findByLoginId(user.getUsername()).orElse(null);
        DepartmentInfo departmentInfo = userInfo.getDepartmentInfo();
        return postsInfoRepository.findByDepartmentInfo(departmentInfo);
    }

    public List<PostInfo> getPostListByUserId(User user){
        UserInfo userInfo = userInfoRepository.findByLoginId(user.getUsername()).orElse(null);
        return postInfoRepository.findByUserInfo(userInfo);
    }

    public PostsInfo getPostById(Long id){
        return postsInfoRepository.findById(id).orElse(null);
    }

    public List<UserInfo> getUserListByDepartmentId(User user){
        UserInfo userInfo = userInfoRepository.findByLoginId(user.getUsername()).orElse(null);
        DepartmentInfo departmentInfo = userInfo.getDepartmentInfo();
        return userInfoRepository.findByDepartmentInfo(departmentInfo);
    }

}
