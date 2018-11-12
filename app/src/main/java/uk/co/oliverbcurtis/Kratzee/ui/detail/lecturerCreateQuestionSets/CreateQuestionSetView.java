package uk.co.oliverbcurtis.Kratzee.ui.detail.lecturerCreateQuestionSets;

import android.os.Bundle;
import android.support.annotation.Nullable;

import uk.co.oliverbcurtis.Kratzee.R;
import uk.co.oliverbcurtis.Kratzee.ui.common.BaseActivity;

public class CreateQuestionSetView extends BaseActivity implements CreateQuestionSetContract.View {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lecturer_create_question_set);
        initView();
    }

    @Override
    public void initView() {

    }
}
