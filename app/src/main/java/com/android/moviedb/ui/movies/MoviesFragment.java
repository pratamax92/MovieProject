package com.android.moviedb.ui.movies;

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
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.android.moviedb.Injector;
import com.android.moviedb.R;
import com.android.moviedb.api.ServiceConfig;
import com.android.moviedb.model.response.BaseApi;
import com.android.moviedb.model.response.Movie;
import com.android.moviedb.presenter.MainPresenter;
import com.android.moviedb.ui.BaseFragment;
import com.android.moviedb.ui.favorite.FavoriteActivity;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MoviesFragment extends BaseFragment implements MainView {

    public static final int SPAN_COUNT = 2;
    public static final int ITEM_SPACE = 16;
    @Bind(R.id.recyclerview)
    RecyclerView recyclerView;
    @Bind(R.id.loading)
    ProgressBar loadingView;
    @Inject
    MainPresenter presenter;

    private MoviesAdapter adapter;
    private String mSort = ServiceConfig.SORT_POPULAR;

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menu1:
                FavoriteActivity.startThisActivity(getContext());
                return true;
            case R.id.submenu1:
                mSort = ServiceConfig.SORT_POPULAR;
                setSubTitle();
                presenter.loadMovie(mSort);
                return true;
            case R.id.submenu2:
                mSort = ServiceConfig.SORT_HIGHEST_RATED;
                setSubTitle();
                presenter.loadMovie(mSort);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }


    }


    public static MoviesFragment newInstance() {
        MoviesFragment fragment = new MoviesFragment();
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Injector.INSTANCE.getApplicationComponent().inject(this);
        presenter.initView(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_movies, container, false);
    }

    private void setSubTitle() {
        String subtitle;
        if (mSort.equals(ServiceConfig.SORT_POPULAR)) {
            subtitle = ((AppCompatActivity) getActivity()).getString(R.string.sort_most_popular);
        } else {
            subtitle = ((AppCompatActivity) getActivity()).getString(R.string.sort_highest_rated);
        }
        ((AppCompatActivity) getActivity()).getSupportActionBar().setSubtitle(subtitle);
        setHasOptionsMenu(true);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        StaggeredGridLayoutManager gridLayoutManager = new StaggeredGridLayoutManager(SPAN_COUNT, LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(gridLayoutManager);

        adapter = new MoviesAdapter();
        recyclerView.setAdapter(adapter);
        mSort = ServiceConfig.SORT_POPULAR;
        setSubTitle();
        presenter.loadMovie(mSort);
    }

    @Override
    public void onLoading(boolean loading) {
        if (loading) {
            loadingView.setVisibility(View.VISIBLE);
        } else {
            loadingView.setVisibility(View.GONE);
        }
    }

    @Override
    public void loadData(BaseApi<Movie> popularResponse) {
        adapter.setMovies(popularResponse.results);
    }

    @Override
    public void showError(final String message) {
        Snackbar.make(recyclerView, message, Snackbar.LENGTH_INDEFINITE)
                .setAction(R.string.label_retry, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        presenter.loadMovie(mSort);
                    }
                })
                .show();
    }


}
