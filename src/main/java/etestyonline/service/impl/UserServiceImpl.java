package etestyonline.service.impl;

import etestyonline.model.*;
import etestyonline.model.util.SETTINGS;
import etestyonline.model.util.UserDTO;
import etestyonline.repository.*;
import etestyonline.service.AuthenticationWrapper;
import etestyonline.service.EmailService;
import etestyonline.service.OrgUnitService;
import etestyonline.service.UserService;
import etestyonline.service.exceptions.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static etestyonline.model.util.SETTINGS.*;


@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VerificationTokenRepository verificationTokenRepository;

    @Autowired
    private PasswordResetTokenRepository passwordResetTokenRepository;

    @Autowired
    private PrincipalTokenRepository principalTokenRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationWrapper authenticationWrapper;

    @Autowired
    private OrgUnitService orgUnitService;

    @Autowired
    private TeacherPromoteTokenRepository teacherPromoteTokenRepository;

    private static Logger logger = LogManager.getLogger(UserServiceImpl.class);

    @Override
    public User getUser(String userId) {
        User authUser = authenticationWrapper.getAuthenticatetUser();
        User user;

        if(authUser.getId().equals(userId))
            return authUser;

        if(authUser.getRoles().contains(SETTINGS.ADMIN)){
            user = userRepository.findOne(userId);
        } else {
            user = userRepository.findByIdAndPrincipal(userId, authUser);
        }

        if(user == null) {
            logger.error(LOG_REQUEST_FOR_USER + authenticationWrapper.getAuthenticatetUser().getEmail() + " : User for id: " + userId + LOG_NOT_FOUND);
            throw new UserNotFoundException("User for id: " + userId + LOG_NOT_FOUND);
        }

        return user;
    }

    @Override
    public User getTeacher(String teacherId) {
        return userRepository.findByIdAndRolesContains(teacherId, SETTINGS.TEACHER);
    }

    @Override
    public User getUserForEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public void editUser(User user) {
        userRepository.save(user);
    }

    @Override
    public void addUser(User user) {
        userRepository.save(user);
    }

    @Override
    public User registerNewUser(UserDTO userDTO) {
        if(emailExist(userDTO.getEmail())){
            logger.error(" : Can't register user with email " + userDTO.getEmail() + " already exists");
            throw new UserAlreadyExistsException("Can't register user with email " + userDTO.getEmail() + " already exists");
        }

        User user = new User();
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));

        User admin = userRepository.findByRolesContains(SETTINGS.ADMIN).get(0);
        user.setPrincipal(admin);

        OrgUnit defaultOrgUnit = orgUnitService.getDefaultOrgUnitForUser(admin);

        user.setOrgUnit(defaultOrgUnit);

        User savedUser = userRepository.save(user);

        String tokenString = UUID.randomUUID().toString();
        VerificationToken token = new VerificationToken(user, tokenString);

        verificationTokenRepository.save(token);

        if(!emailService.sendVerificationEmail(token)){
            userRepository.delete(user);
            verificationTokenRepository.delete(token);
            throw new BadEmailException("Can't register user with email " + userDTO.getEmail() + " error during message sending");
        }

        logger.info(" : User " + user.getEmail() + " registration successful");

        return savedUser;
    }

    @Override
    public void confirmRegistration(String token) {
        //Zakładam osobną usługę/crona do usuwania tokenów po czasie
        VerificationToken verificationToken = verificationTokenRepository.findByToken(token);

        if(verificationToken == null) {
            logger.error(" : Registration token " + token + LOG_NOT_FOUND);
            throw new TokenNotFoundException("Registration token " + token + LOG_NOT_FOUND);
        }

        User user = verificationToken.getUser();

        user.setEnabled(true);

        userRepository.save(user);

        verificationTokenRepository.delete(verificationToken);

        logger.info(" : User " + user.getEmail() + " registration confirmed successfully");
    }

    @Override
    public void createPasswordResetTokenForUser(String email) {
        User user = userRepository.findByEmail(email);

        if(user == null || !user.isEnabled()){
            logger.error(" : User with email " + email + LOG_DOES_NOT_EXISTS);
            throw new UserNotFoundException("User with email " + email + LOG_DOES_NOT_EXISTS);
        }

        PasswordResetToken passwordResetToken = passwordResetTokenRepository.findByUser(user);

        if(passwordResetToken != null) {
            logger.error(" : Password reset token for user " + user.getEmail() + " already exist");
            throw new TokenExistException("Password reset token for user " + user.getEmail() + " already exist");
        }

        String token = UUID.randomUUID().toString();

        passwordResetToken = new PasswordResetToken(user, token);

        passwordResetTokenRepository.save(passwordResetToken);

        if(!emailService.sendPasswordResetEmail(passwordResetToken)){
            passwordResetTokenRepository.delete(passwordResetToken);
            throw new BadEmailException("Can't create password reset token for user " + passwordResetToken.getUser().getEmail() + " error during message sending");
        }

        logger.info(" : Password reset token for user " + user.getEmail() + " created");
    }

    @Override
    public Boolean isPasswordTokenValid(String token) {

        PasswordResetToken passwordResetToken = passwordResetTokenRepository.findByToken(token);

        return passwordResetToken != null;
    }

    @Override
    public void changePasswordForUser(User user, String password) {
        user.setPassword(passwordEncoder.encode(password));

        userRepository.save(user);

        emailService.sendPasswordChangeEmail(user);

        logger.info(" : Password for user " + user.getEmail() + " changed successfully");
    }

    @Override
    public void changePasswordForToken(String token, String password) {
        PasswordResetToken passwordResetToken = passwordResetTokenRepository.findByToken(token);

        if(passwordResetToken == null){
            logger.error(" : Password reset token " + token + LOG_DOES_NOT_EXISTS);
            throw new TokenNotFoundException("Password reset token " + token + LOG_DOES_NOT_EXISTS);
        }

        User user = passwordResetToken.getUser();
        changePasswordForUser(user, password);

        passwordResetTokenRepository.delete(passwordResetToken);
    }

    @Override
    public Page<User> getAllTeachers(Pageable pageable) {
        User user = authenticationWrapper.getAuthenticatetUser();

        if(user.getRoles().contains(ADMIN)){
            logger.error(LOG_REQUEST_FOR_USER + user.getEmail() + " : No valid request for user with role ADMIN");
            throw new BadRoleException("User " + user.getEmail() + " No valid request for user with role ADMIN");
        }

        return userRepository.findByRolesContainsAndIdNotIn(SETTINGS.TEACHER, new String[]{user.getPrincipal().getId(), user.getId()}, pageable);
    }

    @Override
    public String claimPrincipalById(String principalId) {
        User user = authenticationWrapper.getAuthenticatetUser();

        if(user.getRoles().contains(ADMIN)){
            logger.error(LOG_REQUEST_FOR_USER + user.getEmail() + " : No valid request for user with role ADMIN");
            throw new BadRoleException("User " + user.getEmail() + " No valid request for user with role ADMIN");
        }

        if(!user.getPrincipal().getRoles().contains(SETTINGS.ADMIN)){
            logger.error(LOG_REQUEST_FOR_USER + user.getEmail() + " : User " + user.getEmail() + " is already assigned to teacher");
            throw new BadRoleException("User " + user.getEmail() + " is already assigned to teacher");
        }

        if(user.getId().equals(principalId)){
            logger.error(LOG_REQUEST_FOR_USER + user.getEmail() + " : Can't assign user to itself");
            throw new BadInputException("Can't assign user to itself");
        }

        if(user.getPrincipal().getId().equals(principalId)){
            logger.error(LOG_REQUEST_FOR_USER + user.getEmail() + " : User already assigned to TEACHER");
            throw new BadInputException("User already assigned to teacher");
        }

        PrincipalToken principalToken = principalTokenRepository.findByUser(user);

        if(principalToken != null) {
            logger.error(LOG_REQUEST_FOR_USER + user.getEmail() + " : Token for user " + user.getEmail() + " already exists");
            throw new TokenExistException("Token for user " + user.getEmail() + " already exists");
        }

        User teacher = getTeacher(principalId);

        if(teacher == null) {
            logger.error(LOG_REQUEST_FOR_USER + user.getEmail() + " Teacher with id " + principalId + LOG_DOES_NOT_EXISTS);
            throw new UserNotFoundException("Teacher with id " + principalId + LOG_DOES_NOT_EXISTS);
        }

        if(!teacher.getRoles().contains(SETTINGS.TEACHER)) {
            logger.error(LOG_REQUEST_FOR_USER + user.getEmail() + "User for id " + principalId + " is not TEACHER");
            throw new BadRoleException("User for id " + principalId + " is not TEACHER");
        }

        principalToken = new PrincipalToken(user, teacher);

        principalTokenRepository.save(principalToken);

        return teacher.getEmail();
    }

    @Override
    public void claimPromoteToTeacher(String desc) {
        User user = authenticationWrapper.getAuthenticatetUser();

        //Double check
        if(user.getRoles().contains(SETTINGS.TEACHER) || user.getRoles().contains(SETTINGS.ADMIN)) {
            logger.error(LOG_REQUEST_FOR_USER + user.getEmail() + " : Can't promote user " + user.getEmail() + " already has TECHER role");
            throw new BadRoleException("Can't promote user " + user.getEmail() + " already has TECHER role");
        }

        TeacherPromoteToken token = new TeacherPromoteToken();
        token.setDescription(desc);
        token.setUser(user);

        teacherPromoteTokenRepository.save(token);
    }

    @Override
    public Boolean changePasswordForUser(String oldPassword, String newPassword) {
        User user = authenticationWrapper.getAuthenticatetUser();

        if(!passwordEncoder.matches(oldPassword, user.getPassword())){
            logger.error(" : Password change attempt error passwords does not match");
            return false;
        }

        changePasswordForUser(user, newPassword);

        return true;
    }

    @Override
    public Page<User> getAllUsersForOrgUnitId(String orgUnitId, Pageable pageable) {
        OrgUnit orgUnit = orgUnitService.getOrgUnitById(orgUnitId);

        return userRepository.findAllByOrgUnit(orgUnit, pageable);
    }

    @Override
    public Page<User> findAllByPrincipal(User user, Pageable pageable) {
        return userRepository.findAllByPrincipal(user, pageable);
    }

    private boolean emailExist(String email) {
        User user = userRepository.findByEmail(email);
        return user != null;
    }

    @Override
    public Page<User> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
    }
}
