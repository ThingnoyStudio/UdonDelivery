package com.thingnoy.thingnoy500v3.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.thingnoy.thingnoy500v3.R;
import com.thingnoy.thingnoy500v3.adapter.holder.review.ReviewHolder;
import com.thingnoy.thingnoy500v3.dao.review.DataReview;
import com.thingnoy.thingnoy500v3.dao.review.ReviewCollectionDao;

/**
 * Created by HBO on 2/3/2561.
 */

public class ReviewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ReviewCollectionDao dao;

    public ReviewAdapter() {
        this.dao = new ReviewCollectionDao();
    }

    public void setDao(ReviewCollectionDao dao) {
        this.dao = dao;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.holder_review_list, parent, false);
        return new ReviewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        DataReview dataReview = dao.getmData().get(position);

        ReviewHolder reviewHolder = (ReviewHolder) holder;
        String reviewer = dataReview.getmCustomerFName() + " " + dataReview.getmCustomerLName();

        reviewHolder.setImageUrl(dataReview.getmResReviewImage());
        reviewHolder.tvReviewerName.setText(reviewer);
        reviewHolder.rbOfReviewer.setRating(Float.parseFloat(dataReview.getmResReviewScore()));
        reviewHolder.tvDate.setText(dataReview.getmResReviewDate());
        reviewHolder.tvReview.setText(dataReview.getmResComment());
    }

    @Override
    public int getItemCount() {
        if (dao.getmData() == null)
            return 0;
        if (dao.getmData().size() <= 0)
            return 0;
        return dao.getmData().size();
    }
}
