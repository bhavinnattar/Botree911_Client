package com.botree.botree911_client.utility;

import com.botree.botree911_client.model.Project;
import com.botree.botree911_client.model.ProjectOld;
import com.botree.botree911_client.model.Status;
import com.botree.botree911_client.model.Ticket;

import java.util.ArrayList;

/**
 * Created by bhavin on 1/2/17.
 */

public class Constant {

    public static int device_type = 0;
    public static String holder_type = "client";

    public static String baseURL = "http://192.168.0.86:3000"; // Local
//    public static String baseURL = "https://botree911.herokuapp.com"; // Live
    public static String loginURL = baseURL + "/users/sign_in";

    public static String forgotPasswordURL = baseURL + "/users/reset_password";
    public static String allProjectURL = baseURL + "/projects/list";
    public static String allTicketURL = baseURL + "/tickets/list";
    public static String createTicketURL = baseURL + "/tickets";
    public static String commentListTicketURL = "/ticket_comments";
    public static String addCommentTicketURL = "/add_comment";
    public static String historyListTicketURL = "/ticket_status_history";
    public static String editTicketURL = baseURL + "/tickets/";
    public static String allStatusURL = baseURL + "/tickets/status_list";
    public static String updateFCMURL = baseURL + "/users/reset_fcm_token";
    public static String notificationURL = baseURL + "/users/notification";

    public static ArrayList<ProjectOld> allProjectOlds = new ArrayList<ProjectOld>();
    public static ArrayList<ArrayList<Ticket>> allTickets = new ArrayList<ArrayList<Ticket>>();
    public static ArrayList<Project> allProject = new ArrayList<Project>();
    public static ArrayList<Status> allStatus = new ArrayList<Status>();
    public static Ticket selectedTicket = new Ticket();
    public static ProjectOld selectedProjectOld = new ProjectOld();
}
