package uk.co.oliverbcurtis.Kratzee.ui.detail.lecturer_create_question_sets;

import android.os.Bundle;
import android.support.annotation.Nullable;

import uk.co.oliverbcurtis.Kratzee.R;
import uk.co.oliverbcurtis.Kratzee.ui.common.BaseActivity;

public class QuestionSetView extends BaseActivity implements QuestionSetContract.View {

    private QuestionSetPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lecturer_question_set_selection);
        initView();
    }


    @Override
    public void initView() {

        presenter = new QuestionSetPresenter();
        presenter.attachView(this);

    }
}
