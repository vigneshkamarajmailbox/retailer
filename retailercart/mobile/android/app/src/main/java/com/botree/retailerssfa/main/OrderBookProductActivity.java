package com.botree.retailerssfa.main;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.botree.retailerssfa.R;
import com.botree.retailerssfa.base.BaseActivity;
import com.botree.retailerssfa.controller.retrofit.DataManager;
import com.botree.retailerssfa.models.OrderBookingVO;

import java.util.ArrayList;
import java.util.List;

public class OrderBookProductActivity extends BaseActivity implements ProductImageSliderFragment.OnFragmentInteractionListener {

    private int position = 0;
    private List<OrderBookingVO> orderBookingList;
    private ViewPager mViewPager;
    private Toolbar mtoolbar;
    private boolean isVansales = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_book_product);
        position = getIntent().getIntExtra("clickedPosition", 0);
        isVansales = getIntent().getBooleanExtra("vansales", false);
        orderBookingList = DataManager.getInstance().getOrderBookingVOList();
        DataManager.getInstance().addSwipeOrderBookingVOList(orderBookingList);

        mtoolbar = findViewById(R.id.custom_toolbar);
        initToolbar();
        setBaseToolbarTitle(orderBookingList.get(position).getProdName(), null);

        mViewPager = findViewById(R.id.product_viewpager);
        ScreenSlidePagerAdapter mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager(), getFragments(orderBookingList));

        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.setCurrentItem(position, true);


        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //ignored
            }

            @Override
            public void onPageSelected(int pos) {
                position = pos;
                setBaseToolbarTitle(orderBookingList.get(pos).getProdName(), null);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                //ignored
            }
        });

    }

    @Override
    public Toolbar getBaseToolbar() {
        return mtoolbar;
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        overridePendingTransition(R.anim.stay, R.anim.slide_down);
        return true;
    }

    @Override
    public void onBackPressed() {
        overridePendingTransition(R.anim.stay, R.anim.slide_down);
        setResult(RESULT_OK);
        super.onBackPressed();
    }

    @Override
    public void onFragmentInteraction(int position) {
        int nextPostion = position + 1;
        mViewPager.setCurrentItem(nextPostion, true);
    }

    private List<Fragment> getFragments(List<OrderBookingVO> orderBookingList) {

        List<Fragment> fragmentList = new ArrayList<>();
        for (int i = 0; i < orderBookingList.size(); i++) {
            fragmentList.add(ProductImageSliderFragment.newInstance(i, orderBookingList.get(i).getProdCode(), isVansales));
        }
        return fragmentList;
    }

    public static class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {

        List<Fragment> mFragments;

        ScreenSlidePagerAdapter(FragmentManager fm, List<Fragment> fragment) {
            super(fm);
            mFragments = fragment;
        }

        @Override
        public Fragment getItem(int pos) {
            return mFragments.get(pos);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public int getItemPosition(@NonNull Object object) {
            return POSITION_NONE;
        }

    }

}
