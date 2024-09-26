package com.example.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "user_table")
@Data
public class UserInfo {

    @Id
    @Column(name = "login_id")
    private String loginId;
    
    private String password;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private DepartmentInfo departmentInfo;
    

}
