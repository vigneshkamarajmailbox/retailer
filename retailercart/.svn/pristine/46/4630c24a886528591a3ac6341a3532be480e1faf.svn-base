package com.botree.sfaweb.controller;

import com.botree.common.constants.StringConstants;
import com.botree.common.model.DownloadModel;
import com.botree.common.model.UploadModel;
import com.botree.common.model.WidgetModel;
import com.botree.common.util.Function;
import com.botree.interdbentity.model.AppUserModel;
import com.botree.interdbentity.model.UploadStatus;
import com.botree.sfaweb.exception.SessionExpiredException;
import com.botree.sfaweb.exception.UnAuthorizedException;
import com.botree.sfaweb.service.MasterService;
import com.botree.sfaweb.service.SchemeService;
import com.botree.sfaweb.service.TransactionService;
import com.botree.sfaweb.util.RequestValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URI;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * SFAWebServiceController to get all the request for sfaweb data.
 * @author vinodkumar.a
 */
@RestController
@RequestMapping(value = "/sfaweb/controller")
public class SFAWebServiceController {

    /** LOG. */
    private static final Logger LOG = LoggerFactory.getLogger(SFAWebServiceController.class);

    /** requestValidator. */
    private final RequestValidator requestValidator;

    /** httpServletRequest. */
    private final HttpServletRequest httpServletRequest;

    /** masterService. */
    private final MasterService masterService;

    /** schemeService. */
    private final SchemeService schemeService;

    /** transactionService. */
    private final TransactionService transactionService;

    /**
     * Constructor Method.
     * @param requestValidatorIn   requestValidatorIn
     * @param httpServletRequestIn httpServletRequestIn
     * @param masterServiceIn      masterServiceIn
     * @param schemeServiceIn      schemeServiceIn
     * @param transactionServiceIn transactionServiceIn
     */
    public SFAWebServiceController(final RequestValidator requestValidatorIn,
                                   final HttpServletRequest httpServletRequestIn,
                                   final MasterService masterServiceIn,
                                   final SchemeService schemeServiceIn,
                                   final TransactionService transactionServiceIn) {
        this.requestValidator = requestValidatorIn;
        this.httpServletRequest = httpServletRequestIn;
        this.masterService = masterServiceIn;
        this.schemeService = schemeServiceIn;
        this.transactionService = transactionServiceIn;
    }

    /**
     * Method to fetch the download data for the user.
     * @param user user
     * @return download data
     */
    @PostMapping("/download")
    public DownloadModel download(@RequestBody final AppUserModel user) {
        LOG.info("download info :: {}", user.getLoginCode());
        ResponseEntity<?> response = requestValidator.preHandle(httpServletRequest);
        if (response.getStatusCode() == HttpStatus.OK) {
            return masterService.download(user);
        } else if (response.getStatusCode() == HttpStatus.GONE) {
            throw new SessionExpiredException(Objects.requireNonNull(response.getBody()).toString());
        } else {
            throw new UnAuthorizedException(Objects.requireNonNull(response.getBody()).toString());
        }
    }

    /**
     * Method to fetch the download scheme data for the user.
     * @param user user
     * @return download data
     */
    @PostMapping("/downloadscheme")
    public DownloadModel downloadScheme(@RequestBody final AppUserModel user) {
        LOG.info("download scheme info :: {}", user.getLoginCode());
        ResponseEntity<?> response = requestValidator.preHandle(httpServletRequest);
        if (response.getStatusCode() == HttpStatus.OK) {
            return schemeService.downloadScheme(user);
        } else if (response.getStatusCode() == HttpStatus.GONE) {
            throw new SessionExpiredException(Objects.requireNonNull(response.getBody()).toString());
        } else {
            throw new UnAuthorizedException(Objects.requireNonNull(response.getBody()).toString());
        }
    }

    /**
     * Method to upload transaction data.
     * @param model upload data
     * @return UploadStatus
     */
    @PostMapping("/upload")
    public UploadStatus upload(@RequestBody final UploadModel model) {
        LOG.info("upload info :: {}", new Date());
        ResponseEntity<?> response = requestValidator.preHandle(httpServletRequest);
        if (response.getStatusCode() == HttpStatus.OK) {
            return transactionService.upload(model);
        } else if (response.getStatusCode() == HttpStatus.GONE) {
            throw new SessionExpiredException(Objects.requireNonNull(response.getBody()).toString());
        } else {
            throw new UnAuthorizedException(Objects.requireNonNull(response.getBody()).toString());
        }
    }

