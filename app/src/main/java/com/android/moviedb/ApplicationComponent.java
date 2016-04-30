package com.android.moviedb;

import com.android.moviedb.api.ApiModule;
import com.android.moviedb.ui.movie.DetailFragment;
import com.android.moviedb.ui.movies.MoviesFragment;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(
        modules = {
            ApplicationModule.class, ApiModule.class
        }
)
public interface ApplicationComponent {
    void inject(MoviesFragment moviesFragment);
    void inject(DetailFragment detailFragment);
}
