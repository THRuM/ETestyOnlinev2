package etestyonline.service.impl;

import etestyonline.model.OrgUnit;
import etestyonline.model.TeacherPromoteToken;
import etestyonline.model.User;
import etestyonline.model.util.SETTINGS;
import etestyonline.repository.OrgUnitRepository;
import etestyonline.repository.TeacherPromoteTokenRepository;
import etestyonline.service.*;
import etestyonline.service.exceptions.TokenNotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import static etestyonline.model.util.SETTINGS.*;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private TeacherPromoteTokenRepository teacherPromoteTokenRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private TestUtilService testUtilService;

    @Autowired
    private OrgUnitRepository orgUnitRepository;

    @Autowired
    private TestService testService;

    @Autowired
    private AuthenticationWrapper authenticationWrapper;

    private static Logger logger = LogManager.getLogger(AdminServiceImpl.class);

    @Override
    public Page<TeacherPromoteToken> getAllClaims(Pageable pageable) {
        return teacherPromoteTokenRepository.findAll(pageable);
    }

    @Override
    public TeacherPromoteToken getClaimById(String claimId) {
        TeacherPromoteToken token = teacherPromoteTokenRepository.findById(claimId);

        if(token == null) {
            logger.error(LOG_REQUEST_FOR_USER + authenticationWrapper.getAuthenticatetUser().getEmail() + " : " + SETTINGS.LOG_PROMOTE_TOKEN_ID + claimId + LOG_NOT_FOUND);
            throw new TokenNotFoundException(LOG_PROMOTE_TOKEN_ID + claimId + LOG_DOES_NOT_EXISTS);
        }

        return token;
    }

    @Override
    public void acceptPromoteClaimById(String claimId) {
        TeacherPromoteToken token = teacherPromoteTokenRepository.findById(claimId);

        if(token == null) {
            logger.error(LOG_REQUEST_FOR_USER + authenticationWrapper.getAuthenticatetUser().getEmail() + " : " + SETTINGS.LOG_PROMOTE_TOKEN_ID + claimId + LOG_NOT_FOUND);
            throw new TokenNotFoundException(LOG_PROMOTE_TOKEN_ID + claimId + LOG_DOES_NOT_EXISTS);
        }

        User userToPromote = token.getUser();

        userToPromote.getRoles().add(SETTINGS.TEACHER);

        User admin = userService.getUserForEmail(SETTINGS.HEAD_ADMIN_EMAIL);

        OrgUnit teachersOrgUnit = orgUnitRepository.findByOwnerAndDescription(admin, SETTINGS.TEACHERS_ORG_UNIT);

        userToPromote.setOrgUnit(teachersOrgUnit);
        userToPromote.setPrincipal(admin);

        testService.updateOrgUnitForUser(userToPromote, admin.getEmail());

        userService.editUser(userToPromote);

        testUtilService.addTestSpecsForUser(userToPromote);

        OrgUnit userDefaultOrgUnit = new OrgUnit();
        userDefaultOrgUnit.setDescription(SETTINGS.DEFAULT_ORG_UNIT);
        userDefaultOrgUnit.setOwner(userToPromote);

        orgUnitRepository.save(userDefaultOrgUnit);

        teacherPromoteTokenRepository.delete(token);

        logger.info(LOG_REQUEST_FOR_USER + authenticationWrapper.getAuthenticatetUser().getEmail() + " : User " + userToPromote.getEmail() + " promoted to TEACHER");
    }

    @Override
    public void rejectPromoteClaimById(String claimId) {
        TeacherPromoteToken token = teacherPromoteTokenRepository.findById(claimId);

        if(token == null) {
            logger.error(LOG_REQUEST_FOR_USER + authenticationWrapper.getAuthenticatetUser().getEmail() + " : " + SETTINGS.LOG_PROMOTE_TOKEN_ID + claimId + LOG_NOT_FOUND);
            throw new TokenNotFoundException(LOG_PROMOTE_TOKEN_ID + claimId + LOG_DOES_NOT_EXISTS);
        }

        logger.info(LOG_REQUEST_FOR_USER + authenticationWrapper.getAuthenticatetUser().getEmail() + " : Claim to promote to TECHER from user " + token.getUser().getEmail() + " rejected");
        teacherPromoteTokenRepository.delete(token);
    }
}
