package com.thingnoy.thingnoy500v3.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.thingnoy.thingnoy500v3.manager.http.bus.Contextor;

import java.io.InputStream;

public class DownloadImage extends AsyncTask<Uri, Void, Bitmap> {
    private static final String TAG = DownloadImage.class.getSimpleName();
    private onDownloadListener listener;

    public interface onDownloadListener {
        void onDownloadSuccess(Bitmap bmResult);
    }

    public void setOnDownloadImageListener(onDownloadListener listener) {
        this.listener = listener;
    }

//    @Override
//    protected Bitmap doInBackground(String... urls) {
//        if (urls != null) {
//            String urlDisplay = urls[0];
//            Bitmap bitmap = null;
//            try {
////                InputStream in = new java.net.URL(urlDisplay).openStream();
////                bitmap = BitmapFactory.decodeStream(in);
//                bitmap = Glide.with(Contextor.getInstance().getContext()).asBitmap().load(uri).submit().get();
////                bitmap = Glide.with(Contextor.getInstance().getContext()).asBitmap().load(in).submit().get();
//            } catch (Exception e) {
//                Log.e(TAG, "Error: " + e.getMessage());
//                e.printStackTrace();
//            }
//            return bitmap;
//        }
//
//        return null;
//    }

    @Override
    protected Bitmap doInBackground(Uri... uris) {
        Uri uri = uris[0];
        Bitmap bitmap = null;
        try {
            bitmap = GlideApp.with(Contextor.getInstance().getContext())
                    .asBitmap()
                    .load(uri)
                    .override(600,600)
                    .centerCrop()
                    .submit()
                    .get();
        }catch (Exception e){
            Log.e(TAG, "Error: " + e.getMessage());
            e.printStackTrace();
        }
        return bitmap;

    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
        if (listener != null) {
            listener.onDownloadSuccess(bitmap);
        }

    }
}
