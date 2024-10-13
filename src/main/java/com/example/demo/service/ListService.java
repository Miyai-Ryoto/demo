package com.example.demo.service;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import com.example.demo.entity.DepartmentInfo;
import com.example.demo.entity.PostInfo;
import com.example.demo.entity.PostsInfo;
import com.example.demo.entity.UserInfo;
import com.example.demo.form.SearchModel;
import com.example.demo.repository.PostInfoRepository;
import com.example.demo.repository.PostsInfoRepository;
import com.example.demo.repository.UserInfoRepository;
import com.example.demo.specification.PostsSpecifications;

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

    public List<PostsInfo> searchPostList(User user, SearchModel target) {
        UserInfo userInfo = userInfoRepository.findByLoginId(user.getUsername()).orElse(null);
        DepartmentInfo departmentInfo = userInfo.getDepartmentInfo();
        Long belongsDepartmentId = departmentInfo.getId();
        Specification<PostsInfo> spec = Specification
                .where(PostsSpecifications.belongsToDepartment(belongsDepartmentId))
                .and(PostsSpecifications.hasTitle(target.getTitle()))
                .and(PostsSpecifications.hasCondition(target.getCondition()))
                .and(PostsSpecifications.hasDepartment(target.getDepartmentId()))
                .and(PostsSpecifications.startDateGreaterThanEqual(target.getEventDate()));

        return postsInfoRepository.findAll(spec);
    }

    public List<PostInfo> getPostListByUserId(User user) {
        UserInfo userInfo = userInfoRepository.findByLoginId(user.getUsername()).orElse(null);
        return postInfoRepository.findByUserInfo(userInfo);
    }

    public PostsInfo getPostById(Long id) {
        return postsInfoRepository.findById(id).orElse(null);
    }

    public List<UserInfo> getUserListByDepartmentId(User user) {
        UserInfo userInfo = userInfoRepository.findByLoginId(user.getUsername()).orElse(null);
        DepartmentInfo departmentInfo = userInfo.getDepartmentInfo();
        return userInfoRepository.findByDepartmentInfo(departmentInfo);
    }

}
