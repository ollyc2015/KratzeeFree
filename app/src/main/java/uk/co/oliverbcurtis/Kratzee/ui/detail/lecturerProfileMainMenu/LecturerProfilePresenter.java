package uk.co.oliverbcurtis.Kratzee.ui.detail.lecturerProfileMainMenu;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.ProgressBar;

import retrofit2.Call;
import retrofit2.Callback;
import uk.co.oliverbcurtis.Kratzee.async.ServerRequest;
import uk.co.oliverbcurtis.Kratzee.async.ServerResponse;
import uk.co.oliverbcurtis.Kratzee.async.remote.ApiUtils;
import uk.co.oliverbcurtis.Kratzee.async.remote.OperationAPI;
import uk.co.oliverbcurtis.Kratzee.model.Constants;
import uk.co.oliverbcurtis.Kratzee.model.Lecturer;
import uk.co.oliverbcurtis.Kratzee.model.Question;
import uk.co.oliverbcurtis.Kratzee.ui.common.BaseActivity;

public class LecturerProfilePresenter implements LecturerProfileContract.Presenter {

    private LecturerProfileContract.View view;
    private OperationAPI apiService  = ApiUtils.getApiService();

    @Override
    public void attachView(LecturerProfileContract.View view) {

        this.view = view;
    }

    @Override
    public void changePasswordProcess(String email, String old_password, String new_password, ProgressBar progress, AlertDialog dialog){

        Lecturer lecturer = new Lecturer();
        lecturer.setEmail(email);
        lecturer.setOld_password(old_password);
        lecturer.setNew_password(new_password);
        ServerRequest request = new ServerRequest();
        request.setOperation(Constants.CHANGE_PASSWORD_OPERATION);
        request.setLecturer(lecturer);

        apiService.operation(request).enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, retrofit2.Response<ServerResponse> response) {

                ServerResponse resp = response.body();
                if(resp.getResult().equals(Constants.SUCCESS)){
                    progress.setVisibility(View.GONE);
                    dialog.dismiss();
                    BaseActivity.showToast((Context) view, resp.getMessage());

                }else {
                    progress.setVisibility(View.GONE);
                    BaseActivity.showToast((Context) view, resp.getMessage());

                }
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {

                progress.setVisibility(View.GONE);
                BaseActivity.showToast((Context) view, t.toString());
            }
        });
    }


    @Override
    public void removeAllQuestion(ProgressBar progress, SharedPreferences pref){

        //Creating a new User object
        Question question = new Question();
        question.setLecturerID(pref.getString(Constants.LECTURER_ID,""));
        ServerRequest request = new ServerRequest();
        request.setOperation(Constants.DROP_QUESTIONS);
        //set the values entered for the new user
        request.setQuestion(question);

        apiService.operation(request).enqueue(new Callback<ServerResponse>() {

            @Override
            public void onResponse(Call<ServerResponse> call, retrofit2.Response<ServerResponse> response) {

                ServerResponse resp = response.body();
                BaseActivity.showToast((Context) view, resp.getMessage());
                progress.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {

                progress.setVisibility(View.INVISIBLE);
                BaseActivity.showToast((Context) view, t.toString());
            }
        });
    }

    public void removeAllParticipants(ProgressBar progress, SharedPreferences pref){


        //Creating a new Lecturer object
        Lecturer lecturer = new Lecturer();
        lecturer.setLecturerID(pref.getString(Constants.LECTURER_ID,""));
        lecturer.setAssessment_profile("No");
        ServerRequest request = new ServerRequest();
        request.setOperation(Constants.DROP_TRIVIA_PROFILES);
        //set the values entered for the new user
        request.setLecturer(lecturer);

        apiService.operation(request).enqueue(new Callback<ServerResponse>() {

            @Override
            public void onResponse(Call<ServerResponse> call, retrofit2.Response<ServerResponse> response) {

                ServerResponse resp = response.body();
                BaseActivity.showToast((Context) view, resp.getMessage());
                progress.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {

                progress.setVisibility(View.INVISIBLE);
                BaseActivity.showToast((Context) view, t.toString());
            }
        });

    }
}
