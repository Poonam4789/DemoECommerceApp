package com.example.demoecommerceapp;

import com.example.demoecommerceapp.model.CategoriesVO;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class CommercialProductsResponse implements Serializable
{
    @SerializedName("categories")
    private List<CategoriesVO> _categories;

    public List<CategoriesVO> getCategoriesList()
    {
        return _categories;
    }
}
