package com.abhishekshrinath.computershop.Model;

public class Cart
{
    private String pname,pprice,pQuantity;

    public Cart() {
    }

    public Cart(String pname, String pprice, String pQuantity) {
        this.pname = pname;
        this.pprice = pprice;
        this.pQuantity = pQuantity;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getPprice() {
        return pprice;
    }

    public void setPprice(String pprice) {
        this.pprice = pprice;
    }

    public String getpQuantity() {
        return pQuantity;
    }

    public void setpQuantity(String pQuantity) {
        this.pQuantity = pQuantity;
    }
}
