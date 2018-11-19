package uk.co.oliverbcurtis.Kratzee.ui.detail.lecturerCreateQuestionSets;

import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;
import android.view.Gravity;
import android.widget.Button;
import android.widget.LinearLayout;
import uk.co.oliverbcurtis.Kratzee.R;
import static android.content.ContentValues.TAG;
import static com.rd.utils.DensityUtils.dpToPx;

public class DynamicNewQuestionButton {

    public void createButton(CreateQuestionSetView view, String questionId){


            try {

                /*Dynamically create new Button which includes the names of the original team members
                 */
                final Button btn_new_question = new Button(view);

                LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1);
                btn_new_question.setBackgroundColor(Color.parseColor("#FFFFFF"));
                // Sets the text for when the button is first created.
                btn_new_question.setText("Question "+CreateQuestionSetView.questionNumber++);
                // Sets the text for when the button is not in the checked state.
                btn_new_question.setTypeface(null, Typeface.BOLD);
                btn_new_question.setTextColor(Color.WHITE);
                btn_new_question.setBackground(view.getResources().getDrawable(R.drawable.custom_button));
                //set the Tag value of the team_member ID for database submission
                btn_new_question.setTag(questionId);
                btn_new_question.setId(-1);
                btn_new_question.setGravity(Gravity.CENTER);
                params1.setMargins(100, dpToPx(10), 100, dpToPx(10));
                btn_new_question.setPadding(0, 0, 0, 0);
                btn_new_question.setLayoutParams(params1);

                view.populateLayout(btn_new_question);


            } catch (Exception e) {
                Log.d(TAG, "Failed to Create Topic button because "+e.toString());
            }
    }
}
