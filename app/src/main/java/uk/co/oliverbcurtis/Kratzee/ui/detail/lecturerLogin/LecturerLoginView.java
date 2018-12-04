package uk.co.oliverbcurtis.Kratzee.ui.detail.lecturerLogin;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import uk.co.oliverbcurtis.Kratzee.R;
import uk.co.oliverbcurtis.Kratzee.model.Constants;
import uk.co.oliverbcurtis.Kratzee.model.Lecturer;
import uk.co.oliverbcurtis.Kratzee.ui.common.BaseActivity;
import uk.co.oliverbcurtis.Kratzee.ui.detail.lecturerProfileMainMenu.LecturerProfileView;
import uk.co.oliverbcurtis.Kratzee.ui.detail.lecturerForgotPassword.LecturerForgotView;
import uk.co.oliverbcurtis.Kratzee.ui.detail.lecturerRegister.LecturerRegisterView;

public class LecturerLoginView extends BaseActivity implements LecturerLoginContract.View, View.OnClickListener {

    private LecturerLoginPresenter presenter;
    private EditText et_email, et_password;
    private Button btn_login;
    private ProgressBar progress;
    private TextView tv_forgot_password, tv_register;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lecturer_login);
        initView();
    }


    @Override
    public void initView() {

        checkLoginStatus();

        presenter = new LecturerLoginPresenter();
        presenter.attachView(this);

        et_email = findViewById(R.id.et_email);
        et_email.setGravity(Gravity.CENTER);

        et_password = findViewById(R.id.et_password);
        et_password.setGravity(Gravity.CENTER);

        btn_login = findViewById(R.id.btn_login);
        btn_login.setOnClickListener(this);

        tv_forgot_password = findViewById(R.id.tv_forgot_password);
        tv_forgot_password.setOnClickListener(this);

        if(pref.getBoolean(Constants.USER_HAS_NOT_REGISTERED_AN_ACCOUNT,true)) {

            tv_register = findViewById(R.id.tv_register);
            tv_register.setOnClickListener(this);

        }else{

            tv_register = findViewById(R.id.tv_register);
            tv_register.setVisibility(View.GONE);
        }



        progress = findViewById(R.id.progress);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.btn_login:

                String email = et_email.getText().toString().trim();
                String password = et_password.getText().toString().trim();

                presenter.loginRequest(email, password, progress, pref);

                et_email.getText().clear();
                et_password.getText().clear();
                break;


            case R.id.tv_forgot_password:
                goToResetPasswordScreen();
                break;


            case R.id.tv_register:
                goToRegisterScreen();
                break;

        }
    }

    @Override
    public void goToLecturerLobbyScreen(Lecturer lecturer){

        Intent intent = new Intent(getApplicationContext(), LecturerProfileView.class);
        intent.putExtra("lecturer", lecturer);
        startActivity(intent);

    }

    @Override
    public void goToResetPasswordScreen(){

        Intent intent = new Intent(getApplicationContext(), LecturerForgotView.class);
        startActivity(intent);

    }

    @Override
    public void goToRegisterScreen(){

        Intent intent = new Intent(getApplicationContext(), LecturerRegisterView.class);
        startActivity(intent);

    }

    @Override
    public void checkLoginStatus(){

        //If the user logged in earlier, take them straight to their profile else, take them to the login screen
        if(pref.getBoolean(Constants.LECTURER_IS_LOGGED_IN,true)){ // get value of last login status

            Intent intent = new Intent(getApplicationContext(), LecturerProfileView.class);
            startActivity(intent);
        }
    }
}
