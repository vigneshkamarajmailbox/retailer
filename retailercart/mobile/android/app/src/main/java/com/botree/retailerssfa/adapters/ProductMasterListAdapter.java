package com.botree.retailerssfa.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.databinding.library.baseAdapters.BuildConfig;
import androidx.recyclerview.widget.RecyclerView;

import com.botree.retailerssfa.R;
import com.botree.retailerssfa.models.ProductMasterModel;
import com.botree.retailerssfa.support.Globals;
import com.botree.retailerssfa.util.OrderSupportUtil;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class ProductMasterListAdapter extends RecyclerView.Adapter<ProductMasterListAdapter.MyViewHolder> {

    private static final String TAG = ProductMasterListAdapter.class.getSimpleName();
    private String url;
    private Context con;
    private List<ProductMasterModel> productMasterModels;
    private List<ProductMasterModel> searchList;
    private OnBatchClickListener onBatchCLickListener;
    private OnUomClickListener onUomCLickListener;
    private OnTaxClickListener onTaxClickListener;
    private String brandCode="";
    private String brandValue="";
    private String categoryValue;
    DisplayImageOptions options;

    public ProductMasterListAdapter(Context context, List<ProductMasterModel> productMasterModels,
                                    OnBatchClickListener onBatchCLickListener,
                                    OnUomClickListener onUomCLickListener, OnTaxClickListener taxClickListener) {
        this.productMasterModels = productMasterModels;
        this.onBatchCLickListener = onBatchCLickListener;
        this.onUomCLickListener = onUomCLickListener;
        this.onTaxClickListener = taxClickListener;
        this.searchList = new ArrayList<>();
        this.searchList.addAll(productMasterModels);
        con = context;

        url = context.getResources().getString(R.string.BASE_URL_RELEASE) + "controller/getproductimage/";
        if (BuildConfig.DEBUG) {
            url = context.getResources().getString(R.string.BASE_URL_DEBUG) + "controller/getproductimage/";
        }
        Log.e("fetchedurl", "" + url + " " + BuildConfig.DEBUG);

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

    public void applySearchFilter(String charText) {
        applyFilterAndAdd(charText.toLowerCase(Locale.getDefault()));
    }

    public String getValueAt(int position) {
        return productMasterModels.get(position).getProdName();
    }

    @NonNull
    @Override
    public ProductMasterListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_list_adapter_layout, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ProductMasterListAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {

        try {
            String localUrl = url + productMasterModels.get(position).getProdCode() + "/jpg";

            ImageLoader.getInstance().displayImage(localUrl, holder.productImage, options, new SimpleImageLoadingListener() {
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

            holder.prodNameTxt.setText(productMasterModels.get(position).getProdName().toUpperCase(Locale.getDefault()));

            if (productMasterModels.get(position).getSaleableQty() > 0) {
                holder.stockinhandTxt.setTextColor(ContextCompat.getColor(con, R.color.color_green));
            } else {
                holder.stockinhandTxt.setTextColor(ContextCompat.getColor(con, R.color.color_light_red));
            }
            holder.stockinhandTxt.setText(String.valueOf(productMasterModels.get(position).getSaleableQty()));
            if(productMasterModels.get(position).getDefaultUom() == null){
                holder.defaultUom.setText(" - ");
            }else {
                holder.defaultUom.setText(String.valueOf(productMasterModels.get(position).getDefaultUom()));
            }

            holder.productBatch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onBatchCLickListener.onBatchClick(holder.getAdapterPosition());
                }
            });

            holder.productUom.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onUomCLickListener.onUomClick(holder.getAdapterPosition());
                }
            });

            holder.productTax.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onTaxClickListener.onTaxClick(holder.getAdapterPosition());
                }
            });
        } catch (Exception e) {
            Log.e(TAG, "onBindViewHolder: " + e.getMessage(), e);

        }
    }

    @Override
    public int getItemCount() {
        return productMasterModels.size();
    }

    private void applyFilterAndAdd(String filterText) {
        productMasterModels.clear();

        if ((isAllFilterEmpty(filterText) && (brandCode == null || brandCode.isEmpty()) && (categoryValue == null || categoryValue.isEmpty())) ||
                (filterText.contains("all") && (brandCode == null || brandCode.isEmpty())) && (categoryValue == null || categoryValue.isEmpty())) {
            productMasterModels.addAll(searchList);
            notifyDataSetChanged();
            return;
        }

        for (ProductMasterModel productMasterModel : searchList) {
            if (Globals.isCategoryValidationSuccess(productMasterModel, categoryValue)
                    && Globals.isBrandValidationSuccess(productMasterModel, brandCode, brandValue)
                    && Globals.checkFilterText(filterText, productMasterModel.getProdName(), productMasterModel.getProdCode())) {
                        productMasterModels.add(productMasterModel);
                    }
        }

        notifyDataSetChanged();
    }

    private boolean isAllFilterEmpty(String filterText) {
        return filterText.length() == 0;
    }

    public void applyFilter(OrderSupportUtil.ZeroResult zeroNonZeroResult, String brandValue, String brandCode, String categoryValue) {
        this.brandValue = brandValue;
        this.brandCode = brandCode;
        this.categoryValue = categoryValue;
        applyFilterAndAdd("");
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        final TextView prodNameTxt;
        final TextView stockinhandTxt;
        final TextView defaultUom;
        ImageView productImage;
        TextView productBatch;
        TextView productUom;
        TextView productTax;

        MyViewHolder(View view) {
            super(view);

            prodNameTxt = view.findViewById(R.id.stock_product_name_txt);
            stockinhandTxt = view.findViewById(R.id.stock_quantity);
            defaultUom = view.findViewById(R.id.default_uom);
            productImage = view.findViewById(R.id.product_image);
            productBatch = view.findViewById(R.id.product_batch);
            productUom = view.findViewById(R.id.product_uom);
            productTax = view.findViewById(R.id.product_tax);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + prodNameTxt.getText();
        }
    }

    public interface OnBatchClickListener {
        void onBatchClick(int position);
    }

    public interface OnUomClickListener {
        void onUomClick(int position);
    }

    public interface OnTaxClickListener {
        void onTaxClick(int position);
    }
}
