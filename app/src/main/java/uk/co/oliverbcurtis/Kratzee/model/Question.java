package uk.co.oliverbcurtis.Kratzee.model;

import java.io.Serializable;
import java.util.List;


public class Question implements Serializable {

    //Used for single strings sent/returned to/from the external DB
    private String studentPin, lecturerID, questionString, questionID, topicActive, questionTopic;
    private int questionNumber;
    //Used for Lists sent/returned to/from the external DB
    private List questionIDList, questionStringList, questionTopicList, questionPinList, topicActiveList;
    private Answer answer;

    public void setQuestionID(String questionID){
        this.questionID = questionID;
    }

    public String getQuestionID(){

        return questionID;
    }

    public void setQuestionTopic(String questionTopic){

        this.questionTopic = questionTopic;
    }

    public String getQuestionTopic(){

        return questionTopic;
    }

    public void setStudentPin(String pin) {

        studentPin = pin;
    }

    public void setTopicActive(String topicActive){

        this.topicActive = topicActive;
    }

    public String getTopicActive(){

        return topicActive;
    }

    public String getStudentPin(){

        return studentPin;
    }

    public void setLecturerID(String lecturerID){

        this.lecturerID = lecturerID;
    }

    public void setTopicActiveList(List topicActiveList){
        this.topicActiveList = topicActiveList;
    }

    public List getTopicActiveList(){

        return topicActiveList;
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

    public void setQuestionTopicList(List questionTopicList) {
        this.questionTopicList = questionTopicList;
    }

    public List getQuestionTopicList(){

        return questionTopicList;
    }

    public void setQuestionPinList(List questionPinList) {
        this.questionPinList = questionPinList;
    }

    public List getQuestionPinList(){
        return questionPinList;
    }


}
