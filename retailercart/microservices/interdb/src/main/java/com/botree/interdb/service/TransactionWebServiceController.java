package com.botree.interdb.service;

import com.botree.interdbentity.model.AbstractEntity;
import com.botree.interdbentity.model.TransactionModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * TransactionWebServiceController controller to fetch the transaction data and
 * update the status.
 * @author vinodkumar.a
 */
@RestController
@RequestMapping(value = "/interdb/transaction")
public class TransactionWebServiceController {

    /** LOG. */
    private static final Logger LOG = LoggerFactory.getLogger(TransactionWebServiceController.class);

    /** service. */
    private final TransactionService service;

    /** loginService. */
    private final LoginService loginService;

    /** tokenStore. */
    private final TokenStore tokenStore;

    /**
     * Constructor Method.
     * @param serviceIn      serviceIn
     * @param loginServiceIn loginServiceIn
     * @param tokenStoreIn   tokenStoreIn
     */
    public TransactionWebServiceController(final TransactionService serviceIn, final LoginService loginServiceIn,
                                           final @Qualifier("jdbcTokenStore") TokenStore tokenStoreIn) {
        this.service = serviceIn;
        this.loginService = loginServiceIn;
        this.tokenStore = tokenStoreIn;
    }

    /**
     * Method to fetch the transaction data.
     * @param model model
     * @return order booking list
     */
    @PostMapping("/fetchdata")
    public List<AbstractEntity> fetchData(@RequestBody final TransactionModel model) {
        LOG.info("fetch data :: {}", model.getInterDBProcess());
        return service.fetchData(model);
    }

    /**
     * Method to update the transaction success status.
     * @param model model
     */
    @PostMapping("/updatetransaction")
    public final void updateTransaction(@RequestBody final TransactionModel model) {
        LOG.info("update transaction :: {}", model.getInterDBProcess());
        service.updateTransactionStatus(model);
    }

    /**
     * Method to upload the action data to be performed in server.
     * @param model model
     */
    @PostMapping("/actionapi")
    public final void actionAPI(@RequestBody final TransactionModel model) {
        if (model.getActionList() != null && !model.getActionList().isEmpty()) {
            LOG.info("action api :: {}", model.getActionList().size());
            service.saveActionAPI(model);
        } else {
            LOG.info("action api empty value");
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
     * This method is used to logout from oauth session.
     * @param request Report input details
     */
    @PostMapping(value = "/logout")
    public final void logout(final HttpServletRequest request) {
        loginService.logout(request.getUserPrincipal().getName());
        var authHeader = request.getHeader("Authorization");
        if (authHeader != null) {
            var tokenValue = authHeader.replace("Bearer", "").trim();
            var accessToken = tokenStore.readAccessToken(tokenValue);
            tokenStore.removeAccessToken(accessToken);
        }
    }

}
