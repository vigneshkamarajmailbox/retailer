package com.botree.retailerssfa.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.botree.retailerssfa.R;
import com.botree.retailerssfa.models.Distributor;
import com.botree.retailerssfa.models.SchemeModel;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.util.ArrayList;

public class SchemeDistributorListAdapter extends RecyclerView.Adapter<SchemeDistributorListAdapter.ViewHolder> {
    private ArrayList<Distributor> mData;
    private OnRecyclerItemClick mListner;
    private Context mContext;
    private String url;
    DisplayImageOptions options;


    public SchemeDistributorListAdapter(Context context, ArrayList<Distributor> distributorArrayList, OnRecyclerItemClick listener) {
        mData = distributorArrayList;
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
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_scheme_distributor_list, parent, false);
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
        private TextView distributor_code_tv, company_name_tv, distributor_name_tv;
        private ImageView company_logo_iv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            company_name_tv = (TextView) itemView.findViewById(R.id.tv_company_name);
            distributor_code_tv = (TextView) itemView.findViewById(R.id.tv_distr_code);
            distributor_name_tv = (TextView) itemView.findViewById(R.id.tv_distr_name);
            company_logo_iv = (ImageView) itemView.findViewById(R.id.iv_company_logo);
        }

        private void fetchData(int position) {

            if (!mData.get(position).getCompanyName().equalsIgnoreCase("")) {
                company_name_tv.setText(mData.get(position).getCompanyName());
            } else {
                company_name_tv.setText(R.string.distributor_adapter_nil);
            }

            if (!mData.get(position).getDistributorCode().equalsIgnoreCase("")) {
                distributor_code_tv.setText(mData.get(position).getDistributorCode());
            } else {
                distributor_code_tv.setText(R.string.distributor_adapter_nil);
            }

            if (!mData.get(position).getDistributorName().equalsIgnoreCase("")) {
                distributor_name_tv.setText(mData.get(position).getDistributorName()+" ("+mData.get(position).getDistributorCode()+")");
            } else {
                distributor_name_tv.setText(R.string.distributor_adapter_nil);
            }

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
}
