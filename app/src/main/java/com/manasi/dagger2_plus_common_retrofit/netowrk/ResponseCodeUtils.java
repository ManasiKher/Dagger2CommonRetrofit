package com.manasi.dagger2_plus_common_retrofit.netowrk;

/**
 * Created by leftrightmind on 19/07/17.
 */

public class ResponseCodeUtils {

    public static final String R_CODE_200 = "OK";
    public static final String R_CODE_204 = "No Content";
    public static final String R_CODE_400 = "Bad Request";
    public static final String R_CODE_401 = "Unauthorised";
    public static final String R_CODE_403 = "Forbidden";
    public static final String R_CODE_404 = "Server down - Not Found";
    public static final String R_CODE_405 = "Method Not Allowed";
    public static final String R_CODE_500 = "Internal Server Error";
    public static final String R_CODE_502 = "Bad Gateway";
    public static final String R_CODE_594 = "Gateway Timeout";
    public static final String R_CODE_598 = "Network Read Timeout";
    public static final String R_CODE_UNKNOWN = "Unknown error occured";

    public static final int R_SESSION_TIME_OUT_SERVER_CODE = 400;
    public static final int R_NO_TOKEN_SERVER_CODE = 401;
    public static final int R_SERVER_FAILED_SERVER_CODE = 402;
    public static final int R_VALIDTION_ERROR_SERVER_CODE = 403;
    public static final int R_API_NOT_FOUND_SERVER_CODE = 404;
    public static final int R_SERVER_MAINTAINANCE_SERVER_CODE = 405;
    public static final int R_APP_UPGRADE_SERVER_CODE = 406;

    public static final String TYPE_SESSION_TIME_OUT_MSG = "TYPE_SESSION_TIME_OUT";
    public static final String TYPE_NO_TOKEN_MSG = "TYPE_NO_TOKEN_MSG";

    public static final String TYPE_SERVER_MAINTAINANCE_MSG = "TYPE_SERVER_MAINTAINANCE";
    public static final String TYPE_UPDATE_APP_MSG = "TYPE_UPDATE_APP";
    public static final String SERVER_STATUS_SUCCESS = "SUCCESS";


}
