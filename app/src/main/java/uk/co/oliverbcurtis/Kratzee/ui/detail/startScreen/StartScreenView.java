package uk.co.oliverbcurtis.Kratzee.ui.detail.startScreen;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import uk.co.oliverbcurtis.Kratzee.R;
import uk.co.oliverbcurtis.Kratzee.model.Constants;
import uk.co.oliverbcurtis.Kratzee.ui.common.BaseActivity;
import uk.co.oliverbcurtis.Kratzee.ui.common.RequestQuestionsExternalDB;
import uk.co.oliverbcurtis.Kratzee.ui.detail.lecturerLogin.LecturerLoginView;
import uk.co.oliverbcurtis.Kratzee.ui.detail.quizType.QuizTypeView;
import uk.co.oliverbcurtis.Kratzee.ui.detail.tutorialScreens.TutorialView;

public class StartScreenView extends BaseActivity implements StartScreenContract.View, View.OnClickListener{

    public static boolean studentButtonPressed = false;
    public static boolean lecturerButtonPressed = false;

    private Button btn_lecturer, btn_student;
    private TutorialView tutorialView;
    public static int tutorial_counter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_type);
        initView();
    }

    @Override
    public void initView() {

        btn_student = findViewById(R.id.btn_student);
        btn_student.setOnClickListener(this);

        btn_lecturer = findViewById(R.id.btn_lecturer);
        btn_lecturer.setOnClickListener(this);

        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean(Constants.DEMO_REQUEST_MADE, false).apply();

        if(pref.getBoolean(Constants.FIRST_TIME_USER,true)) {

            offerTutorial();

            pref.edit().putBoolean(Constants.FIRST_TIME_USER, false).apply();
        }

        tutorialView = new TutorialView();
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.btn_student:
                studentButtonPressed = true;
                lecturerButtonPressed = false;
                goSelectedScreen();
                break;

            case R.id.btn_lecturer:
                lecturerButtonPressed = true;
                studentButtonPressed = false;
                goSelectedScreen();
                break;
        }

        if(pref.getBoolean(Constants.DEMO_REQUEST_MADE,true)) {
            switch (tutorial_counter) {

                case 0:
                    tutorialView.startScreenTutorial2(this);
                    break;

                case 1:
                    tutorialView.closeStartScreenTutorial();
                    break;
            }
        }
    }

    public void offerTutorial(){

        new AlertDialog.Builder(this)
                .setTitle("First Time?")
                .setMessage("Welcome To Kratzee! Fancy a Tour? If so, Click 'Ok' and we will go Through the app Together!")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(android.R.string.yes, (dialog1, whichButton) -> {

                    SharedPreferences.Editor editor = pref.edit();
                    editor.putBoolean(Constants.DEMO_REQUEST_MADE, true).apply();

                    tutorialView.startScreenTutorial1(this);

                })
                .setNegativeButton(android.R.string.no, null).show();
    }

    @Override
    public void goSelectedScreen() {

        if(studentButtonPressed){

            Intent intent = new Intent(getApplicationContext(), QuizTypeView.class);
            startActivity(intent);

        }else{

            Intent intent = new Intent(getApplicationContext(), LecturerLoginView.class);
            startActivity(intent);
        }
    }


    @Override
    public void onBackPressed() {

        //Below is what causes the activity to go to the previous activity
        //super.onBackPressed();

    }
}
