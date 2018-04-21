package com.thingnoy.thingnoy500v3.ui.fragment;


import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.stepstone.apprating.AppRatingDialog;
import com.thingnoy.thingnoy500v3.R;
import com.thingnoy.thingnoy500v3.adapter.ReviewAdapter;
import com.thingnoy.thingnoy500v3.api.dao.NameAndImageDao;
import com.thingnoy.thingnoy500v3.api.interceptor.CustomHttpLogging;
import com.thingnoy.thingnoy500v3.api.request.AddReviewBody;
import com.thingnoy.thingnoy500v3.api.HttpManager;
import com.thingnoy.thingnoy500v3.api.result.review.AddReviewResult;
import com.thingnoy.thingnoy500v3.api.result.review.ReviewResultGroup;
import com.vansuita.pickimage.bean.PickResult;
import com.vansuita.pickimage.bundle.PickSetup;
import com.vansuita.pickimage.dialog.PickImageDialog;
import com.vansuita.pickimage.listeners.IPickResult;
import com.willy.ratingbar.RotationRatingBar;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class ResInfoFragment extends Fragment implements View.OnClickListener {
    private final static String TAG = ResInfoFragment.class.getSimpleName();
    private NameAndImageDao dao;
    private ImageView imgRes;
    private RatingBar rbTotal;
    private FloatingActionButton fabReview;
    private TextView tvTitle;
    private Button btnSelectImg, btnWriteReview, btnSendReview;
    private EditText edtReview;
    private RotationRatingBar srbRes;
    private ImageView ivReview;
    private Bitmap bitImgReview = null;
    private boolean isChooseImage = false;
    private ReviewAdapter reviewAdapter;
    private RecyclerView rcReviewList;

    public ResInfoFragment() {
        super();
    }

    @SuppressWarnings("unused")
    public static ResInfoFragment newInstance(NameAndImageDao dao) {
        ResInfoFragment fragment = new ResInfoFragment();
        Bundle args = new Bundle();

        args.putParcelable("dao", dao);

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
        callReviewList(dao.getResId());
        return rootView;
    }

    private void loadImgRestaurant() {
        Glide.with(ResInfoFragment.this)// โหลดรูป
                .load(dao.getResImage())// โหลดจาก url นี้
                .apply(new RequestOptions()
                        .placeholder(R.drawable.ic_pic_loading)// กรณี กำลังโหลด
                        .diskCacheStrategy(DiskCacheStrategy.ALL)) //เก็บลงแคช ทุกชนาด
                .into(imgRes);// โหลดเข้า bitImgReview ตัวนี้
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

        btnSelectImg = rootView.findViewById(R.id.btn_select_img);
        btnWriteReview = rootView.findViewById(R.id.btn_write_review);
        btnSendReview = rootView.findViewById(R.id.btn_send_review);
        edtReview = rootView.findViewById(R.id.edt_review);
        srbRes = rootView.findViewById(R.id.srb_rate);
        ivReview = rootView.findViewById(R.id.iv_review);

        rcReviewList = rootView.findViewById(R.id.rc_review_res);


        setupView();
    }

    private void setupView() {
        fabReview.setOnClickListener(this);
        //set title (resName)
        tvTitle.setText(dao.getResName());

        btnSelectImg.setOnClickListener(this);
        btnSendReview.setOnClickListener(this);
        btnWriteReview.setOnClickListener(this);
        ivReview.setOnClickListener(this);

        reviewAdapter = new ReviewAdapter();
        rcReviewList.setLayoutManager(new LinearLayoutManager(getContext()));
        rcReviewList.setAdapter(reviewAdapter);
    }

    private void showDialog() {
        new AppRatingDialog.Builder()
                .setPositiveButtonText("ส่ง")
                .setNegativeButtonText("ยกเลิก")
                .setNeutralButtonText("เลือกรูป")
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

    private void callReviewList(int id) {
        Call<ReviewResultGroup> call = HttpManager.getInstance()
                .getService()
                .getReview(id);
        call.enqueue(new Callback<ReviewResultGroup>() {
            @Override
            public void onResponse(Call<ReviewResultGroup> call, Response<ReviewResultGroup> response) {
                if (response.isSuccessful()) {
                    ReviewResultGroup dao = response.body();

                    reviewAdapter.setDao(dao);
                    reviewAdapter.notifyDataSetChanged();
                } else {
                    showToast("โหลดรายการรีวิวร้าน ไม่สำเร็จ!");
                }
            }

            @Override
            public void onFailure(Call<ReviewResultGroup> call, Throwable t) {
                showToast("ล้มเหลว: " + t);
            }
        });
    }
    private void callSendReview(AddReviewBody body) {
        Call<AddReviewResult> call = HttpManager.getInstance()
                .getService()
                .addReview(body);
        call.enqueue(new Callback<AddReviewResult>() {
            @Override
            public void onResponse(Call<AddReviewResult> call, Response<AddReviewResult> response) {
                if (response.isSuccessful()) {
                    showToast("ส่งรีวิวร้าน เรียบร้อยแล้ว!");
                    callReviewList(dao.getResId());
                } else {
                    showToast("ส่งรีวิวร้าน ไม่สำเร็จ!");
                }
            }

            @Override
            public void onFailure(Call<AddReviewResult> call, @NonNull Throwable t) {
                showToast("ล้มเหลว: " + t);
            }
        });
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

    private String encodeImage(Bitmap bm) {
        if (bm == null) {
            return null;
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] b = baos.toByteArray();
        String encImage = Base64.encodeToString(b, Base64.DEFAULT);

        return encImage;
    }

    @Override
    public void onClick(View v) {
        if (v == fabReview) {
//            Toast.makeText(getContext(), "Fab click!", Toast.LENGTH_SHORT).show();
//            showDialog();
        }
        if (v == btnSelectImg) {
//            chooseImage();
        }
        if (v == btnSendReview) {
            sendReview();
        }
        if (v == btnWriteReview) {
//            Toast.makeText(getContext(), "write review click!", Toast.LENGTH_SHORT).show();
//            showDialog();
        }
        if (v == ivReview) {
            chooseImage();
        }
    }

    private void sendReview() {
        Calendar c = Calendar.getInstance();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentDate = df.format(c.getTime());
        Log.e("date >>", "date now: " + currentDate);

        AddReviewBody body = new AddReviewBody();
        body.setId_customer(2);
        body.setDate(currentDate);
        body.setId_restaurant(dao.getResId());
        if (edtReview.getText().toString().trim().equals("")) {
            showToast("กรุณาเขียนรีวิวด้วยค่ะ");
            return;
        } else {
            body.setRes_comment(edtReview.getText().toString().trim());
        }
        body.setRes_score(srbRes.getRating());
        body.setImgname("reviewimg");
        if (isChooseImage) {
            body.setImg(encodeImage(bitImgReview) == null ? "" : encodeImage(bitImgReview));
        } else {
            body.setImg(null);
        }

        showToast("" + dao.getResId());
        callSendReview(body);

        hideKeybord();
        edtReview.setText("");
        srbRes.setRating(3);
        ivReview.setImageResource(R.drawable.image);
        isChooseImage = false;
    }

    private void showToast(String massage) {
        Toast.makeText(getContext(), massage, Toast.LENGTH_SHORT).show();
    }

    private void chooseImage() {
        isChooseImage = true;
        PickSetup setup = new PickSetup();
        PickImageDialog.build(setup, new IPickResult() {
            @Override
            public void onPickResult(PickResult r) {
                r.getBitmap();
                r.getError();
                r.getUri();


                if (r.getError() == null) {
                    //If you want the Uri.
                    //Mandatory to refresh image from Uri.
//            ivReview.setImageURI(null);

                    //Setting the real returned image.
//                        ivReview.setImageURI(r.getUri());

                    //If you want the Bitmap.
                    ivReview.setImageBitmap(r.getBitmap());
                    bitImgReview = r.getBitmap();

                    //r.getPath();
                    Toast.makeText(getContext(), "Path: " + r.getPath(), Toast.LENGTH_LONG).show();
                } else {
                    //Handle possible errors
                    //TODO: do what you have to do with r.getError();
                    Toast.makeText(getContext(), "nnn :" + r.getError().getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        }).show(getFragmentManager());
    }

    private void hideKeybord() {
        final InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
//        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
//        imm.hideSoftInputFromWindow(getActivity().getWindowToken(), 0);
    }

}
