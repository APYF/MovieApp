package com.example.moviesapp;

import android.content.Context;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieAdapterViewHolder> {
    private static final String TAG = MovieAdapter.class.getSimpleName();

    private static final String MOVIE_POSTER_BASE_URL = "https://image.tmdb.org/t/p/w500/";

    private List<Movie> mMovies;

    private final MovieAdapterOnClickHandler mClickHandler;

    /**
     * The interface that receives onClick messages
     */
    public interface MovieAdapterOnClickHandler {


        void onClick(Movie selectedMovie);

    }

    /**
     * Creates a Movie Adapter.
     *
     * @param clickHandler
     */
    public MovieAdapter(MovieAdapterOnClickHandler clickHandler) {
        mClickHandler = clickHandler;
    }

    /**
     * Cache of the children view for the movie list item
     */
    public class MovieAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final ImageView mMoviePosterImageView;

        public MovieAdapterViewHolder(View view) {
            super(view);
            mMoviePosterImageView = (ImageView) view.findViewById(R.id.iv_movie_poster);
            view.setOnClickListener(this);
        }

        /**
         * This gets called by the child views during a click.
         * @param v the view that was clicked
         */
        @Override
        public void onClick(View v) {
            int adapterPostion = getAdapterPosition();
            Movie selectedMovie = mMovies.get(adapterPostion);
            mClickHandler.onClick(selectedMovie);
        }
    }

    @Override
    public MovieAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType)
    {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.movie_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);

        return new MovieAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieAdapterViewHolder movieAdapterViewHolder, int position) {
        Movie selectedMovie = mMovies.get(position);
        String moviePosterPath = MOVIE_POSTER_BASE_URL+selectedMovie.getPosterURL();
        Picasso.get().load(moviePosterPath).into(movieAdapterViewHolder.mMoviePosterImageView, new Callback() {
            @Override
            public void onSuccess() {
            }

            @Override
            public void onError(Exception e) {
                Log.e(TAG, "Picasso load movie poster onError: ", e);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (mMovies == null)
            return 0;
        return mMovies.size();
    }

    public void setMovieData(List<Movie> movies) {
        mMovies = movies;
        notifyDataSetChanged();
    }

}
