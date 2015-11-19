package com.hankkin.weixingroupicondemo.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.util.Log;

import com.hankkin.weixingroupicondemo.model.BitmapBean;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * Created by Hankkin on 15/11/19.
 */
public class BitmapUtils {

    public static  Drawable getScaleDraw(String imgPath, Context mContext) {

        Bitmap bitmap = null;
        try {
            Log.d("BitmapUtil",
                    "[getScaleDraw]imgPath is " + imgPath.toString());
            File imageFile = new File(imgPath);
            if (!imageFile.exists()) {
                Log.d("BitmapUtil", "[getScaleDraw]file not  exists");
                return null;
            }
            BitmapFactory.Options opts = new BitmapFactory.Options();
            opts.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(imgPath, opts);

            opts.inSampleSize = computeSampleSize(opts, -1, 800 * 480);
            // Log.d("BitmapUtil","inSampleSize===>"+opts.inSampleSize);
            opts.inJustDecodeBounds = false;
            bitmap = BitmapFactory.decodeFile(imgPath, opts);

        } catch (OutOfMemoryError err) {
            Log.d("BitmapUtil", "[getScaleDraw] out of memory");

        }
        if (bitmap == null) {
            return null;
        }
        Drawable resizeDrawable = new BitmapDrawable(mContext.getResources(),
                bitmap);
        return resizeDrawable;
    }

