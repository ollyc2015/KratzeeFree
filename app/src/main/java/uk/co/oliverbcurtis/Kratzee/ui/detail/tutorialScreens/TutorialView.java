package uk.co.oliverbcurtis.Kratzee.ui.detail.tutorialScreens;

import com.github.amlcurran.showcaseview.ShowcaseView;
import com.github.amlcurran.showcaseview.targets.ViewTarget;
import uk.co.oliverbcurtis.Kratzee.R;
import uk.co.oliverbcurtis.Kratzee.ui.common.BaseActivity;
import uk.co.oliverbcurtis.Kratzee.ui.detail.startScreen.StartScreenView;

public class TutorialView extends BaseActivity implements TutorialContract.View {

    private ShowcaseView showcaseView1, showcaseView2;


    @Override
    public void startScreenTutorial1(StartScreenView view){

         showcaseView1 = new ShowcaseView.Builder(view)
                .setTarget(new ViewTarget(view.findViewById(R.id.btn_student)))
                .setContentTitle("Welcome To Kratzee!")
                .setContentText("Click Here To Take a Quiz, Please note that you Will Need a Quiz PIN Given to You by The Person That Created the Quiz")
                .setStyle(R.style.CustomShowcaseTheme2)
                .setOnClickListener(view)
                .build();
        showcaseView1.forceTextPosition(ShowcaseView.ABOVE_SHOWCASE);
        showcaseView1.setButtonText("OK!");

    }


    @Override
    public void startScreenTutorial2(StartScreenView view) {

        showcaseView1.hide();

         showcaseView2 = new ShowcaseView.Builder(view)
                .setTarget(new ViewTarget(view.findViewById(R.id.btn_lecturer)))
                .setContentTitle("Welcome To Kratzee!")
                .setContentText("Click Here To Create an Account and Make Your Own Question Sets!")
                .setStyle(R.style.CustomShowcaseTheme2)
                .setOnClickListener(view)
                .build();
        showcaseView2.setButtonText("OK!");

        StartScreenView.tutorial_counter++;
    }


    @Override
    public void closeStartScreenTutorial(){

        showcaseView2.hide();
    }

}
