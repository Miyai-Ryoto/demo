package com.example.demo.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.RequestInfo;
import com.example.demo.entity.UserInfo;


@Repository
public interface RequestInfoRepository extends JpaRepository<RequestInfo, Long>, JpaSpecificationExecutor<RequestInfo> {

    Page<RequestInfo> findByUserInfoIn(List<UserInfo> userInfos, Pageable pageable);

}
