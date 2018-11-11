package uk.co.oliverbcurtis.Kratzee.ui.detail.individualQuizScreen;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
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

import uk.co.oliverbcurtis.Kratzee.R;
import uk.co.oliverbcurtis.Kratzee.model.Answer;
import uk.co.oliverbcurtis.Kratzee.model.Question;
import uk.co.oliverbcurtis.Kratzee.sqlite.KratzeeContract;
import uk.co.oliverbcurtis.Kratzee.sqlite.KratzeeDatabase;
import uk.co.oliverbcurtis.Kratzee.ui.common.BaseActivity;
import uk.co.oliverbcurtis.Kratzee.ui.common.MainPagerAdapter;
import uk.co.oliverbcurtis.Kratzee.ui.common.SwipeDisabledViewPager;


public class IndiQuizScreenPresenter extends BaseActivity implements IndiQuizScreenContract.Presenter {

    private IndiQuizScreenContract.View view;
    private List<String> array1,array2,array3;
    private View individual_layout;
    private IndiScratchThreshold indiScratchThreshold = new IndiScratchThreshold();

    @Override
    public void selectQuestions(MainPagerAdapter pagerAdapter, SwipeDisabledViewPager pager, KratzeeDatabase kratzeeDatabase) {

        pager.setAdapter(pagerAdapter);

        //Load in the questions from the SQLite DB
        try {

            SQLiteDatabase db = kratzeeDatabase.getReadableDatabase();
            String selectQuery = "SELECT " + KratzeeContract.QUESTION_STRING + " FROM " + KratzeeDatabase.QUESTION_TABLE;
            Cursor cursor = db.rawQuery(selectQuery, null);

            if (cursor != null) {
                //as long as the cursor is not null, create a list to store the questions in
                this.array1 = new ArrayList<String>();
                //move cursor to position 0 of the returned results (the first question)
                if (cursor.moveToFirst()) {
                    //As long as there are questions, keep looping them into the results variable
                    while (!cursor.isAfterLast()) {
                        String results = cursor.getString(cursor.getColumnIndex(KratzeeContract.QUESTION_STRING));
                        //then add them to the array that was created earlier
                        this.array1.add(results);
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

        loadLayouts(pager, pagerAdapter);
    }


    @Override
    public void loadLayouts(final SwipeDisabledViewPager pager, MainPagerAdapter pagerAdapter){

        int questionNumber = 1;
        int questionString = 0;
        int answerString = 0;


        for (int i = 0; i < array1.size(); i++){

            //disable the loaded view in viewpager
            pager.setAllowedSwipeDirection(SwipeDisabledViewPager.SwipeDirection.none);

            //loop out the number of layouts relative to the number of questions held in x
            individual_layout = LayoutInflater.from((Context) view).inflate(R.layout.indi_quiz_screen, null);

            //Use the pageAdapter to add the layout to the users view
            pagerAdapter.addView(individual_layout);

            //Add the page indicator
            pageIndicator(individual_layout, pager);

            //Make reference to the progress bar
            getProgressBar();

            //Disable the users ability to swipe
            disableSwipe(pager);

            //Call method to give the scratches functionality and deal with the point scoring
            indiScratchThreshold.indi_scratch_pads(individual_layout, array1, pager, view);

            //Add an onclick listener to the submit button (only visible on the last question)
            submitButton(individual_layout, pager);

            //Add onClick listener to the pagination buttons
            nextQuestionButton(individual_layout, pager);
            previousQuestionButton(individual_layout, pager);

            //Set the question string
            setQuestions(individual_layout, questionNumber, questionString);

            //Set the answers
            Answer answer = new Answer();
            answer.setAnswerString(array2.get(answerString));
            answer.setIsAnswerCorrect(array3.get(answerString));
            //Then set answer string
            ((TextView) individual_layout.findViewById(R.id.tv_answer1)).setText(answer.getAnswerString());
            //Then if that answer is correct or not
            if (answer.getIsAnswerCorrect().contains("Correct")) {
                individual_layout.findViewById(R.id.star1).setVisibility(View.VISIBLE);
            }
            answerString++;

            answer.setAnswerString(array2.get(answerString));
            answer.setIsAnswerCorrect(array3.get(answerString));
            ((TextView) individual_layout.findViewById(R.id.tv_answer2)).setText(answer.getAnswerString());
            if (answer.getIsAnswerCorrect().contains("Correct")) {
                individual_layout.findViewById(R.id.star2).setVisibility(View.VISIBLE);
            }
            answerString++;

            answer.setAnswerString(array2.get(answerString));
            answer.setIsAnswerCorrect(array3.get(answerString));
            ((TextView) individual_layout.findViewById(R.id.tv_answer3)).setText(answer.getAnswerString());
            if (answer.getIsAnswerCorrect().contains("Correct")) {
                individual_layout.findViewById(R.id.star3).setVisibility(View.VISIBLE);
            }
            answerString++;

            answer.setAnswerString(array2.get(answerString));
            answer.setIsAnswerCorrect(array3.get(answerString));
            ((TextView) individual_layout.findViewById(R.id.tv_answer4)).setText(answer.getAnswerString());
            if (answer.getIsAnswerCorrect().contains("Correct")) {
                individual_layout.findViewById(R.id.star4).setVisibility(View.VISIBLE);
            }

            answerString++;
            questionNumber++;
            questionString++;


            //Below is needed to cache the pages added to the pager, so that the scratch feature works on all pages
            pager.setOffscreenPageLimit(i);

            pagerAdapter.notifyDataSetChanged();
            //Once the user is on the last page, there is no longer a need to cache all the scratches as the user can scroll back to
            //see their previous answers without risking scratching by mistake
            removeScratchFunctionality(pager);

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
    public void submitButton(View individual_layout, SwipeDisabledViewPager pager){

        (individual_layout.findViewById(R.id.btn_submit_question)).setOnClickListener((View.OnClickListener) view);
        ((View.OnClickListener) view).onClick(individual_layout);

        //If the user isn't one the last page, hide the submit button
        if (pager.getAdapter().getCount()!= array1.size()) {

            individual_layout.findViewById(R.id.btn_submit_question).setVisibility(View.GONE);
        }
    }

    @Override
    public void nextQuestionButton(View individual_layout, SwipeDisabledViewPager pager){

        (individual_layout.findViewById(R.id.btn_next)).setOnClickListener((View.OnClickListener) view);
        ((View.OnClickListener) view).onClick(individual_layout);

        if (pager.getAdapter().getCount()== array1.size()) {
            individual_layout.findViewById(R.id.btn_next).setVisibility(View.GONE);
        }
    }

    @Override
    public void previousQuestionButton(View individual_layout, SwipeDisabledViewPager pager){

        (individual_layout.findViewById(R.id.btn_previous)).setOnClickListener((View.OnClickListener) view);
        ((View.OnClickListener) view).onClick(individual_layout);


        if (pager.getAdapter().getItemPosition(individual_layout) == 0 ) {
            individual_layout.findViewById(R.id.btn_previous).setVisibility(View.GONE);
        }

    }

    @Override
    public ProgressBar getProgressBar(){

        ProgressBar progress =(individual_layout.findViewById(R.id.progress));

        return progress;
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
    public void disableSwipe(SwipeDisabledViewPager pager){

        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            public void onPageScrollStateChanged(int state) {}
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }
            public void onPageSelected(int position) {

                //Set the direction the user can swipe in
                pager.setAllowedSwipeDirection(SwipeDisabledViewPager.SwipeDirection.none);

            }
        });
    }

    @Override
    public void removeScratchFunctionality(SwipeDisabledViewPager pager){

        if (pager.getAdapter().getCount()== array1.size()) {

            pager.setOffscreenPageLimit(1);
        }
    }


    @Override
    public void attachView(IndiQuizScreenContract.View view) {

        this.view = view;
    }

    // Here's what the app should do to get the currently displayed page.
    @Override
    public View getCurrentPage(MainPagerAdapter pagerAdapter, SwipeDisabledViewPager pager)
    {
        return pagerAdapter.getView (pager.getCurrentItem());
    }

    public List<String> returnQuestionArray(){

        return array1;
    }
}
