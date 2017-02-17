package com.imgod1.blurimageview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;

/**
 * project name:TestCustomView
 * class desc:Blur ImageView
 * author:imgod1
 * create time:2017/2/16 14:03
 * edit author:imgod1
 * edit time:2017/2/16 14:03
 * eidt desc:
 */
public class BlurImageView extends ImageView {
    private boolean isBlurMode = false;
    private Bitmap realBitmap;
    private Bitmap blurBitmap;

    public BlurImageView(Context context) {
        this(context, null);
    }

    public BlurImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BlurImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        resetBitmapAndUpdate();
    }


    /**
     * show blur
     */
    public void showBlur() {
        isBlurMode = true;
        if (null != blurBitmap) {
            setImageBitmap(blurBitmap, false);
        } else {
            if (null == realBitmap) {
                realBitmap = getBitmapFromDrawable();
            }
            new BlurTask().execute(realBitmap);
        }
    }

    /**
     * show normal
     */
    public void showNormal() {
        isBlurMode = false;
        if (null != realBitmap) {
            setImageBitmap(realBitmap, false);
        } else {
            Log.e("test", "real bitmap null");
        }

    }

    /**
     * get a blur bitmap from old bitmap
     */
    private class BlurTask extends AsyncTask<Bitmap, Void, Bitmap> {
        @Override
        protected Bitmap doInBackground(Bitmap... bitmaps) {
            if (isCancelled()) {
                return null;
            }
            return BlurBuilder.blur(bitmaps[0]);
        }

        @Override
        protected void onPostExecute(Bitmap tempBlurBitmap) {
            blurBitmap = tempBlurBitmap;
            setImageBitmap(blurBitmap, false);
        }
    }

    /**
     * reset bitmap to null
     */
    private void resetBitmap() {
        realBitmap = null;
        blurBitmap = null;
    }

    public boolean isBlurMode() {
        return isBlurMode;
    }

    public void setBlurMode(boolean blurMode) {
        isBlurMode = blurMode;
    }

    /**
     * reset and update
     */
    private void resetBitmapAndUpdate() {
        isBlurMode = false;
        resetBitmap();
        realBitmap = getBitmapFromDrawable();
    }

    /**
     * @return bitmap
     */
    private Bitmap getBitmapFromDrawable() {
        BitmapDrawable bitmapDrawable = (BitmapDrawable) getDrawable();
        if (null != bitmapDrawable) {
            return bitmapDrawable.getBitmap();
        }
        return null;
    }

    public void setImageResource(int resId, boolean isNew) {
        super.setImageResource(resId);
        if (isNew) {
            resetBitmapAndUpdate();
        }
    }

    public void setImageURI(Uri uri, boolean isNew) {
        super.setImageURI(uri);
        if (isNew) {
            resetBitmapAndUpdate();
        }
    }

    public void setImageBitmap(Bitmap bm, boolean isNew) {
        super.setImageBitmap(bm);
        if (isNew) {
            resetBitmapAndUpdate();
        }
    }

    public void setImageDrawable(Drawable drawable, boolean isNew) {
        super.setImageDrawable(drawable);
        if (isNew) {
            resetBitmapAndUpdate();
        }
    }


}
