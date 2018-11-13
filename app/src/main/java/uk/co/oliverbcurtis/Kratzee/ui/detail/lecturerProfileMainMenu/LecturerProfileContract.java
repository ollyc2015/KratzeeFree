package uk.co.oliverbcurtis.Kratzee.ui.detail.lecturerProfileMainMenu;

import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.widget.ProgressBar;

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
    }
}
