package uk.co.oliverbcurtis.Kratzee.ui.common;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;
import android.view.Gravity;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ToggleButton;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import uk.co.oliverbcurtis.Kratzee.R;
import uk.co.oliverbcurtis.Kratzee.ui.detail.teamTriviaExisting.TeamTriviaExistView;

import static android.content.ContentValues.TAG;
import static com.rd.utils.DensityUtils.dpToPx;

//This class is used to generate the NEW dynamic team-member buttons for both the assessment and trivia based quizzes
public class DynamicNewTeamMemberButton {


    //List of ids of all students (regardless if present or not)
    private List<String> new_team_member_id = new ArrayList<>();
    private List<String> new_team_member_present = new ArrayList<>();
    private List<String> new_team_member_name = new ArrayList<>();
    private List<String> new_student_number = new ArrayList<>();
    private List<String> new_student_email = new ArrayList<>();


    public void createButton(Context view, LinearLayout teamMemberLayout, EditText et_team_member_name, EditText et_team_member_student_number, EditText et_team_member_email, TeamTriviaExistView teamTriviaExistView){


        String newTeamMemberUniqueID = UUID.randomUUID().toString();

        try {

            /*Dynamically create new Button of the newly added team-member
             */
            final ToggleButton btn_teamMember_name = new ToggleButton(view);

            LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, dpToPx(50), 1);
            btn_teamMember_name.setBackgroundColor(Color.parseColor("#FFFFFF"));
            // Sets the text for when the button is first created.
            btn_teamMember_name.setText(et_team_member_name.getText() +" | "+ et_team_member_student_number.getText());
            // Sets the text for when the button is not in the checked state.
            btn_teamMember_name.setTextOff(et_team_member_name.getText() +" | "+ et_team_member_student_number.getText());
            // Sets the text for when the button is in the checked state.
            btn_teamMember_name.setTextOn(et_team_member_name.getText() +" | "+ et_team_member_student_number.getText());
            btn_teamMember_name.setTypeface(null, Typeface.BOLD);
            btn_teamMember_name.setTextColor(Color.WHITE);
            btn_teamMember_name.setBackground(view.getResources().getDrawable(R.drawable.mybutton));
            btn_teamMember_name.setTag(newTeamMemberUniqueID);
            btn_teamMember_name.setHint(et_team_member_student_number.toString());
            btn_teamMember_name.setGravity(Gravity.CENTER);
            params1.setMargins(100, dpToPx(10), 100, dpToPx(10));
            btn_teamMember_name.setPadding(0, 0, 0, 0);
            btn_teamMember_name.setLayoutParams(params1);

            new_team_member_id.add(btn_teamMember_name.getTag().toString());
            new_team_member_name.add(et_team_member_name.getText().toString());
            new_student_number.add(et_team_member_student_number.getText().toString());
            new_student_email.add(et_team_member_email.getText().toString());


            teamTriviaExistView.addNewTeamMemberToLayout(btn_teamMember_name, teamMemberLayout, new_team_member_name, new_student_number, new_team_member_present, new_student_email, new_team_member_id);


        } catch (Exception e) {
            Log.d(TAG, "Failed to Create Team-Member button because "+e.toString());
        }
    }
}
