package com.example.demoecommerceapp.adapters;

import android.os.Bundle;
import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.demoecommerceapp.Fragments.ProductsCoverFragment;
import com.example.demoecommerceapp.model.CategoriesVO;
import com.example.demoecommerceapp.model.ProductsVO;

import java.util.ArrayList;
import java.util.List;

public class CategoryPagerAdapter extends FragmentStatePagerAdapter
{
    private List<CategoriesVO> _productCategoryList;
    private ArrayList<ProductsVO> _productsAllVOS;

    public CategoryPagerAdapter(FragmentManager fm, List<CategoriesVO> productCategoryList)
    {
        super(fm);
        _productCategoryList = productCategoryList;
        _productsAllVOS = new ArrayList<ProductsVO>();
        getAllProductsList();
    }

    @Override
    public Fragment getItem(int position)
    {
        ProductsCoverFragment pageFragment = new ProductsCoverFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(ProductsCoverFragment.PRODUCT_LIST, _productCategoryList.get(position).getProducts());
        bundle.putParcelableArrayList(ProductsCoverFragment.ALL_PRODUCTS_LIST, _productsAllVOS);
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

    public void getAllProductsList()
    {
        for (int i = 0; i < _productCategoryList.size(); i++)
        {
            _productsAllVOS.addAll(_productCategoryList.get(i).getProducts());
        }
        Log.d("SORT", "UNSORTED" + _productsAllVOS.size());
    }
}
