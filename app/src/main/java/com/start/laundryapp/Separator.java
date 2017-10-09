package com.start.laundryapp;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class Separator extends RecyclerView.ItemDecoration {

    private Drawable separator;
    private Paint paint;

    public Separator(Context context) {
        separator = ContextCompat.getDrawable(context, R.drawable.divider_line);
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();

        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);

            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();

            int top = child.getBottom() + params.bottomMargin;
            int bottom = top + separator.getIntrinsicHeight();

            separator.setBounds(left, top, right, bottom);
            separator.draw(c);
        }
    }
}


