package uk.co.oliverbcurtis.Kratzee.ui.detail.lecturerEditQuestionSets.selectTopicToEdit;

import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.google.gson.Gson;

import uk.co.oliverbcurtis.Kratzee.R;
import uk.co.oliverbcurtis.Kratzee.model.Constants;
import uk.co.oliverbcurtis.Kratzee.model.Question;
import uk.co.oliverbcurtis.Kratzee.ui.common.BaseActivity;
import uk.co.oliverbcurtis.Kratzee.ui.common.RequestQuestionsExternalDB;


public class EditQuestionSetView extends BaseActivity implements EditQuestionSetContract.View {

    private EditQuestionSetPresenter presenter;
    private static boolean allTopicsRequested = false;
    private ProgressBar progress;
    private LinearLayout existingTopicSelectionLayout;
    private DynamicQuestionTopicButton dynamicQuestionTopicButton = new DynamicQuestionTopicButton();
    private SwipeRefreshLayout swipe_container;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lecturer_edit_question_sets);
        initView();
    }


    @Override
    public void initView() {

        presenter = new EditQuestionSetPresenter();
        presenter.attachView(this);

        existingTopicSelectionLayout = findViewById(R.id.existingTopicSelectionLayout);

        //Manuel swipe to refresh
        swipe_container = findViewById(R.id.swipe_container);
        swipe_container.setOnRefreshListener(() -> { existingTopicSelectionLayout.removeAllViews();
            presenter.getAllTopics(progress, pref, existingTopicSelectionLayout); });

        progress = findViewById(R.id.progress);

        if (!allTopicsRequested){

            allTopicsRequested = true;

            progress.setVisibility(View.VISIBLE);
            presenter.getAllTopics(progress, pref, existingTopicSelectionLayout);

        }else{
            //Retrieve the stored object that was saved in shared pref from the method above.
            //We do this to avoid multiple calls to the external DB
            Gson gson = new Gson();
            String json = pref.getString("questionObject", "");
            Question question = gson.fromJson(json, Question.class);
            dynamicQuestionTopicButton.createButton(this, question, existingTopicSelectionLayout);
        }
    }


    @Override
    public void addTopicButtonToLayout(Button btn_question_topic, LinearLayout topicLayour){

        swipe_container.setRefreshing(false);
        existingTopicSelectionLayout.addView(btn_question_topic);
        progress.setVisibility(View.INVISIBLE);

        btn_question_topic.setOnClickListener(v -> {
            Log.i("Clicked", "" + v.getTag());
            String buttonText = (String) btn_question_topic.getText();
            String topicPin = v.getTag().toString();
            showTopicSelectionDialog(buttonText, topicPin);
        });

    }

    public void showTopicSelectionDialog(String buttonText, String topicPin){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Selected Topic");
        builder.setMessage("You have selected: \n\n"+buttonText+" \n\nPlease note; the Question PIN cannot be changed.");
        builder.setPositiveButton("Select", (dialog, which) -> { });
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());
        AlertDialog dialog = builder.create();
        dialog.show();
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(v -> {

            dialog.dismiss();
            progress.setVisibility(View.VISIBLE);

            SharedPreferences.Editor editor = pref.edit();
            editor.putString(Constants.PIN_ENTERED, topicPin);
            editor.apply();

            getQuestions();


        });
    }

    @Override
    public void getQuestions(){

        RequestQuestionsExternalDB requestQuestionsExternalDB = new RequestQuestionsExternalDB();
        requestQuestionsExternalDB.getQuestions(progress, this, kratzeeDatabase);
    }
}
