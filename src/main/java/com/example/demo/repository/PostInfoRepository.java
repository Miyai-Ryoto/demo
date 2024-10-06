package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.PostInfo;
import com.example.demo.entity.UserInfo;

import java.util.List;
import com.example.demo.entity.PostsInfo;


@Repository
public interface PostInfoRepository extends JpaRepository<PostInfo, Long> {

    List<PostInfo> findByUserInfo(UserInfo userInfo);

    List<PostInfo> findByPostsInfos(PostsInfo postsInfo);

}
