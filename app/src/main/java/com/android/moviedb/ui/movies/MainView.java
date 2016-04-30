package com.android.moviedb.ui.movies;

import com.android.moviedb.model.response.Movie;
import com.android.moviedb.model.response.BaseApi;

public interface MainView {
    public void onLoading(boolean loading);

    void loadData(BaseApi<Movie> popularResponse);

    void showError(String message);
}
