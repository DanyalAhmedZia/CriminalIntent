package com.bignerdranch.android.criminalintent;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;

/**
 * Created by ghazi on 4/24/2016.
 */
public class PictureUtills {

    public static Bitmap getScaledBitmap(String path, Activity activity) {
        Point size = new Point();
        activity.getWindowManager().getDefaultDisplay()
                .getSize(size);
        return getScaledBitmap(path, size.x, size.y);
    }

    public static Bitmap getScaledBitmap(String path, int destWidth, int destHeight) {
// Read in the dimensions of the image on disk
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);
        int srcWidth = options.outWidth;
        int srcHeight = options.outHeight;
// Figure out how much to scale down by
        int inSampleSize = 1;
        if (srcHeight > destHeight || srcWidth > destWidth) {

            final int halfHeight = srcHeight / 2;
            final int halfWidth = srcWidth / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > destHeight
                    && (halfWidth / inSampleSize) > destWidth) {
                inSampleSize *= 2;
            }
        }
//        if (srcHeight > destHeight || srcWidth > destWidth) {
//            if (srcWidth > srcHeight) {
//                inSampleSize = Math.round(srcHeight / destHeight);
//            } else {
//                inSampleSize = Math.round(srcWidth / destWidth);
//            }
//        }
        options = new BitmapFactory.Options();
        options.inSampleSize = inSampleSize;
// Read in and create final bitmap
        return BitmapFactory.decodeFile(path, options);
    }


}
