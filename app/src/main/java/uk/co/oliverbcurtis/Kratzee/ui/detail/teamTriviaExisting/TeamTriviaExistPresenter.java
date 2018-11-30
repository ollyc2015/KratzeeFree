package uk.co.oliverbcurtis.Kratzee.ui.detail.teamTriviaExisting;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.ToggleButton;
import java.util.Iterator;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.oliverbcurtis.Kratzee.R;
import uk.co.oliverbcurtis.Kratzee.async.ServerRequest;
import uk.co.oliverbcurtis.Kratzee.async.ServerResponse;
import uk.co.oliverbcurtis.Kratzee.async.remote.ApiUtils;
import uk.co.oliverbcurtis.Kratzee.async.remote.OperationAPI;
import uk.co.oliverbcurtis.Kratzee.model.Constants;
import uk.co.oliverbcurtis.Kratzee.model.Team;
import uk.co.oliverbcurtis.Kratzee.model.TeamMember;
import uk.co.oliverbcurtis.Kratzee.sqlite.KratzeeContract;
import uk.co.oliverbcurtis.Kratzee.sqlite.KratzeeDatabase;
import uk.co.oliverbcurtis.Kratzee.ui.common.BaseActivity;
import static uk.co.oliverbcurtis.Kratzee.sqlite.KratzeeDatabase.EXISTING_NEW_TEAM_MEMBERS;
import static uk.co.oliverbcurtis.Kratzee.sqlite.KratzeeDatabase.EXISTING_TEAM;
import static uk.co.oliverbcurtis.Kratzee.sqlite.KratzeeDatabase.EXISTING_TEAM_MEMBERS;


public class TeamTriviaExistPresenter implements TeamTriviaExistContract.Presenter {

    TeamTriviaExistContract.View view;


    @Override
    public void attachView(TeamTriviaExistContract.View view) {

        this.view = view;
    }

