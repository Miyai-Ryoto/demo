package com.example.demo.entity;

import com.example.demo.constant.TaskCondition;

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
@Table(name = "task_table")
public class TaskInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "request_id")
    private RequestInfo requestInfo;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private DepartmentInfo departmentInfo;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserInfo userInfo;

    @Column(name = "task_condition")
    private boolean condition;

    public TaskCondition getStatus(){
        return TaskCondition.fromBoolean(condition);
    }

}
