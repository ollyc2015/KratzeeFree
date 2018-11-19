package uk.co.oliverbcurtis.Kratzee.ui.detail.lecturerEditQuestionSets.selectedTopicQuestions;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import java.util.List;
import uk.co.oliverbcurtis.Kratzee.R;
import uk.co.oliverbcurtis.Kratzee.ui.common.BaseActivity;
import uk.co.oliverbcurtis.Kratzee.ui.common.RequestQuestionsExternalDB;
import uk.co.oliverbcurtis.Kratzee.ui.detail.lecturerEditQuestionSets.selectTopicToEdit.SelectTopicView;

public class SelectedTopicView extends BaseActivity implements SelectedTopicContract.View{

    private LinearLayout selectedTopicSelectionLayout;
    private SelectedTopicPresenter presenter;
    private ProgressBar progress;
    private EditText et_edit_question, et_answer1_edit, et_answer2_edit, et_answer3_edit, et_answer4_edit;
    private CheckBox cb_answer1_edit, cb_answer2_edit, cb_answer3_edit, cb_answer4_edit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lecturer_selected_topic_to_edit);
        initView();
    }

    @Override
    public void initView() {

        selectedTopicSelectionLayout = findViewById(R.id.selectedTopicSelectionLayout);
        selectedTopicSelectionLayout.removeAllViews();

        presenter = new SelectedTopicPresenter();
        presenter.attachView(this);

        progress = findViewById(R.id.progress);

        loadAllQuestions(progress);
    }

    @Override
    public void loadAllQuestions(ProgressBar progress){

        progress.setVisibility(View.VISIBLE);
        presenter.getAllQuestions(kratzeeDatabase);

    }


    @Override
    public void populateLayout(Button btn_topic_question){

        selectedTopicSelectionLayout.addView(btn_topic_question);
        progress.setVisibility(View.INVISIBLE);

        btn_topic_question.setOnClickListener(v -> {
            Log.i("Clicked", "" + v.getTag());
            String buttonText = (String) btn_topic_question.getText();
            String questionID = v.getTag().toString();
            showQuestionSelectionDialog(buttonText, questionID);
        });
    }


    @Override
    public void showQuestionSelectionDialog(String buttonText, String questionID){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Selected Question");
        builder.setMessage("You have selected: "+buttonText);
        builder.setNeutralButton("Delete", (dialog, which) -> { });
        builder.setPositiveButton("Edit", (dialog, which) -> { });
        builder.setNegativeButton("Cancel", (dialog, which) -> {dialog.dismiss();});
        AlertDialog dialog = builder.create();
        dialog.show();
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(v -> {

            progress.setVisibility(View.VISIBLE);

            presenter.getSelectQuestion(pref, progress, questionID, kratzeeDatabase);
            dialog.dismiss();


        });
        dialog.getButton(AlertDialog.BUTTON_NEUTRAL).setOnClickListener(v -> {

            dialog.dismiss();

            //If user clicks to delete the question, create another alert dialog to check they wish to delete the question
            AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
            builder1.setTitle("Delete Question");
            builder1.setMessage("Delete Question?");
            builder1.setPositiveButton("Yes", (dialog1, which) -> {

                        progress.setVisibility(View.VISIBLE);
                        presenter.deleteQuestion(questionID, progress, pref);
                    });
            builder1.setNegativeButton("Cancel", (dialog1, which) -> {dialog.dismiss();});
            builder1.show();

        });
    }

    @Override
    public void showEditQuestionLayout(List<String> question_array, List<String> answer_id_array, List<String> answer_array, List<String> isAnswerCorrectArray, String questionID, ProgressBar progress){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View view = inflater.inflate(R.layout.lecturer_edit_question, null);

        et_edit_question = view.findViewById(R.id.et_edit_question);
        et_edit_question.setText(question_array.get(0));

        et_answer1_edit = view.findViewById(R.id.et_answer1_edit);
        et_answer1_edit.setText(answer_array.get(0));
        cb_answer1_edit = view.findViewById(R.id.cb_answer1_edit);

        et_answer2_edit = view.findViewById(R.id.et_answer2_edit);
        et_answer2_edit.setText(answer_array.get(1));
        cb_answer2_edit = view.findViewById(R.id.cb_answer2_edit);

        et_answer3_edit = view.findViewById(R.id.et_answer3_edit);
        et_answer3_edit.setText(answer_array.get(2));
        cb_answer3_edit = view.findViewById(R.id.cb_answer3_edit);

        et_answer4_edit = view.findViewById(R.id.et_answer4_edit);
        et_answer4_edit.setText(answer_array.get(3));
        cb_answer4_edit = view.findViewById(R.id.cb_answer4_edit);

        if (isAnswerCorrectArray.get(0).contains("Correct")){
            cb_answer1_edit.setChecked(true);
        }else{
            cb_answer1_edit.setChecked(false);
        }

        if (isAnswerCorrectArray.get(1).contains("Correct")){
            cb_answer2_edit.setChecked(true);
        }else{
            cb_answer2_edit.setChecked(false);
        }

        if (isAnswerCorrectArray.get(2).contains("Correct")){
            cb_answer3_edit.setChecked(true);
        }else{
            cb_answer3_edit.setChecked(false);
        }

        if (isAnswerCorrectArray.get(3).contains("Correct")){
            cb_answer4_edit.setChecked(true);
        }else{
            cb_answer4_edit.setChecked(false);
        }


        builder.setView(view);
        builder.setTitle("Edit Question");
        builder.setPositiveButton("Update", (dialog, which) -> {

        });
        builder.setNegativeButton("Cancel", (dialog, which) ->{dialog.dismiss(); progress.setVisibility(View.INVISIBLE);});
        AlertDialog dialog = builder.create();
        dialog.show();
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(v -> {

            String question = et_edit_question.getText().toString();
            String answer1 = et_answer1_edit.getText().toString();
            String answer2 = et_answer2_edit.getText().toString();
            String answer3 = et_answer3_edit.getText().toString();
            String answer4 = et_answer4_edit.getText().toString();

            if(!question.isEmpty() && !answer1.isEmpty() && !answer2.isEmpty() && !answer3.isEmpty() && !answer4.isEmpty()){

                this.progress.setVisibility(View.VISIBLE);

                presenter.updateQuestionExternalDB(question, answer_id_array, answer1, answer2, answer3, answer4, cb_answer1_edit, cb_answer2_edit, cb_answer3_edit, cb_answer4_edit, questionID, progress, dialog);

            }else {

                showToast(this, "Please Complete all Fields");
            }
        });
    }

    @Override
    public void getQuestions(){

        RequestQuestionsExternalDB requestQuestionsExternalDB = new RequestQuestionsExternalDB();
        requestQuestionsExternalDB.getQuestions(progress, this, kratzeeDatabase);
    }

    @Override
    public void onBackPressed() {

        //On back pressed has been overridden because, if the user presses the back button after deleting a question,
        //we don't want to make the deleted question button to reappear, we want to send the user to the previous activity
        Intent intent = new Intent(getApplicationContext(), SelectTopicView.class);
        startActivity(intent);

    }
}
