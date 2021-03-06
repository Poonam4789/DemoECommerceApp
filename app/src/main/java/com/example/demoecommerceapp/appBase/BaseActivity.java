package com.example.demoecommerceapp.appBase;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.demoecommerceapp.R;
import com.example.demoecommerceapp.network.NetworkChangeReceiver;
import com.example.demoecommerceapp.network.NetworkStatus;
import com.google.android.material.snackbar.Snackbar;

import butterknife.BindView;
import butterknife.Unbinder;

public abstract class BaseActivity extends AppCompatActivity
{
    private NetworkChangeReceiver _networkReceiver;
    @BindView(R.id.progressDailog)
    protected ProgressBar progressDailog;
    protected Unbinder _unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        _networkReceiver = new NetworkChangeReceiver(networkChangeHandler);
    }

    protected void initToolbar(Toolbar toolbar)
    {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        registerNetworkChangeReceiver();
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        unregisterNetworkChangeReciver();
    }

    public void showHideProgress(NetworkStatus networkStatus)
    {
        switch (networkStatus)
        {
            case STATUS_LOADING:

                showProgressBar(true);
                break;

            case STATUS_SUCCESS:

                showProgressBar(false);
                break;

        }
    }

    protected void showProgressBar(boolean toShow)
    {
        if (progressDailog != null)
        {
            if (toShow)
            {
                progressDailog.setVisibility(View.VISIBLE);
            }
            else
            {
                progressDailog.setVisibility(View.GONE);
            }
        }
    }

    /*******************
     * Network change receiver implementation
     **************/

    /*************
     * Register Network Change Receiver
     *********/
    private void registerNetworkChangeReceiver()
    {
        registerReceiver(_networkReceiver, NetworkChangeReceiver.getNetworkChangeFilter());
    }

    /*********
     * Unregister Network Change Receiver
     ************/
    private void unregisterNetworkChangeReciver()
    {
        unregisterReceiver(_networkReceiver);
    }

    /**
     * @networkChangeHandler will get message
     * msg.what = NETWORK_NOT_CONNECTED; if network connected
     * msg.what = NETWORK_CONNECTED;     if network not connected
     */
    Handler networkChangeHandler = new Handler()
    {
        @Override
        public void handleMessage(Message networkMessage)
        {
            onNetworkChange(NetworkChangeReceiver.isOnline());
        }
    };

    protected abstract void onNetworkChange(boolean isNetworkConnected);

    protected void showNoNetworkBar(boolean isNetworkConnected)
    {
        if (!isNetworkConnected)
        {
            Snackbar.make(findViewById(android.R.id.content), "Please check network connection", Snackbar.LENGTH_LONG).show();
        }
    }

    /**
     * Butterknife binder and unbinder
     */

    @Override
    protected void onDestroy()
    {
        unBindViews();
        super.onDestroy();
    }

    private void unBindViews()
    {
        if (_unbinder != null)
        {
            _unbinder.unbind();
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
