package com.botree.retailerssfa.adapters;

import android.content.Context;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.botree.retailerssfa.R;

import java.util.List;

import static com.botree.retailerssfa.support.Globals.fromHtml;

public class MarqueeSpinnerAdapter extends ArrayAdapter<String> {

    private final LayoutInflater mInflater;
    private final List<String> items;
    private final int mResource;

    public MarqueeSpinnerAdapter(@NonNull Context context, @LayoutRes int resource,
                                 @NonNull List objects) {
        super(context, resource, 0, objects);

        mInflater = LayoutInflater.from(context);
        mResource = resource;
        items = objects;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView,
                                @NonNull ViewGroup parent) {
        return createItemView(position, parent);
    }

    @Override
    public @NonNull
    View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return createItemView(position, parent);
    }

    private View createItemView(int position, ViewGroup parent) {
        final View view = mInflater.inflate(mResource, parent, false);

        TextView offTypeTv = view.findViewById(R.id.text1);

        offTypeTv.setText(fromHtml(items.get(position)));

        return view;
    }
}