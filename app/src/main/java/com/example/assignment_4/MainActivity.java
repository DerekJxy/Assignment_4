package com.example.assignment_4;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements NetworkService.NetworkingListener{

    NetworkService networkingManager;
    JsonService jsonService;
    Spinner dropdown,dropdown1;
    TextView resultAmount;
    Button doitButton;
    EditText inputAmount;
    AlertDialog.Builder builder;
    CurrencyDataBaseClient dataBaseClient;
    static CurrencyManager currencyManager = new CurrencyManager();
    Currency currencyObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dataBaseClient = new CurrencyDataBaseClient(this);
        networkingManager = ((myApp)getApplication()).getNetworkService();
        jsonService = ((myApp)getApplication()).getJsonService();
        networkingManager.listener = this;
        currencyObject = new Currency();

        dropdown = findViewById(R.id.spinner1);
        dropdown1 =findViewById(R.id.spinner2);
        resultAmount = findViewById(R.id.inputNumber);
        doitButton = findViewById(R.id.enterButton);
        inputAmount = findViewById(R.id.inputAmount);
        networkingManager.getAllCountryWithCurrency();
        builder = new AlertDialog.Builder(this);

        doitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String input = String.valueOf(inputAmount.getText());

                if(!input.equals("") && input.chars().allMatch(Character::isDigit) ) {// input is a integer number
                    int maxNumber = 999999999;
                    int inputNumber = Integer.parseInt(input);
                    if(inputNumber > 0 && inputNumber <= maxNumber) { // input number in the range (1 - 999,999,999)
                        String first_dropdownString = dropdown.getSelectedItem().toString();
                        String second_dropdownString = dropdown1.getSelectedItem().toString();

                        String firstCurrency = first_dropdownString.substring(first_dropdownString.indexOf("---") + 4, first_dropdownString.length());
                        String secondCurrency = second_dropdownString.substring(second_dropdownString.indexOf("---") + 4, second_dropdownString.length());
                        if (!firstCurrency.equals(secondCurrency))// two currency are not the same
                            networkingManager.getCurrency(firstCurrency, secondCurrency);
                        else
                            errorCurrency();
                    }else if(inputNumber == 0) { // input number is 0
                        numberZeroToast();
                    }
                    else{ // input number is less than 0 or larger than 999,999,999
                        numberErrorToast();
                    }
                }else{// input is not a integer number
                    errorToast();
                }
            }
        });

    }

    @Override
    public void dataListener(String jsonString) {
        ArrayList<String> strings = jsonService.getCountryFromJson(jsonString); // dropdown list that return from the function
        //set up the initial dropdown list for user
        ArrayAdapter<String> dropdownAdapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, strings);
        dropdown.setAdapter(dropdownAdapter);
        dropdown1.setAdapter(dropdownAdapter);
    }

    @Override
    public void secondDataListener(String jsonString, String firstCurrency, String secondCurrency) {

        String rateString = jsonService.getCurrencyFromJson(jsonString,firstCurrency, secondCurrency); // convert rate that return from the function
        double rate = Double.parseDouble(rateString);
        String displayRate = String.format("%.2f", rate);

        int inputNum = Integer.parseInt(inputAmount.getText().toString());// user input number
        double totalAmount = Math.round(inputNum * rate) ;// converted amount
        String displayAmount = String.format("%.2f", totalAmount);
        resultAmount.setText("Converted amount is : " + displayAmount);// set text

        // Dialog builder
        builder.create();
        builder.setTitle("Result");
        builder.setMessage(inputNum + " " + firstCurrency + " ≈ " + totalAmount + " " + secondCurrency + "\n"+ "Rate is :  1 : " + rate + "\n"
                            +"Do you want to save the convert between "+ firstCurrency + " and " + secondCurrency +" to Room Database?");
        builder.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String currentDateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());

                currencyObject.setSelectedCurrency(firstCurrency);
                currencyObject.setConvertedCurrency(secondCurrency);
                currencyObject.setSelectedAmount(inputNum);
                currencyObject.setConvertedAmount(displayAmount);
                currencyObject.setConvertRate(displayRate);
                currencyObject.setDatetime(currentDateTime);

                currencyManager.addNewCurrency(currencyObject);
                savedToast();
            }
        });
        builder.setPositiveButton("No",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                canceledToast();
            }
        });
        builder.show();

        currencyObject = new Currency(); //reset the object
    }

    //History button
    public void toListActivity(View view) {
        Intent toListActivity = new Intent(this, ListActivity.class);
        toListActivity.putParcelableArrayListExtra("listOfCurrency",currencyManager.getListOfCurrency());
        startActivity(toListActivity);
    }

    //Reset button
    public void restAllValue(View view) {
        inputAmount.setText("");
        resultAmount.setText("Your result will appear here");
        networkingManager.getAllCountryWithCurrency();
    }

    // Something wrong with the input number
    public void errorToast(){
        builder.create();
        builder.setTitle("Error");
        builder.setMessage("Sorry the input number is invalided! Please enter a correct Integer Number!");
        builder.setPositiveButton("OK",null);
        builder.setNegativeButton("Ignore",null);
        builder.show();
        Toast.makeText(this,"Sorry! Please enter a correct number!", Toast.LENGTH_SHORT).show();
    }

    // Toasts
    public void errorCurrency(){Toast.makeText(this,"Sorry! Please select two different currency to use the app!", Toast.LENGTH_SHORT).show(); }
    public void savedToast(){Toast.makeText(this,"Thank you! Your request has been saved to the Room Database!", Toast.LENGTH_SHORT).show(); }
    public void canceledToast(){Toast.makeText(this,"Oops! Your request has been canceled!", Toast.LENGTH_SHORT).show(); }
    public void numberErrorToast(){Toast.makeText(this,"Oops! Your input number is too large."+ "\n"+"Please try again!"+ "\n"+"(Number Range 1 - 999,999,999)", Toast.LENGTH_SHORT).show();}
    public void numberZeroToast(){Toast.makeText(this,"Sorry, input number can't be 0!"+ "\n"+"Please try again!"+ "\n"+"(Number Range 1 - 999,999,999)", Toast.LENGTH_SHORT).show();}
}