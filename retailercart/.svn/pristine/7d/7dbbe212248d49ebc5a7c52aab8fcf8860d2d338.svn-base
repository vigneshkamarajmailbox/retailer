package com.botree.retailerssfa.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.botree.retailerssfa.BuildConfig;
import com.botree.retailerssfa.R;
import com.botree.retailerssfa.models.OrderBookingVO;
import com.botree.retailerssfa.models.ProFilterModel;
import com.botree.retailerssfa.support.CertificatePinner;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ProductFilterAdapter extends RecyclerView.Adapter<ProductFilterAdapter.BrandFilterViewHolder> {

    private List<OrderBookingVO> brandDataList;
    private int selectedPosition = -1;
    private Context context;
    private List<ProFilterModel> filterModelsList;
    private List<ProFilterModel> searchItem = new ArrayList<>();
    private OnListItemClick onItemClick;
    private OnListBrandItemClick brandItemClick;
    private int currentPos;
    private boolean brandImgState;
    private String url;

    public ProductFilterAdapter(Context context, List<ProFilterModel> brandList, int pos,
                                boolean state, List<OrderBookingVO> brandDataImgList) {
        this.filterModelsList = brandList;
        this.brandDataList = brandDataImgList;
        this.context = context;
        this.currentPos = pos;
//        if (brandList != null)
            this.searchItem.addAll(brandList);
        if (state) {
            this.selectedPosition = pos;
        }
        this.brandImgState = state;

        url = context.getResources().getString(R.string.BASE_URL_RELEASE) + "controller/getproductimage/";
        if (BuildConfig.DEBUG) {
            url = context.getResources().getString(R.string.BASE_URL_DEBUG) + "controller/getproductimage/";
        }
    }

    @NonNull
    @Override
    public ProductFilterAdapter.BrandFilterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.brand_filter_list_item, parent, false);
        return new ProductFilterAdapter.BrandFilterViewHolder(view);
    }

    public void setOnItemClickListener(OnListItemClick mItemClickListener) {
        this.onItemClick = mItemClickListener;
    }


    public void setOnBrandItemClickListener(OnListBrandItemClick mItemClickListener) {
        this.brandItemClick = mItemClickListener;
    }

    @Override
    public void onBindViewHolder(@NonNull final ProductFilterAdapter.BrandFilterViewHolder holder, int position) {

        final int pos = position;
        holder.brandNameTxt.setText(filterModelsList.get(position).getHierLelevelName1());

        if (brandImgState) {
            prodLevelFilterSelection(holder, pos);
        } else {
            brandFilterSelected(holder, pos);
        }

        if (brandImgState) {
            holder.mview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (onItemClick != null) {
                        selectedPosition = pos;
                        notifyDataSetChanged();
                        onItemClick.onItemClick(pos, filterModelsList.get(pos).getHierLelevelCode1()
                                , filterModelsList.get(pos).getHierLelevelName1());
                    }
                }
            });
        } else {

            holder.mview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (brandItemClick != null) {
                        selectedPosition = pos;
                        notifyDataSetChanged();
//                        brandItemClick.onItemClick(pos, filterModelsList.get(pos).getHierLelevelCode1());
                        brandItemClick.onItemClick(pos, filterModelsList.get(pos).getHierLelevelName1());
                    }
                }
            });
        }

        setBrandImages(holder, position);
    }

    private void setBrandImages(@NonNull BrandFilterViewHolder holder, int position) {
        if (brandDataList != null && brandImgState) {
            if (position != 0) {
                String localUrl = url + "ORG_" + brandDataList.get(position - 1).getBrandCode() + "/jpg";
                holder.brandIconImg.setVisibility(View.VISIBLE);

                CertificatePinner.getInstCertificate().getCustomPicasso(context)
                        .load(localUrl)
                        .fit()
                        .placeholder(R.drawable.sfa_placeholder)
                        .error(R.drawable.sfa_placeholder)
                        .into(holder.brandIconImg);
            } else {
                holder.brandIconImg.setVisibility(View.GONE);
            }
        } else {
            holder.brandIconImg.setVisibility(View.GONE);
        }
    }

    private void brandFilterSelected(@NonNull BrandFilterViewHolder holder, int pos) {
        if (selectedPosition == pos || currentPos == pos) {
            setBackgroundDrawableToView(holder);
            holder.brandNameTxt.setEnabled(true);
            holder.brandNameTxt.setTypeface(Typeface.SERIF, Typeface.BOLD);

        } else {

            holder.itemView.setBackground(setUnSelectedDrawable());
            holder.brandNameTxt.setEnabled(false);
            holder.brandNameTxt.setTypeface(Typeface.SERIF, Typeface.NORMAL);
        }
    }

    private void prodLevelFilterSelection(@NonNull BrandFilterViewHolder holder, int pos) {
        if (selectedPosition == pos) {
            setBackgroundDrawableToView(holder);
            holder.brandNameTxt.setEnabled(true);
            holder.brandNameTxt.setTypeface(Typeface.SERIF, Typeface.BOLD);

        } else {

            holder.itemView.setBackground(setUnSelectedDrawable());
            holder.brandNameTxt.setEnabled(false);
            holder.brandNameTxt.setTypeface(Typeface.SERIF, Typeface.NORMAL);
        }
    }

    private void setBackgroundDrawableToView(@NonNull ProductFilterAdapter.BrandFilterViewHolder holder) {
        holder.itemView.setBackground(setSelectedDrawable());
    }

    private Drawable setSelectedDrawable() {

        return ContextCompat.getDrawable(context, R.drawable.filter_list_selected);
    }


    private Drawable setUnSelectedDrawable() {

        return ContextCompat.getDrawable(context, R.drawable.filter_list_unselected);
    }

    @Override
    public int getItemCount() {
        return filterModelsList.size();
    }

    public void clearData() {
        final int size = filterModelsList.size();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                filterModelsList.remove(0);
            }

            notifyItemRangeRemoved(0, size);
        }
    }

    public void brandSearchFilter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        filterModelsList.clear();
        if (charText.length() == 0) {
            filterModelsList.addAll(searchItem);
        } else {
            for (ProFilterModel p : searchItem) {
                if (p.getHierLelevelName1().toLowerCase(Locale.getDefault())
                        .contains(charText.toLowerCase())) {
                    filterModelsList.add(p);
                }
            }
        }
        notifyDataSetChanged();
    }

    public interface OnListItemClick {
        void onItemClick(int pos, String brandCode, String brandName);
    }


    public interface OnListBrandItemClick {
        void onItemClick(int pos, String brandName);
    }

    class BrandFilterViewHolder extends RecyclerView.ViewHolder {

        private final TextView brandNameTxt;
        private final ImageView brandIconImg;
        private View mview;

        private BrandFilterViewHolder(View itemView) {
            super(itemView);
            mview = itemView;
            brandNameTxt = itemView.findViewById(R.id.brand_name_txt);
            brandIconImg = itemView.findViewById(R.id.brand_icon_img);
        }
    }

}
