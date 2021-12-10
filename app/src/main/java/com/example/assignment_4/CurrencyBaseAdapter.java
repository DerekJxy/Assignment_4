package com.example.assignment_4;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CurrencyBaseAdapter extends BaseAdapter {
    ArrayList<Currency> listOfCurrency;
    Context list_activity_Context;

    CurrencyBaseAdapter(ArrayList<Currency> list, Context activity_Context) {
        listOfCurrency = list;
        list_activity_Context = activity_Context;
    }

    @Override
    public int getCount() {
        return listOfCurrency.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        view = LayoutInflater.from(list_activity_Context).inflate(R.layout.currency_item,null);
        TextView convert = view.findViewById(R.id.currency_line);
        TextView rate = view.findViewById(R.id.rate_line);
        TextView date = view.findViewById(R.id.date_line);

        convert.setText(listOfCurrency.get(i).getSelectedAmount()
                        + " "
                        + listOfCurrency.get(i).getSelectedCurrency()
                        +  "  ≈ "
                        + listOfCurrency.get(i).getConvertedAmount()
                        + " "
                        + listOfCurrency.get(i).getConvertedCurrency());
        rate.setText("The Convert Rate is : " + "1 : " + listOfCurrency.get(i).getConvertRate());

        date.setText("Stored Date: "+listOfCurrency.get(i).getDatetime());

        return view;
    }



}
