package com.example.assignment_4;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity implements CurrencyDataBaseClient.DatabaseActionListener{

    ArrayList<Currency> listFromMA;
    ListView listOfCurrency;
    TextView number_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        number_text = findViewById(R.id.num_of_currency);
        listOfCurrency = findViewById(R.id.list_of_currency);

        listFromMA = getIntent().getParcelableArrayListExtra("listOfCurrency");
        number_text.setText("Total Saved Result : " + listFromMA.size());
        CurrencyDataBaseClient.listener = this;
        CurrencyDataBaseClient.getAllCurrency();
    }

    @Override
    public void databaseReturnWithList(List<Currency> currencyList) {
        ArrayList<Currency> alCurrency= new  ArrayList<Currency>(currencyList);
        CurrencyBaseAdapter listAdapter = new CurrencyBaseAdapter(alCurrency,this);
        listOfCurrency.setAdapter(listAdapter);
        number_text.setText("Total Saved Result : " + currencyList.size());

        listOfCurrency.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent myIntent = new Intent(view.getContext(),DetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("listOfCurrency",alCurrency);
                myIntent.putExtra("bundle",bundle);
                myIntent.putExtra("index",i);
                myIntent.putExtra("date",alCurrency.get(i).getDatetime());
                startActivity(myIntent);
            }
        });

    }
}
