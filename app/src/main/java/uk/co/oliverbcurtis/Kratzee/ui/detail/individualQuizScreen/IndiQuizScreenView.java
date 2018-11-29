package uk.co.oliverbcurtis.Kratzee.ui.detail.individualQuizScreen;

import android.app.Activity;
import android.content.Intent;
import android.gesture.GestureOverlayView;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Toast;

import java.util.List;
import uk.co.oliverbcurtis.Kratzee.R;
import uk.co.oliverbcurtis.Kratzee.model.Constants;
import uk.co.oliverbcurtis.Kratzee.ui.common.BaseActivity;
import uk.co.oliverbcurtis.Kratzee.ui.common.MainPagerAdapter;
import uk.co.oliverbcurtis.Kratzee.ui.common.SubmitPoints;
import uk.co.oliverbcurtis.Kratzee.ui.common.SwipeDisabledViewPager;
import uk.co.oliverbcurtis.Kratzee.ui.detail.indiTriviaMainScreen.IndiTriviaRegisterView;
import uk.co.oliverbcurtis.Kratzee.ui.detail.quizType.QuizTypeView;
import uk.co.oliverbcurtis.Kratzee.ui.detail.startScreen.StartScreenView;


public class IndiQuizScreenView extends BaseActivity implements IndiQuizScreenContract.View, View.OnClickListener  {

    private IndiQuizScreenPresenter presenter;
    private SwipeDisabledViewPager pager;
    private MainPagerAdapter pagerAdapter;
    private SubmitPoints submitPoints;
    private boolean doubleBackToExitPressedOnce = false;
    private Activity individual_quiz;
    private GestureDetector mGesture;
    private static final int SWIPE_MAX_OFF_PATH = 250;
    private static final int SWIPE_THRESHOLD_VELOCITY = 200;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen_slide);
        initView();
    }


    @Override
    public void initView() {

        presenter = new IndiQuizScreenPresenter();
        presenter.attachView(this);

        pagerAdapter = new MainPagerAdapter();
        pager = findViewById (R.id.pager);

        submitPoints = new SubmitPoints();

        presenter.selectQuestions(pagerAdapter, pager , kratzeeDatabase, pref);

        mGesture = new GestureDetector(this, mOnGesture);
    }


    @Override
    public void onClick(View view) {

        switch (view.getId()) {


            case R.id.btn_submit_question:
                ProgressBar progressBar = presenter.getProgressBar();
                progressBar.setVisibility(View.VISIBLE);
                submitPoints(progressBar);
                break;

            case R.id.btn_previous:
                goToPreviousScreen();
                break;


            case R.id.btn_next:
                goToNextScreen();
                break;

        }


    }

    @Override
    public void submitPoints(ProgressBar progressBar){

        if(IndiTriviaRegisterView.indiTriviaAccountCreated) {

            submitPoints.submitNewIndiTriviaPoints(progressBar, this, kratzeeDatabase);

        //This method is used for existing trivia individual and assessment profiles
        }else{

            submitPoints.submitExistingIndiPoints(progressBar, this, kratzeeDatabase);

        }
    }

    @Override
    public void goToPreviousScreen(){

        View current_page = presenter.getCurrentPage(pagerAdapter, pager);
        List<String> questionArray = presenter.returnQuestionArray();


        if (pager.getAdapter().getCount()== questionArray.size() && current_page.findViewById(R.id.scratch_view1).isShown() && current_page.findViewById(R.id.scratch_view2).isShown() &&
                current_page.findViewById(R.id.scratch_view3).isShown() && current_page.findViewById(R.id.scratch_view4).isShown()) {

            showToast(this, "Please Finish the Quiz Before Reviewing Your Answers");

        }else{

            pager.setCurrentItem(pager.getCurrentItem() - 1);
        }
    }


    @Override
    public void goToNextScreen(){

        View current_page = presenter.getCurrentPage(pagerAdapter, pager);


        if(current_page.findViewById(R.id.scratch_view1).isShown() && current_page.findViewById(R.id.scratch_view2).isShown() &&
                current_page.findViewById(R.id.scratch_view3).isShown() && current_page.findViewById(R.id.scratch_view4).isShown()){

            showToast(this, "Please Finish the Quiz Before Reviewing Your Answers");
        }else{
            pager.setCurrentItem(pager.getCurrentItem() + 1);
        }
    }


    //We are overriding this method so that users cannot simply back out of the quiz
    @Override
    public void onBackPressed() {

      //Below is what causes the activity to go to the previous activity
        if (doubleBackToExitPressedOnce) {
            backToMainMenu();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        showToast(this, "Clicking Back Again Will Take You Back to The Main Menu & You'll Lose Your Progress");

        new Handler().postDelayed(() -> doubleBackToExitPressedOnce=false, 2000);
    }


    @Override
    public void showDemo(View individual_layout){

        //Casting the view to activity is necessary as the showcase view tutorial requires an activity as a param
        individual_quiz = (Activity)individual_layout.getContext();

        tutorialView.indiQuizScreenTutorial1(individual_quiz);

    }

    @Override
    public void backToQuizSelection(){

        Intent intent = new Intent(getApplicationContext(), QuizTypeView.class);
        startActivity(intent);
    }

    @Override
    public void backToMainMenu(){

        Intent intent = new Intent(getApplicationContext(), StartScreenView.class);
        startActivity(intent);
    }

    //The below two methods handle touch events during the scratching. If the user scratches very quickly, the scroll will lock in place
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        boolean handled = super.dispatchTouchEvent(ev);
        handled = mGesture.onTouchEvent(ev);
        return handled;
    }

    private GestureDetector.OnGestureListener mOnGesture = new GestureDetector.SimpleOnGestureListener() {

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                               float velocityY) {
            try {
                if (Math.abs(e1.getY() - e2.getY()) > SWIPE_MAX_OFF_PATH){

                    showToast(IndiQuizScreenView.this, "Scrolling Blocked To Allow Better Scratch Experience, Scroll Lightly to Unblock Scrolling");

                    // Disable Scrolling by setting up an OnTouchListener to do nothing
                    View current_page = presenter.getCurrentPage(pagerAdapter, pager);

                    current_page.setOnTouchListener((arg0, arg1) -> true);
                }else{

                    View current_page = presenter.getCurrentPage(pagerAdapter, pager);
                    current_page.setOnTouchListener(null);

                    showToast(IndiQuizScreenView.this, "Scrolling Not Blocked");

                }
                // right to left swipe/left to right swipe
                if (Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY || Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {

                    View current_page = presenter.getCurrentPage(pagerAdapter, pager);

                    current_page.setOnTouchListener((arg0, arg1) -> true);

                    showToast(IndiQuizScreenView.this, "Scrolling Blocked To Allow Better Scratch Experience, Scroll Lightly to Unblock Scrolling");
                }else{

                    View current_page = presenter.getCurrentPage(pagerAdapter, pager);
                    current_page.setOnTouchListener(null);

                    showToast(IndiQuizScreenView.this, "Scrolling Not Blocked");

                }

            } catch (Exception e) {

            }
            return false;
        }
    };
}
