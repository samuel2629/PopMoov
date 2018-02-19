package com.silho.ideo.popmoov.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.silho.ideo.popmoov.R;
import com.silho.ideo.popmoov.model.MovieObject;
import com.silho.ideo.popmoov.ui.DetailsMovieActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

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

public class MovieMainAdapter extends RecyclerView.Adapter<MovieMainAdapter.ViewHolder> {

    private Context mContext;
    private List<MovieObject> mMovieObjects;

    public MovieMainAdapter(Context context, List<MovieObject> movieObjects){
        mContext = context;
        mMovieObjects = movieObjects;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.movie_item_poster, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bindPoster(position);
    }

    @Override
    public int getItemCount() {
        return mMovieObjects.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView mPosterIv;
        private int mPosition;

        public ViewHolder(View itemView) {
            super(itemView);

            mPosterIv = itemView.findViewById(R.id.posterMainItem);
            itemView.setOnClickListener(this);
        }

        public void bindPoster(int position) {
            mPosition = position;
            Picasso.with(mContext).load(BASE_PATH_POSTER + mMovieObjects.get(position).getPosterPath()).into(mPosterIv);
        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(mContext, DetailsMovieActivity.class);
            intent.putExtra(PATH_POSTER, BASE_PATH_POSTER + mMovieObjects.get(mPosition).getPosterPath());
            intent.putExtra(ORIGINAL_TITLE, mMovieObjects.get(mPosition).getOriginalTitle());
            intent.putExtra(SYNOSPSIS, mMovieObjects.get(mPosition).getOverview());
            intent.putExtra(RATING_USER, mMovieObjects.get(mPosition).getVoteAverage());
            intent.putExtra(DATE_RELEASE, mMovieObjects.get(mPosition).getReleaseDate());
            intent.putExtra(MOVIE_ID, mMovieObjects.get(mPosition).getId());
            mContext.startActivity(intent);
        }
    }
}
