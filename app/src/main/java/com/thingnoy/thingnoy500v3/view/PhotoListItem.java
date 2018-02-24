package com.thingnoy.thingnoy500v3.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.thingnoy.thingnoy500v3.R;
import com.thingnoy.thingnoy500v3.view.state.BundleSavedState;
import com.thingnoy.thingnoy500v3.view.templete.BaseCustomViewGroup;

public class PhotoListItem extends BaseCustomViewGroup {

    private ImageView ivPhoto;
    private TextView tvName;
    private TextView tvDescription;

    public PhotoListItem(Context context) {
        super(context);
        initInflate();
        initInstances();
    }

    public PhotoListItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        initInflate();
        initInstances();
        initWithAttrs(attrs, 0, 0);
    }

    public PhotoListItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initInflate();
        initInstances();
        initWithAttrs(attrs, defStyleAttr, 0);
    }

    @TargetApi(21)
    public PhotoListItem(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initInflate();
        initInstances();
        initWithAttrs(attrs, defStyleAttr, defStyleRes);
    }

    private void initInflate() {
        inflate(getContext(), R.layout.list_item_photo, this);  //edit by skypart
    }

    private void initInstances() {
        // findViewById here
        ivPhoto = (ImageView) findViewById(R.id.ivPhoto);
        tvName = (TextView) findViewById(R.id.tvName);
        tvDescription = (TextView) findViewById(R.id.tvDescription);
    }

    private void initWithAttrs(AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        /*
        TypedArray a = getContext().getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.StyleableName,
                defStyleAttr, defStyleRes);

        try {

        } finally {
            a.recycle();
        }
        */
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();

        BundleSavedState savedState = new BundleSavedState(superState);
        // Save Instance State(s) here to the 'savedState.getBundle()'
        // for example,
        // savedState.getBundle().putString("key", value);

        return savedState;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        BundleSavedState ss = (BundleSavedState) state;
        super.onRestoreInstanceState(ss.getSuperState());

        Bundle bundle = ss.getBundle();
        // Restore State from bundle here
    }

    //แก้ไขขนาดของ ViewGroup
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);// ได้ขนาดเป็น px
        int height = width * 2 / 3;
        int newHeightMeasureSpec = MeasureSpec.makeMeasureSpec(
                height,
                MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, newHeightMeasureSpec);// บอกขนาดใหม่ให้กับ View ลูก

        setMeasuredDimension(width, height);// บอกขนาดใหม่ให้กับ ตัวเอง
    }




    public void setNameText(String Name) {
        this.tvName.setText(Name);
    }

    public void setDescriptionText(String Description) {
        this.tvDescription.setText(Description);
    }

    public void setImageUrl(String url) {
        Glide.with(getContext())
                .load(url)
                .apply(new RequestOptions()
                        .placeholder(R.drawable.loading)
                        .centerCrop()
                        .diskCacheStrategy(DiskCacheStrategy.ALL))
                .into(ivPhoto);

    }

}
