package uk.co.oliverbcurtis.Kratzee.ui.detail.teamTriviaRegister.teamMember;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import uk.co.oliverbcurtis.Kratzee.R;
import uk.co.oliverbcurtis.Kratzee.model.Constants;
import uk.co.oliverbcurtis.Kratzee.ui.common.BaseActivity;
import uk.co.oliverbcurtis.Kratzee.ui.common.RequestQuestionsExternalDB;

public class TeamMemberTriviaRegisterView extends BaseActivity implements TeamMemberTriviaRegisterContract.View, View.OnClickListener {

    private EditText et_team_name1, et_team_name2, et_team_student_number1, et_team_student_number2;
    private Button btn_submit_names;
    private TeamMemberTriviaRegisterPresenter presenter;
    private LinearLayout mLayout;
    private TextView tv_add_name, tv_teamNames;
    private ProgressBar progress;
    //This is needed when as part of if statement to choose the correct points submission method
    public static boolean newTeamSubmitted = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.team_member__register_trivia);

        initView();
    }


    @Override
    public void initView() {


        et_team_name1 = findViewById(R.id.et_team_name1);
        et_team_name1.setGravity(Gravity.CENTER);
        et_team_student_number1 = findViewById(R.id.et_team_student_number1);
        et_team_student_number1.setGravity(Gravity.CENTER);

        et_team_name2 = findViewById(R.id.et_team_name2);
        et_team_name2.setGravity(Gravity.CENTER);
        et_team_student_number2 = findViewById(R.id.et_team_student_number2);
        et_team_student_number2.setGravity(Gravity.CENTER);

        btn_submit_names = findViewById(R.id.btn_submit_names);
        btn_submit_names.setOnClickListener(this);

        tv_add_name = findViewById(R.id.tv_add_name);
        tv_add_name.setOnClickListener(this);

        tv_teamNames = findViewById(R.id.tv_teamNames);
        tv_teamNames.setText(pref.getString(Constants.TEAM_NAME,""));

        progress = findViewById(R.id.progress);

        mLayout = findViewById(R.id.editTextGroupLayout);

        presenter = new TeamMemberTriviaRegisterPresenter();
        presenter.attachView(this);
    }


    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.tv_add_name:
                presenter.confirmAddTeamMember(et_team_name1, et_team_student_number1, et_team_name2, et_team_student_number2, mLayout, this);
                break;

            case R.id.btn_submit_names:
                //Check submitted values
                presenter.validateSubmittedNames(et_team_name1, et_team_student_number1, et_team_name2, et_team_student_number2, this, progress);
                break;
        }
    }

    @Override
    public void confirmTeamMemberSubmission(List<String> teamMemberNameArray, List<String> teamMemberStudentNumberArray){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Please Confirm");
        builder.setMessage("Are you happy to submit your team-members? If you are, click 'Yes' to start your quiz!");
        builder.setPositiveButton("Yes", (dialog, which) -> { });
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());
        AlertDialog dialog = builder.create();
        dialog.show();
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(v -> {

            newTeamSubmitted = true;

            dialog.dismiss();
            progress.setVisibility(View.VISIBLE);

            boolean submitted = presenter.storeTeamMembersInSQLiteDB(teamMemberNameArray, teamMemberStudentNumberArray, kratzeeDatabase);

            if (!submitted) {

                progress.setVisibility(View.INVISIBLE);
                BaseActivity.showToast(this,"Unable to save to SQLite DB");

            } else {

                RequestQuestionsExternalDB requestQuestionsExternalDB = new RequestQuestionsExternalDB();
                requestQuestionsExternalDB.getQuestions(progress, this, kratzeeDatabase);

            }
        });

    }
}
