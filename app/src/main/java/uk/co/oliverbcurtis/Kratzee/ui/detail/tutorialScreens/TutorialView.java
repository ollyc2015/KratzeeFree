package uk.co.oliverbcurtis.Kratzee.ui.detail.tutorialScreens;

import android.app.Activity;
import android.graphics.Point;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.github.amlcurran.showcaseview.ShowcaseView;
import com.github.amlcurran.showcaseview.targets.Target;
import com.github.amlcurran.showcaseview.targets.ViewTarget;
import uk.co.oliverbcurtis.Kratzee.R;
import uk.co.oliverbcurtis.Kratzee.ui.common.BaseActivity;
import uk.co.oliverbcurtis.Kratzee.ui.detail.indiTriviaMainScreen.IndiTriviaRegisterView;
import uk.co.oliverbcurtis.Kratzee.ui.detail.individualQuizScreen.IndiQuizScreenView;
import uk.co.oliverbcurtis.Kratzee.ui.detail.pinInput.QuizPinView;
import uk.co.oliverbcurtis.Kratzee.ui.detail.quizType.QuizTypeView;
import uk.co.oliverbcurtis.Kratzee.ui.detail.startScreen.StartScreenView;

public class TutorialView extends BaseActivity implements TutorialContract.View {

    private ShowcaseView showcaseView1, showcaseView2, showcaseView3, showcaseView4;

    /****************************************BELOW IS THE TUTORIAL FOR THE START SCREEN*****************************/

    @Override
    public void startScreenTutorial1(StartScreenView view, Toolbar toolbar){

        Target homeTarget = () -> {
            // Get approximate position of settings button on the starting screen app bar
            int actionBarSize = toolbar.getHeight();
            int x = actionBarSize * 7;
            int y = actionBarSize / 2;
            return new Point(x, y);
        };

        showcaseView1 = new ShowcaseView.Builder(view)
                .setTarget(homeTarget)
                .setContentTitle("Welcome To Kratzee!")
                .setContentText("Want a Tutorial Refresher or Fancy Giving Some Useful Feedback? Click On The Highlighted Button and Choose From the Available Options.\n\nPlease Click 'Ok' At the Bottom of the Page To Continue the Tutorial.")
                .setStyle(R.style.CustomShowcaseTheme2)
                .setOnClickListener(view)
                .blockAllTouches()
                .build();
        showcaseView1.setButtonText("OK!");


    }

    @Override
    public void startScreenTutorial2(StartScreenView view){

        showcaseView1.hide();

         showcaseView2 = new ShowcaseView.Builder(view)
                .setTarget(new ViewTarget(view.findViewById(R.id.btn_student)))
                .setContentTitle("Welcome To Kratzee!")
                .setContentText("To Take a Quiz Click The Button Highlighted, Please Note that you Will Need a Quiz PIN Given to You by The Person That Created the Quiz.")
                .setStyle(R.style.CustomShowcaseTheme2)
                 .blockAllTouches()
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
                .setContentText("To Create an Account and Make Your Own Question Sets, Click The Highlighted Button! This is Where You'll Generate Your Quiz PIN's! ")
                .setStyle(R.style.CustomShowcaseTheme2)
                 .blockAllTouches()
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
                .setContentText("To Take an Independent Quiz, Click The Highlighted Button. The Idea is to take this by yourself with no help, this will test your own understanding, before taking the same quiz in a team. More will be explained later...")
                .setStyle(R.style.CustomShowcaseTheme2)
                .setOnClickListener(view)
                .blockAllTouches()
                .build();
        showcaseView1.forceTextPosition(ShowcaseView.ABOVE_SHOWCASE);
        showcaseView1.setButtonText("OK!");

    }

    public void quizTypeTutorial2(QuizTypeView view){

        showcaseView1.hide();

        showcaseView2 = new ShowcaseView.Builder(view)
                .setTarget(new ViewTarget(view.findViewById(R.id.btn_team)))
                .setContentTitle("Welcome To Kratzee!")
                .setContentText("Once you have Completed the Individual Quiz, The Highlighted Button will become Available. You will then Take the Same Quiz as a Team, the Objective Will be Explained Later...")
                .setStyle(R.style.CustomShowcaseTheme2)
                .blockAllTouches()
                .setOnClickListener(view)
                .build();
        showcaseView2.forceTextPosition(ShowcaseView.ABOVE_SHOWCASE);
        showcaseView2.setButtonText("OK!");

        StartScreenView.tutorial_counter++;

    }

    public void closeQuizTypeTutorial(){

        showcaseView2.hide();

    }

    /*************************BELOW IS THE TUTORIAL FOR THE QUESTION PIN SCREEN*******************************/


    public void pinInputTutorial1(QuizPinView view){


        showcaseView1 = new ShowcaseView.Builder(view)
                .setTarget(new ViewTarget(view.findViewById(R.id.et_pin)))
                .setContentTitle("Welcome To Kratzee!")
                .setContentText("Enter Your Quiz PIN in the Highlighted Area. This will be Given to You by the Person That has Created the Quiz.")
                .setStyle(R.style.CustomShowcaseTheme2)
                .setOnClickListener(view)
                .blockAllTouches()
                .build();
        showcaseView1.forceTextPosition(ShowcaseView.ABOVE_SHOWCASE);
        showcaseView1.setButtonText("OK!");

    }

    public void closeQuizPinTutorial(){

        showcaseView1.hide();

    }


    /*************************BELOW IS THE TUTORIAL FOR THOSE TAKING AN INDIVIDUAL QUIZ*******************************/


