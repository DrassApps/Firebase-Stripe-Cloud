package com.drassapps.archdeal;

import com.google.gson.annotations.SerializedName;

import retrofit2.http.FormUrlEncoded;

public class StripeObjectSend {

    @SerializedName("amount")
    private int amount;

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
