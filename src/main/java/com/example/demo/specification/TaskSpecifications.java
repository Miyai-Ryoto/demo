package com.example.demo.specification;

import org.springframework.data.jpa.domain.Specification;
import java.time.LocalDate;

import com.example.demo.constant.TaskCondition;
import com.example.demo.entity.TaskInfo;

public class TaskSpecifications {

    public static Specification<TaskInfo> belongsToDepartment(Long belongsDepartmentId) {
        return (root, query, criteriaBuilder) -> belongsDepartmentId == null ? null
                : criteriaBuilder.equal(root.get("departmentInfo").get("id"), belongsDepartmentId);
    }

    public static Specification<TaskInfo> hasTitle(String title) {
        return (root, query, criteriaBuilder) -> title == null || title.isEmpty() ? null
                : criteriaBuilder.like(root.get("postInfo").get("title"), "%" + title + "%");
    }

    public static Specification<TaskInfo> hasCondition(TaskCondition condition) {
        return (root, query, criteriaBuilder) -> condition == null ? null
                : criteriaBuilder.equal(root.get("condition"), TaskCondition.toBoolean(condition));
    }

    public static Specification<TaskInfo> hasDepartment(Long departmentId) {
        return (root, query, criteriaBuilder) -> departmentId == null ? null
                : criteriaBuilder.equal(root.get("postInfo").get("userInfo").get("departmentInfo").get("id"),
                        departmentId);
    }

    public static Specification<TaskInfo> startDateGreaterThanEqual(LocalDate eventDate){
        return (root, query, criteriaBuilder) -> eventDate == null ? null
        : criteriaBuilder.greaterThanOrEqualTo(root.get("postInfo").get("eventDate"), eventDate);
    }

}
