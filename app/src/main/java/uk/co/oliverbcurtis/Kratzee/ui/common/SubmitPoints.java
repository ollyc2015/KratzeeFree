package uk.co.oliverbcurtis.Kratzee.ui.common;

import android.content.ContentValues;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.oliverbcurtis.Kratzee.async.ServerRequest;
import uk.co.oliverbcurtis.Kratzee.async.ServerResponse;
import uk.co.oliverbcurtis.Kratzee.async.remote.ApiUtils;
import uk.co.oliverbcurtis.Kratzee.async.remote.OperationAPI;
import uk.co.oliverbcurtis.Kratzee.model.Constants;
import uk.co.oliverbcurtis.Kratzee.model.Individual;
import uk.co.oliverbcurtis.Kratzee.model.Score;
import uk.co.oliverbcurtis.Kratzee.model.Team;
import uk.co.oliverbcurtis.Kratzee.model.TeamMember;
import uk.co.oliverbcurtis.Kratzee.sqlite.KratzeeContract;
import uk.co.oliverbcurtis.Kratzee.sqlite.KratzeeDatabase;
import uk.co.oliverbcurtis.Kratzee.ui.detail.indiTriviaMainScreen.IndiTriviaRegisterView;
import uk.co.oliverbcurtis.Kratzee.ui.detail.individualQuizScreen.IndiQuizScreenView;
import uk.co.oliverbcurtis.Kratzee.ui.detail.quizType.QuizTypeView;
import uk.co.oliverbcurtis.Kratzee.ui.detail.teamQuizScreen.TeamQuizScreenView;
import uk.co.oliverbcurtis.Kratzee.ui.detail.teamTriviaRegister.teamMember.TeamMemberTriviaRegisterView;

import static uk.co.oliverbcurtis.Kratzee.sqlite.KratzeeDatabase.EXISTING_INDIVIDUAL;
import static uk.co.oliverbcurtis.Kratzee.sqlite.KratzeeDatabase.EXISTING_NEW_TEAM_MEMBERS;
import static uk.co.oliverbcurtis.Kratzee.sqlite.KratzeeDatabase.EXISTING_TEAM;
import static uk.co.oliverbcurtis.Kratzee.sqlite.KratzeeDatabase.NEW_INDIVIDUAL;
import static uk.co.oliverbcurtis.Kratzee.sqlite.KratzeeDatabase.NEW_TEAM;
import static uk.co.oliverbcurtis.Kratzee.sqlite.KratzeeDatabase.NEW_TEAM_MEMBERS;
import static uk.co.oliverbcurtis.Kratzee.ui.common.BaseActivity.showToast;


public class SubmitPoints {

    private List<String> array1;
    final OperationAPI apiService = ApiUtils.getApiService();
    public static boolean indiQuizSubmitted = false, newTeamMemberSubmitted = false, existingTeamMemberSubmitted = false,
                          existingNewTeamMemberSubmitted = false;


    /******************************NEW INDIVIDUAL TRIVIA POINTS SUBMISSION*************************************************************/

