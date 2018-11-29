package uk.co.oliverbcurtis.Kratzee.ui.detail.teamQuizScreen;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ProgressBar;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import uk.co.oliverbcurtis.Kratzee.R;
import uk.co.oliverbcurtis.Kratzee.ui.common.BaseActivity;
import uk.co.oliverbcurtis.Kratzee.ui.common.MainPagerAdapter;
import uk.co.oliverbcurtis.Kratzee.ui.common.SubmitPoints;
import uk.co.oliverbcurtis.Kratzee.ui.common.SwipeDisabledViewPager;
import uk.co.oliverbcurtis.Kratzee.ui.detail.individualQuizScreen.IndiQuizScreenView;
import uk.co.oliverbcurtis.Kratzee.ui.detail.leaderboard.existingTeamLeaderboard.LeaderboardView;
import uk.co.oliverbcurtis.Kratzee.ui.detail.leaderboard.newTriviaTeamEndScreen.EndScreenView;
import uk.co.oliverbcurtis.Kratzee.ui.detail.startScreen.StartScreenView;
import uk.co.oliverbcurtis.Kratzee.ui.detail.teamTriviaRegister.teamMember.TeamMemberTriviaRegisterView;

public class TeamQuizScreenView extends BaseActivity implements TeamQuizScreenContract.View, View.OnClickListener {

    private TeamQuizScreenPresenter presenter;
    private SwipeDisabledViewPager pager;
    private MainPagerAdapter pagerAdapter;
    private SubmitPoints submitPoints;
    private Activity team_quiz;
    private boolean doubleBackToExitPressedOnce = false;
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

        presenter = new TeamQuizScreenPresenter();
        presenter.attachView(this);


        pagerAdapter = new MainPagerAdapter();
        pager = findViewById(R.id.pager);

        submitPoints = new SubmitPoints();

        presenter.selectQuestions(pagerAdapter, pager, kratzeeDatabase, pref);

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
    public void showDemo(View team_layout) {

        //Casting the view to activity is necessary as the showcase view tutorial requires an activity as a param
        team_quiz = (Activity) team_layout.getContext();

        tutorialView.teamQuizScreenTutorial1(team_quiz);

    }


    @Override
    public void submitPoints(ProgressBar progressBar) {

        //Saving the number of questions array in shared pref for use in the leader-board table
        List<String> questionArray = presenter.returnQuestionArray();
        SharedPreferences.Editor editor = pref.edit();
        Set<String> set = new HashSet<>(questionArray);
        editor.putStringSet("NumberOfQuestions", set).apply();

        //This is used to SUBMIT a NEW TRIVIA team to the external DB
        if (TeamMemberTriviaRegisterView.newTeamSubmitted) {

            submitPoints.submitNewTeamTriviaPoints(progressBar, this, kratzeeDatabase);

            //This method is used to UPDATE EXISTING TRIVIA and ASSESSMENT team profiles
        } else {

            submitPoints.submitExistingTeamTriviaPoints(progressBar, this, kratzeeDatabase);

        }
    }

    @Override
    public void goToPreviousScreen() {

        View current_page = presenter.getCurrentPage(pagerAdapter, pager);
        List<String> questionArray = presenter.returnQuestionArray();

        if (pager.getAdapter().getCount() == questionArray.size() && !current_page.findViewById(R.id.star1).isShown() && !current_page.findViewById(R.id.star2).isShown() &&
                !current_page.findViewById(R.id.star3).isShown() && !current_page.findViewById(R.id.star4).isShown()) {

            showToast(this, "You Can Review Your Previous Answers Once You Have Answered All of the Questions");

        } else {

            pager.setCurrentItem(pager.getCurrentItem() - 1);
        }
    }


    @Override
    public void goToNextScreen() {

        View current_page = presenter.getCurrentPage(pagerAdapter, pager);

        if (!current_page.findViewById(R.id.star1).isShown() && !current_page.findViewById(R.id.star2).isShown() &&
                !current_page.findViewById(R.id.star3).isShown() && !current_page.findViewById(R.id.star4).isShown()) {

            showToast(this, "Please Find the Star Before Moving On");

        } else {
            pager.setCurrentItem(pager.getCurrentItem() + 1);
        }
    }

    @Override
    public void showLeaderBoard() {

        Intent intent = new Intent(getApplicationContext(), LeaderboardView.class);
        startActivity(intent);
    }

    public void newTriviaRegisteredTeamEndScreen() {

        Intent intent = new Intent(getApplicationContext(), EndScreenView.class);
        startActivity(intent);

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

        new Handler().postDelayed(() -> doubleBackToExitPressedOnce = false, 2000);
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

                    showToast(TeamQuizScreenView.this, "Scrolling Blocked To Allow Better Scratch Experience, Scroll Lightly to Unblock Scrolling");

                    // Disable Scrolling by setting up an OnTouchListener to do nothing
                    View current_page = presenter.getCurrentPage(pagerAdapter, pager);

                    current_page.setOnTouchListener((arg0, arg1) -> true);
                }else{

                    View current_page = presenter.getCurrentPage(pagerAdapter, pager);
                    current_page.setOnTouchListener(null);

                    showToast(TeamQuizScreenView.this, "Scrolling Not Blocked");

                }
                // right to left swipe/left to right swipe
                if (Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY || Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {

                    View current_page = presenter.getCurrentPage(pagerAdapter, pager);

                    current_page.setOnTouchListener((arg0, arg1) -> true);

                    showToast(TeamQuizScreenView.this, "Scrolling Blocked To Allow Better Scratch Experience, Scroll Lightly to Unblock Scrolling");
                }else{

                    View current_page = presenter.getCurrentPage(pagerAdapter, pager);
                    current_page.setOnTouchListener(null);

                    showToast(TeamQuizScreenView.this, "Scrolling Not Blocked");

                }

            } catch (Exception e) {

            }
            return false;
        }
    };

}
