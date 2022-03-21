package com.example.mainfarahy;

public class GuestClass {
    String GuestName;
    String GuestTotal;
    String GuestCheckBox;
    String GuestID;
    GuestClass(String GuestName, String GuestTotal, String GuestCheckBox,String GuestID){
        this.GuestName=GuestName;
        this.GuestTotal=GuestTotal;
        this.GuestCheckBox=GuestCheckBox;
        this.GuestID=GuestID;
    }
    GuestClass(String GuestName, String GuestTotal, String GuestCheckBox){
        this.GuestName=GuestName;
        this.GuestTotal=GuestTotal;
        this.GuestCheckBox=GuestCheckBox;
    }
    GuestClass(String GuestName, String GuestTotal){
        this.GuestName=GuestName;
        this.GuestTotal=GuestTotal;
    }
    GuestClass(String GuestName){
        this.GuestName=GuestName;
    }

}
