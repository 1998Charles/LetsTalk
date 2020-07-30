package com.example.letstalk.ui.login;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

/**
 * @author Xiaoxi Jia
 * @version July 2020
 */

/**
 * Class that extends the ScrollView class to allow Intercept touch for multiple Scrollview
 */

public class MyScrollView extends ScrollView {
    public MyScrollView(Context context) {
        this(context,null);
    }
    public MyScrollView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }
    public MyScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * Method overrides the onInterceptTouchEvent method
     */

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        getParent().requestDisallowInterceptTouchEvent(true);
        return super.onInterceptTouchEvent(ev);
    }
}