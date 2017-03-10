package com.eighteengray.commonutilslibrary;

import android.view.View;
import java.util.ArrayList;

/**
 * View相关工具类
 */
public class ViewUtils
{

    /**
     * 获取触摸屏幕时的view
     * @param view
     * @param x
     * @param y
     * @return
     */
    public static View getTouchTarget(View view, int x, int y)
    {
        View target = null;
        ArrayList<View> views = view.getTouchables();
        for(View child:views)
        {
            if(isTouchPointInView(view, x, y))
            {
                target = child;
                break;
            }
        }
        return target;
    }


    /**
     * 判断触摸屏幕的点是否在指定View中
     * @param view
     * @param x
     * @param y
     * @return
     */
    public static boolean isTouchPointInView(View view, int x, int y)
    {
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        int left = location[0];
        int top = location[1];
        int right = left + view.getMeasuredWidth();
        int bottom = top + view.getMeasuredHeight();
        if(view.isClickable() && x >= left && x <= right && y >= top && y >= bottom)
        {
            return true;
        }
        return false;
    }

}
