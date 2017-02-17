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
 * 项目名称：TestCustomView
 * 类描述：高斯模糊的ImageView
 * 创建人：imgod1
 * 创建时间：2017/2/16 14:03
 * 修改人：imgod1
 * 修改时间：2017/2/16 14:03
 * 修改备注：
 */
public class BlurImageView extends ImageView {
    private boolean isBlurMode = false;
    private Bitmap realBitmap;//真实bitmap
    private Bitmap blurBitmap;//模糊后的bitmap

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
     * 模糊
     */
    public void showBlur() {
        isBlurMode = true;
        if (null != blurBitmap) {//如果已经有了模糊好的Bitmap
            setImageBitmap(blurBitmap, false);
        } else {//如果没有
            if (null == realBitmap) {//如果有真实Bitmap,那就去模糊
                realBitmap = getBitmapFromDrawable();
            }
            new BlurTask().execute(realBitmap);
        }
    }

    /**
     * 正常
     */
    public void showNormal() {
        isBlurMode = false;
        if (null != realBitmap) {
            setImageBitmap(realBitmap, false);
        } else {
            Log.e("test", "设置真实的Bitmap 空了");
        }

    }

    /**
     * 从原始Bitmap中得到一个模糊的Bitmap
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
     * 清空Bitmap,用户重新设置了显示的资源
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
     * 重置并更新
     */
    private void resetBitmapAndUpdate() {
        isBlurMode = false;
        resetBitmap();
        realBitmap = getBitmapFromDrawable();
    }

    /**
     * 从原来的Drawable里面拿到Bitmap
     *
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
