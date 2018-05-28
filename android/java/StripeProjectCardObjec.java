package com.drassapps.archdeal;

public class StripeProjectCardObjec {

    public String brand;
    public Long address_zip ,exp_month, exp_year, last4;

    public StripeProjectCardObjec(){ }

    public StripeProjectCardObjec(Long address_zip, String brand, Long exp_month,
                                  Long exp_year, Long last4) {
        this.address_zip = address_zip;
        this.brand = brand;
        this.exp_month = exp_month;
        this.exp_year = exp_year;
        this.last4 = last4;
    }
}
