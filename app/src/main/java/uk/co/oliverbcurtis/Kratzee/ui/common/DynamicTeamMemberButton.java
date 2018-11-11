package uk.co.oliverbcurtis.Kratzee.ui.common;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.ToggleButton;
import java.util.ArrayList;
import java.util.List;

import uk.co.oliverbcurtis.Kratzee.R;
import uk.co.oliverbcurtis.Kratzee.model.TeamMember;
import uk.co.oliverbcurtis.Kratzee.ui.detail.teamTriviaExisting.TeamTriviaExistView;

import static android.content.ContentValues.TAG;
import static com.rd.utils.DensityUtils.dpToPx;

//This class is used to generate the dynamic team-member buttons for both the assessment and trivia based quizzes
public class DynamicTeamMemberButton {


    public void createButton(Context view, TeamMember teamMember1, LinearLayout teamMemberLayout, TeamTriviaExistView teamTriviaExistView){


        for (int i = 0; i < teamMember1.getLoaded_team_member_id().size(); i++)


            try {

                /*Dynamically create new Button which includes the names of the original team members
                 */
                final ToggleButton btn_teamMember_name = new ToggleButton(view);

                LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1);
                btn_teamMember_name.setBackgroundColor(Color.parseColor("#FFFFFF"));
                // Sets the text for when the button is first created.
                btn_teamMember_name.setText(teamMember1.getLoaded_fullName().get(i).toString() + " | " + teamMember1.getLoaded_student_number().get(i).toString());
                // Sets the text for when the button is not in the checked state.
                btn_teamMember_name.setTextOff(teamMember1.getLoaded_fullName().get(i).toString() + " | " + teamMember1.getLoaded_student_number().get(i).toString());
                // Sets the text for when the button is in the checked state.
                btn_teamMember_name.setTextOn(teamMember1.getLoaded_fullName().get(i).toString() + " | " + teamMember1.getLoaded_student_number().get(i).toString());
                btn_teamMember_name.setTypeface(null, Typeface.BOLD);
                btn_teamMember_name.setTextColor(Color.WHITE);
                btn_teamMember_name.setBackground(view.getResources().getDrawable(R.drawable.mybutton));
                //set the Tag value of the team_member ID for database submission
                btn_teamMember_name.setTag(teamMember1.getLoaded_team_member_id().get(i).toString());
                btn_teamMember_name.setId(-1);
                btn_teamMember_name.setGravity(Gravity.CENTER);
                params1.setMargins(100, dpToPx(10), 100, dpToPx(10));
                btn_teamMember_name.setPadding(0, 0, 0, 0);
                btn_teamMember_name.setLayoutParams(params1);

                teamTriviaExistView.addExistingTeamMemberToLayout(btn_teamMember_name, teamMemberLayout);



            } catch (Exception e) {
                Log.d(TAG, "Failed to Create Team Member button because "+e.toString());
            }

    }
}
