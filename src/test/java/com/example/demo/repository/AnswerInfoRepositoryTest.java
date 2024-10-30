package com.example.demo.repository;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import com.example.demo.entity.AnswerInfo;
import com.example.demo.entity.RequestInfo;

import java.util.List;

@DataJpaTest
@ActiveProfiles("test")
public class AnswerInfoRepositoryTest {

    @Autowired
    private AnswerInfoRepository answerInfoRepository;

    @Autowired
    private RequestInfoRepository requestInfoRepository; // RequestInfoリポジトリの追加

    private RequestInfo requestInfo;

    @BeforeEach
    public void setUp() {
        // テスト用の RequestInfo を作成
        requestInfo = new RequestInfo();
        requestInfo.setId(1L); // IDを適宜設定
        // 必要なプロパティを設定（例: requestInfo.setName("Test");）
        requestInfoRepository.save(requestInfo); // RequestInfoを保存
    }

    @Test
    public void testFindByRequestInfo() {
        // テストデータのセットアップ
        AnswerInfo answer1 = new AnswerInfo();
        answer1.setRequestInfo(requestInfo);
        answerInfoRepository.save(answer1);

        AnswerInfo answer2 = new AnswerInfo();
        answer2.setRequestInfo(requestInfo);
        answerInfoRepository.save(answer2);

        // 検索
        List<AnswerInfo> results = answerInfoRepository.findByRequestInfo(requestInfo);

        // アサーション
        assertThat(results).hasSize(2);
        assertThat(results).contains(answer1, answer2);
    }
}
