package etestyonline.web;

import etestyonline.service.AdminService;
import etestyonline.service.OrgUnitService;
import etestyonline.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private UserService userService;

    @Autowired
    private OrgUnitService orgUnitService;

    @RequestMapping
    public String adminPanel() {
        return "adminPanel";
    }

    @RequestMapping(value = "claims", method = RequestMethod.GET)
    public String allClaims(Model model, Pageable pageable) {

        model.addAttribute("tokens", adminService.getAllClaims(pageable));

        return "allAdminClaims";
    }

    @RequestMapping(value = "claims/view/{claimId}", method = RequestMethod.GET)
    public String viewClaimDetails(@PathVariable("claimId") String claimId, Model model) {

        model.addAttribute("claim", adminService.getClaimById(claimId));

        return "adminClaimView";
    }

    @RequestMapping(value = "claims/accept/{claimId}", method = RequestMethod.GET)
    public String acceptAdminClaim(@PathVariable("claimId") String claimId) {

        adminService.acceptPromoteClaimById(claimId);

        return "redirect:/admin/claims";
    }

    @RequestMapping(value = "claims/reject/{claimId}", method = RequestMethod.GET)
    public String rejectAdminClaim(@PathVariable("claimId") String claimId) {

        adminService.rejectPromoteClaimById(claimId);

        return "redirect:/admin/claims";
    }


    @RequestMapping(value = "user/all", method = RequestMethod.GET)
    public String allUsersInSystem(Model model, Pageable pageable) {

        model.addAttribute("users", userService.getAllUsers(pageable));

        return "allUsers";
    }

    @RequestMapping(value = "orgUnit/all", method = RequestMethod.GET)
    public String allOrgUnitsInSystem(Model model, Pageable pageable) {

        model.addAttribute("orgUnits", orgUnitService.getAllOrgUnits(pageable, true));
        model.addAttribute("showOwner", true);

        return "allOrgUnits";
    }

}
