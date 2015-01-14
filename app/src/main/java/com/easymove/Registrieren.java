package com.easymove;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.util.Base64;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.util.Date;
import java.text.*;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import java.io.File;

public class Registrieren extends Activity {

    protected EditText mName;
    protected EditText mVorname;
    protected EditText mUsername;
    protected EditText mStrasse;
    protected EditText mWohnort;
    protected EditText mPLZ;
    protected EditText mEmail;
    protected EditText mPassword;
    protected EditText mPasswordW;
    protected Button mSpeichern;
    protected Button mZurück;
    protected Button mBild;
    protected String Bild;

    private static String logtag = "camaertest";
    ImageView imageView;
    ImageView imageViewtest;
    private static  int Take_Picture = 1;
    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrieren);

        //Auslesen
        mName = (EditText) findViewById(R.id.NameText);
        mVorname = (EditText) findViewById(R.id.VornameText);
        mUsername = (EditText) findViewById(R.id.UsernameText);
        mStrasse = (EditText) findViewById(R.id.StrasseText);
        mWohnort = (EditText) findViewById(R.id.WohnortText);
        mPLZ = (EditText) findViewById(R.id.PLZText);
        mEmail = (EditText) findViewById(R.id.EmailText);
        mPassword = (EditText) findViewById(R.id.PasswordText);
        mPasswordW = (EditText) findViewById(R.id.PasswordWText);
        mSpeichern = (Button) findViewById(R.id.SpeichernButton);
        mZurück= (Button) findViewById(R.id.Zurückutton);
        mBild = (Button) findViewById(R.id.BildButton);

        imageView = (ImageView) findViewById(R.id.imageView2);
        imageViewtest = (ImageView) findViewById(R.id.imageView);
        //Listener on Click
        mSpeichern.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //Get Username,Password and email convert to String
                final String Userame = mUsername.getText().toString().trim();
                String Email = mEmail.getText().toString().trim();
                String Password = mPassword.getText().toString().trim();
                String Passwordw = mPasswordW.getText().toString().trim();



                if (Password.equals(Passwordw) ) {

                    //Speichern User in Parse
                    ParseUser user = new ParseUser();
                    user.setUsername(Userame);
                    user.setPassword(Password);
                    user.setEmail(Email);
                    user.put("Vorname",mVorname.getText().toString().trim());
                    //user.put("ProfilBild",new ParseFile("test.jpg",Bild.getBytes()));


                    UserDaten obj = new UserDaten();
                    obj.setName( mName.getText().toString().trim());
                    obj.setVorname(mVorname.getText().toString().trim());
                    obj.setUsername(mUsername.getText().toString().trim());
                    obj.setStrasse(mStrasse.getText().toString().trim());
                    obj.setWohnort(mWohnort.getText().toString().trim());
                    obj.setPLZ(mPLZ.getText().toString().trim());
                    obj.setE_Mail(Email);

                    byte[] data = Bild.getBytes();
                    ParseFile file = new ParseFile("resume.txt", data);
                    file.saveInBackground();
                    obj.setProfilBild(file);

                    obj.saveInBackground();


                    user.signUpInBackground(new SignUpCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e == null) {
                                //user signed up sussesfully
                                //toast
                                Toast.makeText(Registrieren.this, "Welcome ", Toast.LENGTH_LONG).show();

                                //take User to Homepage
                                Intent TakeUserHome = new Intent(Registrieren.this, Umzuganzeigen.class);
                                startActivity(TakeUserHome);

                            } else {
                                //there was an error
                                //toast
                                //Toast.makeText(Registrieren.this, "Failed: "+e.toString(), Toast.LENGTH_LONG).show();

                                // Signup failed.
                                AlertDialog.Builder builder = new AlertDialog.Builder(Registrieren.this);
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
                } else { //toast
                    Toast.makeText(Registrieren.this, "Ungleiches Password ", Toast.LENGTH_LONG).show();
                }
            }


        });

        //Listener on Click
        mZurück.setOnClickListener(new View.OnClickListener()

                                 {
                                     @Override
                                     public void onClick(View v) {

                                         //take User to Homepage
                                         Intent TakeUserBack = new Intent(Registrieren.this, Anmelden.class);
                                         startActivity(TakeUserBack);


                                     }
                                 }

        );//Listener on Click
        /*mBild.setOnClickListener(new View.OnClickListener()

                                   {
                                       @Override
                                       public void onClick(View v) {

                                           //take User to Homepage
                                           Intent TakeUsercam = new Intent(Registrieren.this, camera.class);
                                           startActivity(TakeUsercam);


                                       }
                                   }

        );*/
        mBild.setOnClickListener(cameraListener);

    } private View.OnClickListener cameraListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            takePhoto(v);

        }
    };
    private void takePhoto(View v){
        SimpleDateFormat s = new SimpleDateFormat("ddMMyyyyhhmmss");
        String bildname ="picture"+ s.format(new Date())+".jpg";
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        File photo = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),bildname);
        imageUri = Uri.fromFile(photo);
        intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
        startActivityForResult(intent,Take_Picture);
    }
    @Override
    protected void onActivityResult (int requestCode,int resultCode,Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        if (resultCode == Activity.RESULT_OK){
            Uri selectedImage =imageUri;
            getContentResolver().notifyChange(selectedImage,null);

            ContentResolver Cr = getContentResolver();
            Bitmap bitmap;

            try{
                bitmap = MediaStore.Images.Media.getBitmap(Cr,selectedImage);
                imageView.setImageBitmap(bitmap);

                //Bitmap to String
                ByteArrayOutputStream baos=new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG,100, baos);
                byte [] b=baos.toByteArray();
                Bild=Base64.encodeToString(b, Base64.DEFAULT);



//String to Bitmap
                byte [] encodeByte=Base64.decode(Bild,Base64.DEFAULT);
                Bitmap bit= BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
                //imageViewtest.setImageBitmap(bit);

            }catch (Exception e){
                Log.e(logtag, e.toString());
            }
        }
    }

}
