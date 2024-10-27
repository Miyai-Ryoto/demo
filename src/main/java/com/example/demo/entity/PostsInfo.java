package com.example.demo.entity;

import com.example.demo.constant.PostCondition;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "posts_table")
public class PostsInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private PostInfo postInfo;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private DepartmentInfo departmentInfo;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserInfo userInfo;

    @Column(name = "post_condition")
    private boolean condition;

    @Column(name = "read_condition")
    private boolean read;


    public PostCondition getStatus(){
        return PostCondition.fromBoolean(condition);
    }

}
