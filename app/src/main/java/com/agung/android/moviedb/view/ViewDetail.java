package com.agung.android.moviedb.view;

import com.agung.android.moviedb.model.creditsResponse.CastItem;
import com.agung.android.moviedb.model.creditsResponse.CastsResponse;
import com.agung.android.moviedb.model.creditsResponse.CrewItem;
import com.agung.android.moviedb.model.detailResponse.DetailMovieResponse;
import com.agung.android.moviedb.model.reviewResponse.ResultsItem;

import java.util.List;

/**
 * Created by agung on 26/01/18.
 */

public interface ViewDetail {

    void onLoadData(DetailMovieResponse movie, List<CastItem>casts, List<CrewItem>crews,
                    List<ResultsItem>review);

    void onError(String message);

    void onLoading();

    void noLoading();

}
