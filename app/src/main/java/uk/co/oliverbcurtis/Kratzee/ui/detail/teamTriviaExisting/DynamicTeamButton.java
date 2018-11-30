package uk.co.oliverbcurtis.Kratzee.ui.detail.teamTriviaExisting;

import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.Gravity;
import android.widget.LinearLayout;
import java.util.List;
import uk.co.oliverbcurtis.Kratzee.R;
import uk.co.oliverbcurtis.Kratzee.model.Team;
import static android.content.ContentValues.TAG;
import static com.rd.utils.DensityUtils.dpToPx;

public class DynamicTeamButton {

    public void createButton(TeamTriviaExistView view, Team team) {

        List teamIDArray = team.getLoaded_team_ids();
        List indiPointsArray = team.getLoaded_team_points();
        int count = 0;

        for (final Object value : teamIDArray)

            try {

                /*Dynamically create new Button which includes the team names
                 */
                final AppCompatButton btn_teamName = new AppCompatButton(view);

                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1);
                btn_teamName.setBackgroundColor(Color.parseColor("#FF4DCBBF"));
                btn_teamName.setTextColor(Color.WHITE);
                btn_teamName.setText(team.getLoaded_team_names().get(count).toString());
                btn_teamName.setHint(indiPointsArray.get(count).toString());
                count++;
                //set the Tag value of the question ID for database submission for selection during edit method
                btn_teamName.setTag(value);
                btn_teamName.setTypeface(null, Typeface.BOLD);
                //set button style to custom button
                btn_teamName.setBackground(view.getResources().getDrawable(R.drawable.custom_button));
                //btn_question.setText("Question " + value);
                btn_teamName.setGravity(Gravity.CENTER);
                params.setMargins(100, dpToPx(10), 100, dpToPx(10));
                btn_teamName.setPadding(0, 0, 0, 0);
                btn_teamName.setLayoutParams(params);

                //Pass the created button to populateStudentNames method found in IndiTriviaExistView
                view.populateTeamNames(btn_teamName, view);

            } catch (Exception e) {
                Log.d(TAG, "Failed to create new team-member button because "+e.toString());
            }
    }
}
