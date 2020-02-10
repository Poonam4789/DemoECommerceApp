package com.example.demoecommerceapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RankingVO
{
    @SerializedName("ranking")
    @Expose
    private String ranking;
    @SerializedName("products")
    @Expose
    private List<RankingProductVO> products = null;

    public String getRanking() {
        return ranking;
    }

    public void setRanking(String ranking) {
        this.ranking = ranking;
    }

    public List<RankingProductVO> getProducts() {
        return products;
    }

    public void setProducts(List<RankingProductVO> products) {
        this.products = products;
    }
}
