package uk.co.oliverbcurtis.Kratzee.async;

import uk.co.oliverbcurtis.Kratzee.model.Answer;
import uk.co.oliverbcurtis.Kratzee.model.Individual;
import uk.co.oliverbcurtis.Kratzee.model.Lecturer;
import uk.co.oliverbcurtis.Kratzee.model.Question;
import uk.co.oliverbcurtis.Kratzee.model.Team;
import uk.co.oliverbcurtis.Kratzee.model.TeamMember;

public class ServerResponse {

    private String result, message;
    private Question question;
    private Answer answer;
    private Individual individual;
    private Team team;
    private TeamMember teamMember;
    private Lecturer lecturer;

    public String getResult() {
        return result;
    }

    public String getMessage()   {
        return message;
    }

    public Question getQuestion(){
        return question;
    }

    public Answer getAnswer(){
        return answer;
    }

    public Individual getIndividual(){
        return individual;
    }

    public Team getTeam() {
        return team;
    }

    public TeamMember getTeamMember(){
        return teamMember;
    }

    public Lecturer getLecturer(){
        return lecturer;
    }
}
