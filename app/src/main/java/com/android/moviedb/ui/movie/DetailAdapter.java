package com.android.moviedb.ui.movie;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.moviedb.R;
import com.android.moviedb.model.response.Trailer;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

public class DetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public List<Trailer> trailers = new ArrayList<>();

    public void setTrailers(List<Trailer> trailers) {
        this.trailers = trailers;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new DetailViewHolder(parent);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof DetailViewHolder) {
            DetailViewHolder trailerViewHolder = (DetailViewHolder) holder;
            trailerViewHolder.bind(trailers.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return trailers.size();
    }

    public class Holder extends RecyclerView.ViewHolder {

        public Holder(@LayoutRes int resId, ViewGroup parent) {
            super(LayoutInflater.from(parent.getContext()).inflate(resId, parent, false));

        }

    }

    public class DetailViewHolder extends Holder {


        public DetailViewHolder(final ViewGroup parent) {
            super(R.layout.poster_trailer_view, parent);
            ButterKnife.bind(this, itemView);
        }


        public void bind(final Trailer trailer) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v="+trailer.key));
                    itemView.getContext().startActivity(intent);
                }
            });
        }


    }
}
