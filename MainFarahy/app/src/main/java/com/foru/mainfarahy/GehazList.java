package com.foru.mainfarahy;

public class GehazList {
        String GehazName;
        String GehazComment="";
        boolean GehazCheckBox=false;
    String GehazID="";
    GehazList(String GehazName, String GehazComment, boolean GehazCheckBox){
            this.GehazName=GehazName;
            this.GehazComment=GehazComment;
            this.GehazCheckBox=GehazCheckBox;
        }
    GehazList(String GehazName, String GehazComment, boolean GehazCheckBox, String GehazID){
        this.GehazName=GehazName;
        this.GehazComment=GehazComment;
        this.GehazCheckBox=GehazCheckBox;
        this.GehazID=GehazID;
    }
    //GehazList(String GehazName, String GehazComment){
      //  this.GehazName=GehazName;
        //this.GehazComment=GehazComment;
    //}
    GehazList(String GehazName , String GehazID){
        this.GehazName=GehazName;
        this.GehazID=GehazID;
    }


}
