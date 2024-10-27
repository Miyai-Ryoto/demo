package com.example.demo.service;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.demo.entity.AnswerInfo;
import com.example.demo.entity.RequestInfo;
import com.example.demo.repository.AnswerInfoRepository;
import com.example.demo.repository.RequestInfoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FileService {

    private final RequestInfoRepository requestInfoRepository;

    private final AnswerInfoRepository answerInfoRepository;

    public ResponseEntity<byte[]> serveFile(Long id, String type) {
        JpaRepository<?, Long> repository = getRepository(type);
        Optional<?> optionalFileInfo = repository.findById(id);

        if (optionalFileInfo.isPresent()) {
            Optional<byte[]> fileData = getFileData(optionalFileInfo);
            if (fileData.isPresent()) {
                String fileName = getFileName(optionalFileInfo);
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                        .contentType(getContentType(fileName))
                        .body(fileData.get());
            }
        }
        return ResponseEntity.notFound().build();
    }

    private JpaRepository<?, Long> getRepository(String type) {
        return type.equals("answer") ? answerInfoRepository : requestInfoRepository;
    }

    private Optional<byte[]> getFileData(Optional<?> optionalFileInfo) {
        return optionalFileInfo.flatMap(fileInfo -> {
            if (fileInfo instanceof AnswerInfo) {
                return Optional.ofNullable(((AnswerInfo) fileInfo).getFileData());
            } else if (fileInfo instanceof RequestInfo) {
                return Optional.ofNullable(((RequestInfo) fileInfo).getFileData());
            }
            return Optional.empty();
        });
    }

    private String getFileName(Optional<?> optionalFileInfo) {
        return optionalFileInfo.map(fileInfo -> {
            if (fileInfo instanceof AnswerInfo) {
                return ((AnswerInfo) fileInfo).getFileName();
            } else if (fileInfo instanceof RequestInfo) {
                return ((RequestInfo) fileInfo).getFileName();
            }
            return "";
        }).orElse("");
    }

    private MediaType getContentType(String fileName) {
        if (fileName.endsWith(".docx")) {
            return MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.wordprocessingml.document");
        } else if (fileName.endsWith(".xlsx")) {
            return MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        } else {
            return MediaType.APPLICATION_OCTET_STREAM;
        }
    }

}
