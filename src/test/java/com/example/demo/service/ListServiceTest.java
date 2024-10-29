package com.example.demo.service;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.User;

import com.example.demo.entity.DepartmentInfo;
import com.example.demo.entity.RequestInfo;
import com.example.demo.entity.TaskInfo;
import com.example.demo.entity.UserInfo;
import com.example.demo.repository.RequestInfoRepository;
import com.example.demo.repository.TaskInfoRepository;
import com.example.demo.repository.UserInfoRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class ListServiceTest {

    @InjectMocks
    private ListService listService;

    @Mock
    private RequestInfoRepository requestInfoRepository;

    @Mock
    private TaskInfoRepository taskInfoRepository;

    @Mock
    private UserInfoRepository userInfoRepository;

    @Mock
    private User user;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetTaskById_TaskExists() {
        Long taskId = 1L;
        TaskInfo taskInfo = new TaskInfo();
        when(taskInfoRepository.findById(taskId)).thenReturn(Optional.of(taskInfo));

        TaskInfo result = listService.getTaskById(taskId);

        assertThat(result).isNotNull();
        assertThat(result).isSameAs(taskInfo);
    }

    @Test
    public void testGetTaskById_TaskNotFound() {
        Long taskId = 1L;
        when(taskInfoRepository.findById(taskId)).thenReturn(Optional.empty());

        TaskInfo result = listService.getTaskById(taskId);

        assertThat(result).isNull();
    }

    @Test
    public void testGetRequestById_RequestExists() {
        Long requestId = 1L;
        RequestInfo requestInfo = new RequestInfo();
        when(requestInfoRepository.findById(requestId)).thenReturn(Optional.of(requestInfo));

        RequestInfo result = listService.getRequestById(requestId);

        assertThat(result).isNotNull();
        assertThat(result).isSameAs(requestInfo);
    }

    @Test
    public void testGetRequestById_RequestNotFound() {
        Long requestId = 1L;
        when(requestInfoRepository.findById(requestId)).thenReturn(Optional.empty());

        RequestInfo result = listService.getRequestById(requestId);

        assertThat(result).isNull();
    }

    @Test
    public void testGetUserListByDepartmentId() {
        // モックの作成
        User user = mock(User.class); // Userをモック
        UserInfo userInfo = mock(UserInfo.class); // UserInfoをモック
        DepartmentInfo departmentInfo = mock(DepartmentInfo.class); // DepartmentInfoをモック

        // ユーザー名をモック
        when(user.getUsername()).thenReturn("testUser");

        // リポジトリのモック設定
        when(userInfoRepository.findByLoginId("testUser")).thenReturn(Optional.of(userInfo));
        when(userInfo.getDepartmentInfo()).thenReturn(departmentInfo);
        when(userInfoRepository.findByDepartmentInfo(departmentInfo)).thenReturn(List.of(userInfo));

        // メソッドの呼び出し
        List<UserInfo> result = listService.getUserListByDepartmentId(user);

        // 結果の検証
        assertThat(result).isNotEmpty();
        assertThat(result).contains(userInfo);
    }

    @Test
    public void testGetUserListByDepartmentId_NoUsers() {
        // モックの作成
        User user = mock(User.class); // Userをモック
        UserInfo userInfo = mock(UserInfo.class); // UserInfoをモック
        DepartmentInfo departmentInfo = mock(DepartmentInfo.class); // DepartmentInfoをモック

        // ユーザー名をモック
        when(user.getUsername()).thenReturn("testUser");

        // リポジトリのモック設定
        when(userInfoRepository.findByLoginId("testUser")).thenReturn(Optional.of(userInfo));
        when(userInfo.getDepartmentInfo()).thenReturn(departmentInfo);
        when(userInfoRepository.findByDepartmentInfo(departmentInfo)).thenReturn(Collections.emptyList());

        // メソッドの呼び出し
        List<UserInfo> result = listService.getUserListByDepartmentId(user);

        // 結果の検証
        assertThat(result).isEmpty();
    }

    // Add more tests for searchTaskList, searchRequestList, etc.
}
