package uk.co.oliverbcurtis.Kratzee.ui.detail.tutorialScreens;


import android.support.v7.widget.Toolbar;

import uk.co.oliverbcurtis.Kratzee.ui.detail.startScreen.StartScreenView;

public interface TutorialContract {

    interface View {

        void startScreenTutorial1(StartScreenView view, Toolbar toolbar);
        void startScreenTutorial2(StartScreenView view);
        void startScreenTutorial3(StartScreenView view);
        void closeStartScreenTutorial();


    }


}
