package uk.co.oliverbcurtis.Kratzee.ui.detail.pinInput;

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
import uk.co.oliverbcurtis.Kratzee.model.Question;
import uk.co.oliverbcurtis.Kratzee.ui.common.BaseActivity;
import uk.co.oliverbcurtis.Kratzee.ui.detail.quizType.QuizTypeView;


public class QuizPinPresenter implements QuizPinContract.Presenter {

    private QuizPinContract.View view;
    private OperationAPI apiService  = ApiUtils.getApiService();

    @Override
    public void pinProcess(final String pin, final ProgressBar progress, SharedPreferences pref) {

        if (!pin.isEmpty() && pin.length() == 8) {

            progress.setVisibility(View.VISIBLE);

            //Creating a new Question object
            Question question = new Question();
            question.setStudentPin(pin);
            final ServerRequest request = new ServerRequest();
            request.setOperation(Constants.PIN_OPERATION);
            //set the values entered for the pin entered
            request.setQuestion(question);


            apiService.operation(request).enqueue(new Callback<ServerResponse>() {
                @Override
                public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {

                    ServerResponse resp = response.body();

                    if (resp.getResult().equals(Constants.SUCCESS)) {

                        progress.setVisibility(View.INVISIBLE);

                        SharedPreferences.Editor editor = pref.edit();
                        editor.putString(Constants.PIN_ENTERED, pin);
                        //Log the lecturer out if they were logged-in, but took someone else's quiz as their unique ID will be overwritten in shared pref
                        if(!pref.getString(Constants.LECTURER_ID, "").equals(resp.getQuestion().getLecturerID())){

                            editor.putBoolean(Constants.LECTURER_IS_LOGGED_IN,false);
                            editor.putString(Constants.LECTURER_ID, resp.getQuestion().getLecturerID());

                        }else{

                            editor.putString(Constants.LECTURER_ID, resp.getQuestion().getLecturerID());
                        }

                        editor.apply();

                        handlePinSuccess();

                    } else {
                        String messageFromServer = resp.getMessage();
                        BaseActivity.showToast((Context) view,messageFromServer);
                        progress.setVisibility(View.INVISIBLE);
                    }
                }

                @Override
                public void onFailure(Call<ServerResponse> call, Throwable t) {
                    BaseActivity.showToast((Context) view,t.toString());
                }
            });

        } else {

            BaseActivity.showToast((Context) view,"Your PIN should be 8 characters long");
        }
    }

    @Override
    public void attachView(QuizPinContract.View view) {

        this.view = view;

    }

    @Override
    public void handlePinSuccess(){

        if (QuizTypeView.indiButtonPressed)
        {

            //Pass back to the view class and pass intent to the individual trivia quiz
            view.goToIndividualTrivia();

        }
        else
        {

            //Pass back to the view class and pass intent to the team trivia quiz
            view.goToTeamTrivia();

        }
    }
}
