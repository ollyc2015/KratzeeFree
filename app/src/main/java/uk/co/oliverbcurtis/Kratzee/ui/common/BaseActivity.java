package uk.co.oliverbcurtis.Kratzee.ui.common;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.Toolbar;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;


import javax.inject.Inject;

import uk.co.oliverbcurtis.Kratzee.R;
import uk.co.oliverbcurtis.Kratzee.dagger.DaggerApplication;
import uk.co.oliverbcurtis.Kratzee.model.Constants;
import uk.co.oliverbcurtis.Kratzee.sqlite.KratzeeDatabase;
import uk.co.oliverbcurtis.Kratzee.ui.detail.individualQuizScreen.IndiQuizScreenView;
import uk.co.oliverbcurtis.Kratzee.ui.detail.lecturerEditQuestionSets.selectedTopicQuestions.SelectedTopicView;
import uk.co.oliverbcurtis.Kratzee.ui.detail.lecturerLogin.LecturerLoginView;
import uk.co.oliverbcurtis.Kratzee.ui.detail.startScreen.StartScreenView;
import uk.co.oliverbcurtis.Kratzee.ui.detail.teamQuizScreen.TeamQuizScreenView;
import uk.co.oliverbcurtis.Kratzee.ui.detail.tutorialScreens.TutorialView;


public class BaseActivity extends AppCompatActivity {

    @Inject public
    KratzeeDatabase kratzeeDatabase;
    //@Inject public
   // Context context;
    @Inject public
    TutorialView tutorialView;
    @Inject public
    SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ((DaggerApplication) getApplication()).getAppComponent().inject(this);
/*
        Toolbar toolbar = findViewById(R.id.quiz_app_bar);
        setSupportActionBar(toolbar);

        Toolbar toolbar2 = findViewById(R.id.quiz_type);
        setSupportActionBar(toolbar2);

        Toolbar toolbar3 = findViewById(R.id.quiz_pin);
        setSupportActionBar(toolbar3);

        Toolbar toolbar4 = findViewById(R.id.add_team_member);
        setSupportActionBar(toolbar4);

        Toolbar toolbar5 = findViewById(R.id.quiz_summary);
        setSupportActionBar(toolbar5);

        Toolbar toolbar6 = findViewById(R.id.indi_assessment);
        setSupportActionBar(toolbar6);

        Toolbar toolbar7 = findViewById(R.id.team_selection);
        setSupportActionBar(toolbar7);
*/
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu, this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public static void showToast(Context context, String toast) {
        Toast.makeText(context, toast, Toast.LENGTH_LONG).show();
    }

    public static void goToIndiQuizScreen(Activity activity) {
        activity.startActivity(new Intent(activity, IndiQuizScreenView.class));
    }

    public static void goToTeamQuizScreen(Activity activity){
        activity.startActivity(new Intent(activity, TeamQuizScreenView.class));
    }

    public static void goToAdminEditTopic(Activity activity){
        activity.startActivity(new Intent(activity, SelectedTopicView.class));
    }
}
