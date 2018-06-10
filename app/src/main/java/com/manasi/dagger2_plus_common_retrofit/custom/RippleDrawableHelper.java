package com.manasi.dagger2_plus_common_retrofit.custom;


import android.content.res.ColorStateList;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RippleDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.util.Log;
import android.view.View;


/*class to give ripple effect to relative layout. (title bar back button)*/

public class RippleDrawableHelper {

    private static final String TAG = "RippleDrawableHelper";


    public static Drawable createRippleDrawable(final View v, final int color) {
        return createRippleDrawable(v, color, null);
    }

    public static Drawable createRippleDrawable(final View v, final int color, int drawableResource) {
        return createRippleDrawable(v, color, v.getContext().getResources().getDrawable(drawableResource));
    }

    public static Drawable createRippleDrawable(final View v, final int color, Drawable pressed) {

        try {
            Drawable drawable = v.getBackground();

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

                if (drawable instanceof RippleDrawable) {
                    drawable = ((RippleDrawable) drawable).getDrawable(0);

                    RippleDrawable rippleDrawable = new RippleDrawable(new ColorStateList(
                            new int[][]{
                                    new int[]{android.R.attr.state_pressed},
                                    new int[]{0}
                            },
                            new int[]{color, 0}
                    ), drawable, null);


                    return rippleDrawable;
                } else {
                    if (drawable == null) {
                        drawable = new ColorDrawable(0);
                        v.setBackground(drawable);
                        RippleDrawable rippleDrawable = new RippleDrawable(new ColorStateList(
                                new int[][]{
                                        new int[]{android.R.attr.state_pressed},
                                        new int[]{0}
                                },
                                new int[]{color, 0}
                        ), drawable, new ColorDrawable(0xffffffff));
                        return rippleDrawable;
                    } else {
                        RippleDrawable rippleDrawable = new RippleDrawable(new ColorStateList(
                                new int[][]{
                                        new int[]{android.R.attr.state_pressed},
                                        new int[]{0}
                                },
                                new int[]{color, 0}
                        ), drawable, null);
                        return rippleDrawable;
                    }
                }
            } else {

                if (drawable == null) {
                    drawable = new ColorDrawable(color);
                }

                if (pressed == null) {
                    pressed = drawable;
                }


                StateListDrawable sld = new StateListDrawable();
                sld.addState(
                        new int[]{
                                android.R.attr.state_pressed,
                        },
                        pressed
                );


                if (v.getBackground() != null) {
                    sld.addState(
                            new int[]{
                                    0,
                            },
                            drawable
                    );
                }

                return sld;
            }
        } catch (Exception e) {
            Log.i(TAG, "createRippleDrawable: " + e.getLocalizedMessage());
            return null;
        }
    }


}
