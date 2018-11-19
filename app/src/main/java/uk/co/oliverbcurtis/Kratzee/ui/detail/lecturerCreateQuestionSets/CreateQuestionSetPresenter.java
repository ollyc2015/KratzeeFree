package uk.co.oliverbcurtis.Kratzee.ui.detail.lecturerCreateQuestionSets;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ProgressBar;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.oliverbcurtis.Kratzee.async.ServerRequest;
import uk.co.oliverbcurtis.Kratzee.async.ServerResponse;
import uk.co.oliverbcurtis.Kratzee.async.remote.ApiUtils;
import uk.co.oliverbcurtis.Kratzee.async.remote.OperationAPI;
import uk.co.oliverbcurtis.Kratzee.model.Answer;
import uk.co.oliverbcurtis.Kratzee.model.Constants;
import uk.co.oliverbcurtis.Kratzee.model.Question;
import uk.co.oliverbcurtis.Kratzee.sqlite.KratzeeContract;
import uk.co.oliverbcurtis.Kratzee.sqlite.KratzeeDatabase;
import uk.co.oliverbcurtis.Kratzee.ui.common.BaseActivity;
import static uk.co.oliverbcurtis.Kratzee.sqlite.KratzeeDatabase.ANSWER_TABLE;
import static uk.co.oliverbcurtis.Kratzee.sqlite.KratzeeDatabase.QUESTION_TABLE;

public class CreateQuestionSetPresenter implements CreateQuestionSetContract.Presenter {

    private CreateQuestionSetContract.View view;
    private DynamicNewQuestionButton dynamicNewQuestionButton = new DynamicNewQuestionButton();
    private OperationAPI apiService  = ApiUtils.getApiService();


    @Override
    public void attachView(CreateQuestionSetContract.View view) {

        this.view = view;

    }


    @Override
    public void addQuestionToSQLite(String question, String answer1, String answer2, String answer3, String answer4, CheckBox cb_answer1_edit, CheckBox cb_answer2_edit, CheckBox cb_answer3_edit, CheckBox cb_answer4_edit, ProgressBar progress, AlertDialog dialog, KratzeeDatabase kratzeeDatabase, String topic, SharedPreferences pref, CreateQuestionSetView view){

        try {

            android.database.sqlite.SQLiteDatabase db = kratzeeDatabase.getWritableDatabase();

            String questionId = UUID.randomUUID().toString();
            //Inserting the answer list into the SQLite DB when the getQuestions method is called
            ContentValues contentValues = new ContentValues();
            contentValues.put(KratzeeContract.QUESTION_ID, questionId);
            contentValues.put(KratzeeContract.QUESTION_STRING, question);
            contentValues.put(KratzeeContract.QUESTION_LECTURER_ID,  pref.getString(Constants.LECTURER_ID, ""));
            contentValues.put(KratzeeContract.QUESTION_TOPIC, topic);
            //insert rows
            long result = db.insert(QUESTION_TABLE, null, contentValues);

            if (result == -1) {
                BaseActivity.showToast((Context) view, "Unable to add answers to SQLite DB");
            }

            db.close();

            addAnswersToSQLite(questionId, answer1, answer2, answer3, answer4, cb_answer1_edit, cb_answer2_edit, cb_answer3_edit, cb_answer4_edit, kratzeeDatabase, progress, pref, view, dialog);

        }catch (Exception ex) {
            Log.e("Error", ex.toString());
        }

    }

    @Override
    public void addAnswersToSQLite(String questionId, String answer1, String answer2, String answer3, String answer4, CheckBox cb_answer1_edit, CheckBox cb_answer2_edit, CheckBox cb_answer3_edit, CheckBox cb_answer4_edit, KratzeeDatabase kratzeeDatabase, ProgressBar progress, SharedPreferences pref, CreateQuestionSetView view, AlertDialog dialog){

        List<String> answers = new ArrayList<>();
        answers.add(answer1);
        answers.add(answer2);
        answers.add(answer3);
        answers.add(answer4);

        List<String> isCorrect = new ArrayList<>();

        if (cb_answer1_edit.isChecked()){
            isCorrect.add("Correct");
        }else{
            isCorrect.add("Incorrect");
        }
        if (cb_answer2_edit.isChecked()){
            isCorrect.add("Correct");
        }else{
            isCorrect.add("Incorrect");
        }
        if (cb_answer3_edit.isChecked()){
            isCorrect.add("Correct");
        }else{
            isCorrect.add("Incorrect");
        }
        if (cb_answer4_edit.isChecked()){
            isCorrect.add("Correct");
        }else{
            isCorrect.add("Incorrect");
        }

        try {

            android.database.sqlite.SQLiteDatabase db = kratzeeDatabase.getWritableDatabase();

            for(int i = 0; i < answers.size(); i++) {

                String answerId = UUID.randomUUID().toString();
                //Inserting the answer list into the SQLite DB when the getQuestions method is called
                ContentValues contentValues = new ContentValues();
                contentValues.put(KratzeeContract.ANSWER_ID, answerId);
                contentValues.put(KratzeeContract.ANSWER_STRING, answers.get(i));
                contentValues.put(KratzeeContract.CORRECT, isCorrect.get(i));
                contentValues.put(KratzeeContract.QUESTION_ID, questionId);
                contentValues.put(KratzeeContract.ANSWER_LECTURER_ID, pref.getString(Constants.LECTURER_ID, ""));

                //insert rows
                long result = db.insert(ANSWER_TABLE, null, contentValues);

                if (result == -1) {
                    BaseActivity.showToast((Context) this.view, "Unable to add answers to SQLite DB");
                }

            }

            db.close();

            progress.setVisibility(View.INVISIBLE);
            dialog.dismiss();
            dynamicNewQuestionButton.createButton(view, questionId);

        }catch (Exception ex) {
            Log.e("Error", ex.toString());
        }
    }


