package uk.co.oliverbcurtis.Kratzee.ui.detail.lecturerForgotPassword;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import uk.co.oliverbcurtis.Kratzee.R;
import uk.co.oliverbcurtis.Kratzee.ui.common.BaseActivity;
import uk.co.oliverbcurtis.Kratzee.ui.detail.lecturerLogin.LecturerLoginView;

public class LecturerForgotView extends BaseActivity implements LecturerForgotContract.View,  View.OnClickListener {

    private EditText et_email, et_code, et_password;
    private TextView tv_timer;
    private ProgressBar progress;
    private AppCompatButton btn_reset;
    public static boolean isResetInitiated = false;
    private String email;
    private LecturerForgotPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lecturer_password_reset);
        initView();
    }

    @Override
    public void initView() {

        btn_reset = findViewById(R.id.btn_reset);
        btn_reset.setOnClickListener(this);

        tv_timer = findViewById(R.id.timer);
        et_code = findViewById(R.id.et_code);

        et_email = findViewById(R.id.et_email);

        et_password = findViewById(R.id.et_password);

        et_password.setVisibility(View.GONE);
        et_code.setVisibility(View.GONE);
        tv_timer.setVisibility(View.GONE);

        presenter = new LecturerForgotPresenter();
        presenter.attachView(this);

        progress = findViewById(R.id.progress);

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.btn_reset:

                if (!isResetInitiated) {

                    email = et_email.getText().toString().trim();

                    if (!email.isEmpty()) {
                        progress.setVisibility(View.VISIBLE);
                        presenter.initiateResetPasswordProcess(email, progress, et_email, et_code, et_password, tv_timer, btn_reset);
                    } else {

                        showToast(this, "Please Enter Your Registered Email");
                    }
                } else {

                    String code = et_code.getText().toString().trim();
                    String password = et_password.getText().toString().trim();

                    if (!code.isEmpty() && !password.isEmpty()) {

                        presenter.finishResetPasswordProcess(email, code, password, progress);
                    } else {

                        showToast(this, "Please Complete all empty fields");
                    }
                    break;

                }
        }
    }


    //This is called on password reset completion
    @Override
    public void goToLogin(){

        isResetInitiated = false;
        Intent intent = new Intent(getApplicationContext(), LecturerLoginView.class);
        startActivity(intent);

    }


    //If the user presses the back button after submitting their email, reset the boolean variable before going to the previous screen
    @Override
    public void onBackPressed(){

        isResetInitiated = false;
        super.onBackPressed();

    }
}
