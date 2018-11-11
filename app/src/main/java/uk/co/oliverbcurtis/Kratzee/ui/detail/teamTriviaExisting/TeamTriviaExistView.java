package uk.co.oliverbcurtis.Kratzee.ui.detail.teamTriviaExisting;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.AppCompatButton;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.ToggleButton;
import java.util.ArrayList;
import java.util.List;
import uk.co.oliverbcurtis.Kratzee.R;
import uk.co.oliverbcurtis.Kratzee.model.Team;
import uk.co.oliverbcurtis.Kratzee.model.TeamMember;
import uk.co.oliverbcurtis.Kratzee.ui.common.BaseActivity;
import uk.co.oliverbcurtis.Kratzee.ui.common.DynamicNewTeamMemberButton;
import uk.co.oliverbcurtis.Kratzee.ui.common.DynamicTeamMemberButton;
import uk.co.oliverbcurtis.Kratzee.ui.common.RequestQuestionsExternalDB;


public class TeamTriviaExistView extends BaseActivity implements TeamTriviaExistContract.View, SwipeRefreshLayout.OnRefreshListener  {

    private ProgressBar progress;
    private SwipeRefreshLayout swipe_container;
    private LinearLayout teamNameLayout;
    private TeamTriviaExistPresenter presenter;
    private EditText et_search_name;
    private List<String> new_team_member_id, new_team_member_present, existing_team_member_present, new_team_member_name, new_team_member_student_number, new_student_email;
    private DynamicNewTeamMemberButton dynamicNewTeamMemberButton;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.team_trivia_existing_screen);

        initView();
    }


    @Override
    public void initView() {

        presenter = new TeamTriviaExistPresenter();
        presenter.attachView(this);

        dynamicNewTeamMemberButton = new DynamicNewTeamMemberButton();

        progress = findViewById(R.id.progress);

        teamNameLayout = findViewById(R.id.teamNameLayout);

        swipe_container = findViewById(R.id.swipe_container);
        swipe_container.setOnRefreshListener(this);

        et_search_name = findViewById(R.id.et_search_name);

        searchFilter();

        onRefresh();

    }


    @Override
    public void onRefresh() {
        teamNameLayout.removeAllViews();
        presenter.allTeamNames(progress, pref);
        swipe_container.setRefreshing(false);
    }

    @Override
    public void callDynamicLayoutAdapter(Team team){

        DynamicTeamButton dynamicTeamButton = new DynamicTeamButton();
        dynamicTeamButton.createButton(this, team);

    }

    @Override
    public void populateTeamNames(AppCompatButton btn_indiName, TeamTriviaExistView view){

        teamNameLayout.addView(btn_indiName);

        btn_indiName.setOnClickListener(v -> {
            Log.i("Clicked", "" + v.getTag());
            String teamName = (String) btn_indiName.getText();
            String teamPoints = (String) btn_indiName.getHint();
            String teamID = v.getTag().toString();
            showTeamSelectionDialog(teamID, teamName, teamPoints);
        });
    }

    @Override
    public void searchFilter(){

        et_search_name.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                // When user changed the Text
                for(int i = 0; i < teamNameLayout.getChildCount(); i++){

                    if(!((AppCompatButton)teamNameLayout.getChildAt(i)).getText().toString().toLowerCase().contains(cs.toString().toLowerCase())){

                        teamNameLayout.getChildAt(i).setVisibility(View.GONE);

                        Log.v("Buttons To Disappear", (String)((AppCompatButton)teamNameLayout.getChildAt(i)).getText());
                    }else{
                        teamNameLayout.getChildAt(i).setVisibility(View.VISIBLE);
                    }
                }
            }
            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) { }
            @Override
            public void afterTextChanged(Editable arg0) { }
        });
    }


    @Override
    public void showTeamSelectionDialog(String teamID, String teamName, String teamPoints){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Team Name: " + teamName);
        builder.setPositiveButton("Select Team", (dialog, which) -> { });
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());
        AlertDialog dialog = builder.create();
        dialog.show();
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(v -> {

            dialog.dismiss();
            progress.setVisibility(View.VISIBLE);

            boolean submitted = presenter.storeSelectedTeamInSQLiteDB(teamID, teamName, kratzeeDatabase, teamPoints);

            if (!submitted) {

                progress.setVisibility(View.INVISIBLE);
                BaseActivity.showToast(TeamTriviaExistView.this,"Unable to save to SQLite DB");

            } else {

                //Request team-member names
                presenter.getTeamMembers(progress, pref, teamID, this, kratzeeDatabase);

            }
        });
    }

    @Override
    public void showTeamSelectionLayout(TeamMember teamMember1, TeamTriviaExistView teamTriviaExistView){

        //Inflate the layout select_team_member
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = (LayoutInflater) teamTriviaExistView.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        View view = inflater.inflate(R.layout.select_existing_team_member, null);

        //Make reference to the empty linear layout, found in the select_team_member layout, so that dynamic data can be looped into it
        LinearLayout teamMemberLayout = view.findViewById(R.id.teamMemberLayout);

        //Then create the dynamic existing team-member button
        DynamicTeamMemberButton dynamicTeamMemberButton = new DynamicTeamMemberButton();
        dynamicTeamMemberButton.createButton(getApplicationContext(), teamMember1, teamMemberLayout,  this);

        //The following are the EditText used to add another team-member to an existing team
        EditText et_team_member_name = view.findViewById(R.id.et_team_member_name);
        EditText et_team_member_student_number = view.findViewById(R.id.et_team_member_student_number);
        EditText et_team_member_email = view.findViewById(R.id.et_team_member_email);


        builder.setView(view);
        builder.setTitle("Team-Members");
        builder.setPositiveButton("Start Quiz", (dialog, which) -> { });
        builder.setNeutralButton("Add Team Member", (dialog, which) -> { });
        builder.setNegativeButton("Cancel", (dialog, which) ->{

            //If the cancel button is pushed, create a new DynamicNewTeamMemberButton object to loose reference to any previously added items to
            //to the lists in DynamicNewTeamMemberButton.
            dynamicNewTeamMemberButton = new DynamicNewTeamMemberButton();
            dialog.dismiss();

        });


        AlertDialog dialog = builder.create();


        dialog.setOnShowListener(dialogInterface -> {

            submitButtonAction(dialog, teamMember1);
            addMemberButtonAction(dialog, et_team_member_name, et_team_member_student_number, et_team_member_email, teamMemberLayout, view);

        });

        dialog.show();
    }

    //Adding existing Team-member buttons to the layout
    @Override
    public void addExistingTeamMemberToLayout(ToggleButton teamMemberButton, LinearLayout teamMemberLayout){

        teamMemberLayout.addView(teamMemberButton);

        addOnClickListenerToTeamMemberButton(teamMemberButton);
    }

    //Add an onClickListener to the existing team-member buttons
    @Override
    public void addOnClickListenerToTeamMemberButton(ToggleButton teamMemberButton){

        existing_team_member_present = new ArrayList<>();

        teamMemberButton.setOnClickListener(v -> {

            presenter.existingTeamMember(teamMemberButton, v, existing_team_member_present);


        });
    }


    //Adding the newly created Team-member button to the layout
    @Override
    public void addNewTeamMemberToLayout(ToggleButton teamMemberButton, LinearLayout teamMemberLayout, List<String> new_team_member_name_array, List<String> new_student_number_array, List<String> new_team_member_present_array, List<String> new_student_email_array, List<String> new_team_member_id_array){

        teamMemberLayout.addView(teamMemberButton);

        //These passed params are made global so access can be made when the user submits their selected team (see line 252)
        new_team_member_id = new_team_member_id_array;
        new_team_member_present = new_team_member_present_array;
        new_team_member_name = new_team_member_name_array;
        new_team_member_student_number = new_student_number_array;
        new_student_email = new_student_email_array;

        addOnClickListenerToNewTeamMemberButton(teamMemberButton);
    }

    //Adding an onClickListener to the newly created NEW team-member button
    @Override
    public void addOnClickListenerToNewTeamMemberButton(ToggleButton teamMemberButton){


        teamMemberButton.setOnClickListener(v -> {

            presenter.newTeamMember(teamMemberButton, v, new_team_member_present);


        });
    }

    public void submitButtonAction(AlertDialog dialog, TeamMember teamMember1){

        Button submitButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
        submitButton.setOnClickListener(view1 -> {

            if(!existing_team_member_present.isEmpty() || new_team_member_name != null) {


                new AlertDialog.Builder(this)
                        .setTitle("Confirmation")
                        .setMessage("Do you really want to start the quiz?")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton(android.R.string.yes, (dialog1, whichButton) -> {

                            dialog.dismiss();
                            progress.setVisibility(View.VISIBLE);
                            presenter.submitTeamMembers(new_team_member_present, existing_team_member_present, new_team_member_name, new_team_member_student_number, kratzeeDatabase, pref, teamMember1, new_student_email, new_team_member_id);

                        })
                        .setNegativeButton(android.R.string.no, null).show();

            }else{
                showToast(TeamTriviaExistView.this, "At Least One Team-Member Should be Marked as Present");
            }
        });
    }

    public void addMemberButtonAction(AlertDialog dialog, EditText et_team_member_name, EditText et_team_member_student_number, EditText et_team_member_email, LinearLayout teamMemberLayout, View view){

        Button addNewTeamMember = dialog.getButton(AlertDialog.BUTTON_NEUTRAL);

        addNewTeamMember.setOnClickListener(view1 -> {

            //Create a new button for a new team-member added to an existing team
            if(!et_team_member_name.getText().toString().isEmpty() && et_team_member_student_number.getText().toString().length() == 8) {

                dynamicNewTeamMemberButton.createButton(getApplicationContext(), teamMemberLayout, et_team_member_name, et_team_member_student_number, et_team_member_email, this);

                et_team_member_name.getText().clear();
                et_team_member_student_number.getText().clear();

            }else{

                ((ScrollView) view.findViewById(R.id.myTeamMemberScrollView)).fullScroll(View.FOCUS_DOWN);
                showToast(this, "Please Complete Both Fields Before Trying to Add a New Team-Member");
            }

        });
    }



    @Override
    public void getQuestions(){

        RequestQuestionsExternalDB requestQuestionsExternalDB = new RequestQuestionsExternalDB();
        requestQuestionsExternalDB.getQuestions(progress, this, kratzeeDatabase);
    }
}
