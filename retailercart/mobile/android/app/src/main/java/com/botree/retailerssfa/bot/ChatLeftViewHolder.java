package com.botree.retailerssfa.bot;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.botree.retailerssfa.R;
import com.botree.retailerssfa.support.BarChartItem;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.security.SecureRandom;
import java.util.ArrayList;

/**
 * Created by shantarao on 26/12/17.
 */

class ChatLeftViewHolder extends RecyclerView.ViewHolder {
    private final TextView botTxt;
    private final BarChart barChart;
    private SecureRandom random = new SecureRandom();

    ChatLeftViewHolder(View itemView) {
        super(itemView);
        botTxt = itemView.findViewById(R.id.bot_chat_txt);
        barChart = itemView.findViewById(R.id.bar_chart);
    }

    void bind(Context context, final ChatModel chatModel) {

        botTxt.setText(chatModel.getMessage());


        if (chatModel.getFirstName().startsWith("CHART") || chatModel.getFirstName().startsWith("GRAPH") ||
                chatModel.getFirstName().startsWith("graph")) {

            if (barChart.getVisibility() != View.VISIBLE) barChart.setVisibility(View.VISIBLE);
        } else {
            barChart.setVisibility(View.GONE);
        }

        new BarChartItem(context, generateDataBar(), barChart);

    }

    private BarData generateDataBar() {

        ArrayList<BarEntry> entries = new ArrayList<>();

        for (int i = 1; i < 13; i++) {
            entries.add(new BarEntry(i, (float)(random.nextInt(2700) + 300)));
        }

        BarDataSet d = new BarDataSet(entries, "MTD Sales for August");
        d.setColors(ColorTemplate.MATERIAL_COLORS);
        BarData cd = new BarData(d);
        cd.setDrawValues(false); // disable the bar top values
        cd.setBarWidth(0.9f);
        return cd;
    }
}
