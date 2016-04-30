package com.android.moviedb.ui.movie;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import android.widget.ImageView;

import com.android.moviedb.BaseActivity;
import com.android.moviedb.R;
import com.android.moviedb.api.ServiceConfig;
import com.android.moviedb.model.response.Movie;
import com.android.moviedb.presenter.DetailPresenter;
import com.android.moviedb.utils.ImageSize;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class DetailActivity extends BaseActivity {

    public static String DATA = "data";
    @Bind(R.id.iv_backdrop)
    ImageView ivBackdrop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);
        Movie movie = (Movie) getIntent().getSerializableExtra(DATA);
        setTitle(movie.title);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String image = movie.backdropPath+"";
        if (TextUtils.isEmpty(image)) {
            image = movie.posterPath+"";
        }
        Uri uri = Uri.parse(ServiceConfig.BASE_IMAGE_URL).buildUpon()
                .appendPath(ImageSize.w500.getValue())
                .appendPath(image.replace("/", ""))
                .build();
        Picasso.with(this).load(uri).into(ivBackdrop);


        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().add(R.id.content, DetailFragment.newInstance(movie)).commit();
        }
    }

    public static void startThisActivity(Context context, Movie movies) {
        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra(DATA, movies);
        context.startActivity(intent);
    }


}
