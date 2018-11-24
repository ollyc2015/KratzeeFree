package uk.co.oliverbcurtis.Kratzee.ui.detail.indiTriviaExisting;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.AppCompatButton;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import uk.co.oliverbcurtis.Kratzee.R;
import uk.co.oliverbcurtis.Kratzee.model.Constants;
import uk.co.oliverbcurtis.Kratzee.model.Individual;
import uk.co.oliverbcurtis.Kratzee.ui.common.BaseActivity;
import uk.co.oliverbcurtis.Kratzee.ui.common.RequestQuestionsExternalDB;

public class IndiTriviaExistView extends BaseActivity implements IndiTriviaExistContract.View, SwipeRefreshLayout.OnRefreshListener {

    private ProgressBar progress;
    private SwipeRefreshLayout swipe_container;
    private LinearLayout individualNameLayout;
    private IndiTriviaExistPresenter presenter;
    //private SharedPreferences pref;
    private EditText et_search_name;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.indi_registered_name_selection);
        initView();
    }


    @Override
    public void initView() {

        presenter = new IndiTriviaExistPresenter();
        presenter.attachView(this);

        progress = findViewById(R.id.progress);
        individualNameLayout = findViewById(R.id.individualNameLayout);

        swipe_container = findViewById(R.id.swipe_container);
        swipe_container.setOnRefreshListener(this);

        //pref = PreferenceManager.getDefaultSharedPreferences(this);

        et_search_name = findViewById(R.id.et_search_name);

        searchFilter();

        onRefresh();

        //If the user has decided to take the tutorial, start the first tutorial
        if(pref.getBoolean(Constants.DEMO_REQUEST_MADE,true)) {

            tutorialView.registeredIndividual1(this);
        }
    }



    @Override
    public void onRefresh() {
        individualNameLayout.removeAllViews();
        presenter.allIndividualNames(progress, pref);
        swipe_container.setRefreshing(false);
    }

    @Override
    public void callDynamicLayoutAdapter(Individual individual){

        DynamicIndiButton dynamicIndiButton = new DynamicIndiButton();
        dynamicIndiButton.createButton(this, individual);

    }

    @Override
    public void populateStudentNames(AppCompatButton btn_indiName, IndiTriviaExistView view){

        individualNameLayout.addView(btn_indiName);

        btn_indiName.setOnClickListener(v -> {
            Log.i("Clicked", "" + v.getTag());
            String selectedButtonText = (String) btn_indiName.getText();
            String studentPoints = (String) btn_indiName.getHint();
            String studentID = v.getTag().toString();
            showIndiSelectionDialog(studentID, selectedButtonText, studentPoints);
        });
    }

    @Override
    public void searchFilter(){

        et_search_name.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                // When user changed the Text
                for(int i = 0; i < individualNameLayout.getChildCount(); i++){

                    if(!((AppCompatButton)individualNameLayout.getChildAt(i)).getText().toString().toLowerCase().contains(cs.toString().toLowerCase())){

                        individualNameLayout.getChildAt(i).setVisibility(View.GONE);

                        Log.v("Buttons To Disappear", (String)((AppCompatButton)individualNameLayout.getChildAt(i)).getText());
                    }else{
                        individualNameLayout.getChildAt(i).setVisibility(View.VISIBLE);
                    }
                }
            }
            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) { }
            @Override
            public void afterTextChanged(Editable arg0) { }
        });
    }


    @Override
    public void showIndiSelectionDialog(String studentID, String selectedButtonText, String studentPoints){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Confirmation");
        builder.setMessage("You have Selected Student: \n\n" + selectedButtonText +"\n\nIs this Correct?");
        builder.setIcon(android.R.drawable.ic_dialog_alert);
        builder.setPositiveButton("Start Quiz!", (dialog, which) -> { });
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());
        AlertDialog dialog = builder.create();
        dialog.show();
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(v -> {

            dialog.dismiss();
            progress.setVisibility(View.VISIBLE);
            boolean submitted = presenter.storeSelectedInSQLiteDB(studentID, kratzeeDatabase, studentPoints);

            if (!submitted) {

                progress.setVisibility(View.INVISIBLE);
                BaseActivity.showToast(IndiTriviaExistView.this,"Unable to save to SQLite DB");

            } else {

                RequestQuestionsExternalDB requestQuestionsExternalDB = new RequestQuestionsExternalDB();
                requestQuestionsExternalDB.getQuestions(progress, IndiTriviaExistView.this, kratzeeDatabase);

            }

        });
    }
}
