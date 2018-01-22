package com.agung.android.moviedb.api;

import com.agung.android.moviedb.model.MovieResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by agung on 21/01/18.
 */

public interface ApiService {
    @GET("movie/top_rated")
    Call<MovieResponse>getTopRatedMovies(@Query("api_key")String apiKey);
}
