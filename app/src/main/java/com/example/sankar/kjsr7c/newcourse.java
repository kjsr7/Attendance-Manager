package com.example.sankar.kjsr7c;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class newcourse extends AppCompatActivity {

    DatabaseHelper2 myDB;
    EditText cor,result,branch;
    Button bu;
    int r;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newcourse);
        cor = (EditText)findViewById(R.id.editText3);
        result = (EditText)findViewById(R.id.editText4);
        bu=(Button)findViewById(R.id.button4);
        branch = (EditText)findViewById(R.id.editText5);
        myDB = new DatabaseHelper2(this);


        try
        {
        r = Integer.parseInt(result.getText().toString());
        }
        catch(NumberFormatException nfe) {

        }
        bu.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean isInserted = myDB.addCourse(cor.getText().toString(),branch.getText().toString(),result.getText().toString());
                        if (isInserted == true)
                        { Toast.makeText(newcourse.this, "Course Inserted", Toast.LENGTH_SHORT).show();Intent i= new Intent(newcourse.this,MainActivity.class);
                            startActivity(i);}
                        else
                            Toast.makeText(newcourse.this, "Course not Inserted", Toast.LENGTH_SHORT).show();
                    }
                }
        );

      //  Intent i= new Intent(newcourse.this,MainActivity.class);
     //   startActivity(i);

    }
    public void addCor()
    {

    }
}
