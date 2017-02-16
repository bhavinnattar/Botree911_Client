package com.botree.botree911_client.model;

/**
 * Created by bhavin on 3/2/17.
 */

public class Ticket {

    private String id;
    private String project_id;
    private String name;
    private String description;
    private String status;
    private String created_at;
    private String raised_by;
    private String assingee;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProject_id() {
        return project_id;
    }

    public void setProject_id(String project_id) {
        this.project_id = project_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getRaised_by() {
        return raised_by;
    }

    public void setRaised_by(String raised_by) {
        this.raised_by = raised_by;
    }

    public String getAssingee() {
        return assingee;
    }

    public void setAssingee(String assingee) {
        this.assingee = assingee;
    }
}
