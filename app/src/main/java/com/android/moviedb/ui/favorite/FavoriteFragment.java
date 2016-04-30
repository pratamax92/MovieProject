package com.android.moviedb.ui.favorite;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.android.moviedb.R;
import com.android.moviedb.api.ServiceConfig;
import com.android.moviedb.model.response.BaseApi;
import com.android.moviedb.model.response.FavoriteModel;
import com.android.moviedb.model.response.Movie;
import com.android.moviedb.ui.BaseFragment;
import com.android.moviedb.ui.movies.MoviesAdapter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class FavoriteFragment extends BaseFragment {

    public static final int SPAN_COUNT = 2;

    @Bind(R.id.recyclerview)
    RecyclerView recyclerView;
    @Bind(R.id.loading)
    ProgressBar loadingView;

    private MoviesAdapter adapter;

    public static FavoriteFragment newInstance() {
        FavoriteFragment fragment = new FavoriteFragment();
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_movies, container, false);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Favorite");
        loadingView.setVisibility(View.GONE);
        StaggeredGridLayoutManager gridLayoutManager = new StaggeredGridLayoutManager(SPAN_COUNT, LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(gridLayoutManager);

        adapter = new MoviesAdapter();
        recyclerView.setAdapter(adapter);

        List<FavoriteModel> favoriteModels = new FavoriteModel().find(FavoriteModel.class, null, null);
        if (favoriteModels.size() > 0) {
            List<Movie> movie = new ArrayList<>();
            for (int i = 0; i < favoriteModels.size(); i++) {
                FavoriteModel favoriteModel = favoriteModels.get(i);
                Movie movie1 = new Movie(favoriteModel.posterPath, favoriteModel.backdropPath,
                        favoriteModel.overview, favoriteModel.releaseDate, Long.parseLong(favoriteModel.favid),
                        favoriteModel.originalTitle, favoriteModel.title, favoriteModel.popularity, favoriteModel.voteCount,
                        (int) favoriteModel.voteAverage);
                movie.add(movie1);
            }

            adapter.setMovies(movie);
        }
    }


}
