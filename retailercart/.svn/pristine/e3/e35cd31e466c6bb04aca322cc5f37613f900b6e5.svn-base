/*
 * Copyright (C) 2016 Botree Software International Private Limited
 */

package com.botree.retailerssfa.base;


import android.util.Log;
import android.view.MenuItem;

import androidx.fragment.app.Fragment;

import com.botree.retailerssfa.db.SFADatabase;
import com.botree.retailerssfa.util.SFASharedPref;
import com.botree.retailerssfa.R;


public abstract class SFAFragment extends Fragment {

    private static final String TAG = SFAFragment.class.getSimpleName();

    public SFAActivity getSFAFragmentActivity() {
        return (SFAActivity) getActivity();
    }

    public BaseActivity getSFABaseFragmentActivity() {
        return (BaseActivity) getActivity();
    }

    public void setAutoScreenCount(String screenName) {
        try {

            SFASharedPref preferences = SFASharedPref.getOurInstance();
            SFADatabase db = SFADatabase.getInstance(getActivity());
            assert preferences != null;
            int screenCount = db.getAutoQuickMenuCount(screenName);
            db.insertOrUpdateAutoQuickActions(screenName, screenCount);
        } catch (Exception e) {
            Log.e(TAG, "setAutoScreenCount: " + e.getMessage(), e);
        }
    }

    public void setFavoriteIconBasedOnPref(MenuItem favorite, String screenName) {

        SFASharedPref preferences = SFASharedPref.getOurInstance();
        SFADatabase db = SFADatabase.getInstance(getActivity());
        assert preferences != null;
        favorite.setVisible(false);
        if (db.isFavoriteMenu(screenName)) {
            favorite.setIcon(R.drawable.ic_favorite_star_fill);
        } else {
            favorite.setIcon(R.drawable.ic_favorite_star);
        }
    }

}
