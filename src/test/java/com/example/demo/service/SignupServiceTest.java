package com.example.demo.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import com.example.demo.entity.DepartmentInfo;
import com.example.demo.entity.UserInfo;
import com.example.demo.form.SignupForm;
import com.example.demo.repository.DepartmentInfoRepository;
import com.example.demo.repository.UserInfoRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ActiveProfiles("test")
class SignupServiceTest {

    @Mock
    private UserInfoRepository userInfoRepository;

    @Mock
    private DepartmentInfoRepository departmentInfoRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private SignupService signupService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testResistUserInfo_Success() {
        // モックの設定
        SignupForm form = new SignupForm();
        form.setLoginId("newUser");
        form.setPassword("password123");
        form.setDepartmentId(1L);

        // ユーザーが存在しない場合
        when(userInfoRepository.findByLoginId("newUser")).thenReturn(Optional.empty());
        when(departmentInfoRepository.findById(1L)).thenReturn(Optional.of(new DepartmentInfo()));
        when(passwordEncoder.encode("password123")).thenReturn("encodedPassword");

        // 保存するUserInfoを作成
        UserInfo savedUserInfo = new UserInfo();
        savedUserInfo.setLoginId("newUser");
        savedUserInfo.setPassword("encodedPassword");
        savedUserInfo.setDepartmentInfo(new DepartmentInfo());

        when(userInfoRepository.save(any(UserInfo.class))).thenReturn(savedUserInfo);

        // メソッドの実行
        Optional<UserInfo> result = signupService.resistUserInfo(form);

        // 結果の検証
        assertTrue(result.isPresent());
        assertNotNull(result.get());
        assertEquals("newUser", result.get().getLoginId());
        assertEquals("encodedPassword", result.get().getPassword());
        assertNotNull(result.get().getDepartmentInfo());
        verify(userInfoRepository).save(any(UserInfo.class));
    }

    @Test
    void testResistUserInfo_UserAlreadyExists() {
        // モックの設定
        SignupForm form = new SignupForm();
        form.setLoginId("existingUser");

        // ユーザーが既に存在する場合
        when(userInfoRepository.findByLoginId("existingUser")).thenReturn(Optional.of(new UserInfo()));

        // メソッドの実行
        Optional<UserInfo> result = signupService.resistUserInfo(form);

        // 結果の検証
        assertFalse(result.isPresent());
        verify(userInfoRepository, never()).save(any(UserInfo.class));
    }
}
