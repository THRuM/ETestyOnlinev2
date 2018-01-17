package etestyonline.service;

import etestyonline.model.User;
import etestyonline.model.util.UserDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;

public interface UserService {

    @PreAuthorize("hasRole('USER')")
    User getUser(String userId);

    @PreAuthorize("hasRole('USER')")
    User getTeacher(String teacherId);

    @PreAuthorize("hasRole('USER')")
    User getUserForEmail(String email);

    @PreAuthorize("hasRole('TEACHER')")
    void editUser(User user);

    @PreAuthorize("hasRole('USER') and !hasRole('ADMIN')")
    Page<User> getAllTeachers(Pageable pageable);

    @PreAuthorize("hasRole('USER') and !hasRole('ADMIN')")
    String claimPrincipalById(String principalId);

    @PreAuthorize("isAnonymous()")
    User registerNewUser(UserDTO userDTO);

    @PreAuthorize("isAnonymous()")
    void confirmRegistration(String token);

    @PreAuthorize("isAnonymous()")
    void createPasswordResetTokenForUser(String email);

    @PreAuthorize("hasRole('USER')")
    void changePasswordForUser(User user, String password);

    @PreAuthorize("isAnonymous()")
    void changePasswordForToken(String token, String password);

    @PreAuthorize("isAnonymous()")
    Boolean isPasswordTokenValid(String token);

    @PreAuthorize("hasRole('USER')")
    void claimPromoteToTeacher(String desc);

    @PreAuthorize("hasRole('USER')")
    Boolean changePasswordForUser(String oldPassword, String newPassword);

    @PreAuthorize("hasRole('TEACHER')")
    Page<User> getAllUsersForOrgUnitId(String orgUnitId, Pageable pageable);

    @PreAuthorize("hasRole('TEACHER')")
    Page<User> findAllByPrincipal(User user, Pageable pageable);

    @PreAuthorize("hasRole('TEACHER')")
    void addUser(User user);

    @PreAuthorize("hasRole('ADMIN')")
    Page<User> getAllUsers(Pageable pageable);
}
