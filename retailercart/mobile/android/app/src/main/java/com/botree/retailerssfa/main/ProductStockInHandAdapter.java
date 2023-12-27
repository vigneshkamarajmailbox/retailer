package com.botree.retailerssfa.main;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.botree.retailerssfa.BuildConfig;
import com.botree.retailerssfa.R;
import com.botree.retailerssfa.db.SFADatabase;
import com.botree.retailerssfa.models.ProductsVO;
import com.botree.retailerssfa.support.CertificatePinner;
import com.botree.retailerssfa.util.AppUtils;
import com.botree.retailerssfa.util.OrderSupportUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static com.botree.retailerssfa.util.OrderSupportUtil.ALL_BRANDS;
import static com.botree.retailerssfa.util.OrderSupportUtil.ALL_CATEGORY;
import static com.botree.retailerssfa.util.OrderSupportUtil.ZeroResult;


public class ProductStockInHandAdapter extends RecyclerView.Adapter<ProductStockInHandAdapter.MyViewHolder> {

    private static final String TAG = ProductStockInHandAdapter.class.getSimpleName();
    private String url;
    private Context con;
    private List<ProductsVO> productsVOList;
    private List<ProductsVO> searchList;
    private ZeroResult zeroNonZeroResult;
    private String brandValue;
    private String categoryValue;
    private OnItemClickListener mItemClickListener;
    private final SFADatabase db;

    private String isKgRateDisabled = "N";//change to Y later

    public ProductStockInHandAdapter(Context context, List<ProductsVO> productsVOs) {
        this.productsVOList = productsVOs;
        this.searchList = new ArrayList<>();
        this.searchList.addAll(productsVOs);
        con = context;

        url = context.getResources().getString(R.string.BASE_URL_RELEASE) + "controller/getproductimage/";
        if (BuildConfig.DEBUG) {
            url = context.getResources().getString(R.string.BASE_URL_DEBUG) + "controller/getproductimage/";
        }

        Log.e("fetchedurl", "" + url + " " + BuildConfig.DEBUG);

        db = SFADatabase.getInstance(context);


        isKgRateDisabled = db.getConfigDataBasedOnName("DisableKGRate");

        Log.e("isKgRateDisabled", "" + isKgRateDisabled);
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    public void applyFilter(OrderSupportUtil.ZeroResult zeroNonZeroResult, String brandValue, String categoryValue) {
        this.zeroNonZeroResult = zeroNonZeroResult;
        this.brandValue = brandValue;
        this.categoryValue = categoryValue;
        applyFilterAndAdd("");
    }

    public void applySearchFilter(String charText) {
        applyFilterAndAdd(charText.toLowerCase(Locale.getDefault()));
    }

    public String getValueAt(int position) {
        return productsVOList.get(position).getProdNameCaps();
    }

    @NonNull
    @Override
    public ProductStockInHandAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_master_list_items, parent, false);
        return new ProductStockInHandAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ProductStockInHandAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {

        try {
            String localUrl = url + productsVOList.get(position).getProdCodeCaps() + "/jpg";

            CertificatePinner.getInstCertificate().getCustomPicasso(con)
                    .load(localUrl)
                    .fit()
                    .centerCrop()
                    .placeholder(R.drawable.sfa_placeholder)
                    .error(R.drawable.sfa_placeholder)
                    .into(holder.productImage);

            if (productsVOList.get(position).isCatalogAvailable()) {
                holder.clickCatalog.setVisibility(View.VISIBLE);
            } else {
                holder.clickCatalog.setVisibility(View.GONE);
            }

            if (isKgRateDisabled.equalsIgnoreCase("Y")) {
                holder.kgrateLayout.setVisibility(View.VISIBLE);
                String mKgRate = String.format(Locale.getDefault(), "%.2f", productsVOList.get(position).getKgRate());
                holder.kgRate.setText(mKgRate);
            } else {
                holder.kgrateLayout.setVisibility(View.GONE);
            }

            holder.prodNameTxt.setText(productsVOList.get(position).getProdNameCaps().toUpperCase(Locale.getDefault()));

            if (productsVOList.get(position).getStockInHandCaps().intValue() > 0) {
                holder.stockinhandTxt.setTextColor(ContextCompat.getColor(con, R.color.color_green));
            } else {
                holder.stockinhandTxt.setTextColor(ContextCompat.getColor(con, R.color.color_light_red));
            }
            holder.stockinhandTxt.setText(String.valueOf(productsVOList.get(position).getStockInHandCaps().intValue()));

            holder.mrpTxt.setText(String.format(Locale.ENGLISH, "%.2f", productsVOList.get(position).getmRPCaps()));
            holder.sellingPriceTxt.setText(String.format(Locale.ENGLISH, "%.2f", productsVOList.get(position).getSellPriceCaps()));
            holder.purchasePriceTxt.setText(String.format(Locale.ENGLISH, "%.2f", productsVOList.get(position).getPurchPrice()));

            holder.uomTxt.setText(String.valueOf(productsVOList.get(position).getDefaultUomidCaps()));

            if (productsVOList.get(position).getProdNameCaps() != null && !productsVOList.get(position).getProdNameCaps().isEmpty()) {
                String userInitial = productsVOList.get(position).getProdNameCaps();
                String init = userInitial.substring(0, 1);

                holder.initial.setText(init.toUpperCase(Locale.getDefault()));
            }

            holder.initial.setBackground(AppUtils.getOurInstance().getInitialCircleDrawable(position));
        } catch (Exception e) {
            Log.e(TAG, "onBindViewHolder: " + e.getMessage(), e);

        }
    }

