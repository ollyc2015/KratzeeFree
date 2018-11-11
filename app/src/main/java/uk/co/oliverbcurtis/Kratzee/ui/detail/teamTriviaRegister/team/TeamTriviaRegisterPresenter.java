package uk.co.oliverbcurtis.Kratzee.ui.detail.teamTriviaRegister.team;

import android.content.ContentValues;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import java.util.UUID;
import uk.co.oliverbcurtis.Kratzee.model.Constants;
import uk.co.oliverbcurtis.Kratzee.sqlite.KratzeeContract;
import uk.co.oliverbcurtis.Kratzee.sqlite.KratzeeDatabase;

import static uk.co.oliverbcurtis.Kratzee.sqlite.KratzeeDatabase.NEW_TEAM;

public class TeamTriviaRegisterPresenter implements TeamTriviaRegisterContract.Presenter {

    private TeamTriviaRegisterContract.View view;

    @Override
    public void attachView(TeamTriviaRegisterContract.View view) {

        this.view = view;

    }

    @Override
    public boolean teamSubmission(String teamName, TeamTriviaRegisterView context, KratzeeDatabase kratzeeDatabase) {

        //First save the team name in shared pref
        saveTeamNameSharedPref(teamName, context);

        //Then handle saving in SQLite DB
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);

        long result = 0;

        //Clear any existing individual data if it exists
        try {

            android.database.sqlite.SQLiteDatabase db = kratzeeDatabase.getWritableDatabase();
            db.delete(NEW_TEAM, "1", null);


            String uniqueId = UUID.randomUUID().toString();
            ContentValues contentValues = new ContentValues();
            contentValues.put(KratzeeContract.NEW_TEAM_ID, uniqueId);
            contentValues.put(KratzeeContract.NEW_TEAM_NAME, teamName);
            contentValues.put(KratzeeContract.NEW_TEAM_POINTS, 0);
            contentValues.put(KratzeeContract.NEW_INDIVIDUAL_LECTURER_ID, pref.getString(Constants.LECTURER_ID, ""));
            result = db.insert(NEW_TEAM, null, contentValues);
            db.close();


        } catch (Exception e) {
            e.printStackTrace();
        }

        return result != -1;
    }

    private void saveTeamNameSharedPref(String teamName, TeamTriviaRegisterView context){

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Constants.TEAM_NAME, teamName);
        editor.apply();
    }
}
