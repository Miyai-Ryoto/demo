package com.example.demo.service;

import java.util.List;
import org.springframework.stereotype.Service;

import com.example.demo.entity.DepartmentInfo;
import com.example.demo.entity.PostInfo;
import com.example.demo.repository.PostInfoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ListService {

    private final PostInfoRepository postInfoRepository;

    public List<PostInfo> getPostsByDepartmentId(DepartmentInfo departmentInfo) {
        return postInfoRepository.findByDepartmentInfo(departmentInfo);
    }

}
