package com.example.assignment_4;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity {

    ArrayList<Currency> savedCurrency;
    TextView details, currency_detail, title;
    int index = 0;
    String temp = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        details = findViewById(R.id.detail_Text);
        title = findViewById(R.id.title_text);
        currency_detail = findViewById(R.id.currency_detail_text);

        if(getIntent().hasExtra("bundle")){
            Bundle bundleFromMainActivity = getIntent().getBundleExtra("bundle");
            savedCurrency = bundleFromMainActivity.getParcelableArrayList("listOfCurrency");
            index = getIntent().getIntExtra("index", 0);
            temp = getIntent().getStringExtra("date");
        }
        currency_detail.setText(savedCurrency.get(index).getSelectedAmount()
                                + " "
                                + savedCurrency.get(index).getSelectedCurrency()
                                + " ≈ "
                                + savedCurrency.get(index).getConvertedAmount()
                                + " "
                                + savedCurrency.get(index).getConvertedCurrency());

        details.setText("Currency Rate is about :  1 : "
                        + savedCurrency.get(index).getConvertRate()
                        + "\n"
                        + "Stored Date time was : "
                        + temp
                        //+ savedCurrency.get(index).getDatetime() + temp
        );
        title.setText("Result No." + (index + 1) + " in Details ");
    }
}