    @Override
    public void getQuestionsFromSQLite(KratzeeDatabase kratzeeDatabase, SharedPreferences pref, ProgressBar progress){
        try {

        SQLiteDatabase db1 = kratzeeDatabase.getReadableDatabase();
        String selectQuery = "SELECT " + KratzeeContract.QUESTION_STRING +","
                + KratzeeContract.QUESTION_TOPIC +" FROM " + KratzeeDatabase.QUESTION_TABLE;
        Cursor cursor = db1.rawQuery(selectQuery, null);

        if (cursor != null) {
            //as long as the cursor is not null, create a list to store the individual details in
            List<String> questionStringArray = new ArrayList<String>();
            List<String> questionTopicStringArray = new ArrayList<String>();
            //move cursor to position 0 of the returned results (the students first name)
            if (cursor.moveToFirst()) {
                //As long as there are more details (student number etc), keep looping them into the results variable
                while (!cursor.isAfterLast()) {
                    String questionString = cursor.getString(cursor.getColumnIndex(KratzeeContract.QUESTION_STRING));
                    String questionTopic = cursor.getString(cursor.getColumnIndex(KratzeeContract.QUESTION_TOPIC));
                    //then add them to the array that was created earlier
                    questionStringArray.add(questionString);
                    questionTopicStringArray.add(questionTopic);
                    //after adding the first question, move the cursor to the next position if questions exist
                    cursor.moveToNext();
                }

               getAnswersFromSQLite(questionStringArray, questionTopicStringArray, kratzeeDatabase, pref, progress);
            }
        }

        cursor.close();
        db1.close();

        } catch (SQLiteException se) {
            progress.setVisibility(View.INVISIBLE);
            BaseActivity.showToast((Context) view, "Could not create or Open the database");
        }
    }

    @Override
    public void getAnswersFromSQLite(List<String> questionStringArray, List<String> questionTopicStringArray, KratzeeDatabase kratzeeDatabase, SharedPreferences pref, ProgressBar progress){

        try {

            SQLiteDatabase db1 = kratzeeDatabase.getReadableDatabase();
            String selectQuery = "SELECT " + KratzeeContract.ANSWER_STRING + ","
                    + KratzeeContract.CORRECT + " FROM " + KratzeeDatabase.ANSWER_TABLE;
            Cursor cursor = db1.rawQuery(selectQuery, null);

            if (cursor != null) {
                //as long as the cursor is not null, create a list to store the individual details in
                List<String> answerStringArray = new ArrayList<String>();
                List<String> correctArray = new ArrayList<String>();
                //move cursor to position 0 of the returned results (the students first name)
                if (cursor.moveToFirst()) {
                    //As long as there are more details (student number etc), keep looping them into the results variable
                    while (!cursor.isAfterLast()) {
                        String answerString = cursor.getString(cursor.getColumnIndex(KratzeeContract.ANSWER_STRING));
                        String isAnswerCorrect = cursor.getString(cursor.getColumnIndex(KratzeeContract.CORRECT));
                        //then add them to the array that was created earlier
                        answerStringArray.add(answerString);
                        correctArray.add(isAnswerCorrect);
                        //after adding the first question, move the cursor to the next position if questions exist
                        cursor.moveToNext();
                    }

                    submitQuestions(questionStringArray, questionTopicStringArray, answerStringArray, correctArray, pref, progress);
                }
            }
        } catch (SQLiteException se) {
            progress.setVisibility(View.INVISIBLE);
            BaseActivity.showToast((Context) view, "Could not create or Open the database");
    }
    }


    @Override
    public void submitQuestions(List<String> questionStringArray, List<String> questionTopicStringArray, List<String> answerStringArray, List<String> correctArray, SharedPreferences pref, ProgressBar progress){

        List<Object> completedQuestionObject = new ArrayList<Object>();

        Question question = new Question();
        question.setQuestionStringList(questionStringArray);
        question.setQuestionTopicList(questionTopicStringArray);
        question.setLecturerID(pref.getString(Constants.LECTURER_ID, ""));

        Answer answer = new Answer();
        answer.setAnswerStringList(answerStringArray);
        answer.setIsAnswerCorrectList(correctArray);

        completedQuestionObject.add(question);
        completedQuestionObject.add(answer);


        final ServerRequest request = new ServerRequest();
        request.setOperation(Constants.ADD_NEW_TOPIC);
        //set the values entered for the pin entered
        request.setNewlyAddedTopicQuestions(completedQuestionObject);

        apiService.operation(request).enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {

                ServerResponse resp = response.body();

                if (resp.getResult().equals(Constants.SUCCESS)) {

                    BaseActivity.showToast((Context) view, resp.getMessage());
                    progress.setVisibility(View.INVISIBLE);



                } else {
                    String messageFromServer = resp.getMessage();
                    BaseActivity.showToast((Context) view, messageFromServer);
                    progress.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                BaseActivity.showToast((Context) view, t.toString());
            }
        });
    }
}

