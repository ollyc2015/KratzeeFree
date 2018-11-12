package uk.co.oliverbcurtis.Kratzee.ui.detail.lecturerLogin;

import android.content.Context;
import android.content.SharedPreferences;
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
import uk.co.oliverbcurtis.Kratzee.model.Lecturer;
import uk.co.oliverbcurtis.Kratzee.ui.common.BaseActivity;


public class LecturerLoginPresenter implements LecturerLoginContract.Presenter {


    private LecturerLoginContract.View view;
    private OperationAPI apiService  = ApiUtils.getApiService();

    @Override
    public void attachView(LecturerLoginContract.View view) {

        this.view = view;
    }


    public void loginRequest(String email, String password, ProgressBar progress, SharedPreferences pref){

        if (!email.isEmpty() && !password.isEmpty()) {

            progress.setVisibility(View.VISIBLE);

            //Handling of logging in an individual for an individual quiz
            Lecturer lecturer = new Lecturer();
            lecturer.setEmail(email);
            lecturer.setPassword(password);

            final ServerRequest request = new ServerRequest();
            request.setOperation(Constants.LOGIN_LECTURER);
            //set the values entered for the pin entered
            request.setLecturer(lecturer);

            // Retrofit call to API, returns list of meals
            apiService.operation(request).enqueue(new Callback<ServerResponse>() {
                @Override
                public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {

                    ServerResponse resp = response.body();

                    if (resp.getResult().equals(Constants.SUCCESS)) {

                        progress.setVisibility(View.INVISIBLE);

                        try {

                            SharedPreferences.Editor editor = pref.edit();

                            editor.putBoolean(Constants.LECTURER_IS_LOGGED_IN, true);
                            editor.putString(Constants.LECTURER_ID, resp.getLecturer().getLecturerID());
                            editor.putString(Constants.LECTURER_EMAIL, resp.getLecturer().getEmail());
                            editor.putString(Constants.LECTURER_NAME, resp.getLecturer().getName());
                            editor.apply();

                            Lecturer lecturer1 = resp.getLecturer();

                            BaseActivity.showToast((Context) view, resp.getMessage());

                            view.goToLecturerLobbyScreen(lecturer1);


                        } catch (Exception e) {
                            e.printStackTrace();
                        }

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

            BaseActivity.showToast((Context) view,"Please Enter Your Assigned Email & Password");
        }
    }
}
