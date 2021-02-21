package com.codepath.flixster.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.codepath.flixster.DetailActivity;
import com.codepath.flixster.R;
import com.codepath.flixster.models_.Movie;

import org.parceler.Parcels;

import java.util.List;

public class MovieAdapter extends  RecyclerView.Adapter<MovieAdapter.Viewholder>{

    Context context;
    List<Movie> movies;

    public MovieAdapter(Context context, List<Movie> movies) {
        this.context = context;
        this.movies = movies;
    }

    // Usually involves inflating a layout from XML and returning the holder..
    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("MovieAdapter","onCreateViewHolder");
        View movieView = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false);
        return new Viewholder(movieView);
    }

    //Involves populating data into the item through holder.
    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        Log.d("MovieAdapter","onBindViewHolder" + position);
        // Get the movie at the passedd in position
        Movie movie = movies.get(position);
        // Bind the movie data into the VH
        holder.bind(movie);
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {

        RelativeLayout container;
        TextView tvTitle;
        TextView tvOverview;
        ImageView ivPoster;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvOverview = itemView.findViewById(R.id.tvOverview);
            ivPoster = itemView.findViewById(R.id.ivPoster);
            container = itemView.findViewById(R.id.container);
        }

        public void bind(final Movie movie) {
            tvTitle.setText(movie.getTitle());
            tvOverview.setText(movie.getOverview());
            String imageUrl;
            // if phone in landscape -> imageUrl = backdrop image
            // else imageUrl = poster image.

            if(context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                imageUrl = movie.getBackdropPath();
            } else {
                imageUrl = movie.getPosterPath();
            }

            Glide.with(context).load(imageUrl).into(ivPoster);

            // Part2++
            // 1. Register click listener on the whole row

            container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 2. Navigate to a 'new activity' on the whole row.
                    //--Toast.makeText(context, movie.getTitle(), Toast.LENGTH_SHORT).show(); --> displays title
                    Intent i = new Intent(context, DetailActivity.class);
                    //turning movie into a parcelable value to access movie object.
                    i.putExtra("movie", Parcels.wrap(movie));
                    context.startActivity(i);
                }
            });
        }
    }
}   //OnBindViewholder more efficient than Oncreateviewholder (less created views)..
