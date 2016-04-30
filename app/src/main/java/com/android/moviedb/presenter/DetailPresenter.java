package com.android.moviedb.presenter;

import com.android.moviedb.api.ServiceConfig;
import com.android.moviedb.api.service.ApiService;
import com.android.moviedb.model.response.BaseApi;
import com.android.moviedb.model.response.Trailer;
import com.android.moviedb.ui.movie.DetailView;

import javax.inject.Inject;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class DetailPresenter {
    private DetailView detailView;
    private ApiService apiService;

    @Inject
    public DetailPresenter(ApiService apiService) {
        this.apiService = apiService;
    }

    public void initView(DetailView detailView) {
        this.detailView = detailView;
    }

    public void loadTrailer(long id) {
        if (detailView != null) {
            detailView.onLoading(true);
            Call<BaseApi<Trailer>> call = apiService.trailer(id,ServiceConfig.API_KEY);
            call.enqueue(new Callback<BaseApi<Trailer>>() {
                @Override
                public void onResponse(Response<BaseApi<Trailer>> response, Retrofit retrofit) {
                    detailView.onLoading(false);
                    if (response.isSuccess()) {
                        // STATUS 200
                        detailView.loadData(response.body());
                    } else {
                        // STATUS 400
                        detailView.showError(response.message());
                    }
                }

                @Override
                public void onFailure(Throwable t) {
                    detailView.onLoading(false);
                    detailView.showError(t.getMessage());
                }
            });
        }
    }

}
