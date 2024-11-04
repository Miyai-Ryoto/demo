package com.example.demo.service;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.example.demo.entity.AnswerInfo;
import com.example.demo.entity.RequestInfo;
import com.example.demo.repository.AnswerInfoRepository;
import com.example.demo.repository.RequestInfoRepository;

import java.util.Optional;

class FileServiceTest {

    @Mock
    private RequestInfoRepository requestInfoRepository;

    @Mock
    private AnswerInfoRepository answerInfoRepository;

    @InjectMocks
    private FileService fileService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void serveFile_returnsFileData_whenRequestInfoExists() {
        // Arrange
        Long id = 1L;
        String type = "request";
        RequestInfo requestInfo = new RequestInfo();
        requestInfo.setFileData("File Data".getBytes());
        requestInfo.setFileName("document.docx");

        when(requestInfoRepository.findById(id)).thenReturn(Optional.of(requestInfo));

        // Act
        ResponseEntity<byte[]> response = fileService.serveFile(id, type);

        // Assert
        assertThat(response.getStatusCode().value()).isEqualTo(200);
        assertThat(response.getHeaders().get(HttpHeaders.CONTENT_DISPOSITION))
                .containsExactly("attachment; filename=\"document.docx\"");
        assertThat(response.getHeaders().getContentType()).isEqualTo(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.wordprocessingml.document"));
        assertThat(response.getBody()).isEqualTo("File Data".getBytes());
    }

    @Test
    void serveFile_returnsFileData_whenAnswerInfoExists() {
        // Arrange
        Long id = 1L;
        String type = "answer";
        AnswerInfo answerInfo = new AnswerInfo();
        answerInfo.setFileData("Answer Data".getBytes());
        answerInfo.setFileName("response.xlsx");

        when(answerInfoRepository.findById(id)).thenReturn(Optional.of(answerInfo));

        // Act
        ResponseEntity<byte[]> response = fileService.serveFile(id, type);

        // Assert
        assertThat(response.getStatusCode().value()).isEqualTo(200);
        assertThat(response.getHeaders().get(HttpHeaders.CONTENT_DISPOSITION))
                .containsExactly("attachment; filename=\"response.xlsx\"");
        assertThat(response.getHeaders().getContentType()).isEqualTo(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
        assertThat(response.getBody()).isEqualTo("Answer Data".getBytes());
    }

    @Test
    void serveFile_returnsNotFound_whenFileDoesNotExist() {
        // Arrange
        Long id = 1L;
        String type = "request";

        when(requestInfoRepository.findById(id)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<byte[]> response = fileService.serveFile(id, type);

        // Assert
        assertThat(response.getStatusCode().value()).isEqualTo(404);
    }

    @Test
    void serveFile_returnsNotFound_whenTypeIsInvalid() {
        // Arrange
        Long id = 1L;
        String type = "invalidType";

        // Act
        ResponseEntity<byte[]> response = fileService.serveFile(id, type);

        // Assert
        assertThat(response.getStatusCode().value()).isEqualTo(404);
    }

    @Test
    void serveFile_returnsNotFound_whenNoFileDataExists() {
        // Arrange
        Long id = 1L;
        String type = "request";
        RequestInfo requestInfo = new RequestInfo();
        requestInfo.setFileData(null);
        requestInfo.setFileName("document.docx");

        when(requestInfoRepository.findById(id)).thenReturn(Optional.of(requestInfo));

        // Act
        ResponseEntity<byte[]> response = fileService.serveFile(id, type);

        // Assert
        assertThat(response.getStatusCode().value()).isEqualTo(404);
    }
}

