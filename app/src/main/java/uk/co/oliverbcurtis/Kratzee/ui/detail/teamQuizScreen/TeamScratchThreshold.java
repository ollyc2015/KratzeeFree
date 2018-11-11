package uk.co.oliverbcurtis.Kratzee.ui.detail.teamQuizScreen;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;
import com.jackpocket.scratchoff.ScratchoffController;
import java.util.List;
import uk.co.oliverbcurtis.Kratzee.R;
import uk.co.oliverbcurtis.Kratzee.model.Score;
import uk.co.oliverbcurtis.Kratzee.ui.common.BaseActivity;
import uk.co.oliverbcurtis.Kratzee.ui.common.SwipeDisabledViewPager;

public class TeamScratchThreshold implements TeamQuizScreenContract.ScratchThreshold{

    private int attemptCount = 1;

    @Override
    public void team_scratch_pads(final View current_layout, List<String> array1, final SwipeDisabledViewPager pager, List<String> array3, int answerString, TeamQuizScreenContract.View view) {

        /*
        Get the total count of all the questions as the total number of questions will equal the final position in the
        viewpager - e.g if there are 10 questions, the length of the array will be 10 so the last question will be equal to 10
        we need this to move the scrollview to the bottom of the screen to show the submit button so the users know its there
        */
        int finalPage = array1.size();
        Log.v("current_count", "Answer count is at "+answerString);


        ScratchoffController controller1 = new ScratchoffController((Context) view)
                .setThresholdPercent(0.70d)
                .setTouchRadiusDip((Context) view, 10)
                .setFadeOnClear(true)
                .setClearOnThresholdReached(true)
                .setCompletionCallback(() -> {

                    /*
                  Below is relevant to the Team assessment, once the first attempt is counted before the student is taken to the next screen
                  If the star is revealed and the view is the last page, scroll to the bottom of the screen - attempt count is irrelevant
                     */

                    if (array3.get(answerString).contains("Correct") && pager.getCurrentItem() == finalPage - 1) {

                        current_layout.findViewById(R.id.star1).setVisibility(View.VISIBLE);

                        scrollToBottom(current_layout);
                        Score.increaseScoreByFour();
                        ((TextView) current_layout.findViewById(R.id.tv_current_score)).setText("" + Score.getScore());


                    } else if (!current_layout.findViewById(R.id.star1).isShown() && pager.getCurrentItem() == finalPage - 1 && attemptCount ==1) {

                        Score.decreaseScoreByTwo();
                        attemptCount = 2;


                    }else if (!current_layout.findViewById(R.id.star1).isShown() && pager.getCurrentItem() == finalPage-1 && attemptCount ==2) {

                        Score.decreaseScoreByOne();
                        attemptCount = 3;


                    }else if (!current_layout.findViewById(R.id.star1).isShown() && pager.getCurrentItem() == finalPage-1 && attemptCount ==3){

                        Score.decreaseScoreByOne();
                        attemptCount =1;


                    }else if (array3.get(answerString).contains("Correct")) {

                        current_layout.findViewById(R.id.star1).setVisibility(View.VISIBLE);
                        Score.increaseScoreByFour();
                        paginationDelay(pager);

                        ((TextView) current_layout.findViewById(R.id.tv_current_score)).setText("" + Score.getScore());
                        attemptCount = 1;

                    } else if (!current_layout.findViewById(R.id.star1).isShown() && attemptCount == 1) {

                        Score.decreaseScoreByTwo();
                        attemptCount = 2;

                    }else if (!current_layout.findViewById(R.id.star1).isShown() && attemptCount == 2){

                        Score.decreaseScoreByOne();
                        attemptCount = 3;

                    }else if (!current_layout.findViewById(R.id.star1).isShown() && attemptCount == 3){

                        Score.decreaseScoreByOne();
                        attemptCount = 1;
                    }

                })
                .attach(current_layout.findViewById(R.id.scratch_view1), current_layout.findViewById(R.id.scratch_view_behind1));



        ScratchoffController controller2 = new ScratchoffController((Context) view)
                .setThresholdPercent(0.70d)
                .setTouchRadiusDip((Context) view, 10)
                .setFadeOnClear(true)
                .setClearOnThresholdReached(true)
                .setCompletionCallback(() -> {

                    /*
                  Below is relevant to the Team test, once the first attempt is counted before the student is taken to the next screen
                  If the star is revealed and the view is the last page, scroll to the bottom of the screen - attempt count is irrelevant
                     */


                    if (array3.get(answerString+1).contains("Correct") && pager.getCurrentItem() == finalPage - 1) {

                        current_layout.findViewById(R.id.star2).setVisibility(View.VISIBLE);

                        scrollToBottom(current_layout);
                        Score.increaseScoreByFour();
                        ((TextView) current_layout.findViewById(R.id.tv_current_score)).setText("" + Score.getScore());


                    } else if (!current_layout.findViewById(R.id.star2).isShown() && pager.getCurrentItem() == finalPage - 1 && attemptCount ==1) {

                        Score.decreaseScoreByTwo();
                        attemptCount = 2;


                    }else if (!current_layout.findViewById(R.id.star2).isShown() && pager.getCurrentItem() == finalPage-1 && attemptCount ==2) {

                        Score.decreaseScoreByOne();
                        attemptCount = 3;


                    }else if (!current_layout.findViewById(R.id.star2).isShown() && pager.getCurrentItem() == finalPage-1 && attemptCount ==3){

                        Score.decreaseScoreByOne();
                        attemptCount =1;


                    }else if (array3.get(answerString+1).contains("Correct")) {

                        BaseActivity.showToast((Context) view, "Gets Here");

                        current_layout.findViewById(R.id.star2).setVisibility(View.VISIBLE);
                        Score.increaseScoreByFour();
                        paginationDelay(pager);

                        ((TextView) current_layout.findViewById(R.id.tv_current_score)).setText("" + Score.getScore());
                        attemptCount = 1;

                    } else if (!current_layout.findViewById(R.id.star2).isShown() && attemptCount == 1) {

                        Score.decreaseScoreByTwo();
                        attemptCount = 2;

                    }else if (!current_layout.findViewById(R.id.star2).isShown() && attemptCount == 2){

                        Score.decreaseScoreByOne();
                        attemptCount = 3;

                    }else if (!current_layout.findViewById(R.id.star2).isShown() && attemptCount == 3){

                        Score.decreaseScoreByOne();
                        attemptCount = 1;
                    }

                })
                .attach(current_layout.findViewById(R.id.scratch_view2), current_layout.findViewById(R.id.scratch_view_behind2));


        ScratchoffController controller3 = new ScratchoffController((Context) view)
                .setThresholdPercent(0.70d)
                .setTouchRadiusDip((Context) view, 10)
                .setFadeOnClear(true)
                .setClearOnThresholdReached(true)
                .setCompletionCallback(() -> {

                    /*
                  Below is relevant to the Team test, once the first attempt is counted before the student is taken to the next screen
                  If the star is revealed and the view is the last page, scroll to the bottom of the screen - attempt count is irrelevant
                     */

                    if (array3.get(answerString+2).contains("Correct") && pager.getCurrentItem() == finalPage - 1) {

                        current_layout.findViewById(R.id.star3).setVisibility(View.VISIBLE);

                        scrollToBottom(current_layout);
                        Score.increaseScoreByFour();
                        ((TextView) current_layout.findViewById(R.id.tv_current_score)).setText("" + Score.getScore());


                    } else if (!current_layout.findViewById(R.id.star3).isShown() && pager.getCurrentItem() == finalPage - 1 && attemptCount ==1) {

                        Score.decreaseScoreByTwo();
                        attemptCount = 2;


                    }else if (!current_layout.findViewById(R.id.star3).isShown() && pager.getCurrentItem() == finalPage-1 && attemptCount ==2) {

                        Score.decreaseScoreByOne();
                        attemptCount = 3;


                    }else if (!current_layout.findViewById(R.id.star3).isShown() && pager.getCurrentItem() == finalPage-1 && attemptCount ==3){

                        Score.decreaseScoreByOne();
                        attemptCount =1;


                    }else if (array3.get(answerString+2).contains("Correct")) {

                        current_layout.findViewById(R.id.star3).setVisibility(View.VISIBLE);
                        Score.increaseScoreByFour();
                        paginationDelay(pager);

                        ((TextView) current_layout.findViewById(R.id.tv_current_score)).setText("" + Score.getScore());
                        attemptCount = 1;

                    } else if (!current_layout.findViewById(R.id.star3).isShown() && attemptCount == 1) {

                        Score.decreaseScoreByTwo();
                        attemptCount = 2;

                    }else if (!current_layout.findViewById(R.id.star3).isShown() && attemptCount == 2){

                        Score.decreaseScoreByOne();
                        attemptCount = 3;

                    }else if (!current_layout.findViewById(R.id.star3).isShown() && attemptCount == 3){

                        Score.decreaseScoreByOne();
                        attemptCount = 1;
                    }

                })
                .attach(current_layout.findViewById(R.id.scratch_view3), current_layout.findViewById(R.id.scratch_view_behind3));


        ScratchoffController controller4 = new ScratchoffController((Context) view)
                .setThresholdPercent(0.70d)
                .setTouchRadiusDip((Context) view, 10)
                .setFadeOnClear(true)
                .setClearOnThresholdReached(true)
                .setCompletionCallback(() -> {

                    /*
                  Below is relevant to the Team test, once the first attempt is counted before the student is taken to the next screen
                  If the star is revealed and the view is the last page, scroll to the bottom of the screen - attempt count is irrelevant
                     */

                    if (array3.get(answerString+3).contains("Correct") && pager.getCurrentItem() == finalPage - 1) {

                        current_layout.findViewById(R.id.star4).setVisibility(View.VISIBLE);

                        scrollToBottom(current_layout);
                        Score.increaseScoreByFour();
                        ((TextView) current_layout.findViewById(R.id.tv_current_score)).setText("" + Score.getScore());


                    } else if (!current_layout.findViewById(R.id.star4).isShown() && pager.getCurrentItem() == finalPage - 1 && attemptCount ==1) {

                        Score.decreaseScoreByTwo();
                        attemptCount = 2;


                    }else if (!current_layout.findViewById(R.id.star4).isShown() && pager.getCurrentItem() == finalPage-1 && attemptCount ==2) {

                        Score.decreaseScoreByOne();
                        attemptCount = 3;


                    }else if (!current_layout.findViewById(R.id.star4).isShown() && pager.getCurrentItem() == finalPage-1 && attemptCount ==3){

                        Score.decreaseScoreByOne();
                        attemptCount =1;


                    }else if (array3.get(answerString+3).contains("Correct")) {

                        current_layout.findViewById(R.id.star4).setVisibility(View.VISIBLE);
                        Score.increaseScoreByFour();
                        paginationDelay(pager);

                        ((TextView) current_layout.findViewById(R.id.tv_current_score)).setText("" + Score.getScore());
                        attemptCount = 1;

                    } else if (!current_layout.findViewById(R.id.star4).isShown() && attemptCount == 1) {

                        Score.decreaseScoreByTwo();
                        attemptCount = 2;

                    }else if (!current_layout.findViewById(R.id.star4).isShown() && attemptCount == 2){

                        Score.decreaseScoreByOne();
                        attemptCount = 3;

                    }else if (!current_layout.findViewById(R.id.star4).isShown() && attemptCount == 3){

                        Score.decreaseScoreByOne();
                        attemptCount = 1;
                    }

                })
                .attach(current_layout.findViewById(R.id.scratch_view4), current_layout.findViewById(R.id.scratch_view_behind4));

    }

    private static void scrollToBottom(View current_layout){

        (current_layout.findViewById(R.id.myQuestionScrollView)).post(() -> ((ScrollView) current_layout.findViewById(R.id.myQuestionScrollView)).fullScroll(View.FOCUS_DOWN));
    }

    private static void paginationDelay(SwipeDisabledViewPager pager){

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //Move to the next page after 1200ms (1.2 seconds)
                pager.setCurrentItem(pager.getCurrentItem() + 1);

            }
        }, 1200);
    }
}
