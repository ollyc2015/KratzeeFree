package uk.co.oliverbcurtis.Kratzee.ui.detail.individualQuizScreen;


import android.content.SharedPreferences;
import android.widget.ProgressBar;

import java.util.List;

import uk.co.oliverbcurtis.Kratzee.sqlite.KratzeeDatabase;
import uk.co.oliverbcurtis.Kratzee.ui.common.MainPagerAdapter;
import uk.co.oliverbcurtis.Kratzee.ui.common.SwipeDisabledViewPager;

public interface IndiQuizScreenContract {

        interface View  {

            void initView();
            void goToPreviousScreen();
            void goToNextScreen();
            void submitPoints(ProgressBar progressBar);
            void showDemo(android.view.View individual_layout);
            void backToMainMenu();
        }

        interface Presenter{

            void selectQuestions(MainPagerAdapter pagerAdapter, SwipeDisabledViewPager pager, KratzeeDatabase kratzeeDatabase, SharedPreferences pref);
            void attachView(IndiQuizScreenContract.View view);
            void loadLayouts(final SwipeDisabledViewPager pager, MainPagerAdapter pagerAdapter, SharedPreferences pref);
            void setQuestions(android.view.View group_layout, int questionNumber, int questionString);
            void submitButton(android.view.View group_layout, SwipeDisabledViewPager pager);
            void nextQuestionButton(android.view.View individual_layout, SwipeDisabledViewPager pager);
            void previousQuestionButton(android.view.View individual_layout, SwipeDisabledViewPager pager);
            ProgressBar getProgressBar();
            void pageIndicator(android.view.View group_layout, SwipeDisabledViewPager pager);
            void disableSwipe(SwipeDisabledViewPager pager);
            void removeScratchFunctionality(SwipeDisabledViewPager pager);
            android.view.View getCurrentPage(MainPagerAdapter pagerAdapter, SwipeDisabledViewPager pager);
        }


        interface ScratchThreshold{

            void indi_scratch_pads(final android.view.View current_layout, List<String> array1, final SwipeDisabledViewPager pager, IndiQuizScreenContract.View view);
        }
}
