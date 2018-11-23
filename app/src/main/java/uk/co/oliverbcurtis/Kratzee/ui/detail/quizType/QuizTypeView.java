package uk.co.oliverbcurtis.Kratzee.ui.detail.quizType;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import uk.co.oliverbcurtis.Kratzee.R;
import uk.co.oliverbcurtis.Kratzee.model.Constants;
import uk.co.oliverbcurtis.Kratzee.ui.common.BaseActivity;
import uk.co.oliverbcurtis.Kratzee.ui.common.SubmitPoints;
import uk.co.oliverbcurtis.Kratzee.ui.detail.pinInput.QuizPinView;
import uk.co.oliverbcurtis.Kratzee.ui.detail.startScreen.StartScreenView;
import uk.co.oliverbcurtis.Kratzee.ui.detail.tutorialScreens.TutorialView;

public class QuizTypeView extends BaseActivity implements QuizTypeContract.View, View.OnClickListener {

    private Button btn_indi, btn_team;
    public static boolean indiButtonPressed = false;
    public static boolean teamButtonPressed = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz_type);
        initView();
    }


    @Override
    public void initView() {

        btn_indi = findViewById(R.id.btn_indi);
        btn_indi.setOnClickListener(this);

        btn_team = findViewById(R.id.btn_team);

        if(SubmitPoints.indiQuizSubmitted){
            btn_team.setOnClickListener(this);
            btn_indi.setAlpha(.5f);
            btn_indi.setClickable(false);

            Animation shake1 = AnimationUtils.loadAnimation(this, R.anim.shake);
            btn_team.startAnimation(shake1);

        }else {
            btn_team.setAlpha(.5f);
            btn_team.setClickable(false);

            Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
            btn_indi.startAnimation(shake);
        }

        //If the user has decided to take the tutorial, start the first tutorial
        if(pref.getBoolean(Constants.DEMO_REQUEST_MADE,true)) {

            tutorialView.quizTypeTutorial1(this);
        }
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.btn_indi:
                indiButtonPressed = true;
                teamButtonPressed = false;
                goToPinScreen();
                break;


            case R.id.btn_team:
                teamButtonPressed = true;
                indiButtonPressed = false;
                goToPinScreen();
                break;
        }

    }

    @Override
    public void goToPinScreen(){

        Intent intent = new Intent(getApplicationContext(), QuizPinView.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {

        if(SubmitPoints.indiQuizSubmitted){

            showToast(this, "Please Continue the Quiz by Clicking The Team Quiz Button");

        }else {

            super.onBackPressed();

            StartScreenView.studentButtonPressed = false;
            StartScreenView.lecturerButtonPressed = false;

        }
    }
}
