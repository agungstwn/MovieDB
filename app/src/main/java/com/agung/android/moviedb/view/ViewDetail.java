package com.agung.android.moviedb.view;

import com.agung.android.moviedb.model.detailResponse.DetailMovieResponse;

/**
 * Created by agung on 26/01/18.
 */

public interface ViewDetail {

    void onLoadData(DetailMovieResponse movie);

    void onError(String message);

}
