package com.agung.android.moviedb.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.agung.android.moviedb.R;
import com.agung.android.moviedb.model.creditsResponse.CastItem;
import com.agung.android.moviedb.model.creditsResponse.CastsResponse;
import com.agung.android.moviedb.utils.constant;
import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by agung on 26/01/18.
 */

public class CastAdapter extends RecyclerView.Adapter<CastAdapter.ViewHolder>{
    private List<CastItem> casts;
    private Context context;

    public CastAdapter(List<CastItem> casts, Context context) {
        this.casts = casts;
        this.context = context;
    }

    @Override
    public CastAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_cats, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CastAdapter.ViewHolder holder, int position) {
        CastItem cast = casts.get(position);
        holder.mCharacter.setText(casts.get(position).getCharacter());
        holder.mNamaCast.setText(casts.get(position).getName());
        Glide.with(context).load(constant.Api.IMAGE_PATH
                +cast.getProfilePath()).into(holder.mImageCast);
    }

    @Override
    public int getItemCount() {
        return casts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.cast_character)
        TextView mCharacter;
        @BindView(R.id.cast_name)
        TextView mNamaCast;
        @BindView(R.id.cast_image)
        ImageView mImageCast;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
