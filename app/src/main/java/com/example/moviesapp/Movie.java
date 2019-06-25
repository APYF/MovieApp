package com.example.moviesapp;

import android.os.Parcel;
import android.os.Parcelable;

public class Movie implements Parcelable {

    private Double mId;
    private String mTitle;
    private String mPosterURL;
    private Double mRating;
    private String mOverview;
    private String mReleaseDate;

    public Movie(Double Id, String Title, String PosterURL, Double Rating, String Overview, String ReleaseDate) {
        mId = Id;
        mTitle = Title;
        mPosterURL = PosterURL;
        mRating = Rating;
        mOverview = Overview;
        mReleaseDate = ReleaseDate;
    }

    public Movie(Parcel in) {
        mId = in.readDouble();
        mTitle = in.readString();
        mPosterURL = in.readString();
        mRating = in.readDouble();
        mOverview = in.readString();
        mReleaseDate = in.readString();

    }

    public Double getId() {
        return mId;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getPosterURL() {
        return mPosterURL;
    }

    public Double getRating() {
        return mRating;
    }

    public String getOverView() {
        return mOverview;
    }

    public String getReleaseDate() {
        return mReleaseDate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(mId);
        dest.writeString(mTitle);
        dest.writeString(mPosterURL);
        dest.writeDouble(mRating);
        dest.writeString(mOverview);
        dest.writeString(mReleaseDate);
    }

    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

}