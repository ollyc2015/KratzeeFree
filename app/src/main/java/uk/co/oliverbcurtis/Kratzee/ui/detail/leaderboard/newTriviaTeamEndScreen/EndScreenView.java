package uk.co.oliverbcurtis.Kratzee.ui.detail.leaderboard.newTriviaTeamEndScreen;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import uk.co.oliverbcurtis.Kratzee.R;
import uk.co.oliverbcurtis.Kratzee.model.Score;
import uk.co.oliverbcurtis.Kratzee.ui.common.BaseActivity;
import uk.co.oliverbcurtis.Kratzee.ui.detail.quizType.QuizTypeView;

public class EndScreenView extends BaseActivity implements View.OnClickListener  {

    private TextView tv_points_display;
    private Button btn_home;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.points_summary);

        initView();
    }

    public void initView(){

        tv_points_display = findViewById(R.id.tv_points_display);
        tv_points_display.setText("All Team-Members Will Receive "+Score.getScore()+" Points For This Session, You May Now Close The App!");

        btn_home = findViewById(R.id.btn_home);
        btn_home.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.btn_home:
                backToMainMenu();
                break;

        }
    }


    public void backToMainMenu(){

        Score.resetScore();

        Intent intent = new Intent(getApplicationContext(), QuizTypeView.class);
        startActivity(intent);
    }
}
