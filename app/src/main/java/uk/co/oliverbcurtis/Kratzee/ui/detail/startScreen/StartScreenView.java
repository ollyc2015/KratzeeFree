package uk.co.oliverbcurtis.Kratzee.ui.detail.startScreen;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import uk.co.oliverbcurtis.Kratzee.R;
import uk.co.oliverbcurtis.Kratzee.model.Constants;
import uk.co.oliverbcurtis.Kratzee.ui.common.BaseActivity;
import uk.co.oliverbcurtis.Kratzee.ui.detail.feedbackForm.FeedbackView;
import uk.co.oliverbcurtis.Kratzee.ui.detail.lecturerLogin.LecturerLoginView;
import uk.co.oliverbcurtis.Kratzee.ui.detail.quizType.QuizTypeView;


public class StartScreenView extends BaseActivity implements StartScreenContract.View, View.OnClickListener{

    public static boolean studentButtonPressed = false;
    public static boolean lecturerButtonPressed = false;

    private Button btn_lecturer, btn_student;
    private Toolbar toolbar1;
    private boolean doubleBackToExitPressedOnce = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_type);
        initView();
    }

    @Override
    public void initView() {

        toolbar1 = findViewById(R.id.quiz_format);
        setSupportActionBar(toolbar1);

        btn_student = findViewById(R.id.btn_student);
        btn_student.setOnClickListener(this);

        btn_lecturer = findViewById(R.id.btn_lecturer);
        btn_lecturer.setOnClickListener(this);

        if(pref.getBoolean(Constants.FIRST_RUN,true) && isNetworkAvailable()) {

            offerTutorial();

        }else if(!isNetworkAvailable()){

            showInternetMessage();
        }
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
                QuizTypeView.indiButtonPressed = false;
                QuizTypeView.teamButtonPressed = false;
                goSelectedScreen();
                break;
        }

    }

    @Override
    public void offerTutorial(){

        new AlertDialog.Builder(this)
                .setTitle("Tutorial")
                .setMessage("Welcome To Kratzee! Fancy a Tour? If so, Click 'Yes' and we will go Through the app Together!")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(getString(R.string.yes), (dialog1, whichButton) -> {

                    pref.edit().putBoolean(Constants.FIRST_RUN, false).apply();

                    //Below is needed so that the tutorial won't repeat after the user has set a question-set and gone back to the lecturer main menu
                    //We will only set 'SHOW_HOW_TO_SET_QUESTIONS' to false once shown and leave 'DEMO_REQUEST_MADE' as true to use on the 'Take a Quiz' section
                    pref.edit().putBoolean(Constants.SHOW_HOW_TO_SET_QUESTIONS, true).apply();
                    pref.edit().putBoolean(Constants.DEMO_REQUEST_MADE, true).apply();
                    pref.edit().putBoolean(Constants.INFORM_HOW_TO_SCRATCH, true).apply();

                    tutorialView.startScreenTutorial1(this, toolbar1);

                })
                .setNegativeButton(getString(R.string.no), (dialog1, whichButton) -> {

                    pref.edit().putBoolean(Constants.FIRST_RUN, false).apply();

                }).show();
    }


    @Override
    public void showInternetMessage(){

        new AlertDialog.Builder(this)
                .setTitle("Internet Required")
                .setMessage("Welcome To Kratzee! Unfortunately, an Internet Connection is Needed to Run Kratzee.\n\nPlease connect to 4G or Wifi and Everything Should Run Smoothly!")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(getString(R.string.ok), (dialog1, whichButton) -> {

                    dialog1.dismiss();

                })
                .setNegativeButton(getString(R.string.cancel), (dialog1, whichButton) -> { dialog1.dismiss(); }).show();
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


    //Used to check if there is an available internet connection when starting the app
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        assert connectivityManager != null;
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


    //This method handles the option selected in the app-bar menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){

            case R.id.tutorial_request:
                pref.edit().putBoolean(Constants.DEMO_REQUEST_MADE, true).apply();
                pref.edit().putBoolean(Constants.SHOW_HOW_TO_SET_QUESTIONS, true).apply();
                pref.edit().putBoolean(Constants.INFORM_HOW_TO_SCRATCH, true).apply();
                tutorialView.startScreenTutorial1(this, toolbar1);
                break;

            case R.id.feedback_request:
                Intent intent = new Intent(getApplicationContext(), FeedbackView.class);
                startActivity(intent);
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {

        //Below is what causes the activity to go to the previous activity
        if (doubleBackToExitPressedOnce) {
            finishAffinity(); //used to to close all the tasks in the stack related to the app
            System.exit(0); //exit the app
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        showToast(this, "Clicking Back Again Will Close the App Completely and Take You to Your Home Menu");

        new Handler().postDelayed(() -> doubleBackToExitPressedOnce=false, 2000);

    }
}
