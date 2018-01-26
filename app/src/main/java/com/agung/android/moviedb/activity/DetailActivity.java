package com.agung.android.moviedb.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.agung.android.moviedb.R;
import com.agung.android.moviedb.api.ApiClient;
import com.agung.android.moviedb.model.MovieResponse;
import com.agung.android.moviedb.model.detailResponse.DetailMovieResponse;
import com.agung.android.moviedb.model.detailResponse.GenresItem;
import com.agung.android.moviedb.presenter.PresenterDetail;
import com.agung.android.moviedb.utils.constant;
import com.agung.android.moviedb.view.ViewDetail;
import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DetailActivity extends AppCompatActivity implements ViewDetail {

    @BindView(R.id.iv_header_image)
    ImageView mHeaderImage;
    @BindView(R.id.iv_poster)
    ImageView mPoster;
    @BindView(R.id.tv_description)
    TextView mDescription;
    @BindView(R.id.tv_detail_movie_genre)
    TextView mGenre;
    @BindView(R.id.tv_detail_movie_language)
    TextView mSubtitle;
    @BindView(R.id.tv_detail_movie_rating)
    TextView mRating;
    @BindView(R.id.tv_detail_movie_releasedate)
    TextView mReleaseDate;
    @BindView(R.id.tv_detail_movie_runtime)
    TextView mRuntime;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    private int id;
    private DetailMovieResponse movie;
    private PresenterDetail presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
        presenter = new PresenterDetail();
        presenter.setView(this);
        id = getIntent().getIntExtra("id", 1);
        presenter.loadDetail(id);
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


    @Override
    public void onLoadData(final DetailMovieResponse movie) {
        this.movie = movie;
        mToolbar.setTitle(movie.getTitle());
        Glide.with(this).load(constant.Api.IMAGE_PATH
                + movie.getBackdropPath()).into(mHeaderImage);
        Glide.with(this).load(constant.Api.IMAGE_PATH
                + movie.getPosterPath()).into(mPoster);
        mDescription.setText(movie.getOverview());
        String genres = "";
        for (GenresItem genre : movie.getGenres()) {
            if ("".equals(genres)) genres = genre.getName();
            else genres += ", " + genre.getName();
        }
        mGenre.setText(genres);
        mSubtitle.setText(movie.getSpokenLanguages().get(0).getIso6391()
                + " _ " + movie.getSpokenLanguages().get(0).getName());
        mRating.setText(movie.getVoteAverage() + " / 10");
        mReleaseDate.setText(movie.getReleaseDate());
        int hour = 0;
        int minute = 0;
        String durationString = "";
        if (movie.getRuntime() > 60) {
            hour = movie.getRuntime() / 60;
            minute = movie.getRuntime() - (60 * hour);
            durationString = hour + "h " + minute + " m";
        } else {
            durationString = minute + "m";
        }
        mRuntime.setText(durationString);
    }

    @Override
    public void onError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT);
    }
}
