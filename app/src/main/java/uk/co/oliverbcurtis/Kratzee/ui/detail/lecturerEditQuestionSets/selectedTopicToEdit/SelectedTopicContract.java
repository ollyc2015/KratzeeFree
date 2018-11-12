package uk.co.oliverbcurtis.Kratzee.ui.detail.lecturerEditQuestionSets.selectedTopicToEdit;

import android.widget.Button;

import java.util.List;

import uk.co.oliverbcurtis.Kratzee.sqlite.KratzeeDatabase;

public interface SelectedTopicContract {

    interface View{

        void initView();
        void loadAllQuestions();
        void populateLayout(Button btn_question_topic);
    }

    interface Presenter{

        void attachView(SelectedTopicContract.View view);
        void getAllQuestions(KratzeeDatabase kratzeeDatabase);
        void generateQuestionButton(List<String> questionList, List<String> answerList, List<String> markedCorrectList, List<String> array4);


    }
}
