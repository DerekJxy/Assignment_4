package com.example.assignment_4;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Locale;

public class JsonService {

    public ArrayList<String> getCountryFromJson(String json)  {
        ArrayList<String> stringArrayList = new ArrayList<>(0);
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray json_cities = jsonObject.getJSONArray("data");
            for (int i = 0 ; i< json_cities.length(); i++){
                JSONObject fullCityName = json_cities.getJSONObject(i);

                String temp = fullCityName.getString("name");
                String temName = fullCityName.getString("currency");
                String NameAndCurrency = temp + " --- " + temName;

                stringArrayList.add(NameAndCurrency);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return stringArrayList;
    }

    public String getCurrencyFromJson(String json, String firstCurrency, String secondCurrency)  {
        String currency = "";
        String temp = firstCurrency.toLowerCase();
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONObject json_currency = jsonObject.getJSONObject(temp);

            currency = json_currency.getString(secondCurrency.toLowerCase());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return currency;
    }
}
