package com.android.moviedb.api.service;

import com.android.moviedb.model.response.BaseApi;
import com.android.moviedb.model.response.Movie;
import com.android.moviedb.model.response.Trailer;

import java.util.HashMap;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

public interface ApiService {
    /**
     * will return <BASE URL>"movie/popular?api_key=<API KEY>".
     *
     * @param apiKey
     * @return
     */
    @GET("discover/movie")
    Call<BaseApi<Movie>> popular(
            @Query("api_key") String apiKey
            , @Query("sort_by") String sortBy);

    @GET("movie/{id}/videos")
    Call<BaseApi<Trailer>> trailer(
            @Path("id") long id
            , @Query("api_key") String apiKey
    );

}
