package com.eighteengray.commonutil;

import android.graphics.drawable.GradientDrawable;

import androidx.annotation.ColorInt;


public class ShapeUtils {

    private volatile static ShapeUtils instance =null;

    private ShapeUtils() {
    }

    public static ShapeUtils getInstance() {
        if (instance == null) {
            synchronized (ShapeUtils.class) {
                if (instance == null) {
                    instance = new ShapeUtils();
                }
            }
        }
        return instance;
    }

    //无边框/圆角/单色
    public GradientDrawable createSingleDrawableNoBorder(float radius, @ColorInt int backgroundColor) {
        GradientDrawable drawable = new GradientDrawable();
        drawable.setCornerRadius(radius);
        drawable.setColor(backgroundColor);
        return drawable;
    }

    //无边框/圆角/渐变色/渐变形状{LINEAR_GRADIENT, RADIAL_GRADIENT, SWEEP_GRADIENT}
    //渐变方向{ TOP_BOTTOM,TR_BL,RIGHT_LEFT,BR_TL,BOTTOM_TOP,BL_TR,LEFT_RIGHT,TL_BR}
    public GradientDrawable createMixDrawableNoBorder(float radius, @ColorInt int[] backgroundColor, int gradientType, GradientDrawable.Orientation orientation) {
        GradientDrawable drawable = new GradientDrawable();
        drawable.setCornerRadius(radius);
        drawable.setGradientType(gradientType);
        drawable.setOrientation(orientation);
        drawable.setColors(backgroundColor);
        return drawable;
    }

    //实线/圆角/单色/
    public GradientDrawable createSingleDrawableFillBorder(float radius, int strokeWidth, int strokeColor, @ColorInt int backgroundColor) {
        GradientDrawable drawable = new GradientDrawable();
        drawable.setCornerRadius(radius);
        drawable.setStroke(strokeWidth, strokeColor);
        drawable.setColor(backgroundColor);
        return drawable;
    }

    //实线/圆角/渐变色/渐变形状{LINEAR_GRADIENT, RADIAL_GRADIENT, SWEEP_GRADIENT}
    //渐变方向{ TOP_BOTTOM,TR_BL,RIGHT_LEFT,BR_TL,BOTTOM_TOP,BL_TR,LEFT_RIGHT,TL_BR}
    public GradientDrawable createMixDrawableFillBorder(float radius, int strokeWidth, int strokeColor, @ColorInt int[] backgroundColor, int gradientType, GradientDrawable.Orientation orientation) {
        GradientDrawable drawable = new GradientDrawable();
        drawable.setCornerRadius(radius);
        drawable.setStroke(strokeWidth, strokeColor);
        drawable.setGradientType(gradientType);
        drawable.setOrientation(orientation);
        drawable.setColors(backgroundColor);
        return drawable;
    }

    //虚线/圆角/单色/
    public GradientDrawable createSingleDrawableDashBorder(float radius, int strokeWidth, int strokeColor, float dashWidth, float dashGap, @ColorInt int backgroundColor) {
        GradientDrawable drawable = new GradientDrawable();
        drawable.setCornerRadius(radius);
        drawable.setStroke(strokeWidth, strokeColor, dashWidth, dashGap);
        drawable.setColor(backgroundColor);
        return drawable;
    }

    //虚线/圆角/渐变色/渐变形状{LINEAR_GRADIENT, RADIAL_GRADIENT, SWEEP_GRADIENT}
    //渐变方向{ TOP_BOTTOM,TR_BL,RIGHT_LEFT,BR_TL,BOTTOM_TOP,BL_TR,LEFT_RIGHT,TL_BR}
    public GradientDrawable createMixDrawableDashBorder(float radius, int strokeWidth, int strokeColor, float dashWidth, float dashGap, @ColorInt int[] backgroundColor, int gradientType, GradientDrawable.Orientation orientation) {
        GradientDrawable drawable = new GradientDrawable();
        drawable.setCornerRadius(radius);
        drawable.setStroke(strokeWidth, strokeColor, dashWidth, dashGap);
        drawable.setGradientType(gradientType);
        drawable.setOrientation(orientation);
        drawable.setColors(backgroundColor);
        return drawable;
    }

    //实线/圆角/渐变色/渐变形状{LINEAR_GRADIENT}
    //渐变方向{LEFT_RIGHT}
    public GradientDrawable createNomalMixDrawableFillBorder(float radius, int strokeWidth, int strokeColor, @ColorInt int[] backgroundColor) {
        GradientDrawable drawable = new GradientDrawable();
        drawable.setCornerRadius(radius);
        drawable.setStroke(strokeWidth, strokeColor);
        drawable.setGradientType(GradientDrawable.LINEAR_GRADIENT);
        drawable.setOrientation(GradientDrawable.Orientation.LEFT_RIGHT);
        drawable.setColors(backgroundColor);
        return drawable;
    }

    public GradientDrawable createDrawable(float topLeftRadius, float topRightRadius, float bottomRightRadius, float bottomLeftRadius, int strokeWidth, int strokeColor, float dashWidth, float dashGap, int gradientType, GradientDrawable.Orientation orientation, @ColorInt int... backgroundColor) {
        GradientDrawable drawable = new GradientDrawable();
        drawable.setCornerRadii(new float[]{topLeftRadius, topLeftRadius, topRightRadius, topRightRadius, bottomRightRadius, bottomRightRadius, bottomLeftRadius, bottomLeftRadius});
        if (strokeWidth >= 0) {
            if (dashWidth <= 0 || dashGap <= 0) {
                drawable.setStroke(strokeWidth, strokeColor);
            } else {
                drawable.setStroke(strokeWidth, strokeColor, dashWidth, dashGap);
            }
        }
        if (-1 != gradientType)
            drawable.setGradientType(gradientType);
        if (null != orientation)
            drawable.setOrientation(orientation);
        if (backgroundColor.length == 1) {
            drawable.setColor(backgroundColor[0]);
        } else {
            drawable.setColors(backgroundColor);
        }
        return drawable;
    }

}