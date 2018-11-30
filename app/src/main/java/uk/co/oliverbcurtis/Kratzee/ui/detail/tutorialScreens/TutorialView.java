package uk.co.oliverbcurtis.Kratzee.ui.detail.tutorialScreens;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.ScrollView;
import me.toptas.fancyshowcase.FancyShowCaseQueue;
import me.toptas.fancyshowcase.FancyShowCaseView;
import me.toptas.fancyshowcase.FocusShape;
import me.toptas.fancyshowcase.listener.DismissListener;
import uk.co.oliverbcurtis.Kratzee.R;
import uk.co.oliverbcurtis.Kratzee.ui.common.BaseActivity;
import uk.co.oliverbcurtis.Kratzee.ui.detail.indiTriviaExisting.IndiTriviaExistView;
import uk.co.oliverbcurtis.Kratzee.ui.detail.indiTriviaMainScreen.IndiTriviaRegisterView;
import uk.co.oliverbcurtis.Kratzee.ui.detail.lecturerProfileMainMenu.LecturerProfileView;
import uk.co.oliverbcurtis.Kratzee.ui.detail.pinInput.QuizPinView;
import uk.co.oliverbcurtis.Kratzee.ui.detail.quizType.QuizTypeView;
import uk.co.oliverbcurtis.Kratzee.ui.detail.startScreen.StartScreenView;
import uk.co.oliverbcurtis.Kratzee.ui.detail.teamTriviaExisting.TeamTriviaExistView;
import uk.co.oliverbcurtis.Kratzee.ui.detail.teamTriviaRegister.team.TeamTriviaRegisterView;

import static android.view.View.TRANSLATION_X;
import static android.view.View.TRANSLATION_Y;

public class TutorialView extends BaseActivity implements TutorialContract.View {


    /****************************************BELOW IS THE TUTORIAL FOR THE START SCREEN*****************************/

    @Override
    public void startScreenTutorial1(StartScreenView view, Toolbar toolbar){


        final FancyShowCaseView fancyShowCaseView1 = new FancyShowCaseView.Builder(view)
                .focusOn(toolbar.getChildAt(2))
                .title("\nWelcome To Kratzee! \n\nWant a Tutorial Refresher or Fancy Giving Some Useful Feedback?\n\nClick On The Highlighted Button and Choose From the Available Options.\n\nClick Anywhere on The Screen to Continue the Tutorial.")
                .titleStyle(R.style.MyTitleStyle, Gravity.CENTER)
                .enableAutoTextPosition()
                .roundRectRadius(90)
                .backgroundColor(Color.parseColor("#e51249d9"))
                .build();


         FancyShowCaseView fancyShowCaseView2  = new FancyShowCaseView.Builder(view)
                 .focusOn(view.findViewById(R.id.btn_student))
                 .title("\nTake a Quiz!\n\nTo Take a Quiz Click The Button Highlighted. Please Note that you Will Need a Quiz PIN Given to You by The Person That Created the Quiz.")
                 .titleStyle(R.style.MyTitleStyle, Gravity.TOP|Gravity.CENTER)
                 .focusShape(FocusShape.ROUNDED_RECTANGLE)
                 .roundRectRadius(90)
                 .backgroundColor(Color.parseColor("#e51249d9"))
                 .build();


        FancyShowCaseView fancyShowCaseView3  = new FancyShowCaseView.Builder(view)
                .focusOn(view.findViewById(R.id.btn_lecturer))
                .title("\nCreate an Account and Make a Kratzee!\n\nThe Button Highlighted is Where You'll Create Questions, Obtain Your Quiz PIN's & Other Administrative Tasks!\n\nThis should be Your Starting Point if You haven't Been Given a PIN and Therefore Need to Create a Quiz.")
                .focusShape(FocusShape.ROUNDED_RECTANGLE)
                .titleStyle(R.style.MyTitleStyle, Gravity.CENTER)
                .enableAutoTextPosition()
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
                .title("\nIndividual Quiz\n\nThe Idea of the Individual Quiz is to test your own understanding with no help, before taking the same quiz again in a team...")
                .titleStyle(R.style.MyTitleStyle, Gravity.CENTER | Gravity.TOP)
                .focusShape(FocusShape.ROUNDED_RECTANGLE)
                .roundRectRadius(90)
                .backgroundColor(Color.parseColor("#e51249d9"))
                .build();


        FancyShowCaseView fancyShowCaseView2  = new FancyShowCaseView.Builder(view)
                .focusOn(view.findViewById(R.id.btn_team))
                .title("\nTeam Quiz\n\nOnce you have Completed the Individual Quiz, The Highlighted Button will become Available. You will then Take the Same Quiz as a Team, the Objective Will be Explained Later...")
                .titleStyle(R.style.MyTitleStyle, Gravity.CENTER)
                .enableAutoTextPosition()
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
                .title("\nPIN Input\n\nEnter Your Quiz PIN in the Highlighted Area. This will be Given to You by the Person That has Created the Quiz.")
                .titleStyle(R.style.MyTitleStyle, Gravity.CENTER)
                .enableAutoTextPosition()
                .focusShape(FocusShape.ROUNDED_RECTANGLE)
                .roundRectRadius(90)
                .backgroundColor(Color.parseColor("#e51249d9"))
                .build()
                .show();
    }


