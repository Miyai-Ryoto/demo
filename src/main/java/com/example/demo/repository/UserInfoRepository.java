package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.DepartmentInfo;
import com.example.demo.entity.UserInfo;
import java.util.List;


@Repository
public interface UserInfoRepository extends JpaRepository<UserInfo, Long>{

    Optional<UserInfo> findByLoginId(String loginId);

    List<UserInfo> findByDepartmentInfo(DepartmentInfo departmentInfo);
    
}
