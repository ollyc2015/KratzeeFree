package uk.co.oliverbcurtis.Kratzee.model;

import java.io.Serializable;
import java.util.List;

public class TeamMember implements Serializable {

    //The below is when we are sending over one string regisering a student name and number for the first time
    public String fullName, studentNumber, points, lecturerID, team_id, assessment_profile, email, team_member_id;
    //Confirm if the student is present or not
    public boolean teamMemberPresent;
    //Below a list object is needed because when a user clicks that they have already registered, a list of registered student names is returned by the server
    public List loaded_team_member_id, loaded_fullName, loaded_student_number, loaded_points, loaded_student_team_ID;

    // Constructor that is used to create an instance of the Team-Member object
    public TeamMember(String fullName, String studentNumber, String points) {
        this.fullName = fullName;
        this.studentNumber = studentNumber;
        this.points = points;
    }

    public TeamMember(){}

    public String getTeam_member_id() {
        return team_member_id;
    }

    public void setTeam_member_id(String team_member_id) {
        this.team_member_id = team_member_id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getStudentNumber() {
        return studentNumber;
    }

    public void setStudentNumber(String studentNumber) {
        this.studentNumber = studentNumber;
    }

    public String getPoints() {
        return points;
    }

    public void setPoints(String points) {
        this.points = points;
    }

    public String getLecturerID() {
        return lecturerID;
    }

    public void setLecturerID(String lecturerID) {
        this.lecturerID = lecturerID;
    }

    public String getTeam_id() {
        return team_id;
    }

    public void setTeam_id(String team_id) {
        this.team_id = team_id;
    }

    public String getAssessment_profile() {
        return assessment_profile;
    }

    public void setAssessment_profile(String assessment_profile) {
        this.assessment_profile = assessment_profile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isTeamMemberPresent() {
        return teamMemberPresent;
    }

    public void setTeamMemberPresent(boolean teamMemberPresent) {
        this.teamMemberPresent = teamMemberPresent;
    }

    public List getLoaded_team_member_id() {
        return loaded_team_member_id;
    }

    public void setLoaded_team_member_id(List loaded_team_member_id) {
        this.loaded_team_member_id = loaded_team_member_id;
    }

    public List getLoaded_fullName() {
        return loaded_fullName;
    }

    public void setLoaded_fullName(List loaded_fullName) {
        this.loaded_fullName = loaded_fullName;
    }

    public List getLoaded_student_number() {
        return loaded_student_number;
    }

    public void setLoaded_student_number(List loaded_student_number) {
        this.loaded_student_number = loaded_student_number;
    }

    public List getLoaded_points() {
        return loaded_points;
    }

    public void setLoaded_points(List loaded_points) {
        this.loaded_points = loaded_points;
    }

    public List getLoaded_student_team_ID() {
        return loaded_student_team_ID;
    }

    public void setLoaded_student_team_ID(List loaded_student_team_ID) {
        this.loaded_student_team_ID = loaded_student_team_ID;
    }
}
