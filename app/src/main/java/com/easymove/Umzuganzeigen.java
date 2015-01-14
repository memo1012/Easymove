package com.easymove;


import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;


import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class Umzuganzeigen extends ListActivity {

    public List<Umzug> list =new ArrayList<Umzug>();
    protected Button mZurück;
    protected EditText mSelectVon;
    protected Button mSelect;
    protected String von;
    protected ListView mUmzugsliste;
    protected Button mTipp;
    protected Button mUserBearbeiten;
    protected String User;
    protected String ausgabeUser;
    protected Button mHome;
    public Umzug KlickUmzug;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_umzuganzeigen);
        mUmzugsliste= (ListView)findViewById(R.id.Umzugsliste);

        mTipp = (Button) findViewById(R.id.TippButton);
        mUserBearbeiten = (Button) findViewById(R.id.UserBearbeitenButton);
        mHome = (Button) findViewById(R.id.Homelogbutton);



        ParseQuery<Umzug> query =new ParseQuery<Umzug>("Umzug");
        //query.whereEqualTo("von", "Heidelberg");
        query.orderByDescending("createdAt");
        query.findInBackground(new FindCallback<Umzug>() {
            @Override
            public void done(List<Umzug> umzug, ParseException e) {
                if(e!=null) {
                    Toast.makeText(Umzuganzeigen.this, "Error " + e, Toast.LENGTH_SHORT).show();
                }
                else{
                    //Toast.makeText(Umzuganzeigen.this, "Daten", Toast.LENGTH_SHORT).show();
                    for (Umzug Um : list){
                        Umzug newUm =new Umzug();
                        newUm.setvon(Um.getvon());
                        newUm.setNach(Um.getNach());
                        newUm.setDatum(Um.getDatum());
                        umzug.add(newUm);
                    }}
                ArrayAdapter<Umzug> adapter=new ArrayAdapter<Umzug>(Umzuganzeigen.this,android.R.layout.simple_list_item_1,umzug);
                //setListAdapter(adapter);
                mUmzugsliste.setAdapter(adapter);


            }
        });


        final ParseQueryAdapter<Umzug> adapter = new ParseQueryAdapter<Umzug> (Umzuganzeigen.this, Umzug.class);
        adapter.setTextKey("von");
        ListView listView = (ListView) findViewById(R.id.Umzugsliste);
        mUmzugsliste.setAdapter(adapter);





        mUmzugsliste.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent,View view,int position,long id) {


                Umzug value = (Umzug)adapter.getItem(position);

                Intent intent = new Intent(Umzuganzeigen.this, UmzuganzeigenDetails.class);
                intent.putExtra("objectid",value.getObjectId());
               // Toast.makeText(Umzuganzeigen.this, value.getObjectId(), Toast.LENGTH_SHORT).show();
                startActivity(intent);


            }



        });



        mZurück = (Button) findViewById(R.id.ZurückButton);
        //Listener on Click
        mZurück.setOnClickListener(new View.OnClickListener()

                                   {
                                       @Override
                                       public void onClick(View v) {

                                           //take User to Homepage
                                           Intent TakeUserHome = new Intent(Umzuganzeigen.this, MainActivity.class);
                                           startActivity(TakeUserHome);


                                       }
                                   }

        );







        mSelectVon=(EditText) findViewById(R.id.SelectVonText);
        mSelect=(Button)findViewById(R.id.SelectUmzugeButton);

        von =mSelectVon.getText().toString().trim();
        //von ="Heidelberg";
        //Listener on Click
        mSelect.setOnClickListener(new View.OnClickListener()

                                   {
                                       @Override
                                       public void onClick(View v) {

                                           ParseQuery<Umzug> query =new ParseQuery<Umzug>("Umzug");
                                           query.whereEqualTo("von", mSelectVon.getText().toString().trim());
                                           query.findInBackground(new FindCallback<Umzug>() {
                                               @Override
                                               public void done(List<Umzug> umzug, ParseException e) {
                                                   if(e!=null) {
                                                       Toast.makeText(Umzuganzeigen.this, "Error " + e, Toast.LENGTH_SHORT).show();
                                                   }
                                                   else{

                                                       for (Umzug Um : list){
                                                           Umzug newUm =new Umzug();
                                                           newUm.setvon(Um.getvon());
                                                           newUm.setNach(Um.getNach());
                                                           newUm.setDatum(Um.getDatum());
                                                           umzug.add(newUm);
                                                       }}
                                                   ArrayAdapter<Umzug> adapter=new ArrayAdapter<Umzug>(Umzuganzeigen.this,android.R.layout.simple_list_item_1,umzug);
                                                   //setListAdapter(adapter);
                                                   mUmzugsliste.setAdapter(adapter);
                                               }
                                           });

                                       }
                                   }

        );
        //Listener on Click
        mTipp.setOnClickListener(new View.OnClickListener()

                                 {
                                     @Override
                                     public void onClick(View v) {

                                         //take User to Homepage
                                         Intent crateTippUser = new Intent(Umzuganzeigen.this, CreateTipp.class);
                                         startActivity(crateTippUser);


                                     }
                                 }

        );
        //Listener on Click
        mUserBearbeiten.setOnClickListener(new View.OnClickListener()

                                           {
                                               @Override
                                               public void onClick(View v) {

                                                   //take User to Homepage
                                                   Intent TakeUserBearbeiten = new Intent(Umzuganzeigen.this, UserBearbeiten.class);
                                                   startActivity(TakeUserBearbeiten);


                                               }
                                           }

        );

        //Listener on Click
        mHome.setOnClickListener(new View.OnClickListener()

                                 {
                                     @Override
                                     public void onClick(View v) {

                                         //take User to Homepage
                                         Intent TakeUserHome = new Intent(Umzuganzeigen.this, Anmelden.class);
                                         startActivity(TakeUserHome);

                                         ParseQuery<ParseObject> query = ParseQuery.getQuery("User");

                                         query.findInBackground(new FindCallback<ParseObject>() {
                                             @Override
                                             public void done(List<ParseObject> parseObjects, ParseException e) {
                                                 if (e == null) {


                                                     for (ParseObject delete : parseObjects) {
                                                         delete.deleteInBackground();
                                                         Toast.makeText(getApplicationContext(), "deleted", Toast.LENGTH_SHORT).show();
                                                     }
                                                 } else {
                                                     Toast.makeText(getApplicationContext(), "error in deleting", Toast.LENGTH_SHORT).show();
                                                 }
                                             }
                                         });


                                     }
                                 }

        );


    }
}
