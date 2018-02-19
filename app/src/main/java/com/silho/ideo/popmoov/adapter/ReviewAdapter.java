package com.silho.ideo.popmoov.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.silho.ideo.popmoov.R;
import com.silho.ideo.popmoov.model.ReviewsObject;

import java.util.List;

/**
 * Created by Samuel on 18/02/2018.
 */

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHolder>{

    private Context mContext;
    private List<ReviewsObject> mReviewsObjects;

    public ReviewAdapter(Context context, List<ReviewsObject> reviewsObjects){
        mContext = context;
        mReviewsObjects = reviewsObjects;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext)
                .inflate(R.layout.item_reviews, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bindReviews(position);
    }

    @Override
    public int getItemCount() {
        return mReviewsObjects.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView mReview;
        TextView mAuthor;

        public ViewHolder(View itemView) {
            super(itemView);

            mReview = itemView.findViewById(R.id.reviewTv);
            mAuthor = itemView.findViewById(R.id.authorTv);
        }

        public void bindReviews(int position) {
            mReview.setText(mReviewsObjects.get(position).getContent());
            mAuthor.setText(new StringBuilder().append(mReviewsObjects.get(position).getAuthor()).append(" : ").toString());
        }
    }
}
