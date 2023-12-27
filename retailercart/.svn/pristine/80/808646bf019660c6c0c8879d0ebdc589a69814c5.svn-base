package com.botree.retailerssfa.support;

import com.github.mikephil.charting.charts.BarLineChartBase;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import static com.botree.retailerssfa.support.Globals.MTD_TRENDS_LINECHART;


public class DayAxisValueFormatter implements IAxisValueFormatter {

    protected String[] mMonths = new String[]{
            "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Okt", "Nov", "Dec"
    };
    private int chartType;
    private BarLineChartBase<?> chart;

    public DayAxisValueFormatter(BarLineChartBase<?> chart) {
        this.chart = chart;
    }

    public DayAxisValueFormatter(LineChart lineChart, int chartType) {

        this.chart = lineChart;
        this.chartType = chartType;
    }

    @Override
    public String getFormattedValue(float value, AxisBase axis) {

        int days = (int) value;

        int year = determineYear(days);

        int month = determineMonth(days);

        String monthName = "";
        String yearName = String.valueOf(year);
        if (chartType == MTD_TRENDS_LINECHART) {
            String appendix = "th";
            appendix = getMonthPrefix(days, appendix);
            return days + " " + appendix;
        } else {
            if (chart.getVisibleXRange() > 30 * 6) {

                return monthName + " " + yearName;
            } else {

                int dayOfMonth = determineDayOfMonth(days, month + 12 * (year - 2016));

                String appendix = "th";

                appendix = getMonthPrefix(dayOfMonth, appendix);

                return dayOfMonth == 0 ? "" : dayOfMonth + appendix + " " + monthName;
            }
        }

    }

    private String getMonthPrefix(int dayOfMonth, String appendix) {
        switch (dayOfMonth) {
            case 1:
                appendix = "st";
                break;
            case 2:
                appendix = "nd";
                break;
            case 3:
                appendix = "rd";
                break;
            case 21:
                appendix = "st";
                break;
            case 22:
                appendix = "nd";
                break;
            case 23:
                appendix = "rd";
                break;
            case 31:
                appendix = "st";
                break;
            default:
                break;
        }
        return appendix;
    }

    private int getDaysForMonth(int month) {

        // month is 0-based

        if (month == 1) {
            int x400 = month % 400;
            if (x400 < 0) {
                x400 = -x400;
            }
            boolean is29 = (month % 4) == 0 && x400 != 100 && x400 != 200 && x400 != 300;

            return is29 ? 29 : 28;
        }

        if (month == 3 || month == 5 || month == 8 || month == 10)
            return 30;
        else
            return 31;
    }

    private int determineMonth(int dayOfYear) {

        int month = -1;
        int days = 0;

        while (days < dayOfYear) {
            month = month + 1;

            if (month >= 12)
                month = 0;
            days += getDaysForMonth(month);
        }

        return Math.max(month, 0);
    }

    private int determineDayOfMonth(int days, int month) {

        int count = 0;
        int daysForMonths = 0;

        while (count < month) {

            daysForMonths += getDaysForMonth(count % 12);
            count++;
        }

        return days - daysForMonths;
    }

    private int determineYear(int days) {

        if (days <= 366)
            return 2016;
        else if (days <= 730)
            return 2017;
        else if (days <= 1094)
            return 2018;
        else if (days <= 1458)
            return 2019;
        else
            return 2020;

    }

}
