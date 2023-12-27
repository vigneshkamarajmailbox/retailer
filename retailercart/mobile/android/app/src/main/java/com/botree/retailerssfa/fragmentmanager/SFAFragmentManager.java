/*
 * Copyright (C) 2016 Botree Software International Private Limited
 */

package com.botree.retailerssfa.fragmentmanager;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.botree.retailerssfa.R;
import com.botree.retailerssfa.base.SFAActivity;
import com.botree.retailerssfa.base.SFAFragment;
import com.botree.retailerssfa.main.AboutUsFragment;
import com.botree.retailerssfa.main.ChangePasswordFragment;
import com.botree.retailerssfa.main.DashboardNavigationFragment;
import com.botree.retailerssfa.main.DistributorInfoFragment;
import com.botree.retailerssfa.main.MessageFragment;
import com.botree.retailerssfa.main.OrderBookingFragment;
import com.botree.retailerssfa.main.QuickOrderBookingFragment;
import com.botree.retailerssfa.main.RetailerOrderSummaryFragment;
import com.botree.retailerssfa.main.SalesmanDaySummary;
import com.botree.retailerssfa.main.SettingsFragment;
import com.botree.retailerssfa.main.ShowRetailerInMapFragment;
import com.botree.retailerssfa.main.TransactionFragment;
import com.botree.retailerssfa.main.UpdateLocationFragment;
import com.botree.retailerssfa.support.Globals;
import com.botree.retailerssfa.util.SFASharedPref;
import com.crashlytics.android.Crashlytics;

/**
 * Its is Singleton class which is used to do all functionality related to Fragments
 * <ul>
 * <li>Getting the fragment manager</ol>
 * <li>Adding a fragment</li>
 * <li>Adding fragment to Fragment manager back stack</ol>
 * <li>Getting the tag of fragment</ol>
 * <li>Getting the fragment using tag</ol>
 * <li>Getting current fragment which is displaying</ol>
 * <li>Replace the fragment</ol>
 * <li>Removing all the fragments from back stack</ol>
 * <li>Removing current fragment from back stack</ol>
 * </ul>
 */
public class SFAFragmentManager {

    private static final String TAG = SFAFragmentManager.class.getSimpleName();
    /**
     * Fragment manager instance
     */
    private static SFAFragmentManager sfaFragManger;

    private SFAFragmentManager() {
    }

    /**
     * {@link SFAFragmentManager} singleton instance will be returned
     *
     * @return {@link SFAFragmentManager} instance
     */
    public static SFAFragmentManager newInstance() {

        if (sfaFragManger == null) {
            sfaFragManger = new SFAFragmentManager();
        }

        return sfaFragManger;

    }

    /**
     * Return the value of {@link ESFAFragTags}
     *
     * @param fragTags used to get the value of {@link ESFAFragTags}
     * @return String value of {@link ESFAFragTags}
     */
    private String getFragTag(ESFAFragTags fragTags) {
        if (fragTags == null)
            return null;
        return fragTags.getValue();
    }

    /**
     * Return the fragment of give {@link ESFAFragTags} tag
     *
     * @param fragTags used to get Fragment of {@link SFAFragment}
     * @return {@link SFAFragment}
     */
    private SFAFragment getFragment(ESFAFragTags fragTags) {

        switch (fragTags) {
            case DASHBOARD_NAVIGATION:
                Globals.getOurInstance().setIsSubTitleShow(false);
                return new DashboardNavigationFragment();

            case TRANSACTION:
                Globals.getOurInstance().setIsSubTitleShow(false);
                return new TransactionFragment();

            case SETTINGS:
                Globals.getOurInstance().setIsSubTitleShow(false);
                return new SettingsFragment();

            case ORDER_BOOKING:
                Globals.getOurInstance().setIsSubTitleShow(false);
                return new OrderBookingFragment();

            case QUICK_ORDER_BOOKING:
                Globals.getOurInstance().setIsSubTitleShow(false);
                return new QuickOrderBookingFragment();

            case RETAILER_ORDER_BOOKING_SUMMARY:
                Globals.getOurInstance().setIsSubTitleShow(false);
                return new RetailerOrderSummaryFragment();

            case UPDATE_LOCATION:
                Globals.getOurInstance().setIsSubTitleShow(false);
                return new UpdateLocationFragment();

            case ABOUT_US:
                Globals.getOurInstance().setIsSubTitleShow(false);
                return new AboutUsFragment();

            case CHANGE_PASSWORD:
                Globals.getOurInstance().setIsSubTitleShow(false);
                return new ChangePasswordFragment();

            case MESSAGES:
                Globals.getOurInstance().setIsSubTitleShow(false);
                return new MessageFragment();

            case SHOW_RETAILERS_IN_MAP:
                Globals.getOurInstance().setIsSubTitleShow(false);
                return new ShowRetailerInMapFragment();

            case DISTRIBUTOR_INFO:
                Globals.getOurInstance().setIsSubTitleShow(false);
                return new DistributorInfoFragment();

            case DAY_SUMMARY:
                Globals.getOurInstance().setIsSubTitleShow(false);
                return new SalesmanDaySummary();

            case ORDER_BOOKING_SUMMARY:
//                return new OrderSummaryReportFragment();

            default:
                return null;
        }
    }

