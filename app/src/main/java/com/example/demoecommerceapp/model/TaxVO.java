package com.example.demoecommerceapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class TaxVO implements Parcelable
{
    @SerializedName("name")
    public String taxName;

    @SerializedName("value")
    public String taxAmount;

    @Override
    public int describeContents()
    {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeString(this.taxName);
        dest.writeString(this.taxAmount);
    }

    public TaxVO(Parcel in)
    {
        this.taxName = in.readString();
        this.taxAmount = in.readString();
    }

    public String getTaxName()
    {
        return taxName;
    }

    public String getTaxAmount()
    {
        return taxAmount;
    }

    public static final Parcelable.Creator<TaxVO> CREATOR = new Parcelable.Creator<TaxVO>()
    {
        public TaxVO createFromParcel(Parcel source)
        {
            return new TaxVO(source);
        }

        public TaxVO[] newArray(int size)
        {
            return new TaxVO[size];
        }
    };
}
