package com.android.moviedb.model.response;

import java.util.List;

public class BaseApi<T> {
    public final List<T> results;

    public BaseApi(int page, List<T> results) {
        this.results = results;
    }
}
