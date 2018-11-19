package uk.co.oliverbcurtis.Kratzee.ui.detail.lecturerEditQuestionSets.selectTopicToEdit;

import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import java.util.LinkedHashSet;
import java.util.Set;
import uk.co.oliverbcurtis.Kratzee.R;
import uk.co.oliverbcurtis.Kratzee.model.Question;
import static android.content.ContentValues.TAG;
import static com.rd.utils.DensityUtils.dpToPx;

public class DynamicQuestionTopicButton {

    public void createButton(SelectTopicView view, Question question, LinearLayout topicLayour, ProgressBar progress, SwipeRefreshLayout swipe_container){

        progress.setVisibility(View.INVISIBLE);
        swipe_container.setRefreshing(false);

        //delete duplicate quiz titles/PIN from array - below is used to preserve insertion order
        Set<String> questionTopicSet = new LinkedHashSet<>(question.getQuestionTopicList());
        Set<String> questionTopicPINSet = new LinkedHashSet<>(question.getQuestionPinList());


        for (int i = 0; i < questionTopicSet.size(); i++)


            try {

                /*Dynamically create new Button which includes the names of each topic
                 */
                final Button btn_question_topic = new Button(view);

                LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1);
                btn_question_topic.setBackgroundColor(Color.parseColor("#FFFFFF"));
                // Sets the text for when the button is first created.
                btn_question_topic.setText("Topic: "+questionTopicSet.toArray()[i] + " | PIN: " + questionTopicPINSet.toArray()[i]);
                // Sets the text for when the button is not in the checked state.
                btn_question_topic.setTypeface(null, Typeface.BOLD);
                btn_question_topic.setTextColor(Color.WHITE);
                btn_question_topic.setBackground(view.getResources().getDrawable(R.drawable.custom_button));
                //set the Tag value of the team_member ID for database submission
                btn_question_topic.setTag(questionTopicPINSet.toArray()[i]);
                btn_question_topic.setId(-1);
                btn_question_topic.setGravity(Gravity.CENTER);
                params1.setMargins(100, dpToPx(10), 100, dpToPx(10));
                btn_question_topic.setPadding(0, 0, 0, 0);
                btn_question_topic.setLayoutParams(params1);

                view.addTopicButtonToLayout(btn_question_topic, topicLayour);


            } catch (Exception e) {
                Log.d(TAG, "Failed to Create Topic button because "+e.toString());
            }
    }
}