    /**
     * Get the current fragment from fragment manager
     *
     * @param activity used to get Fragment manager
     * @return {@link SFAFragment}
     */
    public SFAFragment getCurrentFragment(SFAActivity activity) {
        return getCurrentFragment(getFragmentManager(activity));
    }

    /**
     * Update the fragment in fragment manager
     *
     * @param iEnum            Tag of which fragment need to update
     * @param isNeedBackStack  if {@code true} then fragment is added to back stack else not
     * @param fragmentActivity used to get the fragment manager
     * @return true if fragment updated successfully else false
     */
    public boolean updateFragment(IEnum iEnum, boolean isNeedBackStack, SFAActivity fragmentActivity) {
        return updateFragWithBundle(iEnum, isNeedBackStack, fragmentActivity, null, null);
    }

    /**
     * Update the fragment in fragment manager
     *
     * @param iEnum            Tag of which fragment need to update
     * @param isNeedBackStack  if {@code true} then fragment is added to back stack else not
     * @param fragmentActivity used to get the fragment manager
     * @param bundle           is set to the fragment as a argument
     * @param container        for replacing fragment
     * @return true if fragment updated successfully else false
     */
    private boolean updateFragWithBundle(IEnum iEnum, boolean isNeedBackStack, SFAActivity fragmentActivity, Bundle bundle, View container) {

        SFAFragment sfaFragment = getFragment((ESFAFragTags) iEnum);
        SFASharedPref sfaSharedPref = SFASharedPref.getOurInstance();
        String tag = getFragTag((ESFAFragTags) iEnum);
        if (sfaFragment != null) {
            if (container != null) {
                sfaFragment.setArguments(bundle);
                replaceFragment(sfaFragment, fragmentActivity, tag, isNeedBackStack, container, ((ESFAFragTags) iEnum).getId());
            } else {
                sfaFragment.setArguments(bundle);
                replaceFragment(sfaFragment, fragmentActivity, tag, isNeedBackStack, null, ((ESFAFragTags) iEnum).getId());
            }

            return true;
        }

        return false;

    }

    /**
     * Update the fragment in fragment manager
     *
     * @param iEnum            Tag of which fragment need to update
     * @param isNeedBackStack  if {@code true} then fragment is added to back stack else not
     * @param fragmentActivity used to get the fragment manager
     * @param bundle           is set to the fragment as a argument
     * @param container        is set to the fragment as a argument
     * @return true if fragment updated successfully else false
     */
    public boolean updateFragment(IEnum iEnum, boolean isNeedBackStack, SFAActivity fragmentActivity, Bundle bundle, View container) {
        return updateFragWithBundle(iEnum, isNeedBackStack, fragmentActivity, bundle, container);
    }


    /**
     * Update the fragment in fragment manager
     *
     * @param iEnum            Tag of which fragment need to update
     * @param isNeedBackStack  if {@code true} then fragment is added to back stack else not
     * @param fragmentActivity used to get the fragment manager
     * @param bundle           is set to the fragment as a argument
     * @return true if fragment updated successfully else false
     */
    public boolean updateFragment(IEnum iEnum, boolean isNeedBackStack, SFAActivity fragmentActivity, Bundle bundle) {
        return updateFragWithBundle(iEnum, isNeedBackStack, fragmentActivity, bundle, null);
    }

    /**
     * Update the fragment in fragment manager
     *
     * @param iEnum            Tag of which fragment need to update
     * @param fragmentActivity used to get the fragment manager
     * @param bundle           is set to the fragment as a argument
     * @return true if fragment updated successfully else false
     */
    public boolean updateFragment(IEnum iEnum, SFAActivity fragmentActivity, Bundle bundle) {
        return updateFragWithBundle(iEnum, false, fragmentActivity, bundle, null);
    }


    /**
     * Update the fragment in fragment manager
     *
     * @param iEnum            Tag of which fragment need to update
     * @param fragmentActivity used to get the fragment manager
     * @return true if fragment updated successfully else false
     */
    public boolean updateFragment(IEnum iEnum, SFAActivity fragmentActivity) {
        return updateFragment(iEnum, false, fragmentActivity);
    }


