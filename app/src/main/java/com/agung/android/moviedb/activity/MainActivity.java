package com.agung.android.moviedb.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.design.internal.NavigationMenuView;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.agung.android.moviedb.R;
import com.agung.android.moviedb.adapter.MovieAdapter;
import com.agung.android.moviedb.api.ApiClient;
import com.agung.android.moviedb.model.MovieResponse;
import com.agung.android.moviedb.model.ResultsItem;
import com.agung.android.moviedb.utils.Function;
import com.agung.android.moviedb.utils.constant;
import com.bumptech.glide.Glide;

import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Optional;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.agung.android.moviedb.utils.constant.Api.API_KEY;
import static com.agung.android.moviedb.utils.constant.Api.GOOGLE_DEV_IMAGE_URL;

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
    @BindView(R.id.ll_home)
    LinearLayout mLLHome;
    @BindView(R.id.detail_movie_refresh)
    SwipeRefreshLayout mRefresh;
    @BindView(R.id.ll_error)
    LinearLayout mLayoutError;

    private TextView mUsername;
    private TextView mEmail;
    private Context context = this;
    private ProgressDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initToolbarNavigation();
        initRefresh();
        initView();
        dialog = ProgressDialog.show(this, "", "Tunggu Sebentar", true);
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
        dialog.show();
        mLLHome.setVisibility(View.GONE);
        mRefresh.setRefreshing(false);
        initView();
    }


    @Override
    public void onBackPressed() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Apakah anda yakin ingin keluar?");
        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                System.exit(0);
            }
        });
        alert.setNegativeButton("Batal",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                    }
                });

        alert.show();
    }

    void initView() {
        mLayoutError.setVisibility(View.GONE);
        Call<MovieResponse> call = ApiClient.getService().getNowPlaying(API_KEY);
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse>
                                           call, Response<MovieResponse> response) {
                mLLHome.setVisibility(View.VISIBLE);
                dialog.dismiss();

                List<ResultsItem> movies = response.body().getResults();

                mRecylerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                mRecylerView.setAdapter(new MovieAdapter
                        (movies, R.layout.row_home_list, MainActivity.this));
                sliderImages(movies);
                ImageView mAndroidKejar = findViewById(R.id.android_kejar_img);
                ImageView mGoogleDev = findViewById(R.id.google_dev);
                Function.setImage(context, constant.Api.ANDROID_KEJAR_IMAGE_URL, mAndroidKejar);
                Function.setImage(context, constant.Api.GOOGLE_DEV_IMAGE_URL, mGoogleDev);
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                mLayoutError.setVisibility(View.VISIBLE);
                dialog.dismiss();
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
        } else if (id == R.id.action_top_rate) {
            startActivity(new Intent(getApplicationContext(), TopRateActivity.class));
        } else if (id == R.id.action_coming_soon) {
            startActivity(new Intent(getApplicationContext(), UpComingActivity.class));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void sliderImages(final List<ResultsItem> movies) {
        final Random random = new Random();
        ResultsItem movie = movies.get(random.nextInt(movies.size()));
        Glide.with(this).load(constant.Api.IMAGE_PATH + movie.getBackdropPath()).into(mImageHeader);
        mHeaderTitle.setText(movie.getTitle());
        final Context context = this;
        new CountDownTimer(10000, 10000) {

            @Override
            public void onTick(long l) {

            }

            @Override
            public void onFinish() {
                ResultsItem movie = movies.get(random.nextInt(movies.size()));
                Glide.with(context).load(constant.Api.IMAGE_PATH
                        + movie.getBackdropPath()).into(mImageHeader);
                mHeaderTitle.setText(movie.getTitle());
                start();
            }
        }.start();
    }

}
