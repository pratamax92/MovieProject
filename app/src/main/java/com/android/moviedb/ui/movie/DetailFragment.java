package com.android.moviedb.ui.movie;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.moviedb.Injector;
import com.android.moviedb.R;
import com.android.moviedb.api.ServiceConfig;
import com.android.moviedb.model.response.BaseApi;
import com.android.moviedb.model.response.FavoriteModel;
import com.android.moviedb.model.response.Movie;
import com.android.moviedb.model.response.Trailer;
import com.android.moviedb.presenter.DetailPresenter;
import com.android.moviedb.ui.BaseFragment;
import com.android.moviedb.utils.ImageSize;
import com.squareup.picasso.Picasso;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import timber.log.Timber;

public class DetailFragment extends BaseFragment implements DetailView {


    @Bind(R.id.iv_poster)
    ImageView ivPoster;
    @Bind(R.id.tv_releasedate)
    TextView tvReleasedate;
    @Bind(R.id.tv_rate)
    TextView tvRate;
    @Bind(R.id.tv_voter)
    TextView tvVoter;
    @Bind(R.id.tv_synopsis)
    TextView tvSynopsis;
    @Bind(R.id.loading)
    ProgressBar loadingView;
    @Bind(R.id.addFavorite)
    Button addFavorite;
    @Bind(R.id.deleteFavorite)
    Button deleteFavorite;
    @Bind(R.id.recyclerview)
    RecyclerView recyclerView;
    @Bind(R.id.layout_trailer)
    LinearLayout layoutTrailer;

    @Inject
    DetailPresenter presenter;

    private DetailAdapter adapter;
    private Movie movie;
    private String video;

    public DetailFragment() {

    }

    @SuppressLint("ValidFragment")
    public DetailFragment(Movie movie) {
        this.movie = movie;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.add("share")
                .setIcon(R.drawable.share)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        String title = item.getTitle() + "";
        if (title.equals("share")) {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            String v;
            if (TextUtils.isEmpty(video)) {
                v = "";
            } else {
                v = "\nWatch Trailer On https://www.youtube.com/watch?v=" + video;
            }
            sendIntent.putExtra(Intent.EXTRA_TEXT, movie.title + "," + movie.overview + v);
            sendIntent.setType("text/plain");
            startActivity(sendIntent);
        } else {
            getActivity().onBackPressed();
        }
        return true;
    }

    public static DetailFragment newInstance(Movie movie) {
        DetailFragment fragment = new DetailFragment(movie);
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
        View view = inflater.inflate(R.layout.fragment_detail, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        StaggeredGridLayoutManager gridLayoutManager = new StaggeredGridLayoutManager(1, LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(gridLayoutManager);

        adapter = new DetailAdapter();
        recyclerView.setAdapter(adapter);
        presenter.loadTrailer(movie.id);

        tvReleasedate.setText("Release date - " + movie.releaseDate);

        Uri uri = Uri.parse(ServiceConfig.BASE_IMAGE_URL).buildUpon()
                .appendPath(ImageSize.w185.getValue())
                .appendPath(movie.posterPath.replace("/", ""))
                .build();
        Picasso.with(getContext()).load(uri).into(ivPoster);

        tvRate.setText(movie.voteAverage + "");
        tvVoter.setText(movie.voteCount + " Voter");
        tvSynopsis.setText(movie.overview + "");

        List<FavoriteModel> favoriteModels = FavoriteModel.find(FavoriteModel.class, "favid=?", movie.id + "");
        if (favoriteModels.size() == 0) {
            deleteFavorite.setVisibility(View.GONE);
            addFavorite.setVisibility(View.VISIBLE);
        } else {
            deleteFavorite.setVisibility(View.VISIBLE);
            addFavorite.setVisibility(View.GONE);
        }


    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
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
    public void loadData(BaseApi<Trailer> response) {
        if (response.results.size() > 1) {
            layoutTrailer.setVisibility(View.VISIBLE);
            video = response.results.get(0).key;
        }
        adapter.setTrailers(response.results);
    }

    @Override
    public void showError(String message) {

    }

    @OnClick({R.id.addFavorite, R.id.deleteFavorite})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.addFavorite:

                FavoriteModel favoriteModel = new FavoriteModel();
                favoriteModel.posterPath = movie.posterPath;
                favoriteModel.backdropPath = movie.backdropPath;
                favoriteModel.overview = movie.overview;
                favoriteModel.releaseDate = movie.releaseDate;
                favoriteModel.originalTitle = movie.originalTitle;
                favoriteModel.title = movie.title;
                favoriteModel.favid = movie.id + "";
                favoriteModel.popularity = movie.popularity;
                favoriteModel.voteCount = movie.voteCount;
                favoriteModel.voteAverage = movie.voteAverage;
                favoriteModel.save();
                deleteFavorite.setVisibility(View.VISIBLE);
                addFavorite.setVisibility(View.GONE);
                Toast.makeText(getContext(), "Data telah berhasil tersimpan", Toast.LENGTH_SHORT).show();

                break;
            case R.id.deleteFavorite:
                List<FavoriteModel> favorites = new FavoriteModel().find(FavoriteModel.class, "favid=?", movie.id + "");
                if (favorites.size() > 0) {
                    favorites.get(0).delete();
                    deleteFavorite.setVisibility(View.GONE);
                    addFavorite.setVisibility(View.VISIBLE);
                    Toast.makeText(getContext(), "Data telah berhasil terhapus", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }


}
