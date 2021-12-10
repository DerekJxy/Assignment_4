package com.example.assignment_4;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@Entity
public class Currency implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    public int id;

    public String selectedCurrency;
    public String convertedCurrency;
    public int selectedAmount;
    public String convertedAmount;
    public String convertRate;
    public String datetime;

    public Currency(){
        this.selectedCurrency = "";
        this.convertedCurrency = "";
        this.selectedAmount = 0;
        this.convertedAmount = "";
        this.convertRate = "";
        this.datetime = "";
    }

    public Currency(int id, String selectedCurrency, String convertedCurrency, int selectedAmount, String convertedAmount, String convertRate, String datetime) {
        this.id = id;
        this.selectedCurrency = selectedCurrency;
        this.convertedCurrency = convertedCurrency;
        this.selectedAmount = selectedAmount;
        this.convertedAmount = convertedAmount;
        this.convertRate = convertRate;
        this.datetime = datetime;
    }

    public String getSelectedCurrency() {
        return selectedCurrency;
    }

    public String getConvertedCurrency() {
        return convertedCurrency;
    }

    public double getSelectedAmount() {
        return selectedAmount;
    }

    public String getConvertedAmount() {
        return convertedAmount;
    }

    public String getConvertRate() {
        return convertRate;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setSelectedCurrency(String selectedCurrency) {
        this.selectedCurrency = selectedCurrency;
    }

    public void setConvertedCurrency(String convertedCurrency) {
        this.convertedCurrency = convertedCurrency;
    }

    public void setSelectedAmount(int selectedAmount) {
        this.selectedAmount = selectedAmount;
    }

    public void setConvertedAmount(String convertedAmount) {
        this.convertedAmount = convertedAmount;
    }

    public void setConvertRate(String convertRate) {
        this.convertRate = convertRate;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    protected Currency(Parcel in) {
        selectedCurrency = in.readString();
        convertedCurrency = in.readString();
        selectedAmount = in.readInt();
        convertedAmount = in.readString();
        convertRate = in.readString();
    }

    public static final Creator<Currency> CREATOR = new Creator<Currency>() {
        @Override
        public Currency createFromParcel(Parcel in) {
            return new Currency(in);
        }

        @Override
        public Currency[] newArray(int size) {
            return new Currency[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(selectedCurrency);
        parcel.writeString(convertedCurrency);
        parcel.writeInt(selectedAmount);
        parcel.writeString(convertedAmount);
        parcel.writeString(convertRate);
    }
}
