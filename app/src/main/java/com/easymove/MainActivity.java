package com.easymove;

import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    protected Button mHome;
    protected String Date;
    protected Button mSave;
    protected Button mUmzug;
    protected Button mProfil;
    protected Button mTipp;
    protected String User;
    protected EditText mBeschreibung;
    protected DatePicker mDate;
    protected int day;
    protected int month;
    protected int year;
    protected Spinner mdrop;
    protected Spinner mdropnach;
    public String item1;
    public String item2;
    public List<Koordinaten> listO=new ArrayList<Koordinaten>();



    /**
     * Called when the activity is first created.
     */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        mDate =(DatePicker) findViewById(R.id.datePicker);

        mBeschreibung=(EditText) findViewById(R.id.editBeschreibung);
        mSave = (Button) findViewById(R.id.Savebutton);
        mUmzug = (Button) findViewById(R.id.UmzugeButton);
        mHome = (Button) findViewById(R.id.Homebutton);
        mProfil = (Button) findViewById(R.id.Profil);
        mTipp = (Button) findViewById(R.id.Tipp);
        mdrop = (Spinner)findViewById(R.id.spinner);
        mdropnach = (Spinner)findViewById(R.id.nachspinner);

     List<String> list = new ArrayList<String>();
        Toast.makeText(MainActivity.this, list.toString(), Toast.LENGTH_SHORT).show()     ;
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, list);


        ParseUser currentUser = ParseUser.getCurrentUser();
        User=currentUser.getUsername();

        ParseQuery<Koordinaten> queryOrte =new ParseQuery<Koordinaten>("Koordinaten");
        queryOrte.findInBackground(new FindCallback<Koordinaten>() {
            @Override
            public void done(List<Koordinaten> koordinaten, ParseException e) {
                if (e != null) {
                    Toast.makeText(MainActivity.this, "Error " + e, Toast.LENGTH_SHORT).show();
                } else {
                    List<String> list = new ArrayList<String>();
                    for (Koordinaten H : listO) {
                        Koordinaten newO = new Koordinaten();
                        newO.setOrt(H.getOrt());
                        koordinaten.add(newO);
                    }
                   /* mVon.setText(koordinaten.toString());

                    for(Koordinaten str : koordinaten) {
                      list.add(str.getOrt().toString());
                    }
                    mVon.setText(list.toString());*/
                }
            }
        });
        list.add("Heidelberg");
        list.add("Karlsruhe");
       list.add("Freiburg");
        list.add("Frankfurt");
        list.add("München");
        list.add("Berlin");




        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mdrop.setAdapter(dataAdapter);

        mdrop.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parent, View seletedItem, int pos, long id) {
                item1 = (String) parent.getItemAtPosition(pos);
            }

            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mdropnach.setAdapter(dataAdapter);

        mdropnach.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parent, View seletedItem, int pos, long id) {
                item2 = (String) parent.getItemAtPosition(pos);
            }

            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        //Listener on Click
        mSave.setOnClickListener(new View.OnClickListener() {

           //String anyvariable=String.valueOf(mdrop.getSelectedItem());


            @Override
            public void onClick(View v) {
                day=mDate.getDayOfMonth();
                month =mDate.getMonth()+1;
                year=mDate.getYear();
                Date = day+"."+month+"."+year;
                        Umzug obj = new Umzug();
                        //obj.setvon(mVon.getText().toString().trim());
                        obj.setvon(item1);
                       // obj.setNach(mNach.getText().toString().trim());
                         obj.setNach(item2);
                        obj.setDatum(Date);
                        obj.setCreater(User);
                        obj.setBeschreibung(mBeschreibung.getText().toString().trim());
                        obj.saveInBackground();


                        mBeschreibung.setText("");






                        //take User to Homepage
                        Intent ShowUmzug = new Intent(MainActivity.this, Umzuganzeigen.class);
                        startActivity(ShowUmzug);



            }
        });




        //Listener on Click
        mHome.setOnClickListener(new View.OnClickListener()

                                 {
                                     @Override
                                     public void onClick(View v) {

                                         //take User to Homepage
                                         Intent TakeUserHome = new Intent(MainActivity.this, Anmelden.class);
                                         startActivity(TakeUserHome);


                                     }
                                 }

        );


        //Listener on Click
        mUmzug.setOnClickListener(new View.OnClickListener()

                                  {
                                      @Override
                                      public void onClick(View v) {

                                          //take User to Homepage
                                          Intent ShowUmzug = new Intent(MainActivity.this, Umzuganzeigen.class);
                                          startActivity(ShowUmzug);

                                      }
                                  }

        );
        //Listener on Click
        mProfil.setOnClickListener(new View.OnClickListener()

                                  {
                                      @Override
                                      public void onClick(View v) {

                                          //take User to Homepage
                                          Intent ShowUmzug = new Intent(MainActivity.this, UserBearbeiten.class);
                                          startActivity(ShowUmzug);

                                      }
                                  }

        );
        //Listener on Click
        mTipp.setOnClickListener(new View.OnClickListener()

                                  {
                                      @Override
                                      public void onClick(View v) {

                                          //take User to Homepage
                                          Intent ShowUmzug = new Intent(MainActivity.this, CreateTipp.class);
                                          startActivity(ShowUmzug);

                                      }
                                  }

        );


        ParseAnalytics.trackAppOpened(

                getIntent()

        );
    }
}
