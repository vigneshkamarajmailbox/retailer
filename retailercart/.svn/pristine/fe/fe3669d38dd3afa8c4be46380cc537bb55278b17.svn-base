package com.botree.retailerssfa.support;

import android.annotation.SuppressLint;
import android.content.Context;
import android.widget.TextView;


import com.botree.retailerssfa.R;
import com.botree.retailerssfa.util.AppUtils;
import com.botree.retailerssfa.util.DateUtil;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.CandleEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.MPPointF;
import com.github.mikephil.charting.utils.Utils;

import java.util.Calendar;

@SuppressLint("ViewConstructor")
public class MyMarkerView extends MarkerView {

    private final String fragmentName;
    private TextView tvContent;
    private TextView barValueTxt;
    private DateUtil dateUtil;

    public MyMarkerView(Context context, int layoutResource, TextView bartxt, String fragmentName) {
        super(context, layoutResource);

        this.barValueTxt = bartxt;
        this.fragmentName = fragmentName;

        tvContent = findViewById(R.id.tvContent);
        dateUtil = DateUtil.getOurInstance();
    }

    // callbacks everytime the MarkerView is redrawn, can be used to update the
    // content (user-interface)
    @Override
    public void refreshContent(Entry e, Highlight highlight) {

        String currentMonthNumber = DateUtil.getCurrentMonth(dateUtil.covertUtilDateIntoDatebaseDateString(Calendar.getInstance().getTime()));
        String currentMonthName = AppUtils.getOurInstance().getMonthName(Integer.parseInt(currentMonthNumber) - 1);
        if (fragmentName.equalsIgnoreCase("MTDWeekWise")) {
            currentMonthName = " Week";
        }
        if (e instanceof CandleEntry) {

            CandleEntry ce = (CandleEntry) e;
            tvContent.setText(String.valueOf("" + Utils.formatNumber(ce.getHigh(), 0, true)));
            barValueTxt.setText(String.valueOf("" + currentMonthName + Utils.formatNumber(e.getX(), 0, true)
                    + " : " + AppUtils.getOurInstance().getRsFormat((double) e.getY())));

        } else {

            tvContent.setText(String.valueOf("" + Utils.formatNumber(e.getY(), 0, true)));

            barValueTxt.setText(String.valueOf("" + currentMonthName + Utils.formatNumber(e.getX(), 0, true)
                    + " : " +AppUtils.getOurInstance().getRsFormat((double) e.getY())));

        }

        super.refreshContent(e, highlight);
    }

    @Override
    public MPPointF getOffset() {
        return new MPPointF(-(getWidth() / 2), -getHeight());
    }
}
