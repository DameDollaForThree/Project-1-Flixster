package com.example.flixster.adapters;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.flixster.R;
import com.example.flixster.models.Movie;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    Context context;
    List<Movie> movies;

    public MovieAdapter(Context context, List<Movie> movies) {
        this.context = context;
        this.movies = movies;
    }

    @Override
    public int getItemViewType(int position) {
        Movie curr_movie = movies.get(position);
        if (curr_movie.getRating() >= 8) {
            return 1;
        } else {
            return 2;
        }
    }

    /*
     * Usually involves inflating a layout from XML and returning the holder
     */
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("MovieAdapter", "onCreateViewHolder");

        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(context);

        switch (viewType) {
            case 1:
                View v1 = inflater.inflate(R.layout.item_backdrop, parent, false);
                viewHolder = new ViewHolder2(v1);
                break;
            case 2:
                View v2 = inflater.inflate(R.layout.item_movie, parent, false);
                viewHolder = new ViewHolder1(v2);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + viewType);
        }
        return viewHolder;
    }

    /*
     * Involves populating data into the item through holder
     */
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Log.d("MovieAdapter", "onBindViewHolder" + position);
        // get the movie at the passed in position
        Movie movie = movies.get(position);

        switch (holder.getItemViewType()){
            case 1:
                ViewHolder2 vh2 = (ViewHolder2) holder;
                vh2.bind(movie);
                break;
            case 2:
                ViewHolder1 vh1 = (ViewHolder1) holder;
                vh1.bind(movie);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + holder.getItemViewType());
        }

    }

    @Override
    public int getItemCount() {
        return movies.size();
    }


    // big backdrop image with 5 star rating
    public class ViewHolder2 extends RecyclerView.ViewHolder {
        ImageView poster;

        public ViewHolder2(@NonNull View itemView) {
            super(itemView);
            poster = itemView.findViewById(R.id.backdrop);
        }

        public void bind(Movie movie) {
            String imageUrl;
            imageUrl = movie.getBackdropPath();
            Glide.with(context).load(imageUrl).into(poster);
        }
    }

    //
    public class ViewHolder1 extends RecyclerView.ViewHolder {

        TextView tvTitle;
        TextView tvOverview;
        ImageView ivPoster;
        RatingBar rating;

        public ViewHolder1(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvOverview = itemView.findViewById(R.id.tvOverview);
            ivPoster = itemView.findViewById(R.id.ivPoster);
            rating = itemView.findViewById(R.id.ratingBar);
            Drawable drawable = rating.getProgressDrawable();
            drawable.setColorFilter(Color.parseColor("#FFD700"), PorterDuff.Mode.SRC_ATOP);
        }

        public void bind(Movie movie) {
            tvTitle.setText(movie.getTitle());
            tvOverview.setText(movie.getOverview());
            String imageUrl;
            // if phone is in landscape, then imageUrl = back drop image
            // else imageUrl = poster image
            if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                imageUrl = movie.getBackdropPath();
            } else {
                imageUrl = movie.getPosterPath();
            }

            Glide.with(context).load(imageUrl).into(ivPoster);

            // process rating
            if (movie.getRating() <= 10 && movie.getRating() >= 8) {
                rating.setNumStars(5);
            }
            else if (movie.getRating() < 8 && movie.getRating() >= 6) {
                rating.setNumStars(4);
            }
            else if (movie.getRating() < 6 && movie.getRating() >= 4) {
                rating.setNumStars(3);
            }
            else if (movie.getRating() < 4 && movie.getRating() >= 2) {
                rating.setNumStars(2);
            }
            else if (movie.getRating() < 2 && movie.getRating() >= 0) {
                rating.setNumStars(1);
            }
        }
    }
}
