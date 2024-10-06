package com.example.demo.entity;

import java.util.List;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "user_table")
@Data
public class UserInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "login_id")
    private String loginId;
    
    private String password;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private DepartmentInfo departmentInfo;

    @OneToMany(mappedBy = "userInfo" , cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<PostInfo> postInfo;

    @OneToMany(mappedBy = "userInfo" , cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AnswerInfo> answerInfos;

    @OneToMany(mappedBy = "userInfo" , cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ResponsibleInfo> responsibleInfos;


}
