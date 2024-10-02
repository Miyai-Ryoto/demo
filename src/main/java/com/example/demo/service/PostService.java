package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.entity.PostInfo;
import com.example.demo.form.PostForm;
import com.example.demo.repository.PostInfoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostInfoRepository postInfoRepository;

    public List<PostInfo> findAll() {
        return postInfoRepository.findAll();
    }

    public void resistPostInfo(PostForm form) {
        PostInfo postInfo = new PostInfo();
        postInfo.setTitle(form.getTitle());
        postInfo.setContent(form.getContent());
        postInfo.setEventDate(form.getEventDate());
        postInfoRepository.save(postInfo);
    }

}
