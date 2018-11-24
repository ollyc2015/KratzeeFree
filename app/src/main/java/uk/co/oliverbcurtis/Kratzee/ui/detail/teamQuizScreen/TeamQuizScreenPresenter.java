package uk.co.oliverbcurtis.Kratzee.ui.detail.teamQuizScreen;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.rd.PageIndicatorView;
import com.rd.animation.type.AnimationType;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import uk.co.oliverbcurtis.Kratzee.R;
import uk.co.oliverbcurtis.Kratzee.model.Answer;
import uk.co.oliverbcurtis.Kratzee.model.Constants;
import uk.co.oliverbcurtis.Kratzee.model.Question;
import uk.co.oliverbcurtis.Kratzee.model.Score;
import uk.co.oliverbcurtis.Kratzee.sqlite.KratzeeContract;
import uk.co.oliverbcurtis.Kratzee.sqlite.KratzeeDatabase;
import uk.co.oliverbcurtis.Kratzee.ui.common.MainPagerAdapter;
import uk.co.oliverbcurtis.Kratzee.ui.common.SwipeDisabledViewPager;

public class TeamQuizScreenPresenter implements TeamQuizScreenContract.Presenter {

    private TeamQuizScreenContract.View view;
    private List<String> array1,array2,array3;
    private View team_layout;
    private TeamScratchThreshold teamScratchThreshold = new TeamScratchThreshold();
    private Handler handler = new Handler(Looper.getMainLooper());


    @Override
    public void selectQuestions(MainPagerAdapter pagerAdapter, SwipeDisabledViewPager pager, KratzeeDatabase kratzeeDatabase, SharedPreferences pref) {

        pager.setAdapter(pagerAdapter);

        //Load in the questions from the SQLite DB
        try {

            SQLiteDatabase db = kratzeeDatabase.getReadableDatabase();
            String selectQuery = "SELECT " + KratzeeContract.QUESTION_STRING + " FROM " + KratzeeDatabase.QUESTION_TABLE;
            Cursor cursor = db.rawQuery(selectQuery, null);

            if (cursor != null) {
                //as long as the cursor is not null, create a list to store the questions in
                array1 = new ArrayList<String>();
                //move cursor to position 0 of the returned results (the first question)
                if (cursor.moveToFirst()) {
                    //As long as there are questions, keep looping them into the results variable
                    while (!cursor.isAfterLast()) {
                        String results = cursor.getString(cursor.getColumnIndex(KratzeeContract.QUESTION_STRING));
                        //then add them to the array that was created earlier
                        array1.add(results);
                        //after adding the first question, move the cursor to the next position if questions exist
                        cursor.moveToNext();
                    }
                }
            }
            cursor.close();
            db.close();
        } catch (SQLiteException se) {
            Log.e(getClass().getSimpleName(), "Could not create or Open the database");
        }

        //next, load in all the answers
        try {
            SQLiteDatabase db = kratzeeDatabase.getReadableDatabase();
            String selectQuery = "SELECT " + KratzeeContract.ANSWER_STRING + " FROM " + KratzeeDatabase.ANSWER_TABLE;
            Cursor cursor = db.rawQuery(selectQuery, null);

            if (cursor != null) {
                //as long as the cursor is not null, create a list to store the questions in
                array2 = new ArrayList<String>();
                //move cursor to position 0 of the returned results (the first question)
                if (cursor.moveToFirst()) {
                    //As long as there are questions, keep looping them into the results variable
                    while (!cursor.isAfterLast()) {
                        String results = cursor.getString(cursor.getColumnIndex(KratzeeContract.ANSWER_STRING));
                        //then add them to the array that was created earlier
                        array2.add(results);
                        //after adding the first question, move the cursor to the next position if questions exist
                        cursor.moveToNext();
                    }
                }
            }
            cursor.close();
            db.close();
        } catch (SQLiteException se) {
            Log.e(getClass().getSimpleName(), "Could not create or Open the database");
        }

        //next, load in if the answer is correct or incorrect
        try {
            SQLiteDatabase db = kratzeeDatabase.getReadableDatabase();
            String selectQuery = "SELECT " + KratzeeContract.CORRECT + " FROM " + KratzeeDatabase.ANSWER_TABLE;
            Cursor cursor = db.rawQuery(selectQuery, null);

            if (cursor != null) {
                //as long as the cursor is not null, create a list to store the questions in
                array3 = new ArrayList<String>();
                //move cursor to position 0 of the returned results (the first question)
                if (cursor.moveToFirst()) {
                    //As long as there are questions, keep looping them into the results variable
                    while (!cursor.isAfterLast()) {
                        String results = cursor.getString(cursor.getColumnIndex(KratzeeContract.CORRECT));
                        //then add them to the array that was created earlier
                        array3.add(results);
                        //after adding the first question, move the cursor to the next position if questions exist
                        cursor.moveToNext();
                    }
                }
            }
            cursor.close();
            db.close();
        } catch (SQLiteException se) {
            Log.e(getClass().getSimpleName(), "Could not create or Open the database");
        }

        loadLayouts(pager, pagerAdapter, pref);
    }


