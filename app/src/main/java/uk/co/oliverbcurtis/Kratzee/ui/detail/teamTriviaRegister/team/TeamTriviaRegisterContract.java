package uk.co.oliverbcurtis.Kratzee.ui.detail.teamTriviaRegister.team;


import android.content.res.Resources;

import uk.co.oliverbcurtis.Kratzee.sqlite.KratzeeDatabase;

public interface TeamTriviaRegisterContract {

    interface View {

        void initView();

    }


    interface Presenter {

        void attachView(TeamTriviaRegisterContract.View view);
        boolean teamSubmission(String teamName, TeamTriviaRegisterView context, KratzeeDatabase kratzeeDatabase);
    }
}
