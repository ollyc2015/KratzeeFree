package uk.co.oliverbcurtis.Kratzee.ui.common;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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
import uk.co.oliverbcurtis.Kratzee.ui.detail.quizType.QuizTypeView;



import static uk.co.oliverbcurtis.Kratzee.sqlite.KratzeeDatabase.ANSWER_TABLE;
import static uk.co.oliverbcurtis.Kratzee.sqlite.KratzeeDatabase.QUESTION_TABLE;

public class RequestQuestionsExternalDB extends BaseActivity {


    public void getQuestions(final ProgressBar progress, final Context context, final KratzeeDatabase kratzeeDatabase) {

        final OperationAPI apiService = ApiUtils.getApiService();
        final SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);


        //Creating a new Question object
        Question question = new Question();
        question.setStudentPin(pref.getString(Constants.PIN_ENTERED, ""));
        question.setLecturerID(pref.getString(Constants.LECTURER_ID, ""));
        final ServerRequest request = new ServerRequest();
        request.setOperation(Constants.GET_ALL_QUESTIONS);
        //set the values entered for the pin entered
        request.setQuestion(question);


        // Retrofit call to API, returns list of meals
        apiService.operation(request).enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                if (response.isSuccessful()) {

                    ServerResponse resp = response.body();

                    Gson gson = new GsonBuilder()
                            .setLenient()
                            .create();


                    //retrieve the question id from the server
                    final String jsonQuestionIDs = gson.toJson(resp.getQuestion().getQuestionIDList()).trim();
                    //convert it back into an array
                    final String[] questionIDArray = gson.fromJson(jsonQuestionIDs, String[].class);
                    //retrieve the question string from the server
                    final String jsonQuestions = gson.toJson(resp.getQuestion().getQuestionStringList()).trim();
                    final String[] questionArray = gson.fromJson(jsonQuestions, String[].class);

                    //loop out the contents of the question strings into the sqlite database to be retrieved later
                    try {

                        android.database.sqlite.SQLiteDatabase db = kratzeeDatabase.getWritableDatabase();
                        db.execSQL("delete from "+ QUESTION_TABLE);

                        if(questionIDArray.length > 0) {

                            for (int i = 0; i < questionIDArray.length; i++) {
                                //Inserting the question list into the SQLite DB when the getQuestions method is called
                                ContentValues contentValues = new ContentValues();
                                contentValues.put(KratzeeContract.QUESTION_ID, questionIDArray[i]);
                                contentValues.put(KratzeeContract.QUESTION_STRING, questionArray[i]);
                                contentValues.put(KratzeeContract.QUESTION_LECTURER_ID, pref.getString(Constants.LECTURER_ID, ""));

                                //insert rows
                                long result = db.insert(QUESTION_TABLE, null, contentValues);

                                if (result == -1) {
                                    BaseActivity.showToast(context, "Unable to add questions to SQLite DB");
                                }
                            }
                            //db.close();
                            getAnswers(progress, apiService, context, kratzeeDatabase, questionIDArray[0]);
                        }else{

                            //If there are no questions, it is because the admin has deleted all the topic questions, rather than
                            //deleting the topic. Therefore take them back to topic selection since there are no more questions for
                            //the previously selected topic
                            goToAdminTopicSelection((Activity) context);
                        }
                    } catch (Exception ex) {
                        Log.e("Error", ex.toString());
                    }

                } else {

                    BaseActivity.showToast(context, "No Response Received");
                    progress.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                BaseActivity.showToast(context, t.toString());
            }
        });
    }


    public void getAnswers(final ProgressBar progress, OperationAPI apiService, final Context context, final KratzeeDatabase kratzeeDatabase, String question_id){

        final SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);

        //Creating a new Question object
        Answer answer = new Answer();
        answer.setStudentPin(pref.getString(Constants.PIN_ENTERED, ""));
        answer.setLecturerID(pref.getString(Constants.LECTURER_ID,""));
        final ServerRequest request = new ServerRequest();
        request.setOperation(Constants.Get_ALL_ANSWERS);
        //set the values entered for the pin entered
        request.setAnswer(answer);

        // Retrofit call to API, returns list of meals
        apiService.operation(request).enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {

                ServerResponse resp = response.body();

                if (resp.getResult().equals(Constants.SUCCESS)) {


                    Gson gson = new GsonBuilder()
                            .setLenient()
                            .create();

                    //retrieve the answer id object from the server
                    final String jsonAnswerIDs = gson.toJson (resp.getAnswer().getAnswerIDList()).trim();
                    //convert it back into an array
                    final String[] answerIDArray =  gson.fromJson (jsonAnswerIDs, String[].class);

                    final String jsonAnswers = gson.toJson (resp.getAnswer().getAnswerStringList()).trim();
                    final String[] answerArray =  gson.fromJson (jsonAnswers, String[].class);

                    final String jsonCorrect = gson.toJson (resp.getAnswer().getIsAnswerCorrectList()).trim();
                    final String[] isCorrectArray =  gson.fromJson (jsonCorrect, String[].class);

                    final String jsonQuestionIDs = gson.toJson (resp.getAnswer().getQuestionIDList()).trim();
                    final String[] questionIDArray =  gson.fromJson (jsonQuestionIDs, String[].class);

                    //loop out the contents of the question strings into the sqlite database to be retrieved later
                    try {

                        android.database.sqlite.SQLiteDatabase db = kratzeeDatabase.getWritableDatabase();
                        db.execSQL("delete from "+ ANSWER_TABLE);

                        for (int x = 0; x < answerIDArray.length; x++) {
                            //Inserting the answer list into the SQLite DB when the getQuestions method is called
                            ContentValues contentValues = new ContentValues();
                            contentValues.put(KratzeeContract.ANSWER_ID, answerIDArray[x]);
                            contentValues.put(KratzeeContract.ANSWER_STRING, answerArray[x]);
                            contentValues.put(KratzeeContract.CORRECT, isCorrectArray[x]);
                            contentValues.put(KratzeeContract.ANSWER_LECTURER_ID, pref.getString(Constants.LECTURER_ID,""));
                            contentValues.put(KratzeeContract.QUESTION_ID, questionIDArray[x]);
                            //insert rows
                            long result = db.insert(ANSWER_TABLE, null,contentValues);

                            if (result == -1) {
                                BaseActivity.showToast(context,"Unable to add answers to SQLite DB");
                            }
                        }
                        db.close();
                        //progress.setVisibility(View.INVISIBLE);

                        if(QuizTypeView.indiButtonPressed){

                            goToIndiQuizScreen((Activity) context);

                        }else if (QuizTypeView.teamButtonPressed){

                            goToTeamQuizScreen((Activity) context);

                        }else{

                            progress.setVisibility(View.INVISIBLE);
                            goToAdminEditTopic((Activity) context);
                        }


                    }catch (Exception ex){
                        Log.e("Error", ex.toString());
                    }

                } else {

                    BaseActivity.showToast(context,"No Response Received");
                    progress.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                BaseActivity.showToast(context,t.toString());
            }
        });
    }
}
