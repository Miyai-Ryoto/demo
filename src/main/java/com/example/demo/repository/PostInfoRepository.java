package com.example.demo.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.PostInfo;
import com.example.demo.entity.UserInfo;


@Repository
public interface PostInfoRepository extends JpaRepository<PostInfo, Long>, JpaSpecificationExecutor<PostInfo> {

    Page<PostInfo> findByUserInfo(UserInfo userInfo, Pageable pageable);

}
