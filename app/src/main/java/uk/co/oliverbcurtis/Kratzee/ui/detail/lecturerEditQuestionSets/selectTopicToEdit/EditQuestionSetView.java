package uk.co.oliverbcurtis.Kratzee.ui.detail.lecturerEditQuestionSets.selectTopicToEdit;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Switch;
import com.google.gson.Gson;
import uk.co.oliverbcurtis.Kratzee.R;
import uk.co.oliverbcurtis.Kratzee.model.Constants;
import uk.co.oliverbcurtis.Kratzee.model.Question;
import uk.co.oliverbcurtis.Kratzee.ui.common.BaseActivity;
import uk.co.oliverbcurtis.Kratzee.ui.common.RequestQuestionsExternalDB;
import uk.co.oliverbcurtis.Kratzee.ui.detail.lecturerProfileMainMenu.LecturerProfileView;


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

        progress = findViewById(R.id.progress);

        //Manuel swipe to refresh
        swipe_container = findViewById(R.id.swipe_container);
        swipe_container.setOnRefreshListener(() -> { existingTopicSelectionLayout.removeAllViews();
            presenter.getAllTopics(progress, pref, existingTopicSelectionLayout, swipe_container); });


        if (!allTopicsRequested){

            allTopicsRequested = true;

            progress.setVisibility(View.VISIBLE);
            presenter.getAllTopics(progress, pref, existingTopicSelectionLayout, swipe_container);

        }else{
            //Retrieve the stored object that was saved in shared pref from the method above.
            //We do this to avoid multiple calls to the external DB
            Gson gson = new Gson();
            String json = pref.getString("questionObject", "");
            Question question = gson.fromJson(json, Question.class);
            dynamicQuestionTopicButton.createButton(this, question, existingTopicSelectionLayout, progress, swipe_container);
        }
    }


    @Override
    public void addTopicButtonToLayout(Button btn_question_topic, LinearLayout topicLayour){

        existingTopicSelectionLayout.addView(btn_question_topic);

        btn_question_topic.setOnClickListener(v -> {

            Log.i("Clicked", "" + v.getTag());
            String buttonText = (String) btn_question_topic.getText();
            String topicPin = v.getTag().toString();

            presenter.checkIfActive(topicPin, pref, progress, buttonText);

        });
    }

    @Override
    public void showTopicSelectionDialog(String buttonText, String topicPin, String istopicActive){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        Switch sw = new Switch(this);
        sw.setText("Activate Question-Set");
        if(istopicActive.contains("Yes")){
            sw.setChecked(true);
        }

        sw.setOnCheckedChangeListener((buttonView, isChecked) -> {

            String topicActive;

            if(sw.isChecked()){

                topicActive = "Yes";
                presenter.updateTopicActiveState(topicActive, topicPin, pref, progress);

            }else{

                topicActive ="No";
                presenter.updateTopicActiveState(topicActive, topicPin, pref, progress);
            }
        });

        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT));
        linearLayout.setGravity(Gravity.CENTER_HORIZONTAL);
        linearLayout.addView(sw);

        builder.setTitle("Edit Topic Questions");
        builder.setMessage("You have selected: \n\n"+buttonText+" \n\nPlease note; the Question PIN cannot be changed.");
        builder.setPositiveButton("Edit", (dialog, which) -> { });
        builder.setNeutralButton("Delete", (dialog, which) -> { });
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());
        builder.setView(linearLayout);

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
        dialog.getButton(AlertDialog.BUTTON_NEUTRAL).setOnClickListener(v -> {

            dialog.dismiss();

            //If user clicks to delete the question, create another alert dialog to check they wish to delete the question
            AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
            builder1.setTitle("Delete Topic?");
            builder1.setMessage("Delete Topic? This will delete all the associated questions");
            builder1.setPositiveButton("Yes", (dialog1, which) -> {

                progress.setVisibility(View.VISIBLE);
                presenter.deleteTopic(topicPin, progress, pref, existingTopicSelectionLayout, swipe_container);
            });
            builder1.setNegativeButton("Cancel", (dialog1, which) -> {dialog.dismiss();});
            builder1.show();

        });
    }

    @Override
    public void getQuestions(){

        RequestQuestionsExternalDB requestQuestionsExternalDB = new RequestQuestionsExternalDB();
        requestQuestionsExternalDB.getQuestions(progress, this, kratzeeDatabase);
    }

    @Override
    public void onBackPressed() {

        //On back pressed has been overridden because, if the user presses the back button after deleting a topic,
        //we don't want to make the deleted topic button to reappear, we want to send the user to the previous activity
        Intent intent = new Intent(getApplicationContext(), LecturerProfileView.class);
        startActivity(intent);

    }
}
