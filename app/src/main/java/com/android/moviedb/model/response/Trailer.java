package com.android.moviedb.model.response;

import java.io.Serializable;

public class Trailer implements Serializable {
    public final String id;
    public final String iso_639_1;
    public final String iso_3166_1;
    public final String key;
    public final String name;
    public final String site;
    public final long size;
    public final String type;

    public Trailer(String id, String iso_639_1, String iso_3166_1, String key, String name, String site, long size, String type) {
        this.id = id;
        this.iso_639_1 = iso_639_1;
        this.iso_3166_1 = iso_3166_1;
        this.key = key;
        this.name = name;
        this.site = site;
        this.size = size;
        this.type = type;
    }


}
