package com.android.moviedb.utils;

public enum ImageSize {
    w185(185, "w185"),
    w342(342, "w342"),
    w500(500, "w500");

    private int key;
    private String value;

    ImageSize(int key, String value) {
        this.key = key;
        this.value = value;
    }

    public int getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }
}
