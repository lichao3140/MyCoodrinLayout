package com.lichao.mycoodrinlayout;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import com.lichao.mycoodrinlayout.design.Behavior;

/**
 * Created by Administrator on 2017-11-13.
 */

public class ToolBarBehavior extends Behavior {
    private int maxHeight=400;

    public ToolBarBehavior(Context context, AttributeSet set) {
        super(context, set);
    }

    /**
     * 进行透明度变换
     * @param scrollView
     * @param target
     * @param dxConsumed
     * @param dyConsumed
     * @param dxUnconsumed
     * @param dyUnconsumed
     */
    @Override
    public void onNestedScroll(View scrollView, View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        super.onNestedScroll(scrollView, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed);
        if (scrollView.getScrollY() <= maxHeight) {
            //改变透明度
            ViewCompat.setAlpha(target,scrollView.getScrollY()*1.0f/maxHeight);
        } else if (scrollView.getScrollY() == 0) {
            ViewCompat.setAlpha(target,0);
        }
    }
}
