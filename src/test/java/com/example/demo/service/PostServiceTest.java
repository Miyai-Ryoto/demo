package com.example.demo.service;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.entity.DepartmentInfo;
import com.example.demo.entity.RequestInfo;
import com.example.demo.entity.TaskInfo;
import com.example.demo.entity.UserInfo;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.form.PostForm;
import com.example.demo.repository.DepartmentInfoRepository;
import com.example.demo.repository.RequestInfoRepository;
import com.example.demo.repository.TaskInfoRepository;
import com.example.demo.repository.UserInfoRepository;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class PostServiceTest {

    @Mock
    private RequestInfoRepository requestInfoRepository;

    @Mock
    private TaskInfoRepository taskInfoRepository;

    @Mock
    private UserInfoRepository userInfoRepository;

    @Mock
    private DepartmentInfoRepository departmentInfoRepository;

    @InjectMocks
    private PostService postService;

    public PostServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testResistPostInfo_Success() throws IOException {
        // モックの設定
        User user = mock(User.class);
        when(user.getUsername()).thenReturn("testUser");

        MultipartFile mockFile = mock(MultipartFile.class);
        when(mockFile.getOriginalFilename()).thenReturn("test.txt");
        when(mockFile.getBytes()).thenReturn(new byte[0]);

        PostForm form = new PostForm();
        form.setTitle("Test Title");
        form.setContent("Test Content");
        form.setEventDate(LocalDate.now());
        form.setFile(mockFile);
        form.setDepartmentId(Collections.singletonList(1L));

        UserInfo userInfo = new UserInfo();
        when(userInfoRepository.findByLoginId("testUser")).thenReturn(Optional.of(userInfo));

        DepartmentInfo departmentInfo = new DepartmentInfo();
        when(departmentInfoRepository.findById(1L)).thenReturn(Optional.of(departmentInfo));

        // メソッドの実行
        postService.resistPostInfo(form, user);

        // 検証
        verify(requestInfoRepository).save(any(RequestInfo.class));
        verify(taskInfoRepository).save(any(TaskInfo.class));
    }

    @Test
    void testResistPostInfo_UserNotFound() throws IOException {
        // モックの設定
        User user = mock(User.class);
        when(user.getUsername()).thenReturn("testUser");

        MultipartFile mockFile = mock(MultipartFile.class);
        when(mockFile.getOriginalFilename()).thenReturn("test.txt");
        when(mockFile.getBytes()).thenReturn(new byte[0]);

        PostForm form = new PostForm();
        form.setTitle("Test Title");
        form.setContent("Test Content");
        form.setEventDate(LocalDate.now());
        form.setFile(mockFile);
        form.setDepartmentId(Collections.singletonList(1L));

        // ユーザーが見つからない場合の設定
        when(userInfoRepository.findByLoginId("testUser")).thenReturn(Optional.empty());

        // 例外がスローされることを確認
        assertThrows(ResourceNotFoundException.class, () -> {
            postService.resistPostInfo(form, user);
        });
    }

    @Test
    void testResistPostInfo_DepartmentNotFound() throws IOException {
        // モックの設定
        User user = mock(User.class);
        when(user.getUsername()).thenReturn("testUser");

        MultipartFile mockFile = mock(MultipartFile.class);
        when(mockFile.getOriginalFilename()).thenReturn("test.txt");
        when(mockFile.getBytes()).thenReturn(new byte[0]);

        PostForm form = new PostForm();
        form.setTitle("Test Title");
        form.setContent("Test Content");
        form.setEventDate(LocalDate.now());
        form.setFile(mockFile);
        form.setDepartmentId(Collections.singletonList(1L));

        UserInfo userInfo = new UserInfo();
        when(userInfoRepository.findByLoginId("testUser")).thenReturn(Optional.of(userInfo));
        when(departmentInfoRepository.findById(1L)).thenReturn(Optional.empty());

        // 例外がスローされることを確認
        assertThrows(ResourceNotFoundException.class, () -> {
            postService.resistPostInfo(form, user);
        });
    }
}
