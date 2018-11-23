package uk.co.oliverbcurtis.Kratzee.ui.detail.startScreen;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
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
    Toolbar toolbar1;
    private SharedPreferences.Editor editor;

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

        editor = pref.edit();
        editor.putBoolean(Constants.DEMO_REQUEST_MADE, false).apply();

        if(pref.getBoolean(Constants.FIRST_TIME_PARTICIPANT,true) && isNetworkAvailable()) {

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

                    pref.edit().putBoolean(Constants.FIRST_TIME_PARTICIPANT, false).apply();

                    SharedPreferences.Editor editor = pref.edit();
                    editor.putBoolean(Constants.DEMO_REQUEST_MADE, true).apply();

                    tutorialView.startScreenTutorial1(this, toolbar1);

                })
                .setNegativeButton(getString(R.string.no), (dialog1, whichButton) -> {

                    pref.edit().putBoolean(Constants.FIRST_TIME_PARTICIPANT, false).apply();

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
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


    //This method handles the option selected in the app-bar menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){

            case R.id.tutorial_request:
                editor.putBoolean(Constants.DEMO_REQUEST_MADE, true).apply();
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
        //super.onBackPressed();

    }
}
