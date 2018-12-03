package uk.co.oliverbcurtis.Kratzee.ui.detail.lecturerEditQuestionSets.selectedTopicQuestions;

import android.app.AlertDialog;
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



public class SelectedTopicPresenter implements SelectedTopicContract.Presenter {

    private SelectedTopicContract.View view;
    private OperationAPI apiService  = ApiUtils.getApiService();
    private boolean messageShown =false;

    @Override
    public void attachView(SelectedTopicContract.View view) {

        this.view = view;

    }

    @Override
    public void getAllQuestions(KratzeeDatabase kratzeeDatabase) {

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
            BaseActivity.showToast((Context) view, "SQLite error "+se);
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
            BaseActivity.showToast((Context) view, "SQLite error "+se);
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
            BaseActivity.showToast((Context) view, "SQLite error "+se);
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
            BaseActivity.showToast((Context) view, "SQLite error "+se);
        }

        generateQuestionButton(array1, array2, array3, array4);
    }


    @Override
    public void generateQuestionButton(List<String> questionIDList, List<String> questionList, List<String> answerList, List<String> markedCorrectList){


        if(!questionIDList.isEmpty() && questionIDList != null){

            Question question = new Question();
            question.setQuestionIDList(questionIDList);
            question.setQuestionStringList(questionList);

            Answer answer = new Answer();
            answer.setAnswerStringList(answerList);
            answer.setIsAnswerCorrectList(markedCorrectList);

            DynamicQuestionButton dynamicQuestionButton = new DynamicQuestionButton();
            dynamicQuestionButton.createButton((SelectedTopicView) view, question);

        }else{
            //If there are no questionIDs, it is because the admin has deleted all the topic questions, rather than
            //deleting the topic. Therefore take them back to topic selection since there are no more questions for
            //the previously selected topic. This is also
           BaseActivity.showToast((Context) view, "Unable To Find any Questions, Taking you Back to Topic Selection");
        }
    }

    @Override
    public void getSelectQuestion(SharedPreferences pref, ProgressBar progress, String questionID, KratzeeDatabase kratzeeDatabase) {

        List<String> question_array = null, answer_id_array = null, answer_array = null, isAnswerCorrectArray = null;


        //Load in the questions from the SQLite DB
        try {

            SQLiteDatabase db = kratzeeDatabase.getReadableDatabase();
            String selectQuery = "SELECT " + KratzeeContract.QUESTION_STRING + " FROM " + KratzeeDatabase.QUESTION_TABLE +
                    " WHERE " + KratzeeContract.QUESTION_ID +" = "+questionID;

            Log.v("Select_Query", "Query ="+selectQuery);

            Cursor cursor = db.rawQuery(selectQuery, null);

            if (cursor != null) {
                //as long as the cursor is not null, create a list to store the questions in
                question_array = new ArrayList<>();
                //move cursor to position 0 of the returned results (the first question)
                if (cursor.moveToFirst()) {
                    //As long as there are questions, keep looping them into the results variable
                    while (!cursor.isAfterLast()) {
                        String results = cursor.getString(cursor.getColumnIndex(KratzeeContract.QUESTION_STRING));
                        //then add them to the array that was created earlier
                        question_array.add(results);
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

        //next, load in all the answer IDs
        try {
            SQLiteDatabase db = kratzeeDatabase.getReadableDatabase();
            String selectQuery = "SELECT " + KratzeeContract.ANSWER_ID + " FROM " + KratzeeDatabase.ANSWER_TABLE +
                    " WHERE " + KratzeeContract.QUESTION_ID +" = "+questionID;

            Cursor cursor = db.rawQuery(selectQuery, null);

            if (cursor != null) {
                //as long as the cursor is not null, create a list to store the questions in
                answer_id_array = new ArrayList<String>();
                //move cursor to position 0 of the returned results (the first question)
                if (cursor.moveToFirst()) {
                    //As long as there are questions, keep looping them into the results variable
                    while (!cursor.isAfterLast()) {
                        String results = cursor.getString(cursor.getColumnIndex(KratzeeContract.ANSWER_ID));
                        //then add them to the array that was created earlier
                        answer_id_array.add(results);
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
            String selectQuery = "SELECT " + KratzeeContract.ANSWER_STRING + " FROM " + KratzeeDatabase.ANSWER_TABLE +
                    " WHERE " + KratzeeContract.QUESTION_ID +" = "+questionID;

            Cursor cursor = db.rawQuery(selectQuery, null);

            if (cursor != null) {
                //as long as the cursor is not null, create a list to store the questions in
                answer_array = new ArrayList<String>();
                //move cursor to position 0 of the returned results (the first question)
                if (cursor.moveToFirst()) {
                    //As long as there are questions, keep looping them into the results variable
                    while (!cursor.isAfterLast()) {
                        String results = cursor.getString(cursor.getColumnIndex(KratzeeContract.ANSWER_STRING));
                        //then add them to the array that was created earlier
                        answer_array.add(results);
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
            String selectQuery = "SELECT " + KratzeeContract.CORRECT + " FROM " + KratzeeDatabase.ANSWER_TABLE +
                    " WHERE " + KratzeeContract.QUESTION_ID +" = "+questionID;

            Cursor cursor = db.rawQuery(selectQuery, null);

            if (cursor != null) {
                //as long as the cursor is not null, create a list to store the questions in
                isAnswerCorrectArray = new ArrayList<String>();
                //move cursor to position 0 of the returned results (the first question)
                if (cursor.moveToFirst()) {
                    //As long as there are questions, keep looping them into the results variable
                    while (!cursor.isAfterLast()) {
                        String results = cursor.getString(cursor.getColumnIndex(KratzeeContract.CORRECT));
                        //then add them to the array that was created earlier
                        isAnswerCorrectArray.add(results);
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

        view.showEditQuestionLayout(question_array, answer_id_array, answer_array, isAnswerCorrectArray, questionID, progress);

    }

    @Override
    public void updateQuestionExternalDB(String questionString, List<String> answer_id_array, String answerString1, String answerString2, String answerString3, String answerString4, CheckBox cb_answer1_edit, CheckBox cb_answer2_edit, CheckBox cb_answer3_edit, CheckBox cb_answer4_edit, String questionID, ProgressBar progress, AlertDialog dialog){

        String isChecked1, isChecked2, isChecked3, isChecked4;
        List<Question> questionObjectArray = new ArrayList<>();
        List<Answer> answerObjectArray = new ArrayList<>();

        if (cb_answer1_edit.isChecked()){
            isChecked1 = "Correct";
        }else{
            isChecked1 = "Incorrect";
        }

        if (cb_answer2_edit.isChecked()){
            isChecked2 = "Correct";
        }else{
            isChecked2 = "Incorrect";
        }

        if (cb_answer3_edit.isChecked()){
            isChecked3 = "Correct";
        }else{
            isChecked3 = "Incorrect";
        }

        if (cb_answer4_edit.isChecked()){
            isChecked4 = "Correct";
        }else{
            isChecked4 = "Incorrect";
        }


        Question question1;

        question1 = new Question();
        question1.setQuestionString(questionString);
        question1.setQuestionID(questionID);

        questionObjectArray.add(question1);

        Answer answer1, answer2, answer3, answer4;

        answer1 = new Answer();
        answer1.setAnswer_id(answer_id_array.get(0).trim());
        answer1.setAnswerString(answerString1);
        answer1.setIsAnswerCorrect(isChecked1);
        answer1.setQuestion_id(questionID);


        answer2 = new Answer();
        answer2.setAnswer_id(answer_id_array.get(1).trim());
        answer2.setAnswerString(answerString2);
        answer2.setIsAnswerCorrect(isChecked2);
        answer2.setQuestion_id(questionID);

        answer3 = new Answer();
        answer3.setAnswer_id(answer_id_array.get(2).trim());
        answer3.setAnswerString(answerString3);
        answer3.setIsAnswerCorrect(isChecked3);
        answer3.setQuestion_id(questionID);

        answer4 = new Answer();
        answer4.setAnswer_id(answer_id_array.get(3).trim());
        answer4.setAnswerString(answerString4);
        answer4.setIsAnswerCorrect(isChecked4);
        answer4.setQuestion_id(questionID);

        answerObjectArray.add(answer1);
        answerObjectArray.add(answer2);
        answerObjectArray.add(answer3);
        answerObjectArray.add(answer4);

        //if at least one of the fields are used, call the submitQuestions method
        if (!(questionObjectArray.isEmpty() && questionObjectArray.equals("") && answerObjectArray.equals("") && answerObjectArray.isEmpty())) {

            //Pass final built question array to the submitQuestionsEdit
            submitEditedQuestions(questionObjectArray, answerObjectArray, progress, dialog);
        }
    }

    @Override
    public void submitEditedQuestions(final List questionObjectArray, final List answerObjectArray, ProgressBar progress, AlertDialog dialog){

        final ServerRequest request = new ServerRequest();
        request.setOperation(Constants.QUESTION_EDIT);
        //set the values entered for the pin entered
        request.setQuestion((Question) questionObjectArray.get(0));


        apiService.operation(request).enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {

                ServerResponse resp = response.body();

                if (resp.getResult().equals(Constants.SUCCESS)) {

                    submitEditedAnswers(answerObjectArray, progress, dialog);


                } else {
                    String messageFromServer = resp.getMessage();
                    BaseActivity.showToast((Context) view,messageFromServer);
                    progress.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                BaseActivity.showToast((Context) view,t.toString());
            }
        });
    }


    @Override
    public void submitEditedAnswers(final List answerObjectArray, ProgressBar progress, AlertDialog dialog){


        for (int i = 0; i < answerObjectArray.size(); i++) {

            final ServerRequest request = new ServerRequest();
            request.setOperation(Constants.ANSWER_EDIT);
            //set the values entered for the pin entered
            request.setAnswer((Answer) answerObjectArray.get(i));

            int finalI = i;
            apiService.operation(request).enqueue(new Callback<ServerResponse>() {
                @Override
                public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {

                    ServerResponse resp = response.body();

                    if (resp.getResult().equals(Constants.SUCCESS)) {

                        if(finalI == answerObjectArray.size() -1) {
                            BaseActivity.showToast((Context) view, resp.getMessage());
                            //This is called to refresh the questions and update the SQLite DB
                            view.getQuestions();
                        }


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


    @Override
    public void deleteQuestion(String questionID, ProgressBar progress, SharedPreferences pref){

        Question question = new Question();
        question.setLecturerID(pref.getString(Constants.LECTURER_ID, ""));
        question.setQuestionID(questionID);

        final ServerRequest request = new ServerRequest();
        request.setOperation(Constants.DELETE_QUESTION);
        //set the values entered for the pin entered
        request.setQuestion(question);

        apiService.operation(request).enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {

                ServerResponse resp = response.body();

                if (resp.getResult().equals(Constants.SUCCESS)) {

                    BaseActivity.showToast((Context) view, resp.getMessage());
                    view.getQuestions();


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


    public void addNewQuestionToExternalDB(String question, String answer1, String answer2, String answer3, String answer4, CheckBox cb_answer1_edit, CheckBox cb_answer2_edit, CheckBox cb_answer3_edit, CheckBox cb_answer4_edit, AlertDialog dialog, SharedPreferences pref, ProgressBar progress){

        Question question1 = new Question();
        question1.setQuestionString(question);
        question1.setQuestionTopic(pref.getString(Constants.SELECTED_TOPIC, ""));
        question1.setStudentPin(pref.getString(Constants.PIN_ENTERED, ""));
        question1.setLecturerID(pref.getString(Constants.LECTURER_ID, ""));

        final ServerRequest request = new ServerRequest();
        request.setOperation(Constants.ADD_NEW_QUESTION);
        //set the values entered for the pin entered
        request.setQuestion(question1);

        apiService.operation(request).enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {

                ServerResponse resp = response.body();

                if (resp.getResult().equals(Constants.SUCCESS)) {

                    String question_id = resp.getQuestion().getQuestionID();

                    addNewAnswersToExternalDB(question_id, answer1, answer2, answer3, answer4, cb_answer1_edit, cb_answer2_edit, cb_answer3_edit, cb_answer4_edit, dialog, pref, progress);


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


    public void addNewAnswersToExternalDB(String question_id, String answer1, String answer2, String answer3, String answer4, CheckBox cb_answer1_edit, CheckBox cb_answer2_edit, CheckBox cb_answer3_edit, CheckBox cb_answer4_edit, AlertDialog dialog, SharedPreferences pref, ProgressBar progress){

        List<Answer> answerObjectArray = new ArrayList<>();
        String isChecked1, isChecked2, isChecked3, isChecked4;

        if (cb_answer1_edit.isChecked()){
            isChecked1 = "Correct";
        }else{
            isChecked1 = "Incorrect";
        }

        if (cb_answer2_edit.isChecked()){
            isChecked2 = "Correct";
        }else{
            isChecked2 = "Incorrect";
        }

        if (cb_answer3_edit.isChecked()){
            isChecked3 = "Correct";
        }else{
            isChecked3 = "Incorrect";
        }

        if (cb_answer4_edit.isChecked()){
            isChecked4 = "Correct";
        }else{
            isChecked4 = "Incorrect";
        }

        Answer answerObject1= new Answer();
        answerObject1.setQuestion_id(question_id);
        answerObject1.setAnswerString(answer1);
        answerObject1.setIsAnswerCorrect(isChecked1);
        answerObject1.setStudentPin(pref.getString(Constants.PIN_ENTERED, ""));
        answerObject1.setLecturerID(pref.getString(Constants.LECTURER_ID, ""));

        Answer answerObject2 = new Answer();
        answerObject2.setQuestion_id(question_id);
        answerObject2.setAnswerString(answer2);
        answerObject2.setIsAnswerCorrect(isChecked2);
        answerObject2.setStudentPin(pref.getString(Constants.PIN_ENTERED, ""));
        answerObject2.setLecturerID(pref.getString(Constants.LECTURER_ID, ""));


        Answer answerObject3 = new Answer();
        answerObject3.setQuestion_id(question_id);
        answerObject3.setAnswerString(answer3);
        answerObject3.setIsAnswerCorrect(isChecked3);
        answerObject3.setStudentPin(pref.getString(Constants.PIN_ENTERED, ""));
        answerObject3.setLecturerID(pref.getString(Constants.LECTURER_ID, ""));


        Answer answerObject4 = new Answer();
        answerObject4.setQuestion_id(question_id);
        answerObject4.setAnswerString(answer4);
        answerObject4.setIsAnswerCorrect(isChecked4);
        answerObject4.setStudentPin(pref.getString(Constants.PIN_ENTERED, ""));
        answerObject4.setLecturerID(pref.getString(Constants.LECTURER_ID, ""));


        answerObjectArray.add(answerObject1);
        answerObjectArray.add(answerObject2);
        answerObjectArray.add(answerObject3);
        answerObjectArray.add(answerObject4);

        for (int i = 0; i < answerObjectArray.size(); i++) {

            final ServerRequest request = new ServerRequest();
            request.setOperation(Constants.ADD_NEW_ANSWER);
            //set the values entered for the pin entered
            request.setAnswer(answerObjectArray.get(i));

            apiService.operation(request).enqueue(new Callback<ServerResponse>() {
                @Override
                public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {

                    ServerResponse resp = response.body();

                    if (resp.getResult().equals(Constants.SUCCESS)) {

                        if(!messageShown) {
                            messageShown = true;

                            BaseActivity.showToast((Context) view, resp.getMessage());
                            dialog.dismiss();
                            view.getQuestions();
                        }


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
}
