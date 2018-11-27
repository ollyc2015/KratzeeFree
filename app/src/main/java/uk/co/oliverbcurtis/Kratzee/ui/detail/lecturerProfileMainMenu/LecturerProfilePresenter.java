package uk.co.oliverbcurtis.Kratzee.ui.detail.lecturerProfileMainMenu;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.LinkedHashSet;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import uk.co.oliverbcurtis.Kratzee.async.ServerRequest;
import uk.co.oliverbcurtis.Kratzee.async.ServerResponse;
import uk.co.oliverbcurtis.Kratzee.async.remote.ApiUtils;
import uk.co.oliverbcurtis.Kratzee.async.remote.OperationAPI;
import uk.co.oliverbcurtis.Kratzee.model.Constants;
import uk.co.oliverbcurtis.Kratzee.model.Lecturer;
import uk.co.oliverbcurtis.Kratzee.model.Question;
import uk.co.oliverbcurtis.Kratzee.ui.common.BaseActivity;

public class LecturerProfilePresenter implements LecturerProfileContract.Presenter {

    private LecturerProfileContract.View view;
    private OperationAPI apiService  = ApiUtils.getApiService();

    @Override
    public void attachView(LecturerProfileContract.View view) {

        this.view = view;
    }

    @Override
    public void changePasswordProcess(String email, String old_password, String new_password, ProgressBar progress, AlertDialog dialog){

        Lecturer lecturer = new Lecturer();
        lecturer.setEmail(email);
        lecturer.setOld_password(old_password);
        lecturer.setNew_password(new_password);
        ServerRequest request = new ServerRequest();
        request.setOperation(Constants.CHANGE_PASSWORD_OPERATION);
        request.setLecturer(lecturer);

        apiService.operation(request).enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, retrofit2.Response<ServerResponse> response) {

                ServerResponse resp = response.body();
                if(resp.getResult().equals(Constants.SUCCESS)){
                    progress.setVisibility(View.GONE);
                    dialog.dismiss();
                    BaseActivity.showToast((Context) view, resp.getMessage());

                }else {
                    progress.setVisibility(View.GONE);
                    BaseActivity.showToast((Context) view, resp.getMessage());

                }
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {

                progress.setVisibility(View.GONE);
                BaseActivity.showToast((Context) view, t.toString());
            }
        });
    }


    @Override
    public void removeAllQuestion(ProgressBar progress, SharedPreferences pref){

        //Creating a new User object
        Question question = new Question();
        question.setLecturerID(pref.getString(Constants.LECTURER_ID,""));
        ServerRequest request = new ServerRequest();
        request.setOperation(Constants.DROP_QUESTIONS);
        //set the values entered for the new user
        request.setQuestion(question);

        apiService.operation(request).enqueue(new Callback<ServerResponse>() {

            @Override
            public void onResponse(Call<ServerResponse> call, retrofit2.Response<ServerResponse> response) {

                ServerResponse resp = response.body();
                BaseActivity.showToast((Context) view, resp.getMessage());
                progress.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {

                progress.setVisibility(View.INVISIBLE);
                BaseActivity.showToast((Context) view, t.toString());
            }
        });
    }


    @Override
    public void removeAllParticipants(ProgressBar progress, SharedPreferences pref){


        //Creating a new Lecturer object
        Lecturer lecturer = new Lecturer();
        lecturer.setLecturerID(pref.getString(Constants.LECTURER_ID,""));
        lecturer.setAssessment_profile("No");
        ServerRequest request = new ServerRequest();
        request.setOperation(Constants.DROP_TRIVIA_PROFILES);
        //set the values entered for the new user
        request.setLecturer(lecturer);

        apiService.operation(request).enqueue(new Callback<ServerResponse>() {

            @Override
            public void onResponse(Call<ServerResponse> call, retrofit2.Response<ServerResponse> response) {

                ServerResponse resp = response.body();
                BaseActivity.showToast((Context) view, resp.getMessage());
                progress.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {

                progress.setVisibility(View.INVISIBLE);
                BaseActivity.showToast((Context) view, t.toString());
            }
        });
    }


    @Override
    public void getAllTopics(ProgressBar progress, SharedPreferences pref, TextView tv_question_sets_available, AppCompatButton btn_create_questions) {

        //create new Question object
        Lecturer lecturer = new Lecturer();
        lecturer.setLecturerID(pref.getString(Constants.LECTURER_ID, ""));

        final ServerRequest request = new ServerRequest();
        request.setOperation(Constants.LOAD_EXISTING_TOPICS);
        //set values entered for the new question to be sent to the server
        request.setLecturer(lecturer);


        apiService.operation(request).enqueue(new Callback<ServerResponse>() {

            @Override
            public void onResponse(Call<ServerResponse> call, retrofit2.Response<ServerResponse> response) {

                ServerResponse resp = response.body();

                if (resp.getResult().equals(Constants.SUCCESS)) {

                    progress.setVisibility(View.GONE);
                    tv_question_sets_available.setVisibility(View.VISIBLE);

                    Question question = resp.getQuestion();

                    Set<String> questionTopicSet = new LinkedHashSet<>(question.getQuestionTopicList());

                    if(questionTopicSet.size() < 5){
                        tv_question_sets_available.setText("You Have Added "+questionTopicSet.size() + " Question-Sets, Please Note, You Can Add a Total of 5 Question-Sets");
                        btn_create_questions.setVisibility(View.VISIBLE);
                    }else{

                        tv_question_sets_available.setText("You Have Added 5 Question-Sets, Please Delete an Existing Set (You Can Do This by Clicking 'Edit Existing Question-Sets' below) if You Wish To Add a New Question-Set");
                    }

                } else if (resp.getResult().equals(Constants.FAILURE)) {

                    BaseActivity.showToast((Context) view, "Synchronisation Failed, Please Try Again!");
                    progress.setVisibility(View.GONE);
                }

            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {

                progress.setVisibility(View.GONE);
                BaseActivity.showToast((Context) view, t.toString());


            }
        });
    }
}
