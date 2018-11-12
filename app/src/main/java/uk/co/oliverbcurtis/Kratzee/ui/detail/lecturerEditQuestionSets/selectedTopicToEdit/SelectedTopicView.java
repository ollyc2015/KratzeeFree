package uk.co.oliverbcurtis.Kratzee.ui.detail.lecturerEditQuestionSets.selectedTopicToEdit;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Button;
import android.widget.LinearLayout;

import uk.co.oliverbcurtis.Kratzee.R;
import uk.co.oliverbcurtis.Kratzee.ui.common.BaseActivity;

public class SelectedTopicView extends BaseActivity implements SelectedTopicContract.View {

    private LinearLayout selectedTopicSelectionLayout;
    private SelectedTopicPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lecturer_selected_topic_to_edit);
        initView();
    }

    @Override
    public void initView() {

        selectedTopicSelectionLayout = findViewById(R.id.selectedTopicSelectionLayout);
        selectedTopicSelectionLayout.removeAllViews();

        presenter = new SelectedTopicPresenter();
        presenter.attachView(this);

        loadAllQuestions();
    }

    @Override
    public void loadAllQuestions(){

        presenter.getAllQuestions(kratzeeDatabase);

    }


    @Override
    public void populateLayout(Button btn_question_topic){

        selectedTopicSelectionLayout.addView(btn_question_topic);

    }
}
