package com.example.demoecommerceapp.adapters;

import android.os.Bundle;
import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.demoecommerceapp.Fragments.ProductsCoverFragment;
import com.example.demoecommerceapp.model.CategoriesVO;
import com.example.demoecommerceapp.model.ProductsVO;
import com.example.demoecommerceapp.model.RankingProductVO;
import com.example.demoecommerceapp.model.RankingVO;

import java.util.ArrayList;
import java.util.List;

public class CategoryPagerAdapter extends FragmentStatePagerAdapter
{
    private static final String TAG = "CategoryPagerAdapter";
    private List<CategoriesVO> _productCategoryList;
    private ArrayList<ProductsVO> _productsAllVOS;
    private ArrayList<RankingProductVO> _mostViewedList;
    private ArrayList<RankingProductVO> _mostOrderedList;
    private ArrayList<RankingProductVO> _mostSharedList;
    private List<RankingVO> _rankingVOList;

    public CategoryPagerAdapter(FragmentManager fm, List<CategoriesVO> productCategoryList, List<RankingVO> rankingVOList)
    {
        super(fm);
        _productCategoryList = productCategoryList;
        _rankingVOList = rankingVOList;
        _productsAllVOS = new ArrayList<>();
        _mostViewedList = new ArrayList<>();
        _mostOrderedList = new ArrayList<>();
        _mostSharedList = new ArrayList<>();
        getAllProductsList();
        getRankProductsList();
    }

    @Override
    public Fragment getItem(int position)
    {
        ProductsCoverFragment pageFragment = new ProductsCoverFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(ProductsCoverFragment.PRODUCT_LIST, _productCategoryList.get(position).getProducts());
        bundle.putParcelableArrayList(ProductsCoverFragment.ALL_PRODUCTS_LIST, _productsAllVOS);
        bundle.putParcelableArrayList(ProductsCoverFragment.MOST_VIEWED_PRODUCTS_LIST, _mostViewedList);
        bundle.putParcelableArrayList(ProductsCoverFragment.MOST_ORDERED_PRODUCTS_LIST, _mostOrderedList);
        bundle.putParcelableArrayList(ProductsCoverFragment.MOST_SHARED_PRODUCTS_LIST, _mostSharedList);
        pageFragment.setArguments(bundle);
        return pageFragment;
    }

    @Override
    public int getCount()
    {
        return _productCategoryList.size();
    }

    @Override
    public CharSequence getPageTitle(int position)
    {
        return _productCategoryList.get(position).getCategoryName();
    }

    private void getAllProductsList()
    {
            for (int i = 0; i < _productCategoryList.size(); i++)
            {
                _productsAllVOS.addAll(_productCategoryList.get(i).getProducts());
                Log.d(TAG, "getAllProductsList: " + _productsAllVOS.size());
            }
        Log.d("SORT", "UNSORTED" + _productCategoryList.size());
    }

    private void getRankProductsList()
    {
        if (_rankingVOList.size() == 3)
        {
            for (int i = 0; i < _rankingVOList.size(); i++)
            {
                if (i == 0)
                {
                    _mostViewedList.addAll(_rankingVOList.get(i).getProducts());
                } else if (i == 1)
                {
                    _mostOrderedList.addAll(_rankingVOList.get(i).getProducts());
                } else
                {
                    _mostSharedList.addAll(_rankingVOList.get(i).getProducts());
                }
                Log.d(TAG, "getAllProductsList: " + _mostViewedList.size());
            }
        }
        Log.d("SORT", "UNSORTED" + _mostSharedList.size());
    }
}
