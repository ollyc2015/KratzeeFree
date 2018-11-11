package uk.co.oliverbcurtis.Kratzee.ui.detail.lecturerLogin;

import uk.co.oliverbcurtis.Kratzee.model.Lecturer;

public interface LecturerLoginContract {

    interface View {

        void initView();
        void goToLecturerLobbyScreen(Lecturer lecturer);
        void goToResetPasswordScreen();
        void goToRegisterScreen();
        void checkLoginStatus();
    }


    interface Presenter{

        void attachView(LecturerLoginContract.View view);
    }
}
