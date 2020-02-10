package com.example.demoecommerceapp.appBase;

import android.os.Bundle;

import com.example.demoecommerceapp.Fragments.CategoryPageFragment;
import com.example.demoecommerceapp.R;


public class CommertialMainActivity extends BaseActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commertial_main);
        CategoryPageFragment pagerFragment = new CategoryPageFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.contentlayout, pagerFragment, "CategoryPager").commit();
    }

    @Override
    protected void onNetworkChange(boolean isNetworkConnected)
    {
        showNoNetworkBar(isNetworkConnected);
    }
}
