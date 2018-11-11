package uk.co.oliverbcurtis.Kratzee.model;

import java.io.Serializable;
import java.util.List;

public class Individual implements Serializable {

    //The below is when we are sending over one string regisering a student name and number for the first time
    private String student_id, fullName, studentNumber, points, lecturerID, assessment_profile, email, password;
    //Below a list object is needed because when a user clicks that they have already registered, a list of registered student names is returned by the server
    private List individual_student_id_list, fullNameList, student_number_list, student_points_list;


    public void setStudent_id(String student_id) {
        this.student_id = student_id;
    }

    public String getStudent_id(){
        return student_id;
    }

    public void setFullName(String fullName){
        this.fullName = fullName;
    }

    public String getFullName(){
        return fullName;
    }

    public void setStudentNumber(String studentNumber) {
        this.studentNumber = studentNumber;
    }

    public String getStudentNumber(){
        return studentNumber;
    }

    public void setPoints(String points){
        this.points = points;
    }

    public String getPoints(){

        return points;
    }

    public void setLecturerID(String lecturerID){
        this.lecturerID = lecturerID;
    }

    public String getLecturerID(){
        return lecturerID;
    }

    public void setAssessment_profile(String assessment_profile){
        this.assessment_profile = assessment_profile;
    }

    public String getAssessment_profile(){
        return assessment_profile;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public String getEmail(){
        return email;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public String getPassword(){
        return password;
    }

    public void setIndividual_student_id_list(List individual_student_id_list){

        this.individual_student_id_list = individual_student_id_list;
    }

    public List getIndividual_student_id_list() {
        return individual_student_id_list;
    }

    public void setFullNameList(List fullNameList){

        this.fullNameList = fullNameList;
    }

    public List getFullNameList() {
        return fullNameList;
    }

    public void setStudent_number_list(List student_number_list){

        this.student_number_list = student_number_list;
    }

    public List getStudent_number_list() {
        return student_number_list;
    }

    public void setStudent_points_list(List student_points_list) {
        this.student_points_list = student_points_list;
    }

    public List getStudent_points_list() {
        return student_points_list;
    }
}
