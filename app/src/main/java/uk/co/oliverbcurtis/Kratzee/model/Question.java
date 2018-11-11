package uk.co.oliverbcurtis.Kratzee.model;

import java.io.Serializable;
import java.util.List;


public class Question implements Serializable {

    //Used for single strings sent/returned to/from the external DB
    private String studentPin, lecturerID, questionString;
    private int questionNumber;
    //Used for Lists sent/returned to/from the external DB
    private List questionIDList, questionStringList;

    public void setStudentPin(String pin) {

        studentPin = pin;
    }

    public String getStudentPin(){

        return studentPin;
    }

    public void setLecturerID(String lecturerID){

        this.lecturerID = lecturerID;
    }

    public String getLecturerID(){

        return lecturerID;
    }

    public void setQuestionIDList(List questionIDList){

        this.questionIDList = questionIDList;
    }

    public List getQuestionIDList(){

        return questionIDList;
    }

    public void setQuestionStringList(List questionStringList) {
        this.questionStringList = questionStringList;
    }

    public List getQuestionStringList(){
        return questionStringList;
    }

    public void setQuestionNumber(int questionNumber) {
        this.questionNumber = questionNumber;
    }

    public int getQuestionNumber(){

        return questionNumber;
    }

    public void setQuestionString(String questionString) {
        this.questionString = questionString;
    }

    public String getQuestionString(){
        return questionString;
    }
}
