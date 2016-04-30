package com.android.moviedb.model.response;

import java.io.Serializable;

public class Movie implements Serializable {
    public final String posterPath;
    public final String backdropPath;
    public final String overview;
    public final String releaseDate;
    public final long id;
    public final String originalTitle;
    public final String title;
    public final double popularity;
    public final int voteCount;
    public final double voteAverage;

    public Movie(String posterPath, String backdropPath,String overview, String releaseDate, long id,
                 String originalTitle, String title, double popularity, int voteCount,
                 int voteAverage) {
        this.posterPath = posterPath;
        this.backdropPath = backdropPath;
        this.overview = overview;
        this.releaseDate = releaseDate;
        this.id = id;
        this.originalTitle = originalTitle;
        this.title = title;
        this.popularity = popularity;
        this.voteCount = voteCount;
        this.voteAverage = voteAverage;
    }
}
