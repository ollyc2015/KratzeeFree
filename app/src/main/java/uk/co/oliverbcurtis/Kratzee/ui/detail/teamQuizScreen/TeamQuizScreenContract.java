package uk.co.oliverbcurtis.Kratzee.ui.detail.teamQuizScreen;

import android.widget.ProgressBar;

import java.util.List;

import uk.co.oliverbcurtis.Kratzee.sqlite.KratzeeDatabase;
import uk.co.oliverbcurtis.Kratzee.ui.common.MainPagerAdapter;
import uk.co.oliverbcurtis.Kratzee.ui.common.SwipeDisabledViewPager;

public interface TeamQuizScreenContract {

    interface View {

        void initView();
        void goToPreviousScreen();
        void goToNextScreen();
        void submitPoints(ProgressBar progressBar);
        void showLeaderBoard();

    }


    interface Presenter {

        void selectQuestions(MainPagerAdapter pagerAdapter, SwipeDisabledViewPager pager, KratzeeDatabase kratzeeDatabase);
        void loadLayouts(SwipeDisabledViewPager pager, MainPagerAdapter pagerAdapter);
        void setQuestions(android.view.View group_layout, int questionNumber, int questionString);
        void submitButton(android.view.View group_layout, SwipeDisabledViewPager pager);
        void nextQuestionButton(android.view.View group_layout, SwipeDisabledViewPager pager);
        void previousQuestionButton(android.view.View group_layout, SwipeDisabledViewPager pager);
        ProgressBar getProgressBar();
        void pageIndicator(android.view.View group_layout, SwipeDisabledViewPager pager);
        void disableSwipe(SwipeDisabledViewPager pager, MainPagerAdapter pagerAdapter, android.view.View team_layout);
        android.view.View getCurrentPage(MainPagerAdapter pagerAdapter, SwipeDisabledViewPager pager);
        void attachView(TeamQuizScreenContract.View view);
        void removeScratchFunctionality(SwipeDisabledViewPager pager);
        List<String> returnQuestionArray();

    }

    interface ScratchThreshold{

        void team_scratch_pads(final android.view.View current_layout, List<String> array1, final SwipeDisabledViewPager pager, List<String> array3, int answerString, TeamQuizScreenContract.View view);
    }
}
