package com.example.demoecommerceapp.adapters;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.demoecommerceapp.Fragments.ProductsCoverFragment;
import com.example.demoecommerceapp.Fragments.VariantDetailFragment;
import com.example.demoecommerceapp.R;
import com.example.demoecommerceapp.model.ProductsVO;
import com.example.demoecommerceapp.model.TaxVO;

import java.util.ArrayList;

public class ProductPageAdapter extends RecyclerView.Adapter<ProductPageAdapter.ViewHolder>
{
    private ArrayList<ProductsVO> _productsList;
    private TaxVO _taxVOS;
    FragmentManager _fragmentManager;

    public ProductPageAdapter(FragmentManager supportFragmentManager, ArrayList<ProductsVO> list)
    {
        _productsList = list;
        _fragmentManager = supportFragmentManager;
    }

    @Override
    public ProductPageAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i)
    {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.product_view_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProductPageAdapter.ViewHolder holder, int position)
    {
        holder._productId.setText(_productsList.get(position).getProductId());
        holder._productName.setText(_productsList.get(position).getProductName());
        holder._productAddDate.setText(_productsList.get(position).getProductAddedDate());
        _taxVOS = _productsList.get(position).getTax();
        Log.d("TAX", "SIze" + _taxVOS.taxAmount);
        holder._productTax.setText(_taxVOS.taxName + " : " + _taxVOS.taxAmount);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        private TextView _productId, _productName, _productAddDate, _productTax;

        public ViewHolder(View view)
        {
            super(view);
            view.setOnClickListener(this);
            _productId = (TextView) view.findViewById(R.id.product_Id);
            _productName = (TextView) view.findViewById(R.id.product_name);
            _productAddDate = (TextView) view.findViewById(R.id.product_add_date);
            _productTax = (TextView) view.findViewById(R.id.product_tax);

        }

        @Override
        public void onClick(View v)
        {
            Log.d("Product", "position" + _productsList.get(getPosition()).getProductName());
            VariantDetailFragment variantDetailFragment = new VariantDetailFragment();
            Bundle bundle = new Bundle();
            bundle.putInt(VariantDetailFragment.POSITION, getPosition());
            bundle.putString(VariantDetailFragment.PROCUCT_NAME, _productsList.get(getPosition()).getProductName());
            bundle.putParcelableArrayList(ProductsCoverFragment.VARIANT_LIST, _productsList.get(getPosition()).getVariants());
            variantDetailFragment.setArguments(bundle);
            variantDetailFragment.show(_fragmentManager, "DIALOG WINDOW");
        }
    }

    @Override
    public int getItemCount()
    {
        return _productsList.size();
    }

}
