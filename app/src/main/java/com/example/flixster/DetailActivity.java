package com.example.flixster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.flixster.models.Movie;

import org.parceler.Parcels;

public class DetailActivity extends AppCompatActivity {

    TextView tvTitle;
    TextView tvOverview;
    RatingBar ratingBar;
    ImageView backdropPoster;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        tvTitle = findViewById(R.id.tvTitle2);
        tvOverview = findViewById(R.id.tvOverview2);
        ratingBar = findViewById(R.id.ratingBar2);
        backdropPoster = findViewById(R.id.backdrop_poster);

        Movie movie = Parcels.unwrap(getIntent().getParcelableExtra("movie"));
        tvTitle.setText(movie.getTitle());
        tvOverview.setText(movie.getOverview());
        ratingBar.setRating((float)movie.getRating());
        String imageUrl;

        if (getApplicationContext().getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            imageUrl = movie.getPosterPath();
        } else {
            imageUrl = movie.getBackdropPath();
        }

        Glide.with(getApplicationContext()).load(imageUrl).into(backdropPoster);
    }
}