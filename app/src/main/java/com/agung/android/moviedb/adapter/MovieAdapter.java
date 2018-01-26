package com.agung.android.moviedb.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.agung.android.moviedb.R;
import com.agung.android.moviedb.activity.DetailActivity;
import com.agung.android.moviedb.model.ResultsItem;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.content.ContentValues.TAG;

/**
 * Created by agung on 22/01/18.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private List<ResultsItem> movies;
    private int rowLayout;
    private Context context;

    public MovieAdapter(List<ResultsItem> movies, int rowLayout, Context context) {
        this.movies = movies;
        this.rowLayout = rowLayout;
        this.context = context;
    }

    @Override
    public MovieAdapter.MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MovieAdapter.MovieViewHolder holder, final int position) {
        holder.mTitle.setText(movies.get(position).getTitle());
        holder.mSubTitle.setText(movies.get(position).getReleaseDate());
        holder.mDescription.setText(movies.get(position).getOverview());
        Picasso.with(context).load("https://image.tmdb.org/t/p/w185_and_h278_bestv2"
                + movies.get(position).getPosterPath()).into(holder.mPoster);
        Log.d(TAG, "onBindViewHolder: " + movies.get(position).getPosterPath());

        holder.mListLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, ""+movies.get(position).getId(), Toast.LENGTH_SHORT).show();
                context.startActivity(new Intent(context, DetailActivity.class)
                .putExtra("id", movies.get(position).getId()));
            }
        });
    }

    public void onDetail(int id) {
        Intent mDetail = new Intent(context, DetailActivity.class);
        context.startActivity(mDetail);
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_title)
        TextView mTitle;
        @BindView(R.id.tv_subtitle)
        TextView mSubTitle;
        @BindView(R.id.tv_description)
        TextView mDescription;
        @BindView(R.id.iv_poster)
        ImageView mPoster;
        @BindView(R.id.cv_row_movie)
        CardView mListLayout;

        public MovieViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
