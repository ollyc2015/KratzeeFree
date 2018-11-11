package uk.co.oliverbcurtis.Kratzee.ui.detail.indiTriviaExisting;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ProgressBar;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.oliverbcurtis.Kratzee.async.ServerRequest;
import uk.co.oliverbcurtis.Kratzee.async.ServerResponse;
import uk.co.oliverbcurtis.Kratzee.async.remote.ApiUtils;
import uk.co.oliverbcurtis.Kratzee.async.remote.OperationAPI;
import uk.co.oliverbcurtis.Kratzee.model.Constants;
import uk.co.oliverbcurtis.Kratzee.model.Individual;
import uk.co.oliverbcurtis.Kratzee.sqlite.KratzeeContract;
import uk.co.oliverbcurtis.Kratzee.sqlite.KratzeeDatabase;
import uk.co.oliverbcurtis.Kratzee.ui.common.BaseActivity;
import static uk.co.oliverbcurtis.Kratzee.sqlite.KratzeeDatabase.EXISTING_INDIVIDUAL;


public class IndiTriviaExistPresenter implements IndiTriviaExistContract.Presenter {


    private IndiTriviaExistContract.View view;


    @Override
    public void allIndividualNames(ProgressBar progress, SharedPreferences pref) {

        progress.setVisibility(View.VISIBLE);
        final OperationAPI apiService = ApiUtils.getApiService();

        //Creating a new Individual object
        Individual individual = new Individual();
        individual.setLecturerID(pref.getString(Constants.LECTURER_ID, ""));
        //set the if a profile is a trivia or assessment profile
        individual.setAssessment_profile("No");


        final ServerRequest request = new ServerRequest();
        request.setOperation(Constants.ALL_INDI_NAMES);
        request.setIndividual(individual);

        // Retrofit call to API, returns list of meals
        apiService.operation(request).enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {

                ServerResponse resp = response.body();

                if (resp.getResult().equals(Constants.SUCCESS)) {

                    progress.setVisibility(View.INVISIBLE);

                    Individual individual1 = new Individual();
                    individual1.setIndividual_student_id_list(resp.getIndividual().getIndividual_student_id_list());
                    individual1.setFullNameList(resp.getIndividual().getFullNameList());
                    individual1.setStudent_number_list(resp.getIndividual().getStudent_number_list());
                    individual1.setStudent_points_list(resp.getIndividual().getStudent_points_list());


                    view.callDynamicLayoutAdapter(individual1);

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
    public void attachView(IndiTriviaExistContract.View view) {

        this.view = view;
    }

    @Override
    public boolean storeSelectedInSQLiteDB(String studentID, KratzeeDatabase kratzeeDatabase, String studentPoints) {

        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences((Context) view);

        //Clear any existing individual data if it exists
        try {
            android.database.sqlite.SQLiteDatabase db = kratzeeDatabase.getWritableDatabase();
            db.delete(EXISTING_INDIVIDUAL, "1", null);
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        android.database.sqlite.SQLiteDatabase db = kratzeeDatabase.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KratzeeContract.EXISTING_INDIVIDUAL_ID, studentID);
        contentValues.put(KratzeeContract.NEW_INDIVIDUAL_POINTS, studentPoints);
        contentValues.put(KratzeeContract.NEW_INDIVIDUAL_LECTURER_ID, pref.getString(Constants.LECTURER_ID, ""));
        long result = db.insert(EXISTING_INDIVIDUAL, null, contentValues);
        db.close();

        if (result == -1) {
            return false;
        } else {
            return true;
        }

    }
}
