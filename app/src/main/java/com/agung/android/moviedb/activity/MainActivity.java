package com.agung.android.moviedb.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Movie;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.design.internal.NavigationMenuView;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.agung.android.moviedb.R;
import com.agung.android.moviedb.adapter.MovieAdapter;
import com.agung.android.moviedb.api.ApiClient;
import com.agung.android.moviedb.model.nowPlayingResponse.NowPlayingResponse;
import com.agung.android.moviedb.model.nowPlayingResponse.ResultsItem;
import com.bumptech.glide.Glide;

import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.nav_view)
    NavigationView mNavView;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.drawer_layout)
    View rootLayout;
    @BindView(R.id.rv_home)
    RecyclerView mRecylerView;
    @BindView(R.id.iv_home_image)
    ImageView mImageHeader;
    @BindView(R.id.tv_header_title)
    TextView mHeaderTitle;

    private TextView mUsername;
    private TextView mEmail;

    public final static String API_KEY = "a66a817fc8a82a58172fad6b30e38aee";
    public final static String IMAGE_PATH = "https://image.tmdb.org/t/p/w500";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initToolbarNavigation();
        initView();
    }

    void initView() {
        Call<NowPlayingResponse> call = ApiClient.getService().getNowPlaying(API_KEY);
        call.enqueue(new Callback<NowPlayingResponse>() {
            @Override
            public void onResponse(Call<NowPlayingResponse> call, Response<NowPlayingResponse> response) {
                List<ResultsItem> movies = response.body().getResults();
                mRecylerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                mRecylerView.setAdapter(new MovieAdapter(movies, R.layout.row_home_list, getApplication()));
                sliderImages(movies);
            }

            @Override
            public void onFailure(Call<NowPlayingResponse> call, Throwable t) {

            }
        });
    }

    public void initToolbarNavigation() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        mNavView.setNavigationItemSelectedListener(this);
        mNavView.setItemIconTintList(null);

        NavigationMenuView navigationMenuView = (NavigationMenuView)
                mNavView.getChildAt(0);
        if (navigationMenuView != null) {
            navigationMenuView.setVerticalScrollBarEnabled(false);
        }
        View mHeaderview = mNavView.getHeaderView(0);
        mUsername = (TextView) mHeaderview.findViewById(R.id.tv_username);
        mEmail = (TextView) mHeaderview.findViewById(R.id.tv_email);

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_popular) {
            startActivity(new Intent(getApplicationContext(), PopularActivity.class));
            Toast.makeText(this, "Popular Clicked", Toast.LENGTH_SHORT).show();
        }
        else if (id == R.id.action_top_rate) {
            startActivity(new Intent(getApplicationContext(), TopRateActivity.class));
            Toast.makeText(this, "Top Rating Clicked", Toast.LENGTH_SHORT).show();
        }
        else if (id == R.id.action_coming_soon) {
            startActivity(new Intent(getApplicationContext(), UpComingActivity.class));
            Toast.makeText(this, "Comming Soon Clicked", Toast.LENGTH_SHORT).show();
        }
        else if (id == R.id.action_about) {
            Toast.makeText(this, "About Me Clicked", Toast.LENGTH_SHORT).show();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void sliderImages(final List<ResultsItem> movies){
        final Random random = new Random();
        ResultsItem movie = movies.get(random.nextInt(movies.size()));
        Glide.with(this).load(IMAGE_PATH + movie.getBackdropPath()).into(mImageHeader);
        mHeaderTitle.setText(movie.getTitle());
        final Context context = this;

        new CountDownTimer(5000, 1000){

            @Override
            public void onTick(long l) {

            }

            @Override
            public void onFinish() {
                ResultsItem movie = movies.get(random.nextInt(movies.size()));
                Glide.with(context).load(IMAGE_PATH
                        + movie.getBackdropPath()).into(mImageHeader);
                mHeaderTitle.setText(movie.getTitle());
                start();
            }
        }.start();
    }


}
