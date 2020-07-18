package com.example.gin.elder2;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.View;

/**
 * Created by GIN on 2018/3/5.
 */

public class SquareView extends View {

    private static final String TAG = "SquareView";

    public SquareView(Context context) {
        super(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.d(TAG, "畫框");
        // 黃色的線
        Paint p = new Paint();
        p.setColor(Color.YELLOW);
        // 方框
        float w = canvas.getWidth();
        float h = canvas.getHeight();
        // 上下左右四邊
        int margin = 90;
        canvas.drawLine(margin, margin, w - margin, margin, p);
        canvas.drawLine(margin, h - margin, w - margin, h - margin, p);
        canvas.drawLine(margin, margin, margin, h - margin, p);
        canvas.drawLine(w - margin, margin, w - margin, h - margin, p);
    }
}