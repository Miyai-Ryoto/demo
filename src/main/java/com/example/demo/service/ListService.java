package com.example.demo.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import com.example.demo.entity.DepartmentInfo;
import com.example.demo.entity.RequestInfo;
import com.example.demo.entity.TaskInfo;
import com.example.demo.entity.UserInfo;
import com.example.demo.form.RequestSearchModel;
import com.example.demo.form.TaskSearchModel;
import com.example.demo.repository.RequestInfoRepository;
import com.example.demo.repository.TaskInfoRepository;
import com.example.demo.repository.UserInfoRepository;
import com.example.demo.specification.RequestSpecifications;
import com.example.demo.specification.TaskSpecifications;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ListService {

    private final RequestInfoRepository requestInfoRepository;

    private final TaskInfoRepository taskInfoRepository;

    private final UserInfoRepository userInfoRepository;

    public TaskInfo getTaskById(Long id) {
        return taskInfoRepository.findById(id).orElse(null);
    }

    public Page<TaskInfo> getTaskListByDepartmnetId(User user, Pageable pageable){
        DepartmentInfo departmentInfo = getDepartmentInfoByUserName(user);
        return taskInfoRepository.findByDepartmentInfo(departmentInfo, pageable);
    }

    public Page<TaskInfo> searchTaskList(User user, TaskSearchModel target, Pageable pageable) {
        DepartmentInfo departmentInfo = getDepartmentInfoByUserName(user);
        Long belongsDepartmentId = departmentInfo.getId();
        Specification<TaskInfo> spec = Specification
                .where(TaskSpecifications.belongsToDepartment(belongsDepartmentId))
                .and(TaskSpecifications.hasTitle(target.getTitle()))
                .and(TaskSpecifications.hasCondition(target.getCondition()))
                .and(TaskSpecifications.hasDepartment(target.getDepartmentId()))
                .and(TaskSpecifications.startDateGreaterThanEqual(target.getEventDate()));

        return taskInfoRepository.findAll(spec, pageable);
    }

    public RequestInfo getRequestById(Long id) {
        return requestInfoRepository.findById(id).orElse(null);
    }

    public Page<RequestInfo> getRequestListByUserId(User user, Pageable pageable) {
        DepartmentInfo departmentInfo = getDepartmentInfoByUserName(user);
        List<UserInfo> usersInSameDepartment = userInfoRepository.findByDepartmentInfo(departmentInfo);
        return requestInfoRepository.findByUserInfoIn(usersInSameDepartment, pageable);
    }

    public Page<RequestInfo> searchRequestList(User user, RequestSearchModel target, Pageable pageable){
        UserInfo userInfo = userInfoRepository.findByLoginId(user.getUsername()).orElse(null);
        Long belongsUserId = userInfo.getId();
        Specification<RequestInfo> spec = Specification
        .where(RequestSpecifications.belongsToUser(belongsUserId))
        .and(RequestSpecifications.hasTitle(target.getTitle()))
        .and(RequestSpecifications.startDateGreaterThanEqual(target.getEventDate()));

        return requestInfoRepository.findAll(spec, pageable);
    }
    
    public List<UserInfo> getUserListByDepartmentId(User user) {
        DepartmentInfo departmentInfo = getDepartmentInfoByUserName(user);
        return userInfoRepository.findByDepartmentInfo(departmentInfo);
    }

    private DepartmentInfo getDepartmentInfoByUserName(User user) {
        UserInfo userInfo = userInfoRepository.findByLoginId(user.getUsername()).orElse(null);
        return userInfo.getDepartmentInfo();
    }

}
