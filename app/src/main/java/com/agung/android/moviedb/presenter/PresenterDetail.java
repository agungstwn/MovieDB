package com.agung.android.moviedb.presenter;

import com.agung.android.moviedb.api.ApiClient;
import com.agung.android.moviedb.model.creditsResponse.CastItem;
import com.agung.android.moviedb.model.creditsResponse.CastsResponse;
import com.agung.android.moviedb.model.creditsResponse.CrewItem;
import com.agung.android.moviedb.model.detailResponse.DetailMovieResponse;
import com.agung.android.moviedb.model.reviewResponse.ResultsItem;
import com.agung.android.moviedb.model.reviewResponse.ReviewsResponse;
import com.agung.android.moviedb.model.videoResponse.VideoResponse;
import com.agung.android.moviedb.utils.constant;
import com.agung.android.moviedb.view.ViewDetail;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by agung on 26/01/18.
 */

public class PresenterDetail {
    private ViewDetail view;

    public void loadDetail(final int id) {
        Call<DetailMovieResponse> call = ApiClient.getService().getMovieDetails(id, constant.Api.API_KEY);
        call.enqueue(new Callback<DetailMovieResponse>() {
            @Override
            public void onResponse(Call<DetailMovieResponse> call, Response<DetailMovieResponse> response) {
                getView().noLoading();
                getView().onLoading();
                getCasts(response.body());
            }

            @Override
            public void onFailure(Call<DetailMovieResponse> call, Throwable t) {
                getView().onLoading();
                getView().onError("Server Failure: " + t.getMessage());
            }
        });

    }

    public ViewDetail getView() {
        return view;
    }

    public void setView(ViewDetail view) {
        this.view = view;
    }

    private void getCasts(final DetailMovieResponse detail) {
        Call<CastsResponse> call = ApiClient.getService().getCasts(detail.getId(), constant.Api.API_KEY);
        call.enqueue(new Callback<CastsResponse>() {
            @Override
            public void onResponse(Call<CastsResponse> call, Response<CastsResponse> response) {
                if (response.code() == 200) {
                    getReviews(detail, response.body().getCast(), response.body().getCrew());
                }
            }

            @Override
            public void onFailure(Call<CastsResponse> call, Throwable t) {
                getView().onError("Server Failure: " + t.getMessage());
            }
        });
    }

    private void getReviews(final DetailMovieResponse detail, final List<CastItem> cast,
                            final List<CrewItem> crew) {
        Call<ReviewsResponse> call = ApiClient.getService().getReviews(detail.getId(), constant.Api.API_KEY);
        call.enqueue(new Callback<ReviewsResponse>() {
            @Override
            public void onResponse(Call<ReviewsResponse> call, Response<ReviewsResponse> response) {
                getVideos(detail, cast, crew, response.body().getResults());
            }

            @Override
            public void onFailure(Call<ReviewsResponse> call, Throwable t) {
                getView().onError("Server Failure: " + t.getMessage());
            }
        });
    }

    private void getVideos(final DetailMovieResponse detail, final List<CastItem> cast,
                           final List<CrewItem> crew, final List<ResultsItem> review) {
        Call<VideoResponse> call = ApiClient.getService().getVideos(detail.getId(), constant.Api.API_KEY);
        call.enqueue(new Callback<VideoResponse>() {
            @Override
            public void onResponse(Call<VideoResponse> call, Response<VideoResponse> response) {
                getView().onLoadData(detail, cast, crew, review, response.body().getVideos());
            }

            @Override
            public void onFailure(Call<VideoResponse> call, Throwable t) {
                getView().onError("Server Failure: " + t.getMessage());
            }
        });
    }
}
