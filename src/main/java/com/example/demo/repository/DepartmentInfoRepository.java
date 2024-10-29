package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.DepartmentInfo;
import com.example.demo.entity.UserInfo;


@Repository
public interface DepartmentInfoRepository extends JpaRepository<DepartmentInfo, Long> {

    List<UserInfo> findByDepartmentName(String departmentName);

}
