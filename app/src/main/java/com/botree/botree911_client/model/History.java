package com.botree.botree911_client.model;

/**
 * Created by bhavin on 8/2/17.
 */

public class History {

    private String id;
    private String user_name;
    private String last_status;
    private String current_status;
    private String date_time;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getLast_status() {
        return last_status;
    }

    public void setLast_status(String last_status) {
        this.last_status = last_status;
    }

    public String getCurrent_status() {
        return current_status;
    }

    public void setCurrent_status(String current_status) {
        this.current_status = current_status;
    }

    public String getDate_time() {
        return date_time;
    }

    public void setDate_time(String date_time) {
        this.date_time = date_time;
    }
}
