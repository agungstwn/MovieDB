package com.agung.android.moviedb.adapter;

import android.content.Context;
import android.gesture.GestureLibraries;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.agung.android.moviedb.R;
import com.agung.android.moviedb.model.creditsResponse.CrewItem;
import com.agung.android.moviedb.utils.constant;
import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by agung on 29/01/18.
 */

public class CrewAdapter extends RecyclerView.Adapter<CrewAdapter.ViewHolder> {
    private List<CrewItem> crews;
    private Context context;

    public CrewAdapter(List<CrewItem> crews, Context context) {
        this.crews = crews;
        this.context = context;
    }

    @Override
    public CrewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_crews, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CrewAdapter.ViewHolder holder, int position) {
        CrewItem crew = crews.get(position);
        holder.mCrewName.setText(crews.get(position).getName());
        holder.mCrewJob.setText(crews.get(position).getJob());
        Glide.with(context).load(constant.Api.IMAGE_PATH
                + crew.getProfilePath()).into(holder.mCrewImage);
    }

    @Override
    public int getItemCount() {
        return crews.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.crew_name)
        TextView mCrewName;
        @BindView(R.id.crew_job)
        TextView mCrewJob;
        @BindView(R.id.crew_image)
        ImageView mCrewImage;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
