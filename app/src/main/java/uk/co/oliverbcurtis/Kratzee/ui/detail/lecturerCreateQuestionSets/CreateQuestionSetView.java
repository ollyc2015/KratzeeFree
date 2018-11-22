package uk.co.oliverbcurtis.Kratzee.ui.detail.lecturerCreateQuestionSets;

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
import android.widget.ScrollView;

import java.util.List;

import uk.co.oliverbcurtis.Kratzee.R;
import uk.co.oliverbcurtis.Kratzee.ui.common.BaseActivity;
import uk.co.oliverbcurtis.Kratzee.ui.detail.feedbackForm.FeedbackView;
import uk.co.oliverbcurtis.Kratzee.ui.detail.lecturerProfileMainMenu.LecturerProfileView;

import static uk.co.oliverbcurtis.Kratzee.sqlite.KratzeeDatabase.ANSWER_TABLE;
import static uk.co.oliverbcurtis.Kratzee.sqlite.KratzeeDatabase.QUESTION_TABLE;

public class CreateQuestionSetView extends BaseActivity implements CreateQuestionSetContract.View, View.OnClickListener {

    private CreateQuestionSetPresenter presenter;
    private EditText et_question_topic;
    private Button btn_add_question, btn_submit_all_questions;
    private ProgressBar progress;
    private EditText et_edit_question, et_answer1_edit, et_answer2_edit, et_answer3_edit, et_answer4_edit;
    private CheckBox cb_answer1_edit, cb_answer2_edit, cb_answer3_edit, cb_answer4_edit;
    private LinearLayout new_question_layout;
    public static int questionNumber = 1;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lecturer_create_question_set);
        initView();
    }

    @Override
    public void initView() {

        presenter = new CreateQuestionSetPresenter();
        presenter.attachView(this);

        et_question_topic = findViewById(R.id.et_question_topic);

        btn_add_question = findViewById(R.id.btn_add_question);
        btn_add_question.setOnClickListener(this);

        btn_submit_all_questions = findViewById(R.id.btn_submit_all_questions);
        btn_submit_all_questions.setOnClickListener(this);

        progress = findViewById(R.id.progress);

        new_question_layout = findViewById(R.id.new_question_layout);

        //In the event the user has come from the edit questions screen, remove the data in the tables before adding new questions
        android.database.sqlite.SQLiteDatabase db = kratzeeDatabase.getWritableDatabase();
        db.execSQL("delete from " + QUESTION_TABLE);
        db.execSQL("delete from " + ANSWER_TABLE);
        db.close();

    }


    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.btn_add_question:

                String topic = et_question_topic.getText().toString();

                if(!topic.isEmpty()) {

                    inflateQuestionLayout(topic);

                }else{

                    showToast(this, "Please Add a Topic Before Adding a Question");
                }
                break;

            case R.id.btn_submit_all_questions:

                if(new_question_layout.getChildCount() > 0) {

                    showSubmitAllQuestionsDialog();
                }else{

                    showToast(this, "Please add a Question Before Trying to Submit a Question Set");
                }
                break;

        }
    }


    @Override
    public void inflateQuestionLayout(String topic){


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View view = inflater.inflate(R.layout.lecturer_edit_question, null);

        et_edit_question = view.findViewById(R.id.et_edit_question);

        et_answer1_edit = view.findViewById(R.id.et_answer1_edit);
        cb_answer1_edit = view.findViewById(R.id.cb_answer1_edit);

        et_answer2_edit = view.findViewById(R.id.et_answer2_edit);
        cb_answer2_edit = view.findViewById(R.id.cb_answer2_edit);

        et_answer3_edit = view.findViewById(R.id.et_answer3_edit);
        cb_answer3_edit = view.findViewById(R.id.cb_answer3_edit);

        et_answer4_edit = view.findViewById(R.id.et_answer4_edit);
        cb_answer4_edit = view.findViewById(R.id.cb_answer4_edit);

        checkBoxOnClickListeners(cb_answer1_edit, cb_answer2_edit, cb_answer3_edit, cb_answer4_edit);

        builder.setView(view);
        builder.setTitle("Add Question");
        builder.setPositiveButton("Add", (dialog, which) -> {

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

            if(!topic.isEmpty() && !question.isEmpty() && !answer1.isEmpty() && !answer2.isEmpty() && !answer3.isEmpty() && !answer4.isEmpty() &&
                    cb_answer1_edit.isChecked() || cb_answer2_edit.isChecked() || cb_answer3_edit.isChecked() || cb_answer4_edit.isChecked()){

                progress.setVisibility(View.VISIBLE);

                view.findViewById(R.id.editQuestionScrollview).post(() -> ((ScrollView) view.findViewById(R.id.editQuestionScrollview)).fullScroll(View.FOCUS_DOWN));

                presenter.addQuestionToSQLite(question, answer1, answer2, answer3, answer4, cb_answer1_edit, cb_answer2_edit, cb_answer3_edit, cb_answer4_edit, progress, dialog, kratzeeDatabase, topic, pref, this);

            }else {

                showToast(this, "Please Complete all Fields and Mark at Least One Answer as Correct");
            }
        });

    }

    @Override
    public void populateLayout(Button btn_new_question){

        new_question_layout.addView(btn_new_question);

        btn_new_question.setOnClickListener(v -> {
            Log.i("Clicked", "" + v.getTag());
            String buttonText = (String) btn_new_question.getText();
            String questionID = v.getTag().toString();
            showQuestionSelectionDialog(buttonText, questionID);
        });

    }

    @Override
    public void showQuestionSelectionDialog(String buttonText, String questionID){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Selected Question");
        builder.setMessage("You have selected: "+buttonText);
        builder.setNeutralButton("Remove", (dialog, which) -> { });
        builder.setPositiveButton("Edit", (dialog, which) -> { });
        builder.setNegativeButton("Cancel", (dialog, which) -> {dialog.dismiss();});
        AlertDialog dialog = builder.create();
        dialog.show();
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(v -> {

            presenter.getSelectedQuestionFromSQLiteDB(pref, progress, questionID, kratzeeDatabase);
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

                boolean response = presenter.deleteQuestionAnswersFromSQLiteDB(questionID, kratzeeDatabase);

                if(response){

                    response = presenter.deleteQuestionFromSQLiteDB(questionID, kratzeeDatabase);

                    if(response){

                        showToast(this, "Question Removed");
                        progress.setVisibility(View.INVISIBLE);
                        new_question_layout.removeView(new_question_layout.findViewWithTag(questionID));

                    }else{

                        progress.setVisibility(View.INVISIBLE);
                        showToast(this, "Unable to Delete Question");
                    }
                }else{

                    progress.setVisibility(View.INVISIBLE);
                    showToast(this, "Unable to Delete Answers");
                }
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
            cb_answer2_edit.setEnabled(false);
            cb_answer2_edit.setAlpha(.5f);
            cb_answer3_edit.setEnabled(false);
            cb_answer3_edit.setAlpha(.5f);
            cb_answer4_edit.setEnabled(false);
            cb_answer4_edit.setAlpha(.5f);
        }

        if (isAnswerCorrectArray.get(1).contains("Correct")){
            cb_answer2_edit.setChecked(true);
            cb_answer1_edit.setEnabled(false);
            cb_answer1_edit.setAlpha(.5f);
            cb_answer3_edit.setEnabled(false);
            cb_answer3_edit.setAlpha(.5f);
            cb_answer4_edit.setEnabled(false);
            cb_answer4_edit.setAlpha(.5f);
        }

        if (isAnswerCorrectArray.get(2).contains("Correct")){
            cb_answer3_edit.setChecked(true);
            cb_answer2_edit.setEnabled(false);
            cb_answer2_edit.setAlpha(.5f);
            cb_answer1_edit.setEnabled(false);
            cb_answer1_edit.setAlpha(.5f);
            cb_answer4_edit.setEnabled(false);
            cb_answer4_edit.setAlpha(.5f);
        }

        if (isAnswerCorrectArray.get(3).contains("Correct")){
            cb_answer4_edit.setChecked(true);
            cb_answer2_edit.setEnabled(false);
            cb_answer2_edit.setAlpha(.5f);
            cb_answer3_edit.setEnabled(false);
            cb_answer3_edit.setAlpha(.5f);
            cb_answer1_edit.setEnabled(false);
            cb_answer1_edit.setAlpha(.5f);
        }

        checkBoxOnClickListeners(cb_answer1_edit, cb_answer2_edit, cb_answer3_edit, cb_answer4_edit);


        builder.setView(view);
        builder.setTitle("Edit Question");
        builder.setPositiveButton("Update", (dialog, which) -> {

        });
        builder.setNegativeButton("Cancel", (dialog, which) ->{dialog.dismiss(); progress.setVisibility(View.INVISIBLE);});
        AlertDialog dialog = builder.create();
        dialog.show();
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(v -> {


            String questionString = et_edit_question.getText().toString();
            String answer1 = et_answer1_edit.getText().toString();
            String answer2 = et_answer2_edit.getText().toString();
            String answer3 = et_answer3_edit.getText().toString();
            String answer4 = et_answer4_edit.getText().toString();

            if(!questionString.isEmpty() && !answer1.isEmpty() && !answer2.isEmpty() && !answer3.isEmpty() && !answer4.isEmpty()&&
                    cb_answer1_edit.isChecked() || cb_answer2_edit.isChecked() || cb_answer3_edit.isChecked() || cb_answer4_edit.isChecked()){

                progress.setVisibility(View.VISIBLE);

                presenter.updateQuestionSQLiteDB(questionString, answer_id_array, answer1, answer2, answer3, answer4, cb_answer1_edit, cb_answer2_edit, cb_answer3_edit, cb_answer4_edit, questionID, progress, dialog, kratzeeDatabase);

            }else {

                showToast(this, "Please Complete all Fields");
            }
        });
    }


    @Override
    public void showSubmitAllQuestionsDialog(){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Save & Submit All Questions?");
        builder.setPositiveButton("OK", (dialog, which) ->
        {
            progress.setVisibility(View.VISIBLE);
            questionNumber = 1;
            presenter.getQuestionsFromSQLite(kratzeeDatabase, pref, progress);

        });
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());
        builder.create().show();

    }


    @Override
    public void  questionsSubmitSuccessful(){

        new_question_layout.removeAllViews();
        et_question_topic.getText().clear();

        android.database.sqlite.SQLiteDatabase db = kratzeeDatabase.getWritableDatabase();
        db.execSQL("delete from " + QUESTION_TABLE);
        db.execSQL("delete from " + ANSWER_TABLE);
        db.close();

        onBackPressed();

    }

    @Override
    public void checkBoxOnClickListeners(CheckBox cb_answer1_edit, CheckBox cb_answer2_edit, CheckBox cb_answer3_edit, CheckBox cb_answer4_edit){

        cb_answer1_edit.setOnCheckedChangeListener((buttonView, isChecked) -> {

            if(cb_answer1_edit.isChecked()){

                cb_answer2_edit.setEnabled(false);
                cb_answer2_edit.setAlpha(.5f);
                cb_answer3_edit.setEnabled(false);
                cb_answer3_edit.setAlpha(.5f);
                cb_answer4_edit.setEnabled(false);
                cb_answer4_edit.setAlpha(.5f);

            }else if (!cb_answer1_edit.isChecked()){

                cb_answer2_edit.setEnabled(true);
                cb_answer2_edit.setAlpha(1f);
                cb_answer3_edit.setEnabled(true);
                cb_answer3_edit.setAlpha(1f);
                cb_answer4_edit.setEnabled(true);
                cb_answer4_edit.setAlpha(1f);
            }
        });

        cb_answer2_edit.setOnCheckedChangeListener((buttonView, isChecked) -> {

            if(cb_answer2_edit.isChecked()){

                cb_answer1_edit.setEnabled(false);
                cb_answer1_edit.setAlpha(.5f);
                cb_answer3_edit.setEnabled(false);
                cb_answer3_edit.setAlpha(.5f);
                cb_answer4_edit.setEnabled(false);
                cb_answer4_edit.setAlpha(.5f);

            }else if (!cb_answer2_edit.isChecked()){

                cb_answer1_edit.setEnabled(true);
                cb_answer1_edit.setAlpha(1f);
                cb_answer3_edit.setEnabled(true);
                cb_answer3_edit.setAlpha(1f);
                cb_answer4_edit.setEnabled(true);
                cb_answer4_edit.setAlpha(1f);
            }

        });

        cb_answer3_edit.setOnCheckedChangeListener((buttonView, isChecked) -> {

            if(cb_answer3_edit.isChecked()){

                cb_answer1_edit.setEnabled(false);
                cb_answer1_edit.setAlpha(.5f);
                cb_answer2_edit.setEnabled(false);
                cb_answer2_edit.setAlpha(.5f);
                cb_answer4_edit.setEnabled(false);
                cb_answer4_edit.setAlpha(.5f);

            }else if (!cb_answer3_edit.isChecked()){

                cb_answer1_edit.setEnabled(true);
                cb_answer1_edit.setAlpha(1f);
                cb_answer2_edit.setEnabled(true);
                cb_answer2_edit.setAlpha(1f);
                cb_answer4_edit.setEnabled(true);
                cb_answer4_edit.setAlpha(1f);
            }
        });

        cb_answer4_edit.setOnCheckedChangeListener((buttonView, isChecked) -> {

            if(cb_answer4_edit.isChecked()){

                cb_answer1_edit.setEnabled(false);
                cb_answer1_edit.setAlpha(.5f);
                cb_answer2_edit.setEnabled(false);
                cb_answer2_edit.setAlpha(.5f);
                cb_answer3_edit.setEnabled(false);
                cb_answer3_edit.setAlpha(.5f);

            }else if (!cb_answer4_edit.isChecked()){

                cb_answer1_edit.setEnabled(true);
                cb_answer1_edit.setAlpha(1f);
                cb_answer2_edit.setEnabled(true);
                cb_answer2_edit.setAlpha(1f);
                cb_answer3_edit.setEnabled(true);
                cb_answer3_edit.setAlpha(1f);
            }

        });
    }


    @Override
    public void onBackPressed() {

        questionNumber = 1;
        //Below is what causes the activity to go to the previous activity
        Intent intent = new Intent(getApplicationContext(), LecturerProfileView.class);
        startActivity(intent);

    }
}
