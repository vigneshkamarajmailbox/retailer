package com.botree.retailerssfa.support;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.botree.retailerssfa.R;
import com.botree.retailerssfa.adapters.CurrencyListAdapter;
import com.botree.retailerssfa.models.ExtendedCurrency;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by shantarao on 10/4/18.
 */

public class CurrencyPicker extends DialogFragment {

    private CurrencyListAdapter adapter;
    private List<ExtendedCurrency> currenciesList = new ArrayList<>();
    private List<ExtendedCurrency> selectedCurrenciesList = new ArrayList<>();
    private CurrencyPickerListener listener;

    /**
     * To support show as dialog
     */
    public static CurrencyPicker newInstance(String dialogTitle) {
        CurrencyPicker picker = new CurrencyPicker();
        Bundle bundle = new Bundle();
        bundle.putString("dialogTitle", dialogTitle);
        picker.setArguments(bundle);
        return picker;
    }

    public CurrencyPicker() {
        setCurrenciesList(ExtendedCurrency.getOurInstance().getAllCurrencies());
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.currency_picker, container,false);
        Bundle args = getArguments();
        if (args != null && getDialog() != null) {
            String dialogTitle = args.getString("dialogTitle");
            getDialog().setTitle(dialogTitle);

            int width =200;
            int height =300;
            getDialog().getWindow().setLayout(width, height);
        }
        EditText searchEditText = view.findViewById(R.id.currency_code_picker_search);
        ListView currencyListView = view.findViewById(R.id.currency_code_picker_listview);

        selectedCurrenciesList = new ArrayList<>(currenciesList.size());
        selectedCurrenciesList.addAll(currenciesList);

        adapter = new CurrencyListAdapter(getActivity(), selectedCurrenciesList);
        currencyListView.setAdapter(adapter);

        currencyListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (listener != null) {
                    ExtendedCurrency currency = selectedCurrenciesList.get(position);
                    listener.onSelectCurrency(currency.getName(), currency.getCode(), currency.getSymbol(),
                            currency.getFlag());
                }
            }
        });

        searchEditText.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //ignored
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //ignored
            }

            @Override
            public void afterTextChanged(Editable s) {
                search(s.toString());
            }
        });

        return view;
    }

    @Override
    public void dismiss() {
        if (getDialog() != null) {
            super.dismiss();
        } else {
            getFragmentManager().popBackStack();
        }
    }

    public void setListener(CurrencyPickerListener listener) {
        this.listener = listener;
    }

    @SuppressLint("DefaultLocale")
    private void search(String text) {
        selectedCurrenciesList.clear();
        for (ExtendedCurrency currency : currenciesList) {
            if (currency.getName().toLowerCase(Locale.ENGLISH).contains(text.toLowerCase(Locale.getDefault()))) {
                selectedCurrenciesList.add(currency);
            }
        }
        adapter.notifyDataSetChanged();
    }

    public void setCurrenciesList(List<ExtendedCurrency> newCurrencies) {
        this.currenciesList.clear();
        this.currenciesList.addAll(newCurrencies);
    }
}
