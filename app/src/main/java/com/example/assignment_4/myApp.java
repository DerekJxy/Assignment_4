package com.example.assignment_4;

import android.app.Application;

public class myApp extends Application {
    private NetworkService networkService = new NetworkService();

    public JsonService getJsonService() {
        return jsonService;
    }

    private JsonService jsonService = new JsonService();


    public NetworkService getNetworkService() {
        return networkService;
    }
}
