package com.easymove;

import android.app.AlertDialog;

import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;

import java.util.ArrayList;
import java.util.List;

public class Anmelden extends ListActivity {

    protected EditText mUser;
    protected EditText mPW;
    protected EditText mTest;
    protected Button mReg;
    protected Button mLogin;
    public List<Tipp> list =new ArrayList<Tipp>();





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anmelden);

        mReg = (Button) findViewById(R.id.CreateAccButton);
        mLogin = (Button) findViewById(R.id.LoginButton);
        mUser = (EditText) findViewById(R.id.UsernameLoginTextBox);
        mPW = (EditText) findViewById(R.id.PasswordLoginTextBox);



        ParseQuery<Tipp> query =new ParseQuery<Tipp>("Tipp");

        query.findInBackground(new FindCallback<Tipp>()
        {
            @Override
            public void done(List<Tipp> tipp,ParseException e) {
                if(e!=null) {
                    Toast.makeText(Anmelden.this, "Error " + e, Toast.LENGTH_SHORT).show();
                }
                else{
                    for (Tipp Ti : list){
                        Tipp newTi =new Tipp();
                        newTi.setText(Ti.getText());
                        tipp.add(newTi);
                    }}
                ArrayAdapter<Tipp> adapter=new ArrayAdapter<Tipp>(Anmelden.this,android.R.layout.simple_list_item_1,tipp);
                setListAdapter(adapter);
            }
        });

        //Listener on Click
        mReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //take User to Reg
                Intent TakeUserReg = new Intent(Anmelden.this, Registrieren.class);
                startActivity(TakeUserReg);

            }
        });
        //Listener on Click
        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String Username = mUser.getText().toString().trim();
                String PW = mPW.getText().toString().trim();

                ParseUser.logInInBackground(Username, PW, new LogInCallback() {
                    @Override
                    public void done(ParseUser parseUser, ParseException e) {
                        if (e == null) {
                            // Hooray! The user is logged in.
                            //toast
                            Toast.makeText(Anmelden.this, "Welcome ", Toast.LENGTH_LONG).show();
                            //take User Home
                            Intent TakeUserHome = new Intent(Anmelden.this, Umzuganzeigen.class);
                            startActivity(TakeUserHome);


                        } else {
                            // Signup failed.
                            AlertDialog.Builder builder = new AlertDialog.Builder(Anmelden.this);
                            builder.setMessage(e.getMessage());
                            builder.setTitle("Sorry!");
                            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    //close the dialog
                                    dialogInterface.dismiss();
                                }
                            });
                            AlertDialog dialog = builder.create();
                            dialog.show();
                        }

                    }
                });
            }

        });





}
}
