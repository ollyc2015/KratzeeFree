package uk.co.oliverbcurtis.Kratzee.model;

import java.io.Serializable;
import java.util.List;

public class Answer implements Serializable {

    //Used for single strings sent/returned to/from the external DB
    private String answer_id, studentPin, lecturerID, answerString, isAnswerCorrect, question_id;
    //Used for Lists sent/returned to/from the external DB
    private List answerIDList, answerStringList, isAnswerCorrectList, questionIDList;

    public void setAnswer_id(String answer_id){
        this.answer_id = answer_id;
    }

    public String getAnswer_id(){

        return answer_id;
    }

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

    public void setQuestionIDList(List question_id){

        this.questionIDList = question_id;
    }

    public List getQuestionIDList(){

        return questionIDList;
    }

    public void setQuestion_id(String question_id){

        this.question_id = question_id;
    }

    public String getQuestion_id(){

        return question_id;
    }
}
