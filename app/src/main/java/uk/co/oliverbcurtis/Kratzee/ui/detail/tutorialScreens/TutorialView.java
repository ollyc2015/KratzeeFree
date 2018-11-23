package uk.co.oliverbcurtis.Kratzee.ui.detail.tutorialScreens;

import android.app.Activity;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.ScrollView;
import me.toptas.fancyshowcase.FancyShowCaseQueue;
import me.toptas.fancyshowcase.FancyShowCaseView;
import me.toptas.fancyshowcase.FocusShape;
import uk.co.oliverbcurtis.Kratzee.R;
import uk.co.oliverbcurtis.Kratzee.ui.common.BaseActivity;
import uk.co.oliverbcurtis.Kratzee.ui.detail.indiTriviaMainScreen.IndiTriviaRegisterView;
import uk.co.oliverbcurtis.Kratzee.ui.detail.pinInput.QuizPinView;
import uk.co.oliverbcurtis.Kratzee.ui.detail.quizType.QuizTypeView;
import uk.co.oliverbcurtis.Kratzee.ui.detail.startScreen.StartScreenView;

public class TutorialView extends BaseActivity implements TutorialContract.View {


    /****************************************BELOW IS THE TUTORIAL FOR THE START SCREEN*****************************/

    @Override
    public void startScreenTutorial1(StartScreenView view, Toolbar toolbar){

        int actionBarSize = toolbar.getHeight();


        final FancyShowCaseView fancyShowCaseView1 = new FancyShowCaseView.Builder(view)
                .focusOn(toolbar)
                .title("\n\nWelcome To Kratzee! \n\nWant a Tutorial Refresher or Fancy Giving Some Useful Feedback?\n\nClick On The Highlighted Button and Choose From the Available Options.\n\nClick Anywhere on The Screen to Continue the Tutorial.")
                .titleStyle(R.style.MyTitleStyle, Gravity.BOTTOM | Gravity.CENTER)
                .focusRectAtPosition(actionBarSize * 7, actionBarSize / 2,  250, 250)
                .roundRectRadius(90)
                .backgroundColor(Color.parseColor("#e51249d9"))
                .build();


         FancyShowCaseView fancyShowCaseView2  = new FancyShowCaseView.Builder(view)
                 .focusOn(view.findViewById(R.id.btn_student))
                 .title("\n\nTake a Quiz!\n\nTo Take a Quiz Click The Button Highlighted. Please Note that you Will Need a Quiz PIN Given to You by The Person That Created the Quiz.")
                 .titleStyle(R.style.MyTitleStyle, Gravity.TOP | Gravity.CENTER)
                 .focusShape(FocusShape.ROUNDED_RECTANGLE)
                 .roundRectRadius(90)
                 .backgroundColor(Color.parseColor("#e51249d9"))
                 .build();


        FancyShowCaseView fancyShowCaseView3  = new FancyShowCaseView.Builder(view)
                .focusOn(view.findViewById(R.id.btn_lecturer))
                .title("\n\nCreate an Account and Make a Kratzee!\n\nTo Create an Account and Make Your Own Question-Sets, Click The Highlighted Button! This is Where You'll Generate Your Quiz PIN's! ")
                .focusShape(FocusShape.ROUNDED_RECTANGLE)
                .titleStyle(R.style.MyTitleStyle, Gravity.TOP | Gravity.CENTER)
                .roundRectRadius(90)
                .backgroundColor(Color.parseColor("#e51249d9"))
                .build();


        FancyShowCaseQueue mQueue = new FancyShowCaseQueue()
                .add(fancyShowCaseView1)
                .add(fancyShowCaseView2)
                .add(fancyShowCaseView3);

        mQueue.show();

    }

   /***********************************BELOW IS THE TUTORIAL FOR SECOND SCREEN (QUIZ TYPE) ************************************************/



