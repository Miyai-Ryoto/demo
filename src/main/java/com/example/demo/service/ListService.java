package com.example.demo.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    public Page<PostsInfo> getPostListByDepartmnetId(User user, Pageable pageable){
        UserInfo userInfo = userInfoRepository.findByLoginId(user.getUsername()).orElse(null);
        DepartmentInfo departmentInfo = userInfo.getDepartmentInfo();
        return postsInfoRepository.findByDepartmentInfo(departmentInfo, pageable);
    }

    public Page<PostsInfo> searchPostList(User user, SearchModel target, Pageable pageable) {
        UserInfo userInfo = userInfoRepository.findByLoginId(user.getUsername()).orElse(null);
        DepartmentInfo departmentInfo = userInfo.getDepartmentInfo();
        Long belongsDepartmentId = departmentInfo.getId();
        Specification<PostsInfo> spec = Specification
                .where(PostsSpecifications.belongsToDepartment(belongsDepartmentId))
                .and(PostsSpecifications.hasTitle(target.getTitle()))
                .and(PostsSpecifications.hasCondition(target.getCondition()))
                .and(PostsSpecifications.hasDepartment(target.getDepartmentId()))
                .and(PostsSpecifications.startDateGreaterThanEqual(target.getEventDate()));

        return postsInfoRepository.findAll(spec, pageable);
    }

    public List<PostInfo> getPostListByUserId(User user) {
        UserInfo userInfo = userInfoRepository.findByLoginId(user.getUsername()).orElse(null);
        return postInfoRepository.findByUserInfo(userInfo);
    }

    public PostInfo getPostById(Long id) {
        return postInfoRepository.findById(id).orElse(null);
    }

    public PostsInfo getPostsById(Long id) {
        return postsInfoRepository.findById(id).orElse(null);
    }

    public List<UserInfo> getUserListByDepartmentId(User user) {
        UserInfo userInfo = userInfoRepository.findByLoginId(user.getUsername()).orElse(null);
        DepartmentInfo departmentInfo = userInfo.getDepartmentInfo();
        return userInfoRepository.findByDepartmentInfo(departmentInfo);
    }

}
