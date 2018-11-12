package uk.co.oliverbcurtis.Kratzee.ui.detail.lecturerCreateQuestionSets;

public interface CreateQuestionSetContract {

    interface View{

        void initView();
    }

    interface Presenter{

        void attachView(CreateQuestionSetContract.View view);
    }
}
