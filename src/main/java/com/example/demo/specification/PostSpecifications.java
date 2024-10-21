package com.example.demo.specification;

import java.time.LocalDate;

import org.springframework.data.jpa.domain.Specification;
import com.example.demo.entity.PostInfo;

public class PostSpecifications {

    public static Specification<PostInfo> belongsToUser(Long belongsUserId) {
        return (root, query, criteriaBuilder) -> belongsUserId == null ? null
                : criteriaBuilder.equal(root.get("userInfo").get("id"), belongsUserId);
    }

    public static Specification<PostInfo> hasTitle(String title) {
        return (root, query, criteriaBuilder) -> title == null || title.isEmpty() ? null
                : criteriaBuilder.like(root.get("title"), "%" + title + "%");
    }

    public static Specification<PostInfo> startDateGreaterThanEqual(LocalDate eventDate){
        return (root, query, criteriaBuilder) -> eventDate == null ? null
        : criteriaBuilder.greaterThanOrEqualTo(root.get("eventDate"), eventDate);
    }

}
