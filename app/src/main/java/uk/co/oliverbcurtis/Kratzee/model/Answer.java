package uk.co.oliverbcurtis.Kratzee.model;

import java.io.Serializable;
import java.util.List;

public class Answer implements Serializable {

    //Used for single strings sent/returned to/from the external DB
    private String studentPin, lecturerID, answerString, isAnswerCorrect;
    //Used for Lists sent/returned to/from the external DB
    private List answerIDList, answerStringList, isAnswerCorrectList;

    public void setStudentPin (String pin){

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

    public void setAnswerIDList(List answerIDList){

        this.answerIDList = answerIDList;
    }

    public List getAnswerIDList(){

        return answerIDList;
    }

    public void setAnswerStringList(List answerStringList){

        this.answerStringList = answerStringList;
    }

    public List getAnswerStringList(){

        return answerStringList;
    }

    public void setIsAnswerCorrectList(List isAnswerCorrectList){

        this.isAnswerCorrectList = isAnswerCorrectList;
    }

    public List getIsAnswerCorrectList(){

        return isAnswerCorrectList;
    }

    public void setAnswerString(String answerString) {
        this.answerString = answerString;
    }

    public String getAnswerString(){
        return answerString;
    }

    public void setIsAnswerCorrect(String isAnswerCorrect) {
        this.isAnswerCorrect = isAnswerCorrect;
    }

    public String getIsAnswerCorrect(){
        return isAnswerCorrect;
    }
}
