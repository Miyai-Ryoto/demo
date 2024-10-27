package com.example.demo.repository;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.DepartmentInfo;
import com.example.demo.entity.PostsInfo;

@Repository
public interface PostsInfoRepository extends JpaRepository<PostsInfo, Long>, JpaSpecificationExecutor<PostsInfo>{

    Page<PostsInfo> findByDepartmentInfo(DepartmentInfo departmentInfo, Pageable pageable);

    List<PostsInfo> findByReadAndDepartmentInfo(boolean read, DepartmentInfo departmentInfo);

}
