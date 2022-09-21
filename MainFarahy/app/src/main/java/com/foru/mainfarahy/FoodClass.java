package com.foru.mainfarahy;

public class FoodClass {
    String GehazID="";
    int Image;
    String FoodCatgName="";
    String SubFoodName="";
    String comment="";
    FoodClass(int Image, String FoodCatgName, String SubFoodName,String comment){
        this.Image=Image;
        this.FoodCatgName=FoodCatgName;
        this.SubFoodName=SubFoodName;
        this.comment=comment;
    }
    FoodClass(int Image, String FoodCatgName, String SubFoodName){
        this.Image=Image;
        this.FoodCatgName=FoodCatgName;
        this.SubFoodName=SubFoodName;

    }

}
