package com.agung.android.moviedb.view;

import com.agung.android.moviedb.model.creditsResponse.CastItem;
import com.agung.android.moviedb.model.creditsResponse.CrewItem;
import com.agung.android.moviedb.model.detailResponse.DetailMovieResponse;
import com.agung.android.moviedb.model.reviewResponse.ResultsItem;
import com.agung.android.moviedb.model.videoResponse.VideosItem;

import java.util.List;

/**
 * Created by agung on 26/01/18.
 */

public interface ViewDetail {

    void onLoadData(DetailMovieResponse movie, List<CastItem>casts, List<CrewItem>crews,
                    List<ResultsItem>review, List<VideosItem> videos);

    void onError(String message);

    void onLoading();

    void noLoading();

}
