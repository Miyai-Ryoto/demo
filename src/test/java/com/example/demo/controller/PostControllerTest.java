package com.example.demo.controller;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.MessageSource;
import org.springframework.security.core.userdetails.User;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.form.PostForm;
import com.example.demo.repository.DepartmentInfoRepository;
import com.example.demo.service.PostService;

import java.io.IOException;
import java.util.Collections;


@ActiveProfiles("test")
public class PostControllerTest {

    @InjectMocks
    private PostController postController;

    @Mock
    private MessageSource messageSource;

    @Mock
    private PostService postService;

    @Mock
    private DepartmentInfoRepository departmentInfoRepository;

    @Mock
    private Model model;

    @Mock
    private BindingResult bindingResult;

    @Mock
    private User user;

    private PostForm postForm;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        postForm = new PostForm(); // 必要に応じてフィールドを設定
    }

    @Test
    public void testView() {
        // Arrange
        when(departmentInfoRepository.findAll()).thenReturn(Collections.emptyList());

        // Act
        String result = postController.view(model, postForm);

        // Assert
        verify(model).addAttribute("departments", Collections.emptyList());
        verify(model).addAttribute("postForm", postForm);
        assertThat(result).isEqualTo("post");
    }

    @Test
    public void testCreatePost_Success() throws IOException {
        // Arrange
        when(bindingResult.hasErrors()).thenReturn(false);

        // Act
        String result = postController.createPost(postForm, bindingResult, user, model);

        // Assert
        verify(postService).resistPostInfo(postForm, user);
        assertThat(result).isEqualTo("redirect:/requestlist");
    }

    @Test
    public void testCreatePost_BindingErrors() {
        // Arrange
        when(bindingResult.hasErrors()).thenReturn(true);
        when(messageSource.getMessage(anyString(), any(), any())).thenReturn("Error occurred");
        when(departmentInfoRepository.findAll()).thenReturn(Collections.emptyList());

        // Act
        String result = postController.createPost(postForm, bindingResult, user, model);

        // Assert
        verify(model).addAttribute("message", "Error occurred");
        verify(model).addAttribute("departments", Collections.emptyList());
        assertThat(result).isEqualTo("post");
    }

    @Test
    public void testCreatePost_ResourceNotFoundException() throws IOException {
        // Arrange
        when(bindingResult.hasErrors()).thenReturn(false);
        doThrow(new ResourceNotFoundException("Resource not found"))
                .when(postService).resistPostInfo(postForm, user);
        when(departmentInfoRepository.findAll()).thenReturn(Collections.emptyList());

        // Act
        String result = postController.createPost(postForm, bindingResult, user, model);

        // Assert
        verify(model).addAttribute("message", "Resource not found");
        verify(model).addAttribute("departments", Collections.emptyList());
        assertThat(result).isEqualTo("post");
    }

    @Test
    public void testCreatePost_IOException() throws IOException {
        // Arrange
        when(bindingResult.hasErrors()).thenReturn(false);
        doThrow(new IOException("IO error"))
                .when(postService).resistPostInfo(postForm, user);
        when(messageSource.getMessage(anyString(), any(), any())).thenReturn("An error occurred");
        when(departmentInfoRepository.findAll()).thenReturn(Collections.emptyList());

        // Act
        String result = postController.createPost(postForm, bindingResult, user, model);

        // Assert
        verify(model).addAttribute("message", "An error occurred");
        verify(model).addAttribute("departments", Collections.emptyList());
        assertThat(result).isEqualTo("post");
    }
}

