package com.agung.android.moviedb.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.agung.android.moviedb.R;
import com.agung.android.moviedb.adapter.CastAdapter;
import com.agung.android.moviedb.adapter.CrewAdapter;
import com.agung.android.moviedb.adapter.ReviewAdapter;
import com.agung.android.moviedb.adapter.VideoAdapter;
import com.agung.android.moviedb.model.creditsResponse.CastItem;
import com.agung.android.moviedb.model.creditsResponse.CastsResponse;
import com.agung.android.moviedb.model.creditsResponse.CrewItem;
import com.agung.android.moviedb.model.detailResponse.DetailMovieResponse;
import com.agung.android.moviedb.model.detailResponse.GenresItem;
import com.agung.android.moviedb.model.reviewResponse.ResultsItem;
import com.agung.android.moviedb.model.videoResponse.VideosItem;
import com.agung.android.moviedb.presenter.PresenterDetail;
import com.agung.android.moviedb.utils.constant;
import com.agung.android.moviedb.view.ViewDetail;
import com.bumptech.glide.Glide;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


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
    @BindView(R.id.detail_title)
    TextView mDetailTitle;
    @BindView(R.id.pb_loading)
    ProgressBar mLoading;
    @BindView(R.id.ll_detail_movie_layout)
    LinearLayout mLayoutDetal;
    @BindView(R.id.cast)
    RecyclerView castList;
    @BindView(R.id.crew)
    RecyclerView crewList;
    @BindView(R.id.review)
    RecyclerView reviewList;
    @BindView(R.id.trailer)
    RecyclerView videoList;

    private int id;
    private DetailMovieResponse movie;
    private PresenterDetail presenter;
    private CastAdapter castAdapter;
    private CrewAdapter crewAdapter;
    private ReviewAdapter reviewAdapter;
    private VideoAdapter videoAdapter;

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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.share_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_share:
                Intent intentShare = new Intent();
                intentShare.setAction(Intent.ACTION_SEND);
                intentShare.putExtra(Intent.EXTRA_TEXT, onShare());
                intentShare.setType("text/plain");
                startActivity(intentShare);
                break;
        }
        return true;
    }

    private String onShare() {
        String content = "[MovieDB - My Movie List]\n";
        content += "Check this out!!\n" + movie.getTitle() + " ";
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date;
        try {
            date = format.parse(movie.getReleaseDate());
            if (isPlaying(date)) {
                content += "\n This movie will be release on " + movie.getReleaseDate();
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        content += "\n\nyou can check my Github if you wont to see my source code : \n" +
                constant.Api.GITHUB;
        return content;
    }

    private boolean isPlaying(Date date) {
        return GregorianCalendar.getInstance().getTime()
                .before(date);
    }

    @Override
    public void onLoadData(final DetailMovieResponse movie, final List<CastItem> casts,
                           final List<CrewItem> crews, final List<ResultsItem>review, final List<VideosItem> video) {
        this.movie = movie;
        mDetailTitle.setText(movie.getTitle());
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

        if (castAdapter == null) castAdapter = new CastAdapter(casts, this);
        castList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,
                false));
        castList.setHasFixedSize(true);
        castList.setAdapter(castAdapter);

        if (crewAdapter == null) crewAdapter = new CrewAdapter(crews, this);
        crewList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,
                false));
        crewList.setHasFixedSize(true);
        crewList.setAdapter(crewAdapter);

        if (reviewAdapter == null) reviewAdapter = new ReviewAdapter(review, this);
        reviewList.setLayoutManager(new LinearLayoutManager(this));
        reviewList.setHasFixedSize(true);
        reviewList.setAdapter(reviewAdapter);

        if (videoAdapter == null) videoAdapter = new VideoAdapter(video, this);
        videoList.setLayoutManager(new LinearLayoutManager(this));
        videoList.setHasFixedSize(true);
        videoList.setAdapter(videoAdapter);
    }

    @Override
    public void onError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT);
    }

    @Override
    public void onLoading() {
        mLoading.setVisibility(View.GONE);
    }

    @Override
    public void noLoading() {
        mLayoutDetal.setVisibility(View.VISIBLE);
    }
}
