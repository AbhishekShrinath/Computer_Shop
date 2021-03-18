package com.abhishekshrinath.computershop.Model;

public class Users
{
    private String username,mobileNumber,Password;

    public Users()
    {

    }

    public Users(String username, String mobileNumber, String password) {
        this.username = username;
        this.mobileNumber = mobileNumber;
        this.Password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }
}
