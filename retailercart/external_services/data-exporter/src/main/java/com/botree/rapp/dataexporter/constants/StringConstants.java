package com.botree.rapp.dataexporter.constants;

/**
 * This class is used to hold string constant values.
 * @author vinodkumar.a
 */
public final class StringConstants {

    /** FETCH_USER_LOGGING. */
    public static final String FETCH_USER_LOGGING = "FETCH_USER_LOGGING";
    /** SERVER_PARAM_1. */
    public static final String SERVER_PARAM_1 = "Reporting_Data,application=";
    /** LOGGER_PARAM_1. */
    public static final String LOGGER_PARAM_1 = ",server=";
    /** LOGGER_PARAM_2. */
    public static final String LOGGER_PARAM_2 = " User_Connected_To_Application=";
    /** LOGGER_PARAM_3. */
    public static final String LOGGER_PARAM_3 = ",User_Connected_To_Reporting=";
    /** LOGGER_PARAM_4. */
    public static final String LOGGER_PARAM_4 = ",User_Connected_To_Transaction=";
    /** QUERY_RS_1. */
    public static final String QUERY_RS_1 = "Server";
    /** QUERY_RS_2. */
    public static final String QUERY_RS_2 = "User_Connected_To_Application";
    /** QUERY_RS_3. */
    public static final String QUERY_RS_3 = "User_Connected_To_Reporting";
    /** QUERY_RS_4. */
    public static final String QUERY_RS_4 = "User_Connected_To_Transaction";

    /**
     * Default constructor.
     */
    private StringConstants() {
        throw new UnsupportedOperationException();
    }
}
