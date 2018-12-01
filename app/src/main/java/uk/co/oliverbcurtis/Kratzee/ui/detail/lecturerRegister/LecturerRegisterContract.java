package uk.co.oliverbcurtis.Kratzee.ui.detail.lecturerRegister;

import android.content.SharedPreferences;
import android.widget.ProgressBar;

import uk.co.oliverbcurtis.Kratzee.model.Lecturer;

public interface LecturerRegisterContract {

    interface View {

        void initView();
        void registerUser();
        void goToLecturerLobbyScreen(Lecturer lecturer);
    }

    interface Presenter{

        void attachView(LecturerRegisterContract.View view);
        void registerProcess(String name, String email, String password, ProgressBar progress, SharedPreferences pref);
    }
}
