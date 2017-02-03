package com.botree.botree911_client.utility;

/**
 * Created by bhavin on 1/2/17.
 */

public class Constant {

    public static int device_type = 0;

    public static String baseURL = "http://192.168.0.150:3000"; // Local
    public static String loginURL = baseURL + "/users/sign_in";
    public static String forgotPasswordURL = baseURL + "";
    public static String allProjectURL = baseURL + "/projects/list";
    public static String allTicketURL = baseURL + "/tickets/list";
    public static String createTicketURL = baseURL + "/tickets";
    public static String allStatusURL = baseURL + "/tickets/status_list";
}
