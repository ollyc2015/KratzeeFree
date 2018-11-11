package uk.co.oliverbcurtis.Kratzee.ui.detail.lecturer_create_question_sets;

public interface QuestionSetContract {

    interface View{

        void initView();
    }

    interface Presenter{

        void attachView(QuestionSetContract.View view);
    }
}
