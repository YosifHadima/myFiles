package com.foru.mainfarahy;

public class VendorList {
    public String VendorName;
    public String VendorComment="";
    public String VendorPrice="";
    public  String VendorPhonenumber="";
    public String MainTopicName;
public String myID;
    public VendorList(String VendorName, String VendorComment, String VendorPrice, String VendorPhonenumber, String MainTopicName, String myID){
        this.VendorName=VendorName;
        this.VendorComment=VendorComment;
        this.VendorPrice=VendorPrice;
        this.VendorPhonenumber=VendorPhonenumber;
        this.MainTopicName=MainTopicName;
        this.myID=myID;
    }
    public VendorList()
    {

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