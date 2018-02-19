package com.silho.ideo.popmoov.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.silho.ideo.popmoov.R;
import com.silho.ideo.popmoov.model.MovieObject;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Samuel on 15/02/2018.
 */

public class GridViewAdapter extends ArrayAdapter<MovieObject>{

    private static final String BASE_PATH_POSTER = "http://image.tmdb.org/t/p/w185/";

    private String mCompletePosterPath;

    public GridViewAdapter(@NonNull Context context, @NonNull List<MovieObject> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if(getItem(position).getPosterPath() != null) {
            mCompletePosterPath = BASE_PATH_POSTER + getItem(position).getPosterPath();
        }

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.movie_item_poster, parent, false);
        }

        ImageView iconImageView = convertView.findViewById(R.id.posterMainItem);
        Picasso.with(getContext()).load(mCompletePosterPath).into(iconImageView);
        return convertView;
    }

}
