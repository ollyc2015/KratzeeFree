package uk.co.oliverbcurtis.Kratzee.ui.detail.lecturerProfileMainMenu;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import uk.co.oliverbcurtis.Kratzee.R;
import uk.co.oliverbcurtis.Kratzee.model.Constants;
import uk.co.oliverbcurtis.Kratzee.ui.common.BaseActivity;
import uk.co.oliverbcurtis.Kratzee.ui.detail.lecturerCreateQuestionSets.CreateQuestionSetView;
import uk.co.oliverbcurtis.Kratzee.ui.detail.lecturerEditQuestionSets.selectTopicToEdit.SelectTopicView;
import uk.co.oliverbcurtis.Kratzee.ui.detail.startScreen.StartScreenView;

public class LecturerProfileView extends BaseActivity implements LecturerProfileContract.View, View.OnClickListener {

    private TextView tv_name,tv_email;
    private AppCompatButton btn_chg_password,btn_logout,btn_create_questions,btn_remove_all_questions, btn_remove_student_data, btn_edit_topics;
    private EditText et_old_password,et_new_password;
    private AlertDialog dialog;
    private ProgressBar progress;
    private LecturerProfilePresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lecturer_main_menu);
        initView();
    }

    @Override
    public void initView() {

        presenter = new LecturerProfilePresenter();
        presenter.attachView(this);

        btn_create_questions = findViewById(R.id.btn_create_questions);
        btn_create_questions.setOnClickListener(this);

        btn_edit_topics = findViewById(R.id.btn_edit_topics);
        btn_edit_topics.setOnClickListener(this);

        btn_remove_student_data = findViewById(R.id.btn_remove_student_data);
        btn_remove_student_data.setOnClickListener(this);

        btn_remove_all_questions = findViewById(R.id.btn_remove_all_questions);
        btn_remove_all_questions.setOnClickListener(this);

        btn_chg_password = findViewById(R.id.btn_chg_password);
        btn_chg_password.setOnClickListener(this);

        btn_logout = findViewById(R.id.btn_logout);
        btn_logout.setOnClickListener(this);

        tv_name = findViewById(R.id.tv_name);
        tv_name.setText("Hello : "+pref.getString(Constants.LECTURER_NAME,""));

        tv_email = findViewById(R.id.tv_email);
        tv_email.setText(pref.getString(Constants.LECTURER_EMAIL,""));


        progress = findViewById(R.id.progress);

    }


    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.btn_create_questions:
                goToCreateQuestionSets();
                break;


            case R.id.btn_edit_topics:
                goToEditQuestionSets();
                break;

            case R.id.btn_remove_student_data:
                removeAllStudentDataDialog();
                break;

            case R.id.btn_remove_all_questions:
                removeAllQuestionsDialog();
                break;


            case R.id.btn_chg_password:
                changePasswordDialog();
                break;

            case R.id.btn_logout:
                logout();
                break;

        }
    }

    @Override
    public void logout(){
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean(Constants.LECTURER_IS_LOGGED_IN,false);
        editor.apply();

        goToStartMenu();
    }


    @Override
    public void removeAllStudentDataDialog(){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Remove All Participant Data?");
        builder.setMessage("Doing this will Remove ALL (Independant & Teams) of the Created Accounts used to Take Your Quizzes.");
        builder.setPositiveButton("Remove", (dialog, which) -> { presenter.removeAllParticipants(progress, pref); });
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());
        builder.create().show();

    }


    @Override
    public void removeAllQuestionsDialog(){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Remove All Question Topics?");
        builder.setMessage("Doing this will Remove ALL of your Created Question Topics.");
        builder.setPositiveButton("Remove", (dialog, which) -> { presenter.removeAllQuestion(progress, pref); });
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());
        builder.create().show();

    }


    @Override
    public void changePasswordDialog(){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_change_password, null);
        et_old_password = view.findViewById(R.id.et_old_password);
        et_new_password = view.findViewById(R.id.et_new_password);

        progress = view.findViewById(R.id.progress);
        builder.setView(view);
        builder.setTitle("Change Password");
        builder.setPositiveButton("Change Password", (dialog, which) -> {

        });
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());
        dialog = builder.create();
        dialog.show();
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(v -> {
            String old_password = et_old_password.getText().toString();
            String new_password = et_new_password.getText().toString();

            if(!old_password.isEmpty() && !new_password.isEmpty()){

                progress.setVisibility(View.VISIBLE);
                presenter.changePasswordProcess(pref.getString(Constants.LECTURER_EMAIL,""),old_password,new_password, progress, dialog);

            }else {

                showToast(this, "Please Complete Both Fields before Submission");
            }
        });
    }

    @Override
    public void goToCreateQuestionSets(){
        Intent intent = new Intent(getApplicationContext(), CreateQuestionSetView.class);
        startActivity(intent);
    }


    @Override
    public void goToEditQuestionSets(){
        Intent intent = new Intent(getApplicationContext(), SelectTopicView.class);
        startActivity(intent);
    }


    @Override
    public void onBackPressed() {

        //Below is what causes the activity to go to the previous activity
        //super.onBackPressed();

        Intent intent = new Intent(getApplicationContext(), StartScreenView.class);
        startActivity(intent);
    }

    @Override
    public void goToStartMenu(){

        Intent intent = new Intent(getApplicationContext(), StartScreenView.class);
        startActivity(intent);

    }
}
