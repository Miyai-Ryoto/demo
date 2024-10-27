package com.example.demo.entity;

import java.time.LocalDate;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "request_table")
public class RequestInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String content;

    @Column(name = "event_date")
    private LocalDate eventDate;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "file_data")
    @Lob
    private byte[] fileData;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserInfo userInfo;

    @OneToMany(mappedBy = "requestInfo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TaskInfo> taskInfos;

    @OneToMany(mappedBy = "requestInfo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AnswerInfo> answerInfos;

}

