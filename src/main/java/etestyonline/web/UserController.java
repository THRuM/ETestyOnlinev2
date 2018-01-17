package etestyonline.web;

import etestyonline.model.util.DescriptionDTO;
import etestyonline.model.util.PasswordChangeDTO;
import etestyonline.service.AuthenticationWrapper;
import etestyonline.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.method.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationWrapper authenticationWrapper;

    @Autowired
    private MessageSource messageSource;

    @RequestMapping(value = "principal/all", method = RequestMethod.GET)
    public String getAllTeachers(Model model, Pageable pageable) {

        model.addAttribute("teachers", userService.getAllTeachers(pageable));

        return "allPrincipals";
    }

    @RequestMapping(value = "principal/{principalId}", method = RequestMethod.GET)
    public String claimPrincipal(@PathVariable("principalId") String principalId, Model model) {

        String teacherEmail = userService.claimPrincipalById(principalId);

        model.addAttribute("header_message", messageSource.getMessage("claim.sent", null, LocaleContextHolder.getLocale()));
        model.addAttribute("message", messageSource.getMessage("principal.claim.sent", null, LocaleContextHolder.getLocale()) + " " + teacherEmail);

        return "confirm";
    }

    @RequestMapping(value = "me", method = RequestMethod.GET)
    public String getMyProfile(Model model) {

        model.addAttribute("user", authenticationWrapper.getAuthenticatetUser());

        return "userProfile";
    }

    @RequestMapping(value = "passwordChange", method = RequestMethod.GET)
    public String changePassword(Model model) {
        model.addAttribute("changePassword", new PasswordChangeDTO());
        return "passwordChange";
    }

    @RequestMapping(value = "passwordChange", method = RequestMethod.POST)
    public String changePasswordPOST(@ModelAttribute("changePassword") @Valid PasswordChangeDTO passwordChangeDTO,
                                     BindingResult result, Model model) {

        if(result.hasErrors()){
            return "passwordChange";
        }

        boolean isSuccess = userService.changePasswordForUser(passwordChangeDTO.getOldPassword(), passwordChangeDTO.getPassword());

        if(!isSuccess) {
            result.reject("oldPassword", "password.oldNotMatch");
            return "passwordChange";
        }

        model.addAttribute("header_message", messageSource.getMessage("password.change.header", null, LocaleContextHolder.getLocale()));
        model.addAttribute("message", messageSource.getMessage("password.change.confirm", null, LocaleContextHolder.getLocale()));

        return "confirm";
    }

    @RequestMapping(value = "promote", method = RequestMethod.GET)
    public String claimPromoteToTeacher(Model model) {
        model.addAttribute("reasonObj", new DescriptionDTO());
        return "claimPromote";
    }

    @RequestMapping(value = "promote", method = RequestMethod.POST)
    public String claimPromoteToTeacherPOST(Model model, @ModelAttribute("reasonObj") @Valid DescriptionDTO desc, BindingResult result) {

        if(result.hasErrors()){
            return "claimPromote";
        }

        userService.claimPromoteToTeacher(desc.getDescription());

        model.addAttribute("header_message", messageSource.getMessage("claim.sent", null, LocaleContextHolder.getLocale()));
        model.addAttribute("message", messageSource.getMessage("admin.claim.sent", null, LocaleContextHolder.getLocale()));

        return "confirm";
    }
}
