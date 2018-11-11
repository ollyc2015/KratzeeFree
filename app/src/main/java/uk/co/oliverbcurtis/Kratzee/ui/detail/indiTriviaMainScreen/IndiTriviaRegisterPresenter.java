package uk.co.oliverbcurtis.Kratzee.ui.detail.indiTriviaMainScreen;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import java.util.UUID;
import uk.co.oliverbcurtis.Kratzee.model.Constants;
import uk.co.oliverbcurtis.Kratzee.sqlite.KratzeeContract;
import uk.co.oliverbcurtis.Kratzee.sqlite.KratzeeDatabase;
import static uk.co.oliverbcurtis.Kratzee.sqlite.KratzeeDatabase.NEW_INDIVIDUAL;

public class IndiTriviaRegisterPresenter implements IndiTriviaRegisterContract.Presenter{

    private IndiTriviaRegisterContract.View view;

    @Override
    public void attachView(IndiTriviaRegisterContract.View view) {

        this.view = view;
    }

    @Override
    public boolean saveToSQLite(String indi_name, String indi_student_number, KratzeeDatabase kratzeeDatabase) {

        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences((Context) view);

        //Clear any existing individual data if it exists
        try{
            android.database.sqlite.SQLiteDatabase db = kratzeeDatabase.getWritableDatabase();
            db.delete(NEW_INDIVIDUAL,"1",null);
            db.close();
        }catch (Exception e){
            e.printStackTrace();
        }

        String uniqueId = UUID.randomUUID().toString();
        android.database.sqlite.SQLiteDatabase db = kratzeeDatabase.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KratzeeContract.NEW_INDIVIDUAL_ID, uniqueId);
        contentValues.put(KratzeeContract.NEW_INDIVIDUAL_STUDENT_FULLNAME, indi_name);
        contentValues.put(KratzeeContract.NEW_INDIVIDUAL_STUDENT_NUMBER, indi_student_number);
        contentValues.put(KratzeeContract.NEW_INDIVIDUAL_POINTS, 0);
        contentValues.put(KratzeeContract.NEW_INDIVIDUAL_LECTURER_ID, pref.getString(Constants.LECTURER_ID,""));
        long result = db.insert(NEW_INDIVIDUAL, null, contentValues);
        db.close();

        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }
}
