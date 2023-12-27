package com.botree.retailerssfa.support;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;

import com.botree.retailerssfa.R;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;


public class PieChartView {
    private Context context;
    private int screencount;
    private PieChart mChart;
    private Typeface mTfRegular;
    private OnChartItemClick onChartItemClick;

    public PieChartView(Context context, PieChart mChart, PieData pieChartData) {
        this.context = context;
        this.mChart = mChart;

        pieChartCoverage(pieChartData);
    }

    public PieChartView(Context context, PieChart mChart, int count, PieData pieChartData) {

        this.context = context;
        this.screencount = count;
        this.mChart = mChart;

        pieChart(pieChartData);
    }

    /**
     * @param context  current acitivity context
     * @param pieData  data for pieChart
     * @param pieChart Pie chart View
     */
    public PieChartView(Context context, PieData pieData, PieChart pieChart, String chartName) {
        this.context = context;
        this.mChart = pieChart;

        if (chartName.equalsIgnoreCase("ChannelPerformance")) {
            channelContributionPieChart(pieData);
        } else {
            pieChartTrendsActivity(pieData, chartName);//TOP10SKU Fragment
        }
    }

    private void pieChartTrendsActivity(PieData pieData, String chartName) {
        mChart.setUsePercentValues(true);
        mChart.getDescription().setEnabled(false);
        mChart.setExtraOffsets(5, 10, 5, 5);

        mChart.setDragDecelerationFrictionCoef(0.95f);
        mChart.setCenterText(generateCenterSpannableTrendsText(chartName));

        mChart.setDrawHoleEnabled(true);
        mChart.setHoleColor(Color.WHITE);

        mChart.setTransparentCircleColor(Color.WHITE);
        mChart.setTransparentCircleAlpha(110);

        mChart.setHoleRadius(58f);
        mChart.setTransparentCircleRadius(61f);

        mChart.setDrawCenterText(true);

        mChart.setRotationAngle(0);
        // enable rotation of the chart by touch
        mChart.setRotationEnabled(false);
        mChart.setHighlightPerTapEnabled(true);


        mChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);

        Legend l = mChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(0f);
        l.setYOffset(0f);
        l.setTextSize(11f);
        l.setEnabled(chartName.equals("TOP 10 SKUs"));

        mChart.getLegend().setWordWrapEnabled(true);
        // entry label styling
        mChart.setEntryLabelColor(Color.TRANSPARENT);
        mChart.setEntryLabelTypeface(mTfRegular);
        mChart.setEntryLabelTextSize(12f);

