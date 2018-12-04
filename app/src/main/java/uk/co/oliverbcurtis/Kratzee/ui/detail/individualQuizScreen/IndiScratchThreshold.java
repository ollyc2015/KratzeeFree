package uk.co.oliverbcurtis.Kratzee.ui.detail.individualQuizScreen;

import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.widget.ScrollView;
import com.jackpocket.scratchoff.ScratchoffController;
import java.util.List;
import uk.co.oliverbcurtis.Kratzee.R;
import uk.co.oliverbcurtis.Kratzee.model.Score;
import uk.co.oliverbcurtis.Kratzee.ui.common.BaseActivity;
import uk.co.oliverbcurtis.Kratzee.ui.common.SwipeDisabledViewPager;

public class IndiScratchThreshold extends IndiQuizScreenView implements IndiQuizScreenContract.ScratchThreshold  {

    private ScratchoffController controller1, controller2, controller3, controller4;


    @Override
    public void indi_scratch_pads(final View current_layout, List<String> array1, final SwipeDisabledViewPager pager, IndiQuizScreenContract.View view) {


        /*
        Get the total count of all the questions as the total number of questions will equal the final position in the
        viewpager - e.g if there are 10 questions, the length of the array will be 10 so the last question will be equal to 10
        we need this to move the scrollview to the bottom of the screen to show the submit button so the users know its there
        */
        int finalPage = array1.size();


         controller1 = new ScratchoffController((Context) view)
                .setThresholdPercent(0.70d)
                .setTouchRadiusDip((Context) view, 10)
                .setFadeOnClear(true)
                .setClearOnThresholdReached(true)
                .setCompletionCallback(() -> {

                    /*
                  Below is relevant to the individual test, once the first attempt is counted before the student is taken to the next screen
                  If the star is revealed and the view is the last page, scroll to the bottom of the screen - attempt count is irrelevant
                     */

                    if (current_layout.findViewById(R.id.star1).isShown() && pager.getCurrentItem() == finalPage - 1) {

                        Score.increaseScoreByOne();
                        scrollToBottom(current_layout);
                        current_layout.findViewById(R.id.scratch_text1).setVisibility(View.VISIBLE);

                        BaseActivity.showToast((Context) view, "Please Stop Scratching (Only 1 Answer Needed), Scroll Down and Submit.");


                    } else if (!current_layout.findViewById(R.id.star1).isShown() && pager.getCurrentItem() == finalPage - 1)  {

                        scrollToBottom(current_layout);
                        Score.noChangeToScore();
                        current_layout.findViewById(R.id.scratch_text1).setVisibility(View.VISIBLE);

                        BaseActivity.showToast((Context) view, "Please Stop Scratching (Only 1 Answer Needed), Scroll Down and Submit.");

                    }else if (current_layout.findViewById(R.id.star1).isShown()) {

                        Score.increaseScoreByOne();
                        paginationDelay(pager);
                        current_layout.findViewById(R.id.scratch_text1).setVisibility(View.VISIBLE);


                    } else {

                        paginationDelay(pager);
                        Score.noChangeToScore();
                        current_layout.findViewById(R.id.scratch_text1).setVisibility(View.VISIBLE);
                    }

                })
                .attach(current_layout.findViewById(R.id.scratch_view1), current_layout.findViewById(R.id.scratch_view_behind1));




         controller2 = new ScratchoffController((Context) view)
                .setThresholdPercent(0.70d)
                .setTouchRadiusDip((Context) view, 10)
                .setFadeOnClear(true)
                .setClearOnThresholdReached(true)
                .setCompletionCallback(() -> {

                    if (current_layout.findViewById(R.id.star2).isShown() && pager.getCurrentItem() == finalPage - 1) {

                        Score.increaseScoreByOne();
                        scrollToBottom(current_layout);
                        current_layout.findViewById(R.id.scratch_text2).setVisibility(View.VISIBLE);

                        BaseActivity.showToast((Context) view, "Please Stop Scratching (Only 1 Answer Needed), Scroll Down and Submit.");


                    } else if (!current_layout.findViewById(R.id.star2).isShown() && pager.getCurrentItem() == finalPage - 1)  {

                        scrollToBottom(current_layout);
                        Score.noChangeToScore();
                        current_layout.findViewById(R.id.scratch_text2).setVisibility(View.VISIBLE);

                        BaseActivity.showToast((Context) view, "Please Stop Scratching (Only 1 Answer Needed), Scroll Down and Submit.");


                    }else if (current_layout.findViewById(R.id.star2).isShown()) {

                        Score.increaseScoreByOne();
                        paginationDelay(pager);
                        current_layout.findViewById(R.id.scratch_text2).setVisibility(View.VISIBLE);


                    } else {

                        paginationDelay(pager);
                        Score.noChangeToScore();
                        current_layout.findViewById(R.id.scratch_text2).setVisibility(View.VISIBLE);

                    }

                })
                .attach(current_layout.findViewById(R.id.scratch_view2), current_layout.findViewById(R.id.scratch_view_behind2));


         controller3 = new ScratchoffController((Context) view)
                .setThresholdPercent(0.70d)
                .setTouchRadiusDip((Context) view, 10)
                .setFadeOnClear(true)
                .setClearOnThresholdReached(true)
                .setCompletionCallback(() -> {

                    if (current_layout.findViewById(R.id.star3).isShown() && pager.getCurrentItem() == finalPage - 1) {

                        Score.increaseScoreByOne();
                        scrollToBottom(current_layout);
                        current_layout.findViewById(R.id.scratch_text3).setVisibility(View.VISIBLE);

                        BaseActivity.showToast((Context) view, "Please Stop Scratching (Only 1 Answer Needed), Scroll Down and Submit.");


                    } else if (!current_layout.findViewById(R.id.star3).isShown() && pager.getCurrentItem() == finalPage - 1)  {

                        scrollToBottom(current_layout);
                        Score.noChangeToScore();
                        current_layout.findViewById(R.id.scratch_text3).setVisibility(View.VISIBLE);

                        BaseActivity.showToast((Context) view, "Please Stop Scratching (Only 1 Answer Needed), Scroll Down and Submit.");


                    }else if (current_layout.findViewById(R.id.star3).isShown()) {


                        Score.increaseScoreByOne();
                        paginationDelay(pager);
                        current_layout.findViewById(R.id.scratch_text3).setVisibility(View.VISIBLE);


                    } else {

                        paginationDelay(pager);
                        Score.noChangeToScore();
                        current_layout.findViewById(R.id.scratch_text3).setVisibility(View.VISIBLE);

                    }

                })
                .attach(current_layout.findViewById(R.id.scratch_view3), current_layout.findViewById(R.id.scratch_view_behind3));


         controller4 = new ScratchoffController((Context) view)
                .setThresholdPercent(0.70d)
                .setTouchRadiusDip((Context) view, 10)
                .setFadeOnClear(true)
                .setClearOnThresholdReached(true)
                .setCompletionCallback(() -> {


                    if (current_layout.findViewById(R.id.star4).isShown() && pager.getCurrentItem() == finalPage - 1) {

                        Score.increaseScoreByOne();
                        scrollToBottom(current_layout);
                        current_layout.findViewById(R.id.scratch_text4).setVisibility(View.VISIBLE);

                        BaseActivity.showToast((Context) view, "Please Stop Scratching (Only 1 Answer Needed), Scroll Down and Submit.");


                    } else if (!current_layout.findViewById(R.id.star4).isShown() && pager.getCurrentItem() == finalPage - 1)  {

                        scrollToBottom(current_layout);
                        Score.noChangeToScore();
                        current_layout.findViewById(R.id.scratch_text4).setVisibility(View.VISIBLE);

                        BaseActivity.showToast((Context) view, "Please Stop Scratching (Only 1 Answer Needed), Scroll Down and Submit.");


                    }else if (current_layout.findViewById(R.id.star4).isShown()) {

                        Score.increaseScoreByOne();
                        paginationDelay(pager);
                        current_layout.findViewById(R.id.scratch_text4).setVisibility(View.VISIBLE);


                    } else {

                        paginationDelay(pager);
                        Score.noChangeToScore();
                        current_layout.findViewById(R.id.scratch_text4).setVisibility(View.VISIBLE);

                    }

                })
                .attach(current_layout.findViewById(R.id.scratch_view4), current_layout.findViewById(R.id.scratch_view_behind4));

    }

