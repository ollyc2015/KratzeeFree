package uk.co.oliverbcurtis.Kratzee.dagger;

import javax.inject.Singleton;

import dagger.Component;
import uk.co.oliverbcurtis.Kratzee.ui.common.BaseActivity;


    //Component binds our dependencies
    @Singleton
    @Component(modules = {AppModule.class})

    public interface AppComponent{

        void inject(DaggerApplication application);

        void inject(BaseActivity baseActivity);
    }

