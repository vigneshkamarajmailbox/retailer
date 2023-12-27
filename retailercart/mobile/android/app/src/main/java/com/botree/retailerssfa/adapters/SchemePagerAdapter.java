/*
 * Copyright (C) 2016 Botree Software International Private Limited
 */

package com.botree.retailerssfa.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.botree.retailerssfa.R;
import com.botree.retailerssfa.db.SFADatabase;
import com.botree.retailerssfa.models.SchemeModel;
import com.botree.retailerssfa.util.DateUtil;

import java.util.List;


public class SchemePagerAdapter extends PagerAdapter {
    private Context con;
    private List<SchemeModel> schemelist;
    private LayoutInflater inflater;
    private SFADatabase db;


    public SchemePagerAdapter(Context con, List<SchemeModel> schemes) {
        this.con = con;
        this.schemelist = schemes;
        inflater = LayoutInflater.from(con);
        db = SFADatabase.getInstance(con);

        for (SchemeModel schemeModel : schemes) {
            schemeModel.setUom(SFADatabase.getInstance(con).getUomCodeForScheme(schemeModel));
        }
    }

    @Override
    public int getCount() {
        return schemelist.size();
    }

    @NonNull
    @SuppressLint("NewApi")
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        String desc = "Scheme Not Available";
        String no = (position + 1) + "/" + schemelist.size();

        inflater = (LayoutInflater) con.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        assert inflater != null;
        View itemView = inflater.inflate(R.layout.scheme_viewpager, container, false);
        TextView pageno = itemView.findViewById(R.id.pageno);
        TextView schemeDesc = itemView.findViewById(R.id.schemedesc);
        TextView schemeCode = itemView.findViewById(R.id.schemeCode);
        TextView payoutTxt = itemView.findViewById(R.id.scheme_payout_txt);
        TextView schemeTypeTxt = itemView.findViewById(R.id.scheme_type_txt);
        TextView schemeStartDateTxt = itemView.findViewById(R.id.scheme_start_date_txt);
        TextView schemeEndDateTxt = itemView.findViewById(R.id.scheme_end_date_txt);
        TextView schemeSlabUomTxt = itemView.findViewById(R.id.scheme_slab_uom_txt);
        TextView tvPayout = itemView.findViewById(R.id.tvPayout);
        RecyclerView recyclerViewSchemeSlabDetails = itemView.findViewById(R.id.recyclerviewSchemeDetails);
        recyclerViewSchemeSlabDetails.setLayoutManager(new LinearLayoutManager(con));

        pageno.setText(no);
        if (schemelist.get(position).getSchemeDescription() != null) {

            schemeCode.setText(schemelist.get(position).getSchemeCode());
            schemeDesc.setText(schemelist.get(position).getSchemeDescription());
            payoutTxt.setText(schemelist.get(position).getPayoutType());
            schemeTypeTxt.setText(schemelist.get(position).getSchemeBase());
            schemeSlabUomTxt.setText(schemelist.get(position).getUom());
            schemeStartDateTxt.setText(DateUtil.covertTimeStampIntoData(schemelist.get(position).getStartDate(), "dd/MM/yyyy"));
            schemeEndDateTxt.setText(DateUtil.covertTimeStampIntoData(schemelist.get(position).getEndDate(), "dd/MM/yyyy"));
            List<SchemeModel> schemeslablist;
            schemeslablist = db.getSchemeSlabDetails(schemelist.get(position).getSchemeCode());
            SchemeSlabDetailAdapter schemeslabAdapter = new SchemeSlabDetailAdapter(schemeslablist);
            recyclerViewSchemeSlabDetails.setAdapter(schemeslabAdapter);
            tvPayout.setText(con.getResources().getString(R.string.scheme_slab_payout)+ " ( " + schemelist.get(position).getPayoutType()+ " )");
        } else {
            schemeDesc.setText(desc);
        }
        container.addView(itemView);

        return itemView;

    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((LinearLayout) object);
    }

    @Override
    public boolean isViewFromObject(@NonNull View arg0, @NonNull Object arg1) {
        return arg0 == arg1;
    }
}
