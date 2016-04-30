package com.android.moviedb.ui.favorite;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.android.moviedb.BaseActivity;
import com.android.moviedb.R;
import com.android.moviedb.model.response.Movie;

public class FavoriteActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.content, FavoriteFragment.newInstance()).commit();
        }
    }


    public static void startThisActivity(Context context) {
        Intent intent = new Intent(context, FavoriteActivity.class);
        context.startActivity(intent);
    }

    public void onRestart() {
        super.onRestart();
        new Handler().post(new Runnable() {
            public void run() {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.content, FavoriteFragment.newInstance()).commit();
            }
        });
    }


}
