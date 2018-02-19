package com.silho.ideo.popmoov.retrofitApi;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Samuel on 16/02/2018.
 */

public class MovieService {

    private static final String BASE_URL = "https://api.themoviedb.org/3/movie/";

    public static MovieRetrofitMethods.TopRatedMovies getTopRatedMovies(){
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(MovieRetrofitMethods.TopRatedMovies.class);
    }

    public static MovieRetrofitMethods.PopularMovies getPopularMovies(){
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(MovieRetrofitMethods.PopularMovies.class);
    }

    public static MovieRetrofitMethods.ReviewsMovie getReviewsMovie(){
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(MovieRetrofitMethods.ReviewsMovie.class);
    }

    public static MovieRetrofitMethods.VideoTrailerMovie getTrailersMovie(){
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(MovieRetrofitMethods.VideoTrailerMovie.class);
    }

    public static MovieRetrofitMethods.MovieById getMovieById(){
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(MovieRetrofitMethods.MovieById.class);
    }




}
