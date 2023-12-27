package com.botree.sfaweb.controller;

import com.botree.common.exception.CommonExceptionHandler;
import com.botree.common.model.LoginReferralModel;
import com.botree.common.model.OrderReferralModel;
import com.botree.common.model.ResponseModel;
import com.botree.interdbentity.model.SalesmanEntity;
import com.botree.sfaweb.exception.SessionExpiredException;
import com.botree.sfaweb.exception.UnAuthorizedException;
import com.botree.sfaweb.service.ReferralService;
import com.botree.sfaweb.util.RequestValidator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;

/**
 * Referral controller to get all the referral related information.
 * @author Naveen kumar.r Emp Id (1261)
 */
@RestController
@RequestMapping(value = "/sfaweb/referral")
public class ReferralController {

    /** ReferralService. */
    private final ReferralService referralService;

    /** requestValidator. */
    private final RequestValidator requestValidator;


    /** httpServletRequest. */
    private final HttpServletRequest httpServletRequest;

    /**
     * Constructor Method.
     * @param referralServiceIn referralServiceIn
     * @param requestValidatorIn referralServiceIn
     * @param httpServletRequestIn referralServiceIn
     */
    public ReferralController(final ReferralService referralServiceIn,
                              final RequestValidator requestValidatorIn,
                              final HttpServletRequest httpServletRequestIn) {
        this.referralService = referralServiceIn;
        this.requestValidator = requestValidatorIn;
        this.httpServletRequest = httpServletRequestIn;
    }

    /**
     * This method used to get salesman list.
     * @param loginCode loginCode
     * @return salesmanList
     */
    @GetMapping("/salesman")
    public List<SalesmanEntity> fetchSalesman(final @RequestParam("loginCode") String loginCode) {
        ResponseEntity<?> response = requestValidator.preHandle(httpServletRequest);
        if (response.getStatusCode() == HttpStatus.OK) {
            return this.referralService.fetchSalesman(loginCode);
        } else if (response.getStatusCode() == HttpStatus.GONE) {
            throw new SessionExpiredException(Objects.requireNonNull(response.getBody()).toString());
        } else {
            throw new UnAuthorizedException(Objects.requireNonNull(response.getBody()).toString());
        }

    }

    /**
     * This method used to save login referral.
     * @param referralModel LoginReferralModel
     * @return responseModel
     */
    @PostMapping("/login")
    public ResponseEntity<ResponseModel> saveLoginReferral(final @RequestBody() LoginReferralModel referralModel) throws
            CommonExceptionHandler {
        ResponseEntity<?> response = requestValidator.preHandle(httpServletRequest);
        if (response.getStatusCode() == HttpStatus.OK) {
            return ResponseEntity.ok(this.referralService.saveLoginReferral(referralModel));
        } else if (response.getStatusCode() == HttpStatus.GONE) {
            throw new SessionExpiredException(Objects.requireNonNull(response.getBody()).toString());
        } else {
            throw new UnAuthorizedException(Objects.requireNonNull(response.getBody()).toString());
        }
    }

    /**
     * This method used to save order referral.
     * @param referralModel OrderReferralModel
     * @return responseModel
     */
    @PostMapping("/order")
    public ResponseModel saveOrderReferral(final @RequestBody() OrderReferralModel referralModel) {
        ResponseEntity<?> response = requestValidator.preHandle(httpServletRequest);
        if (response.getStatusCode() == HttpStatus.OK) {
            return this.referralService.saveOrderReferral(referralModel);
        } else if (response.getStatusCode() == HttpStatus.GONE) {
            throw new SessionExpiredException(Objects.requireNonNull(response.getBody()).toString());
        } else {
            throw new UnAuthorizedException(Objects.requireNonNull(response.getBody()).toString());
        }

    }

}
