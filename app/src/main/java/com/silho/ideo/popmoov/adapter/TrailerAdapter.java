package com.silho.ideo.popmoov.adapter;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.silho.ideo.popmoov.R;
import com.silho.ideo.popmoov.model.VideoObject;

import java.util.List;

/**
 * Created by Samuel on 18/02/2018.
 */

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.ViewHolder> {

    private Context mContext;
    private List<VideoObject> mVideoObjects;

    public TrailerAdapter(Context context, List<VideoObject> videoObjects){
        mContext = context;
        mVideoObjects = videoObjects;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_videos, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bindTrailer(position);
    }

    @Override
    public int getItemCount() {
        return mVideoObjects.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView mTrailerPositionTv;
        private int mPosition;

        public ViewHolder(View itemView) {
            super(itemView);

            mTrailerPositionTv = itemView.findViewById(R.id.trailerTv);
            itemView.setOnClickListener(this);
        }

        public void bindTrailer(int position) {

                mPosition = position;
                String trailer = "Trailer " + (position + 1);
                mTrailerPositionTv.setText(trailer);
        }

        @Override
        public void onClick(View view) {
            Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + mVideoObjects.get(mPosition).getKey()));
            Intent webIntent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://www.youtube.com/watch?v=" + mVideoObjects.get(mPosition).getKey()));
            try {
                mContext.startActivity(appIntent);
            } catch (ActivityNotFoundException ex) {
                mContext.startActivity(webIntent);
            }
        }
    }
}
