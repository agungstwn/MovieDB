package com.agung.android.moviedb.presenter;

import com.agung.android.moviedb.api.ApiClient;
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

    public void loadDetail(int id) {
        Call<DetailMovieResponse> call = ApiClient.getService().getMovieDetails(id, constant.Api.API_KEY);
        call.enqueue(new Callback<DetailMovieResponse>() {
            @Override
            public void onResponse(Call<DetailMovieResponse> call, Response<DetailMovieResponse> response) {
                getView().onLoadData(response.body());
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
}
