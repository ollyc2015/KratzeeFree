package uk.co.oliverbcurtis.Kratzee.ui.detail.lecturerLoginMainMenu;

import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.widget.ProgressBar;
import android.widget.TextView;

public interface LecturerProfileContract {

    interface View {

        void initView();
        void logout();
        void goToStartMenu();
        void changePasswordDialog();
        void removeAllQuestionsDialog();
        void removeAllStudentDataDialog();
        void goToQuestionSets();
    }


    interface Presenter{

        void attachView(LecturerProfileContract.View view);
        void changePasswordProcess(String email, String old_password, String new_password, ProgressBar progress, TextView tv_message, AlertDialog dialog);
        void removeAllQuestion(ProgressBar progress, SharedPreferences pref);
    }
}
