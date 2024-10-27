package com.example.demo.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.DepartmentInfo;
import com.example.demo.entity.TaskInfo;

@Repository
public interface TaskInfoRepository extends JpaRepository<TaskInfo, Long>, JpaSpecificationExecutor<TaskInfo>{

    Page<TaskInfo> findByDepartmentInfo(DepartmentInfo departmentInfo, Pageable pageable);
}