    /**
     * Replace the fragment to fragment manager and set the custom animation.
     *
     * @param fragment         which needs to update
     * @param activity         used to get the fragment manager
     * @param tag              which is added in fragment manager to represent the fragment
     * @param isAddToBackStack If true then fragment added to fragment manager back stack else not.
     * @param container        for replacing fragments
     */
    private void replaceFragment(final SFAFragment fragment, final SFAActivity activity, final String tag, final boolean isAddToBackStack, final View container,
                                 final int id) {


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (fragment != null) {
                    FragmentManager fragmentManager = getFragmentManager(activity);
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.setCustomAnimations(R.anim.pull_in_right, R.anim.push_out_left, R.anim.pull_in_left, R.anim.push_out_right);
                    if (container != null) {
                        fragmentTransaction.replace(container.getId(), fragment, tag);
                    } else {
                        fragmentTransaction.replace(R.id.frame_container, fragment, tag);
                    }
                    if (isAddToBackStack)
                        fragmentTransaction.addToBackStack(tag);
                    try {
                        fragmentTransaction.commitAllowingStateLoss();
                    } catch (IllegalStateException e) {
                        Log.w("fragTrans.commit()", e);
                        Crashlytics.logException(e);
                        fragmentTransaction.commitAllowingStateLoss();
                    }
                    fragmentManager.executePendingTransactions();
                    try {
                        activity.setToolbarTitle(activity.getResources().getString(id));
                    }catch (Exception e){
                        activity.setToolbarTitle(tag);
                    }

                } else {
                    Log.e("DashboardActivity", "Error in creating fragment");
                }
            }
        }, 100);

    }

    /**
     * Return the FragmentManager for interacting with fragments associated
     * with this activity.
     *
     * @param activity used to get the fragment manager
     * @return {@link FragmentManager}
     */
    private FragmentManager getFragmentManager(SFAActivity activity) {
        return activity.getSupportFragmentManager();
    }

    /**
     * Remove the fragment from back stack
     *
     * @param fm  Removed the fragment using this {@link FragmentManager}
     * @param tag this used to get the fragment
     */

    private void removeFromBackStack(FragmentManager fm, String tag) {

        if (tag != null && !"".equals(tag)) {

            for (int entry = 0; entry < fm.getBackStackEntryCount(); entry++) {
                String name = fm.getBackStackEntryAt(entry).getName();
                if (name != null && name.equals(tag)) {
                    fm.popBackStackImmediate(fm.getBackStackEntryAt(entry).getId(),
                            FragmentManager.POP_BACK_STACK_INCLUSIVE);


                    break;
                }
            }
        } else {
            fm.popBackStackImmediate();
        }
    }

    /**
     * Return the current fragment from {@link FragmentManager}
     *
     * @param fragmentManager used this fragment manager to get the current fragment
     * @return {@link SFAFragment}
     */
    private SFAFragment getCurrentFragment(FragmentManager fragmentManager) {
        return (SFAFragment) fragmentManager.findFragmentById(R.id.frame_container);
    }

    /**
     * Set the tag as title in {@link SFAActivity}
     *
     * @param activity set the title in toolbar of current fragment
     * @param fm       used to get the current fragment
     */
    private void setCurrentFragmentTitle(SFAActivity activity, FragmentManager fm) {

        setCurrentFragmentTitle(activity, getCurrentFragment(fm).getTag());
    }

    /**
     * Set the tag as title in {@link SFAActivity}
     *
     * @param activity set the title in toolbar of current fragment
     * @param title    used to get the current fragment
     */
    public void setCurrentFragmentTitle(SFAActivity activity, String title) {

        activity.setToolbarTitle(title);
    }

    /**
     * Pop the given {@link ESFAFragTags} fragment from back stack of {@link FragmentManager}
     *
     * @param activity used to get the {@link FragmentManager}
     * @param fragTags is removed from back stack entry
     */
    public void popBackStack(SFAActivity activity, ESFAFragTags fragTags) {

        FragmentManager fm = getFragmentManager(activity);
        String tag = getFragTag(fragTags);
        removeFromBackStack(fm, tag);
        setCurrentFragmentTitle(activity, fm);

    }

    /**
     * Remove all the fragments from back stack entry of {@link FragmentManager}
     *
     * @param activity used to get the {@link FragmentManager}
     */

    public void clearBackStack(SFAActivity activity) {
        try {
            FragmentManager fragmentManager = getFragmentManager(activity);
            fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        } catch (Exception e) {
            Log.e(TAG, "clearBackStack: " + e.getMessage(), e);

        }
    }

}