    //Submit points for a newly registered individual trivia student profile
    public void submitNewIndiTriviaPoints(ProgressBar progress, IndiQuizScreenView indiQuizScreenView, KratzeeDatabase kratzeeDatabase){


        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(indiQuizScreenView);

        int individualPoints = Score.getScore();

        try {

            android.database.sqlite.SQLiteDatabase db = kratzeeDatabase.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(KratzeeContract.NEW_INDIVIDUAL_POINTS, individualPoints);
            db.update(NEW_INDIVIDUAL, contentValues, null,null);
            db.close();

            SQLiteDatabase db1 = kratzeeDatabase.getReadableDatabase();
            String selectQuery = "SELECT " + KratzeeContract.NEW_INDIVIDUAL_STUDENT_FULLNAME +","
                    + KratzeeContract.NEW_INDIVIDUAL_STUDENT_NUMBER + ","
                    + KratzeeContract.NEW_INDIVIDUAL_POINTS +" FROM " + KratzeeDatabase.NEW_INDIVIDUAL;
            Cursor cursor = db1.rawQuery(selectQuery, null);

            if (cursor != null) {
                //as long as the cursor is not null, create a list to store the individual details in
                array1 = new ArrayList<String>();
                //move cursor to position 0 of the returned results (the students first name)
                if (cursor.moveToFirst()) {
                    //As long as there are more details (student number etc), keep looping them into the results variable
                    while (!cursor.isAfterLast()) {
                        String firstName = cursor.getString(cursor.getColumnIndex(KratzeeContract.NEW_INDIVIDUAL_STUDENT_FULLNAME));
                        String studentNumber = cursor.getString(cursor.getColumnIndex(KratzeeContract.NEW_INDIVIDUAL_STUDENT_NUMBER));
                        String points = cursor.getString(cursor.getColumnIndex(KratzeeContract.NEW_INDIVIDUAL_POINTS));
                        //then add them to the array that was created earlier
                        array1.add(firstName);
                        array1.add(studentNumber);
                        array1.add(points);
                        //after adding the first question, move the cursor to the next position if questions exist
                        cursor.moveToNext();
                    }
                }
            }
            cursor.close();
            db1.close();

        } catch (SQLiteException se) {
            Log.e(getClass().getSimpleName(), "Could not create or Open the database");
        }

        //Creating a new Individual object
        Individual individual = new Individual();
        individual.setFullName(array1.get(0));
        individual.setStudentNumber(array1.get(1));
        individual.setPoints(array1.get(2));
        individual.setLecturerID(pref.getString(Constants.LECTURER_ID,""));
        final ServerRequest request = new ServerRequest();
        request.setOperation(Constants.INSERT_INDIVIDUAL_STUDENT);
        //set the values entered for the pin entered
        request.setIndividual(individual);


        apiService.operation(request).enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {

                ServerResponse resp = response.body();

                if (resp.getResult().equals(Constants.SUCCESS)) {

                    progress.setVisibility(View.INVISIBLE);

                    String messageFromServer = resp.getMessage();
                    showToast(indiQuizScreenView, messageFromServer);
                    Score.resetScore();
                    indiQuizSubmitted = true;
                    IndiTriviaRegisterView.indiTriviaAccountCreated =false;
                    //QuizTypeView.indiButtonPressed = false;
                    array1.clear();
                    indiQuizScreenView.backToQuizSelection();

                } else {
                    String messageFromServer = resp.getMessage();
                    showToast(indiQuizScreenView,messageFromServer);
                    progress.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                showToast(indiQuizScreenView,t.toString());
            }
        });

    }

    /******************************UPDATING EXISTING TRIVIA/ASSESSMENT INDIVIDUAL PROFILE (POINTS)*************************************************************/

    //Submit points for an existing individual student profile - UPDATE IS MADE BASED ON STUDENT ID, hence why trivia and assessment profiles can be updated from this method
    public void submitExistingIndiPoints(ProgressBar progress, IndiQuizScreenView indiQuizScreenView, KratzeeDatabase kratzeeDatabase){

        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(indiQuizScreenView);

        int thisSessionsPoints = Score.getScore();
        int currentPoints = 0;

        try {
            //First, select the students current points
            SQLiteDatabase db2 = kratzeeDatabase.getReadableDatabase();
            String selectQuery1 = "SELECT " + KratzeeContract.EXISTING_INDIVIDUAL_POINTS +" FROM " + KratzeeDatabase.EXISTING_INDIVIDUAL;
            Cursor cursor1 = db2.rawQuery(selectQuery1, null);
            if (cursor1.moveToFirst()) {
                 currentPoints = Integer.parseInt(cursor1.getString(0));
            }
            //Then add this sessions points to their existing points
            android.database.sqlite.SQLiteDatabase db = kratzeeDatabase.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(KratzeeContract.EXISTING_INDIVIDUAL_POINTS, thisSessionsPoints + currentPoints);
            db.update(EXISTING_INDIVIDUAL, contentValues, null,null);
            db.close();

            //Then select all the student data to be sent so that the external DB can be updated
            SQLiteDatabase db1 = kratzeeDatabase.getReadableDatabase();
            String selectQuery = "SELECT " + KratzeeContract.EXISTING_INDIVIDUAL_ID +","
                    + KratzeeContract.EXISTING_INDIVIDUAL_POINTS +" FROM " + KratzeeDatabase.EXISTING_INDIVIDUAL;
            Cursor cursor = db1.rawQuery(selectQuery, null);

            if (cursor != null) {
                //as long as the cursor is not null, create a list to store the individual details in
                array1 = new ArrayList<String>();
                //move cursor to position 0 of the returned results (the students first name)
                if (cursor.moveToFirst()) {
                    //As long as there are more details (student number etc), keep looping them into the results variable
                    while (!cursor.isAfterLast()) {
                        String studentID = cursor.getString(cursor.getColumnIndex(KratzeeContract.EXISTING_INDIVIDUAL_ID));
                        String points = cursor.getString(cursor.getColumnIndex(KratzeeContract.EXISTING_INDIVIDUAL_POINTS));
                        //then add them to the array that was created earlier
                        array1.add(studentID);
                        array1.add(points);
                        //after adding the first question, move the cursor to the next position if questions exist
                        cursor.moveToNext();
                    }
                }
            }
            cursor.close();
            db1.close();

        } catch (SQLiteException se) {
            Log.e(getClass().getSimpleName(), "Could not create or Open the database");
        }

        //Creating a new Individual object
        Individual individual = new Individual();
        individual.setStudent_id(array1.get(0));
        individual.setPoints(array1.get(1));
        individual.setLecturerID(pref.getString(Constants.LECTURER_ID,""));
        final ServerRequest request = new ServerRequest();
        request.setOperation(Constants.UPDATE_INDIVIDUAL_POINTS);
        //set the values entered for the pin entered
        request.setIndividual(individual);


        apiService.operation(request).enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {

                ServerResponse resp = response.body();

                if (resp.getResult().equals(Constants.SUCCESS)) {

                    progress.setVisibility(View.INVISIBLE);

                    String messageFromServer = resp.getMessage();
                    showToast(indiQuizScreenView, messageFromServer);
                    Score.resetScore();
                    array1.clear();
                    indiQuizSubmitted = true;
                    //QuizTypeView.indiButtonPressed = false;
                    indiQuizScreenView.backToQuizSelection();

                } else {
                    String messageFromServer = resp.getMessage();
                    showToast(indiQuizScreenView,messageFromServer);
                    progress.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                showToast(indiQuizScreenView,t.toString());
            }
        });

    }

    /******************************NEW TRIVIA TEAM POINTS SUBMISSION*************************************************************/

    //Submit a newly registered trivia teams points
    public void submitNewTeamTriviaPoints(ProgressBar progress, TeamQuizScreenView teamQuizScreenView, KratzeeDatabase kratzeeDatabase){

        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(teamQuizScreenView);

        int teamPoints = Score.getScore();

        try {

            android.database.sqlite.SQLiteDatabase db = kratzeeDatabase.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(KratzeeContract.NEW_TEAM_POINTS, teamPoints);
            db.update(NEW_TEAM, contentValues, null,null);
            db.close();

            SQLiteDatabase db1 = kratzeeDatabase.getReadableDatabase();
            String selectQuery = "SELECT " + KratzeeContract.NEW_TEAM_NAME +","
                    + KratzeeContract.NEW_TEAM_POINTS +" FROM " + KratzeeDatabase.NEW_TEAM;
            Cursor cursor = db1.rawQuery(selectQuery, null);

            if (cursor != null) {
                //as long as the cursor is not null, create a list to store the individual details in
                array1 = new ArrayList<String>();
                //move cursor to position 0 of the returned results (the students first name)
                if (cursor.moveToFirst()) {
                    //As long as there are more details (student number etc), keep looping them into the results variable
                    while (!cursor.isAfterLast()) {
                        String teamName = cursor.getString(cursor.getColumnIndex(KratzeeContract.NEW_TEAM_NAME));
                        String points = cursor.getString(cursor.getColumnIndex(KratzeeContract.NEW_TEAM_POINTS));
                        //then add them to the array that was created earlier
                        array1.add(teamName);
                        array1.add(points);
                        //after adding the first question, move the cursor to the next position if questions exist
                        cursor.moveToNext();
                    }
                }
            }
            cursor.close();
            db1.close();

        } catch (SQLiteException se) {
            Log.e(getClass().getSimpleName(), "Could not create or Open the database");
        }

        //Creating a new team object
        Team team = new Team();
        team.setTeamName(array1.get(0));
        team.setTeamPoints(array1.get(1));
        team.setLecturerID(pref.getString(Constants.LECTURER_ID,""));
        final ServerRequest request = new ServerRequest();
        request.setOperation(Constants.REGISTER_NEW_TEAM);
        //set the values entered for the team object created
        request.setTeam(team);


        apiService.operation(request).enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {

                ServerResponse resp = response.body();

                if (resp.getResult().equals(Constants.SUCCESS)) {

                    String team_id = resp.getTeam().team_id;

                    array1.clear();
                    submitNewTeamMemberTriviaPoints(progress, teamQuizScreenView, kratzeeDatabase, team_id);


                } else {
                    String messageFromServer = resp.getMessage();
                    showToast(teamQuizScreenView,messageFromServer);
                    progress.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                showToast(teamQuizScreenView,t.toString());
            }
        });

    }

    //Submit a newly registered trivia teams team-member points
    public void submitNewTeamMemberTriviaPoints(ProgressBar progress, TeamQuizScreenView teamQuizScreenView, KratzeeDatabase kratzeeDatabase, String team_id){


        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(teamQuizScreenView);

        int teamMemberPoints = Score.getScore();

        try {

            android.database.sqlite.SQLiteDatabase db = kratzeeDatabase.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(KratzeeContract.NEW_TEAM_MEMBER_POINTS, teamMemberPoints);
            db.update(NEW_TEAM_MEMBERS, contentValues, null,null);
            db.close();

            SQLiteDatabase db1 = kratzeeDatabase.getReadableDatabase();
            String selectQuery = "SELECT " + KratzeeContract.NEW_TEAM_MEMBER_FULLNAME +","
                    + KratzeeContract.NEW_TEAM_MEMBER_STUDENT_NUMBER + ","
                    + KratzeeContract.NEW_TEAM_MEMBER_POINTS +" FROM " + KratzeeDatabase.NEW_TEAM_MEMBERS;
            Cursor cursor = db1.rawQuery(selectQuery, null);

            if (cursor != null) {
                //as long as the cursor is not null, create a list to store the individual details in
                array1 = new ArrayList<String>();
                //move cursor to position 0 of the returned results (the students first name)
                if (cursor.moveToFirst()) {
                    //As long as there are more details (student number etc), keep looping them into the results variable
                    while (!cursor.isAfterLast()) {
                        String teamMemberName = cursor.getString(cursor.getColumnIndex(KratzeeContract.NEW_TEAM_MEMBER_FULLNAME));
                        String studentNumber = cursor.getString(cursor.getColumnIndex(KratzeeContract.NEW_TEAM_MEMBER_STUDENT_NUMBER));
                        String points = cursor.getString(cursor.getColumnIndex(KratzeeContract.NEW_TEAM_MEMBER_POINTS));
                        //then add them to the array that was created earlier
                        array1.add(teamMemberName);
                        array1.add(studentNumber);
                        array1.add(points);
                        //after adding the first question, move the cursor to the next position if questions exist
                        cursor.moveToNext();
                    }
                }
            }
            cursor.close();
            db1.close();

        } catch (SQLiteException se) {
            Log.e(getClass().getSimpleName(), "Could not create or Open the database");
        }

        for (int i = 0; i < array1.size(); i+=3) {

            //Creating a new team-member object
            TeamMember teamMember = new TeamMember();

            teamMember.setFullName(array1.get(i));
            teamMember.setStudentNumber(array1.get(i+1));
            teamMember.setPoints(array1.get(i+2));
            teamMember.setTeam_id(team_id);
            teamMember.setLecturerID(pref.getString(Constants.LECTURER_ID, ""));
            teamMember.setTeamMemberPresent(true);

            final ServerRequest request = new ServerRequest();
            request.setOperation(Constants.REGISTER_NEW_TEAM_MEMBER);
            //set the values entered for the team-member object created
            request.setTeamMember(teamMember);


            apiService.operation(request).enqueue(new Callback<ServerResponse>() {
                @Override
                public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {

                    ServerResponse resp = response.body();

                    if (resp.getResult().equals(Constants.SUCCESS)) {


                        if(!newTeamMemberSubmitted) {

                            newTeamMemberSubmitted = true;

                            String messageFromServer = resp.getMessage();
                            showToast(teamQuizScreenView, messageFromServer);

                            progress.setVisibility(View.INVISIBLE);
                            array1.clear();
                            indiQuizSubmitted = false;
                            TeamMemberTriviaRegisterView.newTeamSubmitted = false;
                            teamQuizScreenView.newTriviaRegisteredTeamEndScreen();
                        }


                    } else {
                        String messageFromServer = resp.getMessage();
                        showToast(teamQuizScreenView, messageFromServer);
                        progress.setVisibility(View.INVISIBLE);
                    }
                }

                @Override
                public void onFailure(Call<ServerResponse> call, Throwable t) {
                    showToast(teamQuizScreenView, t.toString());
                }
            });
        }

        newTeamMemberSubmitted  = false;

    }

    /******************************UPDATE EXISTING TRIVIA TEAM POINTS OR ASSESSMENT TEAM POINTS*************************************************************/

    //Submit an existing trivia team points
    public void submitExistingTeamTriviaPoints(ProgressBar progress, TeamQuizScreenView teamQuizScreenView, KratzeeDatabase kratzeeDatabase){


        int thisSessionsPoints = Score.getScore();
        int currentPoints = 0;

        try {
            //First, select the students current points
            SQLiteDatabase db2 = kratzeeDatabase.getReadableDatabase();
            String selectQuery1 = "SELECT " + KratzeeContract.EXISTING_TEAM_POINTS +" FROM " + KratzeeDatabase.EXISTING_TEAM;
            Cursor cursor1 = db2.rawQuery(selectQuery1, null);
            if (cursor1.moveToFirst()) {
                currentPoints = Integer.parseInt(cursor1.getString(0));
            }
            //Then add this sessions points to their existing points
            android.database.sqlite.SQLiteDatabase db = kratzeeDatabase.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(KratzeeContract.EXISTING_TEAM_MEMBER_POINTS, thisSessionsPoints + currentPoints);
            db.update(EXISTING_TEAM, contentValues, null,null);
            db.close();

            //Then select the existing team data to send to the external DB
            SQLiteDatabase db1 = kratzeeDatabase.getReadableDatabase();
            String selectQuery = "SELECT " + KratzeeContract.EXISTING_TEAM_ID +","
                    + KratzeeContract.EXISTING_TEAM_NAME +","
                    + KratzeeContract.EXISTING_TEAM_POINTS +" FROM " + KratzeeDatabase.EXISTING_TEAM;
            Cursor cursor = db1.rawQuery(selectQuery, null);

            if (cursor != null) {
                //as long as the cursor is not null, create a list to store the individual details in
                array1 = new ArrayList<String>();
                //move cursor to position 0 of the returned results (the students first name)
                if (cursor.moveToFirst()) {
                    //As long as there are more details (student number etc), keep looping them into the results variable
                    while (!cursor.isAfterLast()) {
                        String teamID = cursor.getString(cursor.getColumnIndex(KratzeeContract.EXISTING_TEAM_ID));
                        String teamName = cursor.getString(cursor.getColumnIndex(KratzeeContract.EXISTING_TEAM_NAME));
                        String points = cursor.getString(cursor.getColumnIndex(KratzeeContract.EXISTING_TEAM_POINTS));
                        //then add them to the array that was created earlier
                        array1.add(teamID);
                        array1.add(teamName);
                        array1.add(points);
                        //after adding the first question, move the cursor to the next position if questions exist
                        cursor.moveToNext();
                    }
                }
            }
            cursor.close();
            db1.close();

        } catch (SQLiteException se) {
            Log.e(getClass().getSimpleName(), "Could not create or Open the database");
        }

        //Creating a new existing team object
        Team team = new Team();
        team.setTeam_id(array1.get(0));
        team.setTeamName(array1.get(1));
        team.setTeamPoints(array1.get(2));


        final ServerRequest request = new ServerRequest();
        request.setOperation(Constants.UPDATE_EXISTING_TEAM_POINTS);
        //set the values entered for the pin entered
        request.setTeam(team);


        apiService.operation(request).enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {

                ServerResponse resp = response.body();

                if (resp.getResult().equals(Constants.SUCCESS)) {

                    array1.clear();
                    submitExistingTeamMemeberTriviaPoints(progress, teamQuizScreenView, kratzeeDatabase);

                } else {
                    String messageFromServer = resp.getMessage();
                    showToast(teamQuizScreenView,messageFromServer);
                    progress.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                showToast(teamQuizScreenView,t.toString());
            }
        });

    }

    //Submit an existing trivia team-member points
    public void submitExistingTeamMemeberTriviaPoints(ProgressBar progress, TeamQuizScreenView teamQuizScreenView, KratzeeDatabase kratzeeDatabase){


        int thisSessionsPoints = Score.getScore();


        try {
            //First, update the current points with this sessions points
            SQLiteDatabase db1 = kratzeeDatabase.getWritableDatabase();
            String strSQL = "UPDATE "+ KratzeeDatabase.EXISTING_TEAM_MEMBERS +" SET "+ KratzeeContract.EXISTING_TEAM_MEMBER_POINTS
                    +" = "+ KratzeeContract.EXISTING_TEAM_MEMBER_POINTS +"+"+ thisSessionsPoints + " WHERE " + KratzeeContract.EXISTING_TEAM_MEMBER_PRESENT +" = 1";
            db1.execSQL(strSQL);
            db1.close();

            SQLiteDatabase db = kratzeeDatabase.getReadableDatabase();
            //Then select the team-members to update the external DB
            String selectQuery = "SELECT " + KratzeeContract.EXISTING_TEAM_MEMBER_ID +","
                    + KratzeeContract.EXISTING_TEAM_MEMBER_POINTS + ","
                    + KratzeeContract.EXISTING_TEAM_MEMBER_PRESENT + ","
                    + KratzeeContract.EXISTING_TEAM_MEMBER_LECTURER_ID +" FROM " + KratzeeDatabase.EXISTING_TEAM_MEMBERS;
            Cursor cursor = db.rawQuery(selectQuery, null);

            if (cursor != null) {
                //as long as the cursor is not null, create a list to store the individual details in
                array1 = new ArrayList<String>();
                //move cursor to position 0 of the returned results (the students first name)
                if (cursor.moveToFirst()) {
                    //As long as there are more details (student number etc), keep looping them into the results variable
                    while (!cursor.isAfterLast()) {
                        String team_member_id = cursor.getString(cursor.getColumnIndex(KratzeeContract.EXISTING_TEAM_MEMBER_ID));
                        String team_member_points = cursor.getString(cursor.getColumnIndex(KratzeeContract.EXISTING_TEAM_MEMBER_POINTS));
                        String team_member_attendance = cursor.getString(cursor.getColumnIndex(KratzeeContract.EXISTING_TEAM_MEMBER_PRESENT));
                        String lecturer_id = cursor.getString(cursor.getColumnIndex(KratzeeContract.EXISTING_TEAM_MEMBER_LECTURER_ID));
                        //then add them to the array that was created earlier
                        array1.add(team_member_id);
                        array1.add(team_member_points);
                        array1.add(team_member_attendance);
                        array1.add(lecturer_id);
                        //after adding the first question, move the cursor to the next position if questions exist
                        cursor.moveToNext();
                    }
                }
            }
            cursor.close();
            db.close();

        } catch (SQLiteException se) {
            Log.e(getClass().getSimpleName(), "Could not create or Open the database");
        }

        for (int i = 0; i < array1.size(); i+=4) {

            //Creating a new team-member object
            TeamMember teamMember = new TeamMember();

            teamMember.setTeam_member_id(array1.get(i));
            teamMember.setPoints(array1.get(i+1));

            if (array1.get(i + 2).equals("1")) {

                teamMember.setTeamMemberPresent(true);
            }else{

                teamMember.setTeamMemberPresent(false);
            }

            teamMember.setLecturerID(array1.get(i+3));

            final ServerRequest request = new ServerRequest();
            request.setOperation(Constants.UPDATE_ORIGINAL_TEAM_MEMBER);
            //set the values entered for the team-member object created
            request.setTeamMember(teamMember);


            apiService.operation(request).enqueue(new Callback<ServerResponse>() {
                @Override
                public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {

                    ServerResponse resp = response.body();

                    if (resp.getResult().equals(Constants.SUCCESS)) {


                        if(!existingTeamMemberSubmitted) {

                            existingTeamMemberSubmitted = true;
                            String messageFromServer = resp.getMessage();
                            array1.clear();
                            submitNewTeamMembersExistingTeam(progress, teamQuizScreenView, kratzeeDatabase, messageFromServer);
                        }

                    } else {
                        String messageFromServer = resp.getMessage();
                        showToast(teamQuizScreenView, messageFromServer);
                        progress.setVisibility(View.INVISIBLE);
                    }
                }

                @Override
                public void onFailure(Call<ServerResponse> call, Throwable t) {
                    showToast(teamQuizScreenView, t.toString());
                }
            });
        }
        existingTeamMemberSubmitted = false;
    }

    private void submitNewTeamMembersExistingTeam(ProgressBar progress, TeamQuizScreenView teamQuizScreenView, KratzeeDatabase kratzeeDatabase, String messageFromServer){

        //First, check to see if there were any new team-members added to an existing team, if not skip this step
        SQLiteDatabase db2 = kratzeeDatabase.getReadableDatabase();
        String selectQuery1 = "SELECT " + KratzeeContract.EXISTING_NEW_TEAM_MEMBER_FULLNAME +" FROM " + KratzeeDatabase.EXISTING_NEW_TEAM_MEMBERS;
        Cursor cursor1 = db2.rawQuery(selectQuery1, null);
        if (cursor1.getCount() == 0) {

            showToast(teamQuizScreenView, messageFromServer);
            progress.setVisibility(View.INVISIBLE);
            array1.clear();
            indiQuizSubmitted = false;
            TeamMemberTriviaRegisterView.newTeamSubmitted = false;
            teamQuizScreenView.showLeaderBoard();

            cursor1.close();
            db2.close();

        }else{

            int thisSessionsPoints = Score.getScore();

            try {
                //Add this sessions points to the new team-members added if they were marked as present
                android.database.sqlite.SQLiteDatabase db = kratzeeDatabase.getWritableDatabase();
                ContentValues contentValues = new ContentValues();
                contentValues.put(KratzeeContract.EXISTING_NEW_TEAM_MEMBER_POINTS, thisSessionsPoints);
                db.update(EXISTING_NEW_TEAM_MEMBERS, contentValues, KratzeeContract.EXISTING_NEW_TEAM_MEMBER_PRESENT + "=" + 1,null);
                db.close();


                //Then select the team-members to update the external DB
                SQLiteDatabase db1 = kratzeeDatabase.getReadableDatabase();
                String selectQuery = "SELECT " + KratzeeContract.EXISTING_NEW_TEAM_MEMBER_FULLNAME +","
                        + KratzeeContract.EXISTING_NEW_TEAM_MEMBER_STUDENT_NUMBER + ","
                        + KratzeeContract.EXISTING_NEW_TEAM_MEMBER_EMAIL + ","
                        + KratzeeContract.EXISTING_NEW_TEAM_MEMBER_POINTS + ","
                        + KratzeeContract.EXISTING_NEW_TEAM_MEMBER_TEAM_ID+ ","
                        + KratzeeContract.EXISTING_NEW_TEAM_MEMBER_PRESENT+ ","
                        + KratzeeContract.EXISTING_TEAM_MEMBER_LECTURER_ID +" FROM " + KratzeeDatabase.EXISTING_NEW_TEAM_MEMBERS;
                cursor1 = db1.rawQuery(selectQuery, null);

                if (cursor1 != null) {
                    //as long as the cursor is not null, create a list to store the individual details in
                    array1 = new ArrayList<String>();
                    //move cursor to position 0 of the returned results (the students first name)
                    if (cursor1.moveToFirst()) {
                        //As long as there are more details (student number etc), keep looping them into the results variable
                        while (!cursor1.isAfterLast()) {
                            String team_member_name = cursor1.getString(cursor1.getColumnIndex(KratzeeContract.EXISTING_NEW_TEAM_MEMBER_FULLNAME));
                            String student_number = cursor1.getString(cursor1.getColumnIndex(KratzeeContract.EXISTING_NEW_TEAM_MEMBER_STUDENT_NUMBER));
                            String email = cursor1.getString(cursor1.getColumnIndex(KratzeeContract.EXISTING_NEW_TEAM_MEMBER_EMAIL));
                            String points = cursor1.getString(cursor1.getColumnIndex(KratzeeContract.EXISTING_NEW_TEAM_MEMBER_POINTS));
                            String team_id = cursor1.getString(cursor1.getColumnIndex(KratzeeContract.EXISTING_NEW_TEAM_MEMBER_TEAM_ID));
                            String attendance = cursor1.getString(cursor1.getColumnIndex(KratzeeContract.EXISTING_NEW_TEAM_MEMBER_PRESENT));
                            String lecturer_id = cursor1.getString(cursor1.getColumnIndex(KratzeeContract.EXISTING_TEAM_MEMBER_LECTURER_ID));
                            //then add them to the array that was created earlier
                            array1.add(team_member_name);
                            array1.add(student_number);
                            array1.add(email);
                            array1.add(points);
                            array1.add(team_id);
                            array1.add(attendance);
                            array1.add(lecturer_id);
                            //after adding the first question, move the cursor to the next position if questions exist
                            cursor1.moveToNext();
                        }
                    }
                }
                cursor1.close();
                db2.close();

            } catch (SQLiteException se) {
                Log.e(getClass().getSimpleName(), "Could not create or Open the database");
            }

            for (int i = 0; i < array1.size(); i+=7) {

                //Creating a new team-member object
                TeamMember teamMember = new TeamMember();

                teamMember.setFullName(array1.get(i));
                teamMember.setStudentNumber(array1.get(i+1));
                teamMember.setEmail(array1.get(i+2));
                teamMember.setPoints(array1.get(i+3));
                teamMember.setTeam_id(array1.get(i+4));
                if (array1.get(i + 5).equals("1")) {

                    teamMember.setTeamMemberPresent(true);
                }else{

                    teamMember.setTeamMemberPresent(false);
                }
                teamMember.setLecturerID(array1.get(i+6));


                teamMember.setAssessment_profile("No");


                final ServerRequest request = new ServerRequest();
                request.setOperation(Constants.NEW_TEAM_MEMBERS);
                //set the values entered for the team-member object created
                request.setTeamMember(teamMember);


                apiService.operation(request).enqueue(new Callback<ServerResponse>() {
                    @Override
                    public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {

                        ServerResponse resp = response.body();

                        if (resp.getResult().equals(Constants.SUCCESS)) {


                            if(!existingNewTeamMemberSubmitted) {

                                existingNewTeamMemberSubmitted = true;

                                showToast(teamQuizScreenView, messageFromServer);
                                progress.setVisibility(View.INVISIBLE);
                                array1.clear();
                                indiQuizSubmitted = false;
                                TeamMemberTriviaRegisterView.newTeamSubmitted = false;
                                teamQuizScreenView.showLeaderBoard();
                            }


                        } else {
                            String messageFromServer = resp.getMessage();
                            showToast(teamQuizScreenView, messageFromServer);
                            progress.setVisibility(View.INVISIBLE);
                        }
                    }

                    @Override
                    public void onFailure(Call<ServerResponse> call, Throwable t) {
                        showToast(teamQuizScreenView, t.toString());
                    }
                });
            }

            existingNewTeamMemberSubmitted  = false;
        }
    }
}
