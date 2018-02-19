package com.silho.ideo.popmoov.ui;

import android.content.ContentValues;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.silho.ideo.popmoov.R;
import com.silho.ideo.popmoov.adapter.ReviewAdapter;
import com.silho.ideo.popmoov.adapter.TrailerAdapter;
import com.silho.ideo.popmoov.database.MovieAsFavContract;
import com.silho.ideo.popmoov.model.ReviewsMainObject;
import com.silho.ideo.popmoov.model.VideoMainObject;
import com.silho.ideo.popmoov.retrofitApi.MovieService;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.silho.ideo.popmoov.ui.MainActivity.DATE_RELEASE;
import static com.silho.ideo.popmoov.ui.MainActivity.MOVIE_ID;
import static com.silho.ideo.popmoov.ui.MainActivity.ORIGINAL_TITLE;
import static com.silho.ideo.popmoov.ui.MainActivity.PATH_POSTER;
import static com.silho.ideo.popmoov.ui.MainActivity.RATING_USER;
import static com.silho.ideo.popmoov.ui.MainActivity.SYNOSPSIS;

public class DetailsMovieActivity extends AppCompatActivity {

    private RecyclerView mReviewRv;
    private TextView mReviewLabel;
    private TextView mTrailerLabel;
    private Button mMarkAsFavBtn;
    private RecyclerView mTrailerRv;
    private String mOriginalTitle;
    private int mMovieId;
    private String mCompletePosterPath;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_movie);

        mTrailerRv = findViewById(R.id.trailersRecyclerView);
        mReviewRv = findViewById(R.id.reviewsRecyclerView);
        mMarkAsFavBtn = findViewById(R.id.markAsFavoriteBtn);
        mReviewLabel = findViewById(R.id.reviewLabelTv);
        mTrailerLabel = findViewById(R.id.trailerLabelTv);

        TextView titleTv = findViewById(R.id.titleTv);
        TextView releaseDateTv = findViewById(R.id.releaseDateTv);
        TextView synopsisTv = findViewById(R.id.synopsisTv);
        TextView userRatingTv = findViewById(R.id.userRatingTv);
        ImageView posterIv = findViewById(R.id.ivDetailActivity);

        if(getIntent().getExtras() != null){
            mCompletePosterPath = getIntent().getStringExtra(PATH_POSTER);
            mOriginalTitle = getIntent().getStringExtra(ORIGINAL_TITLE);
            String synopsis = getIntent().getStringExtra(SYNOSPSIS);
            String dateRelease = getIntent().getStringExtra(DATE_RELEASE);
            mMovieId = getIntent().getIntExtra(MOVIE_ID, 0);
            Double userRating = getIntent().getDoubleExtra(RATING_USER, 0);

            titleTv.setText(mOriginalTitle);
            releaseDateTv.setText(dateRelease);
            synopsisTv.setText(synopsis);
            userRatingTv.setText(Double.toString(userRating));
            Picasso.with(this).load(mCompletePosterPath).into(posterIv);

            getReviews(mMovieId);
            getTrailers(mMovieId);
        }

        mMarkAsFavBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContentValues contentValues = new ContentValues();
                contentValues.put(MovieAsFavContract.MovieAsFavEntry.COLUMN_TITLE, mOriginalTitle);
                contentValues.put(MovieAsFavContract.MovieAsFavEntry.COLUMN_ID, mMovieId);
                contentValues.put(MovieAsFavContract.MovieAsFavEntry.COLUMN_PATH_POSTER, mCompletePosterPath);
                Uri uri = getContentResolver().insert(MovieAsFavContract.MovieAsFavEntry.CONTENT_URI, contentValues);
                if(uri != null)
                    mMarkAsFavBtn.setText(R.string.unmark_as_fav);
            }
        });

    }

    private void getReviews(int id){
        MovieService.getReviewsMovie().reviewsMovie(id).enqueue(new Callback<ReviewsMainObject>() {
            @Override
            public void onResponse(Call<ReviewsMainObject> call, Response<ReviewsMainObject> response) {
                if(response.body().getResults().isEmpty()){
                    mReviewRv.setVisibility(View.GONE);
                    mReviewLabel.setVisibility(View.GONE);
                }
                ReviewAdapter reviewAdapter = new ReviewAdapter(DetailsMovieActivity.this, response.body().getResults());
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(DetailsMovieActivity.this);
                mReviewRv.setLayoutManager(linearLayoutManager);
                mReviewRv.setAdapter(reviewAdapter);
            }

            @Override
            public void onFailure(Call<ReviewsMainObject> call, Throwable t) {
                Toast.makeText(DetailsMovieActivity.this, R.string.no_results, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getTrailers(int id){
        MovieService.getTrailersMovie().trailerMovie(id).enqueue(new Callback<VideoMainObject>() {
            @Override
            public void onResponse(Call<VideoMainObject> call, Response<VideoMainObject> response) {
                if(response.body().getResults().isEmpty()){
                    mTrailerLabel.setVisibility(View.GONE);
                    mTrailerRv.setVisibility(View.GONE);
                }
                TrailerAdapter trailerAdapter = new TrailerAdapter(DetailsMovieActivity.this, response.body().getResults());
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(DetailsMovieActivity.this);
                mTrailerRv.setLayoutManager(linearLayoutManager);
                mTrailerRv.setAdapter(trailerAdapter);
            }

            @Override
            public void onFailure(Call<VideoMainObject> call, Throwable t) {
                Toast.makeText(DetailsMovieActivity.this, R.string.no_results, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
