package com.example.sankar.kjsr7c;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    DatePicker dp;
    EditText et;
    DatabaseHelper2 myDB2;
    EditText et2;
    Button b,b2,b4;
    TextView et4;
    String branch;
    //int e;
    int day,mon,year;
    EditText et3;

    Spinner spinner;
   // String cor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        dp = (DatePicker) findViewById(R.id.simpleDatePicker);
//        dp.setSpinnersShown(false);

        b = (Button) findViewById(R.id.button);


        // d = dp.toString();
        b2 = (Button) findViewById(R.id.button3);
        et4 = (TextView) findViewById(R.id.textView);
        spinner = (Spinner)findViewById(R.id.spinner);

        myDB2 = new DatabaseHelper2(this);
        Boolean f = myDB2.isTableExists();
        if (f == false)
        {
        et4.setVisibility(View.VISIBLE);
        et4.setText("There are no courses in your list");
        }
         /*   String[] a = myDB2.retbra();
            ArrayAdapter adapter = new ArrayAdapter<String>(this,R.layout.activity_listview,a);
            ListView listView = (ListView) findViewById(R.id.jai);
            listView.setAdapter(adapter);*/

            //AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this);

            //mBuilder.setTitle("kjsr7c attendace");
         //   mBuilder.set

            //et4.setText("table exists");


            //final int k = Integer.parseInt(e);
            //Integer.
        else
        {
            b.setVisibility(View.VISIBLE);
            spinner.setVisibility(View.VISIBLE);
            spinner.setOnItemSelectedListener(this);
            loadSpinnerData();
        }
            b2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(MainActivity.this, newcourse.class);
                    startActivity(i);
                }
            });

        }
        private void loadSpinnerData()
        {
            List<String> branches = myDB2.getAllLabels();
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_spinner_item, branches);
            dataAdapter
                    .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(dataAdapter);

        }
    public void onItemSelected(AdapterView<?> parent, View view, int position,
                               long id) {
        // On selecting a spinner item
        final String label = parent.getItemAtPosition(position).toString();

        // Showing selected spinner item
        Toast.makeText(parent.getContext(), "You selected: " + label,
                Toast.LENGTH_SHORT).show();

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, selector.class);
                Bundle extras = new Bundle();

                //      i.putExtra("date",dp);
                day = dp.getDayOfMonth();
                mon = dp.getMonth();
                year = dp.getYear();


                i.putExtra("day", day);
                i.putExtra("mon", mon);
                i.putExtra("year", year);
                i.putExtra("bc",label);
                i.putExtras(extras);

                startActivity(i);
            }
        });
        Toast.makeText(parent.getContext(), "You selected: " + label,
                Toast.LENGTH_SHORT).show();

/*        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(MainActivity.this, Main3Activity.class);
                Bundle extras = new Bundle();
                day = dp.getDayOfMonth();
                mon = dp.getMonth();
                year = dp.getYear();


                i.putExtra("day", day);
                i.putExtra("mon", mon);
                i.putExtra("year", year);
                i.putExtra("bc",label);
                i.putExtras(extras);
                startActivity(i);
            }
        });*/
    }
    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub

    }
    }

