package com.thingnoy.thingnoy500v3.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.stepstone.apprating.AppRatingDialog;
import com.stepstone.apprating.listener.RatingDialogListener;
import com.thingnoy.thingnoy500v3.R;
import com.thingnoy.thingnoy500v3.dao.DataResProDao;

import java.util.Arrays;

import static android.content.ContentValues.TAG;


/**
 * A simple {@link Fragment} subclass.
 */
public class ResInfoFragment extends Fragment implements View.OnClickListener {

    private DataResProDao dao;
    private ImageView imgRes;
    private RatingBar rbTotal;
    private FloatingActionButton fabReview;
    private TextView tvTitle;

    public ResInfoFragment() {
        super();
    }

    @SuppressWarnings("unused")
    public static ResInfoFragment newInstance(DataResProDao dao) {
        ResInfoFragment fragment = new ResInfoFragment();
        Bundle args = new Bundle();

        args.putParcelable("dao",dao);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(savedInstanceState);

        dao = getArguments().getParcelable("dao");

        if (savedInstanceState != null)
            onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_res_info, container, false);
        initInstances(rootView, savedInstanceState);
        loadImgRestaurant();
        return rootView;
    }

    private void loadImgRestaurant() {
        Glide.with(ResInfoFragment.this)// โหลดรูป
                .load(dao.getRestaurantNameDao().getResImg())// โหลดจาก url นี้
                .apply(new RequestOptions()
                        .placeholder(R.drawable.loading)// กรณี กำลังโหลด
                        .diskCacheStrategy(DiskCacheStrategy.ALL)) //เก็บลงแคช ทุกชนาด
                .into(imgRes);// โหลดเข้า imageView ตัวนี้
    }

    private void init(Bundle savedInstanceState) {
        // Init Fragment level's variable(s) here
    }

    @SuppressWarnings("UnusedParameters")
    private void initInstances(View rootView, Bundle savedInstanceState) {
        // Init 'View' instance(s) with rootView.findViewById here
        imgRes = rootView.findViewById(R.id.iv_restaurant);
        tvTitle = rootView.findViewById(R.id.tv_restaurant_name);
//        rbTotal = rootView.findViewById(R.id.rb_total);
        fabReview = rootView.findViewById(R.id.fab_review);
        fabReview.setOnClickListener(this);

        tvTitle.setText(dao.getRestaurantNameDao().getResName());
    }

    private void showDialog() {
        new AppRatingDialog.Builder()
                .setPositiveButtonText("ส่ง")
                .setNegativeButtonText("ยกเลิก")
//                .setNeutralButtonText("ภายหลัง")
                .setNoteDescriptions(Arrays.asList("ไม่ดีเลย", "เฉยๆ", "พอใช้", "ดี", "ดีเยี่ยม !!!"))
                .setDefaultRating(2)
                .setTitle("ให้คะแนนร้านนี้")
//                .setDescription("กรุณาเลือกดาว")
//                .setDefaultComment("This app is pretty cool !")
                .setStarColor(R.color.starColor)
                .setNoteDescriptionTextColor(R.color.noteDescriptionTextColor)
                .setTitleTextColor(R.color.titleTextColor)
                .setDescriptionTextColor(R.color.contentTextColor)
                .setHint("กรุณาเขียนรีวิวที่นี่ ...")
                .setHintTextColor(R.color.hintTextColor)
                .setCommentTextColor(R.color.commentTextColor)
                .setCommentBackgroundColor(R.color.commentBackgroundColor)
                .setWindowAnimation(R.style.MyDialogFadeAnimation)
                .create(getActivity())
                .show();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    /*
     * Save Instance State Here
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Save Instance State here
    }

    /*
     * Restore Instance State Here
     */
    @SuppressWarnings("UnusedParameters")
    private void onRestoreInstanceState(Bundle savedInstanceState) {
        // Restore Instance State here
    }

    @Override
    public void onClick(View v) {
        if (v == fabReview) {
            Toast.makeText(getContext(), "Fab click!", Toast.LENGTH_SHORT).show();
            showDialog();
        }
    }
}
