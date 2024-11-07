package com.example.demo.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.User;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.entity.AnswerInfo;
import com.example.demo.entity.RequestInfo;
import com.example.demo.entity.TaskInfo;
import com.example.demo.entity.UserInfo;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.form.AnswerForm;
import com.example.demo.repository.AnswerInfoRepository;
import com.example.demo.repository.RequestInfoRepository;
import com.example.demo.repository.TaskInfoRepository;
import com.example.demo.repository.UserInfoRepository;


@ActiveProfiles("test")
public class AnswerServiceTest {

    @InjectMocks
    private AnswerService answerService;

    @Mock
    private AnswerInfoRepository answerInfoRepository;

    @Mock
    private UserInfoRepository userInfoRepository;

    @Mock
    private TaskInfoRepository taskInfoRepository;

    @Mock
    private RequestInfoRepository requestInfoRepository;

    private AnswerForm answerForm;
    private User user;
    private Long taskId = 1L;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        answerForm = new AnswerForm();
        answerForm.setContent("Sample Answer");
        // モックされたファイルオブジェクトの設定を追加することも可能
        user = mock(User.class);
        when(user.getUsername()).thenReturn("testUser");
    }

    @Test
    public void testResistAnswerInfo_Success() throws IOException {
        // 正常系: AnswerInfoが正常に保存される場合
        UserInfo userInfo = new UserInfo();
        when(userInfoRepository.findByLoginId("testUser")).thenReturn(Optional.of(userInfo));

        TaskInfo taskInfo = new TaskInfo();
        taskInfo.setRequestInfo(new RequestInfo());
        when(taskInfoRepository.findById(taskId)).thenReturn(Optional.of(taskInfo));

        // ファイルのモックも必要に応じて設定
        answerForm.setFile(mock(MultipartFile.class));
        when(answerForm.getFile().getOriginalFilename()).thenReturn("sample.txt");
        when(answerForm.getFile().getBytes()).thenReturn(new byte[] {});

        answerService.resistAnswerInfo(answerForm, user, taskId);

        verify(answerInfoRepository).save(any(AnswerInfo.class));
        verify(taskInfoRepository).save(taskInfo);
        assertThat(taskInfo.isCondition());
    }

    @Test
    public void testResistAnswerInfo_UserNotFound() throws IOException {
        // Arrange
        MultipartFile mockFile = mock(MultipartFile.class);
        when(mockFile.getOriginalFilename()).thenReturn("test.txt");
        when(mockFile.getBytes()).thenReturn(new byte[0]); // ファイルデータを空のバイト配列として設定

        AnswerForm answerForm = new AnswerForm();
        answerForm.setFile(mockFile); // モックしたMultipartFileを設定

        // ユーザーが見つからない場合の設定
        when(userInfoRepository.findByLoginId(anyString())).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> {
            answerService.resistAnswerInfo(answerForm, mock(User.class), taskId);
        }).isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("user.not.found");
    }

    @Test
    public void testResistAnswerInfo_TaskNotFound() throws IOException {
        // 異常系: タスクが見つからない場合
        UserInfo userInfo = new UserInfo();
        userInfo.setLoginId("testUser"); // ユーザー名を設定
        when(userInfoRepository.findByLoginId("testUser")).thenReturn(Optional.of(userInfo));

        // モックのMultipartFileを作成
        MultipartFile mockFile = mock(MultipartFile.class);
        when(mockFile.getOriginalFilename()).thenReturn("test.txt");
        when(mockFile.getBytes()).thenReturn(new byte[0]); // 空のバイト配列を返す

        // AnswerFormにモックしたファイルを設定
        AnswerForm answerForm = new AnswerForm();
        answerForm.setFile(mockFile);
        answerForm.setContent("Some content"); // コンテンツも設定しておく

        // タスクが見つからないことを設定
        when(taskInfoRepository.findById(taskId)).thenReturn(Optional.empty());

        // ユーザーのモックを作成
        User user = mock(User.class);
        when(user.getUsername()).thenReturn("testUser");

        assertThatThrownBy(() -> {
            answerService.resistAnswerInfo(answerForm, user, taskId);
        }).isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("task.not.found");
    }

    @Test
    public void testGetAnswerListByPostId() {
        // 正常系: AnswerInfoのリストを取得する
        Long requestId = 1L;
        RequestInfo requestInfo = new RequestInfo();
        when(requestInfoRepository.findById(requestId)).thenReturn(Optional.of(requestInfo));
        List<AnswerInfo> answerList = new ArrayList<>();
        when(answerInfoRepository.findByRequestInfo(requestInfo)).thenReturn(answerList);

        List<AnswerInfo> result = answerService.getAnswerListByPostId(requestId);

        assertThat(result).isEqualTo(answerList);
    }

    @Test
    public void testGetAnswerListByPostId_RequestNotFound() {
        // 異常系: RequestInfoが見つからない場合
        Long requestId = 1L;
        when(requestInfoRepository.findById(requestId)).thenReturn(Optional.empty());

        List<AnswerInfo> result = answerService.getAnswerListByPostId(requestId);

        assertThat(result).isNull(); // nullを返すことを期待
    }

    @Test
    public void testGetAnswerById() {
        // 正常系: 特定のAnswerInfoを取得する
        Long answerId = 1L;
        AnswerInfo answerInfo = new AnswerInfo();
        when(answerInfoRepository.findById(answerId)).thenReturn(Optional.of(answerInfo));

        AnswerInfo result = answerService.getAnswerById(answerId);

        assertThat(result).isEqualTo(answerInfo);
    }

    @Test
    public void testGetAnswerById_NotFound() {
        // 異常系: AnswerInfoが見つからない場合
        Long answerId = 1L;
        when(answerInfoRepository.findById(answerId)).thenReturn(Optional.empty());

        AnswerInfo result = answerService.getAnswerById(answerId);

        assertThat(result).isNull(); // nullを返すことを期待
    }
}
