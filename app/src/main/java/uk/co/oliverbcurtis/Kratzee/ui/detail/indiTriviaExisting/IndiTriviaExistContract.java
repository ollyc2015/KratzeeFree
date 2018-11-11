package uk.co.oliverbcurtis.Kratzee.ui.detail.indiTriviaExisting;

import android.content.SharedPreferences;
import android.support.v7.widget.AppCompatButton;
import android.widget.ProgressBar;

import uk.co.oliverbcurtis.Kratzee.model.Individual;
import uk.co.oliverbcurtis.Kratzee.sqlite.KratzeeDatabase;

public interface IndiTriviaExistContract {

    interface View {

        void initView();
        void callDynamicLayoutAdapter(Individual jsonIndiStudentNumbers);
        void populateStudentNames(AppCompatButton btn_indiName, IndiTriviaExistView view);
        void showIndiSelectionDialog(String studentID, String selectedButtonText, String studentPoints);
        void searchFilter();
    }


    interface Presenter {

        void allIndividualNames(ProgressBar progress, SharedPreferences pref);
        void attachView(IndiTriviaExistContract.View view);
        boolean storeSelectedInSQLiteDB(String studentID, KratzeeDatabase kratzeeDatabase, String studentPoints);
    }
}