        mChart.setData(pieData);
        mChart.invalidate();
    }

    public void setOnChartItemClickListener(OnChartItemClick mItemClickListener) {
        this.onChartItemClick = mItemClickListener;
    }

    private void pieChartCoverage(PieData pieChartData) {

        mChart.setUsePercentValues(true);
        mChart.getDescription().setEnabled(false);
        mChart.setExtraOffsets(5, 10, 5, 5);

        mChart.setDragDecelerationFrictionCoef(0.95f);
        mChart.setDrawHoleEnabled(true);
        mChart.setHoleColor(Color.WHITE);

        mChart.setTransparentCircleColor(Color.WHITE);
        mChart.setTransparentCircleAlpha(50);

        mChart.setHoleRadius(55f);
        mChart.setDrawCenterText(true);

        mChart.setTransparentCircleRadius(60f);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mChart.setElevation(70f);
        }

        mChart.setRotationAngle(0);
        mChart.setTouchEnabled(true);
        mChart.setHighlightPerTapEnabled(true);
        // enable rotation of the chart by touch
        mChart.setRotationEnabled(false);
        mChart.setDrawCenterText(true);
        mChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                if (onChartItemClick != null) {
                    onChartItemClick.onItemClick("", e.getX(), mChart.getId());
                }
            }

            @Override
            public void onNothingSelected() {
                //ignored
            }
        });

        mChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);

        Legend l = mChart.getLegend();


        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);

        if (!context.getResources().getBoolean(R.bool.portrait_only)) {

            l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
            l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
            l.setOrientation(Legend.LegendOrientation.VERTICAL);
        }

        l.setDrawInside(true);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(0f);
        l.setYOffset(0f);
        l.setTextSize(12f);
        l.setEnabled(false);

        mChart.getLegend().setWordWrapEnabled(true);
        // entry label styling
        mChart.setEntryLabelColor(Color.WHITE);
        mChart.setEntryLabelTypeface(mTfRegular);
        mChart.setEntryLabelTextSize(14f);

        mChart.setData(pieChartData);
        mChart.invalidate();
    }

    private void channelContributionPieChart(PieData pieData) {

        mChart.setUsePercentValues(true);
        mChart.getDescription().setEnabled(false);
        mChart.setExtraOffsets(5, 10, 5, 5);

        mChart.setDragDecelerationFrictionCoef(0.95f);

        mChart.setCenterText(generateCenterSpannableTrendsText("Channel \n Contribution"));
        mChart.setCenterTextSize(10);

        mChart.setDrawHoleEnabled(true);
        mChart.setHoleColor(Color.WHITE);

        mChart.setTransparentCircleColor(Color.WHITE);
        mChart.setTransparentCircleAlpha(110);

        mChart.setHoleRadius(50f);
        mChart.setTransparentCircleRadius(63f);

        mChart.setDrawCenterText(true);

        mChart.setRotationAngle(0);
        // enable rotation of the chart by touch
        mChart.setRotationEnabled(false);
        mChart.setHighlightPerTapEnabled(true);


        mChart.setDragDecelerationFrictionCoef(0.95f);

        mChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);

        Legend l = mChart.getLegend();


        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);


        l.setDrawInside(false);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(0f);
        l.setYOffset(0f);
        l.setEnabled(false);

        mChart.getLegend().setWordWrapEnabled(true);
        // entry label styling
        mChart.setEntryLabelColor(Color.WHITE);
        mChart.setEntryLabelTypeface(mTfRegular);
        mChart.setEntryLabelTextSize(12f);
        mChart.setData(pieData);

        // undo all highlights
        mChart.highlightValues(null);

        mChart.invalidate();
    }

    public void pieChart(PieData pieChartData) {

        mChart.setUsePercentValues(true);
        mChart.getDescription().setEnabled(false);
        mChart.setExtraOffsets(5, 10, 5, 5);
        mChart.setDragDecelerationFrictionCoef(0.95f);
        mChart.setCenterText("Top 10 Retailers");

        mChart.setDrawHoleEnabled(false);
        mChart.setHoleColor(Color.WHITE);

        mChart.setTransparentCircleColor(Color.WHITE);
        mChart.setTransparentCircleAlpha(110);

        mChart.setHoleRadius(58f);
        mChart.setTransparentCircleRadius(61f);

        mChart.setDrawCenterText(false);

        mChart.setRotationAngle(0);
        // enable rotation of the chart by touch
        mChart.setHighlightPerTapEnabled(true);
        mChart.setTouchEnabled(true);
        mChart.setRotationEnabled(false);

        mChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);

        Legend l = mChart.getLegend();

        if (screencount == 1 || screencount == 2) {

            l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
            l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
            l.setOrientation(Legend.LegendOrientation.HORIZONTAL);

        } else {
            l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
            l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
            l.setOrientation(Legend.LegendOrientation.VERTICAL);

        }

        if (!context.getResources().getBoolean(R.bool.portrait_only)) {
            l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
            l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
            l.setOrientation(Legend.LegendOrientation.VERTICAL);
        }

        l.setDrawInside(false);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(0f);
        l.setYOffset(0f);

        mChart.setData(pieChartData);

        mChart.getLegend().setWordWrapEnabled(true);
        // entry label styling
        mChart.setEntryLabelColor(Color.WHITE);
        mChart.setEntryLabelTypeface(mTfRegular);
        mChart.setEntryLabelTextSize(0f);
        mChart.getData().setValueTypeface(mTfRegular);

        // undo all highlights
        mChart.highlightValues(null);

        mChart.invalidate();
    }

    private SpannableString generateCenterSpannableTrendsText(String chartName) {
        SpannableString s = new SpannableString(chartName);
        s.setSpan(new StyleSpan(Typeface.NORMAL), 0, s.length(), 0);
        s.setSpan(new ForegroundColorSpan(Color.BLACK), 0, s.length(), 0);
        s.setSpan(new RelativeSizeSpan(1.3f), 0, s.length(), 0);
        s.setSpan(new StyleSpan(Typeface.ITALIC), 0, s.length(), 0);

        return s;
    }


    public interface OnChartItemClick {
        void onItemClick(String flag, float x, int chartId);
    }
}
