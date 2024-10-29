package com.example.demo.controller;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.User;
import org.springframework.ui.Model;

import com.example.demo.entity.TaskInfo;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.form.ResponsibleForm;
import com.example.demo.service.ListService;
import com.example.demo.service.ResponsibleService;

public class ResponsibleControllerTest {

    @InjectMocks
    private ResponsibleController responsibleController;

    @Mock
    private ResponsibleService responsibleService;

    @Mock
    private ListService listService;

    @Mock
    private Model model;

    @Mock
    private User user;

    private ResponsibleForm responsibleForm;
    private Long taskId = 1L;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        responsibleForm = new ResponsibleForm(); // 必要に応じてフィールドを設定
    }

    @Test
    public void testCreateResponsible_Success() {
        // 正常系: リダイレクトが期待される
        String result = responsibleController.createResponsible(model, user, responsibleForm, taskId);

        assertThat(result).isEqualTo("redirect:/tasklist");
        verify(responsibleService).resistResponsibleInfo(responsibleForm, taskId);
    }

    @Test
    public void testCreateResponsible_ResourceNotFoundException() {
        // 異常系: ResourceNotFoundExceptionが発生する場合
        doThrow(new ResourceNotFoundException("Resource not found"))
                .when(responsibleService).resistResponsibleInfo(responsibleForm, taskId);

        when(listService.getTaskById(taskId)).thenReturn(new TaskInfo()); // 必要に応じて設定
        when(listService.getUserListByDepartmentId(user)).thenReturn(new ArrayList<>());

        String result = responsibleController.createResponsible(model, user, responsibleForm, taskId);

        verify(model).addAttribute("message", "Resource not found");
        verify(model).addAttribute(eq("task"), any(TaskInfo.class)); // 必要に応じて型を修正
        verify(model).addAttribute(eq("userList"), anyList());
        assertThat(result).isEqualTo("taskdetail");
    }
}

