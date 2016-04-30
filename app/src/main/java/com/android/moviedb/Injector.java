package com.android.moviedb;

import android.app.Application;

public enum Injector {
    INSTANCE;

    Injector() {
    }

    private ApplicationComponent applicationComponent;

    public void initializeApplication(Application application) {
        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(application))
                .build();
    }

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }
}
