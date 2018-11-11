package uk.co.oliverbcurtis.Kratzee.async;

import uk.co.oliverbcurtis.Kratzee.model.Answer;
import uk.co.oliverbcurtis.Kratzee.model.Individual;
import uk.co.oliverbcurtis.Kratzee.model.Lecturer;
import uk.co.oliverbcurtis.Kratzee.model.Question;
import uk.co.oliverbcurtis.Kratzee.model.Team;
import uk.co.oliverbcurtis.Kratzee.model.TeamMember;

public class ServerRequest {

    private String operation;
    private Question question;
    private Answer answer;
    private Individual individual;
    private Team team;
    private TeamMember teamMember;
    private Lecturer lecturer;

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public void setQuestion (Question question){
        this.question = question;
    }

    public void setAnswer (Answer answer){
        this.answer = answer;
    }

    public void setIndividual (Individual individual){
        this.individual = individual;
    }

    public void setTeam (Team team){
        this.team = team;
    }

    public void setTeamMember(TeamMember teamMember){
        this.teamMember = teamMember;
    }

    public void setLecturer (Lecturer lecturer){
        this.lecturer = lecturer;
    }
}
