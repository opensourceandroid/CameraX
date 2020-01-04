package com.example.link_two_viewpager;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.viewpager.widget.ViewPager;

/**
 * @author hjy
 * @date 2019/11/16
 * @des https://stackoverflow.com/questions/16107016/synchronizing-two-viewpagers-using-onpagechangelistener/24104748
 * #24104748
 * https://www.cnblogs.com/buyishi/p/11793919.html
 */
public class CustomPager extends ViewPager {
    CustomPager mCustomPager;
    private boolean forSuper;

    public CustomPager(Context context) {
        super(context);
    }

    public CustomPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent arg0) {
        if (!forSuper) {

            mCustomPager.forSuper(true);
            mCustomPager.onInterceptTouchEvent(arg0);
            mCustomPager.forSuper(false);
        }
        return super.onInterceptTouchEvent(arg0);
    }

    @Override
    public boolean onTouchEvent(MotionEvent arg0) {
        if (!forSuper) {
            mCustomPager.forSuper(true);
            mCustomPager.onTouchEvent(arg0);
            mCustomPager.forSuper(false);
        }
        return super.onTouchEvent(arg0);
    }

    public void setViewPager(CustomPager customPager) {
        mCustomPager = customPager;
    }

    public void forSuper(boolean forSuper) {
        this.forSuper = forSuper;
    }

    @Override
    public void setCurrentItem(int item, boolean smoothScroll) {
        if (!forSuper) {
            mCustomPager.forSuper(true);
            mCustomPager.setCurrentItem(item, smoothScroll);
            mCustomPager.forSuper(false);
        }
        super.setCurrentItem(item, smoothScroll);
    }

    @Override
    public void setCurrentItem(int item) {
        if (!forSuper) {
            mCustomPager.forSuper(true);
            mCustomPager.setCurrentItem(item);
            mCustomPager.forSuper(false);
        }
        super.setCurrentItem(item);

    }

}