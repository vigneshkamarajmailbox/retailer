package com.botree.retailerssfa.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.botree.retailerssfa.R;
import com.botree.retailerssfa.models.DistrReportModel;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;

import static android.content.ContentValues.TAG;

public class CustomSubFilterAdapter extends RecyclerView.Adapter<CustomSubFilterAdapter.MainFilterViewHolder> {
    private int menuListPos;
    private OnItemClickListener mItemClickListener;
    private List<DistrReportModel> subMenulist;
    private List<DistrReportModel> searchItem = new ArrayList<>();
    private Context context;
    private String selectedColFn;
    private OnDropClickListener mDropClickListener;
    private String chartType;

    public CustomSubFilterAdapter(List<DistrReportModel> subMenulist, Context con, int position, String colGroup, String chartType) {
        this.menuListPos = position;
        this.subMenulist = subMenulist;
        this.searchItem.addAll(subMenulist);
        this.context = con;
        this.chartType = chartType;
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    public void setOnDropClickListener(final OnDropClickListener mDropClickListener) {
        this.mDropClickListener = mDropClickListener;
    }

    public void addValuesFromHashmaptoList(LinkedHashMap<String, List<DistrReportModel>> mHashmapFilter) {
        try {
            List<DistrReportModel> list = new ArrayList<>();
            for (int i = 0; i < subMenulist.size(); i++) {
                DistrReportModel pending = subMenulist.get(i);
                if (mHashmapFilter.containsKey(pending.getColumnGroup())) {
                    boolean enable = false;
                    for (int j = 0; j < mHashmapFilter.get(pending.getColumnGroup()).size(); j++) {
                        if (pending.getColumnHeader().equals(mHashmapFilter.get(pending.getColumnGroup()).get(j).getColumnHeader())) {
                            enable = mHashmapFilter.get(pending.getColumnGroup()).get(j).isEnable();
                            pending.setEnable(enable);
                            pending.setSelectedColFn(mHashmapFilter.get(pending.getColumnGroup()).get(j).getSelectedColFn());
                            break;
                        }
                    }
                }
                list.add(pending);
            }

            subMenulist.clear();
            subMenulist.addAll(list);

            notifyDataSetChanged();
        } catch (Exception e) {
            Log.e(TAG, "addValuesFromHashmaptoList: " + e.getMessage(), e);
        }
    }

    @NonNull
    @Override
    public MainFilterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.subfilter_list_item, parent, false);
        return new MainFilterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MainFilterViewHolder holder, @SuppressLint("RecyclerView") final int position) {

        holder.distNameTxt.setText(subMenulist.get(position).getColumnHeader());
        holder.checkBox.setChecked(subMenulist.get(position).isEnable());
        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setCheckBoxLogic(v, holder, position);
            }
        });


        if (subMenulist.get(position).getColumnFunction() != null && !subMenulist.get(position).getColumnFunction().isEmpty()) {
            holder.layoutSpinner.setVisibility(View.VISIBLE);
            String colFun = subMenulist.get(position).getColumnFunction();
            final String[] arrayOfString = colFun.split(", ");

            ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(context, R.layout.spinner_list_item, arrayOfString);
            spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            holder.spinner.setAdapter(spinnerAdapter);

            for (int i = 0; i < arrayOfString.length; i++) {
                if (arrayOfString[i].equals(subMenulist.get(position).getSelectedColFn())) {
                    holder.spinner.setSelection(i);
                }
            }

            holder.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    selectedColFn = adapterView.getItemAtPosition(i).toString();
                    subMenulist.get(position).setSelectedColFn(selectedColFn);
                    if (mDropClickListener != null) {
                        mDropClickListener.onDropItemClick(menuListPos,
                                subMenulist.get(position).getColumnHeader(), subMenulist.get(position), holder.checkBox.isChecked(), subMenulist.get(position).getSelectedColFn());
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
// ignored
                }
            });

        } else {
            holder.layoutSpinner.setVisibility(View.GONE);
        }
    }

    private void setCheckBoxLogic(View v, MainFilterViewHolder holder, int position) {
        if (mItemClickListener != null) {
            mItemClickListener.onItemClick(v, menuListPos,
                    subMenulist.get(position).getColumnHeader(), subMenulist.get(position), holder.checkBox.isChecked(), subMenulist.get(position).getSelectedColFn());

            if (!chartType.equalsIgnoreCase("TABLE")) {
                for (DistrReportModel item : subMenulist) {
                    item.setEnable(false);
                }
                subMenulist.get(position).setEnable(holder.checkBox.isChecked());
                holder.checkBox.setChecked(subMenulist.get(position).isEnable());
                notifyDataSetChanged();
            }
        }
    }

    @Override
    public int getItemCount() {
        return subMenulist.size();
    }

    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        subMenulist.clear();

        if (charText.length() == 0) {
            subMenulist.addAll(searchItem);

        } else {

            if (charText.contains("all")) {
                subMenulist.addAll(searchItem);
            } else {
                for (DistrReportModel p : searchItem) {

                    if (p.getColumnHeader().toLowerCase(Locale.getDefault()).contains(charText)) {
                        Log.e("filter text: ", p.getColumnHeader().toLowerCase(Locale.getDefault()));
                        subMenulist.add(p);
                    }
                }
            }

        }
        notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int menuPosition, String colHeader, DistrReportModel clickPosition, boolean checked, String selectedColFunction);
    }

    public interface OnDropClickListener {
        void onDropItemClick(int menuPosition, String colHeader, DistrReportModel clickPosition, boolean checked, String selectedColFunction);
    }

    public class MainFilterViewHolder extends RecyclerView.ViewHolder {


        final View mView;
        final TextView distNameTxt;
        final CheckBox checkBox;
        LinearLayout layoutSpinner;
        Spinner spinner;

        MainFilterViewHolder(View view) {
            super(view);
            mView = view;
            distNameTxt = view.findViewById(R.id.sub_filter_child_name_txt);
            checkBox = view.findViewById(R.id.list_view_item_checkbox);
            layoutSpinner = view.findViewById(R.id.layoutSpinner);
            spinner = view.findViewById(R.id.spinner);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + distNameTxt.getText();
        }

    }


}