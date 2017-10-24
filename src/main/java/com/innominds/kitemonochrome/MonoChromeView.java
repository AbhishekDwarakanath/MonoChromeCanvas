package com.innominds.kitemonochrome;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by adwarakanath on 22/8/17.
 */
public class MonoChromeView extends View {
    public MonoChromeView(Context context) {
        super(context);
    }

    public MonoChromeView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MonoChromeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Paint pBackground = new Paint();
        pBackground.setColor(Color.BLACK);
        canvas.drawRect(200, 100, 1260, 720, pBackground);
        Paint pText = new Paint();
        pText.setColor(Color.GREEN);
        pText.setTextSize(50);
        canvas.drawText("Menu", 630, 700, pText);

    }
}
