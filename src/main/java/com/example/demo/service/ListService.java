package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

import com.example.demo.entity.DepartmentInfo;
import com.example.demo.entity.PostInfo;
import com.example.demo.entity.PostsInfo;
import com.example.demo.entity.UserInfo;
import com.example.demo.repository.PostInfoRepository;
import com.example.demo.repository.PostsInfoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ListService {

    private final PostInfoRepository postInfoRepository;

    private final PostsInfoRepository postsInfoRepository;

    public List<PostInfo> getPostsByDepartmentId(DepartmentInfo departmentInfo) {
        List<PostsInfo> postsInfo = postsInfoRepository.findByDepartmentInfo(departmentInfo);
        List<PostInfo> postInfos = new ArrayList<PostInfo>();
        for(PostsInfo postId : postsInfo){
            postInfos.add(postId.getPostInfo());
        }
        return postInfos;
    }


    public List<PostInfo> getPostaByUserId(UserInfo userInfo){
        return postInfoRepository.findByUserInfo(userInfo);
    }

    public PostInfo getPostById(Long id){
        return postInfoRepository.findById(id).orElse(null);
    }

}
