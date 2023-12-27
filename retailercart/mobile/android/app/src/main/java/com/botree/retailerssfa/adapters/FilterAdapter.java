package com.botree.retailerssfa.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.botree.retailerssfa.R;
import com.botree.retailerssfa.models.DistrReportModel;

import java.util.List;
import java.util.Locale;

public class FilterAdapter extends BaseAdapter {
    private Context context;
    private List<DistrReportModel> menutitles;

    public FilterAdapter(Context applicationContext, List<DistrReportModel> menuList) {
        this.context = applicationContext;
        this.menutitles = menuList;
    }

    @Override
    public int getCount() {
        return menutitles.size();
    }

    @Override
    public Object getItem(int position) {
        return menutitles.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater)
                    context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            assert mInflater != null;
            convertView = mInflater.inflate(R.layout.filter_list_item, parent, false);
        }
        TextView txtTitle = convertView.findViewById(R.id.filter_title);
        if (spaceCountInString(menutitles.get(position).getColumnGroup().trim()) == 0) {

            txtTitle.setText(menutitles.get(position).getColumnGroup());
        } else {
            txtTitle.setText(processWords(menutitles.get(position).getColumnGroup()).toUpperCase(Locale.getDefault()));

        }

        return convertView;
    }

    private String processWords(String input) {

        StringBuilder charBuffer = new StringBuilder();
        String[] s = input.split("(\\s)+");
        for (String values : s) {
            charBuffer.append(values.charAt(0));
        }
        return charBuffer.toString();
    }

    private int spaceCountInString(String str) {
        int count = 0;
        for (int i = 0; i < str.length(); i++) {
            if (Character.isWhitespace(str.charAt(i))) count++;
        }
        return count;
    }
}
