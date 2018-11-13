package uk.co.oliverbcurtis.Kratzee.ui.detail.lecturerEditQuestionSets.selectTopicToEdit;

import android.widget.Button;
import android.widget.LinearLayout;

public interface EditQuestionSetContract {

    interface View{

        void initView();
        void addTopicButtonToLayout(Button btn_question_topic, LinearLayout topicLayour);
        void getQuestions();
        void showTopicSelectionDialog(String buttonText, String topicPin);
    }

    interface Presenter{

        void attachView(EditQuestionSetContract.View view);

    }
}
