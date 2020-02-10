package com.example.demoecommerceapp.Fragments;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.demoecommerceapp.CommercialProductsResponse;
import com.example.demoecommerceapp.CommertialApplication;
import com.example.demoecommerceapp.R;
import com.example.demoecommerceapp.adapters.CategoryPagerAdapter;
import com.example.demoecommerceapp.appBase.CommertialMainActivity;
import com.example.demoecommerceapp.model.CategoriesVO;
import com.example.demoecommerceapp.model.RankingVO;
import com.example.demoecommerceapp.network.RestApiInterface;
import com.google.android.material.tabs.TabLayout;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.demoecommerceapp.network.NetworkStatus.STATUS_LOADING;
import static com.example.demoecommerceapp.network.NetworkStatus.STATUS_SUCCESS;

public class CategoryPageFragment extends Fragment
{
    private static final String TAG = "RESPONSE";
    private TabLayout _categoryTabs;
    private ViewPager _productsPager;
    private CategoryPagerAdapter _categoryPagerAdapter;
    private List<CategoriesVO> _categoriesVOList;
    private List<RankingVO> _rankingVOList;
    private CommertialMainActivity commertialMainActivity;
    private TextView _all_title;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState)
    {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.category_page_layout, container, false);
        _categoryTabs = view.findViewById(R.id.categories_tabs);
        _productsPager = view.findViewById(R.id.product_pager);

        _all_title = view.findViewById(R.id.all_title);

        _categoryTabs.setTabMode(TabLayout.MODE_SCROLLABLE);
        commertialMainActivity = new CommertialMainActivity();
        CallWebservice();
        return view;
    }

    private void CallWebservice()
    {
        commertialMainActivity.showHideProgress(STATUS_LOADING);
        RestApiInterface restApiService = CommertialApplication.getRestApiClientInterface();
        Call<CommercialProductsResponse> call = restApiService.getCommercialProducts();
        call.enqueue(new Callback<CommercialProductsResponse>()
        {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override
            public void onResponse(Call<CommercialProductsResponse> call, Response<CommercialProductsResponse> response)
            {
                if (response.isSuccessful())
                {
                    commertialMainActivity.showHideProgress(STATUS_SUCCESS);
                    Log.d(TAG, "Got the body for the file");
                    _categoriesVOList = response.body().getCategoriesList();
                    _rankingVOList = response.body().getRankings();
                    if (_categoriesVOList != null)
                    {
                        SetTabPagerAdapters(_categoriesVOList,_rankingVOList);
                        Log.d(TAG, "Response " + response.body().toString());
                        Log.d(TAG, "CategoryList " + response.body().getCategoriesList().get(0).getCategoryName());
                    }
                }
                else
                {
                    Log.d(TAG, "Connection failed " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<CommercialProductsResponse> call, Throwable t)
            {
                commertialMainActivity.showHideProgress(STATUS_SUCCESS);
                Log.d("RESPONSE", "Exception " + t.toString());
            }
        });
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void SetTabPagerAdapters(List<CategoriesVO> categoriesList, List<RankingVO> rankingVOList)
    {
        _categoryPagerAdapter = new CategoryPagerAdapter(getChildFragmentManager(), categoriesList,rankingVOList);
        _categoryTabs.setupWithViewPager(_productsPager);
        _productsPager.setAdapter(_categoryPagerAdapter);
        _productsPager.setCurrentItem(0);
        _productsPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener()
        {
            @Override
            public void onPageSelected(int position)
            {
                super.onPageSelected(position);
                Log.d(TAG, "PageSelected" + _productsPager.getCurrentItem());
            }

        });
    }

    public void setCategortyProductContainerVisibility(boolean visibility)
    {
        if (visibility)
        {
            _categoryTabs.setVisibility(View.VISIBLE);
            _all_title.setVisibility(View.GONE);
        }
        else
        {
            _categoryTabs.setVisibility(View.GONE);
            _all_title.setVisibility(View.VISIBLE);
        }
    }

    public List<RankingVO> get_rankingVOList()
    {
        return _rankingVOList;
    }

    @Override
    public void onDestroyView()
    {
        super.onDestroyView();
        _productsPager = null;
        _categoryPagerAdapter = null;
    }
}
