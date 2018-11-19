package uk.co.oliverbcurtis.Kratzee.ui.detail.lecturerCreateQuestionSets;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import uk.co.oliverbcurtis.Kratzee.R;
import uk.co.oliverbcurtis.Kratzee.ui.common.BaseActivity;

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

                    et_question_topic.setEnabled(false);

                    inflateQuestionLayout(topic);

                }else{

                    showToast(this, "Please Add a Topic Before Adding a Question");
                }
                break;

            case R.id.btn_submit_all_questions:

                if(new_question_layout.getChildCount() > 0) {

                    showConfirmationDialog();
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

            if(!question.isEmpty() && !answer1.isEmpty() && !answer2.isEmpty() && !answer3.isEmpty() && !answer4.isEmpty()){

                progress.setVisibility(View.VISIBLE);

                presenter.addQuestionToSQLite(question, answer1, answer2, answer3, answer4, cb_answer1_edit, cb_answer2_edit, cb_answer3_edit, cb_answer4_edit, progress, dialog, kratzeeDatabase, topic, pref, this);

            }else {

                showToast(this, "Please Complete all Fields");
            }
        });

    }

    @Override
    public void populateLayout(Button btn_new_question){

        new_question_layout.addView(btn_new_question);

    }


    @Override
    public void showConfirmationDialog(){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Submit All Questions?");
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
    public void onBackPressed() {

        questionNumber = 1;
        //Below is what causes the activity to go to the previous activity
        super.onBackPressed();

    }
}
