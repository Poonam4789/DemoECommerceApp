package com.example.demoecommerceapp.Fragments;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;

import com.example.demoecommerceapp.R;
import com.example.demoecommerceapp.adapters.VariantPagerAdapter;
import com.example.demoecommerceapp.model.VariantsVO;

import java.util.ArrayList;

public class VariantDetailFragment extends DialogFragment
{
    private final String TAG = "VariantDetailFragment";
    public static final String PROCUCT_NAME = "product_name";
    public static final String POSITION = "position";
    private View view;
    private int _position = 0;
    private Toolbar _dialogToolbar;
    private TextView _toolbarTextView;
    private ArrayList<VariantsVO> _variantList;
    private String _dialogTitle;
    private ListView _variantLiew;
    private VariantPagerAdapter _variantPagerAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState)
    {
        super.onCreateView(inflater, container, savedInstanceState);
        view = inflater.inflate(R.layout.variant_dialog_layout, null, false);

        TextView _dialogDismissCross = (TextView) view.findViewById(R.id.dialog_dismiss_cross);
        _dialogToolbar = (Toolbar) view.findViewById(R.id.dialog_toolbar);
        _toolbarTextView = (TextView) view.findViewById(R.id.toolbar_title);

        _variantLiew = view.findViewById(R.id.variantListView);


        if (_dialogDismissCross != null)
        {
            _dialogDismissCross.setVisibility(View.VISIBLE);
            _dialogDismissCross.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    dismiss();
                }
            });
        }
        _variantList = getArguments().getParcelableArrayList(ProductsCoverFragment.VARIANT_LIST);
        _dialogTitle = getArguments().getString(PROCUCT_NAME);
        _position = getArguments().getInt(POSITION);

        setVariantData();
        Log.d(TAG, "no of Products" + _variantList.size());
        return view;
    }

    private void setVariantData()
    {
        _variantPagerAdapter = new VariantPagerAdapter(getContext(), R.layout.product_view_item, _variantList);
        _variantLiew.setAdapter(_variantPagerAdapter);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        super.onViewCreated(view, savedInstanceState);
        setToolbarTitle(_dialogTitle);
    }

    @Override
    public void onStart()
    {
        super.onStart();
        int height = getResources().getDimensionPixelSize(R.dimen.dialog_fragment_height);
        int width = getResources().getDimensionPixelSize(R.dimen.dialog_fragment_width);
        getDialog().getWindow().setLayout(width, height);
    }

    public void setToolbarTitle(String title)
    {
        if (_toolbarTextView != null)
        {
            _toolbarTextView.setText(title);
        }
    }

    @Override
    public void onDismiss(DialogInterface dialog)
    {
        super.onDismiss(dialog);
        if (getActivity() instanceof DialogInterface.OnDismissListener)
        {
            ((DialogInterface.OnDismissListener) getActivity()).onDismiss(dialog);
        }
    }
}
