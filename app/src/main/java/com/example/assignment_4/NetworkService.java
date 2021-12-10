package com.example.assignment_4;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NetworkService {

    private String countryWithCurrencyUrl= "https://countriesnow.space/api/v0.1/countries/currency";
//    private String currencyUrl1 = "https://www.amdoren.com/api/currency.php?api_key=L9HuinGiD5EqrXdyvi6t7jknmWDVGr&from=";
//    private String currencyUrl2 = "&to=";
//    private String currencyUrl3 ="&amount=";
    private String currencyUrlTest = "https://cdn.jsdelivr.net/gh/fawazahmed0/currency-api@1/latest/currencies/";

    public static ExecutorService networkExecutorService = Executors.newFixedThreadPool(4);
    public static Handler networkingHandler = new Handler(Looper.getMainLooper());

    interface NetworkingListener{
        void dataListener(String jsonString);
        void secondDataListener(String jsonString, String firstCurrency, String secondCurrency);
    }
    public NetworkingListener listener;

    public void getAllCountryWithCurrency(){
        String url = countryWithCurrencyUrl;
        connection(url);
    }

    public void getCurrency(String firstCurrency, String secondCurrency){
        secondConnection(firstCurrency, secondCurrency);
    }

    public void connection(String url){
        networkExecutorService.execute(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection httpURLConnection = null;
                try {
                    String jsonData = "";
                    URL urlObj = new URL(url);
                    httpURLConnection = (HttpURLConnection) urlObj.openConnection();
                    httpURLConnection.setRequestMethod("GET");
                    httpURLConnection.setRequestProperty("Conent-Type","application/json");
                    InputStream in = httpURLConnection.getInputStream();
                    InputStreamReader reader = new InputStreamReader(in);
                    int inputSteamData = 0;
                    while ( (inputSteamData = reader.read()) != -1){
                        char current = (char)inputSteamData;
                        jsonData += current;
                    }
                    final String finalData = jsonData;
                    networkingHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            listener.dataListener(finalData);
                        }
                    });
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                finally {
                    httpURLConnection.disconnect();
                }
            }
        });

    }


    public void secondConnection(String firstCurrency, String secondCurrency){
        networkExecutorService.execute(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection httpURLConnection = null;
                try {
                    String jsonData = "";
                    URL urlObj = new URL(currencyUrlTest + firstCurrency.toLowerCase() +".json");
                    httpURLConnection = (HttpURLConnection) urlObj.openConnection();
                    httpURLConnection.setRequestMethod("GET");
                    httpURLConnection.setRequestProperty("Conent-Type","application/json");
                    InputStream in = httpURLConnection.getInputStream();
                    InputStreamReader reader = new InputStreamReader(in);
                    int inputSteamData = 0;
                    while ( (inputSteamData = reader.read()) != -1){
                        char current = (char)inputSteamData;
                        jsonData += current;
                    }
                    final String finalData = jsonData;
                    networkingHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            listener.secondDataListener(finalData,firstCurrency,secondCurrency);
                        }
                    });
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                finally {
                    httpURLConnection.disconnect();
                }
            }
        });

    }


}
