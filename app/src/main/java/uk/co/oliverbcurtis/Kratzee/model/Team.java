package uk.co.oliverbcurtis.Kratzee.model;

import java.io.Serializable;
import java.util.List;

public class Team implements Serializable {

    //The below is when we are sending over one string registering a student name and number for the first time
    public String teamName, teamPoints,team_id, email, password, lecturerID;;
    //Below a list object is needed because when a user clicks that they have already registered, a list of registered team names is returned by the server
    public List loaded_team_ids, loaded_team_names, student_number_list, loaded_team_points;


    public String getLecturerID() {
        return lecturerID;
    }

    public void setLecturerID(String lecturerID) {
        this.lecturerID = lecturerID;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getTeamPoints() {
        return teamPoints;
    }

    public void setTeamPoints(String teamPoints) {
        this.teamPoints = teamPoints;
    }

    public String getTeam_id() {
        return team_id;
    }

    public void setTeam_id(String team_id) {
        this.team_id = team_id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List getLoaded_team_ids() {
        return loaded_team_ids;
    }

    public void setLoaded_team_ids(List loaded_team_ids) {
        this.loaded_team_ids = loaded_team_ids;
    }

    public List getLoaded_team_names() {
        return loaded_team_names;
    }

    public void setLoaded_team_names(List loaded_team_names) {
        this.loaded_team_names = loaded_team_names;
    }

    public List getStudent_number_list() {
        return student_number_list;
    }

    public void setStudent_number_list(List student_number_list) {
        this.student_number_list = student_number_list;
    }

    public List getLoaded_team_points() {
        return loaded_team_points;
    }

    public void setLoaded_team_points(List loaded_team_points) {
        this.loaded_team_points = loaded_team_points;
    }


}
