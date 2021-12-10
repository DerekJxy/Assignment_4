package com.example.assignment_4;

import java.util.ArrayList;

public class CurrencyManager {

    ArrayList<Currency> listOfCurrency = new ArrayList<>(0);


    public ArrayList<Currency> getListOfCurrency() {
        return listOfCurrency;
    }

    public void addNewCurrency(Currency c) {
        listOfCurrency.add(c);
        CurrencyDataBaseClient.insertNewDonation(c);
    }

}
