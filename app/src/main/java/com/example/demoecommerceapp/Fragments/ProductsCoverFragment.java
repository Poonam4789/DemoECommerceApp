package com.example.demoecommerceapp.Fragments;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demoecommerceapp.R;
import com.example.demoecommerceapp.adapters.PageListViewAdapter;
import com.example.demoecommerceapp.adapters.ProductPageAdapter;
import com.example.demoecommerceapp.adapters.RankingPageListViewAdapter;
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
    public static final String MOST_VIEWED_PRODUCTS_LIST = "most_viewed_products_list";
    public static final String MOST_ORDERED_PRODUCTS_LIST = "most_ordered_products_list";
    public static final String MOST_SHARED_PRODUCTS_LIST = "most_shared_products_list";
    public static final String VARIANT_LIST = "variant_list";

    private RecyclerView _productCoversListView;
    private ArrayList<ProductsVO> _productList;
    private ArrayList<ProductsVO> _allProductsList;
    private ArrayList<RankingProductVO> _mostViewedList;
    private ArrayList<RankingProductVO> _mostOrderedList;
    private ArrayList<RankingProductVO> _mostSharedList;

    private ProductPageAdapter _productPageAdapter;
    private PageListViewAdapter _pageListViewAdapter;
    private RankingPageListViewAdapter _rankingPageListViewAdapter;
    private ProgressBar _progressBar;
    private Switch _switchRanking;
    private LinearLayout _ll_ranking;
    private AppCompatSpinner _rankingFilter;
    private ListView _rankingLiew;
    private CategoryPageFragment _parentFrag;
    private ArrayList<String> _filterList = new ArrayList<>();
    private String _filterSelected = "All";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.page_cover_layout, container, false);

        _progressBar = (ProgressBar) view.findViewById(R.id.page_progress);

        _productCoversListView = (RecyclerView) view.findViewById(R.id.product_view);
        _ll_ranking = view.findViewById(R.id.ll_ranking);
        _rankingFilter = view.findViewById(R.id.ranking_filter);
        _rankingLiew = (ListView) view.findViewById(R.id.rankingLiew);
        _switchRanking = (Switch) view.findViewById(R.id.swich_ranking);

        _productCoversListView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        _productCoversListView.setItemAnimator(null);

        _productList = getArguments().getParcelableArrayList(PRODUCT_LIST);
        _allProductsList = getArguments().getParcelableArrayList(ALL_PRODUCTS_LIST);
        _mostViewedList = getArguments().getParcelableArrayList(MOST_VIEWED_PRODUCTS_LIST);
        _mostOrderedList = getArguments().getParcelableArrayList(MOST_ORDERED_PRODUCTS_LIST);
        _mostSharedList = getArguments().getParcelableArrayList(MOST_SHARED_PRODUCTS_LIST);

        _parentFrag = ((CategoryPageFragment) ProductsCoverFragment.this.getParentFragment());
        _parentFrag.setCategortyProductContainerVisibility(true);

        Log.d(TAG, "no of Products" + _productList.size());
        Log.d(TAG, "no of All Products" + _allProductsList.size());

        _filterList.add("All");
        _filterList.add("Most Viewed");
        _filterList.add("Most Ordered");
        _filterList.add("Most Shared");

        ArrayAdapter adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, _filterList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        _rankingFilter.setAdapter(adapter);
        _rankingFilter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
            {
                _progressBar.setVisibility(View.VISIBLE);
                String filterSelected = _filterList.get(i);
                _filterSelected = filterSelected;
                Toast.makeText(getActivity(), "Filter: " + filterSelected, Toast.LENGTH_SHORT).show();
                setFilterListAsPerFilterSelected(_filterSelected);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView)
            {

            }
        });

        _switchRanking.setOnCheckedChangeListener(this);
        SortListByRanking(_allProductsList);
        _progressBar.setVisibility(View.VISIBLE);
        if (CheckSwitchEnabled())
        {
            setProductRankingWiseAdapter();
        } else
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
        _ll_ranking.setVisibility(View.GONE);
        _rankingLiew.setVisibility(View.GONE);


        _productPageAdapter = new ProductPageAdapter(getActivity().getSupportFragmentManager(), _productList);
        _productCoversListView.setAdapter(_productPageAdapter);
    }

    private void setProductRankingWiseAdapter()
    {
        _progressBar.setVisibility(View.VISIBLE);
        _parentFrag.setCategortyProductContainerVisibility(false);
        _ll_ranking.setVisibility(View.VISIBLE);
        _rankingLiew.setVisibility(View.VISIBLE);
        _productCoversListView.setVisibility(View.GONE);

        setFilterListAsPerFilterSelected("All");
    }

    private void setFilterListAsPerFilterSelected(String filterSelected)
    {
        _progressBar.setVisibility(View.GONE);
        if (filterSelected.equalsIgnoreCase("Most Viewed"))
        {
            SortListByRankingMost(_mostViewedList);
            _rankingPageListViewAdapter = new RankingPageListViewAdapter(getActivity(), R.layout.product_view_item, getActivity().getSupportFragmentManager(), _mostViewedList);
            _rankingLiew.setAdapter(_rankingPageListViewAdapter);
        } else if (filterSelected.equalsIgnoreCase("Most Ordered"))
        {
            SortListByRankingMost(_mostOrderedList);
            _rankingPageListViewAdapter = new RankingPageListViewAdapter(getContext(), R.layout.product_view_item, getActivity().getSupportFragmentManager(), _mostOrderedList);
            _rankingLiew.setAdapter(_rankingPageListViewAdapter);
        } else if (filterSelected.equalsIgnoreCase("Most Shared"))
        {
            SortListByRankingMost(_mostSharedList);
            _rankingPageListViewAdapter = new RankingPageListViewAdapter(getContext(), R.layout.product_view_item, getActivity().getSupportFragmentManager(), _mostSharedList);
            _rankingLiew.setAdapter(_rankingPageListViewAdapter);
        } else
        {
            _pageListViewAdapter = new PageListViewAdapter(getContext(), R.layout.product_view_item, getActivity().getSupportFragmentManager(), _allProductsList);
            _rankingLiew.setAdapter(_pageListViewAdapter);
        }
    }

    @Override
    public void onDestroyView()
    {
        super.onDestroyView();
        _progressBar = null;
        _rankingFilter = null;
        _productCoversListView = null;
        _productPageAdapter = null;
        _rankingPageListViewAdapter = null;
    }

    public boolean CheckSwitchEnabled()
    {
        if (_switchRanking.isChecked())
        {
            return true;
        } else
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
        } else
        {
            setProductCategoryWiseAdapter();
            _productPageAdapter.notifyDataSetChanged();
            _progressBar.setVisibility(View.GONE);
        }
    }

    public void SortListByRanking(ArrayList<ProductsVO> _productList)
    {
        Collections.sort(_productList, new ProductComparator());
    }

    public void SortListByRankingMost(ArrayList<RankingProductVO> _productList)
    {
        Collections.sort(_productList, new RankProductComparator());
    }

    public class ProductComparator implements Comparator<ProductsVO>
    {
        @Override
        public int compare(ProductsVO productsVO1, ProductsVO productsVO2)
        {
            return Integer.parseInt(productsVO1.getProductId()) - Integer.parseInt(productsVO2.getProductId());
        }
    }

    public class RankProductComparator implements Comparator<RankingProductVO>
    {
        @Override
        public int compare(RankingProductVO productsVO1, RankingProductVO productsVO2)
        {
            if (_filterSelected.equalsIgnoreCase("Most Viewed"))
            {
                return productsVO2.getViewCount() - productsVO1.getViewCount();

            } else if (_filterSelected.equalsIgnoreCase("Most Ordered"))
            {
                return productsVO2.getOrderCount() - productsVO1.getOrderCount();
            } else
            {
                return productsVO2.getShares() - productsVO1.getShares();
            }

        }
    }
}
