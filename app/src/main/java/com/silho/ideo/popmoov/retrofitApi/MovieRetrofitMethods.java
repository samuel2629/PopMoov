package com.silho.ideo.popmoov.retrofitApi;

import com.silho.ideo.popmoov.model.MainResultObject;
import com.silho.ideo.popmoov.model.MovieObject;
import com.silho.ideo.popmoov.model.ReviewsMainObject;
import com.silho.ideo.popmoov.model.VideoMainObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Samuel on 15/02/2018.
 */

public interface MovieRetrofitMethods {

    interface TopRatedMovies {
        @GET("top_rated?api_key=d3b4ca5e256f5d96ac4403e568f413ea&language=en-US&page=1")
        Call<MainResultObject> topRatedMovies();
    }

    interface PopularMovies {
        @GET("popular?api_key=d3b4ca5e256f5d96ac4403e568f413ea&language=en-US&page=1")
        Call<MainResultObject> popularMovies();
    }

    interface ReviewsMovie {
        @GET("{id}/reviews?api_key=d3b4ca5e256f5d96ac4403e568f413ea&language=en-US&page=1")
        Call<ReviewsMainObject> reviewsMovie(@Path("id") int id);
    }

    interface VideoTrailerMovie {
        @GET("{id}/videos?api_key=d3b4ca5e256f5d96ac4403e568f413ea&language=en-US&page=1")
        Call<VideoMainObject> trailerMovie(@Path("id") int id);
    }

    interface MovieById{
        @GET("{id}?api_key=d3b4ca5e256f5d96ac4403e568f413ea&language=en-US")
        Call<MovieObject> movieById(@Path("id") int id);
    }
}
