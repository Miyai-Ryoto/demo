package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.AnswerInfo;
import com.example.demo.entity.RequestInfo;

@Repository
public interface AnswerInfoRepository extends JpaRepository<AnswerInfo, Long>{

    List<AnswerInfo> findByRequestInfo(RequestInfo requestInfo);

}
