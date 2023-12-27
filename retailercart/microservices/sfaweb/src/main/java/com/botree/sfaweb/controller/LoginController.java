package com.botree.sfaweb.controller;

import com.botree.interdbentity.model.AppUserModel;
import com.botree.sfaweb.service.MessageService;
import com.botree.sfaweb.service.UserService;
import com.botree.sfaweb.util.RequestValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * LoginController to get all the request for sfaweb data.
 * @author vinodkumar.a
 */
@RestController
@RequestMapping(value = "/sfaweb/login")
public class LoginController {

    /** LOG. */
    private static final Logger LOG = LoggerFactory.getLogger(LoginController.class);

    /** userService. */
    private final UserService userService;

    /** messageService. */
    private final MessageService messageService;

    /** requestValidator. */
    private final RequestValidator requestValidator;

    /** httpServletRequest. */
    private final HttpServletRequest httpServletRequest;

    /**
     * Constructor Method.
     * @param userServiceIn        userServiceIn
     * @param messageServiceIn     messageServiceIn
     * @param requestValidatorIn   requestValidatorIn
     * @param httpServletRequestIn httpServletRequestIn
     */
    public LoginController(final UserService userServiceIn, final MessageService messageServiceIn,
                           final RequestValidator requestValidatorIn, final HttpServletRequest httpServletRequestIn) {
        this.userService = userServiceIn;
        this.messageService = messageServiceIn;
        this.requestValidator = requestValidatorIn;
        this.httpServletRequest = httpServletRequestIn;
    }

    /**
     * Method to get the user authentication.
     * @param user userform
     * @return user
     */
    @PostMapping("/authenticate")
    public AppUserModel login(@RequestBody final AppUserModel user) {
        LOG.info("login :: {}", user.getLoginCode());
        return userService.validateLogin(user);
    }

    /**
     * Method to get the user authentication.
     * @param user userform
     * @return user
     */
    @PostMapping("/logOut")
    public AppUserModel logOut(@RequestBody final AppUserModel user) {
        LOG.info("log out :: {}", user.getLoginCode());
        return userService.userLogout(user);
    }

    /**
     * Method to update the user password.
     * @param user user
     */
    @PostMapping("/changepassword")
    public void changePassword(@RequestBody final AppUserModel user) {
        LOG.info("change password :: {}", user.getLoginCode());
        requestValidator.validateRequest(httpServletRequest);
        userService.changePassword(user);
    }

    /**
     * Method to send otp for the user.
     * @param user user
     * @return message status
     */
    @PostMapping("/sendotp")
    public Object sendOtp(@RequestBody final AppUserModel user) {
        LOG.info("send otp :: {}", user.getLoginCode());
        requestValidator.validateRequest(httpServletRequest);
        return messageService.sendOtp(user);
    }
}
