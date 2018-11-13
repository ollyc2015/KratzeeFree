package uk.co.oliverbcurtis.Kratzee.ui.detail.lecturerEditQuestionSets.selectedTopicQuestions;

import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ProgressBar;

import java.util.List;

import uk.co.oliverbcurtis.Kratzee.sqlite.KratzeeDatabase;

public interface SelectedTopicContract {

    interface View{

        void initView();
        void loadAllQuestions(ProgressBar progress);
        void populateLayout(Button btn_question_topic);
        void showQuestionSelectionDialog(String buttonText, String questionID);
        void showEditQuestionLayout(List<String> questionArray, List<String> question_array, List<String> answer_array, List<String> isAnswerCorrectArray, String questionID, ProgressBar progress);
        void getQuestions();
    }

    interface Presenter{

        void attachView(SelectedTopicContract.View view);
        void getAllQuestions(KratzeeDatabase kratzeeDatabase);
        void generateQuestionButton(List<String> questionList, List<String> answerList, List<String> markedCorrectList, List<String> array4);
        void getSelectQuestion(SharedPreferences pref, ProgressBar progress, String questionID, KratzeeDatabase kratzeeDatabase);
        void updateQuestionExternalDB(String questionString, List<String> answer_id_array, String answerString1, String answerString2, String answerString3, String answerString4, CheckBox cb_answer1_edit, CheckBox cb_answer2_edit, CheckBox cb_answer3_edit, CheckBox cb_answer4_edit, String questionID, ProgressBar progress, AlertDialog dialog);
        void submitEditedQuestions(List questionObjectArray, List answerObjectArray, ProgressBar progress, AlertDialog dialog);
        void submitEditedAnswers(List answerObjectArray, ProgressBar progress, AlertDialog dialog);
        void deleteQuestion(String questionID, ProgressBar progress, SharedPreferences pref);

    }
}
