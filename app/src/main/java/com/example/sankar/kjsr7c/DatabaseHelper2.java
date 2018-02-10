package com.example.sankar.kjsr7c;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import static com.example.sankar.kjsr7c.dbhelper.TABLE_NAME;

/**
 * Created by SANKAR on 1/4/2018.
 */

class DatabaseHelper2 extends SQLiteOpenHelper {

    String Tname = "course";

    public DatabaseHelper2(Context context) {
        super(context, "courses.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + Tname + " ( NAME STRING, BRANCH STRING, NOS STRING );");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean addCourse(String n, String b, String nsoc) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("NAME", n);
        contentValues.put("BRANCH", b);
        contentValues.put("NOS", nsoc);
        long r = db.insert(Tname, null, contentValues);
        if (r == -1)
            return false;
        else
            return true;
    }
    public Boolean checkCourse(String b,String c)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res;
        res =  db.rawQuery("SELECT NOS FROM " + Tname+" WHERE NAME = ? AND BRANCH = ?", new String[] {c, b});


        if(res == null)
            return false;
        else
            return true;
    }

    public String retrive(String cname,String b) {

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select NOS from "+Tname+ " where NAME =? and BRANCH =?", new String[]{cname,b});
        //myDataBase.rawQuery("select * from Bank where english =? ", new String [] {currentWord.english});

         res.moveToFirst();
        if(res.getCount() == 0)
            return " ";
        else

       // Log.d("NOS",res.getString(0));
               return res.getString(0);
          }
          public String[] retbra()
          {
              SQLiteDatabase db = this.getReadableDatabase();
              Cursor res = db.rawQuery("SELECT BRANCH, NAME FROM "+Tname, null);
              int r = 0;

              res.moveToFirst();
              while(!res.isLast())
              {
                  ++r;
              }
              ++r;

              /*if(res.getCount()==0)
              {
                  String []b = new String[1];
                  b[0]=" ";

                  return b;
              }*/
              String []a = new String[r];
              int m= -1;
              res.moveToFirst();
              while(!res.isLast())
              {
                  a[++m] = res.getString(0) +" "+ res.getString(1);
              }
              a[++m] = res.getString(0) +" "+ res.getString(1);
              res.close();
              return a;
                  }
          public boolean isTableExists()
          {
              SQLiteDatabase db = this.getReadableDatabase();
              Cursor res = db.rawQuery("select * from "+Tname,null);
              res.moveToFirst();
              if(res.getCount() == 0)
                  return false;
              else
                  return true;
          }
    public List<String> getAllLabels(){
        List<String> labels = new ArrayList<String>();

        // Select All Query
        String selectQuery = "SELECT  BRANCH,NAME FROM " + Tname;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                labels.add(cursor.getString(0)+" "+cursor.getString(1));
            } while (cursor.moveToNext());
        }

        // closing connection
        //cursor.close();
       // db.close();

        // returning lables
        return labels;
    }


}


