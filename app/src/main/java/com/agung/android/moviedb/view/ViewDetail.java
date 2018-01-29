package com.agung.android.moviedb.view;

import com.agung.android.moviedb.model.creditsResponse.CastItem;
import com.agung.android.moviedb.model.creditsResponse.CastsResponse;
import com.agung.android.moviedb.model.detailResponse.DetailMovieResponse;

import java.util.List;

/**
 * Created by agung on 26/01/18.
 */

public interface ViewDetail {

    void onLoadData(DetailMovieResponse movie, List<CastItem>casts);

    void onError(String message);

    void onLoading();

    void noLoading();

}
