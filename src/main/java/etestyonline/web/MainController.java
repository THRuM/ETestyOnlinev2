package etestyonline.web;

import etestyonline.model.util.PasswordChangeDTO;
import etestyonline.model.util.UserDTO;
import etestyonline.service.TestService;
import etestyonline.service.UserService;
import etestyonline.service.exceptions.BadEmailException;
import etestyonline.service.exceptions.TokenExistException;
import etestyonline.service.exceptions.UserAlreadyExistsException;
import etestyonline.service.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

@Controller
public class MainController {

    @Autowired
    private UserService userService;

    @Autowired
    private TestService testService;

    @Autowired
    private MessageSource messageSource;

    @RequestMapping(value = "/" , method = RequestMethod.GET)
    public String index(Model model){
        model.addAttribute("lastTests", testService.getLastFiveTestsForUser());
        return "index";
    }

    @RequestMapping("favicon.ico")
    public String favicon() {
        return "forward:/resources/images/favicon.ico";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(){
        return "login";
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String register(Model model){
        model.addAttribute("user", new UserDTO());
        return "register";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String registerPOST(@ModelAttribute("user") @Valid  UserDTO userDTO, BindingResult result, Model model){

        if(result.hasErrors()){
           return "register";
        }

        try{
            userService.registerNewUser(userDTO);
        } catch (UserAlreadyExistsException | BadEmailException e){
            result.reject("User.register.problem");
            return "register";
        }

        model.addAttribute("header_message", messageSource.getMessage("register.title", null, LocaleContextHolder.getLocale()));
        model.addAttribute("message", messageSource.getMessage("register.email.confirm", null, LocaleContextHolder.getLocale()));

        return "confirm";
    }

    @RequestMapping(value = "/registerConfirm", method = RequestMethod.GET)
    public String registerConfirm(@RequestParam("token") String token, Model model){

        userService.confirmRegistration(token);

        model.addAttribute("header_message", messageSource.getMessage("register.title", null, LocaleContextHolder.getLocale()));
        model.addAttribute("message", messageSource.getMessage("register.success", null, LocaleContextHolder.getLocale()));

        return "confirm";
    }

    @RequestMapping(value = "/passwordResetRequest", method = RequestMethod.GET)
    public String passwordResetRequest() {
        return "passwordResetRequest";
    }

    @RequestMapping(value = "/passwordResetRequest", method = RequestMethod.POST)
    public String passwordResetRequestPOST(@ModelAttribute("email") String email, BindingResult result, Model model) {

        try {
            userService.createPasswordResetTokenForUser(email);
        } catch (UserNotFoundException | TokenExistException | BadEmailException e){
            result.reject("PasswordReset.problem");
            return "passwordResetRequest";
        }

        model.addAttribute("header_message", messageSource.getMessage("reset.header", null, LocaleContextHolder.getLocale()));
        model.addAttribute("message", messageSource.getMessage("reset.confirm", null, LocaleContextHolder.getLocale()));

        return "confirm";
    }

    @RequestMapping(value = "/passwordReset", method = RequestMethod.GET)
    public String passwordReset(@RequestParam("token") String token, Model model) {

        boolean isValid = userService.isPasswordTokenValid(token);

        model.addAttribute("isValid", isValid);

        if(isValid)
            model.addAttribute("changePassword", new PasswordChangeDTO());

        return "passwordReset";
    }

    @RequestMapping(value = "/passwordReset", method = RequestMethod.POST)
    public String passwordResetPOST(@RequestParam("token") String token,
                                    @ModelAttribute("changePassword") @Valid PasswordChangeDTO passwordChangeDTO,
                                    BindingResult result, Model model) {

        boolean isValid = userService.isPasswordTokenValid(token);

        model.addAttribute("isValid", isValid);

        if(result.hasErrors() || !isValid){
            return "passwordReset";
        }

        userService.changePasswordForToken(token, passwordChangeDTO.getPassword());

        model.addAttribute("header_message", messageSource.getMessage("reset.header", null, LocaleContextHolder.getLocale()));
        model.addAttribute("message", messageSource.getMessage("reset.changed", null, LocaleContextHolder.getLocale()));

        return "confirm";
    }
}
