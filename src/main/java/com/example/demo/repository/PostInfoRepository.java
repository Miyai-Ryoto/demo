package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.PostInfo;

public interface PostInfoRepository extends JpaRepository<PostInfo, Long> {

}
