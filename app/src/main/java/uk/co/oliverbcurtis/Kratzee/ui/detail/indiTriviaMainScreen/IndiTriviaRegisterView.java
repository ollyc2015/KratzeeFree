package uk.co.oliverbcurtis.Kratzee.ui.detail.indiTriviaMainScreen;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import uk.co.oliverbcurtis.Kratzee.R;
import uk.co.oliverbcurtis.Kratzee.model.Constants;
import uk.co.oliverbcurtis.Kratzee.ui.common.BaseActivity;
import uk.co.oliverbcurtis.Kratzee.ui.common.RequestQuestionsExternalDB;
import uk.co.oliverbcurtis.Kratzee.ui.detail.indiTriviaExisting.IndiTriviaExistView;
import uk.co.oliverbcurtis.Kratzee.ui.detail.startScreen.StartScreenView;

public class IndiTriviaRegisterView extends BaseActivity implements IndiTriviaRegisterContract.View, View.OnClickListener {

    private IndiTriviaRegisterPresenter presenter;
    private EditText et_indi_name, et_indi_student_number;
    private Button btn_submit_indi;
    private ProgressBar progress;
    public static boolean indiTriviaAccountCreated = false;
    TextView tv_studentSelection;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.individual_trivia_main_screen);
        initView();
    }


    @Override
    public void initView() {

        StartScreenView.tutorial_counter = 0;

        presenter = new IndiTriviaRegisterPresenter();
        presenter.attachView(this);

        et_indi_name = findViewById(R.id.et_indi_name);
        et_indi_name.setGravity(Gravity.CENTER);

        et_indi_student_number = findViewById(R.id.et_indi_student_number);
        et_indi_student_number.setGravity(Gravity.CENTER);

        btn_submit_indi = findViewById(R.id.btn_submit_indi);
        btn_submit_indi.setOnClickListener(this);

        tv_studentSelection = findViewById(R.id.tv_studentSelection);
        tv_studentSelection.setOnClickListener(this);

        progress = findViewById(R.id.progress);

        //If the user has decided to take the tutorial, start the first tutorial for this screen
        if(pref.getBoolean(Constants.DEMO_REQUEST_MADE,true)) {

            tutorialView.indiRegisterTutorial1(this);
        }

    }


    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.btn_submit_indi:

                String indi_name = et_indi_name.getText().toString().trim();
                String indi_student_number = et_indi_student_number.getText().toString().trim();

                if (!indi_name.isEmpty() && indi_student_number.length() == 8) {

                    showAlertDialog(indi_name, indi_student_number);

                } else {
                    BaseActivity.showToast(this,"Please Enter your Full-Name and Full Student-Number");
                }

                break;

            case R.id.tv_studentSelection:

                goToStudentNameSelection();
                break;
        }


        if(pref.getBoolean(Constants.DEMO_REQUEST_MADE,true)) {

            switch (StartScreenView.tutorial_counter) {

                case 0:
                    tutorialView.indiRegisterTutorial2(this);
                    break;


                case 1:
                    tutorialView.indiRegisterTutorial3(this);
                    break;


                case 2:
                    tutorialView.closeIndiRegisterTutorial();
                    break;
            }
        }
    }

    @Override
    public void showAlertDialog(String indi_name, String indi_student_number){

        new AlertDialog.Builder(this)
                .setTitle("Confirmation")
                .setMessage("Do you really want to start the quiz?")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(android.R.string.yes, (dialog1, whichButton) -> {

                    progress.setVisibility(View.VISIBLE);
                    indiTriviaAccountCreated = true;

                    boolean result = presenter.saveToSQLite(indi_name, indi_student_number, kratzeeDatabase);

                    if (!result) {

                        progress.setVisibility(View.INVISIBLE);
                        BaseActivity.showToast(this,"Unable to save to SQLite DB");

                    } else {

                        RequestQuestionsExternalDB requestQuestionsExternalDB = new RequestQuestionsExternalDB();
                        requestQuestionsExternalDB.getQuestions(progress, this, kratzeeDatabase);

                    }
                })
                .setNegativeButton(android.R.string.no, null).show();
    }


    @Override
    public void goToStudentNameSelection(){

        Intent intent = new Intent(getApplicationContext(), IndiTriviaExistView.class);
        startActivity(intent);
    }
}
