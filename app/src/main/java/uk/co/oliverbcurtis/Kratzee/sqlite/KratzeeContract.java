package uk.co.oliverbcurtis.Kratzee.sqlite;


public class KratzeeContract {


    //Defines question table contents
    public static final String QUESTION_ID = "question_id";
    public static final String QUESTION_STRING = "question_string";
    public static final String QUESTION_LECTURER_ID = "lecturerID";


    //Defines answer table contents
    public static final String ANSWER_ID = "answer_id";
    public static final String ANSWER_STRING = "answer_string";
    public static final String CORRECT = "correct";
    public static final String ANSWER_LECTURER_ID = "lecturerID";


    //Defines EXISTING team
    public static final String EXISTING_TEAM_ID = "team_id";
    public static final String EXISTING_TEAM_NAME = "team_name";
    public static final String EXISTING_TEAM_POINTS = "points";
    public static final String EXISTING_TEAM_LECTURER_ID = "lecturerID";

    //Defines EXISTING team-members
    public static final String EXISTING_TEAM_MEMBER_ID = "team_member_id";
    public static final String EXISTING_TEAM_MEMBER_FULLNAME = "fullname";
    public static final String EXISTING_TEAM_MEMBER_STUDENT_NUMBER = "studentNumber";
    public static final String EXISTING_TEAM_MEMBER_POINTS = "points";
    public static final String EXISTING_TEAM_MEMBER_PRESENT = "present";
    public static final String EXISTING_TEAM_MEMBER_LECTURER_ID = "lecturerID";
    public static final String EXISTING_TEAM_MEMBER_TEAM_ID = "team_id";

    //Defines new team-members added to an EXISTING team
    public static final String EXISTING_NEW_TEAM_MEMBER_ID = "new_team_member_id";
    public static final String EXISTING_NEW_TEAM_MEMBER_FULLNAME = "fullname";
    public static final String EXISTING_NEW_TEAM_MEMBER_STUDENT_NUMBER = "studentNumber";
    public static final String EXISTING_NEW_TEAM_MEMBER_EMAIL = "email";
    public static final String EXISTING_NEW_TEAM_MEMBER_POINTS = "points";
    public static final String EXISTING_NEW_TEAM_MEMBER_PRESENT = "present";
    public static final String EXISTING_NEW_TEAM_MEMBER_LECTURER_ID = "lecturerID";
    public static final String EXISTING_NEW_TEAM_MEMBER_TEAM_ID = "team_id";

    //Defines a NEW team
    public static final String NEW_TEAM_ID = "team_id";
    public static final String NEW_TEAM_NAME = "team_name";
    public static final String NEW_TEAM_POINTS = "points";
    public static final String NEW_TEAM_LECTURER_ID = "lecturerID";

    //Defines team-members for a NEW team
    public static final String NEW_TEAM_MEMBER_ID = "new_team_member_id";
    public static final String NEW_TEAM_MEMBER_FULLNAME = "fullname";
    public static final String NEW_TEAM_MEMBER_STUDENT_NUMBER = "studentNumber";
    public static final String NEW_TEAM_MEMBER_POINTS = "points";
    public static final String NEW_TEAM_MEMBER_LECTURER_ID = "lecturerID";

    //Defines existing individual student quiz profiles
    public static final String EXISTING_INDIVIDUAL_ID = "individual_existing_id";
    public static final String EXISTING_INDIVIDUAL_FULLNAME = "individual_fullname";
    public static final String EXISTING_INDIVIDUAL_STUDENT_NUMBER = "individual_student_number";
    public static final String EXISTING_INDIVIDUAL_POINTS = "points";
    public static final String EXISTING_INDIVIDUAL_LECTURER_ID = "lecturerID";

    //Defines new individual student quiz profiles
    public static final String NEW_INDIVIDUAL_ID = "individual_id";
    public static final String NEW_INDIVIDUAL_STUDENT_FULLNAME = "fullName";
    public static final String NEW_INDIVIDUAL_STUDENT_NUMBER = "studentNumber";
    public static final String NEW_INDIVIDUAL_POINTS = "points";
    public static final String NEW_INDIVIDUAL_LECTURER_ID = "lecturerID";

    //Defines leaderboard columns
    public static final String LEADERBOARD_ID = "leaderBoard_id";
    public static final String LEADERBOARD_FULLNAME = "fullname";
    public static final String LEADERBOARD_STUDENT_NUMBER = "student_number";
    public static final String LEADERBOARD_POINTS = "points";

}
