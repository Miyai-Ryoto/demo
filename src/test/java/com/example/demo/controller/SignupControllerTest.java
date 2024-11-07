package com.example.demo.controller;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.MessageSource;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import com.example.demo.constant.MessageConst;
import com.example.demo.form.SignupForm;
import com.example.demo.repository.DepartmentInfoRepository;
import com.example.demo.service.SignupService;
import com.example.demo.util.AppUtil;
import com.example.demo.entity.UserInfo;


@ActiveProfiles("test")
public class SignupControllerTest {

    @InjectMocks
    private SignupController signupController;

    @Mock
    private SignupService signupService;

    @Mock
    private MessageSource messageSource;

    @Mock
    private DepartmentInfoRepository departmentInfoRepository;

    @Mock
    private Model model;

    @Mock
    private BindingResult bindingResult;

    private SignupForm signupForm;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        signupForm = new SignupForm(); // 必要に応じてフィールドを設定
    }

    @Test
    public void testSignupView() {
        // ユーザー登録画面の表示をテスト
        when(departmentInfoRepository.findAll()).thenReturn(new ArrayList<>());

        String viewName = signupController.signupView(model, signupForm);

        assertThat(viewName).isEqualTo("signup");
        verify(model).addAttribute(eq("departments"), anyList());
    }

    @Test
    public void testSignup_Success() {
        // ユーザー登録の正常系をテスト
        when(bindingResult.hasErrors()).thenReturn(false);
        when(signupService.resistUserInfo(signupForm)).thenReturn(Optional.of(new UserInfo())); // UserInfoを返す

        signupController.signup(model, signupForm, bindingResult);

        var message = AppUtil.getMessage(messageSource, MessageConst.SIGNUP_RESIST_SUCCEED);
        verify(model).addAttribute("message", message);
        verify(model).addAttribute("isError", false);
    }

    @Test
    public void testSignup_WithErrors() {
        // ユーザー登録の異常系をテスト（バインディングエラーがある場合）
        when(bindingResult.hasErrors()).thenReturn(true);

        signupController.signup(model, signupForm, bindingResult);

        var message = AppUtil.getMessage(messageSource, MessageConst.FORM_ERROR);
        verify(model).addAttribute("message", message);
        verify(model).addAttribute("isError", true);
    }

    @Test
    public void testSignup_ExistedLoginId() {
        // ユーザー登録の異常系（既に存在するログインID）
        when(bindingResult.hasErrors()).thenReturn(false);
        when(signupService.resistUserInfo(signupForm)).thenReturn(Optional.empty()); // 既存のユーザー

        signupController.signup(model, signupForm, bindingResult);

        var message = AppUtil.getMessage(messageSource, MessageConst.SIGNUP_EXISTED_LOGIN_ID);
        verify(model).addAttribute("message", message);
        verify(model).addAttribute("isError", true);
    }
}


