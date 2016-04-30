package com.android.moviedb.ui.movies;

import android.os.Bundle;

import com.android.moviedb.BaseActivity;
import com.android.moviedb.R;

public class MainActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.content, MoviesFragment.newInstance()).commit();
        }
    }


}
