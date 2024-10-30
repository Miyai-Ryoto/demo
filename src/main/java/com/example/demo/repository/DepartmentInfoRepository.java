package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.DepartmentInfo;


@Repository
public interface DepartmentInfoRepository extends JpaRepository<DepartmentInfo, Long> {

    List<DepartmentInfo> findByDepartmentName(String departmentName);

}
