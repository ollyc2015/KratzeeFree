package uk.co.oliverbcurtis.Kratzee.ui.detail.pinInput;


import android.widget.ProgressBar;

public interface QuizPinContract {

    //View defines the rules for the ListView_View Class - the below methods will need to be implemented in ListView_View
    interface View {
        //Method used to set some data

        void initView();
        void goToIndividualTrivia();
        void goToTeamTrivia();
    }

    //The below methods will be defined in the ListViewPresenter class
    interface Presenter {

        void attachView(QuizPinContract.View view);
        void pinProcess(String pin, ProgressBar progress);
        void handlePinSuccess();

    }
}
