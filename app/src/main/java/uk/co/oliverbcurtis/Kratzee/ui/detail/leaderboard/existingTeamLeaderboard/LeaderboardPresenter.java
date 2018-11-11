package uk.co.oliverbcurtis.Kratzee.ui.detail.leaderboard.existingTeamLeaderboard;

import android.content.ContentValues;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import java.util.List;
import uk.co.oliverbcurtis.Kratzee.model.TeamMember;
import uk.co.oliverbcurtis.Kratzee.sqlite.KratzeeContract;
import uk.co.oliverbcurtis.Kratzee.sqlite.KratzeeDatabase;
import uk.co.oliverbcurtis.Kratzee.ui.detail.leaderboard.existingTeamLeaderboard.LeaderboardContract;

import static uk.co.oliverbcurtis.Kratzee.sqlite.KratzeeDatabase.EXISTING_NEW_TEAM_MEMBERS;
import static uk.co.oliverbcurtis.Kratzee.sqlite.KratzeeDatabase.LEADERBOARD;

public class LeaderboardPresenter implements LeaderboardContract.Presenter {

    LeaderboardContract.View view;

    @Override
    public void attachView(LeaderboardContract.View view) {

        this.view = view;
    }

    @Override
    public void getTeamMemberPoints(KratzeeDatabase kratzeeDatabase, SharedPreferences pref) {

        List<String> array1 = new ArrayList<String>();

        //First, select the students current points
        SQLiteDatabase db = kratzeeDatabase.getReadableDatabase();
        String selectQuery = "SELECT " + KratzeeContract.EXISTING_TEAM_MEMBER_FULLNAME +","
                + KratzeeContract.EXISTING_TEAM_MEMBER_STUDENT_NUMBER + ","
                + KratzeeContract.EXISTING_TEAM_MEMBER_POINTS +" FROM " + KratzeeDatabase.EXISTING_TEAM_MEMBERS;

        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor != null) {

            if (cursor.moveToFirst()) {
                do {
                    String fullName = cursor.getString(cursor.getColumnIndex(KratzeeContract.EXISTING_TEAM_MEMBER_FULLNAME));
                    String studentNumber = cursor.getString(cursor.getColumnIndex(KratzeeContract.EXISTING_TEAM_MEMBER_STUDENT_NUMBER));
                    String points = cursor.getString(cursor.getColumnIndex(KratzeeContract.EXISTING_TEAM_MEMBER_POINTS));

                    array1.add(fullName);
                    array1.add(studentNumber);
                    array1.add(points);

                } while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
        }

        checkIfNewTeamMembersExist(kratzeeDatabase, array1);


    }

    private void checkIfNewTeamMembersExist(KratzeeDatabase kratzeeDatabase, List<String> array1){

        SQLiteDatabase db1 = kratzeeDatabase.getReadableDatabase();
        String selectQuery = "SELECT " + KratzeeContract.EXISTING_NEW_TEAM_MEMBER_FULLNAME +","
                + KratzeeContract.EXISTING_NEW_TEAM_MEMBER_STUDENT_NUMBER + ","
                + KratzeeContract.EXISTING_NEW_TEAM_MEMBER_POINTS +" FROM " + EXISTING_NEW_TEAM_MEMBERS;

        Cursor cursor1 = db1.rawQuery(selectQuery, null);

        if (cursor1.getCount() > 0) {

            if (cursor1.moveToFirst()) {
                do {
                    String fullName = cursor1.getString(cursor1.getColumnIndex(KratzeeContract.EXISTING_NEW_TEAM_MEMBER_FULLNAME));
                    String studentNumber = cursor1.getString(cursor1.getColumnIndex(KratzeeContract.EXISTING_NEW_TEAM_MEMBER_STUDENT_NUMBER));
                    String points = cursor1.getString(cursor1.getColumnIndex(KratzeeContract.EXISTING_NEW_TEAM_MEMBER_POINTS));

                    array1.add(fullName);
                    array1.add(studentNumber);
                    array1.add(points);
                } while (cursor1.moveToNext());
            }

            //Once the team-members are in the leaderboard table, clear it.
            db1.delete(EXISTING_NEW_TEAM_MEMBERS, "1", null);


            cursor1.close();
            db1.close();

            insertIntoLeaderBoardTable(array1, kratzeeDatabase);

        }else{

            insertIntoLeaderBoardTable(array1, kratzeeDatabase);
        }
    }

    private void insertIntoLeaderBoardTable(List<String> array1, KratzeeDatabase kratzeeDatabase){

        android.database.sqlite.SQLiteDatabase db = kratzeeDatabase.getWritableDatabase();
        db.delete(LEADERBOARD, null, null);

        for (int i = 0; i < array1.size(); i+=3) {

            ContentValues contentValues = new ContentValues();
            contentValues.put(KratzeeContract.LEADERBOARD_FULLNAME, array1.get(i));
            contentValues.put(KratzeeContract.LEADERBOARD_STUDENT_NUMBER, array1.get(i+1));
            contentValues.put(KratzeeContract.LEADERBOARD_POINTS, array1.get(i+2));
            db.insert(LEADERBOARD, null, contentValues);
        }
        array1.clear();
        db.close();

        selectLeaderboardContent(kratzeeDatabase);
    }



    private void selectLeaderboardContent(KratzeeDatabase kratzeeDatabase) {

        List<TeamMember> teamMembers = new ArrayList<>();

        SQLiteDatabase db1 = kratzeeDatabase.getReadableDatabase();
        String selectQuery = "SELECT " + KratzeeContract.LEADERBOARD_FULLNAME + ","
                + KratzeeContract.LEADERBOARD_STUDENT_NUMBER + ","
                + KratzeeContract.LEADERBOARD_POINTS + " FROM " + KratzeeDatabase.LEADERBOARD
                +" ORDER BY "+ KratzeeContract.LEADERBOARD_POINTS +" DESC";;

        Cursor cursor1 = db1.rawQuery(selectQuery, null);

        if (cursor1 != null) {

            if (cursor1.moveToFirst()) {
                do {
                    String fullName = cursor1.getString(cursor1.getColumnIndex(KratzeeContract.LEADERBOARD_FULLNAME));
                    String studentNumber = cursor1.getString(cursor1.getColumnIndex(KratzeeContract.LEADERBOARD_STUDENT_NUMBER));
                    String points = cursor1.getString(cursor1.getColumnIndex(KratzeeContract.LEADERBOARD_POINTS));

                    teamMembers.add(new TeamMember(fullName, studentNumber , points));

                } while (cursor1.moveToNext());
            }
            cursor1.close();
            db1.close();

        }

        view.populateLeaderboard(teamMembers);
    }
}
