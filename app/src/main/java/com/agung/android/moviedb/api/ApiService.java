package com.agung.android.moviedb.api;

import com.agung.android.moviedb.model.nowPlayingResponse.NowPlayingResponse;
import com.agung.android.moviedb.model.popularResponse.PopularResponse;
import com.agung.android.moviedb.model.topRateResponse.MovieResponse;
import com.agung.android.moviedb.model.upComingResponse.UpComingResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by agung on 21/01/18.
 */

public interface ApiService {
    @GET("movie/top_rated")
    Call<MovieResponse>getTopRatedMovies(@Query("api_key")String apiKey);

    @GET("movie/now_playing")
    Call<NowPlayingResponse>getNowPlaying(@Query("api_key")String apiKey);

    @GET("movie/upcoming")
    Call<UpComingResponse>getUpcomingResponse(@Query("api_key")String apiKey);

    @GET("movie/popular")
    Call<PopularResponse>getPopularRensponse(@Query("api_key")String apiKey);



}
