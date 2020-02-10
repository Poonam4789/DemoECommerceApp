package com.example.demoecommerceapp;

import android.app.Application;
import android.content.Context;

import com.example.demoecommerceapp.network.NetworkChangeReceiver;
import com.example.demoecommerceapp.network.RestApiClient;
import com.example.demoecommerceapp.network.RestApiInterface;

public class CommertialApplication extends Application
{
    private static CommertialApplication _commertialApplication;
    private static RestApiInterface _restApiClientInterface;
    public static final String DEVELOPMENT_BUILD = "development", PRODUCTION_BUILD = "production";

    public static CommertialApplication getApplicationInstance()
    {
        return _commertialApplication;
    }

    @Override
    public void onCreate()
    {
        super.onCreate();
        _commertialApplication = this;
        _restApiClientInterface = RestApiClient.getRestInstance().getRetrofitApiInstance();
        NetworkChangeReceiver.initNetworkChange(getApplicationContext());
    }

    public static RestApiInterface getRestApiClientInterface()
    {
        if (_restApiClientInterface == null)
        {
            return _restApiClientInterface = RestApiClient.getRestInstance().getRetrofitApiInstance();
        }
        return _restApiClientInterface;
    }

    public Context getApplicationContext()
    {
        return CommertialApplication.this;
    }
}
