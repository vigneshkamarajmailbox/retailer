package com.botree.retailerssfa.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.botree.retailerssfa.R;
import com.botree.retailerssfa.adapters.OnRecyclerItemClick;
import com.botree.retailerssfa.models.Distributor;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class DistributorInfoAdapter extends RecyclerView.Adapter<DistributorInfoAdapter.ViewHolder> {
    private ArrayList<Distributor> mData;
    private OnRecyclerItemClick mListner;
    private Context mContext;
    private String url;
    DisplayImageOptions options;


    public DistributorInfoAdapter(Context context, ArrayList<Distributor> distributor, OnRecyclerItemClick listener) {
        mData = distributor;
        mListner = listener;
        mContext = context;
        url = mContext.getResources().getString(R.string.BASE_URL_RELEASE) + "controller/getcompanyimage/";
        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.sfa_placeholder)
                .showImageForEmptyUri(R.drawable.sfa_placeholder)
                .showImageOnFail(R.drawable.sfa_placeholder)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_distributor_info, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.fetchData(position);
        holder.itemOnclick(position);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView distributor_name_tv, distributor_company_name_tv, distrobutor_address_one_tv, distrobutor_address_two_tv, distrobutor_address_three_tv;
        private ImageView company_logo_iv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            distributor_name_tv = (TextView) itemView.findViewById(R.id.tv_distributor_name);
            distributor_company_name_tv = (TextView) itemView.findViewById(R.id.tv_distributor_company_name);
            distrobutor_address_one_tv = (TextView) itemView.findViewById(R.id.tv_distributor_address_one);
            distrobutor_address_two_tv = (TextView) itemView.findViewById(R.id.tv_distributor_address_two);
            distrobutor_address_three_tv = (TextView) itemView.findViewById(R.id.tv_distributor_address_three);
            company_logo_iv = (ImageView) itemView.findViewById(R.id.iv_company_logo);
        }

        private void fetchData(int position) {
            distributor_name_tv.setText(mData.get(position).getDistributorName());

            String localUrl = url + mData.get(position).getCompanyCode() + "/jpeg";

            ImageLoader.getInstance().displayImage(localUrl, company_logo_iv, options, new SimpleImageLoadingListener() {
                @Override
                public void onLoadingStarted(String imageUri, View view) {
                    //ignored
                }

                @Override
                public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                    //ignored
                }

                @Override
                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                    //ignored
                }
            });
            if (!mData.get(position).getCompanyName().equalsIgnoreCase("")) {
                distributor_company_name_tv.setText(mData.get(position).getCompanyName());
            } else {
                distributor_company_name_tv.setText(R.string.distributor_adapter_nil);
            }

            if (!mData.get(position).getDistributorAddress1().equalsIgnoreCase("")) {
                distrobutor_address_one_tv.setText(mData.get(position).getDistributorAddress1());
            } else {
                distrobutor_address_one_tv.setText(R.string.distributor_adapter_nil);
            }
            if (!mData.get(position).getDistributorAddress2().equalsIgnoreCase("")) {
                distrobutor_address_two_tv.setText(mData.get(position).getDistributorAddress2());
            } else {
                distrobutor_address_two_tv.setText("");
                distrobutor_address_two_tv.setVisibility(View.GONE);
            }
            if (!mData.get(position).getDistributorAddress3().equalsIgnoreCase("")) {
                distrobutor_address_three_tv.setText(mData.get(position).getDistributorAddress3());
            } else {
                distrobutor_address_three_tv.setText("");
                distrobutor_address_three_tv.setVisibility(View.GONE);
            }

        }

        private void itemOnclick(final int position) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListner.onItemClick(position);
                }
            });
        }

    }

    public static Bitmap getBitmapFromURL(String src) {
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            Log.e("Bitmap", "returned");
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("Exception", e.getMessage());
            return null;
        }
    }
}
