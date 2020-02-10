package com.example.demoecommerceapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.demoecommerceapp.R;
import com.example.demoecommerceapp.model.VariantsVO;

import java.util.ArrayList;

public class VariantPagerAdapter extends ArrayAdapter<VariantsVO>
{
    private ArrayList<VariantsVO> _variantsVOSList;
    private LayoutInflater inflater;
    private TextView _variant_id, _variant_color, _variant_size, _variant_price;

    public VariantPagerAdapter(@NonNull Context context, int resource, ArrayList<VariantsVO> variantsVOsArrayList)
    {
        super(context, resource,variantsVOsArrayList);
        _variantsVOSList = variantsVOsArrayList;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public View getView(final int position, View convertView, ViewGroup parent)
    {
        if (convertView == null)
        {
            convertView = inflater.inflate(R.layout.product_view_item, parent, false);
        }

        final VariantsVO vo = getItem(position);
        _variant_id = (TextView) convertView.findViewById(R.id.product_Id);
        _variant_color = (TextView) convertView.findViewById(R.id.product_name);
        _variant_size = (TextView) convertView.findViewById(R.id.product_add_date);
        _variant_price = (TextView) convertView.findViewById(R.id.product_tax);

        _variant_id.setText(vo.getVariantId());
        _variant_color.setText(vo.getVariantColor());
        _variant_size.setText(vo.getVariantSize());
        _variant_price.setText(vo.getVariantPrice());

        return convertView;
    }

    @Override
    public int getCount()
    {
        return _variantsVOSList.size();
    }
}
