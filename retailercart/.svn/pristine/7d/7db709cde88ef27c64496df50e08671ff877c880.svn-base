package com.botree.retailerssfa.support;


import android.annotation.SuppressLint;
import android.util.Log;

import com.botree.retailerssfa.db.FileAccessUtil;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.lang.reflect.Field;

/**
 * Created by shantarao on 8/1/18.
 */

public class BottomNavigationViewHelper {
    private BottomNavigationViewHelper() {

    }

    @SuppressLint("RestrictedApi")
    public static void disableShiftMode(BottomNavigationView view) {
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) view.getChildAt(0);
        try {
            Field shiftingMode = menuView.getClass().getDeclaredField("mShiftingMode");
            FileAccessUtil.getInstance().setAccessibleField(shiftingMode, true);
            FileAccessUtil.getInstance().setBooleanField(shiftingMode, menuView, false);
            FileAccessUtil.getInstance().setAccessibleField(shiftingMode, false);
            for (int i = 0; i < menuView.getChildCount(); i++) {
                BottomNavigationItemView item = (BottomNavigationItemView) menuView.getChildAt(i);
                //noinspection RestrictedApi
//                item.setShiftingMode(false);
                // set once again checked value, so view will be updated
                //noinspection RestrictedApi
                item.setChecked(item.getItemData().isChecked());
            }
        } catch (NoSuchFieldException e) {
            Log.e("BNVHelper", "Unable to get shift mode field", e);
        } catch (Exception e) {
            Log.e("BNVHelper", "Unable to change value of shift mode", e);
        }
    }
}
