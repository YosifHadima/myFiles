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

public class DataBaseGehazHelper extends SQLiteOpenHelper {

    public  static final String DATABASE_NAME="Gehaz.db";
    public  static final String TABLE_NAME="Farahy_table";

    public  static final String COL_1="ID";
    public  static final String COL_2="NAME";
    public  static final String COL_3="TOTAL";
    public  static final String COL_4="CHECK_BOX";
    public  static final String COL_5="MY_ID";



    public DataBaseGehazHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public DataBaseGehazHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version, @Nullable DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    public DataBaseGehazHelper(@Nullable Context context, @Nullable String name, int version, @NonNull SQLiteDatabase.OpenParams openParams) {
        super(context, name, version, openParams);
    }

    public DataBaseGehazHelper(Context context){
        super(context,DATABASE_NAME,null,1);

    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE "+TABLE_NAME+" (ID INTEGER PRIMARY KEY AUTOINCREMENT ,NAME TEXT ,TOTAL TEXT ,CHECK_BOX TEXT ,MY_ID TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
    }

    public boolean insertData(String name,String TOTAL , String CHECK_BOX,String MY_ID){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(COL_2,name);
        contentValues.put(COL_3,TOTAL);
        contentValues.put(COL_4,CHECK_BOX);
        contentValues.put(COL_5,MY_ID);

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

    public boolean updateData(String id,String name,String TOTAL ,String CHECK_BOX, String MY_ID){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(COL_2,name);
        contentValues.put(COL_3,TOTAL);
        contentValues.put(COL_4,CHECK_BOX);
        contentValues.put(COL_5,MY_ID);
        int result = db.update(TABLE_NAME,contentValues,"ID =?",new String[]{id});
        db.close();
        //To check Data is inserted or not
        if(result>0){
            return true;
        }else {
            return false;
        }
    }

    public boolean deleteData(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        int result=  db.delete(TABLE_NAME,"MY_ID=?",new String[]{id});
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
}
