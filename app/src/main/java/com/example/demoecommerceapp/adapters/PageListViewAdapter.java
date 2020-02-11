package com.example.demoecommerceapp.adapters;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import com.example.demoecommerceapp.Fragments.ProductsCoverFragment;
import com.example.demoecommerceapp.Fragments.VariantDetailFragment;
import com.example.demoecommerceapp.R;
import com.example.demoecommerceapp.model.ProductsVO;
import com.example.demoecommerceapp.model.TaxVO;

import java.util.ArrayList;

public class PageListViewAdapter extends ArrayAdapter<ProductsVO>
{
    private LayoutInflater inflater;
    private ArrayList<ProductsVO> _productsList;

    private FragmentManager _fragmentManager;

    public PageListViewAdapter(@NonNull Context context, int resource, FragmentManager supportFragmentManager, @NonNull ArrayList<ProductsVO> productsVOArrayList)
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

        final ProductsVO vo = getItem(position);
        TextView _productId = (TextView) convertView.findViewById(R.id.product_Id);
        TextView _productName = (TextView) convertView.findViewById(R.id.product_name);
        TextView _productAddDate = (TextView) convertView.findViewById(R.id.product_add_date);
        TextView _productTax = (TextView) convertView.findViewById(R.id.product_tax);

        _productId.setText(vo.getProductId());
        _productName.setText(vo.getProductName());
        _productAddDate.setText(vo.getProductAddedDate());
        TaxVO _taxVOS = vo.getTax();

        Log.d("TAX", "SIze from List" + _taxVOS.taxAmount);
        _productTax.setText(_taxVOS.taxName + " : " + _taxVOS.taxAmount);

        convertView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Log.d("Product", "position" + vo.getProductName());
                VariantDetailFragment variantDetailFragment = new VariantDetailFragment();
                Bundle bundle = new Bundle();
                bundle.putInt(VariantDetailFragment.POSITION, position);
                bundle.putString(VariantDetailFragment.PROCUCT_NAME, vo.getProductName());
                bundle.putParcelableArrayList(ProductsCoverFragment.VARIANT_LIST, vo.getVariants());
                variantDetailFragment.setArguments(bundle);
                variantDetailFragment.show(_fragmentManager, "DIALOG WINDOW");
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
