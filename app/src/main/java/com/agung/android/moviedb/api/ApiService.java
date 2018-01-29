package com.agung.android.moviedb.api;

import com.agung.android.moviedb.model.MovieResponse;
import com.agung.android.moviedb.model.creditsResponse.CastsResponse;
import com.agung.android.moviedb.model.detailResponse.DetailMovieResponse;
import com.agung.android.moviedb.model.reviewResponse.ReviewsResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by agung on 21/01/18.
 */

public interface ApiService {
    @GET("movie/top_rated")
    Call<MovieResponse>getTopRatedMovies(@Query("api_key")String apiKey);

    @GET("movie/now_playing")
    Call<MovieResponse>getNowPlaying(@Query("api_key")String apiKey);

    @GET("movie/upcoming")
    Call<MovieResponse>getUpcomingResponse(@Query("api_key")String apiKey);

    @GET("movie/popular")
    Call<MovieResponse>getPopularRensponse(@Query("api_key")String apiKey);

    @GET("movie/{id}")
    Call<DetailMovieResponse>getMovieDetails(@Path("id")int id, @Query("api_key") String apiKey);

    @GET("movie/{movie_id}/credits")
    Call<CastsResponse>getCasts(@Path("movie_id")int id, @Query("api_key") String apiKey);

    @GET("movie/{movie_id}/reviews")
    Call<ReviewsResponse>getReviews(@Path("movie_id")int id, @Query("api_key") String apiKey);

}
