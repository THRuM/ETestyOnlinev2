package etestyonline.service.impl;

import etestyonline.model.OrgUnit;
import etestyonline.model.PrincipalToken;
import etestyonline.model.User;
import etestyonline.model.util.SETTINGS;
import etestyonline.repository.OrgUnitRepository;
import etestyonline.repository.PrincipalTokenRepository;
import etestyonline.service.AuthenticationWrapper;
import etestyonline.service.TeacherService;
import etestyonline.service.UserService;
import etestyonline.service.exceptions.OrgUnitNotFoundException;
import etestyonline.service.exceptions.TokenNotFoundException;
import etestyonline.service.exceptions.UserNotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import static etestyonline.model.util.SETTINGS.LOG_NOT_FOUND;
import static etestyonline.model.util.SETTINGS.LOG_REQUEST_FOR_USER;

@Service
public class TeacherServiceImpl implements TeacherService {

    @Autowired
    private PrincipalTokenRepository principalTokenRepository;

    @Autowired
    private AuthenticationWrapper authenticationWrapper;

    @Autowired
    private OrgUnitRepository orgUnitRepository;

    @Autowired
    private UserService userService;

    private static Logger logger = LogManager.getLogger(TeacherServiceImpl.class);

    @Override
    public Page<PrincipalToken> getAllClaims(Pageable pageable) {
        User teacher = authenticationWrapper.getAuthenticatetUser();

        return principalTokenRepository.findAllByTeacher(teacher, pageable);
    }

    @Override
    public void acceptClaimById(String principalTokenId) {

        PrincipalToken principalToken = principalTokenRepository.findOne(principalTokenId);

        if(principalToken == null) {
            logger.error(LOG_REQUEST_FOR_USER + authenticationWrapper.getAuthenticatetUser().getEmail() + " : Principal token with id: " + principalTokenId + LOG_NOT_FOUND);
            throw new TokenNotFoundException("Principal token with id: " + principalTokenId + LOG_NOT_FOUND);
        }

        User user = principalToken.getUser();
        User teacher = principalToken.getTeacher();

        user.setPrincipal(teacher);

        OrgUnit defaultTeacherOrgUnit = orgUnitRepository.findByOwnerAndDescription(teacher, SETTINGS.DEFAULT_ORG_UNIT);

        user.setOrgUnit(defaultTeacherOrgUnit);

        userService.addUser(user);

        principalTokenRepository.delete(principalToken);

        logger.info(LOG_REQUEST_FOR_USER + authenticationWrapper.getAuthenticatetUser().getEmail() + " : Claim from user " + user.getEmail() + " accepted");
    }

    @Override
    public void rejectClaimById(String principalTokenId) {
        PrincipalToken principalToken = principalTokenRepository.findOne(principalTokenId);

        if(principalToken == null) {
            logger.error(LOG_REQUEST_FOR_USER + authenticationWrapper.getAuthenticatetUser().getEmail() + " : Principal token with id: " + principalTokenId + LOG_NOT_FOUND);
            throw new TokenNotFoundException("Principal token with id: " + principalTokenId + LOG_NOT_FOUND);
        }

        logger.info(LOG_REQUEST_FOR_USER + authenticationWrapper.getAuthenticatetUser().getEmail() + " : Claim from user " + principalToken.getUser().getEmail() + " rejected");
        principalTokenRepository.delete(principalToken);
    }

    @Override
    public Page<User> getAllAssignedUsers(Pageable pageable) {
        User authUser = authenticationWrapper.getAuthenticatetUser();

        return userService.findAllByPrincipal(authUser, pageable);
    }

    @Override
    public void addOrgUnit(String description) {
        User user = authenticationWrapper.getAuthenticatetUser();

        OrgUnit orgUnit = new OrgUnit();
        orgUnit.setOwner(user);
        orgUnit.setDescription(description);

        orgUnitRepository.save(orgUnit);
        logger.info(LOG_REQUEST_FOR_USER + authenticationWrapper.getAuthenticatetUser().getEmail() + " : OrgUnit " + description + " added");
    }

    @Override
    public void updateOrgUnitForUser(String userId, String orgUnitId) {
        User pupil = userService.getUser(userId);
        User authUser = authenticationWrapper.getAuthenticatetUser();
        OrgUnit newOrgUnit = orgUnitRepository.findByIdAndOwner(orgUnitId, authUser);

        if(pupil == null){
            logger.error(LOG_REQUEST_FOR_USER + authenticationWrapper.getAuthenticatetUser().getEmail() + " : User with id: " + userId + LOG_NOT_FOUND);
            throw new UserNotFoundException("User with id: " + userId + LOG_NOT_FOUND);
        }

        if (newOrgUnit == null){
            logger.error(LOG_REQUEST_FOR_USER + authenticationWrapper.getAuthenticatetUser().getEmail() + " : OrgUnit with id: " + orgUnitId + LOG_NOT_FOUND);
            throw new OrgUnitNotFoundException("OrgUnit with id: " + orgUnitId + LOG_NOT_FOUND);
        }

        pupil.setOrgUnit(newOrgUnit);

        userService.editUser(pupil);

        logger.info(LOG_REQUEST_FOR_USER + authenticationWrapper.getAuthenticatetUser().getEmail() + " : OrgUnit for user " + pupil.getEmail() + " updated");
    }

    @Override
    public boolean haveRightsToEdit(String userId) {
        User authUser = authenticationWrapper.getAuthenticatetUser();
        User editUser = userService.getUser(userId);

        boolean haveRights = false;

        if(authUser.getRoles().contains(SETTINGS.TEACHER)) {
            if (editUser.getPrincipal() != null && editUser.getPrincipal().getEmail().equals(authUser.getEmail())) {
                haveRights = true;
            }
        }

        return haveRights;
    }
}
