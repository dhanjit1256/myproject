package com.example.DCompany;

public class Profile {
    private String User_Name;
    private String User_Address;

    public Profile() {
    }

    public Profile(String User_Name, String User_Address) {
        this.User_Name = User_Name;
        this.User_Address = User_Address;
    }

    public String getUser_Name() {
        return User_Name;
    }

    public void setUser_Name(String User_Name) {
        this.User_Name = User_Name;
    }

    public String getUser_Address() {
        return User_Address;
    }

    public void setUser_Address(String User_Address) {
        this.User_Address = User_Address;
    }

}