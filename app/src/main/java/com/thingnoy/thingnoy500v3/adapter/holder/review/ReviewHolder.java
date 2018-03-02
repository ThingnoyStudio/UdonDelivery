package com.thingnoy.thingnoy500v3.adapter.holder.review;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.thingnoy.thingnoy500v3.R;
import com.thingnoy.thingnoy500v3.manager.http.bus.Contextor;

/**
 * Created by HBO on 2/3/2561.
 */

public class ReviewHolder extends RecyclerView.ViewHolder {
    public ImageView ivReviewer;
    public TextView tvReviewerName, tvDate, tvReview;
    public RatingBar rbOfReviewer;

    public ReviewHolder(View itemView) {
        super(itemView);

        ivReviewer = itemView.findViewById(R.id.iv_reviewer);
        tvReviewerName = itemView.findViewById(R.id.tv_reviewer_name);
        rbOfReviewer = itemView.findViewById(R.id.rb_of_reviewer);
        tvDate = itemView.findViewById(R.id.tv_date_review);
        tvReview = itemView.findViewById(R.id.tv_str_Review);
    }

    public void setImageUrl(String url) {
        Glide.with(Contextor.getInstance().getContext())
                .load(url)
                .apply(new RequestOptions()
                        .placeholder(R.drawable.loading)
                        .centerCrop()
                        .diskCacheStrategy(DiskCacheStrategy.ALL))
                .into(ivReviewer);

    }
}
