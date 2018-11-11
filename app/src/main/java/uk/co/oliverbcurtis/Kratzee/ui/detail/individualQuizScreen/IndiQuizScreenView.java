package uk.co.oliverbcurtis.Kratzee.ui.detail.individualQuizScreen;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ProgressBar;

import java.util.List;

import uk.co.oliverbcurtis.Kratzee.R;
import uk.co.oliverbcurtis.Kratzee.ui.common.BaseActivity;
import uk.co.oliverbcurtis.Kratzee.ui.common.MainPagerAdapter;
import uk.co.oliverbcurtis.Kratzee.ui.common.SubmitPoints;
import uk.co.oliverbcurtis.Kratzee.ui.common.SwipeDisabledViewPager;
import uk.co.oliverbcurtis.Kratzee.ui.detail.indiTriviaMainScreen.IndiTriviaRegisterView;
import uk.co.oliverbcurtis.Kratzee.ui.detail.quizType.QuizTypeView;


public class IndiQuizScreenView extends BaseActivity implements IndiQuizScreenContract.View, View.OnClickListener  {

    private IndiQuizScreenPresenter presenter;
    private SwipeDisabledViewPager pager;
    private MainPagerAdapter pagerAdapter;
    private SubmitPoints submitPoints;


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

        presenter.selectQuestions(pagerAdapter, pager , kratzeeDatabase);
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


    public void backToMainMenu(){

        Intent intent = new Intent(getApplicationContext(), QuizTypeView.class);
        startActivity(intent);
    }


    //We are overriding this method so that users cannot simply back out of the quiz
    @Override
    public void onBackPressed() {

      //Below is what causes the activity to go to the previous activity
      //super.onBackPressed();
    }
}
