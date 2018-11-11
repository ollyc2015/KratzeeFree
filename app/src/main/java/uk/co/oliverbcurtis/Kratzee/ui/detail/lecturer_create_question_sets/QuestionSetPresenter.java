package uk.co.oliverbcurtis.Kratzee.ui.detail.lecturer_create_question_sets;

public class QuestionSetPresenter implements QuestionSetContract.Presenter {

    QuestionSetContract.View view;

    @Override
    public void attachView(QuestionSetContract.View view) {

        this.view = view;
    }
}
