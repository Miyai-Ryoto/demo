package com.example.demo.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;
import org.springframework.security.core.userdetails.User;
import org.springframework.ui.Model;

import com.example.demo.entity.TaskInfo;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.form.AnswerForm;
import com.example.demo.service.AnswerService;
import com.example.demo.service.ListService;

import java.io.IOException;
import java.util.ArrayList;

@ExtendWith(MockitoExtension.class)
public class AnswerControllerTest {

    @InjectMocks
    private AnswerController answerController;

    @Mock
    private AnswerService answerService;

    @Mock
    private ListService listService;

    @Mock
    private MessageSource messageSource;

    @Mock
    private Model model;

    @Mock
    private User user;

    private AnswerForm answerForm;
    private Long taskId = 1L;

    @BeforeEach
    public void setUp() {
        answerForm = new AnswerForm();
    }

    @Test
    public void testCreateAnswer_Success() throws IOException {
        // 正常系: リダイレクトが期待される
        String result = answerController.createAnswer(answerForm, user, taskId, model);

        assertThat(result).isEqualTo("redirect:/tasklist");
        verify(answerService).resistAnswerInfo(answerForm, user, taskId);
    }

    @Test
    public void testCreateAnswer_ResourceNotFoundException() throws IOException {
        // 異常系: ResourceNotFoundExceptionが発生する場合
        doThrow(new ResourceNotFoundException("Resource not found"))
                .when(answerService).resistAnswerInfo(answerForm, user, taskId);

        when(listService.getTaskById(taskId)).thenReturn(new TaskInfo());
        when(listService.getUserListByDepartmentId(any(User.class))).thenReturn(new ArrayList<>());

        String result = answerController.createAnswer(answerForm, user, taskId, model);

        verify(model).addAttribute("message", "Resource not found");
        verify(model).addAttribute(eq("task"), any(TaskInfo.class)); // マッチャーを使用
        verify(model).addAttribute(eq("userList"), anyList());
        assertThat(result).isEqualTo("taskdetail");
    }

    @Test
    public void testCreateAnswer_IOException() throws IOException {
        // 異常系: IOExceptionが発生する場合
        doThrow(new IOException("IO error"))
                .when(answerService).resistAnswerInfo(answerForm, user, taskId);

        when(messageSource.getMessage(anyString(), any(), any())).thenReturn("An error occurred");
        when(listService.getTaskById(taskId)).thenReturn(new TaskInfo());
        when(listService.getUserListByDepartmentId(user)).thenReturn(new ArrayList<>());

        String result = answerController.createAnswer(answerForm, user, taskId, model);

        verify(model).addAttribute("message", "An error occurred");
        verify(model).addAttribute(eq("task"), any(TaskInfo.class)); // 修正: TaskInfoの型
        verify(model).addAttribute(eq("userList"), anyList());
        assertThat(result).isEqualTo("taskdetail");
    }
}
