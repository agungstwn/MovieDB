package com.agung.android.moviedb.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.agung.android.moviedb.R;
import com.agung.android.moviedb.adapter.PopularAdapter;
import com.agung.android.moviedb.api.ApiClient;
import com.agung.android.moviedb.model.popularResponse.PopularResponse;
import com.agung.android.moviedb.model.popularResponse.ResultsItem;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.agung.android.moviedb.activity.MainActivity.API_KEY;

public class PopularActivity extends AppCompatActivity {

    @BindView(R.id.toolbar_popular)
    Toolbar mToolbar;
    @BindView(R.id.recycler_view)
    RecyclerView mRecylerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popular);
        ButterKnife.bind(this);
        initToolbar();
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
        Call<PopularResponse> call = ApiClient.getService().getPopularRensponse(API_KEY);
        call.enqueue(new Callback<PopularResponse>() {
            @Override
            public void onResponse(Call<PopularResponse> call, Response<PopularResponse> response) {
                List<ResultsItem> movies = response.body().getResults();
                mRecylerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                mRecylerView.setAdapter(new PopularAdapter
                        (movies, R.layout.row_home_list, getApplicationContext()));
            }

            @Override
            public void onFailure(Call<PopularResponse> call, Throwable t) {

            }

        });
    }




}
