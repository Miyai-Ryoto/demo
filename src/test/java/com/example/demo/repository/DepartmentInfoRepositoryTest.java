package com.example.demo.repository;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.example.demo.entity.DepartmentInfo;
import com.example.demo.entity.UserInfo;

import java.util.List;

@DataJpaTest
public class DepartmentInfoRepositoryTest {

    @Autowired
    private DepartmentInfoRepository departmentInfoRepository;

    @Autowired
    private UserInfoRepository userInfoRepository; // UserInfoRepositoryを追加

    private DepartmentInfo department;

    @BeforeEach
    public void setUp() {
        // テスト用のDepartmentInfoを作成
        department = new DepartmentInfo();
        department.setDepartmentName("Engineering");
        departmentInfoRepository.save(department);

        // テスト用のUserInfoを作成
        UserInfo user1 = new UserInfo();
        user1.setLoginId("Alice");
        user1.setDepartmentInfo(department);
        userInfoRepository.save(user1); // UserInfoRepositoryを使用

        UserInfo user2 = new UserInfo();
        user2.setLoginId("Bob");
        user2.setDepartmentInfo(department);
        userInfoRepository.save(user2); // UserInfoRepositoryを使用
    }

    @Test
    public void testFindByDepartmentName() {
        // 検索
        List<DepartmentInfo> results = departmentInfoRepository.findByDepartmentName("Engineering");

        // アサーション
        assertThat(results).hasSize(1); // 部門は1つ
        assertThat(results.get(0).getDepartmentName()).isEqualTo("Engineering");
    }

    @Test
    public void testFindUsersByDepartment() {
        // DepartmentInfoからユーザーを検索
        List<UserInfo> users = userInfoRepository.findByDepartmentInfo(department);

        // アサーション
        assertThat(users).hasSize(2);
        assertThat(users).extracting(UserInfo::getLoginId).containsExactlyInAnyOrder("Alice", "Bob");
    }
}
