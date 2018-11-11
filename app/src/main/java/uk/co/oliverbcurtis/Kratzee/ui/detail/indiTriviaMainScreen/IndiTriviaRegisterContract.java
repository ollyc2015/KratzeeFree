package uk.co.oliverbcurtis.Kratzee.ui.detail.indiTriviaMainScreen;


import uk.co.oliverbcurtis.Kratzee.sqlite.KratzeeDatabase;

public interface IndiTriviaRegisterContract {

    interface View {

        void initView();
        void showAlertDialog(String indi_name, String indi_student_number);
        void goToStudentNameSelection();

    }

    interface Presenter{

        void attachView(IndiTriviaRegisterContract.View view);
        boolean saveToSQLite(String indi_name, String indi_student_number, KratzeeDatabase kratzeeDatabase);
    }
}
