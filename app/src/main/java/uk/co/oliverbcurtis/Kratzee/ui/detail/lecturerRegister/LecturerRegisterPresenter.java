package uk.co.oliverbcurtis.Kratzee.ui.detail.lecturerRegister;

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

public class LecturerRegisterPresenter implements LecturerRegisterContract.Presenter {

    public LecturerRegisterContract.View view;
    private OperationAPI apiService  = ApiUtils.getApiService();

    @Override
    public void attachView(LecturerRegisterContract.View view) {

        this.view = view;

    }

    @Override
    public void registerProcess(String name, String email, String password, ProgressBar progress, SharedPreferences pref) {

        //Creating a new Lecturer object
        Lecturer lecturer = new Lecturer();
        lecturer.setName(name);
        lecturer.setEmail(email);
        lecturer.setPassword(password);

        final ServerRequest request = new ServerRequest();
        request.setOperation(Constants.REGISTER_LECTURER);

        request.setLecturer(lecturer);

        apiService.operation(request).enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {

                ServerResponse resp = response.body();

                if (resp.getResult().equals(Constants.SUCCESS)) {

                    progress.setVisibility(View.INVISIBLE);

                    BaseActivity.showToast((Context) view, resp.getMessage());


                    SharedPreferences.Editor editor = pref.edit();

                    editor.putBoolean(Constants.LECTURER_IS_LOGGED_IN, true);
                    editor.putString(Constants.LECTURER_ID, resp.getLecturer().getLecturerID());
                    editor.putString(Constants.LECTURER_EMAIL, resp.getLecturer().getEmail());
                    editor.putString(Constants.LECTURER_NAME, resp.getLecturer().getName());
                    editor.apply();

                    Lecturer lecturer1 = resp.getLecturer();

                    BaseActivity.showToast((Context) view, resp.getMessage());

                    view.goToLecturerLobbyScreen(lecturer1);

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
    }
}