    public void scrollToBottom(View current_layout){

        ((ScrollView) current_layout.findViewById(R.id.myQuestionScrollView)).post(new Runnable() {
            @Override
            public void run() {
                ((ScrollView) current_layout.findViewById(R.id.myQuestionScrollView)).fullScroll(View.FOCUS_DOWN);
            }
        });
    }

    public void paginationDelay(SwipeDisabledViewPager pager){

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //Move to the next page after 1200ms (1.2 seconds)
                pager.setCurrentItem(pager.getCurrentItem() + 1);

            }
        }, 1200);
    }

    /*
    private void registeredAnswerSound() {
        MediaPlayer mp = MediaPlayer.create(this,
                R.raw.correct_answer);
        mp.start();
        mp.setOnCompletionListener(mp1 -> mp1.release());
    }
    */

    @Override
    public void onPause(){
        controller1.onPause();
        controller2.onPause();
        controller3.onPause();
        controller4.onPause();
        super.onPause();
    }

    @Override
    public void onResume(){
        super.onResume();
        controller1.onResume();
        controller2.onResume();
        controller3.onResume();
        controller4.onResume();
    }

    @Override
    public void onDestroy(){
        controller1.onDestroy();
        controller2.onDestroy();
        controller3.onDestroy();
        controller4.onDestroy();
        super.onDestroy();
    }
}
