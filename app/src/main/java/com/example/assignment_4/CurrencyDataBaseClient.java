package com.example.assignment_4;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import androidx.room.Room;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CurrencyDataBaseClient {

    static CurrencyDataBase dbClient;
    static Context db_context;

    public interface DatabaseActionListener {
        public void databaseReturnWithList(List<Currency> currencyList);
    }

    static   DatabaseActionListener listener;

    public static final ExecutorService databaseExecutor = Executors.newFixedThreadPool(4);
    static Handler handler = new Handler(Looper.getMainLooper());

    CurrencyDataBaseClient(Context context){
        db_context = context;
        dbClient = Room.databaseBuilder(context,
                CurrencyDataBase.class, "database-currency").build();
    }


    public static CurrencyDataBase getDbClient(){
        if (dbClient == null){
            dbClient = new CurrencyDataBaseClient(db_context).dbClient;
        }
        return dbClient;
    }

    public static void insertNewDonation(Currency newCurrency){
        // background thread
        databaseExecutor.execute(new Runnable() {
            @Override
            public void run() {
                dbClient.getCurrencyDao().insert(newCurrency);
            }
        });
    }

    public static void getAllCurrency(){
        databaseExecutor.execute(new Runnable() {
            @Override
            public void run() {
                List<Currency> listFromDB = dbClient.getCurrencyDao().getAllCurrency();
                // run some code in main thread
                // return from background thread
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        // return something to main thread
                        listener.databaseReturnWithList(listFromDB);
                    }
                });
            }
        });
    }
}
