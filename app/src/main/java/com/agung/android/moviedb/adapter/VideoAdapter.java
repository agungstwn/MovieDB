package com.agung.android.moviedb.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.agung.android.moviedb.R;
import com.agung.android.moviedb.api.ApiClient;
import com.agung.android.moviedb.model.videoResponse.VideosItem;
import com.agung.android.moviedb.utils.Function;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by agung on 31/01/18.
 */

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.ViewHolder> {
    private List<VideosItem> videos;
    private Context context;

    public VideoAdapter(List<VideosItem> videos, Context context) {
        this.videos = videos;
        this.context = context;
    }

    @Override
    public VideoAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate
                (R.layout.row_trailers, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final VideoAdapter.ViewHolder holder, int position) {
        Function.setImage(context, ApiClient.getUrlYoutubeImage
                (videos.get(position).getKey()), holder.mTrailers);
        holder.mNameTrailers.setText(videos.get(position).getName());
        holder.mSourceTrailers.setText(videos.get(position).getSite());
        holder.mLayoutCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String trailerUrl = ApiClient.getYoutubeLink(videos.get(holder.getAdapterPosition()).getKey());
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(trailerUrl));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return videos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.cv_detail_trailer_layout)
        CardView mLayoutCardView;
        @BindView(R.id.iv_detail_trailer_pic)
        ImageView mTrailers;
        @BindView(R.id.tv_detail_trailer_name)
        TextView mNameTrailers;
        @BindView(R.id.tv_detail_trailer_source)
        TextView mSourceTrailers;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
