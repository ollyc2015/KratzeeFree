package uk.co.oliverbcurtis.Kratzee.ui.detail.lecturerForgotPassword;

public interface LecturerForgotContract {

    interface View {

        void initView();
        void goToLogin();

    }

    interface Presenter {

        void attachView(LecturerForgotContract.View view);

    }
}
