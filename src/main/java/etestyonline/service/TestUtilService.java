package etestyonline.service;

import etestyonline.model.Category;
import etestyonline.model.TestParams;
import etestyonline.model.TestSpecs;
import etestyonline.model.User;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.multipart.MultipartFile;

public interface TestUtilService {
    @PreAuthorize("hasRole('ADMIN')")
    void addTestSpecsForUser(User user);

    @PreAuthorize("hasRole('TEACHER')")
    void addCategory(Category category);

    @PreAuthorize("hasRole('USER')")
    TestSpecs getTestSpecs();

    @PreAuthorize("hasRole('TEACHER')")
    void addTimeAndAmount(Integer time, Integer amount);

    @PreAuthorize("hasRole('TEACHER')")
    void removeTimeAndAmount(String time, String amount);

    @PreAuthorize("hasRole('USER')")
    boolean isTestParamValid(TestParams testParams);

    @PreAuthorize("hasRole('ADMIN')")
    void addDefaultPicture(MultipartFile file);

    @PreAuthorize("hasRole('USER')")
    String getDefaultImage();
}