    @Override
    public void allTeamNames(ProgressBar progress, SharedPreferences pref) {

        progress.setVisibility(View.VISIBLE);
        final OperationAPI apiService = ApiUtils.getApiService();

        //Creating a new Individual object
        Team team = new Team();
        team.setLecturerID(pref.getString(Constants.LECTURER_ID, ""));


        final ServerRequest request = new ServerRequest();
        request.setOperation(Constants.ALL_TEAM_NAMES);
        request.setTeam(team);

        // Retrofit call to API, returns list of teams
        apiService.operation(request).enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {

                ServerResponse resp = response.body();

                if (resp.getResult().equals(Constants.SUCCESS)) {

                    progress.setVisibility(View.INVISIBLE);

                    Team team1 = new Team();
                    team1.setLoaded_team_ids(resp.getTeam().getLoaded_team_ids());
                    team1.setLoaded_team_names(resp.getTeam().getLoaded_team_names());
                    team1.setLoaded_team_points(resp.getTeam().getLoaded_team_points());


                    view.callDynamicLayoutAdapter(team1);

                } else {
                    String messageFromServer = resp.getMessage();
                    BaseActivity.showToast((Context) view, messageFromServer);
                    progress.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                BaseActivity.showToast((Context) view, t.toString());
            }
        });
    }

    @Override
    public boolean storeSelectedTeamInSQLiteDB(String teamID, String teamName, KratzeeDatabase kratzeeDatabase, String teamPoints) {

        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences((Context) view);

        //Clear any existing team data if it exists
        try {
            android.database.sqlite.SQLiteDatabase db = kratzeeDatabase.getWritableDatabase();
            db.delete(EXISTING_TEAM, "1", null);
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        android.database.sqlite.SQLiteDatabase db = kratzeeDatabase.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KratzeeContract.EXISTING_TEAM_ID, teamID);
        contentValues.put(KratzeeContract.EXISTING_TEAM_NAME, teamName);
        contentValues.put(KratzeeContract.EXISTING_TEAM_POINTS, teamPoints);
        contentValues.put(KratzeeContract.EXISTING_TEAM_LECTURER_ID, pref.getString(Constants.LECTURER_ID, ""));
        long result = db.insert(EXISTING_TEAM, null, contentValues);
        db.close();

        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void getTeamMembers(ProgressBar progress, SharedPreferences pref, String teamID, TeamTriviaExistView teamTriviaExistView, KratzeeDatabase kratzeeDatabase) {

        final OperationAPI apiService = ApiUtils.getApiService();

        //Creating a new Individual object
        TeamMember teamMember = new TeamMember();
        teamMember.setTeam_id(teamID);
        teamMember.setLecturerID(pref.getString(Constants.LECTURER_ID, ""));


        final ServerRequest request = new ServerRequest();
        request.setOperation(Constants.ALL_TEAM_MEMBERS);
        request.setTeamMember(teamMember);

        // Retrofit call to API, returns list of teams
        apiService.operation(request).enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {

                ServerResponse resp = response.body();

                if (resp.getResult().equals(Constants.SUCCESS)) {

                    progress.setVisibility(View.INVISIBLE);

                    TeamMember teamMember1 = new TeamMember();
                    teamMember1.setLoaded_team_member_id(resp.getTeamMember().getLoaded_team_member_id());
                    teamMember1.setLoaded_fullName(resp.getTeamMember().getLoaded_fullName());
                    teamMember1.setLoaded_student_number(resp.getTeamMember().getLoaded_student_number());
                    teamMember1.setLoaded_points(resp.getTeamMember().getLoaded_points());
                    teamMember1.setTeam_id(teamID);


                    storeSelectedTeamMembersInSQLiteDB(teamMember1, teamTriviaExistView, kratzeeDatabase);

                } else {
                    String messageFromServer = resp.getMessage();
                    BaseActivity.showToast((Context) view, messageFromServer);
                    progress.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                BaseActivity.showToast((Context) view, t.toString());
            }
        });
    }

    @Override
    public void storeSelectedTeamMembersInSQLiteDB(TeamMember teamMember1, TeamTriviaExistView teamTriviaExistView, KratzeeDatabase kratzeeDatabase) {

        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences((Context) view);

        //Clear any existing existing team-member data if it exists
        try {
            android.database.sqlite.SQLiteDatabase db = kratzeeDatabase.getWritableDatabase();
            db.delete(EXISTING_TEAM_MEMBERS, "1", null);

            for (int i = 0; i < teamMember1.getLoaded_team_member_id().size(); i++) {

                ContentValues contentValues = new ContentValues();
                contentValues.put(KratzeeContract.EXISTING_TEAM_MEMBER_ID, teamMember1.getLoaded_team_member_id().get(i).toString());
                contentValues.put(KratzeeContract.EXISTING_TEAM_MEMBER_FULLNAME, teamMember1.getLoaded_fullName().get(i).toString());
                contentValues.put(KratzeeContract.EXISTING_TEAM_MEMBER_STUDENT_NUMBER, teamMember1.getLoaded_student_number().get(i).toString());
                contentValues.put(KratzeeContract.EXISTING_TEAM_MEMBER_POINTS, teamMember1.getLoaded_points().get(i).toString());
                contentValues.put(KratzeeContract.EXISTING_TEAM_MEMBER_PRESENT, false);
                contentValues.put(KratzeeContract.EXISTING_TEAM_LECTURER_ID, pref.getString(Constants.LECTURER_ID, ""));
                contentValues.put(KratzeeContract.EXISTING_TEAM_MEMBER_TEAM_ID, teamMember1.getTeam_id());
                db.insert(EXISTING_TEAM_MEMBERS, null, contentValues);

            }

            db.close();
            view.showTeamSelectionLayout(teamMember1, teamTriviaExistView);

        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void existingTeamMember(ToggleButton teamMemberButton, View v, List<String> existing_team_member_present){

        if (teamMemberButton.isChecked()) {

            //Change the background colour of the button if the button is checked
            teamMemberButton.setBackground(v.getResources().getDrawable(R.drawable.highlight_button));

            //get the id of the present student
             Object originalStudentTagPresent = v.getTag();

            //add student id to the present array if button is checked
            existing_team_member_present.add((String) originalStudentTagPresent);


        } else {

            teamMemberButton.setBackground(v.getResources().getDrawable(R.drawable.custom_button));

            //get the id of the absent student
            Object originalStudentTagAbsent = v.getTag();

            Iterator<String> iterator = existing_team_member_present.iterator();
            while (iterator.hasNext()) {
                String value = iterator.next();
                if (originalStudentTagAbsent.equals(value)) {
                    iterator.remove();
                    break;
                }
            }
        }
    }

    @Override
    public void newTeamMember(ToggleButton teamMemberButton, View v, List<String> new_team_member_present){

        if (teamMemberButton.isChecked()) {

            //Change the background colour of the button if the button is checked
            teamMemberButton.setBackground(v.getResources().getDrawable(R.drawable.highlight_button));

            //get the id of the present student
            Object originalStudentTagPresent = v.getTag();

            //add student id to the present array if button is checked
            new_team_member_present.add((String) originalStudentTagPresent);


        } else {

            teamMemberButton.setBackground(v.getResources().getDrawable(R.drawable.custom_button));

            //get the id of the absent student
            Object originalStudentTagAbsent = v.getTag();

            Iterator<String> iterator = new_team_member_present.iterator();
            while (iterator.hasNext()) {
                String value = iterator.next();
                if (originalStudentTagAbsent.equals(value)) {
                    iterator.remove();
                    break;
                }
            }
        }
    }

    @Override
    public void submitTeamMembers(List<String> new_team_member_present, List<String> existing_team_member_present, List<String> new_team_member_name, List<String> new_team_member_student_number, KratzeeDatabase kratzeeDatabase, SharedPreferences pref, TeamMember teamMember1, List<String> new_student_email, List<String> new_team_member_id){

        try {
            android.database.sqlite.SQLiteDatabase db = kratzeeDatabase.getWritableDatabase();

            //If the existing_team_member_present is empty, do not run the block as this means all original team-members are absent
            //and this is already what is saved in the SQLite DB so no need for an else statement

            if(existing_team_member_present != null && !existing_team_member_present.isEmpty()) {


                for (int i = 0; i < existing_team_member_present.size(); i++) {

                    ContentValues contentValues = new ContentValues();
                    contentValues.put(KratzeeContract.EXISTING_TEAM_MEMBER_PRESENT, true);
                    db.update(EXISTING_TEAM_MEMBERS, contentValues, KratzeeContract.EXISTING_TEAM_MEMBER_ID + "=?", new String[]{String.valueOf(existing_team_member_present.get(i))});

                }

                db.close();
               checkIfNewTeamMembersAdded(new_team_member_name, new_team_member_student_number, kratzeeDatabase, pref, teamMember1, new_team_member_present, new_student_email, new_team_member_id);

            }else{

                checkIfNewTeamMembersAdded(new_team_member_name, new_team_member_student_number, kratzeeDatabase, pref, teamMember1, new_team_member_present, new_student_email, new_team_member_id);
            }


        }catch (Exception e) {
            e.printStackTrace();
            BaseActivity.showToast((Context) view, "Unable to Submit Team To Database due to "+e);
        }

    }

    @Override
    public void checkIfNewTeamMembersAdded(List<String> new_team_member_name, List<String> new_team_member_student_number, KratzeeDatabase kratzeeDatabase, SharedPreferences pref, TeamMember teamMember1, List<String> new_team_member_present, List<String> new_team_member_email, List<String> new_team_member_id){

        if(new_team_member_name != null && !new_team_member_name.isEmpty()) {

            //Clear any existing NEW team-member data if it exists
            android.database.sqlite.SQLiteDatabase db = kratzeeDatabase.getWritableDatabase();
            db.delete(EXISTING_NEW_TEAM_MEMBERS, "1", null);

            for (int i = 0; i < new_team_member_name.size(); i++) {

                ContentValues contentValues = new ContentValues();

                if(new_team_member_present.contains(new_team_member_id.get(i))) {

                    contentValues.put(KratzeeContract.EXISTING_NEW_TEAM_MEMBER_ID, new_team_member_id.get(i));
                    contentValues.put(KratzeeContract.EXISTING_NEW_TEAM_MEMBER_FULLNAME, new_team_member_name.get(i));
                    contentValues.put(KratzeeContract.EXISTING_NEW_TEAM_MEMBER_STUDENT_NUMBER, new_team_member_student_number.get(i));
                    contentValues.put(KratzeeContract.EXISTING_NEW_TEAM_MEMBER_EMAIL, new_team_member_email.get(i));
                    contentValues.put(KratzeeContract.EXISTING_NEW_TEAM_MEMBER_POINTS, 0);
                    contentValues.put(KratzeeContract.EXISTING_NEW_TEAM_MEMBER_LECTURER_ID, pref.getString(Constants.LECTURER_ID, ""));
                    contentValues.put(KratzeeContract.EXISTING_NEW_TEAM_MEMBER_TEAM_ID, teamMember1.getTeam_id());
                    db.insert(EXISTING_NEW_TEAM_MEMBERS, null, contentValues);

                    ContentValues updateValues = new ContentValues();
                    updateValues.put(KratzeeContract.EXISTING_NEW_TEAM_MEMBER_PRESENT, true);

                    db.update(EXISTING_NEW_TEAM_MEMBERS, updateValues, KratzeeContract.EXISTING_NEW_TEAM_MEMBER_ID + "=?", new String[]{String.valueOf(new_team_member_id.get(i))});

                }else{

                    contentValues.put(KratzeeContract.EXISTING_NEW_TEAM_MEMBER_ID, new_team_member_id.get(i));
                    contentValues.put(KratzeeContract.EXISTING_NEW_TEAM_MEMBER_FULLNAME, new_team_member_name.get(i));
                    contentValues.put(KratzeeContract.EXISTING_NEW_TEAM_MEMBER_STUDENT_NUMBER, new_team_member_student_number.get(i));
                    contentValues.put(KratzeeContract.EXISTING_NEW_TEAM_MEMBER_EMAIL, new_team_member_email.get(i));
                    contentValues.put(KratzeeContract.EXISTING_NEW_TEAM_MEMBER_POINTS, 0);
                    contentValues.put(KratzeeContract.EXISTING_NEW_TEAM_MEMBER_LECTURER_ID, pref.getString(Constants.LECTURER_ID, ""));
                    contentValues.put(KratzeeContract.EXISTING_NEW_TEAM_MEMBER_TEAM_ID, teamMember1.getTeam_id());
                    db.insert(EXISTING_NEW_TEAM_MEMBERS, null, contentValues);

                    ContentValues updateValues = new ContentValues();
                    updateValues.put(KratzeeContract.EXISTING_NEW_TEAM_MEMBER_PRESENT, false);

                    db.update(EXISTING_NEW_TEAM_MEMBERS, updateValues, KratzeeContract.EXISTING_NEW_TEAM_MEMBER_ID + "=?", new String[]{String.valueOf(new_team_member_id.get(i))});
                }
            }

            db.close();
            view.getQuestions();


        }else{

            view.getQuestions();
        }
    }
}
