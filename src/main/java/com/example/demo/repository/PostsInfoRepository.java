package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.DepartmentInfo;
import com.example.demo.entity.PostsInfo;

@Repository
public interface PostsInfoRepository extends JpaRepository<PostsInfo, Long>, JpaSpecificationExecutor<PostsInfo>{

    List<PostsInfo> findByDepartmentInfo(DepartmentInfo departmentInfo);

}
