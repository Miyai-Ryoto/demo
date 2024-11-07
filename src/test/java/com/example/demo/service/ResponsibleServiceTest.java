package com.example.demo.service;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ActiveProfiles;

import com.example.demo.entity.TaskInfo;
import com.example.demo.entity.UserInfo;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.form.ResponsibleForm;
import com.example.demo.repository.TaskInfoRepository;
import com.example.demo.repository.UserInfoRepository;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Optional;


@ActiveProfiles("test")
class ResponsibleServiceTest {

    @Mock
    private UserInfoRepository userInfoRepository;

    @Mock
    private TaskInfoRepository taskInfoRepository;

    @InjectMocks
    private ResponsibleService responsibleService;

    public ResponsibleServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testResistResponsibleInfo_Success() {
        // モックの設定
        ResponsibleForm form = new ResponsibleForm();
        form.setUserId(1L);

        TaskInfo taskInfo = new TaskInfo();
        UserInfo userInfo = new UserInfo();

        // タスクとユーザーのモック設定
        when(taskInfoRepository.findById(1L)).thenReturn(Optional.of(taskInfo));
        when(userInfoRepository.findById(1L)).thenReturn(Optional.of(userInfo));

        // メソッドの実行
        responsibleService.resistResponsibleInfo(form, 1L);

        // 検証
        verify(taskInfoRepository).save(taskInfo);
        assertEquals(userInfo, taskInfo.getUserInfo());
    }

    @Test
    void testResistResponsibleInfo_TaskNotFound() {
        // モックの設定
        ResponsibleForm form = new ResponsibleForm();
        form.setUserId(1L);

        // タスクが見つからない場合の設定
        when(taskInfoRepository.findById(1L)).thenReturn(Optional.empty());

        // 例外がスローされることを確認
        assertThrows(ResourceNotFoundException.class, () -> {
            responsibleService.resistResponsibleInfo(form, 1L);
        });
    }

    @Test
    void testResistResponsibleInfo_UserNotFound() {
        // モックの設定
        ResponsibleForm form = new ResponsibleForm();
        form.setUserId(1L);

        TaskInfo taskInfo = new TaskInfo();

        // タスクは存在するがユーザーが見つからない場合の設定
        when(taskInfoRepository.findById(1L)).thenReturn(Optional.of(taskInfo));
        when(userInfoRepository.findById(1L)).thenReturn(Optional.empty());

        // 例外がスローされることを確認
        assertThrows(ResourceNotFoundException.class, () -> {
            responsibleService.resistResponsibleInfo(form, 1L);
        });
    }
}
