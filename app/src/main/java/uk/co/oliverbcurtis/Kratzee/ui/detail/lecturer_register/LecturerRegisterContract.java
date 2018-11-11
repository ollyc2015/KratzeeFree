package uk.co.oliverbcurtis.Kratzee.ui.detail.lecturer_register;

import android.widget.ProgressBar;

public interface LecturerRegisterContract {

    interface View {

        void initView();
        void registerUser();
        void gotToLogin();
    }

    interface Presenter{

        void attachView(LecturerRegisterContract.View view);
        void registerProcess(String name, String email, String password, ProgressBar progress);
    }
}
