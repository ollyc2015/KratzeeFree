package uk.co.oliverbcurtis.Kratzee.model;

import java.io.Serializable;

public class Lecturer implements Serializable {

    private String name;
    private String email;
    private String lecturerID;
    private String password;
    private String old_password;
    private String new_password;
    private String code;
    private String assessment_profile;


    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getLecturerID() {
        return lecturerID;
    }

    public String getAssessment_profile() {
        return assessment_profile;
    }

    public void setLecturerID(String lecturerID) {
        this.lecturerID = lecturerID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setOld_password(String old_password) {
        this.old_password = old_password;
    }

    public void setNew_password(String new_password) {
        this.new_password = new_password;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setAssessment_profile(String assessment_profile) {
        this.assessment_profile = assessment_profile;
    }
}
