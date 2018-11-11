package uk.co.oliverbcurtis.Kratzee.ui.detail.teamTriviaExisting;

import android.content.SharedPreferences;
import android.support.v7.widget.AppCompatButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ToggleButton;

import java.util.List;

import uk.co.oliverbcurtis.Kratzee.model.Team;
import uk.co.oliverbcurtis.Kratzee.model.TeamMember;
import uk.co.oliverbcurtis.Kratzee.sqlite.KratzeeDatabase;

public interface TeamTriviaExistContract {

    interface View {

        void initView();
        void callDynamicLayoutAdapter(Team team);
        void populateTeamNames(AppCompatButton btn_indiName, TeamTriviaExistView view);
        void searchFilter();
        void showTeamSelectionDialog(String studentID, String selectedButtonText, String studentPoints);
        void showTeamSelectionLayout(TeamMember teamMember1, TeamTriviaExistView teamTriviaExistView);
        void addExistingTeamMemberToLayout(ToggleButton teamMemberButton, LinearLayout teamMemberLayout);
        void addNewTeamMemberToLayout(ToggleButton teamMemberButton, LinearLayout teamMemberLayout, List<String> new_team_member_name_array, List<String> new_student_number_array, List<String> new_team_member_present, List<String> new_student_email, List<String> new_team_member_id);
        void addOnClickListenerToTeamMemberButton(ToggleButton teamMemberButton);
        void addOnClickListenerToNewTeamMemberButton(ToggleButton teamMemberButton);
        void getQuestions();
    }


    interface Presenter {

        void attachView(TeamTriviaExistContract.View view);
        void allTeamNames(ProgressBar progress, SharedPreferences pref);
        boolean storeSelectedTeamInSQLiteDB(String id, String teamID, KratzeeDatabase kratzeeDatabase, String teamPoints);
        void getTeamMembers(ProgressBar progress, SharedPreferences pref, String teamID, TeamTriviaExistView teamTriviaExistView, KratzeeDatabase kratzeeDatabase);
        void storeSelectedTeamMembersInSQLiteDB(TeamMember teamMember1, TeamTriviaExistView teamTriviaExistView, KratzeeDatabase kratzeeDatabase);
        void existingTeamMember(ToggleButton teamMemberButton, android.view.View v, List<String> unique_id_present);
        void newTeamMember(ToggleButton teamMemberButton, android.view.View v, List<String> unique_id_present);
        void submitTeamMembers(List<String> new_team_member_present, List<String> existing_team_member_present, List<String> new_team_member_name, List<String> new_team_member_student_number, KratzeeDatabase kratzeeDatabase, SharedPreferences pref, TeamMember teamMember1, List<String> new_student_email, List<String> new_team_member_id);
        void checkIfNewTeamMembersAdded(List<String> new_team_member_name, List<String> new_team_member_student_number, KratzeeDatabase kratzeeDatabase, SharedPreferences pref, TeamMember teamMember1, List<String> new_team_member_present, List<String> new_team_member_email, List<String> new_team_member_id);

    }
}
