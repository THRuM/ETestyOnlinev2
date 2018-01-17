package etestyonline.service.impl;

import etestyonline.model.OrgUnit;
import etestyonline.model.User;
import etestyonline.model.util.SETTINGS;
import etestyonline.repository.OrgUnitRepository;
import etestyonline.service.AuthenticationWrapper;
import etestyonline.service.OrgUnitService;
import etestyonline.service.exceptions.OrgUnitNotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

import static etestyonline.model.util.SETTINGS.LOG_NOT_FOUND;
import static etestyonline.model.util.SETTINGS.LOG_REQUEST_FOR_USER;

@Service
public class OrgUnitServiceImpl implements OrgUnitService {

    @Autowired
    private OrgUnitRepository orgUnitRepository;

    @Autowired
    private AuthenticationWrapper authenticationWrapper;

    private static Logger logger = LogManager.getLogger(OrgUnitServiceImpl.class);

    @Override
    public OrgUnit getDefaultOrgUnitForUser(User user) {
        return orgUnitRepository.findByOwnerAndDescription(user, SETTINGS.DEFAULT_ORG_UNIT);
    }

    @Override
    public List<OrgUnit> getAllOrgUnits() {
        User user = authenticationWrapper.getAuthenticatetUser();
        return orgUnitRepository.findAllByOwner(user);
    }

    @Override
    public Page<OrgUnit> getAllOrgUnits(Pageable pageable, boolean respectAdminRole) {
        User user = authenticationWrapper.getAuthenticatetUser();

        if(respectAdminRole && user.getRoles().contains(SETTINGS.ADMIN)){
            return orgUnitRepository.findAll(pageable);
        }

        return orgUnitRepository.findAllByOwner(user, pageable);
    }

    @Override
    public void deleteOrgUnitById(String orgUnitId) {
        User user = authenticationWrapper.getAuthenticatetUser();
        OrgUnit orgUnit = orgUnitRepository.findByIdAndOwner(orgUnitId, user);

        if(orgUnit == null){
            logger.error(LOG_REQUEST_FOR_USER + authenticationWrapper.getAuthenticatetUser().getEmail() + " : OrgUnit for id: " + orgUnitId + LOG_NOT_FOUND);
            throw new OrgUnitNotFoundException("OrgUnit for id: " + orgUnitId + LOG_NOT_FOUND);
        }

        logger.info(LOG_REQUEST_FOR_USER + authenticationWrapper.getAuthenticatetUser().getEmail() + " : OrgUnit with id: " + orgUnitId + " deleted");
        orgUnitRepository.delete(orgUnit);
    }

    @Override
    public OrgUnit getOrgUnitById(String orgUnitId) {
        User user = authenticationWrapper.getAuthenticatetUser();

        OrgUnit orgUnit;

        if(user.getRoles().contains(SETTINGS.ADMIN)){
            orgUnit = orgUnitRepository.findOne(orgUnitId);
        } else {
            orgUnit = orgUnitRepository.findByIdAndOwner(orgUnitId, user);
        }

        if(orgUnit == null){
            logger.error(LOG_REQUEST_FOR_USER + authenticationWrapper.getAuthenticatetUser().getEmail() + " : OrgUnit for id " + orgUnitId + LOG_NOT_FOUND);
            throw new OrgUnitNotFoundException("OrgUnit for id: " + orgUnitId + LOG_NOT_FOUND);
        }

        return orgUnit;
    }
}