    public void quizTypeTutorial1(QuizTypeView view){

        FancyShowCaseView fancyShowCaseView1  = new FancyShowCaseView.Builder(view)
                .focusOn(view.findViewById(R.id.btn_indi))
                .title("\n\nIndividual Quiz\n\nTo Take an Independent Quiz, Click The Highlighted Button. The Idea is to take this by yourself with no help, this will test your own understanding, before taking the same quiz in a team. More will be explained later...")
                .titleStyle(R.style.MyTitleStyle, Gravity.TOP | Gravity.CENTER)
                .focusShape(FocusShape.ROUNDED_RECTANGLE)
                .roundRectRadius(90)
                .backgroundColor(Color.parseColor("#e51249d9"))
                .build();


        FancyShowCaseView fancyShowCaseView2  = new FancyShowCaseView.Builder(view)
                .focusOn(view.findViewById(R.id.btn_team))
                .title("\n\nTeam Quiz\n\nOnce you have Completed the Individual Quiz, The Highlighted Button will become Available. You will then Take the Same Quiz as a Team, the Objective Will be Explained Later...")
                .titleStyle(R.style.MyTitleStyle, Gravity.TOP | Gravity.CENTER)
                .focusShape(FocusShape.ROUNDED_RECTANGLE)
                .roundRectRadius(90)
                .backgroundColor(Color.parseColor("#e51249d9"))
                .build();



        FancyShowCaseQueue mQueue = new FancyShowCaseQueue()
                .add(fancyShowCaseView1)
                .add(fancyShowCaseView2);

        mQueue.show();

    }

    /*************************BELOW IS THE TUTORIAL FOR THE QUESTION PIN SCREEN*******************************/


    public void pinInputTutorial1(QuizPinView view){


           new FancyShowCaseView.Builder(view)
                .focusOn(view.findViewById(R.id.et_pin))
                .title("\n\nPIN Input\n\nEnter Your Quiz PIN in the Highlighted Area. This will be Given to You by the Person That has Created the Quiz.")
                .titleStyle(R.style.MyTitleStyle, Gravity.TOP | Gravity.CENTER)
                .focusShape(FocusShape.ROUNDED_RECTANGLE)
                .roundRectRadius(90)
                .backgroundColor(Color.parseColor("#e51249d9"))
                .build()
                .show();
    }


    /*************************BELOW IS THE TUTORIAL FOR THOSE TAKING AN INDIVIDUAL QUIZ*******************************/


    public void indiRegisterTutorial1(IndiTriviaRegisterView view){


        FancyShowCaseView fancyShowCaseView1  = new FancyShowCaseView.Builder(view)
                .focusOn(view.findViewById(R.id.et_indi_name))
                .title("\n\nFirst Time?\n\nIf this is your First time Taking a Quiz by The Person who Gave You Their PIN, Register Your Name In the Highlighted Area.")
                .titleStyle(R.style.MyTitleStyle, Gravity.TOP | Gravity.CENTER)
                .focusShape(FocusShape.ROUNDED_RECTANGLE)
                .roundRectRadius(90)
                .backgroundColor(Color.parseColor("#e51249d9"))
                .build();

        FancyShowCaseView fancyShowCaseView2  = new FancyShowCaseView.Builder(view)
                .focusOn(view.findViewById(R.id.et_indi_student_number))
                .title("\n\nFirst Time?\n\nHere You Can Enter Your Student ID or Create a Random 7 Figure Unique-ID (and Remember it) - This will Help You To Identify Yourself in the Event You Take Future Quizzes set by The Same Person")
                .titleStyle(R.style.MyTitleStyle, Gravity.TOP | Gravity.CENTER)
                .focusShape(FocusShape.ROUNDED_RECTANGLE)
                .roundRectRadius(90)
                .backgroundColor(Color.parseColor("#e51249d9"))
                .build();


        FancyShowCaseView fancyShowCaseView3  = new FancyShowCaseView.Builder(view)
                .focusOn(view.findViewById(R.id.tv_studentSelection))
                .title("\n\nAlready Registered?\n\nIf you have Previously Taken a Quiz by The Person who Gave You Their PIN and Registered Your Name, Click on The Highlighted Button and Search For Your Name or Student Number in The Filterable List")
                .titleStyle(R.style.MyTitleStyle, Gravity.TOP | Gravity.CENTER)
                .focusShape(FocusShape.ROUNDED_RECTANGLE)
                .roundRectRadius(90)
                .backgroundColor(Color.parseColor("#e51249d9"))
                .build();


        FancyShowCaseQueue mQueue = new FancyShowCaseQueue()
                .add(fancyShowCaseView1)
                .add(fancyShowCaseView2)
                .add(fancyShowCaseView3);

        mQueue.show();

    }


    /*************************BELOW IS THE TUTORIAL FOR THE INDIVIDUAL QUIZ SCREEN********************************/

