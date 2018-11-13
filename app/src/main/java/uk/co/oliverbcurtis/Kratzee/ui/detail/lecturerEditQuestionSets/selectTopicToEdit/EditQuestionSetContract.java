package uk.co.oliverbcurtis.Kratzee.ui.detail.lecturerEditQuestionSets.selectTopicToEdit;

import android.content.SharedPreferences;
import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

public interface EditQuestionSetContract {

    interface View{

        void initView();
        void addTopicButtonToLayout(Button btn_question_topic, LinearLayout topicLayour);
        void getQuestions();
        void showTopicSelectionDialog(String buttonText, String topicPin, String istopicActive);
    }

    interface Presenter{

        void attachView(EditQuestionSetContract.View view);
        void deleteTopic(String topicPin, ProgressBar progress, SharedPreferences pref, LinearLayout existingTopicSelectionLayout, SwipeRefreshLayout swipe_container);
        void updateTopicActiveState(String topicActive, String topicPin, SharedPreferences pref, ProgressBar progress);
    }
}
