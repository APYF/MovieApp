package com.example.moviesapp;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import java.util.List;

public class MainActivity extends AppCompatActivity implements MovieAdapter.MovieAdapterOnClickHandler {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String POPULAR_MOVIES = "popular";
    private static final String TOP_RATED_MOVIES = "top_rated";

    private RecyclerView mRecyclerView;
    private MovieAdapter mMovieAdapter;

    private ProgressBar mLoadingIndicator;

    private String listViewState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_movie);

        GridLayoutManager layoutManager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);

        mRecyclerView.setLayoutManager(layoutManager);

        mRecyclerView.setHasFixedSize(true);
        mMovieAdapter = new MovieAdapter(this);
        mRecyclerView.setAdapter(mMovieAdapter);

        mLoadingIndicator = (ProgressBar) findViewById(R.id.progressBar);
        setTitle(R.string.popular);
        loadMovies(POPULAR_MOVIES);
    }

    public void loadMovies(String movieListType) {
        showMovieRecyclerView();
        new FetchWeatherTask().execute(movieListType);

    }

    public class FetchWeatherTask extends AsyncTask<String, Void, List<Movie>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadingIndicator.setVisibility(View.VISIBLE);
        }

        @Override
        protected List<Movie> doInBackground(String... params) {
            List<Movie> movieList;

            if (params.length == 0) {
                return null;
            }

            String movieListType = params[0];
            try {
                movieList = NetworkUtils.fetchMovies(movieListType);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }

            return movieList;

        }

        @Override
        protected void onPostExecute(List<Movie> movieList) {
            mLoadingIndicator.setVisibility(View.INVISIBLE);
            if (movieList != null) {
                mMovieAdapter.setMovieData(movieList);
            } else {
            }
        }
    }

    @Override
    public void onClick(Movie movie) {
        Context context = this;
        Class destinationClass = DetailActivity.class;
        Intent intentToStartDetailActivity = new Intent(context, destinationClass);
        intentToStartDetailActivity.putExtra("parcel_data", movie);
        startActivity(intentToStartDetailActivity);
    }

    public void showMovieRecyclerView() {
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        item.setChecked(true);
        if (id == R.id.action_popular) {
            setTitle(R.string.popular);
            listViewState = POPULAR_MOVIES;
            loadMovies(listViewState);
            return true;
        } else if (id == R.id.action_top_rated) {
            setTitle(R.string.top_rated);
            listViewState = TOP_RATED_MOVIES;
            loadMovies(listViewState);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
