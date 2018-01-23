package com.agung.android.moviedb.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.agung.android.moviedb.R;
import com.agung.android.moviedb.adapter.UpComingAdapter;
import com.agung.android.moviedb.api.ApiClient;
import com.agung.android.moviedb.model.upComingResponse.ResultsItem;
import com.agung.android.moviedb.model.upComingResponse.UpComingResponse;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.agung.android.moviedb.activity.MainActivity.API_KEY;

public class UpComingActivity extends AppCompatActivity {
    @BindView(R.id.toolbar_upcoming)
    Toolbar mToolbar;
    @BindView(R.id.recycler_view)
    RecyclerView mRecylcerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_up_coming);
        ButterKnife.bind(this);

        initView();
        initToolbar();
    }

    private void initToolbar() {
        mToolbar.setTitle("");
        mToolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private void initView() {
        Call<UpComingResponse> call = ApiClient.getService().getUpcomingResponse(API_KEY);
        call.enqueue(new Callback<UpComingResponse>() {
            @Override
            public void onResponse(Call<UpComingResponse> call, Response<UpComingResponse> response) {
                List<ResultsItem> movies = response.body().getResults();
                mRecylcerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                mRecylcerView.setAdapter(new UpComingAdapter
                        (movies, R.layout.row_home_list, getApplicationContext()));
            }

            @Override
            public void onFailure(Call<UpComingResponse> call, Throwable t) {

            }
        });
    }
}
