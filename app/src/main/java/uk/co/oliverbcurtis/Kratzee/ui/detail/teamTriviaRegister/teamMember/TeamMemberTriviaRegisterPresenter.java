package uk.co.oliverbcurtis.Kratzee.ui.detail.teamTriviaRegister.teamMember;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import uk.co.oliverbcurtis.Kratzee.sqlite.KratzeeContract;
import uk.co.oliverbcurtis.Kratzee.sqlite.KratzeeDatabase;
import uk.co.oliverbcurtis.Kratzee.ui.common.BaseActivity;
import static uk.co.oliverbcurtis.Kratzee.sqlite.KratzeeDatabase.NEW_TEAM_MEMBERS;

public class TeamMemberTriviaRegisterPresenter implements TeamMemberTriviaRegisterContract.Presenter {

    private TeamMemberTriviaRegisterContract.View view;
    private List<EditText>  dynamicTeamMemberNameList = new ArrayList<>(), dynamicTeamMemberStudentNumberList = new ArrayList<>();
    private DynamicAddTeamMemberField newTeamMember = new DynamicAddTeamMemberField();

    @Override
    public void attachView(TeamMemberTriviaRegisterContract.View view) {

        this.view = view;

    }

    @Override
    public void confirmAddTeamMember(EditText et_team_name1, EditText et_team_student_number1, EditText et_team_name2, EditText et_team_student_number2, LinearLayout mLayout, TeamMemberTriviaRegisterView context) {

        //If the first two student input fields are empty and the user tries to create a dynamic field, ask them to complete the first two
        if (et_team_name1.getText().toString().trim().isEmpty() || et_team_student_number1.getText().toString().trim().length() != 8
                || et_team_name2.getText().toString().trim().isEmpty() || et_team_student_number2.getText().toString().trim().length() != 8) {

            BaseActivity.showToast(context, "Please Add Your Full-Name & Full 8-Character Student-Number, Starting in the First Two Team-Member Slots (Two Team-member minimum)");

        } else {

            new AlertDialog.Builder((Context)view)
                    .setTitle("Add Team-Member")
                    .setMessage("Do you really want to add another team-member?")
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setPositiveButton(android.R.string.yes, (dialog, whichButton) -> newTeamMember.createNewTeamMember(mLayout, context, dynamicTeamMemberNameList, dynamicTeamMemberStudentNumberList))
                    .setNegativeButton(android.R.string.no, null).show();
        }
    }



    @Override
    public void validateSubmittedNames(EditText et_team_name1, EditText et_team_student_number1, EditText et_team_name2, EditText et_team_student_number2, TeamMemberTriviaRegisterView context, ProgressBar progress) {

        //If the first two student input fields are empty and the user trys to create a dynamic field, ask them to complete the first two
        if (et_team_name1.getText().toString().trim().isEmpty() || et_team_student_number1.getText().toString().trim().length() != 8
                || et_team_name2.getText().toString().trim().isEmpty() || et_team_student_number2.getText().toString().trim().length() != 8) {

            BaseActivity.showToast(context, "Please Add Your Full-Name & Full 8-Character Student-Number, Starting in the First Two Team-Member Slots (Two Team-member minimum)");

        } else {

            //Create a list array of the all of the completed fields used, into one two final lists
            List<String> teamMemberNameArray = new ArrayList<String>();
            List<String> teamMemberStudentNumberArray = new ArrayList<String>();

            //First check that the lists are not empty
            if(dynamicTeamMemberNameList != null && !dynamicTeamMemberNameList.isEmpty()
                    && dynamicTeamMemberStudentNumberList != null && !dynamicTeamMemberStudentNumberList.isEmpty()) {
                //Retrieving values from dynamically created EditText
                String[] teamMember3name = new String[dynamicTeamMemberNameList.size()];
                String[] teamMember3studentNumber = new String[dynamicTeamMemberStudentNumberList.size()];

                //Loop though each list and check that they meet the input requirements
                for (int i = 0; i < dynamicTeamMemberNameList.size(); i++) {

                    teamMember3name[i] = dynamicTeamMemberNameList.get(i).getText().toString().trim();
                    teamMember3studentNumber[i] = dynamicTeamMemberStudentNumberList.get(i).getText().toString().trim();

                    //add individual student names and their student numbers to the array as long as the dynamic edit-texts are not empty
                    if (teamMember3name[i].length() > 0 && teamMember3studentNumber[i].length() == 8) {

                        teamMemberNameArray.add(teamMember3name[i]);
                        teamMemberStudentNumberArray.add(teamMember3studentNumber[i]);

                        //If we are on the last iteration and the condition above is passed, we can call the alert dialog
                        if(i+1 == dynamicTeamMemberNameList.size()){

                            //Then add the content from the static edit-texts
                            teamMemberNameArray.add(et_team_name1.getText().toString().trim());
                            teamMemberStudentNumberArray.add(et_team_student_number1.getText().toString().trim());

                            teamMemberNameArray.add(et_team_name2.getText().toString().trim());
                            teamMemberStudentNumberArray.add(et_team_student_number2.getText().toString().trim());

                            view.confirmTeamMemberSubmission(teamMemberNameArray, teamMemberStudentNumberArray);
                        }


                    } else {

                        BaseActivity.showToast(context, "Please make sure you have entered your full name and 8-character student number, or remove all content from the created inputfields, if you do not wish to use them");
                    }
                }


            }else {

                //Else, just add the values of the static inputs only
                teamMemberNameArray.add(et_team_name1.getText().toString().trim());
                teamMemberStudentNumberArray.add(et_team_student_number1.getText().toString().trim());

                teamMemberNameArray.add(et_team_name2.getText().toString().trim());
                teamMemberStudentNumberArray.add(et_team_student_number2.getText().toString().trim());

                view.confirmTeamMemberSubmission(teamMemberNameArray, teamMemberStudentNumberArray);
            }
        }
    }


    @Override
    public boolean storeTeamMembersInSQLiteDB(List<String> teamMemberNameArray, List<String> teamMemberStudentNumberArray, KratzeeDatabase kratzeeDatabase) {


        long result = 0;

        //Clear any existing team_member data if it exists
        try {

            android.database.sqlite.SQLiteDatabase db = kratzeeDatabase.getWritableDatabase();
            db.delete(NEW_TEAM_MEMBERS, "1", null);

            for (int i = 0; i < teamMemberNameArray.size(); i++) {
                String uniqueId = UUID.randomUUID().toString();
                ContentValues contentValues = new ContentValues();
                contentValues.put(KratzeeContract.NEW_TEAM_MEMBER_ID, uniqueId);
                contentValues.put(KratzeeContract.NEW_TEAM_MEMBER_FULLNAME, teamMemberNameArray.get(i));
                contentValues.put(KratzeeContract.NEW_TEAM_MEMBER_STUDENT_NUMBER, teamMemberStudentNumberArray.get(i));
                contentValues.put(KratzeeContract.NEW_TEAM_MEMBER_POINTS, 0);

                //insert rows
                result = db.insert(NEW_TEAM_MEMBERS, null, contentValues);

        }
        db.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result != -1;
    }
}
