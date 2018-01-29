package com.agung.android.moviedb.presenter;

import com.agung.android.moviedb.api.ApiClient;
import com.agung.android.moviedb.model.creditsResponse.CastItem;
import com.agung.android.moviedb.model.creditsResponse.CastsResponse;
import com.agung.android.moviedb.model.detailResponse.DetailMovieResponse;
import com.agung.android.moviedb.utils.constant;
import com.agung.android.moviedb.view.ViewDetail;

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
                getView().onLoading();
                getView().noLoading();
                getCasts(response.body());

            }

            @Override
            public void onFailure(Call<DetailMovieResponse> call, Throwable t) {
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

    private void getCasts(final DetailMovieResponse detail){
        Call<CastsResponse> call = ApiClient.getService().getCasts(detail.getId(), constant.Api.API_KEY);
        call.enqueue(new Callback<CastsResponse>() {
            @Override
            public void onResponse(Call<CastsResponse> call, Response<CastsResponse> response) {
                if (response.code() == 200){
                    getView().onLoadData(detail, response.body().getCast());
                }
            }

            @Override
            public void onFailure(Call<CastsResponse> call, Throwable t) {

            }
        });
    }
}