    /*************************BELOW IS THE TUTORIAL FOR THOSE REGISTERING TO TAKE AN INDIVIDUAL QUIZ*******************************/


    public void indiRegisterTutorial1(IndiTriviaRegisterView view){


        FancyShowCaseView fancyShowCaseView1  = new FancyShowCaseView.Builder(view)
                .focusOn(view.findViewById(R.id.et_indi_name))
                .title("\nFirst Time?\n\nIf this is your First time Taking an Individual-Quiz by The Person who Gave You Their PIN, Register Your Name In the Highlighted Area.")
                .titleStyle(R.style.MyTitleStyle, Gravity.CENTER | Gravity.TOP)
                .focusShape(FocusShape.ROUNDED_RECTANGLE)
                .roundRectRadius(90)
                .backgroundColor(Color.parseColor("#e51249d9"))
                .build();

        FancyShowCaseView fancyShowCaseView2  = new FancyShowCaseView.Builder(view)
                .focusOn(view.findViewById(R.id.et_indi_student_number))
                .title("\nFirst Time?\n\nHere You Can Enter Your Student ID or Create a Random 7 Figure Unique-ID (and Remember it) - This will Help You To Identify Yourself in the Event You Take Future Quizzes set by The Same Person")
                .titleStyle(R.style.MyTitleStyle, Gravity.CENTER)
                .enableAutoTextPosition()
                .focusShape(FocusShape.ROUNDED_RECTANGLE)
                .roundRectRadius(90)
                .backgroundColor(Color.parseColor("#e51249d9"))
                .build();


        FancyShowCaseView fancyShowCaseView3  = new FancyShowCaseView.Builder(view)
                .focusOn(view.findViewById(R.id.tv_studentSelection))
                .title("\nAlready Registered?\n\nIf you have Previously Taken an Individual-Quiz by The Person who Gave You Their PIN and Registered Your Name, Click on The Highlighted Button and Search For Your Name or Student Number in The Filterable List")
                .titleStyle(R.style.MyTitleStyle, Gravity.CENTER)
                .enableAutoTextPosition()
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

    /*************************BELOW IS THE TUTORIAL FOR THOSE ALREADY REGISTERED TO TAKE AN INDIVIDUAL QUIZ********************************/

    public void registeredIndividual1(IndiTriviaExistView view){

        FancyShowCaseView fancyShowCaseView1  = new FancyShowCaseView.Builder(view)
                .focusOn(view.findViewById(R.id.et_search_name))
                .title("\nQuick Search\n\nIf You Cannot Immediately See Your Name In the List Below, Use the Search Field Highlighted to Filter By Entering Your Full-Name or Student-ID.")
                .titleStyle(R.style.MyTitleStyle, Gravity.CENTER)
                .enableAutoTextPosition()
                .enableAutoTextPosition()
                .focusShape(FocusShape.ROUNDED_RECTANGLE)
                .roundRectRadius(90)
                .backgroundColor(Color.parseColor("#e51249d9"))
                .build();


        FancyShowCaseView fancyShowCaseView2  = new FancyShowCaseView.Builder(view)
                .focusOn(view.findViewById(R.id.individualNameLayout))
                .title("\nRegistered Teams\n\nIn the Highlighted Area You Can See All The Registered Names used to Take the Quizzes Set by The user that Gave You Their Quiz PIN. Please Search For Your Name In The Highlighted Area.")
                .titleStyle(R.style.MyTitleStyle, Gravity.CENTER)
                .enableAutoTextPosition()
                .focusShape(FocusShape.ROUNDED_RECTANGLE)
                .roundRectRadius(90)
                .backgroundColor(Color.parseColor("#e51249d9"))
                .build();


        FancyShowCaseQueue mQueue = new FancyShowCaseQueue()
                .add(fancyShowCaseView1)
                .add(fancyShowCaseView2);

        mQueue.show();
    }


    /*************************BELOW IS THE TUTORIAL FOR THE INDIVIDUAL QUIZ SCREEN********************************/

    public void indiQuizScreenTutorial1(Activity view){


        FancyShowCaseView fancyShowCaseView1  = new FancyShowCaseView.Builder(view)
                .focusOn(view.findViewById(R.id.tv_question))
                .title("\nIndividual Quiz Screen Tutorial\n\nHere You Can Find the Question, Read it Carefully! You Only Need to Select ONE answer Per Question. At this Point You will Not Know If You Are Correct, This is To Test Your Own Understanding Before The Team Quiz.")
                .titleStyle(R.style.MyTitleStyle, Gravity.CENTER)
                .enableAutoTextPosition()
                .focusShape(FocusShape.ROUNDED_RECTANGLE)
                .roundRectRadius(90)
                .backgroundColor(Color.parseColor("#e51249d9"))
                .build();

        FancyShowCaseView fancyShowCaseView2  = new FancyShowCaseView.Builder(view)
                .focusOn(view.findViewById(R.id.tv_answer1))
                .title("\nIndividual Quiz Screen Tutorial\n\nHere You Find One of Four Possible Answers, Carefully Read Each Possible Answer.")
                .titleStyle(R.style.MyTitleStyle, Gravity.CENTER | Gravity.TOP)
                .focusShape(FocusShape.ROUNDED_RECTANGLE)
                .roundRectRadius(90)
                .backgroundColor(Color.parseColor("#e51249d9"))
                .build();


        FancyShowCaseView fancyShowCaseView3  = new FancyShowCaseView.Builder(view)
                .focusOn(view.findViewById(R.id.imageView1))
                .title("\nIndividual Quiz Screen Tutorial\n\nOnce You Have Read All The Possible Answers and Are Happy to Submit Your Choice, Scratch a Pad. Only when More Than 80% of the Pad has been Scratched, Will an answer Be Committed.")
                .titleStyle(R.style.MyTitleStyle, Gravity.CENTER)
                .enableAutoTextPosition()
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

            ImageView fingerDemo = view.findViewById(R.id.finger_demo);
            fingerDemo.setImageResource(R.drawable.finger_demo);



            TranslateAnimation mAnimation = new TranslateAnimation(0, 180, 0, 0);
            mAnimation.setDuration(1000);
            mAnimation.setFillAfter(true);
            mAnimation.setRepeatCount(-1); //if set to -1, the animation will repeat continuously
            mAnimation.setRepeatMode(Animation.REVERSE);
            fingerDemo.setAnimation(mAnimation);


            /*
            ValueAnimator animator = ValueAnimator.ofFloat(0, 1); // values from 0 to 1
            animator.setDuration(1000); // 1 seconds duration
            animator.setRepeatCount(-1);
            animator.setRepeatMode(ValueAnimator.REVERSE);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener()
            {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {

                    float value = (Float) (animation.getAnimatedValue());
                    // Set translation of your view here. Position can be calculated
                    // out of value. This code should move the view in a half circle.
                    fingerDemo.setTranslationX((float)(180.0 * Math.sin(value*Math.PI)));
                    fingerDemo.setTranslationY((float)(80.0 * Math.cos(value*Math.PI)));

                }
            });
            animator.addListener(new AnimatorListenerAdapter()
            {
                @Override
                public void onAnimationEnd(Animator animation)
                {
                    // done
                }
            });
            animator.start();

*/


            mAnimation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                    new FancyShowCaseView.Builder(view)
                            .focusOn(view.findViewById(R.id.imageView1))
                            .title("\nIndividual Quiz Screen Tutorial\n\nSimply Scratch Left to Right Then Lift your Finger off the Screen and then Re-Apply, Left to Right For A Better Scratch Experience!")
                            .titleStyle(R.style.MyTitleStyle, Gravity.CENTER)
                            .enableAutoTextPosition()
                            .focusCircleRadiusFactor(1.8)
                            .roundRectRadius(90)
                            .backgroundColor(Color.parseColor("#e51249d9"))
                            .dismissListener(new DismissListener() {
                                @Override
                                public void onDismiss(String id) {
                                    // FancyShowCaseView is dismissed by user
                                    //Remove the finger image
                                    fingerDemo.setImageResource(android.R.color.transparent);
                                    ((ScrollView) view.findViewById(R.id.myQuestionScrollView)).fullScroll(View.FOCUS_DOWN);

                                    final Handler handler = new Handler();
                                    handler.postDelayed(() -> {
                                        //Call the next tutorial screen after 100ms, this gives enough time for the view to focus down
                                        quizScreenTutorial2(view);
                                    }, 200);
                                }

                                @Override
                                public void onSkipped(String id) {
                                    // Skipped because it was setup to shown only once and shown before
                                }
                            })
                            .build()
                            .show();

                }

                @Override
                public void onAnimationRepeat(Animation animation) { }

                @Override
                public void onAnimationEnd(Animation animation) { }
            });

        });

    }

    private void quizScreenTutorial2(Activity view){

        FancyShowCaseView fancyShowCaseView1  = new FancyShowCaseView.Builder(view)
                .focusOn(view.findViewById(R.id.pagination_container))
                .title("\nPagination Buttons\n\nHighlighted are the Pagination Buttons; These Are Not Active During the Quiz as Pagination is Automatic. However, You Can Use the Pagination Buttons Once You Have Completed The Quiz To Reflect On Your Committed Answers.")
                .titleStyle(R.style.MyTitleStyle, Gravity.CENTER)
                .enableAutoTextPosition()
                .focusShape(FocusShape.ROUNDED_RECTANGLE)
                .roundRectRadius(90)
                .backgroundColor(Color.parseColor("#e51249d9"))
                .build();


        FancyShowCaseView fancyShowCaseView2  = new FancyShowCaseView.Builder(view)
                .focusOn(view.findViewById(R.id.pageIndicatorView))
                .title("\nIndividual Quiz Screen Tutorial\n\nHighlighted is the the Page Indicator. This Will Display How Many Questions Are Remaining.\n\nGood Luck!")
                .titleStyle(R.style.MyTitleStyle, Gravity.CENTER)
                .enableAutoTextPosition()
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


    /*************************BELOW IS THE TUTORIAL FOR THE TEAM REGISTRATION SCREEN********************************/

    public void teamRegistrationTutorial1(TeamTriviaRegisterView view){


        FancyShowCaseView fancyShowCaseView1  = new FancyShowCaseView.Builder(view)
                .focusOn(view.findViewById(R.id.et_team))
                .title("\nFirst Time?\n\nIf this is your First time Taking a Team Quiz by The Person who Gave You Their PIN, Register Your Team-Name In the Highlighted Area. Then Add Your Team-Members!")
                .titleStyle(R.style.MyTitleStyle, Gravity.CENTER | Gravity.TOP)
                .focusShape(FocusShape.ROUNDED_RECTANGLE)
                .roundRectRadius(90)
                .backgroundColor(Color.parseColor("#e51249d9"))
                .build();



        FancyShowCaseView fancyShowCaseView2  = new FancyShowCaseView.Builder(view)
                .focusOn(view.findViewById(R.id.tv_teamSelection))
                .title("\nAlready Registered?\n\nIf you have Previously Taken a Team-Quiz by The Person who Gave You Their PIN and Registered Your Team-Name and Team-Members\n\nClick on The Highlighted Button and Search For Your Team-Name in The Filterable List")
                .titleStyle(R.style.MyTitleStyle, Gravity.CENTER)
                .enableAutoTextPosition()
                .focusShape(FocusShape.ROUNDED_RECTANGLE)
                .roundRectRadius(90)
                .backgroundColor(Color.parseColor("#e51249d9"))
                .build();


        FancyShowCaseQueue mQueue = new FancyShowCaseQueue()
                .add(fancyShowCaseView1)
                .add(fancyShowCaseView2);

        mQueue.show();
    }

    /*************************BELOW IS THE TUTORIAL FOR A TEAM THAT IS ALREADY REGISTERED SCREEN********************************/

    public void registeredTeamTutorial1(TeamTriviaExistView view){

        FancyShowCaseView fancyShowCaseView1  = new FancyShowCaseView.Builder(view)
                .focusOn(view.findViewById(R.id.tv_all_student_names))
                .title("\nExisting Teams\n\nFind Your Team Name and Once You Click on it, You'll be able to Select Your Team-Members That Are Present For This Session.\n\nYou Can also Add Another Team-Member if They were Not Present When You Originally Created the Team.")
                .titleStyle(R.style.MyTitleStyle, Gravity.CENTER)
                .enableAutoTextPosition()
                .enableAutoTextPosition()
                .focusShape(FocusShape.ROUNDED_RECTANGLE)
                .roundRectRadius(90)
                .backgroundColor(Color.parseColor("#e51249d9"))
                .build();

        FancyShowCaseView fancyShowCaseView2  = new FancyShowCaseView.Builder(view)
                .focusOn(view.findViewById(R.id.et_search_name))
                .title("\nQuick Search\n\nIf You Cannot Immediately See Your Team Name In the List Below, Use the Search Field Highlighted to Filter Through The Registered Teams")
                .titleStyle(R.style.MyTitleStyle, Gravity.CENTER)
                .enableAutoTextPosition()
                .focusShape(FocusShape.ROUNDED_RECTANGLE)
                .roundRectRadius(90)
                .backgroundColor(Color.parseColor("#e51249d9"))
                .build();


        FancyShowCaseView fancyShowCaseView3  = new FancyShowCaseView.Builder(view)
                .focusOn(view.findViewById(R.id.teamNameLayout))
                .title("\nRegistered Teams\n\nIn the Highlighted Area You Can See All The Registered Teams used to Take the Quizzes Set by the user that Gave You Their Quiz PIN. Please Search For The Team Name You Created")
                .titleStyle(R.style.MyTitleStyle, Gravity.CENTER)
                .enableAutoTextPosition()
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

    /*************************BELOW IS THE TUTORIAL FOR THE TEAM QUIZ SCREEN********************************/
    public void teamQuizScreenTutorial1(Activity view){

        FancyShowCaseView fancyShowCaseView1  = new FancyShowCaseView.Builder(view)
                .focusOn(view.findViewById(R.id.team_points_containter))
                .title("\nTeam Quiz Screen Tutorial\n\nThe Layout is Exactly the Same as the Individual Quiz Layout with a Couple of Additional Features.\n\nYou Can Now Keep Track of your Points. The Purpose here is to have a discussion with Your Team-Members, based on Your Individual Submissions, to Help Sniff out the correct answer as a Team!")
                .titleStyle(R.style.MyTitleStyle, Gravity.CENTER | Gravity.BOTTOM )
                .enableAutoTextPosition()
                .focusShape(FocusShape.ROUNDED_RECTANGLE)
                .roundRectRadius(90)
                .backgroundColor(Color.parseColor("#e51249d9"))
                .build();


        FancyShowCaseView fancyShowCaseView2  = new FancyShowCaseView.Builder(view)
                .focusOn(view.findViewById(R.id.imageView1))
                .title("\nTeam Quiz Screen Tutorial\n\nNow When Your Scratch a Pad, You will Know if You have Found the Correct Answer by Finding the Star! Good Luck!\n\nIf you Find the Star on your...\nFirst Attempt = 4 Points\nSecond Attempt = 2 Points\nThird Attempt = 1 Point\nFourth Attempt = 0 Points")
                .titleStyle(R.style.MyTitleStyle, Gravity.CENTER)
                .enableAutoTextPosition()
                .focusShape(FocusShape.ROUNDED_RECTANGLE)
                .roundRectRadius(90)
                .backgroundColor(Color.parseColor("#e51249d9"))
                .build();


        FancyShowCaseQueue mQueue = new FancyShowCaseQueue()
                .add(fancyShowCaseView1)
                .add(fancyShowCaseView2);

        mQueue.show();
    }

    /*************************BELOW IS THE TUTORIAL FOR LECTURER PROFILE MAIN MENU SCREEN********************************/

    public void lecturerProfileTutorial1(LecturerProfileView view){

        FancyShowCaseView fancyShowCaseView1  = new FancyShowCaseView.Builder(view)
                .focusOn(view.findViewById(R.id.tv_question_sets_available))
                .title("\nQuiz Creator Tutorial\n\nHere you can Keep Track of your Remaining Question-Set Allocation. You can add a Total of 5 different Question-Sets.")
                .titleStyle(R.style.MyTitleStyle, Gravity.CENTER )
                .focusCircleRadiusFactor(1.8)
                .focusShape(FocusShape.ROUNDED_RECTANGLE)
                .roundRectRadius(90)
                .backgroundColor(Color.parseColor("#e51249d9"))
                .build();

        FancyShowCaseView fancyShowCaseView2  = new FancyShowCaseView.Builder(view)
                .focusOn(view.findViewById(R.id.btn_create_questions))
                .title("\nCreate a Question-Set\n\nThe Highlighted Button is Visible When you're within Your Question-Set Allocation Limit. Create a Question-Set & a Question-PIN will automatically be Generated.\n\nA Question-Set is defined by a Topic and You can add as many Questions to a Topic as you Wish!")
                .titleStyle(R.style.MyTitleStyle, Gravity.CENTER | Gravity.TOP )
                .enableAutoTextPosition()
                .focusShape(FocusShape.ROUNDED_RECTANGLE)
                .roundRectRadius(90)
                .backgroundColor(Color.parseColor("#e51249d9"))
                .build();

        FancyShowCaseView fancyShowCaseView3  = new FancyShowCaseView.Builder(view)
                .focusOn(view.findViewById(R.id.btn_edit_topics))
                .title("\nEdit a Question-Set\n\nIn 'Edit Existing Question-Sets' (the button highlighted) you can Edit an Existing Question-Set and Also Find Your Generated Question-PINs.\n\nYou can also Activate and Deactivate Your Question-Topics. This means that whilst deactivated, no-one can access your Question-Set, Even if they have the PIN.")
                .titleStyle(R.style.MyTitleStyle, Gravity.CENTER | Gravity.TOP )
                .enableAutoTextPosition()
                .focusShape(FocusShape.ROUNDED_RECTANGLE)
                .roundRectRadius(90)
                .backgroundColor(Color.parseColor("#e51249d9"))
                .build();

        FancyShowCaseView fancyShowCaseView4  = new FancyShowCaseView.Builder(view)
                .focusOn(view.findViewById(R.id.btn_edit_topics))
                .title("\nEdit a Question-Set\n\nA Good Tip\n\nOnce Your users have Taken the Individual Quiz and have Entered the same PIN for the Team-Quiz, Deactivate the Quiz PIN. This will Limit Cheating because if a Team Back out of The Quiz after completing it but not submitting and then Re-Enter the PIN, they will be unable to Gain Access to the Quiz and Start Again without having to notify you.\n\nNow Explore and Enjoy!")
                .titleStyle(R.style.MyTitleStyle, Gravity.CENTER | Gravity.TOP )
                .enableAutoTextPosition()
                .focusShape(FocusShape.ROUNDED_RECTANGLE)
                .roundRectRadius(90)
                .backgroundColor(Color.parseColor("#e51249d9"))
                .build();

        FancyShowCaseQueue mQueue = new FancyShowCaseQueue()
                .add(fancyShowCaseView1)
                .add(fancyShowCaseView2)
                .add(fancyShowCaseView3)
                .add(fancyShowCaseView4);

        mQueue.show();

    }

}
