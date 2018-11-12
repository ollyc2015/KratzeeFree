package uk.co.oliverbcurtis.Kratzee.ui.detail.lecturerEditQuestionSets.selectedTopicToEdit;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;

import uk.co.oliverbcurtis.Kratzee.model.Answer;
import uk.co.oliverbcurtis.Kratzee.model.Question;
import uk.co.oliverbcurtis.Kratzee.sqlite.KratzeeContract;
import uk.co.oliverbcurtis.Kratzee.sqlite.KratzeeDatabase;
import uk.co.oliverbcurtis.Kratzee.ui.common.BaseActivity;

public class SelectedTopicPresenter implements SelectedTopicContract.Presenter {

    SelectedTopicContract.View view;

    @Override
    public void attachView(SelectedTopicContract.View view) {

        this.view = view;

    }

    @Override
    public void
    getAllQuestions(KratzeeDatabase kratzeeDatabase) {

        List<String> array1 = null, array2 = null,array3 =null, array4 =null;

        //First, load all the question IDs into a list
        try {

            SQLiteDatabase db = kratzeeDatabase.getReadableDatabase();
            String selectQuery = "SELECT " + KratzeeContract.QUESTION_ID + " FROM " + KratzeeDatabase.QUESTION_TABLE;
            Cursor cursor = db.rawQuery(selectQuery, null);

            if (cursor != null) {
                //as long as the cursor is not null, create a list to store the questions in
                array1 = new ArrayList<>();
                //move cursor to position 0 of the returned results (the first question)
                if (cursor.moveToFirst()) {
                    //As long as there are questions, keep looping them into the results variable
                    while (!cursor.isAfterLast()) {
                        String results = cursor.getString(cursor.getColumnIndex(KratzeeContract.QUESTION_ID));
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

        //Load in the questions from the SQLite DB
        try {

            SQLiteDatabase db = kratzeeDatabase.getReadableDatabase();
            String selectQuery = "SELECT " + KratzeeContract.QUESTION_STRING + " FROM " + KratzeeDatabase.QUESTION_TABLE;
            Cursor cursor = db.rawQuery(selectQuery, null);

            if (cursor != null) {
                //as long as the cursor is not null, create a list to store the questions in
                array2 = new ArrayList<>();
                //move cursor to position 0 of the returned results (the first question)
                if (cursor.moveToFirst()) {
                    //As long as there are questions, keep looping them into the results variable
                    while (!cursor.isAfterLast()) {
                        String results = cursor.getString(cursor.getColumnIndex(KratzeeContract.QUESTION_STRING));
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

        //next, load in all the answers
        try {
            SQLiteDatabase db = kratzeeDatabase.getReadableDatabase();
            String selectQuery = "SELECT " + KratzeeContract.ANSWER_STRING + " FROM " + KratzeeDatabase.ANSWER_TABLE;
            Cursor cursor = db.rawQuery(selectQuery, null);

            if (cursor != null) {
                //as long as the cursor is not null, create a list to store the questions in
                array3 = new ArrayList<String>();
                //move cursor to position 0 of the returned results (the first question)
                if (cursor.moveToFirst()) {
                    //As long as there are questions, keep looping them into the results variable
                    while (!cursor.isAfterLast()) {
                        String results = cursor.getString(cursor.getColumnIndex(KratzeeContract.ANSWER_STRING));
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

        //next, load in if the answer is correct or incorrect
        try {
            SQLiteDatabase db = kratzeeDatabase.getReadableDatabase();
            String selectQuery = "SELECT " + KratzeeContract.CORRECT + " FROM " + KratzeeDatabase.ANSWER_TABLE;
            Cursor cursor = db.rawQuery(selectQuery, null);

            if (cursor != null) {
                //as long as the cursor is not null, create a list to store the questions in
                array4 = new ArrayList<String>();
                //move cursor to position 0 of the returned results (the first question)
                if (cursor.moveToFirst()) {
                    //As long as there are questions, keep looping them into the results variable
                    while (!cursor.isAfterLast()) {
                        String results = cursor.getString(cursor.getColumnIndex(KratzeeContract.CORRECT));
                        //then add them to the array that was created earlier
                        array4.add(results);
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

        generateQuestionButton(array1, array2, array3, array4);
    }


    @Override
    public void generateQuestionButton(List<String> questionIDList, List<String> questionList, List<String> answerList, List<String> markedCorrectList){

        if(questionIDList != null && questionList != null && answerList != null && markedCorrectList != null){

            Question question = new Question();
            question.setQuestionIDList(questionIDList);
            question.setQuestionStringList(questionList);

            Answer answer = new Answer();
            answer.setAnswerStringList(answerList);
            answer.setIsAnswerCorrectList(markedCorrectList);

            DynamicQuestionButton dynamicQuestionButton = new DynamicQuestionButton();
            dynamicQuestionButton.createButton((SelectedTopicView) view, question, answer);

        }else{

            BaseActivity.showToast((Context) view, "Unable to Load Questions");
        }

    }

}
