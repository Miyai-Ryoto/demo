package com.example.demo.repository;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import com.example.demo.entity.DepartmentInfo;
import com.example.demo.entity.UserInfo;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@ActiveProfiles("test")
public class UserInfoRepositoryTest {

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    private DepartmentInfoRepository departmentInfoRepository;

    private DepartmentInfo department;
    private UserInfo user1;
    private UserInfo user2;

    @BeforeEach
    public void setUp() {
        // テスト用のDepartmentInfoを作成
        department = new DepartmentInfo();
        department.setDepartmentName("Engineering");
        departmentInfoRepository.save(department);

        // テスト用のUserInfoを作成
        user1 = new UserInfo();
        user1.setLoginId("alice123");
        user1.setDepartmentInfo(department);
        userInfoRepository.save(user1);

        user2 = new UserInfo();
        user2.setLoginId("bob456");
        user2.setDepartmentInfo(department);
        userInfoRepository.save(user2);
    }

    @Test
    public void testFindByLoginId() {
        Optional<UserInfo> result = userInfoRepository.findByLoginId("alice123");

        // アサーション
        assertThat(result).isPresent(); // 結果が存在することを確認
        assertThat(result.get().getLoginId()).isEqualTo("alice123"); // 正しいユーザーが返されることを確認
    }

    @Test
    public void testFindByDepartmentInfo() {
        List<UserInfo> users = userInfoRepository.findByDepartmentInfo(department);

        // アサーション
        assertThat(users).hasSize(2); // 部署に所属するユーザーが2人いるはず
        assertThat(users)
                .extracting(UserInfo::getLoginId)
                .containsExactlyInAnyOrder("alice123", "bob456"); // 正しいユーザーが返されることを確認
    }
}
