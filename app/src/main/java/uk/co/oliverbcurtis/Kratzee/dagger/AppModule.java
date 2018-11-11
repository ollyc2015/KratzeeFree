package uk.co.oliverbcurtis.Kratzee.dagger;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.preference.PreferenceManager;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import uk.co.oliverbcurtis.Kratzee.sqlite.KratzeeDatabase;

@Module
public class AppModule {

    private final DaggerApplication application;

    //This is where context is being defined, singleton behaviour is specified as we only need one instance to be created
    public AppModule(DaggerApplication app) {
        this.application = app;
    }


    @Provides
    @Singleton
    Context providesApplicationContext() {

        return application;
    }

    //Below creates reference to a new Kratzee database object
    @Provides
    @Singleton
    public KratzeeDatabase providesKratzeeDatabase(Context app) {
        return new KratzeeDatabase(app);
    }


    //Provide reference to shared pref
    @Provides @Singleton
    SharedPreferences providesSharedPreferences(Context app){
        return  PreferenceManager.getDefaultSharedPreferences(app);
    }

}
