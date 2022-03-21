package com.example.mainfarahy;

public class VendorList {
    String VendorName;
    String VendorComment="";
    String VendorPrice="";
    String VendorPhonenumber="";

    VendorList(String VendorName, String VendorComment, String VendorPrice,String VendorPhonenumber){
        this.VendorName=VendorName;
        this.VendorComment=VendorComment;
        this.VendorPrice=VendorPrice;
        this.VendorPhonenumber=VendorPhonenumber;
    }
    VendorList(String VendorName, String VendorComment, String VendorPrice){
        this.VendorName=VendorName;
        this.VendorComment=VendorComment;
        this.VendorPrice=VendorPrice;

    }
    VendorList(String VendorName, String VendorComment){
        this.VendorName=VendorName;
        this.VendorComment=VendorComment;


    }
    VendorList(String VendorName){
        this.VendorName=VendorName;



    }

}