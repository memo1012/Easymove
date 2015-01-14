package com.easymove;


import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;


import android.view.MenuItem;
import android.view.View;
import android.graphics.BitmapFactory;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.GetDataCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;


public class UserBearbeiten extends ListActivity {

    public List<UserDaten> list = new ArrayList<UserDaten>();

    protected String User;
    protected Button mHome;
    protected Button mProfil;
    protected Button mTipp;
    protected Button mUmzug;
    protected Button mEPDat;
    protected Button mSPDat;

    protected String VorName;
    protected String NachName;
    protected String UserName;
    protected String PLZ;
    protected String Strasse;
    protected String E_Mail;
    protected String Ort;

    protected TextView mName;
    protected TextView mVorName;
    protected TextView mUserName;
    protected TextView mPLZ;
    protected TextView mStrasse;
    protected TextView mE_Mail;

    protected EditText mVorname;
    protected EditText mNachname;
    protected TextView mUsername;
    protected EditText mEPLZ;
    protected EditText mStrass;
    protected EditText mEmail;

    protected ImageView mIV;
    protected Bitmap bitmap;
    protected String  S;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userbearbeiten);

        mHome = (Button) findViewById(R.id.Homebutton);
        mProfil = (Button) findViewById(R.id.Profil);
        mUmzug = (Button) findViewById(R.id.Umzüg);
        mTipp = (Button) findViewById(R.id.Tipp);
        mEPDat = (Button) findViewById(R.id.EPdat);
        mSPDat = (Button) findViewById(R.id.SPdat);


        mName = (TextView) findViewById(R.id.NachNamenn);
        mVorName = (TextView) findViewById(R.id.Vornamenn);
        mUserName = (TextView) findViewById(R.id.UserNamenn);
        mPLZ = (TextView) findViewById(R.id.PLZN);
        mStrasse = (TextView) findViewById(R.id.Strassen);
        mE_Mail = (TextView) findViewById(R.id.E_Mailn);
        mUsername = (TextView) findViewById(R.id.editTextUsername);

        mVorname = (EditText) findViewById(R.id.editTextVorname);
        mNachname = (EditText) findViewById(R.id.editTextNachname);
        mEPLZ = (EditText) findViewById(R.id.editTextPLZ);
        mStrass = (EditText) findViewById(R.id.editTextStrasse);
        mEmail = (EditText) findViewById(R.id.editTextEmail);
        mIV = (ImageView) findViewById(R.id.Profilbild);


        //Get current User
        ParseUser currentUser = ParseUser.getCurrentUser();

        ParseQuery<UserDaten> query = ParseQuery.getQuery("UserDaten");
        query.whereEqualTo("Username", currentUser.getUsername());

        query.getFirstInBackground(new GetCallback<UserDaten>() {
            public void done(UserDaten Ud, ParseException e) {
                if (e == null) {

                    Toast.makeText(UserBearbeiten.this, "Daten von P", Toast.LENGTH_SHORT).show();
                    VorName = Ud.getString("Vorname");
                    NachName = Ud.getString("Name");
                    UserName = Ud.getString("Username");
                    PLZ = Ud.getString("PLZ");
                    Strasse = Ud.getString("Strasse");
                    E_Mail = Ud.getString("E_Mail");



                    ParseFile file = (ParseFile) Ud.get("ProfilBild");
                    file.getDataInBackground(new GetDataCallback() {

                        public void done(byte[] data, ParseException e) {
                            if (e == null) {
                                //.makeText(UserBearbeiten.this, "Daten ", Toast.LENGTH_LONG).show();
                                 S = new String(data);
                               // mName.setText("String "+S);
                                Log.d("test",S);
                                bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                                //use this bitmap as you want
                                 mIV.setImageBitmap(bitmap);


                            } else {
                                // something went wrong
                                Toast.makeText(UserBearbeiten.this, "Daten fail ", Toast.LENGTH_LONG).show();

                            }
                        }
                    });
                    addData();


                } else {
                    e.printStackTrace();
                    Toast.makeText(UserBearbeiten.this, "Fail: "+e, Toast.LENGTH_SHORT).show();
                }
            }

            public void addData() {
                mVorName.setText("Vorname: " + VorName);
                mName.setText("Nachname: " + NachName);
                mUserName.setText("Username: " + UserName);
                mPLZ.setText("PLZ: " + PLZ);
                mStrasse.setText("Strasse: " + Strasse);
                mE_Mail.setText("E-Mail: " + E_Mail);
            }
        });


        //List.ner on Click
        mHome.setOnClickListener(new View.OnClickListener()

                                 {
                                     @Override
                                     public void onClick(View v) {

                                         //take User to Homepage
                                         Intent TakeUserHome = new Intent(UserBearbeiten.this, Anmelden.class);
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
                                          Intent ShowUmzug = new Intent(UserBearbeiten.this, Umzuganzeigen.class);
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
                                           Intent ShowUmzug = new Intent(UserBearbeiten.this, UserBearbeiten.class);
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
                                         Intent ShowUmzug = new Intent(UserBearbeiten.this, CreateTipp.class);
                                         startActivity(ShowUmzug);

                                     }
                                 }

        );
        //Listener on Click
        mEPDat.setOnClickListener(new View.OnClickListener()

                                  {
                                      @Override
                                      public void onClick(View v) {


                                          mSPDat.setVisibility(View.VISIBLE);
                                          mVorname.setVisibility(View.VISIBLE);
                                          mNachname.setVisibility(View.VISIBLE);
                                          mUsername.setVisibility(View.VISIBLE);
                                          mEPLZ.setVisibility(View.VISIBLE);
                                          mStrass.setVisibility(View.VISIBLE);
                                          mEmail.setVisibility(View.VISIBLE);


                                          mEPDat.setVisibility(View.INVISIBLE);

                                          mVorName.setText("Vorname: ");
                                          mName.setText("Nachname: ");
                                          mUserName.setText("Username: ");
                                          mPLZ.setText("PLZ: ");
                                          mStrasse.setText("Strasse: ");
                                          mE_Mail.setText("E-Mail: ");


                                          mVorname.setText(VorName);
                                          mNachname.setText(NachName);
                                          mUsername.setText(UserName);
                                          mEPLZ.setText(PLZ);
                                          mStrass.setText(Strasse);
                                          mEmail.setText(E_Mail);

                                      }
                                  }

        );
        //Listener on Click
        mSPDat.setOnClickListener(new View.OnClickListener()

                                  {
                                      @Override
                                      public void onClick(View v) {

                                          mEPDat.setVisibility(View.VISIBLE);
                                          mSPDat.setVisibility(View.INVISIBLE);
                                          mVorname.setVisibility(View.INVISIBLE);
                                          mNachname.setVisibility(View.INVISIBLE);
                                          mUsername.setVisibility(View.INVISIBLE);
                                          mEPLZ.setVisibility(View.INVISIBLE);
                                          mStrass.setVisibility(View.INVISIBLE);
                                          mEmail.setVisibility(View.INVISIBLE);

                                          UserDaten UDat = new UserDaten();
                                          UDat.setVorname(VorName);
                                          UDat.setName(NachName);
                                          UDat.setUsername(UserName);
                                          UDat.setPLZ(PLZ);
                                          UDat.setStrasse(Strasse);
                                          UDat.setE_Mail(E_Mail);


                                          Toast.makeText(getApplicationContext(), UDat.toString(), Toast.LENGTH_SHORT).show();
                                          UDat.setVorname( mVorname.getText().toString().trim());
                                          UDat.put("Name", mNachname.getText().toString().trim());
                                          UDat.put("Username", UserName);
                                          UDat.put("PLZ", mEPLZ.getText().toString().trim());
                                          UDat.put("Strasse", mStrass.getText().toString().trim());
                                          UDat.put("E_Mail", mEmail.getText().toString().trim());
                                           UDat.saveInBackground();


                                          mVorName.setText("Vorname: " + mVorname.getText().toString().trim());
                                          mName.setText("Nachname: " + mNachname.getText().toString().trim());
                                          mUserName.setText("Username: " + UserName);
                                          mPLZ.setText("PLZ: " + mEPLZ.getText().toString().trim());
                                          mStrasse.setText("Strasse: " + mStrass.getText().toString().trim());
                                          mE_Mail.setText("E-Mail: " + mEmail.getText().toString().trim());


                                      }
                                  }

        );
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_userbearbeiten, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
