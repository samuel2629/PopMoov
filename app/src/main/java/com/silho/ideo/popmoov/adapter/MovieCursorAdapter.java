package com.silho.ideo.popmoov.adapter;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Toast;

import com.silho.ideo.popmoov.R;
import com.silho.ideo.popmoov.database.MovieAsFavContract;
import com.silho.ideo.popmoov.model.MovieObject;
import com.silho.ideo.popmoov.retrofitApi.MovieService;
import com.silho.ideo.popmoov.ui.DetailsMovieActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.silho.ideo.popmoov.ui.MainActivity.BASE_PATH_POSTER;
import static com.silho.ideo.popmoov.ui.MainActivity.DATE_RELEASE;
import static com.silho.ideo.popmoov.ui.MainActivity.MOVIE_ID;
import static com.silho.ideo.popmoov.ui.MainActivity.ORIGINAL_TITLE;
import static com.silho.ideo.popmoov.ui.MainActivity.PATH_POSTER;
import static com.silho.ideo.popmoov.ui.MainActivity.RATING_USER;
import static com.silho.ideo.popmoov.ui.MainActivity.SYNOSPSIS;


/**
 * Created by Samuel on 19/02/2018.
 */

public class MovieCursorAdapter extends MyRecyclerAdapter<MovieCursorAdapter.ViewHolder>{

    private Context mContext;
    private Map<Integer, Integer> mMap = new HashMap<>();


    public MovieCursorAdapter(Context context, Cursor cursor) {
        super(context, cursor);
        mContext = context;
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, Cursor cursor, int position) {


        int id = cursor.getColumnIndex(MovieAsFavContract.MovieAsFavEntry.COLUMN_ID);
        int path = cursor.getColumnIndex(MovieAsFavContract.MovieAsFavEntry.COLUMN_PATH_POSTER);

        int movieId = cursor.getInt(id);
        String pathPoster = cursor.getString(path);
        mMap.put(position, movieId);

        Picasso.with(mContext).load(pathPoster).into(viewHolder.mImageView);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.movie_item_poster, parent, false);
        return new ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements OnClickListener{

        ImageView mImageView;

        public ViewHolder(View itemView) {
            super(itemView);

            mImageView = itemView.findViewById(R.id.posterMainItem);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

            MovieService.getMovieById().movieById(mMap.get(getAdapterPosition())).enqueue(new Callback<MovieObject>() {
                @Override
                public void onResponse(Call<MovieObject> call, Response<MovieObject> response) {
                    MovieObject movieObject = response.body();
                    Intent intent = new Intent(mContext, DetailsMovieActivity.class);
                    intent.putExtra(PATH_POSTER, BASE_PATH_POSTER + movieObject.getPosterPath());
                    intent.putExtra(ORIGINAL_TITLE, movieObject.getOriginalTitle());
                    intent.putExtra(SYNOSPSIS, movieObject.getOverview());
                    intent.putExtra(RATING_USER, movieObject.getVoteAverage());
                    intent.putExtra(DATE_RELEASE, movieObject.getReleaseDate());
                    intent.putExtra(MOVIE_ID, movieObject.getId());
                    mContext.startActivity(intent);
                }

                @Override
                public void onFailure(Call<MovieObject> call, Throwable t) {
                    Toast.makeText(mContext, R.string.call_problem, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
