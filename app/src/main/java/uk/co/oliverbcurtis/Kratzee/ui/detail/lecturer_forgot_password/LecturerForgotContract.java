package uk.co.oliverbcurtis.Kratzee.ui.detail.lecturer_forgot_password;

public interface LecturerForgotContract {

    interface View {

        void initView();
        void goToLogin();

    }

    interface Presenter {

        void attachView(LecturerForgotContract.View view);

    }
}
