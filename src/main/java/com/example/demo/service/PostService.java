package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.entity.PostInfo;
import com.example.demo.repository.PostInfoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PostService {

    private PostInfoRepository postInfoRepository;

    public List<PostInfo> findAll() {
        return postInfoRepository.findAll();
    }

    public void save(PostInfo postInfo) {
        postInfoRepository.save(postInfo);
    }

}
