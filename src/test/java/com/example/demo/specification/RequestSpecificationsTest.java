package com.example.demo.specification; 
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.ActiveProfiles;

import com.example.demo.entity.RequestInfo;
import com.example.demo.entity.UserInfo;
import com.example.demo.repository.RequestInfoRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.time.LocalDate;
import java.util.List;

@DataJpaTest
@ActiveProfiles("test")
public class RequestSpecificationsTest {

    @Autowired
    private RequestInfoRepository requestInfoRepository;

    @PersistenceContext
    private EntityManager entityManager;

    private UserInfo user;
    private RequestInfo request1;
    private RequestInfo request2;

    @BeforeEach
    public void setUp() {
        user = new UserInfo();
        user.setLoginId("testUser");
        entityManager.persist(user);

        request1 = new RequestInfo();
        request1.setTitle("Test Title 1");
        request1.setEventDate(LocalDate.now());
        request1.setUserInfo(user);
        entityManager.persist(request1);

        request2 = new RequestInfo();
        request2.setTitle("Another Title");
        request2.setEventDate(LocalDate.now().plusDays(1));
        request2.setUserInfo(user);
        entityManager.persist(request2);
    }

    @Test
    public void testBelongsToUser() {
        Specification<RequestInfo> spec = RequestSpecifications.belongsToUser(user.getId());
        List<RequestInfo> results = requestInfoRepository.findAll(spec);

        assertThat(results).hasSize(2);
    }

    @Test
    public void testHasTitle() {
        Specification<RequestInfo> spec = RequestSpecifications.hasTitle("Test Title");
        List<RequestInfo> results = requestInfoRepository.findAll(spec);

        assertThat(results).hasSize(1);
        assertThat(results.get(0).getTitle()).isEqualTo("Test Title 1");
    }

    @Test
    public void testStartDateGreaterThanEqual() {
        Specification<RequestInfo> spec = RequestSpecifications.startDateGreaterThanEqual(LocalDate.now());
        List<RequestInfo> results = requestInfoRepository.findAll(spec);

        assertThat(results).hasSize(2);
    }

    @Test
    public void testStartDateGreaterThanEqualWithNull() {
        Specification<RequestInfo> spec = RequestSpecifications.startDateGreaterThanEqual(null);
        List<RequestInfo> results = requestInfoRepository.findAll(spec);

        assertThat(results).hasSize(2);
    }
}

