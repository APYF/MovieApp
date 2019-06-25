package com.example.moviesapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DetailActivity extends AppCompatActivity {
    private static final String TAG = DetailActivity.class.getSimpleName();
    private static final String MOVIE_POSTER_BASE_URL = "https://image.tmdb.org/t/p/w500/";

    private TextView mTitleTV;
    private TextView mReleaseDateTV;
    private TextView mSynoposisTV;
    private ImageView mPosterIV;
    private TextView mVoteAverageTV;

    private Movie mMovie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        setTitle(R.string.title_movie_detail);

        mTitleTV = (TextView) findViewById(R.id.tv_title);
        mPosterIV = (ImageView) findViewById(R.id.iv_detail_movie_poster);
        mVoteAverageTV = (TextView) findViewById(R.id.tv_movie_vote_average);
        mReleaseDateTV = (TextView) findViewById(R.id.tv_release_date);
        mSynoposisTV = (TextView) findViewById(R.id.tv_movie_plot_synopsis);

        Intent intentThatStartedThisActivity = getIntent();

        if (intentThatStartedThisActivity != null) {
            if (intentThatStartedThisActivity.hasExtra("parcel_data")) {
                mMovie = (Movie) intentThatStartedThisActivity.getParcelableExtra("parcel_data");
                mTitleTV.setText(mMovie.getTitle());
                Picasso.get().load(MOVIE_POSTER_BASE_URL + mMovie.getPosterURL()).into(mPosterIV, new Callback() {
                    @Override
                    public void onSuccess() {
                    }

                    @Override
                    public void onError(Exception e) {
                        Log.e(TAG, "Picasso load movie poster onError: ", e);
                    }
                });
                String releaseDate = mMovie.getReleaseDate();
                try {
                    Date date = new SimpleDateFormat("yy-MM-dd").parse(releaseDate);
                    releaseDate = new SimpleDateFormat("yyyy").format(date);
                } catch (ParseException e) {
                    Log.e(TAG, "Parse release date exception: ", e);
                }
                mReleaseDateTV.setText(releaseDate);
                String ratingText = mMovie.getRating().toString() + "/10";
                mVoteAverageTV.setText(ratingText);
                mSynoposisTV.setText(mMovie.getOverView());
            }
        }
    }
}
