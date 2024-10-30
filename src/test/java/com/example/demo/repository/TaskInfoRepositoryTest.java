package com.example.demo.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.data.domain.PageRequest.of;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.demo.entity.DepartmentInfo;
import com.example.demo.entity.TaskInfo;

@DataJpaTest
public class TaskInfoRepositoryTest {

    @Autowired
    private TaskInfoRepository taskInfoRepository;

    @Autowired
    private DepartmentInfoRepository departmentInfoRepository;

    private DepartmentInfo department;
    private TaskInfo task1;
    private TaskInfo task2;

    @BeforeEach
    public void setUp() {
        // テスト用のDepartmentInfoを作成
        department = new DepartmentInfo();
        department.setDepartmentName("Engineering");
        department = departmentInfoRepository.save(department); // 保存後、戻り値を取得

        // テスト用のTaskInfoを作成
        task1 = new TaskInfo();
        task1.setId((long) 1);
        task1.setDepartmentInfo(department);
        task1 = taskInfoRepository.save(task1); // 保存後、戻り値を取得

        task2 = new TaskInfo();
        task2.setId((long) 2);
        task2.setDepartmentInfo(department);
        task2 = taskInfoRepository.save(task2); // 保存後、戻り値を取得
    }

    @Test
    public void testFindByDepartmentInfo() {
        Pageable pageable = of(0, 10);
        Page<TaskInfo> page = taskInfoRepository.findByDepartmentInfo(department, pageable);

        // アサーション
        assertThat(page.getTotalElements()).isEqualTo(2); // 2件のタスクがあるはず
        assertThat(page.getContent()).containsExactlyInAnyOrder(task1, task2); // 両方のタスクが含まれていることを確認
    }
}
