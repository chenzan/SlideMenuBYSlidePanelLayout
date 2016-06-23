package com.chzan.slidemenu.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.support.v4.widget.SlidingPaneLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by chenzan on 2016/6/23.
 */
public class SlideMenuPaneLayout extends SlidingPaneLayout {
    private View menuPanel;
    private float mSlideOffset;
    private boolean isSliding = false;

    public SlideMenuPaneLayout(Context context) {
        this(context, null);
    }

    public SlideMenuPaneLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SlideMenuPaneLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
        setPanelSlideListener(new PanelSlideListener() {
            @Override
            public void onPanelSlide(View panel, float slideOffset) {
                mSlideOffset = slideOffset;
                isSliding = true;
                Log.e("menu", "slideOffset:" + slideOffset);
                final float scaleLeft = 1 - 0.3f * (1 - slideOffset);
                menuPanel.setPivotX(-0.3f * menuPanel.getWidth());
                menuPanel.setPivotY(menuPanel.getHeight() / 2f);
                menuPanel.setScaleX(scaleLeft);
                menuPanel.setScaleY(scaleLeft);
                //panel 指的是拖动的view
                final float scale = 1 - 0.2f * slideOffset;
                panel.setPivotX(0);
                panel.setPivotY(panel.getHeight() / 2.0f);
                panel.setScaleX(scale);
                panel.setScaleY(scale);
            }

            @Override
            public void onPanelOpened(View panel) {

            }

            @Override
            public void onPanelClosed(View panel) {

            }
        });
        setSliderFadeColor(0);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (isSliding)//记录一个滑动状态 只有需要重绘的时候才去绘制透明度
            dimOnForeground(canvas);
    }

    @Override
    protected boolean drawChild(Canvas canvas, View child, long drawingTime) {
        boolean b = super.drawChild(canvas, child, drawingTime);
        if (child == menuPanel && isSliding) {
            dimOnForeground(canvas);
        }
        return b;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        //布局中第一个view就是要滑出的view
        menuPanel = getChildAt(0);
    }

    private void dimOnForeground(Canvas canvas) {
        canvas.drawColor(Color.argb((int) (0xff * (1 - mSlideOffset)), 0, 0, 0));
    }
}
