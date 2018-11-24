package uk.co.oliverbcurtis.Kratzee.ui.detail.teamTriviaRegister.team;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import uk.co.oliverbcurtis.Kratzee.R;
import uk.co.oliverbcurtis.Kratzee.model.Constants;
import uk.co.oliverbcurtis.Kratzee.ui.common.BaseActivity;
import uk.co.oliverbcurtis.Kratzee.ui.detail.teamTriviaExisting.TeamTriviaExistView;
import uk.co.oliverbcurtis.Kratzee.ui.detail.teamTriviaRegister.teamMember.TeamMemberTriviaRegisterView;

public class TeamTriviaRegisterView extends BaseActivity implements TeamTriviaRegisterContract.View, View.OnClickListener {

    private EditText et_team;
    private AppCompatButton btn_submit_team;
    private ProgressBar progress;
    private TextView tv_teamSelection;
    private TeamTriviaRegisterPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.team_trivia_main_screen);

        initView();
    }


    @Override
    public void initView() {

        et_team = findViewById(R.id.et_team);
        et_team.setGravity(Gravity.CENTER);

        btn_submit_team = findViewById(R.id.btn_submit_team);
        btn_submit_team.setOnClickListener(this);

        tv_teamSelection = findViewById(R.id.tv_teamSelection);
        tv_teamSelection.setOnClickListener(this);

        presenter = new TeamTriviaRegisterPresenter();

        progress = findViewById(R.id.progress);

        //If the user has decided to take the tutorial, start the first tutorial
        if(pref.getBoolean(Constants.DEMO_REQUEST_MADE,true)) {

            tutorialView.teamRegistrationTutorial1(this);
        }
    }


    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.tv_teamSelection:
                goToTeamSelection();
                break;


            case R.id.btn_submit_team:

                String teamName = et_team.getText().toString().trim();
                boolean submitted = false;

                if(!teamName.isEmpty()) {

                    progress.setVisibility(View.VISIBLE);

                    submitted = presenter.teamSubmission(teamName,this, kratzeeDatabase);
                }else{
                    showToast(this, "Please Submit a Team Name");
                }

                if(submitted){

                    goToAddTeamMembers();

                }else{
                    progress.setVisibility(View.INVISIBLE);
                    showToast(this, "Unable to submit team name");
                }
                break;
        }
    }


    private void goToAddTeamMembers(){

        progress.setVisibility(View.INVISIBLE);

        Intent intent = new Intent(getApplicationContext(), TeamMemberTriviaRegisterView.class);
        startActivity(intent);
    }


    private void goToTeamSelection(){

        Intent intent = new Intent(getApplicationContext(), TeamTriviaExistView.class);
        startActivity(intent);
    }
}
