package com.botree.retailerssfa.support;

import android.content.Context;
import android.graphics.Typeface;
import android.widget.TextView;

import com.botree.retailerssfa.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.XAxis.XAxisPosition;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.ChartData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.DefaultAxisValueFormatter;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

public class BarChartItem {

    private Context context;
    private BarChart barChart;
    private ChartData<?> chartData;
    private TextView barValueTxtView;
    private int chartType;
    private OnChartItemClick onChartItemClick;
    public BarChartItem(Context context, ChartData<?> cd, BarChart barChart, int chartType, TextView barValueTxt, String fragmentName, OnChartItemClick chartItemClick) {

        this.context = context;
        this.chartData = cd;
        this.chartType = chartType;
        this.barChart = barChart;
        this.barValueTxtView = barValueTxt;
        this.onChartItemClick = chartItemClick;
        mtdSalesBarchar(fragmentName);
    }

    public BarChartItem(Context context, ChartData<?> cd, BarChart barChart) {

        this.context = context;
        this.chartData = cd;
        this.barChart = barChart;

        barChart.getLegend().setEnabled(false);

        barchar();
    }

    private void barchar() {

        Typeface mTf = Typeface.createFromAsset(context.getAssets(), "OpenSans-Regular.ttf");
        barChart.getDescription().setEnabled(false);
        barChart.setDrawGridBackground(false); // disable background grid
        barChart.setDrawBarShadow(false); // disable bar shadow
        barChart.setScaleEnabled(false);//disable zoom

        barChart.setExtraOffsets(5, 5, 5, 5);

        // create a custom MarkerView (extend MarkerView) and specify the layout
        // to use for it
        com.botree.retailerssfa.bot.MyMarkerView mv = new com.botree.retailerssfa.bot.MyMarkerView(context, R.layout.custom_bar_marker);
        mv.setChartView(barChart); // For bounds control
        barChart.setMarker(mv); // Set the marker to the chart

        IAxisValueFormatter xAxisFormatter = new DefaultAxisValueFormatter(20);
        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxisPosition.BOTTOM);
        xAxis.setTypeface(mTf);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(true);
        xAxis.setGranularity(1f);
        xAxis.setLabelCount(7);
        xAxis.setValueFormatter(xAxisFormatter);


        YAxis leftAxis = barChart.getAxisLeft();
        leftAxis.setTypeface(mTf);
        leftAxis.setLabelCount(5, false);
        leftAxis.setSpaceTop(20f);
        leftAxis.setDrawGridLines(true);
        leftAxis.setAxisMinimum(20f); // this replaces setStartAtZero(true)

        leftAxis.setEnabled(true);
        leftAxis.setAxisMinimum(20f); // this replaces setStartAtZero(true)

        YAxis rightAxis = barChart.getAxisRight();
        rightAxis.setTypeface(mTf);
        rightAxis.setLabelCount(5, false);
        rightAxis.setSpaceTop(20f);

        rightAxis.setEnabled(false);
        rightAxis.setAxisMinimum(20f); // this replaces setStartAtZero(true)
        // this replaces setStartAtZero(true)
        rightAxis.setEnabled(false);

        chartData.setValueTypeface(mTf);

        // set data
        barChart.setData((BarData) chartData);
        barChart.setFitBars(true);

        // do not forget to refresh the chart
        barChart.invalidate();
        barChart.animateY(1000);
    }


    private void mtdSalesBarchar(final String fragmentName) {

        Typeface mTf = Typeface.createFromAsset(context.getAssets(), "OpenSans-Regular.ttf");
        barChart.getDescription().setEnabled(false);
        barChart.setDrawGridBackground(false); // disable background grid
        barChart.setDrawBarShadow(false); // disable bar shadow
        barChart.setScaleEnabled(true);//disable zoom
        barChart.setPinchZoom(true);
        barChart.setDoubleTapToZoomEnabled(false);

        IAxisValueFormatter xAxisFormatter = new DayAxisValueFormatter(barChart);
        XAxis xAxis = barChart.getXAxis();

        xAxis.setPosition(XAxisPosition.BOTTOM);
        xAxis.setTypeface(mTf);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(true);
        xAxis.setGranularity(1f);
        xAxis.setLabelCount(7);
        xAxis.setValueFormatter(xAxisFormatter);

        if (chartType == Globals.MTD_DASHBOARD_BARCHART) {
            MyMarkerView mv = new MyMarkerView(context, R.layout.custom_marker_view, barValueTxtView, fragmentName);
            mv.setChartView(barChart);
            barChart.setMarker(mv);
            if (fragmentName.equalsIgnoreCase("MTDWeekWise")) {
                xAxis.setValueFormatter(new IAxisValueFormatter() {

                    @Override
                    public String getFormattedValue(float value, AxisBase axis) {
                        return "Week " + (int) value;
                    }

                });
            }
        }

        YAxis leftAxis = barChart.getAxisLeft();
        leftAxis.setTypeface(mTf);
        leftAxis.setLabelCount(5, false);
        leftAxis.setSpaceTop(10f);
        leftAxis.setDrawGridLines(false);
        leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)
        leftAxis.setEnabled(true);
        leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)

        YAxis rightAxis = barChart.getAxisRight();
        rightAxis.setTypeface(mTf);
        rightAxis.setLabelCount(5, false);
        rightAxis.setSpaceTop(10f);
        rightAxis.setEnabled(false);
        rightAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)
        // this replaces setStartAtZero(true)
        rightAxis.setEnabled(false);


        if (chartType == 3) {

            leftAxis.setEnabled(true);
            leftAxis.setAxisMinimum(0f);

            xAxis.setValueFormatter(new IAxisValueFormatter() {

                @Override
                public String getFormattedValue(float value, AxisBase axis) {

                    if (((int) value) < 100) {

                        return (((int) value) + "Dist");

                    } else {
                        return "";
                    }
                }

            });
        }

        chartData.setValueTypeface(mTf);

        // set data
        barChart.setData((BarData) chartData);
        barChart.setFitBars(false);

        float barWidth = 0.9f; // x4 DataSet
        // specify the width each bar should have
        barChart.getBarData().setBarWidth(barWidth);
        // do not forget to refresh the chart
        barChart.invalidate();
        barChart.animateY(1500);

        barChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                if (onChartItemClick != null) {
                    onChartItemClick.onItemClick(e.getX(), fragmentName);
                }
            }

            @Override
            public void onNothingSelected() {
// ignored
            }
        });
    }

    public interface OnChartItemClick {
        void onItemClick(float xValue, String fragmentName);
    }
}
