package com.santiago.canchaapp.app.viewholder;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

public class FotoCanchaView extends android.support.v7.widget.AppCompatImageView {

    public FotoCanchaView(Context context) {
        super(context);
    }

    public FotoCanchaView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FotoCanchaView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }
}