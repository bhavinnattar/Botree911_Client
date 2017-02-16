package com.botree.botree911_client.utility;

import com.botree.botree911_client.model.Project;
import com.botree.botree911_client.model.Ticket;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bhavin on 1/2/17.
 */

public class Constant {

    public static int device_type = 0;
    public static String holder_type = "client";

    public static String baseURL = "http://192.168.0.134:3000"; // Local
    public static String loginURL = baseURL + "/users/sign_in";
    public static String forgotPasswordURL = baseURL + "";
    public static String allProjectURL = baseURL + "/projects/list";
    public static String allTicketURL = baseURL + "/tickets/list";
    public static String createTicketURL = baseURL + "/tickets";
    public static String commentListTicketURL = "/ticket_comments";
    public static String historyListTicketURL = "/ticket_status_history";
    public static String editTicketURL = baseURL + "/tickets/";
    public static String allStatusURL = baseURL + "/tickets/status_list";

    public static ArrayList<Project> allProjects = new ArrayList<Project>();
    public static ArrayList<Ticket> allTickets = new ArrayList<Ticket>();
    public static Ticket selectedTicket = new Ticket();
    public static Project selectedProject = new Project();
}