    public void indiQuizScreenTutorial1(Activity view){


        FancyShowCaseView fancyShowCaseView1  = new FancyShowCaseView.Builder(view)
                .focusOn(view.findViewById(R.id.tv_question))
                .title("\n\nIndividual Quiz Screen Tutorial\n\nHere You Can Find the Question, Read it Carefully! You Only Need to Select ONE answer Per Question. At this Point You will Not Know If You Are Correct, This is To Test Your Own Understanding Before The Team Quiz.")
                .titleStyle(R.style.MyTitleStyle, Gravity.CENTER)
                .enableAutoTextPosition()
                .focusShape(FocusShape.ROUNDED_RECTANGLE)
                .roundRectRadius(90)
                .backgroundColor(Color.parseColor("#e51249d9"))
                .build();

        FancyShowCaseView fancyShowCaseView2  = new FancyShowCaseView.Builder(view)
                .focusOn(view.findViewById(R.id.tv_answer1))
                .title("\n\nIndividual Quiz Screen Tutorial\n\nHere You Find One of Four Possible Answers, Carefully Read Each Possible Answer.")
                .titleStyle(R.style.MyTitleStyle, Gravity.BOTTOM | Gravity.CENTER)
                .focusShape(FocusShape.ROUNDED_RECTANGLE)
                .roundRectRadius(90)
                .backgroundColor(Color.parseColor("#e51249d9"))
                .build();


        FancyShowCaseView fancyShowCaseView3  = new FancyShowCaseView.Builder(view)
                .focusOn(view.findViewById(R.id.imageView1))
                .title("\n\nIndividual Quiz Screen Tutorial\n\nOnce You Have Read All The Possible Answers and Are Happy to Submit Your Choice, Scratch a Pad. Only when More Than 80% of the Pad has been Scratched, Will an answer Be Committed.")
                .titleStyle(R.style.MyTitleStyle, Gravity.BOTTOM | Gravity.CENTER)
                .focusShape(FocusShape.ROUNDED_RECTANGLE)
                .roundRectRadius(90)
                .backgroundColor(Color.parseColor("#e51249d9"))
                .build();



        FancyShowCaseQueue mQueue = new FancyShowCaseQueue()
                .add(fancyShowCaseView1)
                .add(fancyShowCaseView2)
                .add(fancyShowCaseView3);

        mQueue.show();


        mQueue.setCompleteListener(() -> {

            ((ScrollView) view.findViewById(R.id.myQuestionScrollView)).fullScroll(View.FOCUS_DOWN);

            final Handler handler = new Handler();
            handler.postDelayed(() -> {
                //Call the next tutorial screen after 100ms, this gives enough time for the view to focus down
                quizScreenTutorial2(view);
            }, 100);
        });

    }

    private void quizScreenTutorial2(Activity view){

        FancyShowCaseView fancyShowCaseView1  = new FancyShowCaseView.Builder(view)
                .focusOn(view.findViewById(R.id.pagination_container))
                .title("\n\nHighlighted are the Pagination Buttons; These Are Not Active During the Quiz as Pagination is Automatic. However, You Can Use the Pagination Buttons Once You Have Completed The Quiz To Reflect On Your Committed Answers.")
                .titleStyle(R.style.MyTitleStyle, Gravity.CENTER)
                .focusShape(FocusShape.ROUNDED_RECTANGLE)
                .roundRectRadius(90)
                .backgroundColor(Color.parseColor("#e51249d9"))
                .build();


        FancyShowCaseView fancyShowCaseView2  = new FancyShowCaseView.Builder(view)
                .focusOn(view.findViewById(R.id.pageIndicatorView))
                .title("\n\nIndividual Quiz Screen Tutorial\n\nHighlighted is the the Page Indicator. This will Display How Many Questions Are Remaining.")
                .titleStyle(R.style.MyTitleStyle, Gravity.BOTTOM | Gravity.CENTER)
                .focusShape(FocusShape.ROUNDED_RECTANGLE)
                .roundRectRadius(90)
                .backgroundColor(Color.parseColor("#e51249d9"))
                .build();

        FancyShowCaseQueue mQueue = new FancyShowCaseQueue()
                .add(fancyShowCaseView1)
                .add(fancyShowCaseView2);

        mQueue.show();

        mQueue.setCompleteListener(() -> ((ScrollView) view.findViewById(R.id.myQuestionScrollView)).fullScroll(View.FOCUS_UP));

    }

}
