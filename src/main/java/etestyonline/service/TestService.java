package etestyonline.service;

import etestyonline.model.Test;
import etestyonline.model.TestParams;
import etestyonline.model.User;
import etestyonline.model.util.TestProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

public interface TestService {

    @PreAuthorize("hasRole('USER')")
    Test makeTest(TestParams testParams);

    @PreAuthorize("hasRole('USER')")
    Double endTest(Test test);

    @PreAuthorize("hasRole('USER')")
    Page<Test> allTests(Pageable pageable);

    @PreAuthorize("hasRole('USER')")
    Page<Test> allTestsForUserId(String userId, Pageable pageable);

    @PreAuthorize("hasRole('USER')")
    Test getTestForId(String testId);

    @PreAuthorize("hasRole('TEACHER')")
    Page<Test> getAllTestForTeacher(Pageable pageable);

    @PreAuthorize("hasRole('ADMIN')")
    void updateOrgUnitForUser(User user, String newOrgUnit);

    @PreAuthorize("hasRole('USER')")
    long checkTimeForTest(Test test);

    @PreAuthorize("hasRole('USER')")
    List<TestProjection> getLastFiveTestsForUser();
}
