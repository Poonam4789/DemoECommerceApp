package com.example.demoecommerceapp;

import com.example.demoecommerceapp.model.CategoriesVO;
import com.example.demoecommerceapp.model.RankingVO;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class CommercialProductsResponse implements Serializable
{
    @SerializedName("categories")
    private List<CategoriesVO> _categories;

    @SerializedName("rankings")
    @Expose
    private List<RankingVO> _rankings = null;

    public List<CategoriesVO> getCategoriesList()
    {
        return _categories;
    }

    public List<RankingVO> getRankings() {
        return _rankings;
    }

    public void setRankings(List<RankingVO> rankings) {
        this._rankings = rankings;
    }
}
