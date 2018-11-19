package uk.co.oliverbcurtis.Kratzee.ui.detail.lecturerEditQuestionSets.selectedTopicQuestions;

import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;
import android.view.Gravity;
import android.widget.Button;
import android.widget.LinearLayout;

import uk.co.oliverbcurtis.Kratzee.R;
import uk.co.oliverbcurtis.Kratzee.model.Answer;
import uk.co.oliverbcurtis.Kratzee.model.Question;
import static android.content.ContentValues.TAG;
import static com.rd.utils.DensityUtils.dpToPx;

public class DynamicQuestionButton {

    public void createButton(SelectedTopicView view, Question question){

        int questionNumber = 1;

        for (int i = 0; i < question.getQuestionIDList().size(); i++)


            try {

                /*Dynamically create new Button which includes the names of the original team members
                 */
                final Button btn_topic_question = new Button(view);

                LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1);
                btn_topic_question.setBackgroundColor(Color.parseColor("#FFFFFF"));
                // Sets the text for when the button is first created.
                btn_topic_question.setText("Question "+questionNumber++);
                // Sets the text for when the button is not in the checked state.
                btn_topic_question.setTypeface(null, Typeface.BOLD);
                btn_topic_question.setTextColor(Color.WHITE);
                btn_topic_question.setBackground(view.getResources().getDrawable(R.drawable.mybutton));
                //set the Tag value of the team_member ID for database submission
                btn_topic_question.setTag(question.getQuestionIDList().get(i));
                btn_topic_question.setId(-1);
                btn_topic_question.setGravity(Gravity.CENTER);
                params1.setMargins(100, dpToPx(10), 100, dpToPx(10));
                btn_topic_question.setPadding(0, 0, 0, 0);
                btn_topic_question.setLayoutParams(params1);

                view.populateLayout(btn_topic_question);


            } catch (Exception e) {
                Log.d(TAG, "Failed to Create Topic button because "+e.toString());
            }

    }
}
