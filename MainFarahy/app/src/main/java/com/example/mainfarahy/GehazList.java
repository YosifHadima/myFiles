package com.example.mainfarahy;

public class GehazList {
        String GehazName;
        String GehazComment="";
        boolean GehazCheckBox=false;

    GehazList(String GehazName, String GehazComment, boolean GehazCheckBox){
            this.GehazName=GehazName;
            this.GehazComment=GehazComment;
            this.GehazCheckBox=GehazCheckBox;
        }
    GehazList(String GehazName, String GehazComment){
        this.GehazName=GehazName;
        this.GehazComment=GehazComment;
    }
    GehazList(String GehazName){
        this.GehazName=GehazName;
    }


}
