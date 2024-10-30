package com.example.demo.repository;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;

import com.example.demo.entity.RequestInfo;
import com.example.demo.entity.UserInfo;

import java.util.Arrays;
import java.util.List;

@DataJpaTest
@ActiveProfiles("test")
public class RequestInfoRepositoryTest {

    @Autowired
    private RequestInfoRepository requestInfoRepository;

    @Autowired
    private UserInfoRepository userInfoRepository;

    private UserInfo user1;
    private UserInfo user2;
    private RequestInfo request1;
    private RequestInfo request2;
    private RequestInfo request3; // user2のリクエスト

    @BeforeEach
    public void setUp() {
        // ユーザーの作成
        user1 = new UserInfo();
        user1.setLoginId("Alice");
        userInfoRepository.save(user1);

        user2 = new UserInfo();
        user2.setLoginId("Bob");
        userInfoRepository.save(user2);

        // リクエスト情報の作成
        request1 = new RequestInfo();
        request1.setUserInfo(user1);
        requestInfoRepository.save(request1);

        request2 = new RequestInfo();
        request2.setUserInfo(user1);
        requestInfoRepository.save(request2);

        // user2 のリクエストを追加
        request3 = new RequestInfo();
        request3.setUserInfo(user2);
        requestInfoRepository.save(request3);
    }

    @Test
    public void testFindByUserInfoIn() {
        List<UserInfo> userInfos = Arrays.asList(user1, user2);
        Page<RequestInfo> page = requestInfoRepository.findByUserInfoIn(userInfos, PageRequest.of(0, 10));

        // アサーション
        assertThat(page.getTotalElements()).isEqualTo(3); // 3件のリクエストがあるはず

        // user1のリクエストとuser2のリクエストが含まれるか確認
        assertThat(page.getContent()).contains(request1, request2, request3);
    }
}
