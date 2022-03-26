package com.example.mainfarahy;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

public class DataBaseHelperDate extends SQLiteOpenHelper {

    public  static final String DATABASE_NAME="Date.db";
    public  static final String TABLE_NAME="Farahy_table";

    public  static final String COL_1="ID";
    public  static final String COL_2="NAME_FEMALE";
    public  static final String COL_3="NAME_MALE";
    public  static final String COL_4="MARGE_DATE_YEAR";
    public  static final String COL_5="MARGE_DATE_MONTH";
    public  static final String COL_6="MARGE_DATE_DAY";



    public DataBaseHelperDate(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public DataBaseHelperDate(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version, @Nullable DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    public DataBaseHelperDate(@Nullable Context context, @Nullable String name, int version, @NonNull SQLiteDatabase.OpenParams openParams) {
        super(context, name, version, openParams);
    }

    public DataBaseHelperDate(Context context){
        super(context,DATABASE_NAME,null,1);

    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE "+TABLE_NAME+" (ID INTEGER PRIMARY KEY AUTOINCREMENT ,NAME_FEMALE TEXT ,NAME_MALE TEXT ,MARGE_DATE_YEAR TEXT ,MARGE_DATE_MONTH TEXT ,MARGE_DATE_DAY TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
    }

    public boolean insertData(String Femalename,String MaleName,String MARGE_DATE_YEAR,String MARGE_DATE_MONTH,String MARGE_DATE_DAY){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(COL_2,Femalename);
        contentValues.put(COL_3,MaleName);
        contentValues.put(COL_4,MARGE_DATE_YEAR);
        contentValues.put(COL_5,MARGE_DATE_MONTH);
        contentValues.put(COL_6,MARGE_DATE_DAY);

        long result = db.insert(TABLE_NAME,null,contentValues);
        db.close();
        //To check Data is inserted or not
        if(result==-1){
            return false;
        }else {
            return true;
        }
    }
    public Cursor getALData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res=db.rawQuery("Select * from "+TABLE_NAME,null);
        return res;
    }

    public boolean updateData(String id,String Femalename,String MaleName,String MARGE_DATE_YEAR,String MARGE_DATE_MONTH,String MARGE_DATE_DAY){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(COL_2,Femalename);
        contentValues.put(COL_3,MaleName);
        contentValues.put(COL_4,MARGE_DATE_YEAR);
        contentValues.put(COL_5,MARGE_DATE_MONTH);
        contentValues.put(COL_6,MARGE_DATE_DAY);

        int result = db.update(TABLE_NAME,contentValues,"ID =?",new String[]{id});
        db.close();
        //To check Data is inserted or not
        if(result>0){
            return false;
        }else {
            return true;
        }
    }
}
