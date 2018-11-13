package uk.co.oliverbcurtis.Kratzee.model;

public class Constants {

    //Success is sent with the returned JSON array, we will check for this when handling responses
    public static final String SUCCESS = "success";
    public static final String FAILURE = "failure";

    //Sets the PIN operation to get sent over to the server
    public static final String PIN_OPERATION = "check_question_pin";

    //Below is the used to store the PIN value entered by the student into shared pref for access later
    public static final String PIN_ENTERED = "pin_entered";

    //Below is the operation set to get all the questions/answers associated with a PIN number
    public static final String GET_ALL_QUESTIONS = "get_all_questions";
    public static final String Get_ALL_ANSWERS = "get_all_answers";

    //Below is the operation set when submitting a new individual student quiz profile to the external DB
    public static final String INSERT_INDIVIDUAL_STUDENT = "insert_individual_student";

    //Below is the operation set when retrieving all the registered individual student names for the trivia quiz
    public static final String ALL_INDI_NAMES = "all_indi_names";

    //Below is the operation set when updating the points of an individual student profile taking the individual quiz
    public static final String UPDATE_INDIVIDUAL_POINTS = "update_individual_points";

    //Below is the saved team name stored in shared pref to access on the team-member screen
    public static final String TEAM_NAME = "team_name";

    //Below is called when the team has completed the quiz, first, the team details are sent to the external DB
    public static final String REGISTER_NEW_TEAM = "register_new_team";

    //Then, when submitting the new teamMembers, the below operation is used
    public static final String REGISTER_NEW_TEAM_MEMBER = "register_new_team_member";

    //Below is the constant used to set the operation for requesting all the trivia team-names in the external DB
    public static final String ALL_TEAM_NAMES = "all_team_names";

    //Below is the operation set to call all the associated team-members of a trivia based team
    public static final String ALL_TEAM_MEMBERS = "all_team_members";

    //The below is called when submitting an existing team quiz to update the points earned (both trivia and assessment)
    public static final String UPDATE_EXISTING_TEAM_POINTS = "update_existing_team_points";

    //The below is called when sending a completed existing trivia team-member object to the external DB
    public static final String UPDATE_ORIGINAL_TEAM_MEMBER = "update_original_team_member";

    //The below is the operation set for when a new team-member is added to an existing team
    public static final String NEW_TEAM_MEMBERS = "new_team_members";

    //Below is the operation set to edit an existing topic
    public static final String EDIT_QUESTION = "edit_question";

    //Below is the operation set when a Lecturer (person creating the quiz) attempts to login/register/reset/change their password.
    //and removing questions
    public static final String LOGIN_LECTURER = "login_lecturer";
    public static final String REGISTER_LECTURER = "register_lecturer";
    public static final String RESET_PASSWORD_INITIATE = "resPassReq";
    public static final String RESET_PASSWORD_FINISH = "resPass";
    public static final String CHANGE_PASSWORD_OPERATION = "chgPass";
    public static final String DROP_QUESTIONS = "drop_questions";
    public static final String DROP_TRIVIA_PROFILES = "drop_trivia_profiles";


    //Shared pref to save Lecturer Login details
    public static final String LECTURER_IS_LOGGED_IN = "lecturer_is_Logged_In";
    public static final String LECTURER_EMAIL = "lecturer_email";
    public static final String LECTURER_NAME = "lecturer_name";
    //Below is the constant used to store the retured lecturer ID after the PIN submission has been or a lecturer logs in made
    public static final String LECTURER_ID = "lecturer_id";

    //Check if this is the first time the user has opened the app and clicked to take a quiz
    public static final String FIRST_TIME_PARTICIPANT = "first_time_participant";
    //Check if this is the first time the user has opened the app set their own quiz
    public static final String FIRST_TIME_LECTURER = "first_time_lecturer";

    //At the beginning of the app, the user will be asked if they wish to have a demo of the app features
    public static final String DEMO_REQUEST_MADE = "demo_has_request_made";

    //When a lecturer loads their profile and clicks to add a question topic, first load all existing topics, below is the operation needed
    public static final String LOAD_EXISTING_TOPICS = "load_existing_topics";
    public static final String LOAD_SELECTED_TOPIC_QUESTIONS = "load_selected_topic_questions";

    //Below is used when a lecturer edits one of their questions sets
    public static final String QUESTION_EDIT = "question_edit";
    public static final String ANSWER_EDIT = "answer_edit";

}
