package com.lichao.mycoodrinlayout;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import com.lichao.mycoodrinlayout.design.Behavior;

/**
 * Created by Administrator on 2017-11-13.
 */

public class ImageBehavior extends Behavior {
    private static final String TAG = "touch";
    private  int maxHeight=400;
    private  int originHeight;

    public ImageBehavior(Context context, AttributeSet set) {
        super(context, set);
    }

    @Override
    public void onLayoutFinish(View parent, View child) {
        super.onLayoutFinish(parent, child);
        if (originHeight == 0) {
            originHeight=child.getHeight();
        }
    }

    /**
     * 滚动方法在这里进行缩放
     * @param scrollView
     * @param target
     * @param dxConsumed
     * @param dyConsumed
     * @param dxUnconsumed
     * @param dyUnconsumed
     */
    @Override
    public void onNestedScroll(View scrollView, View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        Log.i(TAG, "onNestedScroll: "+scrollView.getScrollY()+"   dyConsumed  "+ dyConsumed);
        if (scrollView.getScrollY() > 0) {
            ViewGroup.LayoutParams params = target.getLayoutParams();
            Log.i(TAG, "onNestedScroll: parmas.height  "+params.height+"  originHeight  "+originHeight);
            params.height = params.height - Math.abs(dyConsumed);
            if (params.height < originHeight) {
                params.height = originHeight;
            }
            target.setLayoutParams(params);
        } else if (scrollView.getScrollY() == 0) {
            ViewGroup.LayoutParams params = target.getLayoutParams();
            params.height = params.height+ Math.abs(dyUnconsumed);
            if(params.height>= maxHeight){
                params.height =maxHeight;
            }
            target.setLayoutParams(params);
        }
    }
}
