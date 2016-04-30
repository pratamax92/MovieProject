package com.android.moviedb.ui.movie;

import com.android.moviedb.model.response.BaseApi;
import com.android.moviedb.model.response.Movie;
import com.android.moviedb.model.response.Trailer;

public interface DetailView {
    public void onLoading(boolean loading);

    void loadData(BaseApi<Trailer> popularResponse);

    void showError(String message);
}
