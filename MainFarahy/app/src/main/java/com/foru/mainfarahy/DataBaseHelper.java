package com.foru.mainfarahy;

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

public class DataBaseHelper extends SQLiteOpenHelper {

    public  static final String DATABASE_NAME="Farahy.db";
    public  static final String TABLE_NAME="Farahy_table";

    public  static final String COL_1="ID";
    public  static final String COL_2="NAME_FEMALE";//ID will be thiere
    public  static final String COL_3="NAME_MALE";
    public  static final String COL_4="MARGE_DATE";
    public  static final String COL_5="MAIN_TOPIC";
    public  static final String COL_6="SUP_TOPIC";
    public  static final String COL_7="COMMENT_TOPIC";
    public  static final String COL_8="CHECKBOX";


    public DataBaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public DataBaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version, @Nullable DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    public DataBaseHelper(@Nullable Context context, @Nullable String name, int version, @NonNull SQLiteDatabase.OpenParams openParams) {
        super(context, name, version, openParams);
    }

    public DataBaseHelper(Context context){
        super(context,DATABASE_NAME,null,1);

    }
    @Override
    public void onCreate(SQLiteDatabase db) {
db.execSQL("CREATE TABLE "+TABLE_NAME+" (ID INTEGER PRIMARY KEY AUTOINCREMENT ,NAME_FEMALE TEXT ,NAME_MALE TEXT ,MARGE_DATE TEXT ,MAIN_TOPIC TEXT ,SUP_TOPIC TEXT ,COMMENT_TOPIC TEXT ,CHECKBOX TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
    }
    public boolean deleteData(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        int result=  db.delete(TABLE_NAME,"ID=?",new String[]{id});
        // db.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME = '" + TABLE_NAME + "'");

        //db.rawQuery("DBCC CHECKIDENT ("+TABLE_NAME+", RESEED, 0)");
        db.close();
        //DBCC CHECKIDENT (mytable, RESEED, 0)
        if(result>0){
            return true;
        }else {
            return false;
        }
    }
    public boolean insertData(String Femalename,String MaleName,String MargeDate,String MainTopic,String SupTopic,String CommentTopic,String checkbox){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(COL_2,Femalename);
        contentValues.put(COL_3,MaleName);
        contentValues.put(COL_4,MargeDate);
        contentValues.put(COL_5,MainTopic);
        contentValues.put(COL_6,SupTopic);
        contentValues.put(COL_7,CommentTopic);
        contentValues.put(COL_8,checkbox);
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

    public boolean updateData(String id,String Femalename,String MaleName,String MargeDate,String MainTopic,String SupTopic,String CommentTopic,String checkBox){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(COL_2,Femalename);
        contentValues.put(COL_3,MaleName);
        contentValues.put(COL_4,MargeDate);
        contentValues.put(COL_5,MainTopic);
        contentValues.put(COL_6,SupTopic);
        contentValues.put(COL_7,CommentTopic);
        contentValues.put(COL_8,checkBox);
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
