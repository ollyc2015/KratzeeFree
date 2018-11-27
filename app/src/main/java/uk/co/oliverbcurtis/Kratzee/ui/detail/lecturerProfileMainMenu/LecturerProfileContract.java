package uk.co.oliverbcurtis.Kratzee.ui.detail.lecturerProfileMainMenu;

import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.support.v7.widget.AppCompatButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import uk.co.oliverbcurtis.Kratzee.ui.detail.tutorialScreens.TutorialView;

public interface LecturerProfileContract {

    interface View {

        void initView();
        void logout();
        void goToStartMenu();
        void changePasswordDialog();
        void removeAllQuestionsDialog();
        void removeAllStudentDataDialog();
        void goToCreateQuestionSets();
        void goToEditQuestionSets();
    }


    interface Presenter{

        void attachView(LecturerProfileContract.View view);
        void changePasswordProcess(String email, String old_password, String new_password, ProgressBar progress, AlertDialog dialog);
        void removeAllQuestion(ProgressBar progress, SharedPreferences pref);
        void removeAllParticipants(ProgressBar progress, SharedPreferences pref);
        void getAllTopics(ProgressBar progress, SharedPreferences pref, TextView tv_question_sets_available, AppCompatButton btn_create_questions, TutorialView tutorialView);
    }
}
