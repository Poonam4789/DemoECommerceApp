package com.example.demoecommerceapp.Fragments;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Switch;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demoecommerceapp.R;
import com.example.demoecommerceapp.adapters.PageListViewAdapter;
import com.example.demoecommerceapp.adapters.ProductPageAdapter;
import com.example.demoecommerceapp.model.ProductsVO;
import com.example.demoecommerceapp.model.RankingProductVO;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ProductsCoverFragment extends Fragment implements CompoundButton.OnCheckedChangeListener
{
    private final String TAG = "ProductCoverFragment";
    public static final String PRODUCT_LIST = "product_list";
    public static final String ALL_PRODUCTS_LIST = "all_products_list";
    public static final String VARIANT_LIST = "variant_list";

    private RecyclerView _productCoversListView;
    private ArrayList<ProductsVO> _productList;
    private ArrayList<RankingProductVO> _allProductsList;
    private ProductPageAdapter _productPageAdapter;
    private PageListViewAdapter _pageListViewAdapter;
    private ProgressBar _progressBar;
    private Switch _switchRanking;
    private ListView _rankingLiew;
    private CategoryPageFragment _parentFrag;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.page_cover_layout, container, false);

        _progressBar = (ProgressBar) view.findViewById(R.id.page_progress);

        _productCoversListView = (RecyclerView) view.findViewById(R.id.product_view);
        _rankingLiew = (ListView) view.findViewById(R.id.rankingLiew);
        _switchRanking = (Switch) view.findViewById(R.id.swich_ranking);

        _productCoversListView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        _productCoversListView.setItemAnimator(null);

        _productList = getArguments().getParcelableArrayList(PRODUCT_LIST);
        _allProductsList = getArguments().getParcelableArrayList(ALL_PRODUCTS_LIST);

        _parentFrag = ((CategoryPageFragment) ProductsCoverFragment.this.getParentFragment());
        _parentFrag.setCategortyProductContainerVisibility(true);

        Log.d(TAG, "no of Products" + _productList.size());
        Log.d(TAG, "no of All Products" + _allProductsList.size());

        _switchRanking.setOnCheckedChangeListener(this);
        SortListByRanking(_allProductsList);
        _progressBar.setVisibility(View.VISIBLE);
        if (CheckSwitchEnabled())
        {
            setProductRankingWiseAdapter();
        }
        else
        {
            setProductCategoryWiseAdapter();
        }
        return view;
    }

    private void setProductCategoryWiseAdapter()
    {
        _progressBar.setVisibility(View.GONE);
        _parentFrag.setCategortyProductContainerVisibility(true);

        _productCoversListView.setVisibility(View.VISIBLE);
        _rankingLiew.setVisibility(View.GONE);

        _productPageAdapter = new ProductPageAdapter(getActivity().getSupportFragmentManager(), _productList);
        _productCoversListView.setAdapter(_productPageAdapter);
    }

    private void setProductRankingWiseAdapter()
    {
        _progressBar.setVisibility(View.GONE);
        _parentFrag.setCategortyProductContainerVisibility(false);
        _rankingLiew.setVisibility(View.VISIBLE);
        _productCoversListView.setVisibility(View.GONE);

        _pageListViewAdapter = new PageListViewAdapter(getContext(), R.layout.product_view_item, getActivity().getSupportFragmentManager(), _allProductsList);
        _rankingLiew.setAdapter(_pageListViewAdapter);
    }

    @Override
    public void onDestroyView()
    {
        super.onDestroyView();
        _progressBar = null;
        _productCoversListView = null;
        _productPageAdapter = null;
        _pageListViewAdapter = null;
    }

    public boolean CheckSwitchEnabled()
    {
        if (_switchRanking.isChecked())
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
    {
        if (isChecked)
        {
            setProductRankingWiseAdapter();
            _pageListViewAdapter.notifyDataSetChanged();
        }
        else
        {
            setProductCategoryWiseAdapter();
            _productPageAdapter.notifyDataSetChanged();
            _progressBar.setVisibility(View.GONE);
        }
    }

    public void SortListByRanking(ArrayList<RankingProductVO> _productList)
    {
        Collections.sort(_productList, new ProductComparator());
    }

    public class ProductComparator implements Comparator<RankingProductVO>
    {
        @Override
        public int compare(RankingProductVO productsVO1, RankingProductVO productsVO2)
        {
            return productsVO1.getViewCount() - productsVO2.getViewCount();
        }
    }
}
