package uk.co.oliverbcurtis.Kratzee.ui.detail.lecturerForgotPassword;

import android.content.Context;
import android.os.CountDownTimer;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import retrofit2.Call;
import retrofit2.Callback;
import uk.co.oliverbcurtis.Kratzee.async.ServerRequest;
import uk.co.oliverbcurtis.Kratzee.async.ServerResponse;
import uk.co.oliverbcurtis.Kratzee.async.remote.ApiUtils;
import uk.co.oliverbcurtis.Kratzee.async.remote.OperationAPI;
import uk.co.oliverbcurtis.Kratzee.model.Constants;
import uk.co.oliverbcurtis.Kratzee.model.Lecturer;
import uk.co.oliverbcurtis.Kratzee.ui.common.BaseActivity;

public class LecturerForgotPresenter implements LecturerForgotContract.Presenter {

    private LecturerForgotContract.View view;
    private OperationAPI apiService  = ApiUtils.getApiService();
    private CountDownTimer countDownTimer;

    @Override
    public void attachView(LecturerForgotContract.View view) {

        this.view = view;
    }

    public void initiateResetPasswordProcess(String email, ProgressBar progress, EditText et_email, EditText et_code, EditText et_password, TextView tv_timer, AppCompatButton btn_reset){

        progress.setVisibility(View.VISIBLE);

        Lecturer lecturer = new Lecturer();
        lecturer.setEmail(email);
        ServerRequest request = new ServerRequest();
        request.setOperation(Constants.RESET_PASSWORD_INITIATE);
        request.setLecturer(lecturer);

        apiService.operation(request).enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, retrofit2.Response<ServerResponse> response) {

                ServerResponse resp = response.body();

                if(resp.getResult().equals(Constants.SUCCESS)){

                    BaseActivity.showToast((Context) view, resp.getMessage());
                    et_email.setVisibility(View.GONE);
                    et_code.setVisibility(View.VISIBLE);
                    et_password.setVisibility(View.VISIBLE);
                    tv_timer.setVisibility(View.VISIBLE);
                    btn_reset.setText("Change Password");
                    LecturerForgotView.isResetInitiated = true;
                    startCountdownTimer(tv_timer);

                } else {

                    BaseActivity.showToast((Context) view, resp.getMessage());

                }
                progress.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {

                progress.setVisibility(View.INVISIBLE);
                BaseActivity.showToast((Context) view, t.toString());

            }
        });

    }

    public void finishResetPasswordProcess(String email, String code, String password, ProgressBar progress){

        Lecturer lecturer = new Lecturer();
        lecturer.setEmail(email);
        lecturer.setCode(code);
        lecturer.setPassword(password);
        ServerRequest request = new ServerRequest();
        request.setOperation(Constants.RESET_PASSWORD_FINISH);
        request.setLecturer(lecturer);

        apiService.operation(request).enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, retrofit2.Response<ServerResponse> response) {

                ServerResponse resp = response.body();

                if(resp.getResult().equals(Constants.SUCCESS)){

                    BaseActivity.showToast((Context) view, resp.getMessage());
                    countDownTimer.cancel();
                    view.goToLogin();

                } else {

                    BaseActivity.showToast((Context) view, resp.getMessage());

                }
                progress.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {

                progress.setVisibility(View.INVISIBLE);
                BaseActivity.showToast((Context) view, t.toString());

            }
        });
    }

    private void startCountdownTimer(TextView tv_timer){ //was 120000
        countDownTimer = new CountDownTimer(300000, 1000) {

            public void onTick(long millisUntilFinished) {
                tv_timer.setText("Time remaining : " + millisUntilFinished / 1000);
            }

            public void onFinish() {
                BaseActivity.showToast((Context) view, "Time Out! Request again to reset password.");
                view.goToLogin();
            }
        }.start();
    }
}
