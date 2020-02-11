package com.example.demoecommerceapp.model;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.RequiresApi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RankingProductVO implements Parcelable
{
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("view_count")
    @Expose
    private int viewCount;
    @SerializedName("order_count")
    @Expose
    private int orderCount;
    @SerializedName("shares")
    @Expose
    private int shares;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getViewCount() {
        return viewCount;
    }

    public void setViewCount(int viewCount) {
        this.viewCount = viewCount;
    }

    public int getOrderCount() {
        return orderCount;
    }

    public void setOrderCount(int orderCount) {
        this.orderCount = orderCount;
    }

    public int getShares() {
        return shares;
    }

    public void setShares(int shares) {
        this.shares = shares;
    }

    @Override
    public int describeContents()
    {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i)
    {
        parcel.writeInt(this.id);
        parcel.writeInt(this.viewCount);
        parcel.writeInt(this.orderCount);
        parcel.writeInt(this.shares);
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    private RankingProductVO(Parcel in)
    {
        this.id = in.readInt();
        this.viewCount = in.readInt();
        this.orderCount = in.readInt();
        this.shares = in.readInt();
    }

    public static final Creator<RankingProductVO> CREATOR = new Creator<RankingProductVO>()
    {
        @RequiresApi(api = Build.VERSION_CODES.M)
        public RankingProductVO createFromParcel(Parcel source)
        {
            return new RankingProductVO(source);
        }

        public RankingProductVO[] newArray(int size)
        {
            return new RankingProductVO[size];
        }
    };
}