    @Override
    public int getItemCount() {
        return productsVOList.size();
    }

    private void applyFilterAndAdd(String filterText) {
        productsVOList.clear();

        if (isAllFilterEmpty(filterText) || filterText.contains("all")) {
            productsVOList.addAll(searchList);
            notifyDataSetChanged();
            return;
        }

        for (ProductsVO bookingVO : searchList) {

            if (checkFilterText(filterText, bookingVO)) {

                if (getBrandCategoryFilResult(bookingVO)) continue;

                zeroNonzeroFilterResult(bookingVO);
            }
        }
        notifyDataSetChanged();
    }

    private boolean getBrandCategoryFilResult(ProductsVO bookingVO) {
        if (isBrandValidationSuccess(bookingVO))
            return true;


        if (isCategoryValidationSuccess(bookingVO))
            return true;


        if (zeroNonZeroResult == null) {
            productsVOList.add(bookingVO);
            return true;
        }
        return false;
    }

    private void zeroNonzeroFilterResult(ProductsVO bookingVO) {
        if (zeroNonZeroResult == ZeroResult.ALL_PRODUCTS) {
            productsVOList.add(bookingVO);
        } else if (zeroNonZeroResult == ZeroResult.ZERO_PRODUCTS) {
            if (bookingVO.getStockInHandCaps() == 0)
                productsVOList.add(bookingVO);
        } else {
            if (bookingVO.getStockInHandCaps() > 0)
                productsVOList.add(bookingVO);
        }
    }

    private boolean isCategoryValidationSuccess(ProductsVO bookingVO) {
        if (isStrEmpty(categoryValue) &&
                !categoryValue.equals(ALL_CATEGORY) &&
                !(/*"".equals(bookingVO.getProductStatus()) &&*/
                        "".equals(bookingVO.getCategoryCaps()))) {

            if (bookingVO.getCategoryCaps() == null)
                return true;

            if (bookingVO.isContainsMultipleCategory() && bookingVO.getCategoryList() != null &&
                    !bookingVO.getCategoryList().isEmpty()) {
                List<String> categoryList = bookingVO.getCategoryList();
                if (categoryList.contains(categoryValue))
                    return false;
            }

            return bookingVO.getCategoryCaps() != null && !bookingVO.getCategoryCaps().equals(categoryValue);

        }

        return false;
    }

    private boolean isBrandValidationSuccess(ProductsVO bookingVO) {
        return isStrEmpty(brandValue) &&
                !brandValue.equals(ALL_BRANDS) &&
                !"".equals(bookingVO.getProdHierValNameCaps()) && !bookingVO.getProdHierValNameCaps().equals(brandValue);
    }

    private boolean isStrEmpty(String str) {
        return str != null && str.length() != 0;
    }

    private boolean checkFilterText(String filterText, ProductsVO orderBookingVO) {

        return filterText.length() == 0 || (orderBookingVO.getProdNameCaps().toLowerCase(Locale.getDefault()).contains(filterText));

    }

    private boolean isAllFilterEmpty(String filterText) {
        return filterText.length() == 0
                && zeroNonZeroResult == null
                && isBrandFilterEmpty()
                && isCategoryFilterEmpty();

    }

    private boolean isBrandFilterEmpty() {
        return brandValue != null && (brandValue.length() == 0 || brandValue.equals(ALL_BRANDS));
    }

    private boolean isCategoryFilterEmpty() {
        return categoryValue != null && (categoryValue.length() == 0 || categoryValue.equals(ALL_CATEGORY));
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);

    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        final View mView;
        final TextView initial;
        final TextView prodNameTxt;
        final TextView stockinhandTxt;
        final TextView mrpTxt;
        final TextView sellingPriceTxt;
        final TextView uomTxt;
        final TextView purchasePriceTxt;
        TextView clickCatalog;
        ImageView productImage;
        final TextView kgRate;

        LinearLayout kgrateLayout;


        MyViewHolder(View view) {
            super(view);
            mView = view;
            initial = view.findViewById(R.id.product_initail_txt);
            prodNameTxt = view.findViewById(R.id.stock_product_name_txt);
            stockinhandTxt = view.findViewById(R.id.stock_amount_txt);
            mrpTxt = view.findViewById(R.id.stock_mrp_name_txt);
            clickCatalog = view.findViewById(R.id.click_text);
            sellingPriceTxt = view.findViewById(R.id.stock_selling_price_name_txt);
            uomTxt = view.findViewById(R.id.stock_uom_txt);
            purchasePriceTxt = view.findViewById(R.id.stock_purchase_price_txt);
            productImage = view.findViewById(R.id.product_image);
            kgRate = view.findViewById(R.id.kg_rate_txt);
            kgrateLayout = view.findViewById(R.id.kg_rate_layout);

            mView.setOnClickListener(this);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + prodNameTxt.getText();
        }

        @Override
        public void onClick(View view) {

            if (mItemClickListener != null) {
                mItemClickListener.onItemClick(view, getAdapterPosition());
            }

        }
    }


}
