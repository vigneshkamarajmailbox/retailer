package com.botree.retailerssfa.adapters;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.botree.retailerssfa.BuildConfig;
import com.botree.retailerssfa.R;
import com.botree.retailerssfa.support.CertificatePinner;

import java.util.List;

/**
 * Created by shantarao on 21/11/17.
 */

public class CustomPagerAdapter extends PagerAdapter {

    private final Context context;
    private LayoutInflater mLayoutInflater;
    private String imgUrl;
    private List<String> bannerList;

    public CustomPagerAdapter(Context context, List<String> resources) {

        this.context = context;
        this.bannerList = resources;
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (BuildConfig.DEBUG) {
            imgUrl = context.getResources().getString(R.string.BASE_URL_DEBUG);
        } else {
            imgUrl = context.getResources().getString(R.string.BASE_URL_RELEASE);
        }
    }


    @Override
    public int getCount() {
        return bannerList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View itemView = mLayoutInflater.inflate(R.layout.pager_item, container, false);

        ImageView imageView = itemView.findViewById(R.id.imageView);

        CertificatePinner.getInstCertificate().getCustomPicasso(context)
                .load(imgUrl + bannerList.get(position))
                .fit()
                .placeholder(R.drawable.ad1)
                .error(R.drawable.ad1)
                .into(imageView);

        container.addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((LinearLayout) object);
    }
}
