package com.example.demoecommerceapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class CategoriesVO implements Parcelable
{
    @SerializedName("id")
    private String categoryId;

    @SerializedName("name")
    private String categoryName;

    @SerializedName("products")
    private ArrayList<ProductsVO> products;


    @Override
    public int describeContents()
    {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeString(this.categoryId);
        dest.writeString(this.categoryName);
        dest.writeTypedList(this.products);
    }

    public String getCategoryId()
    {
        return categoryId;
    }

    public String getCategoryName()
    {
        return categoryName;
    }

    public ArrayList<ProductsVO> getProducts()
    {
        return products;
    }

    private CategoriesVO(Parcel in)
    {
        this.categoryId = in.readString();
        this.categoryName = in.readString();

        products = new ArrayList<>();
        in.readTypedList(products, ProductsVO.CREATOR);
    }

    public static final Creator<CategoriesVO> CREATOR = new Creator<CategoriesVO>()
    {
        public CategoriesVO createFromParcel(Parcel source)
        {
            return new CategoriesVO(source);
        }

        public CategoriesVO[] newArray(int size)
        {
            return new CategoriesVO[size];
        }
    };
}
