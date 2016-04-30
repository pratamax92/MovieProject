package com.android.moviedb.presenter;

import com.android.moviedb.api.ServiceConfig;
import com.android.moviedb.api.service.ApiService;
import com.android.moviedb.model.response.BaseApi;
import com.android.moviedb.model.response.Movie;
import com.android.moviedb.ui.movies.MainView;

import javax.inject.Inject;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class MainPresenter {
    private MainView mainView;
    private ApiService apiService;

    @Inject
    public MainPresenter(ApiService apiService) {
        this.apiService = apiService;
    }

    public void initView(MainView mainView) {
        this.mainView = mainView;
    }

    public void loadMovie(String mSort) {

        if (mainView != null) {
            mainView.onLoading(true);
            Call<BaseApi<Movie>> call = apiService.popular(ServiceConfig.API_KEY,mSort);
            call.enqueue(new Callback<BaseApi<Movie>>() {
                @Override
                public void onResponse(Response<BaseApi<Movie>> response, Retrofit retrofit) {
                    mainView.onLoading(false);
                    if (response.isSuccess()) {
                        // STATUS 200
                        mainView.loadData(response.body());
                    } else {
                        // STATUS 400
                        mainView.showError(response.message());
                    }
                }

                @Override
                public void onFailure(Throwable t) {
                    mainView.onLoading(false);
                    mainView.showError(t.getMessage());
                }
            });
        }
    }

}
