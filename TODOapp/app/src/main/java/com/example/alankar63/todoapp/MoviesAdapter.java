
package com.example.alankar63.todoapp;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MyViewHolder> {

    private List<Movie> moviesList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, date_time, pending;

        public MyViewHolder(View view) {
            super(view);

            title = (TextView) view.findViewById(R.id.title);
            pending = (TextView) view.findViewById(R.id.pending);
            date_time = (TextView) view.findViewById(R.id.date_time);
        }
    }


    public MoviesAdapter(List<Movie> moviesList) {
        this.moviesList = moviesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Movie movie = moviesList.get(position);
        holder.title.setText(movie.getTitle());
        holder.pending.setText(movie.getpending());
        holder.date_time.setText(movie.getdate_time());
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }
}