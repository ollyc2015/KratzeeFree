package uk.co.oliverbcurtis.Kratzee.ui.detail.tutorialScreens;

import android.graphics.Point;
import android.support.v7.widget.Toolbar;

import com.github.amlcurran.showcaseview.ShowcaseView;
import com.github.amlcurran.showcaseview.targets.Target;
import com.github.amlcurran.showcaseview.targets.ViewTarget;
import uk.co.oliverbcurtis.Kratzee.R;
import uk.co.oliverbcurtis.Kratzee.ui.common.BaseActivity;
import uk.co.oliverbcurtis.Kratzee.ui.detail.quizType.QuizTypeView;
import uk.co.oliverbcurtis.Kratzee.ui.detail.startScreen.StartScreenView;

public class TutorialView extends BaseActivity implements TutorialContract.View {

    private ShowcaseView showcaseView1, showcaseView2, showcaseView3;

    /****************************************BELOW IS THE TUTORIAL FOR THE START SCREEN*****************************/

    @Override
    public void startScreenTutorial1(StartScreenView view, Toolbar toolbar){

        Target homeTarget = () -> {
            // Get approximate position of home icon's center
            int actionBarSize = toolbar.getHeight();
            int x = actionBarSize * 7;
            int y = actionBarSize / 2;
            return new Point(x, y);
        };

        showcaseView1 = new ShowcaseView.Builder(view)
                .setTarget(homeTarget)
                .setContentTitle("Welcome To Kratzee!")
                .setContentText("What a Tutorial Refresher or Give Some Useful Feedback? Click Here")
                .setStyle(R.style.CustomShowcaseTheme2)
                .setOnClickListener(view)
                .build();
        showcaseView1.setButtonText("OK!");


    }

    @Override
    public void startScreenTutorial2(StartScreenView view){

        showcaseView1.hide();

         showcaseView2 = new ShowcaseView.Builder(view)
                .setTarget(new ViewTarget(view.findViewById(R.id.btn_student)))
                .setContentTitle("Welcome To Kratzee!")
                .setContentText("Click Here To Take a Quiz, Please note that you Will Need a Quiz PIN Given to You by The Person That Created the Quiz")
                .setStyle(R.style.CustomShowcaseTheme2)
                .setOnClickListener(view)
                .build();
        showcaseView2.forceTextPosition(ShowcaseView.ABOVE_SHOWCASE);
        showcaseView2.setButtonText("OK!");

        StartScreenView.tutorial_counter++;

    }


    @Override
    public void startScreenTutorial3(StartScreenView view) {

        showcaseView2.hide();

         showcaseView3 = new ShowcaseView.Builder(view)
                .setTarget(new ViewTarget(view.findViewById(R.id.btn_lecturer)))
                .setContentTitle("Welcome To Kratzee!")
                .setContentText("Click Here To Create an Account and Make Your Own Question Sets! This is Where You'll Generate Your Quiz PIN's!")
                .setStyle(R.style.CustomShowcaseTheme2)
                .setOnClickListener(view)
                .build();
        showcaseView3.setButtonText("OK!");

        StartScreenView.tutorial_counter++;
    }


    @Override
    public void closeStartScreenTutorial(){

        showcaseView3.hide();

    }

   /***********************************BELOW IS THE TUTORIAL FOR SECOND SCREEN (QUIZ TYPE) ************************************************/



    public void quizTypeTutorial1(QuizTypeView view){


        showcaseView1 = new ShowcaseView.Builder(view)
                .setTarget(new ViewTarget(view.findViewById(R.id.btn_indi)))
                .setContentTitle("Welcome To Kratzee!")
                .setContentText("Click Here to Take an Independent Quiz. The Idea is to take this by yourself with no help, this will test your own understanding, before taking the same quiz in a team. More will be explained later...")
                .setStyle(R.style.CustomShowcaseTheme2)
                .setOnClickListener(view)
                .build();
        showcaseView1.forceTextPosition(ShowcaseView.ABOVE_SHOWCASE);
        showcaseView1.setButtonText("OK!");

    }

    public void quizTypeTutorial2(QuizTypeView view){

        showcaseView1.hide();

        showcaseView3 = new ShowcaseView.Builder(view)
                .setTarget(new ViewTarget(view.findViewById(R.id.btn_team)))
                .setContentTitle("Welcome To Kratzee!")
                .setContentText("Once you have completed the individual quiz, this button will become available. You will then take the same quiz as a Team, the objective will be explained later...")
                .setStyle(R.style.CustomShowcaseTheme2)
                .setOnClickListener(view)
                .build();
        showcaseView3.forceTextPosition(ShowcaseView.ABOVE_SHOWCASE);
        showcaseView3.setButtonText("OK!");

        StartScreenView.tutorial_counter++;

    }

    public void closeQuizTypeTutorial(){

        showcaseView3.hide();

    }
}
