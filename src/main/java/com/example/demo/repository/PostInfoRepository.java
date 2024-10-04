package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.DepartmentInfo;
import com.example.demo.entity.PostInfo;
import com.example.demo.entity.UserInfo;

import java.util.List;


public interface PostInfoRepository extends JpaRepository<PostInfo, Long> {

    List<PostInfo> findByDepartmentInfo(DepartmentInfo departmentInfo);

    List<PostInfo> findByUserInfo(UserInfo userInfo);

}
