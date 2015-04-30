package com.leventealbert.mesi;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Shader;

/**
 * class that is being used for the transformation to make the images round with stroke for the avatars
 *
 * @author Levente Albert
 */
public class RoundedTransformation implements com.squareup.picasso.Transformation {
    private final int radius;
    private final int margin;  // dp

    // radius is corner radii in dp
    // margin is the board in dp
    public RoundedTransformation(final int radius, final int margin) {
        this.radius = radius;
        this.margin = margin;
    }

    /**
     * function that does the transformation of the bitmap
     *
     * @param source Bitmap
     * @return Bitmap
     */
    @Override
    public Bitmap transform(final Bitmap source) {

        final Paint paint = new Paint();
        paint.setAntiAlias(true);

        //using shader to be more efficient
        paint.setShader(new BitmapShader(source, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));
        paint.setColorFilter(new PorterDuffColorFilter(Color.WHITE, PorterDuff.Mode.DST_OVER));

        Bitmap output = Bitmap.createBitmap(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(output);
        canvas.drawCircle((source.getWidth() - margin) / 2, (source.getHeight() - margin) / 2, radius - 2, paint);

        if (source != output) {
            source.recycle();
        }

        //adding the stroke to the image
        Paint paint1 = new Paint();
        paint1.setColor(Color.LTGRAY);
        paint1.setStyle(Paint.Style.STROKE);
        paint1.setAntiAlias(true);
        paint1.setStrokeWidth(1);
        canvas.drawCircle((source.getWidth() - margin) / 2, (source.getHeight() - margin) / 2, radius - 2, paint1);


        return output;
    }

    /**
     * return the key
     *
     * @return String
     */
    @Override
    public String key() {
        return "rounded";
    }
}