    /**
     * Method to fetch screen access data for user.
     * @param user user
     * @return download data
     */
    @PostMapping("/fetchscreenaccess")
    public DownloadModel fetchScreenAccess(@RequestBody final AppUserModel user) {
        LOG.info("download screen access  info :: {}", user.getLoginCode());
        ResponseEntity<?> response = requestValidator.preHandle(httpServletRequest);
        if (response.getStatusCode() == HttpStatus.OK) {
            return masterService.fetchScreenAccess(user);
        } else if (response.getStatusCode() == HttpStatus.GONE) {
            throw new SessionExpiredException(Objects.requireNonNull(response.getBody()).toString());
        } else {
            throw new UnAuthorizedException(Objects.requireNonNull(response.getBody()).toString());
        }
    }

    /**
     * Method to fetch image.
     * @param id   file
     * @param type type
     * @return image response
     * @throws IOException IOException
     */
    @GetMapping("/getproductimage/{id}/{type}")
    public ResponseEntity<byte[]> getProductImage(@PathVariable final String id, @PathVariable final String type)
            throws IOException {
        LOG.info("get product image info :: {} {} ", id, type);
        var uri = masterService.getProductImage(id);
        return sendImageResponse(uri);
    }

    /**
     * Method to fetch image.
     * @param id   file
     * @param type type
     * @return image response
     * @throws IOException IOException
     */
    @GetMapping("/getnotification/{id}/{type}")
    public ResponseEntity<Resource> getNotificationFile(@PathVariable final String id, @PathVariable final String type)
            throws IOException {
        LOG.info("get notification info :: {} ", id);
        Resource file = new UrlResource(masterService.getNotificationFile(id, type));
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                StringConstants.ATTACHMENT + file.getFilename() + "\"").body(file);
    }

    /**
     * Method to fetch image.
     * @param id   file
     * @param type type
     * @return image response
     * @throws IOException IOException
     */
    @GetMapping("/getcompanyimage/{id}/{type}")
    public ResponseEntity<byte[]> getCompanyImage(@PathVariable final String id, @PathVariable final String type)
            throws IOException {
        LOG.info("get company image info :: {} {} ", id, type);
        var uri = masterService.getCompanyImage(id);
        return sendImageResponse(uri);
    }

    /**
     * Method to fetch banner image.
     * @param id   file
     * @param type type
     * @return image response
     * @throws IOException IOException
     */
    @GetMapping("/getbannerimage/{id}/{type}")
    public ResponseEntity<byte[]> getBannerImage(@PathVariable final String id, @PathVariable final String type)
            throws IOException {
        LOG.info("get banner image info :: {} {} ", id, type);
        var uri = masterService.getBannerImage(id);
        return sendImageResponse(uri);
    }

    /**
     * Method to set image response.
     * @param uri uri
     * @return response entity
     * @throws IOException io exception
     */
    private ResponseEntity<byte[]> sendImageResponse(final URI uri) throws IOException {
        if (uri != null) {
            var headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType(Function.convertMimeType(uri)));
            return new ResponseEntity<>(Function.convertFileToByteArray(uri), headers, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    /**
     * Method to fetch the download data for the user.
     * @param languageCode languageCode
     * @param loginCode    loginCode
     * @return download data
     */
    @GetMapping("/download/{languageCode}/{loginCode}")
    public DownloadModel download(@PathVariable final String languageCode,
                                  @PathVariable final String loginCode) {
        LOG.info("download info :: {}", loginCode);
        requestValidator.validateRequest(httpServletRequest);
        ResponseEntity<?> response = requestValidator.preHandle(httpServletRequest);
        if (response.getStatusCode() == HttpStatus.OK) {
            return masterService.downloadMaster(convertUser(languageCode, loginCode));
        } else if (response.getStatusCode() == HttpStatus.GONE) {
            throw new SessionExpiredException(Objects.requireNonNull(response.getBody()).toString());
        } else {
            throw new UnAuthorizedException(Objects.requireNonNull(response.getBody()).toString());
        }
    }

    /**
     * Method to fetch the download scheme data for the user.
     * @param languageCode languageCode
     * @param loginCode    loginCode
     * @return download data
     */
    @GetMapping("/downloadscheme/{languageCode}/{loginCode}")
    public DownloadModel downloadScheme(@PathVariable final String languageCode,
                                        @PathVariable final String loginCode) {
        LOG.info("download scheme info :: {}", loginCode);
        requestValidator.validateRequest(httpServletRequest);
        ResponseEntity<?> response = requestValidator.preHandle(httpServletRequest);
        if (response.getStatusCode() == HttpStatus.OK) {
            return schemeService.downloadSchemeMaster(convertUser(languageCode, loginCode));
        } else if (response.getStatusCode() == HttpStatus.GONE) {
            throw new SessionExpiredException(Objects.requireNonNull(response.getBody()).toString());
        } else {
            throw new UnAuthorizedException(Objects.requireNonNull(response.getBody()).toString());
        }
    }

    /**
     * Method to fetch screen access data for user.
     * @param languageCode languageCode
     * @param loginCode    loginCode
     * @return download data
     */
    @GetMapping("/downloadscreenaccess/{languageCode}/{loginCode}")
    public DownloadModel fetchScreenAccess(@PathVariable final String languageCode,
                                           @PathVariable final String loginCode) {
        LOG.info("download screen access  info :: {}", loginCode);
        requestValidator.validateRequest(httpServletRequest);
        ResponseEntity<?> response = requestValidator.preHandle(httpServletRequest);
        if (response.getStatusCode() == HttpStatus.OK) {
            return masterService.fetchScreenAccess(convertUser(languageCode, loginCode));
        } else if (response.getStatusCode() == HttpStatus.GONE) {
            throw new SessionExpiredException(Objects.requireNonNull(response.getBody()).toString());
        } else {
            throw new UnAuthorizedException(Objects.requireNonNull(response.getBody()).toString());
        }
    }

    /**
     * Method to fetch notification data for user.
     * @param languageCode languageCode
     * @param loginCode    loginCode
     * @return download data
     */
    @GetMapping("/downloadnotification/{languageCode}/{loginCode}")
    public DownloadModel fetchNotification(@PathVariable final String languageCode,
                                           @PathVariable final String loginCode) {
        LOG.info("fetch notification info :: {}", loginCode);
        requestValidator.validateRequest(httpServletRequest);
        ResponseEntity<?> response = requestValidator.preHandle(httpServletRequest);
        if (response.getStatusCode() == HttpStatus.OK) {
            return masterService.fetchNotification(convertUser(languageCode, loginCode));
        } else if (response.getStatusCode() == HttpStatus.GONE) {
            throw new SessionExpiredException(Objects.requireNonNull(response.getBody()).toString());
        } else {
            throw new UnAuthorizedException(Objects.requireNonNull(response.getBody()).toString());
        }
    }

    /**
     * Method to fetch previous order data for user.
     * @param languageCode languageCode
     * @param loginCode    loginCode
     * @return download data
     */
    @GetMapping("/downloadpreviousorder/{languageCode}/{loginCode}")
    public DownloadModel fetchPreviousOrderReport(@PathVariable final String languageCode,
                                                  @PathVariable final String loginCode) {
        LOG.info("download previous order info :: {}", loginCode);
        requestValidator.validateRequest(httpServletRequest);
        ResponseEntity<?> response = requestValidator.preHandle(httpServletRequest);
        if (response.getStatusCode() == HttpStatus.OK) {
            return masterService.fetchPreviousOrderReport(convertUser(languageCode, loginCode));
        } else if (response.getStatusCode() == HttpStatus.GONE) {
            throw new SessionExpiredException(Objects.requireNonNull(response.getBody()).toString());
        } else {
            throw new UnAuthorizedException(Objects.requireNonNull(response.getBody()).toString());
        }
    }

    /**
     * Method to fetch distributor data for user.
     * @param languageCode languageCode
     * @param loginCode    loginCode
     * @return download data
     */
    @GetMapping("/downloaddistributor/{languageCode}/{loginCode}")
    public DownloadModel fetchDistributorInfo(@PathVariable final String languageCode,
                                              @PathVariable final String loginCode) {
        LOG.info("fetch distributor info :: {}", loginCode);
        requestValidator.validateRequest(httpServletRequest);
        ResponseEntity<?> response = requestValidator.preHandle(httpServletRequest);
        if (response.getStatusCode() == HttpStatus.OK) {
            return masterService.fetchDistributorInfo(convertUser(languageCode, loginCode));
        } else if (response.getStatusCode() == HttpStatus.GONE) {
            throw new SessionExpiredException(Objects.requireNonNull(response.getBody()).toString());
        } else {
            throw new UnAuthorizedException(Objects.requireNonNull(response.getBody()).toString());
        }
    }

    /**
     * Method to download product  image.
     * @param id file
     * @return image response
     * @throws IOException IOException
     */
    @GetMapping("/downloadproductimage/{id}")
    public ResponseEntity<byte[]> downloadProductImage(@PathVariable final String id) throws IOException {
        LOG.info("download product image info :: {} ", id);
        var uri = masterService.getProductImage(id);
        return sendImageResponse(uri);
    }

    /**
     * Method to download company image.
     * @param id file
     * @return image response
     * @throws IOException IOException
     */
    @GetMapping("/downloadcompanyimage/{id}")
    public ResponseEntity<byte[]> downloadCompanyImage(@PathVariable final String id) throws IOException {
        LOG.info("download company image info :: {} ", id);
        var uri = masterService.getCompanyImage(id);
        return sendImageResponse(uri);
    }

    /**
     * Method to download banner image.
     * @param bannerType bannerType
     * @param id         file
     * @return image response
     * @throws IOException IOException
     */
    @GetMapping("/downloadbannerimage/{bannerType}/{id}")
    public ResponseEntity<byte[]> downloadBannerImage(@PathVariable final String bannerType,
                                                      @PathVariable final String id) throws IOException {
        LOG.info("download banner info :: {} ", id);
        if (StringConstants.CONST_BANNER_TYPE_IMAGE.equalsIgnoreCase(bannerType)) {
            var uri = masterService.getBannerImage(id);
            return sendImageResponse(uri);
        } else {
            final var data = masterService.getBannerVideo(id);
            if (data != null && data.length > 0) {
                var headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
                return new ResponseEntity<>(data, headers, HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Method to convert user model.
     * @param languageCode languageCode
     * @param loginCode    loginCode
     * @return app user model
     */
    private AppUserModel convertUser(final String languageCode, final String loginCode) {
        var user = new AppUserModel();
        user.setLoginCode(loginCode);
        user.setLanguageCode(languageCode);
        user.setEnableCompress(Boolean.FALSE);
        return user;
    }

    /**
     * Method to fetch profile data for user.
     * @param languageCode languageCode
     * @param loginCode    loginCode
     * @return download data
     */
    @GetMapping("/fetchprofile/{languageCode}/{loginCode}")
    public DownloadModel fetchProfileInfo(@PathVariable final String languageCode,
                                          @PathVariable final String loginCode) {
        LOG.info("fetch profile info :: {}", loginCode);
        requestValidator.validateRequest(httpServletRequest);
        ResponseEntity<?> response = requestValidator.preHandle(httpServletRequest);
        if (response.getStatusCode() == HttpStatus.OK) {
            return masterService.fetchProfileInfo(convertUser(languageCode, loginCode));
        } else if (response.getStatusCode() == HttpStatus.GONE) {
            throw new SessionExpiredException(Objects.requireNonNull(response.getBody()).toString());
        } else {
            throw new UnAuthorizedException(Objects.requireNonNull(response.getBody()).toString());
        }
    }

    /**
     * Method to check success status.
     * @return test
     */
    @GetMapping("/index")
    public final String index() {
        LOG.info("index :: ");
        return "success";
    }

    /**
     * Method to fetch profile data for user.
     * @param languageCode languageCode
     * @param customerCode loginCode
     * @return download data
     */
    @GetMapping("/fetch/dashboard/{languageCode}/{customerCode}")
    public List<WidgetModel> fetchDashboardWidget(@PathVariable final String languageCode,
                                                  @PathVariable final String customerCode)
            throws IllegalAccessException {
        LOG.info("fetch dashboard info :: {}", customerCode);
        requestValidator.validateRequest(httpServletRequest);
        ResponseEntity<?> response = requestValidator.preHandle(httpServletRequest);
        if (response.getStatusCode() == HttpStatus.OK) {
            return masterService.fetchDashboardWidget(convertUser(languageCode, customerCode));
        } else if (response.getStatusCode() == HttpStatus.GONE) {
            throw new SessionExpiredException(Objects.requireNonNull(response.getBody()).toString());
        } else {
            throw new UnAuthorizedException(Objects.requireNonNull(response.getBody()).toString());
        }
    }
}
