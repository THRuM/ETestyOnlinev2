package etestyonline.service.impl;

import etestyonline.model.PasswordResetToken;
import etestyonline.model.User;
import etestyonline.model.VerificationToken;
import etestyonline.service.EmailService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSenderImpl mailSender;

    @Autowired
    private MessageSource messageSource;

    private static final String HEADER = "<h1>ETestyOnline</h1>";
    private static final String LOCATION = "https://e-testyonline.herokuapp.com/";

    private static Logger logger = LogManager.getLogger(EmailServiceImpl.class);

    @Override
    public boolean sendVerificationEmail(VerificationToken token) {
        MimeMessage mimeMessage = new MimeMessage(mailSender.getSession());
        try {
            mimeMessage.setSubject(messageSource.getMessage("register.email.subject", null, LocaleContextHolder.getLocale()), "utf-8");
            mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(token.getUser().getEmail() ,false));
            mimeMessage.setSentDate(new Date());

            String link = HEADER + "<a href=\"" + LOCATION + "registerConfirm?token=" + token.getToken() + "\">" +
                    messageSource.getMessage("register.email.link", null, LocaleContextHolder.getLocale())
                    + "</a>";

            mimeMessage.setContent(link, "text/html; charset=utf-8");
        } catch (MessagingException e) {
          logger.error("Error during Verification message creation. Message for " + token.getUser().getEmail());
            return false;
        }

        try {
            mailSender.send(mimeMessage);
        } catch (MailException e){
            logger.error("Can't send Verification message to " + token.getUser().getEmail());
            return false;
        }

        logger.info("Verification email send successfully to " + token.getUser().getEmail());

//        System.out.println("Token: " + token.getToken());

        return true;
    }

    @Override
    public boolean sendPasswordResetEmail(PasswordResetToken token) {
        MimeMessage mimeMessage = new MimeMessage(mailSender.getSession());

        try {
            mimeMessage.setSubject(messageSource.getMessage("reset.email.subject", null, LocaleContextHolder.getLocale()), "utf-8");
            mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(token.getUser().getEmail() ,false));
            mimeMessage.setSentDate(new Date());

            String link = HEADER + "<a href=\"" + LOCATION + "passwordReset?token=" + token.getToken() + "\">" +
                    messageSource.getMessage("reset.email.link", null, LocaleContextHolder.getLocale())
                    + "</a>";

            mimeMessage.setContent(link, "text/html; charset=utf-8");
        } catch (MessagingException e) {
            logger.error("Error during Password Reset message creation. Message for " + token.getUser().getEmail());
            return false;
        }

        try {
            mailSender.send(mimeMessage);
        } catch (MailException e){
            logger.error("Can't send Password Reset message to " + token.getUser().getEmail());
            return false;
        }

        logger.info("Password Reset email send successfully to " + token.getUser().getEmail());

//        System.out.println("Token: " + token.getToken());

        return true;
    }

    @Override
    public boolean sendPasswordChangeEmail(User user) {

        MimeMessage mimeMessage = new MimeMessage(mailSender.getSession());

        try {
            mimeMessage.setSubject(messageSource.getMessage("password.email.subject", null, LocaleContextHolder.getLocale()), "utf-8");
            mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(user.getEmail() ,false));
            mimeMessage.setSentDate(new Date());

            String link = HEADER + "<h3>" + messageSource.getMessage("password.email.info", null, LocaleContextHolder.getLocale()) + "</h3>";

            mimeMessage.setContent(link, "text/html; charset=utf-8");
        } catch (MessagingException e) {
            logger.error("Error during Password Change message creation. Message for " + user.getEmail());
            return false;
        }

        try {
            mailSender.send(mimeMessage);
        } catch (MailException e){
            logger.error("Can't send Password Change message to " + user.getEmail());
            return false;
        }

        logger.info("Password Change email send successfully to " + user.getEmail());

        return true;
    }
}
