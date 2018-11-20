package uk.co.oliverbcurtis.Kratzee.ui.detail.lecturerCreateQuestionSets;

import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ProgressBar;

import java.util.List;

import uk.co.oliverbcurtis.Kratzee.sqlite.KratzeeDatabase;

public interface CreateQuestionSetContract {

    interface View{

        void initView();
        void inflateQuestionLayout(String topic);
        void populateLayout(Button btn_new_question);
        void showSubmitAllQuestionsDialog();
        void questionsSubmitSuccessful();
        void showQuestionSelectionDialog(String buttonText, String questionID);
        void showEditQuestionLayout(List<String> question_array, List<String> answer_id_array, List<String> answer_array, List<String> isAnswerCorrectArray, String questionID, ProgressBar progress);
    }

    interface Presenter{

        void attachView(CreateQuestionSetContract.View view);
        void addQuestionToSQLite(String question, String answer1, String answer2, String answer3, String answer4, CheckBox cb_answer1_edit, CheckBox cb_answer2_edit, CheckBox cb_answer3_edit, CheckBox cb_answer4_edit, ProgressBar progress, AlertDialog dialog, KratzeeDatabase kratzeeDatabase, String topic, SharedPreferences pref, CreateQuestionSetView view);
        void addAnswersToSQLite(String questionId, String answer1, String answer2, String answer3, String answer4, CheckBox cb_answer1_edit, CheckBox cb_answer2_edit, CheckBox cb_answer3_edit, CheckBox cb_answer4_edit, KratzeeDatabase kratzeeDatabase, ProgressBar progress, SharedPreferences pref, CreateQuestionSetView view, AlertDialog dialog);
        void getQuestionsFromSQLite(KratzeeDatabase kratzeeDatabase, SharedPreferences pref, ProgressBar progress);
        void getAnswersFromSQLite(List<String> questionStringArray, List<String> questionTopicStringArray, KratzeeDatabase kratzeeDatabase, SharedPreferences pref, ProgressBar progress);
        void submitQuestions(List<String> questionStringArray, List<String> questionTopicStringArray, List<String> answerStringArray, List<String> correctArray, SharedPreferences pref, ProgressBar progress);
        boolean deleteQuestionAnswersFromSQLiteDB(String questionID, KratzeeDatabase kratzeeDatabase);
        boolean deleteQuestionFromSQLiteDB(String questionID, KratzeeDatabase kratzeeDatabase);
        void getSelectedQuestionFromSQLiteDB(SharedPreferences pref, ProgressBar progress, String questionID, KratzeeDatabase kratzeeDatabase);
        void updateQuestionSQLiteDB(String questionString, List<String> answer_id_array, String answerString1, String answerString2, String answerString3, String answerString4, CheckBox cb_answer1_edit, CheckBox cb_answer2_edit, CheckBox cb_answer3_edit, CheckBox cb_answer4_edit, String questionID, ProgressBar progress, AlertDialog dialog, KratzeeDatabase kratzeeDatabase);
    }
}