    @Override
    public void loadLayouts(final SwipeDisabledViewPager pager, MainPagerAdapter pagerAdapter, SharedPreferences pref){

        int questionNumber = 1;
        int questionString = 0;
        int answerString = 0;


        for (int i = 0; i < array1.size(); i++){

            //disable the loaded view in viewpager
            pager.setAllowedSwipeDirection(SwipeDisabledViewPager.SwipeDirection.none);

            //loop out the number of layouts relative to the number of questions held in x
            team_layout = LayoutInflater.from((Context) view).inflate(R.layout.team_quiz_screen, null);

            //Use the pageAdapter to add the layout to the users view
            pagerAdapter.addView(team_layout);

            //Add the page indicator
            pageIndicator(team_layout, pager);

            //Make reference to the progress bar
            getProgressBar();

            //Disable the users ability to swipe
            disableSwipe(pager, pagerAdapter, team_layout);

            //Call method to give the scratches functionality and deal with the point scoring
            teamScratchThreshold.team_scratch_pads(team_layout, array1, pager, array3, answerString, view);

            //Add an onclick listener to the submit button (only visible on the last question)
            submitButton(team_layout, pager);

            //Add onClick listener to the pagination buttons
            nextQuestionButton(team_layout, pager);
            previousQuestionButton(team_layout, pager);

            //Set the question string
            setQuestions(team_layout, questionNumber, questionString);

            //Set the answers
            Answer answer = new Answer();

            //Answer 1
            answer.setAnswerString(array2.get(answerString));
            ((TextView) team_layout.findViewById(R.id.tv_answer1)).setText(answer.getAnswerString());
            answerString++;

            //Answer 2
            answer.setAnswerString(array2.get(answerString));
            ((TextView) team_layout.findViewById(R.id.tv_answer2)).setText(answer.getAnswerString());
            answerString++;

            //Answer 3
            answer.setAnswerString(array2.get(answerString));
            ((TextView) team_layout.findViewById(R.id.tv_answer3)).setText(answer.getAnswerString());
            answerString++;

            //Answer 4
            answer.setAnswerString(array2.get(answerString));
            ((TextView) team_layout.findViewById(R.id.tv_answer4)).setText(answer.getAnswerString());
            answerString++;

            questionNumber++;
            questionString++;

            //Below is needed to cache the pages added to the pager, so that if a user skips a question, they can come back to it and
            //the scratch will still function
            pager.setOffscreenPageLimit(i);

            //make sure the adapter updates after each iteration so that the new layout can be created in the viewpager
            pagerAdapter.notifyDataSetChanged();

            //Once the user is on the last page, there is no longer a need to cache all the scratches as the user can scroll back to
            //see their previous answers without risking scratching by mistake
            removeScratchFunctionality(pager);

        }

        //If the user has decided to take the tutorial, start the first tutorial for this screen
        if(pref.getBoolean(Constants.DEMO_REQUEST_MADE,true)) {

            //use view post to add the demo to the queue so the main thread isn't over used
            handler.post(() -> {

                // use showcaseview (demo) here...
                if (pager.getCurrentItem() == 0) {

                    view.showDemo(team_layout);

                }
            });
        }


    }

