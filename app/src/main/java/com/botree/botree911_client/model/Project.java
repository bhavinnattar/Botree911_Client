package com.botree.botree911_client.model;

/**
 * Created by bhavin on 2/2/17.
 */

public class Project {

    private String id = "";
    private String name = "";
    private String description = "";
    private String client = "";
    private String project_manager = "";
    private String team_leader = "";
    private String team_member = "";
    private String noOfTeam = "";

    public String getNoOfTeam() {
        return noOfTeam;
    }

    public void setNoOfTeam(String noOfTeam) {
        this.noOfTeam = noOfTeam;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getProject_manager() {
        return project_manager;
    }

    public void setProject_manager(String project_manager) {
        this.project_manager = project_manager;
    }

    public String getTeam_leader() {
        return team_leader;
    }

    public void setTeam_leader(String team_leader) {
        this.team_leader = team_leader;
    }

    public String getTeam_member() {
        return team_member;
    }

    public void setTeam_member(String team_member) {
        this.team_member = team_member;
    }

}
