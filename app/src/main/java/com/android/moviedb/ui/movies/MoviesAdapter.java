package com.android.moviedb.ui.movies;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.moviedb.R;
import com.android.moviedb.api.ServiceConfig;
import com.android.moviedb.model.response.Movie;
import com.android.moviedb.ui.movie.DetailActivity;
import com.android.moviedb.utils.ImageSize;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import timber.log.Timber;

public class MoviesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public List<Movie> movies = new ArrayList<>();

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MovieViewHolder(parent);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MovieViewHolder) {
            MovieViewHolder movieViewHolder = (MovieViewHolder) holder;
            movieViewHolder.bind(movies.get(position));

        }
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class Holder extends RecyclerView.ViewHolder {

        public Holder(@LayoutRes int resId, ViewGroup parent) {
            super(LayoutInflater.from(parent.getContext()).inflate(resId, parent, false));

        }

    }

    public class MovieViewHolder extends Holder {

        @Bind(R.id.release_date)
        TextView releaseDate;
        @Bind(R.id.poster)
        ImageView posterView;

        public MovieViewHolder(final ViewGroup parent) {
            super(R.layout.poster_movie_view, parent);
            ButterKnife.bind(this, itemView);
        }


        public void bind(final Movie movie) {


            try {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
                Date date = dateFormat.parse(movie.releaseDate);
                SimpleDateFormat newFormat = new SimpleDateFormat("dd MMM yyyy");
                releaseDate.setText(newFormat.format(date));
            } catch (ParseException e) {
                e.printStackTrace();
            }


            Uri uri = Uri.parse(ServiceConfig.BASE_IMAGE_URL).buildUpon()
                    .appendPath(ImageSize.w185.getValue())
                    .appendPath(movie.posterPath.replace("/", ""))
                    .build();
            Picasso.with(itemView.getContext()).load(uri).into(posterView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DetailActivity.startThisActivity(itemView.getContext(), movie);
                }
            });
        }


    }
}
