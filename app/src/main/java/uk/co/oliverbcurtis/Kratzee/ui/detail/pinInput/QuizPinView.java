package uk.co.oliverbcurtis.Kratzee.ui.detail.pinInput;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import uk.co.oliverbcurtis.Kratzee.R;
import uk.co.oliverbcurtis.Kratzee.model.Constants;
import uk.co.oliverbcurtis.Kratzee.ui.common.BaseActivity;
import uk.co.oliverbcurtis.Kratzee.ui.detail.lecturerLogin.LecturerLoginView;
import uk.co.oliverbcurtis.Kratzee.ui.detail.indiTriviaMainScreen.IndiTriviaRegisterView;
import uk.co.oliverbcurtis.Kratzee.ui.detail.quizType.QuizTypeView;
import uk.co.oliverbcurtis.Kratzee.ui.detail.startScreen.StartScreenView;
import uk.co.oliverbcurtis.Kratzee.ui.detail.teamTriviaRegister.team.TeamTriviaRegisterView;
import uk.co.oliverbcurtis.Kratzee.ui.detail.tutorialScreens.TutorialView;

public class QuizPinView extends BaseActivity implements QuizPinContract.View, View.OnClickListener {

    private EditText et_pin;
    private AppCompatButton btn_submit_pin;
    private ProgressBar progress;
    private QuizPinPresenter presenter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz_pin_input);

        initView();
    }

    @Override
    public void initView() {

        StartScreenView.tutorial_counter = 0;

        presenter = new QuizPinPresenter();
        presenter.attachView(this);

        et_pin = findViewById(R.id.et_pin);
        et_pin.setGravity(Gravity.CENTER);

        if(QuizTypeView.teamButtonPressed){

            et_pin.setText(pref.getString(Constants.PIN_ENTERED, ""));
        }



        btn_submit_pin = findViewById(R.id.btn_submit_pin);
        btn_submit_pin.setOnClickListener(this);

        progress = findViewById(R.id.progress);

        //If the user has decided to take the tutorial, start the first tutorial
        if(pref.getBoolean(Constants.DEMO_REQUEST_MADE,true)) {

            tutorialView.pinInputTutorial1(this);
        }

    }



    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.btn_submit_pin:

                String pin = et_pin.getText().toString().trim();

                presenter.pinProcess(pin, progress, pref);
                break;
        }

        if(pref.getBoolean(Constants.DEMO_REQUEST_MADE,true)) {
            switch (StartScreenView.tutorial_counter) {

                case 0:
                    tutorialView.closeQuizPinTutorial();
                    break;
            }
        }

    }

    @Override
    public void goToIndividualTrivia(){

        Intent intent = new Intent(getApplicationContext(), IndiTriviaRegisterView.class);
        startActivity(intent);

    }


    @Override
    public void goToTeamTrivia() {

        Intent intent = new Intent(getApplicationContext(), TeamTriviaRegisterView.class);
        startActivity(intent);
    }



    //We are overriding this method so that users cannot simply back out of the quiz
    @Override
    public void onBackPressed() {

        QuizTypeView.indiButtonPressed = false;
        QuizTypeView.teamButtonPressed = false;

        //Below is what causes the activity to go to the previous activity
        super.onBackPressed();
    }

}