    /**
     * 保存Bitmap图片
     * @param mContext  上下文
     * @param bitmap
     * @param desName   名字
     * @throws IOException
     */
    public static void saveMyBitmap(Context mContext, Bitmap bitmap,
                                    String desName) throws IOException {
        FileOutputStream fOut = null;

        if (Environment.getExternalStorageState() != Environment.MEDIA_MOUNTED) {
            fOut = mContext.openFileOutput(desName + ".png",
                    Context.MODE_PRIVATE);
        } else {
            File f = new File(Environment.getExternalStorageDirectory()
                    .getPath() + "/Asst/cache/" + desName + ".png");
            f.createNewFile();
            fOut = new FileOutputStream(f);
        }
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
        try {
            fOut.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            fOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 生成缩略图
     * @param res
     * @param id
     * @return
     */
     public static Bitmap getScaleBitmap(Resources res, int id) {

        Bitmap bitmap = null;
        try {
            BitmapFactory.Options opts = new BitmapFactory.Options();
            opts.inJustDecodeBounds = true;
            BitmapFactory.decodeResource(res, id, opts);

            opts.inSampleSize = computeSampleSize(opts, -1, 800 * 480);
            opts.inJustDecodeBounds = false;
            bitmap = BitmapFactory.decodeResource(res, id, opts);
        } catch (OutOfMemoryError err) {
        }
        return bitmap;
    }

    /**
     * 压缩bitmap，防止oom
     * @param options
     * @param minSideLength
     * @param maxNumOfPixels
     * @return
     */
    public static int computeSampleSize(BitmapFactory.Options options,
                                        int minSideLength, int maxNumOfPixels) {

        int initialSize = computeInitialSampleSize(options, minSideLength,
                maxNumOfPixels);
        int roundedSize;
        if (initialSize <= 8) {
            roundedSize = 1;
            while (roundedSize < initialSize) {
                roundedSize <<= 1;
            }
        } else {
            roundedSize = (initialSize + 7) / 8 * 8;
        }

        return roundedSize;

    }

    /**
     * 压缩bitmap
     * @param options
     * @param minSideLength
     * @param maxNumOfPixels
     * @return
     */
    private static int computeInitialSampleSize(BitmapFactory.Options options,
                                                int minSideLength, int maxNumOfPixels) {

        double w = options.outWidth;
        double h = options.outHeight;
        int lowerBound = (maxNumOfPixels == -1) ? 1 : (int) Math.ceil(Math
                .sqrt(w * h / maxNumOfPixels));
        int upperBound = (minSideLength == -1) ? 128 : (int) Math.min(
                Math.floor(w / minSideLength), Math.floor(h / minSideLength));

        if (upperBound < lowerBound) {
            // return the larger one when there is no overlapping zone.
            return lowerBound;
        }

        if ((maxNumOfPixels == -1) && (minSideLength == -1)) {
            return 1;
        } else if (minSideLength == -1) {
            return lowerBound;
        } else {
            return upperBound;
        }

    }

    /**
     * 获得合在一起的bitmap
     * @param mEntityList
     * @param bitmaps
     * @return
     */
    public static Bitmap getCombineBitmaps(List<BitmapBean> mEntityList,
                                           Bitmap... bitmaps) {
        Bitmap newBitmap = Bitmap.createBitmap(200, 200, Bitmap.Config.ARGB_8888);
        for (int i = 0; i < mEntityList.size(); i++) {
            bitmaps[i] = GetRoundedCornerBitmap(bitmaps[i]);
            newBitmap = mixtureBitmap(newBitmap, bitmaps[i], new PointF(
                    mEntityList.get(i).getX(), mEntityList.get(i).getY()));
        }
        return newBitmap;
    }

    public static Bitmap combineBitmaps(int columns, Bitmap... bitmaps) {
        if (columns <= 0 || bitmaps == null || bitmaps.length == 0) {
            throw new IllegalArgumentException(
                    "Wrong parameters: columns must > 0 and bitmaps.length must > 0.");
        }
        int maxWidthPerImage = 20;
        int maxHeightPerImage = 20;
        for (Bitmap b : bitmaps) {
            maxWidthPerImage = maxWidthPerImage > b.getWidth() ? maxWidthPerImage
                    : b.getWidth();
            maxHeightPerImage = maxHeightPerImage > b.getHeight() ? maxHeightPerImage
                    : b.getHeight();
        }
        int rows = 0;
        if (columns >= bitmaps.length) {
            rows = 1;
            columns = bitmaps.length;
        } else {
            rows = bitmaps.length % columns == 0 ? bitmaps.length / columns
                    : bitmaps.length / columns + 1;
        }
        Bitmap newBitmap = Bitmap.createBitmap(columns * maxWidthPerImage, rows
                * maxHeightPerImage, Bitmap.Config.ARGB_8888);
        for (int x = 0; x < rows; x++) {
            for (int y = 0; y < columns; y++) {
                int index = x * columns + y;
                if (index >= bitmaps.length)
                    break;
                newBitmap = mixtureBitmap(newBitmap, bitmaps[index],
                        new PointF(y * maxWidthPerImage, x * maxHeightPerImage));
            }
        }
        return newBitmap;
    }

    /**
     * 画bitmap
     * @param first
     * @param second
     * @param fromPoint
     * @return
     */
    public static Bitmap mixtureBitmap(Bitmap first, Bitmap second,
                                       PointF fromPoint) {
        if (first == null || second == null || fromPoint == null) {
            return null;
        }
        Bitmap newBitmap = Bitmap.createBitmap(first.getWidth(),
                first.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas cv = new Canvas(newBitmap);
        cv.drawBitmap(first, 0, 0, null);
        cv.drawBitmap(second, fromPoint.x, fromPoint.y, null);
        cv.save(Canvas.ALL_SAVE_FLAG);  //保存全部图层
        cv.restore();
        return newBitmap;
    }

    /**
     * 获取圆形的bitmap
     * @param bitmap
     * @return
     */
    public static Bitmap GetRoundedCornerBitmap(Bitmap bitmap) {
        try {
            Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                    bitmap.getHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(output);
            final Paint paint = new Paint();
            final Rect rect = new Rect(0, 0, bitmap.getWidth(),
                    bitmap.getHeight());
            final RectF rectF = new RectF(new Rect(0, 0, bitmap.getWidth(),
                    bitmap.getHeight()));
            final float roundPx = 50;
            paint.setAntiAlias(true);
            canvas.drawARGB(0, 0, 0, 0);
            paint.setColor(Color.BLACK);
            canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));

            final Rect src = new Rect(0, 0, bitmap.getWidth(),
                    bitmap.getHeight());

            canvas.drawBitmap(bitmap, src, rect, paint);
            return output;
        } catch (Exception e) {
            return bitmap;
        }
    }
}