    public void indiRegisterTutorial1(IndiTriviaRegisterView view){

        showcaseView1 = new ShowcaseView.Builder(view)
                .setTarget(new ViewTarget(view.findViewById(R.id.et_indi_name)))
                .setContentTitle("First Time?")
                .setContentText("If this is your First time Taking a Quiz by The Person who Gave You Their PIN, Register Your Name In the Highlighted Area.")
                .setStyle(R.style.CustomShowcaseTheme2)
                .setOnClickListener(view)
                .blockAllTouches()
                .build();
        showcaseView1.forceTextPosition(ShowcaseView.ABOVE_SHOWCASE);
        showcaseView1.setButtonText("OK!");

    }

    public void indiRegisterTutorial2(IndiTriviaRegisterView view){

        showcaseView1.hide();

        showcaseView2 = new ShowcaseView.Builder(view)
                .setTarget(new ViewTarget(view.findViewById(R.id.et_indi_student_number)))
                .setContentTitle("First Time?")
                .setContentText("Here You Can Enter Your Student ID or Create a Random 7 Figure Unique-ID (and Remember it) - This will Help You To Identify Yourself in the Event You Take Future Quizzes set by The Same Person")
                .setStyle(R.style.CustomShowcaseTheme2)
                .setOnClickListener(view)
                .blockAllTouches()
                .build();
        showcaseView2.forceTextPosition(ShowcaseView.ABOVE_SHOWCASE);
        showcaseView2.setButtonText("OK!");

        StartScreenView.tutorial_counter++;

    }

    public void indiRegisterTutorial3(IndiTriviaRegisterView view){

        showcaseView2.hide();

        showcaseView3 = new ShowcaseView.Builder(view)
                .setTarget(new ViewTarget(view.findViewById(R.id.tv_studentSelection)))
                .setContentTitle("Already Registered?")
                .setContentText("If you have Previously Taken a Quiz by The Person who Gave You Their PIN and Registered Your Name, Click on The Highlighted Button and Search For Your Name or Student Number in The Filterable List")
                .setStyle(R.style.CustomShowcaseTheme2)
                .setOnClickListener(view)
                .blockAllTouches()
                .build();
        showcaseView3.forceTextPosition(ShowcaseView.ABOVE_SHOWCASE);
        showcaseView3.setButtonText("OK!");

        StartScreenView.tutorial_counter++;

    }


    public void closeIndiRegisterTutorial(){

        showcaseView3.hide();

    }


    /*************************BELOW IS THE TUTORIAL FOR THE INDIVIDUAL QUIZ SCREEN
     * @param view
     ********************************/

    public void indiQuizScreenTutorial1(Activity view){

        showcaseView1 = new ShowcaseView.Builder(view)
                .setTarget(new ViewTarget(view.findViewById(R.id.tv_question)))
                .setContentTitle("Individual Quiz Screen Tutorial")
                .setContentText("Here You Can Find the Question, Read it Carefully! You Only Need to Select ONE answer Per Question. At this Point You will Not Know If You Are Correct, This is To Test Your Own Understanding Before The Team Quiz.")
                .setStyle(R.style.CustomShowcaseTheme2)
               // .setOnClickListener(this::indiQuizScreenTutorial1)
                .blockAllTouches()
                .build();
        showcaseView1.setButtonText("OK!");

    }


    public void indiQuizScreenTutorial2(IndiQuizScreenView view){

        showcaseView1.hide();

        showcaseView2 = new ShowcaseView.Builder(view)
                .setTarget(new ViewTarget(view.findViewById(R.id.tv_answer2)))
                .setContentTitle("Individual Quiz Screen Tutorial")
                .setContentText("Here You Find One of Four Possible Answers, Carefully Read Each Possible Answer.")
                .setStyle(R.style.CustomShowcaseTheme2)
                .setOnClickListener(view)
                .blockAllTouches()
                .build();
        showcaseView2.setButtonText("OK!");

        StartScreenView.tutorial_counter++;

    }

    public void indiQuizScreenTutorial3(IndiQuizScreenView view){

        showcaseView2.hide();

        showcaseView3 = new ShowcaseView.Builder(view)
                .setTarget(new ViewTarget(view.findViewById(R.id.imageView2)))
                .setContentTitle("Individual Quiz Screen Tutorial")
                .setContentText("Once You Have Read All The Possible Answers and Are Happy to Submit Your Choice, Scratch a Pad. Only when More Than 80% of the Pad has been Scratched, Will an answer Be Committed.")
                .setStyle(R.style.CustomShowcaseTheme2)
                .setOnClickListener(view)
                .blockAllTouches()
                .build();
        showcaseView3.setButtonText("OK!");

        StartScreenView.tutorial_counter++;

    }


    public void indiQuizScreenTutorial4(IndiQuizScreenView view){

        showcaseView3.hide();

        showcaseView4 = new ShowcaseView.Builder(view)
                .setTarget(new ViewTarget(view.findViewById(R.id.pageIndicatorView)))
                .setContentTitle("Individual Quiz Screen Tutorial")
                .setContentText("Here You Can See the Page Indicator. This will Display How Many Questions Are Remaining. You Can Use the Pagination Buttons above, Once You Have Completed The Quiz To Reflect On Your Committed Answers.")
                .setStyle(R.style.CustomShowcaseTheme2)
                .setOnClickListener(view)
                .blockAllTouches()
                .build();
        showcaseView4.setButtonText("OK!");

        StartScreenView.tutorial_counter++;

    }


    public void closeIndiQuizScreenTutorial(){

        showcaseView4.hide();

    }

}
