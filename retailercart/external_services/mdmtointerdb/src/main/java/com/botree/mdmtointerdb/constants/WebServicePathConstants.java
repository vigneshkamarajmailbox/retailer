package com.botree.mdmtointerdb.constants;

/**
 * Class contains the web service path constant value.
 * @author vinodkumar.a
 */
public final class WebServicePathConstants {

    /** REDIRECT_TO_CONTROLLER. */
    public static final String REDIRECT_TO_CONTROLLER = "/interdb/controller";

    /** REDIRECT_TO_TRANSACTION_FETCHDATA. */
    public static final String REDIRECT_TO_TRANSACTION_FETCHDATA = "/interdb/transaction/fetchdata";

    /** REDIRECT_TO_UPDATE_TRANSACTION_STATUS. */
    public static final String REDIRECT_TO_UPDATE_TRANSACTION_STATUS = "/interdb/transaction/updatetransaction";

    /** REDIRECT_TO_OAUTH_LOGIN. */
    public static final String REDIRECT_TO_OAUTH_LOGIN = "/oauth/token?";

    /** REDIRECT_TO_OAUTH_LOGOUT. */
    public static final String REDIRECT_TO_OAUTH_LOGOUT = "/interdb/transaction/logout";

    /** REDIRECT_TO_UPDATE_ACTIONAPI_STATUS. */
    public static final String REDIRECT_TO_UPDATE_ACTIONAPI_STATUS = "/interdb/transaction/actionapi";

    /**
     * Empty constructor.
     */
    private WebServicePathConstants() {

    }
}