    @Override
    public void setQuestions(View group_layout, int questionNumber, int questionString){

        Question question = new Question();
        question.setQuestionNumber(questionNumber);
        question.setQuestionString(array1.get(questionString));

        //Set the title of the all the views - Question 1, Question 2 etc
        ((TextView) group_layout.findViewById(R.id.tv_question_number)).setText("Question "+question.getQuestionNumber());
        //Set the question string
        ((TextView) group_layout.findViewById(R.id.tv_question)).setText(question.getQuestionString());

    }

    @Override
    public void submitButton(View group_layout, SwipeDisabledViewPager pager){

        (group_layout.findViewById(R.id.btn_submit_question)).setOnClickListener((View.OnClickListener) view);
        ((View.OnClickListener) view).onClick(group_layout);

        //Set submit button on the last layout in the viewpager
        if (Objects.requireNonNull(pager.getAdapter()).getCount()!= array1.size()) {

            group_layout.findViewById(R.id.btn_submit_question).setVisibility(View.GONE);
        }
    }

    @Override
    public void nextQuestionButton(View group_layout, SwipeDisabledViewPager pager){

        (group_layout.findViewById(R.id.btn_next)).setOnClickListener((View.OnClickListener) view);
        ((View.OnClickListener) view).onClick(group_layout);

        if (pager.getAdapter().getCount()== array1.size()) {
            group_layout.findViewById(R.id.btn_next).setVisibility(View.GONE);
        }
    }

    @Override
    public void previousQuestionButton(View group_layout, SwipeDisabledViewPager pager){

        (group_layout.findViewById(R.id.btn_previous)).setOnClickListener((View.OnClickListener) view);
        ((View.OnClickListener) view).onClick(group_layout);

        if (pager.getAdapter().getItemPosition(group_layout) == 0 ) {
            group_layout.findViewById(R.id.btn_previous).setVisibility(View.GONE);
        }
    }

    @Override
    public ProgressBar getProgressBar(){

        return (team_layout.findViewById(R.id.progress));
    }

    @Override
    public void pageIndicator(View group_layout, SwipeDisabledViewPager pager){

        //Page indicator
        PageIndicatorView pageIndicatorView = group_layout.findViewById(R.id.pageIndicatorView);
        pageIndicatorView.setViewPager(pager);
        pageIndicatorView.setInteractiveAnimation(true);
        pageIndicatorView.setAnimationType(AnimationType.DROP);
        pageIndicatorView.setAutoVisibility(true);
        pageIndicatorView.setDynamicCount(true);
    }

    @Override
    public void disableSwipe(SwipeDisabledViewPager pager, MainPagerAdapter pagerAdapter, View team_layout){

        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            public void onPageScrollStateChanged(int state) {}
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                if (getCurrentPage(pagerAdapter, pager) == team_layout) {
                    ((TextView) team_layout.findViewById(R.id.tv_current_score)).setText("" + Score.getScore());
                }

            }
            public void onPageSelected(int position) {

                //Set the direction the user can swipe in
                pager.setAllowedSwipeDirection(SwipeDisabledViewPager.SwipeDirection.none);

            }
        });
    }

    @Override
    public List<String> returnQuestionArray(){

        return array1;
    }


    @Override
    public void removeScratchFunctionality(SwipeDisabledViewPager pager){

        if (pager.getAdapter().getCount()== array1.size()) {

            pager.setOffscreenPageLimit(1);
        }
    }


    // Here's what the app should do to get the currently displayed page.
    @Override
    public View getCurrentPage(MainPagerAdapter pagerAdapter, SwipeDisabledViewPager pager)
    {
        return pagerAdapter.getView (pager.getCurrentItem());
    }



    @Override
    public void attachView(TeamQuizScreenContract.View view) {

        this.view = view;

    }
}
