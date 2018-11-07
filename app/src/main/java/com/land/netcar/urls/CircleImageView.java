package com.land.netcar.urls;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * 圆角图片
 *
 * @author zyh
 */
public class CircleImageView extends ImageView {

    public CircleImageView(Context context) {
        super(context);
    }

    public CircleImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CircleImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    private int bWidth = 10;// 边框宽度
    private int bColor = Color.WHITE;// 边框颜色

    /**
     * 设置圆形图片的边框颜色
     *
     * @param color
     */
    public void setBorderColor(int color) {
        bColor = color;
    }

    /**
     * 设置圆形图片的边框宽度
     *
     * @param
     */
    public void setBorderWidth(int width) {
        bWidth = width;
    }

    /**
     * 设置圆形图片的边框宽度和颜色
     *
     * @param
     */
    public void setBorderWidth(int width, int color) {
        bWidth = width;
        bColor = color;
    }

    @Override
    public void setImageBitmap(Bitmap bm) {

        int d = Math.min(bm.getWidth(), bm.getHeight());// 或得圆直径
        Bitmap dest = Bitmap.createBitmap(d, d, bm.getConfig());// 创建一个副本
        // 画边框
        Canvas c = new Canvas(dest);
        Paint paint = new Paint();
        paint.setAntiAlias(true);// 设置抗锯齿
        c.drawCircle(d / 2, d / 2, d / 2, paint);
        // 画圆
        Path path = new Path();
        path.addCircle(d / 2, d / 2, d / 2 - bWidth, Path.Direction.CW);
        c.clipPath(path); // 裁剪区域

        Matrix matrix = new Matrix();// 不缩放
        c.drawBitmap(bm, matrix, paint);// 把图画上去
        super.setImageBitmap(dest);
    }

}