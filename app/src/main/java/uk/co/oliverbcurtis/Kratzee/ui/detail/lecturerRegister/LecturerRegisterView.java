package uk.co.oliverbcurtis.Kratzee.ui.detail.lecturerRegister;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import uk.co.oliverbcurtis.Kratzee.R;
import uk.co.oliverbcurtis.Kratzee.model.Lecturer;
import uk.co.oliverbcurtis.Kratzee.ui.common.BaseActivity;
import uk.co.oliverbcurtis.Kratzee.ui.detail.lecturerLogin.LecturerLoginView;
import uk.co.oliverbcurtis.Kratzee.ui.detail.lecturerProfileMainMenu.LecturerProfileView;


public class LecturerRegisterView extends BaseActivity implements LecturerRegisterContract.View, View.OnClickListener {

    private AppCompatButton btn_register;
    private EditText et_email,et_password,et_name;
    private ProgressBar progress;
    private LecturerRegisterPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lecturer_register);
        initView();
    }

    @Override
    public void initView() {

        presenter = new LecturerRegisterPresenter();
        presenter.attachView(this);

        btn_register = findViewById(R.id.btn_register);
        btn_register.setOnClickListener(this);

        et_name = findViewById(R.id.et_name);
        et_email = findViewById(R.id.et_email);
        et_password = findViewById(R.id.et_password);

        progress = findViewById(R.id.progress);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btn_register:
                registerUser();
                break;

        }

    }

    @Override
    public void registerUser(){

        String name = et_name.getText().toString().trim();
        String email = et_email.getText().toString().trim();
        String password = et_password.getText().toString().trim();

        if(!name.isEmpty() && !email.isEmpty() && !password.isEmpty()) {

            progress.setVisibility(View.VISIBLE);
            presenter.registerProcess(name,email,password, progress, pref);

        } else {

            showToast(this, "Please Complete all The Required Fields");
        }
    }

    @Override
    public void goToLecturerLobbyScreen(Lecturer lecturer){

        Intent intent = new Intent(getApplicationContext(), LecturerProfileView.class);
        intent.putExtra("lecturer", lecturer);
        startActivity(intent);

    }
}
