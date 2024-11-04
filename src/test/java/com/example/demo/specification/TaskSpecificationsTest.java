package com.example.demo.specification;

import com.example.demo.constant.TaskCondition;
import com.example.demo.entity.DepartmentInfo;
import com.example.demo.entity.RequestInfo;
import com.example.demo.entity.TaskInfo;
import com.example.demo.repository.DepartmentInfoRepository;
import com.example.demo.repository.RequestInfoRepository;
import com.example.demo.repository.TaskInfoRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class TaskSpecificationsTest {

    @Autowired
    private TaskInfoRepository taskInfoRepository;

    @Autowired
    private RequestInfoRepository requestInfoRepository;

    @Autowired
    private DepartmentInfoRepository departmentInfoRepository;

    @BeforeEach
    void setUp() {

        DepartmentInfo departmentInfo = new DepartmentInfo();
        departmentInfo.setId(null); // IDをnullにして自動生成させる
        departmentInfoRepository.save(departmentInfo);

        RequestInfo requestInfo1 = new RequestInfo();
        requestInfo1.setTitle("Important Update");
        requestInfo1.setEventDate(LocalDate.now());
        requestInfoRepository.save(requestInfo1);

        // テストデータのセットアップ
        TaskInfo task1 = new TaskInfo();
        task1.setId(1L);
        task1.setCondition(TaskCondition.toBoolean(TaskCondition.ANSWERED)); // 修正
        task1.setRequestInfo(requestInfo1);
        task1.setDepartmentInfo(departmentInfo);

        RequestInfo requestInfo2 = new RequestInfo();
        requestInfo2.setTitle("Important Update");
        requestInfo2.setEventDate(LocalDate.now().plusDays(1));
        requestInfoRepository.save(requestInfo2);

        TaskInfo task2 = new TaskInfo();
        task2.setId(2L);
        task2.setCondition(TaskCondition.toBoolean(TaskCondition.UNANSWERED)); // 修正
        task1.setRequestInfo(requestInfo2);
        task2.setDepartmentInfo(departmentInfo);

        taskInfoRepository.saveAll(List.of(task1, task2));
    }

    @Test
    void testBelongsToDepartment() {
        Specification<TaskInfo> spec = TaskSpecifications.belongsToDepartment(1L);
        List<TaskInfo> results = taskInfoRepository.findAll(spec);

        // 期待する結果の検証
        assertThat(results).hasSize(2); // 実際の部門IDに基づく
    }

    @Test
    void testHasTitle() {
        Specification<TaskInfo> spec = TaskSpecifications.hasTitle("Important");
        List<TaskInfo> results = taskInfoRepository.findAll(spec);

        // 期待する結果の検証
        assertThat(results).hasSize(1);
        assertThat(results.get(0).getRequestInfo().getTitle()).isEqualTo("Important Update");
    }

    @Test
    void testHasCondition() {
        Specification<TaskInfo> spec = TaskSpecifications.hasCondition(TaskCondition.ANSWERED);
        List<TaskInfo> results = taskInfoRepository.findAll(spec);

        // 期待する結果の検証
        assertThat(results).hasSize(1);
        assertThat(results.get(0).getRequestInfo().getTitle()).isEqualTo("Important Update");
    }

    @Test
    void testStartDateGreaterThanEqual() {
        Specification<TaskInfo> spec = TaskSpecifications.startDateGreaterThanEqual(null);
        List<TaskInfo> results = taskInfoRepository.findAll(spec);

        // 期待する結果の検証
        assertThat(results).hasSize(2); // すべてのタスクが今日以降のイベント日である場合
    }
}
