package com.example.sankar.kjsr7c;

import android.content.ContentValues;
import android.content.Context;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.ContactsContract;


/**
 * Created by SANKAR on 12/31/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {


    private String TABLE_NAME;
    private String column;
    private int result;



    public DatabaseHelper(Context context, String db_name, String mon, int col, int res) {

        super( context,db_name, null, 1);
        TABLE_NAME = mon;
        column = "date" + String.valueOf(col);
        result = res;
        SQLiteDatabase db = this.getWritableDatabase();
        onCreate(db);

    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS "+ TABLE_NAME +" ( RNO INTEGER PRIMARY KEY );");
        ContentValues contentValues = new ContentValues();

   for(int i=0;i<result;i++) {
            contentValues.put("RNO", i+1);
            long r = db.insert(TABLE_NAME,null ,contentValues);


        }



    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {


db.execSQL("DROP TABLE IF EXISTS TABLE_NAME " + TABLE_NAME);
onCreate(db);

    }
    public boolean updateData(boolean pora[], int result) {
        SQLiteDatabase db = this.getWritableDatabase();




        ContentValues contentValue = new ContentValues();
        long res;
        for(int i=0;i<result;i++)
        {
            // contentValue.put(column, pora[i]);
            //res = db.insert(TABLE_NAME,null ,contentValue);
            String strFilter = "RNO=" + String.valueOf(i+1);
            ContentValues args = new ContentValues();
            args.put(column, pora[i]);
            res=db.update(TABLE_NAME, args, strFilter, null);
            //db.update()
            if(res == -1)
                return false;
        }

        return true;
    }
    public String retrive(String tname,String cname)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select RNO,"+cname+" from "+tname, null );
        res.moveToFirst();
        String s="";
        int i=1;
        while(res.isAfterLast() == false){
            //array_list.add(res.getString(res.getColumnIndex(CONTACTS_COLUMN_NAME)));
            if(res.getInt(1) == 1)
            {
                i=res.getInt(0);
                        s=s+", "+String.valueOf(i);
            }


            res.moveToNext();
        }

return s;


    }

    public boolean insertData(boolean pora[], int result) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        boolean isalready = checkData(column);
        if(isalready == false)
        db.execSQL("ALTER TABLE "+TABLE_NAME+" ADD COLUMN "+column+" BOOLEAN DEFAULT FALSE");
        ContentValues contentValue = new ContentValues();
        long res;
        for(int i=0;i<result;i++)
        {
            String strFilter = "RNO=" + String.valueOf(i+1);
            ContentValues args = new ContentValues();
            args.put(column, pora[i]);
           res=db.update(TABLE_NAME, args, strFilter, null);
             if(res == -1)
                return false;
        }

        return true;
    }
    public boolean checkData(String col)
    {

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor resultSet = db.rawQuery("Select * from "+ TABLE_NAME,null);
        int m =resultSet.getColumnIndex(col);
        if(m==-1)
            return false;
        else
            return true;
    }
    public Cursor getuser(String t) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + t + " ",
                null);
        return res;
    }





}
