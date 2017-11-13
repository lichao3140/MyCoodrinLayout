package com.lichao.mycoodrinlayout.design;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.view.NestedScrollingParent;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.RelativeLayout;
import com.lichao.mycoodrinlayout.R;
import java.lang.reflect.Constructor;

/**
 * Created by Administrator on 2017-11-13.
 */

public class BeaviorCoordinatorLayout extends RelativeLayout implements ViewTreeObserver.OnGlobalLayoutListener , NestedScrollingParent {
    public float lastX;
    public float lastY;

    public BeaviorCoordinatorLayout(Context context) {
        super(context);
    }

    public BeaviorCoordinatorLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BeaviorCoordinatorLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Log.d("lichao", "getHeight-->" + getHeight() + "h-->" + h + "oldf-->" + oldh);
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            LayoutParams params = (LayoutParams) child.getLayoutParams();
            if (params.getBehavior() != null) {
                params.getBehavior().onSizeChanged(this, child, w, h, oldw, oldh);
            }
        }
    }

    /**
     * 设置监听时一定要注意
     * 必须当前绘制完成onFinishInflate
     * 设置监听
     */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        getViewTreeObserver().addOnGlobalLayoutListener(this);
    }

    /**
     * 字符串  --》反射实例化
     * @param attrs
     * @return
     */
    @Override
    public RelativeLayout.LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new LayoutParams(getContext(),attrs);
    }

    /**
     * ----------------布局发生变换事件------------------------------
     * @return
     */
    @Override
    public void onGlobalLayout() {
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            //重写了的LayoutParams
            LayoutParams layoutParams= (LayoutParams) child.getLayoutParams();
            if (layoutParams.getBehavior() != null) {
                layoutParams.getBehavior().onLayoutFinish(this,child);
            }
        }
    }

    /**
     * ----------------触摸事件------------------------------
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastX=event.getRawX();
                lastY=event.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                onTouchMove(event);
                break;
        }
        return super.onTouchEvent(event);
    }

    private void onTouchMove(MotionEvent event) {
        float moveX = event.getRawX();
        float moveY = event.getRawY();
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            LayoutParams params = (LayoutParams) child.getLayoutParams();
            if (params.getBehavior() != null) {
                params.getBehavior().onTouchMove(this, child, event, moveX, moveY, lastX, lastY);
            }
        }
        lastY = moveY;
        lastX = moveX;
    }

    /**
     * ----------------滚动事件------------------move------------
     * move 肯定是拿不到
     *  一定返回 true
     * 实现了  NestedScrolling机制的滚动控件
     * @return
     */
    @Override
    public boolean onStartNestedScroll(View child, View target, int nestedScrollAxes) {
        return true;
    }

    @Override
    public void onStopNestedScroll(View child) {

    }

    /**
     *
     * @param target  同上
     * @param dxConsumed  表示target已经消费的x方向的距离
     * @param dyConsumed  表示target已经消费的y方向的距离
     * @param dxUnconsumed  表示x方向剩下的滑动距离
     * @param dyUnconsumed  表示y方向剩下的滑动距离
     */
    @Override
    public void onNestedScroll(View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            LayoutParams param = (LayoutParams) child.getLayoutParams();
            if (param.getBehavior() != null) {
                param.getBehavior().onNestedScroll(target ,child, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed);
            }
        }
    }

    @Override
    public void onNestedScrollAccepted(View child, View target, int axes) {

    }

    /**
     *
     * @param target
     * @param dx  表示target本次滚动产生的x方向的滚动总距离
     * @param dy  表示target本次滚动产生的y方向的滚动总距离
     * @param consumed  表示父布局要消费的滚动距离,consumed[0]和consumed[1]分别表示父布局在x和y方向上消费的距离.
     */
    @Override
    public void onNestedPreScroll(View target, int dx, int dy, int[] consumed) {

    }

    /**
     * onNestedFling可以捕获对内部View的fling事件，如果return true则表示拦截掉内部View的事件
     * @param target
     * @param velocityX
     * @param velocityY
     * @return
     */
    @Override
    public boolean onNestedPreFling(View target, float velocityX, float velocityY) {
        return false;
    }

    @Override
    public boolean onNestedFling(View target, float velocityX, float velocityY, boolean consumed) {
        return  false;
    }

    public static class LayoutParams extends RelativeLayout.LayoutParams {
        private static final String TAG = "touch";
        private Behavior behavior;

        public Behavior getBehavior() {
            return behavior;
        }

        public LayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
            final TypedArray a = c.obtainStyledAttributes(attrs, R.styleable.BeaviorCoordinatorLayout);
            behavior = parseBehavior(c, attrs, a.getString(R.styleable.BeaviorCoordinatorLayout_layout_behavior));
            Log.i(TAG, "LayoutParams:   名字   "+ a.getString(R.styleable.BeaviorCoordinatorLayout_layout_behavior));
            a.recycle();
        }

        private Behavior parseBehavior(Context context, AttributeSet attrs, String name) {
            if (TextUtils.isEmpty(name)) {
                return null;
            }
            try {
                final Class clazz = Class.forName(name, true, context.getClassLoader());
                Constructor c = clazz.getConstructor(new Class[]{Context.class,AttributeSet.class});
                c.setAccessible(true);
                return (Behavior) c.newInstance(context, attrs);
            } catch (Exception e) {
                throw new RuntimeException("Could not inflate Behavior subclass " + name, e);
            }
        }

        public LayoutParams(int w, int h) {
            super(w, h);
        }

        public LayoutParams(ViewGroup.LayoutParams source) {
            super(source);
        }

        public LayoutParams(MarginLayoutParams source) {
            super(source);
        }

        public LayoutParams(RelativeLayout.LayoutParams source) {
            super(source);
        }
    }
}
