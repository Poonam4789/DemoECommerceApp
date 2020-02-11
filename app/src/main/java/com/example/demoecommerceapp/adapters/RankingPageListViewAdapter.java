package com.example.demoecommerceapp.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;

import com.example.demoecommerceapp.R;
import com.example.demoecommerceapp.model.RankingProductVO;

import java.util.ArrayList;

public class RankingPageListViewAdapter extends ArrayAdapter<RankingProductVO>
{
    private LayoutInflater inflater;
    private ArrayList<RankingProductVO> _productsList;

    private FragmentManager _fragmentManager;

    public RankingPageListViewAdapter(@NonNull Context context, int resource, FragmentManager supportFragmentManager, @NonNull ArrayList<RankingProductVO> productsVOArrayList)
    {
        super(context, resource, productsVOArrayList);
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        _productsList = productsVOArrayList;
        _fragmentManager = supportFragmentManager;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent)
    {
        if (convertView == null)
        {
            convertView = inflater.inflate(R.layout.product_view_item, parent, false);
        }

        final RankingProductVO vo = getItem(position);
        TextView _productId = convertView.findViewById(R.id.product_Id);
        TextView _productName = convertView.findViewById(R.id.product_name);
        TextView _productAddDate = convertView.findViewById(R.id.product_add_date);
        TextView _productTax = convertView.findViewById(R.id.product_tax);

        _productId.setText("Product ID: " + vo.getId());
        if(vo.getViewCount()>0)
        {
            _productName.setText("View Count : " + vo.getViewCount());
            _productAddDate.setVisibility(View.GONE);
            _productTax.setVisibility(View.GONE);
        }
        if(vo.getOrderCount()>0)
        {
            _productAddDate.setText("Ordered Count : " + vo.getOrderCount());
            _productName.setVisibility(View.GONE);
            _productTax.setVisibility(View.GONE);

        }
        if(vo.getShares()>0)
        {
            _productTax.setText("Share Count : " + vo.getShares());
            _productAddDate.setVisibility(View.GONE);
            _productName.setVisibility(View.GONE);
        }

        convertView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Log.d("Product", "position" + vo.getId());
//                VariantDetailFragment variantDetailFragment = new VariantDetailFragment();
//                Bundle bundle = new Bundle();
//                bundle.putInt(VariantDetailFragment.POSITION, position);
//                bundle.putString(VariantDetailFragment.PROCUCT_NAME, vo.getProductName());
//                bundle.putParcelableArrayList(ProductsCoverFragment.VARIANT_LIST, vo.getVariants());
//                variantDetailFragment.setArguments(bundle);
//                variantDetailFragment.show(_fragmentManager, "DIALOG WINDOW");
            }
        });
        return convertView;
    }

    @Override
    public int getCount()
    {
        return _productsList.size();
    }
}
