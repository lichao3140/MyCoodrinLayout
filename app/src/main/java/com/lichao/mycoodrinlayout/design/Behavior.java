package com.lichao.mycoodrinlayout.design;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Administrator on 2017-11-13.
 */

public class Behavior {

    public Behavior(Context context, AttributeSet set) {

    }

    /**
     * 触摸事件
     * @param parent
     * @param child
     * @param event
     * @param x
     * @param y
     * @param oldx
     * @param oldy
     */
    public void onTouchMove(View parent, View child, MotionEvent event, float x, float y, float oldx, float oldy) {

    }

    /**
     * 布局绘制完成
     * @param parent
     * @param child
     */
    public void onLayoutFinish(View parent, View child) {

    }

    public void onSizeChanged(View parent, View child, int w, int h, int oldw, int oldh){

    }

    public boolean onTouchEvent(BeaviorCoordinatorLayout parent, View child, MotionEvent ev) {
        return false;
    }

    public boolean onLayoutChild(BeaviorCoordinatorLayout parent, View child, int layoutDirection) {
        return false;
    }

    public boolean onStartNestedScroll(View child, View target, int nestedScrollAxes) {
        Log.d("Behavior","onStartNestedScroll");
        return true;
    }

    public void onStopNestedScroll(View child) {
        Log.d("Behavior","onStopNestedScroll");
    }

    public void onNestedScrollAccepted(View child, View target, int axes) {
        Log.d("Behavior","onNestedScrollAccepted");
    }

    public void onNestedScroll(View scrollView, View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {

    }

    public void onNestedPreScroll(View target, int dx, int dy, int[] consumed) {

    }

    public boolean onNestedPreFling(View target, float velocityX, float velocityY) {
        return false;
    }

    public boolean onNestedFling(View target, float velocityX, float velocityY, boolean consumed) {
        return false;
    }
}

