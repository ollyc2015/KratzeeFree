package uk.co.oliverbcurtis.Kratzee.ui.detail.leaderboard.existingTeamLeaderboard;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;
import java.util.Set;

import uk.co.oliverbcurtis.Kratzee.R;
import uk.co.oliverbcurtis.Kratzee.model.Score;
import uk.co.oliverbcurtis.Kratzee.model.TeamMember;
import uk.co.oliverbcurtis.Kratzee.ui.common.BaseActivity;
import uk.co.oliverbcurtis.Kratzee.ui.detail.quizType.QuizTypeView;
import uk.co.oliverbcurtis.Kratzee.ui.detail.startScreen.StartScreenView;


public class LeaderboardView extends BaseActivity implements LeaderboardContract.View, View.OnClickListener {

    private LeaderboardPresenter presenter;
    private ListView points_summary_listview;
    private Button btn_close;
    private TextView pointsInfo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.leaderboard);

        initView();
    }

    @Override
    public void initView() {

        presenter = new LeaderboardPresenter();
        presenter.attachView(this);

        //Retrieve the values
        Set<String> questionArray = pref.getStringSet("NumberOfQuestions", null);

        points_summary_listview = findViewById(R.id.points_summary_listview);

        pointsInfo = findViewById(R.id.tv_points_info);
        pointsInfo.setText("This Sessions Points: "+Score.getScore()+"/"+questionArray.size()*4+" Points\nThese Points Will Be Added To Your Existing Points (If Applicable). Students Marked as Absent will Not Benefit From This Sessions Points.");

        btn_close = findViewById(R.id.btn_close);
        btn_close.setOnClickListener(this);

        presenter.getTeamMemberPoints(kratzeeDatabase, pref);

    }

    @Override
    public void populateLeaderboard(List<TeamMember> teamMembers){

        LeaderboardAdapter leaderboardAdapter = new LeaderboardAdapter(this, teamMembers);

        points_summary_listview.setAdapter(leaderboardAdapter);

    }


    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.btn_close:
                backToMainMenu();
                break;

        }
    }

    @Override
    public void backToMainMenu(){

        Score.resetScore();

        Intent intent = new Intent(getApplicationContext(), StartScreenView.class);
        startActivity(intent);
    }
}
