package com.agung.android.moviedb.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;

import com.agung.android.moviedb.R;
import com.agung.android.moviedb.adapter.MovieAdapter;
import com.agung.android.moviedb.api.ApiClient;
import com.agung.android.moviedb.model.MovieResponse;
import com.agung.android.moviedb.model.ResultsItem;
import com.agung.android.moviedb.utils.constant;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class UpComingActivity extends AppCompatActivity {
    @BindView(R.id.toolbar_upcoming)
    Toolbar mToolbar;
    @BindView(R.id.recycler_view)
    RecyclerView mRecylcerView;
    @BindView(R.id.progress_bar)
    ProgressBar mProgressbar;
    @BindView(R.id.detail_movie_refresh)
    SwipeRefreshLayout mRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_up_coming);
        ButterKnife.bind(this);
        initRefresh();
        initView();
        initToolbar();
    }

    private void initRefresh() {
        mRefresh.setColorSchemeColors(Color.RED, Color.YELLOW, Color.GREEN, Color.BLUE);
        mRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getDetailMovies();
            }
        });
    }

    private void getDetailMovies(){
        mProgressbar.setVisibility(View.VISIBLE);
        mRecylcerView.setVisibility(View.GONE);
        mRefresh.setRefreshing(false);
        initView();
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
        Call<MovieResponse> call = ApiClient.getService().getUpcomingResponse(constant.Api.API_KEY);
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                mProgressbar.setVisibility(View.GONE);
                mRecylcerView.setVisibility(View.VISIBLE);

                List<ResultsItem> movies = response.body().getResults();
                mRecylcerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                mRecylcerView.setAdapter(new MovieAdapter
                        (movies, R.layout.row_home_list, UpComingActivity.this));
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                mProgressbar.setVisibility(View.GONE);
            }
        });
    }
}
