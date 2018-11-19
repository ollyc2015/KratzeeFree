package uk.co.oliverbcurtis.Kratzee.ui.detail.lecturerEditQuestionSets.selectTopicToEdit;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import com.google.gson.Gson;
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

public class SelectTopicPresenter implements SelectTopicContract.Presenter {

    private SelectTopicContract.View view;
    private OperationAPI apiService  = ApiUtils.getApiService();
    private DynamicQuestionTopicButton dynamicQuestionTopicButton = new DynamicQuestionTopicButton();


    @Override
    public void attachView(SelectTopicContract.View view) {

        this.view = view;
    }


    public void getAllTopics(ProgressBar progress, SharedPreferences pref, LinearLayout existingTopicSelectionLayout, SwipeRefreshLayout swipe_container) {

        existingTopicSelectionLayout.removeAllViews();

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

                   Question question = resp.getQuestion();

                    SharedPreferences.Editor editor = pref.edit();
                    Gson gson = new Gson();
                    String json = gson.toJson(question);
                    editor.putString("questionObject", json);
                    editor.apply();

                    dynamicQuestionTopicButton.createButton((SelectTopicView) view, question, existingTopicSelectionLayout, progress, swipe_container);


                } else if (resp.getResult().equals(Constants.FAILURE)) {

                    BaseActivity.showToast((Context) view, "Synchronisation Failed, Please Try Again!");
                }

            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {

                progress.setVisibility(View.INVISIBLE);
                BaseActivity.showToast((Context) view, t.toString());


            }
        });
    }


    public void checkIfActive(String topicPin, SharedPreferences pref, ProgressBar progress, String buttonText) {

        //create new Question object
        Question question = new Question();
        question.setStudentPin(topicPin);
        question.setLecturerID(pref.getString(Constants.LECTURER_ID, ""));

        final ServerRequest request = new ServerRequest();
        request.setOperation(Constants.CHECK_IF_TOPIC_ACTIVE);
        //set values entered for the new question to be sent to the server
        request.setQuestion(question);


        apiService.operation(request).enqueue(new Callback<ServerResponse>() {

            @Override
            public void onResponse(Call<ServerResponse> call, retrofit2.Response<ServerResponse> response) {

                ServerResponse resp = response.body();

                if (resp.getResult().equals(Constants.SUCCESS)) {

                    String istopicActive = resp.getQuestion().getTopicActiveList().get(0).toString();

                    view.showTopicSelectionDialog(buttonText, topicPin, istopicActive);


                } else if (resp.getResult().equals(Constants.FAILURE)) {

                    BaseActivity.showToast((Context) view, "Unable To Load Data, Please Try Again!");
                }
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {

                progress.setVisibility(View.INVISIBLE);
                BaseActivity.showToast((Context) view, t.toString());


            }
        });
    }


    @Override
    public void deleteTopic(String topicPin, ProgressBar progress, SharedPreferences pref, LinearLayout existingTopicSelectionLayout, SwipeRefreshLayout swipe_container){

        //create new Question object
        Question question = new Question();
        question.setStudentPin(topicPin);
        question.setLecturerID(pref.getString(Constants.LECTURER_ID, ""));

        final ServerRequest request = new ServerRequest();
        request.setOperation(Constants.DELETE_TOPIC);
        //set values entered for the new question to be sent to the server
        request.setQuestion(question);


        apiService.operation(request).enqueue(new Callback<ServerResponse>() {

            @Override
            public void onResponse(Call<ServerResponse> call, retrofit2.Response<ServerResponse> response) {

                ServerResponse resp = response.body();

                if (resp.getResult().equals(Constants.SUCCESS)) {

                    BaseActivity.showToast((Context) view, resp.getMessage());
                    getAllTopics(progress, pref, existingTopicSelectionLayout, swipe_container);


                } else if (resp.getResult().equals(Constants.FAILURE)) {

                    BaseActivity.showToast((Context) view, "Synchronisation Failed, Please Try Again!");
                }

            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {

                progress.setVisibility(View.INVISIBLE);
                BaseActivity.showToast((Context) view, t.toString());


            }
        });
    }


    @Override
    public void updateTopicActiveState(String topicActive, String topicPin, SharedPreferences pref, ProgressBar progress){

        //create new Question object
        Question question = new Question();
        question.setStudentPin(topicPin);
        question.setLecturerID(pref.getString(Constants.LECTURER_ID, ""));
        question.setTopicActive(topicActive);

        final ServerRequest request = new ServerRequest();
        request.setOperation(Constants.UPDATE_TOPIC_ACTIVE_STATE);
        //set values entered for the new question to be sent to the server
        request.setQuestion(question);


        apiService.operation(request).enqueue(new Callback<ServerResponse>() {

            @Override
            public void onResponse(Call<ServerResponse> call, retrofit2.Response<ServerResponse> response) {

                ServerResponse resp = response.body();

                if (resp.getResult().equals(Constants.SUCCESS)) {

                    BaseActivity.showToast((Context) view, resp.getMessage());


                } else if (resp.getResult().equals(Constants.FAILURE)) {

                    BaseActivity.showToast((Context) view, "Synchronisation Failed, Please Try Again!");
                }

            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {

                progress.setVisibility(View.INVISIBLE);
                BaseActivity.showToast((Context) view, t.toString());


            }
        });
    }
}
