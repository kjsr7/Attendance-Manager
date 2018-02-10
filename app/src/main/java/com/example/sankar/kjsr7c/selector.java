package com.example.sankar.kjsr7c;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.provider.ContactsContract;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompatSideChannelService;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import org.w3c.dom.Text;

import java.io.File;
import java.util.ArrayList;
import java.util.Locale;

import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

public class selector extends AppCompatActivity implements ActivityCompat.OnRequestPermissionsResultCallback{

    Button b;
    Button b2;
    Button b3;
    Button b4;
    TextView abs;
    TextView act;
    String[] as;
    String cor;
    String bc;
    boolean isalready;
    int day;
    String []months;
    int mon;
    public static final int jai=0;
    boolean[] pora;
    int result;
    int year;
    String bra;
    DatabaseHelper myDB;
    DatabaseHelper2 myDB2;
    ArrayList<Integer> mabs = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selector);
        //    myDB = new DatabaseHelper(this);
        b = (Button) findViewById(R.id.btnOrder);
        b2 = (Button) findViewById(R.id.done);
        b3 = (Button) findViewById(R.id.button2);
        b4 = (Button)findViewById(R.id.button5);

        abs = (TextView) findViewById(R.id.tvItemSelected);

        months = getYearMonthName();


        Bundle extras = getIntent().getExtras();
        // String date;

        if (extras != null) {
            //cor = extras.getString("course");
            //bra = extras.getString("branch");
            day = extras.getInt("day");

            mon = extras.getInt("mon");
            //mon =1;
            year = extras.getInt("year");
            bc=extras.getString("bc");

            act = (TextView) findViewById(R.id.textView2);
            String h = String.valueOf(day) + "/" + months[mon] + "/" + String.valueOf(year);
            act.setText(h);

        }
        String[] splitstr = bc.split("\\s+");
        bra = splitstr[0];
        cor = splitstr[1];

        myDB2 = new DatabaseHelper2(this);
        String r = myDB2.retrive(cor, bra);
      //  act.append(bc);

        if (r == " ")
            act.append(" No such course " +cor+" "+bra);
        else {
            b4.setVisibility(View.VISIBLE);
            result = Integer.parseInt(r);
            myDB = new DatabaseHelper(this, cor + "," + bra + ".db", months[mon], day, result);
            isalready = myDB.checkData("date" + String.valueOf(day));
            if (isalready == true) {
                act.append(" Attendace already marked");
                b3.setVisibility(View.VISIBLE);
                b4.setVisibility(View.VISIBLE);
            } else {
                b.setVisibility(View.VISIBLE);
                b2.setVisibility(View.VISIBLE);
              //  b4.setVisibility(View.VISIBLE);
            }
            as = new String[result];
            pora = new boolean[as.length];

            for (int i = 0; i < result; i++) {

                as[i] = Integer.toString(i + 1);
            }
            //abs.append(as[2]);


            b.setOnClickListener(new View.OnClickListener() {

                @Override

                public void onClick(View view) {

                    AlertDialog.Builder mBuilder = new AlertDialog.Builder(selector.this);

                    mBuilder.setTitle("check the absentees");


                    mBuilder.setMultiChoiceItems(as, pora, new DialogInterface.OnMultiChoiceClickListener() {
                        @Override

                        public void onClick(DialogInterface dialogInterface, int position, boolean isChecked) {
                            if (isChecked) {

                                mabs.add(position);

                            } else {

                                mabs.remove((Integer.valueOf(position)));

                            }

                        }

                    });


                    mBuilder.setCancelable(false);

                    mBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                        @Override

                        public void onClick(DialogInterface dialogInterface, int which) {

                            String item = "";
                            //String i=" ";

                            for (int i = 0; i < mabs.size(); i++) {

                                item = item + as[mabs.get(i)];


                                if (i != mabs.size() - 1) {

                                    item = item + ", ";

                                }
                            }

                            abs.setText(item);
                        }

                    });


                    mBuilder.setNegativeButton("DISMISS", new DialogInterface.OnClickListener() {

                        @Override

                        public void onClick(DialogInterface dialogInterface, int i) {

                            dialogInterface.dismiss();


                        }

                    });


                    mBuilder.setNeutralButton("CLEAR", new DialogInterface.OnClickListener() {

                        @Override

                        public void onClick(DialogInterface dialogInterface, int which) {

                            for (int i = 0; i < pora.length; i++) {

                                pora[i] = false;

                                mabs.clear();

                                abs.setText("");

                            }

                        }

                    });


                    AlertDialog mDialog = mBuilder.create();

                    mDialog.show();

                }

            });

       /* b3 = (Button) findViewById(R.id.button2);

        if(isalready == true)
        {Toast.makeText(selector.this, "attendace already marked for "+String.valueOf(day) + "/" + months[mon] + "/" + String.valueOf(year) ,Toast.LENGTH_LONG).show();;
        b3.setVisibility(View.VISIBLE); AddData();}
        else
        {b2.setVisibility(View.VISIBLE);updateData();}*/
            if (isalready == false)
                AddData();

            b3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String abse = myDB.retrive(months[mon], "date" + String.valueOf(day));
                    if(abse == " ")
                        act.append("There were no Absentees");
                    else
                    act.append("and Absentees were " + abse);
                    b.setVisibility(View.VISIBLE);
                    b3.setVisibility(View.GONE);
                    b2.setVisibility(View.VISIBLE);
                    AddData();
                }
            });
            b4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   // int jai=1;
                    if (ContextCompat.checkSelfPermission(selector.this,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            != PackageManager.PERMISSION_GRANTED) {

                        // Should we show an explanation?
                        if (ActivityCompat.shouldShowRequestPermissionRationale(selector.this,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                            // Show an explanation to the user *asynchronously* -- don't block
                            // this thread waiting for the user's response! After the user
                            // sees the explanation, try again to request the permission.

                        } else {

                            // No explanation needed, we can request the permission.

                            ActivityCompat.requestPermissions(selector.this,
                                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                    jai);

                            // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                            // app-defined int constant. The callback method gets the
                            // result of the request.
                        }
                    }
                    else
                    {
                        final Cursor cursor= myDB.getuser(months[mon]);
                        File sd = Environment.getExternalStorageDirectory();

                        String csvFile = months[mon]+" "+cor + "," + bra+".xls";

                        int c = cursor.getColumnCount();

                        File directory = new File(sd.getAbsolutePath());
                       // File mydir = new File(getCacheDir(), cor + "," + bra);

                        //create directory if not exist
                        if (!directory.isDirectory()) {
                            directory.mkdirs();
                        }
                        try {

                            //file path
                            File file = new File(directory, csvFile);
                            WorkbookSettings wbSettings = new WorkbookSettings();
                            wbSettings.setLocale(new Locale("en", "EN"));
                            WritableWorkbook workbook;
                            workbook = Workbook.createWorkbook(file, wbSettings);
                            //Excel sheet name. 0 represents first sheet
                            WritableSheet sheet = workbook.createSheet("userList", 0);

                      /* sheet.addCell(new Label(0, 0, "UserName")); // column and row
                         sheet.addCell(new Label(1, 0, "PhoneNumber"));*/
                            for(int i=0;i<c;i++)
                            {
                                sheet.addCell(new Label(i,0,cursor.getColumnName(i)));
                            }

                            if (cursor.moveToFirst()) {
                                do {
                                    // String title = cursor.getString(cursor.getColumnIndex("user_name"));
                                    // String phoneNumber = cursor.getString(cursor.getColumnIndex("phone_number"));

                                    int i = cursor.getPosition() + 1;
                          /*      sheet.addCell(new Label(0, i, title));
                                sheet.addCell(new Label(1, i, phoneNumber));*/
                                    for(int k=0;k<c;k++)
                                    {
                                        sheet.addCell(new Label(k,i,cursor.getString(k)));
                                    }
                                } while (cursor.moveToNext());
                            }
                            //closing cursor
                            cursor.close();
                            workbook.write();
                            workbook.close();
                            Toast.makeText(getApplication(), "ExcelSheet Exported in path:  "+sd.getAbsolutePath(), Toast.LENGTH_SHORT).show();
                            Toast.makeText(getApplication(), "In the Excel sheet, 1 means the roll no is absent ", Toast.LENGTH_SHORT).show();


                        } catch (Exception e) {
                            e.printStackTrace();
                        }




                    }






                }
            });





        }




    }

 /*   b2 = (Button)findViewById(R.id.done);
    /* b2.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {



//myDB.execSQL("CREATE TABLE IF NOT EXISTS "+ mon +"(RNO INTEGER PRIMARY KEY, ATTEND TEXT);");




         }
     });*/
    public void updateData()
    {
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isInserted = myDB.insertData(pora, result);

                if (isInserted == true)
                    Toast.makeText(selector.this, "Data Inserted", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(selector.this, "Data not Inserted", Toast.LENGTH_SHORT).show();


            }
        });
    }
    public void AddData() {
        b2.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {



                            boolean isInserted = myDB.insertData(pora, result);

                            if (isInserted == true)
                                Toast.makeText(selector.this, "Data Inserted", Toast.LENGTH_SHORT).show();
                            else
                                Toast.makeText(selector.this, "Data not Inserted", Toast.LENGTH_SHORT).show();

                            Intent i = new Intent(selector.this,MainActivity.class);
                            startActivity(i);
                    }
                }
        );
    }
    public String[] getYearMonthName() {
        return getResources().getStringArray(R.array.year_month_name);
        //or like
        //return cntx.getResources().getStringArray(R.array.month_names);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case jai: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    final Cursor cursor= myDB.getuser(months[mon]);
                    File sd = Environment.getExternalStorageDirectory();
                    String csvFile = months[mon]+" "+cor + "," + bra+".xls";
                    int c = cursor.getColumnCount();

                    File directory = new File(sd.getAbsolutePath());
                    //create directory if not exist
                    if (!directory.isDirectory()) {
                        directory.mkdirs();
                    }
                    try {

                        //file path
                        File file = new File(directory, csvFile);
                        WorkbookSettings wbSettings = new WorkbookSettings();
                        wbSettings.setLocale(new Locale("en", "EN"));
                        WritableWorkbook workbook;
                        workbook = Workbook.createWorkbook(file, wbSettings);
                        //Excel sheet name. 0 represents first sheet
                        WritableSheet sheet = workbook.createSheet("userList", 0);

                      /* sheet.addCell(new Label(0, 0, "UserName")); // column and row
                         sheet.addCell(new Label(1, 0, "PhoneNumber"));*/
                        for(int i=0;i<c;i++)
                        {
                            sheet.addCell(new Label(i,0,cursor.getColumnName(i)));
                        }

                        if (cursor.moveToFirst()) {
                            do {
                                // String title = cursor.getString(cursor.getColumnIndex("user_name"));
                                // String phoneNumber = cursor.getString(cursor.getColumnIndex("phone_number"));

                                int i = cursor.getPosition() + 1;
                          /*      sheet.addCell(new Label(0, i, title));
                                sheet.addCell(new Label(1, i, phoneNumber));*/
                                for(int k=0;k<c;k++)
                                {
                                    sheet.addCell(new Label(k,i,cursor.getString(k)));
                                }
                            } while (cursor.moveToNext());
                        }
                        //closing cursor
                        cursor.close();
                        workbook.write();
                        workbook.close();
                        Toast.makeText(getApplication(), "ExcelSheet Exported in path: "+sd.getAbsolutePath(), Toast.LENGTH_LONG).show();
                        Toast.makeText(getApplication(), "In the Excel sheet, 1 means the rollno is absent",Toast.LENGTH_LONG).show();


                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } else {


                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request.
        }
    }


}
