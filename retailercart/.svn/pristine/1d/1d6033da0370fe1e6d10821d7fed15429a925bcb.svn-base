package com.botree.retailerssfa.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.botree.retailerssfa.R;
import com.botree.retailerssfa.models.ExtendedCurrency;

import java.util.List;

public class CurrencyListAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private Context mContext;
    private List<ExtendedCurrency> currencies;

    public CurrencyListAdapter(Context context, List<ExtendedCurrency> currencies) {
        super();
        this.mContext = context;
        this.currencies = currencies;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return currencies.size();
    }

    @Override
    public Object getItem(int arg0) {
        return null;
    }

    @Override
    public long getItemId(int arg0) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ExtendedCurrency currency = currencies.get(position);

        if (view == null)
            view = inflater.inflate(R.layout.currency_row, parent, false);

        Cell cell = Cell.from(view);
        if (cell != null)
            cell.textView.setText(currency.getName());

        currency.loadFlagByCode(mContext);
        if (currency.getFlag() != -1 && cell != null)
            cell.imageView.setImageResource(currency.getFlag());
        return view;
    }

    static class Cell {
        private TextView textView;
        private ImageView imageView;

        static Cell from(View view) {
            if (view == null)
                return null;

            if (view.getTag() == null) {
                Cell cell = new Cell();
                cell.textView = view.findViewById(R.id.row_title);
                cell.imageView = view.findViewById(R.id.row_icon);
                view.setTag(cell);
                return cell;
            } else {
                return (Cell) view.getTag();
            }
        }
    }
}
