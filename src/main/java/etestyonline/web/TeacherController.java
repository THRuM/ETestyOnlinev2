package etestyonline.web;

import etestyonline.model.util.DescriptionDTO;
import etestyonline.model.util.TestSpecsDTO;
import etestyonline.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping(value = "/teacher")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private UserService userService;

    @Autowired
    private OrgUnitService orgUnitService;

    @Autowired
    private TestUtilService testUtilService;

    @Autowired
    private TestService testService;

    @RequestMapping(method = RequestMethod.GET)
    public String teacherPanel() {
        return "teacherPanel";
    }

    @RequestMapping(value = "claims", method = RequestMethod.GET)
    public String allClaims(Model model, Pageable pageable) {

        model.addAttribute("tokens", teacherService.getAllClaims(pageable));

        return "allTeachersClaims";
    }

    @RequestMapping(value = "claims/accept/{principalTokenId}", method = RequestMethod.GET)
    public String acceptClaim(@PathVariable("principalTokenId") String principalTokenId) {

        teacherService.acceptClaimById(principalTokenId);

        return "redirect:/teacher/claims";
    }

    @RequestMapping(value = "claims/reject/{principalTokenId}", method = RequestMethod.GET)
    public String rejectClaim(@PathVariable("principalTokenId") String principalTokenId) {

        teacherService.rejectClaimById(principalTokenId);

        return "redirect:/teacher/claims";
    }

    @RequestMapping(value = "users/all", method = RequestMethod.GET)
    public String getAllUsersForTeacher(Model model, Pageable pageable) {

        model.addAttribute("users", teacherService.getAllAssignedUsers(pageable));

        return "allUsers";
    }

    @RequestMapping(value = "users/view/{userId}", method = RequestMethod.GET)
    public String viewUserProfile(@PathVariable("userId") String userId, Model model) {

        model.addAttribute("user", userService.getUser(userId));
        model.addAttribute("orgUnits", orgUnitService.getAllOrgUnits());

        return "userProfile";
    }

    @RequestMapping(value = "users/view/{userId}", method = RequestMethod.POST)
    public String updateUserProfile(@PathVariable("userId") String userId,
                                    @ModelAttribute("orgUnitId") String orgUnitId) {

        teacherService.updateOrgUnitForUser(userId, orgUnitId);

        return "redirect:/teacher/users/view/" + userId;
    }

    @RequestMapping(value = "orgUnit/all", method = RequestMethod.GET)
    public String getAllOrgUnits(Model model, Pageable pageable) {

        model.addAttribute("orgUnits", orgUnitService.getAllOrgUnits(pageable, false));
        model.addAttribute("showOwner", false);
        return "allOrgUnits";
    }

    @RequestMapping(value = "orgUnit/add", method = RequestMethod.GET)
    public String addOrgUnit(Model model) {
        model.addAttribute("descriptionObj", new DescriptionDTO());
        return "addOrgUnit";
    }

    @RequestMapping(value = "orgUnit/add", method = RequestMethod.POST)
    public String addOrgUnitPOST(@ModelAttribute("descriptionObj") @Valid DescriptionDTO description, BindingResult result) {

        if(result.hasErrors()){
            return "addOrgUnit";
        }

        teacherService.addOrgUnit(description.getDescription());
        return "redirect:/teacher/orgUnit/all";
    }

    @RequestMapping(value = "orgUnit/delete/{orgUnitId}", method = RequestMethod.GET)
    public String deleteOrgUnitById(@PathVariable("orgUnitId") String orgUnitId) {

        orgUnitService.deleteOrgUnitById(orgUnitId);

        return "redirect:/teacher/orgUnit/all";
    }

    @RequestMapping(value = "orgUnit/view/{orgUnitId}", method = RequestMethod.GET)
    public String getAllUsersForOrgUnitId(@PathVariable("orgUnitId") String orgUnitId, Model model, Pageable pageable) {

        model.addAttribute("users", userService.getAllUsersForOrgUnitId(orgUnitId, pageable));

        return "allUsers";
    }

    @RequestMapping(value = "/testSpecs", method = RequestMethod.GET)
    public String editTestSpecs(Model model) {

        model.addAttribute("testSpecs", testUtilService.getTestSpecs());
        model.addAttribute("testSpecsDTO", new TestSpecsDTO());

        return "testSpecs";
    }

    @RequestMapping(value = "/testSpecs", method = RequestMethod.POST)
    public String editTestSpecsPOST(@ModelAttribute("testSpecsDTO") @Valid TestSpecsDTO testSpecsDTO, BindingResult result,
                                    Model model) {

        if(result.hasErrors()){
            model.addAttribute("testSpecs", testUtilService.getTestSpecs());
            return "testSpecs";
        }

        testUtilService.addTimeAndAmount(testSpecsDTO.getValueOne(), testSpecsDTO.getValueTwo());
        if(testSpecsDTO.getFile() != null)
            testUtilService.addDefaultPicture(testSpecsDTO.getFile());

        return "redirect:/teacher/testSpecs";
    }

    @RequestMapping(value = "/testSpecs/delete", method = RequestMethod.GET)
    public String deleteTestSpecsValue(@RequestParam(name = "time", required = false, defaultValue = "none") String time,
                                       @RequestParam(name = "amount", required = false, defaultValue = "none") String amount) {

        testUtilService.removeTimeAndAmount(time, amount);

        return "redirect:/teacher/testSpecs";
    }

    @RequestMapping(value = "/test/all", method = RequestMethod.GET)
    public String allTestForTeacher(Model model, Pageable pageable) {

        model.addAttribute("showUser", true);
        model.addAttribute("tests", testService.getAllTestForTeacher(pageable));

        return "allTests";
    }
}
