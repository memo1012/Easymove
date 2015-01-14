package com.easymove;


import android.content.Context;
import android.content.Intent;
import android.location.Location;

import android.location.LocationManager;
import android.os.Bundle;

import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.location.LocationRequest;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class UmzuganzeigenDetails extends FragmentActivity
        implements GooglePlayServicesClient.ConnectionCallbacks, com.google.android.gms.location.LocationListener, GooglePlayServicesClient.OnConnectionFailedListener {

    private GoogleMap myMap;            // map reference
    private LocationClient myLocationClient;

    protected String Ortvon;
    protected String Von;
    protected String Nach;
    protected String am;
    protected String Beschreibung;
    protected String User;
    protected String ausgabeUser;
    protected String Kommi;

    protected TextView mVon;
    protected TextView mNach;
    protected TextView mam;
    protected TextView mBeschrei;

    protected Button mHelfen;
    protected Button mSaveKommi;
    protected Button mGProfil;
    protected Button mGUmzug;
    protected Button mGTipp;
    protected Button mGLogout;
    protected Button mNHelfen;

    protected ListView mHelferliste;
    protected ListView mKommiliste;
    protected EditText mKommi;
    protected TextView Textt;
    protected String VLang;
    protected String VBreit;

    public List<Helfer> listeH=new ArrayList<Helfer>();
    public List<Kommentar> listeK=new ArrayList<Kommentar>();

    private static final LocationRequest REQUEST = LocationRequest.create()
            .setInterval(5000)         // 5 seconds
            .setFastestInterval(16)    // 16ms = 60fps
            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);


    /**
     * Activity's lifecycle event.
     * onResume will be Called when the activity is starting.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_umzuganzeigebdetails);
        getMapReference();
        //addMarker();
        setUpMap();
        mVon = (TextView) findViewById(R.id.Umzugvon);
        mNach = (TextView) findViewById(R.id.Umzugnach);
        mam = (TextView) findViewById(R.id.Umzugam);
        mBeschrei=(TextView)findViewById(R.id.editText);

        mHelfen =(Button) findViewById(R.id.HelfenButton);
        mNHelfen =(Button) findViewById(R.id.nichtHelfen);
        mSaveKommi=(Button)findViewById(R.id.SaveKommiButton);
        mGProfil=(Button) findViewById(R.id.button2);
        mGUmzug=(Button) findViewById(R.id.button3);
        mGTipp=(Button)findViewById(R.id.button4);
        mGLogout=(Button)findViewById(R.id.button5);

        mHelferliste=(ListView) findViewById(R.id.listViewHelfer);
        mKommiliste=(ListView) findViewById(R.id.listViewKommi);
        mKommi=(EditText)findViewById(R.id.editTextKomm);
        //Textt=(TextView)findViewById(R.id.textView7);

        Bundle zielkorb = getIntent().getExtras();
        final String id = zielkorb.getString("objectid");

        final ParseUser currentUser = ParseUser.getCurrentUser();
        User=currentUser.getUsername();

    addDaten(id);
     filHelferliste(id);
        FillKommi(id);




                mHelfen.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {



                        Helfer obj = new Helfer();
                        obj.setHelfername(User);
                        obj.setUmzugsid(id);
                        //obj.setText(Test);
                        obj.saveInBackground();
                        mHelfen.setVisibility(View.INVISIBLE);
                        mNHelfen.setVisibility(View.VISIBLE);
                        //Toast.makeText(UmzuganzeigenDetails.this, id , Toast.LENGTH_SHORT).show();
                        filHelferliste(id);
                       /* ParseQuery<Helfer> queryHelfer = new ParseQuery<Helfer>("Helfer");
                        queryHelfer.whereEqualTo("Umzugsid", id);
                        queryHelfer.findInBackground(new FindCallback<Helfer>() {
                            @Override
                            public void done(List<Helfer> helfer, ParseException e) {
                                if (e != null) {
                                    Toast.makeText(UmzuganzeigenDetails.this, "Error " + e, Toast.LENGTH_SHORT).show();
                                } else {
                                    //Toast.makeText(Umzuganzeigen.this, "Daten", Toast.LENGTH_SHORT).show();
                                    for (Helfer H : listeH) {
                                        Helfer newH = new Helfer();
                                        newH.setHelfername(H.getHelfername());
                                        helfer.add(newH);
                                    }
                                }
                                ArrayAdapter<Helfer> adapter = new ArrayAdapter<Helfer>(UmzuganzeigenDetails.this, android.R.layout.simple_list_item_1, helfer);
                                mHelferliste.setAdapter(adapter);


                            }
                        });*/


                    }
                });

        mNHelfen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ParseQuery<ParseObject> queryd = ParseQuery.getQuery("Helfer");
                queryd.whereEqualTo("Helfername", User);
                queryd.whereEqualTo("Umzugsid", id);

                queryd.findInBackground(new FindCallback<ParseObject>() {
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

               /* ParseQuery<Helfer> queryHelfer = new ParseQuery<Helfer>("Helfer");
                queryHelfer.whereEqualTo("Umzugsid", id);
                queryHelfer.findInBackground(new FindCallback<Helfer>() {
                    @Override
                    public void done(List<Helfer> helfer, ParseException e) {
                        if (e != null) {
                            Toast.makeText(UmzuganzeigenDetails.this, "Error " + e, Toast.LENGTH_SHORT).show();
                        } else {
                            //Toast.makeText(Umzuganzeigen.this, "Daten", Toast.LENGTH_SHORT).show();
                            for (Helfer H : listeH) {
                                Helfer newH = new Helfer();
                                newH.setHelfername(H.getHelfername());
                                helfer.add(newH);
                            }
                        }
                        ArrayAdapter<Helfer> adapter = new ArrayAdapter<Helfer>(UmzuganzeigenDetails.this, android.R.layout.simple_list_item_1, helfer);
                        mHelferliste.setAdapter(adapter);


                    }
                });*/

                filHelferliste(id);
                mHelfen.setVisibility(View.VISIBLE);
                mNHelfen.setVisibility(View.INVISIBLE);

            }
        });





        //Listener on Click
        mSaveKommi.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                //Toast.makeText(UmzuganzeigenDetails.this, ausgabeUser , Toast.LENGTH_SHORT).show();
                Kommi=mKommi.getText().toString().trim();
                Kommentar obj = new Kommentar();
                obj.setKommi(Kommi);
                obj.setUmzugsid(id);
                obj.setCreater(User);
                obj.saveInBackground();
                mKommi.setText("");

            FillKommi(id);
            }
        });

        //Listener on Click
        mGLogout.setOnClickListener(new View.OnClickListener()

                                 {
                                     @Override
                                     public void onClick(View v) {

                                         //take User to Homepage
                                         Intent TakeUserHome = new Intent(UmzuganzeigenDetails.this, Anmelden.class);
                                         startActivity(TakeUserHome);

                                     }
                                 }

        );
        //Listener on Click
        mGUmzug.setOnClickListener(new View.OnClickListener()

                                  {
                                      @Override
                                      public void onClick(View v) {

                                          //take User to Homepage
                                          Intent ShowUmzug = new Intent(UmzuganzeigenDetails.this, Umzuganzeigen.class);
                                          startActivity(ShowUmzug);

                                      }
                                  }

        );//Listener on Click
        mGProfil.setOnClickListener(new View.OnClickListener()

                                   {
                                       @Override
                                       public void onClick(View v) {

                                           //take User to Homepage
                                           Intent ShowProf = new Intent(UmzuganzeigenDetails.this, UserBearbeiten.class);
                                           startActivity(ShowProf);

                                       }
                                   }

        );
        //Listener on Click
        mGTipp.setOnClickListener(new View.OnClickListener()

                                 {
                                     @Override
                                     public void onClick(View v) {

                                         //take User to Homepage
                                         Intent ShowTipp = new Intent(UmzuganzeigenDetails.this, CreateTipp.class);
                                         startActivity(ShowTipp);

                                     }
                                 }

        );



    }

/**
 * Activity's lifecycle event.
 * onResume will be called when the Activity receives focus
 * and is visible
 */
    @Override
    protected void onResume() {
        super.onResume();
        getMapReference();
        wakeUpLocationClient();
        myLocationClient.connect();
    }

    /**
     * Activity's lifecycle event.
     * onPause will be called when activity is going into the background,
     */
    @Override
    public void onPause() {
        super.onPause();
        if (myLocationClient != null) {
            myLocationClient.disconnect();
        }
    }

    /**
     * @param lat - latitude of the location to move the camera to
     * @param lng - longitude of the location to move the camera to
     *            Prepares a CameraUpdate object to be used with  callbacks
     */
    private void gotoMyLocation(double lat, double lng) {
        changeCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition.Builder().target(new LatLng(lat, lng))
                        .zoom(16.0f)
                        .bearing(0)
                        .tilt(25)
                        .build()

        ), new GoogleMap.CancelableCallback() {
            @Override
            public void onFinish() {
                // Your code here to do something after the Map is rendered
            }

            @Override
            public void onCancel() {
                // Your code here to do something after the Map rendering is cancelled
            }
        });
    }


    private void gotoMyLocation1(Location loc) {
        myMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                new LatLng(loc.getLatitude(), loc.getLongitude()), 13));

        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(new LatLng(loc.getLatitude(), loc.getLongitude()))      // Sets the center of the map to location user
                .zoom(17)                   // Sets the zoom
                .bearing(90)                // Sets the orientation of the camera to east
                .tilt(40)                   // Sets the tilt of the camera to 30 degrees
                .build();                   // Creates a CameraPosition from the builder
        myMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition)

                , new GoogleMap.CancelableCallback() {
            @Override
            public void onFinish() {
                // Your code here to do something after the Map is rendered
            }

            @Override
            public void onCancel() {
                // Your code here to do something after the Map rendering is cancelled
            }
        });
    }
    /**
     * When we receive focus, we need to get back our LocationClient
     * Creates a new LocationClient object if there is none
     */
    private void wakeUpLocationClient() {
        if (myLocationClient == null) {
            myLocationClient = new LocationClient(getApplicationContext(),
                    this,       // Connection Callbacks
                    this);      // OnConnectionFailedListener
        }
    }

    /**
     * Get a map object reference if none exits and enable blue arrow icon on map
     */
    private void getMapReference() {
        if (myMap == null) {
            myMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();

        }
        if (myMap != null) {
            myMap.setMyLocationEnabled(true);
        }
    }




    /**
     * @param bundle LocationClient is connected
     */
    @Override
    public void onConnected(Bundle bundle) {
        myLocationClient.requestLocationUpdates(
                REQUEST,
                this); // LocationListener
    }

    /**
     * LocationClient is disconnected
     */
    @Override
    public void onDisconnected() {

    }

    /**
     * @param location - Location object with all the information about location
     *                 Callback from LocationClient every time our location is changed
     */
    @Override
    public void onLocationChanged(Location location) {
       // gotoMyLocation(location.getLatitude(), location.getLongitude());
       // gotoMyLocation1(location);
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
    }


    private void changeCamera(CameraUpdate update, GoogleMap.CancelableCallback callback) {
        myMap.moveCamera(update);
    }
    private void addDaten(String id ) {
        ParseQuery<Umzug> query = ParseQuery.getQuery("Umzug");
        query.getInBackground(id, new GetCallback<Umzug>() {

            @Override
            public void done(Umzug umzug, com.parse.ParseException e) {
                if (e == null) {

                    Von = umzug.getString("von");
                    Nach = umzug.getString("Nach");
                    am = umzug.getString("Datum");
                    Beschreibung = umzug.getString("Beschreibung");
                    addData();
                    addMarker(Von);
                    addMarkernach(Nach);
                } else {
                    e.printStackTrace();
                    Toast.makeText(UmzuganzeigenDetails.this, "Fail: ", Toast.LENGTH_SHORT).show();
                }
            }
            public void addData() {
                mVon.setText("Von: " + Von);
                mNach.setText("Nach: " + Nach);
                mam.setText("Am:" + am);
                mBeschrei.setText(Beschreibung);

            }

        });


    }

    private void addMarker(String Vonn) {

        /** Make sure that the map has been initialised **
         if (null != myMap) {
         myMap.addMarker(new MarkerOptions()

         .position(new LatLng(49.0068901,8.4036527))
         .title("Karlsruhe Zentrum")
         .draggable(true)
         );
         }*/


        ParseQuery<Koordinaten> queryvon = ParseQuery.getQuery("Koordinaten");
        queryvon.whereEqualTo("Ort", Vonn);
        queryvon.getFirstInBackground(new GetCallback<Koordinaten>() {
            public void done(Koordinaten Ud, ParseException e) {
                if (e == null) {


                    VLang = Ud.getString("Langengrad");
                    VBreit = Ud.getString("Breitengrad");
                    //Toast.makeText(UmzuganzeigenDetails.this, "lang: "+VLang +" Breit:" +VBreit, Toast.LENGTH_SHORT).show();
                    double VL = Double.parseDouble(VLang);
                    double VB = Double.parseDouble(VBreit);

                    myMap.addMarker(new MarkerOptions()
                                    .position(new LatLng(VB,VL))
                                    .title("Von: "+Von)
                                    .draggable(true)
                    );

                    // addData();
                } else {
                    e.printStackTrace();
                    Toast.makeText(UmzuganzeigenDetails.this, "Fail to Load "+Von, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void addMarkernach(String nachh) {

        ParseQuery<Koordinaten> queryvon = ParseQuery.getQuery("Koordinaten");
        queryvon.whereEqualTo("Ort", nachh);
        queryvon.getFirstInBackground(new GetCallback<Koordinaten>() {
            public void done(Koordinaten Ud, ParseException e) {
                if (e == null) {


                    VLang = Ud.getString("Langengrad");
                    VBreit = Ud.getString("Breitengrad");
                    //Toast.makeText(UmzuganzeigenDetails.this, "lang: "+VLang +" Breit:" +VBreit, Toast.LENGTH_SHORT).show();
                    double VL = Double.parseDouble(VLang);
                    double VB = Double.parseDouble(VBreit);

                    myMap.addMarker(new MarkerOptions()
                                    .position(new LatLng(VB,VL))
                                    .title("Nach: "+Nach)
                                    .draggable(true)
                    );
                } else {
                    e.printStackTrace();
                    Toast.makeText(UmzuganzeigenDetails.this, "Fail to Load "+Nach, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    double Lat=50;
    double Lng =5;
    private void setUpMap() {

       /* myMap.addMarker(new MarkerOptions()
                .position(new LatLng(Lat,Lng))
                .title("Golden Gate Bridge")
                .snippet("San Francisco")
                .icon(BitmapDescriptorFactory
                        .fromResource(R.drawable.ic_launcher)));*/

        LatLngBounds AUSTRALIA = new LatLngBounds(
                new LatLng(12, 5), new LatLng(119, 9));
        myMap.moveCamera(CameraUpdateFactory.newLatLngZoom(AUSTRALIA.getCenter(), 6));

    }
    private void  filHelferliste(String id)
    {
        ParseQuery<Helfer> queryHelfer =new ParseQuery<Helfer>("Helfer");
        queryHelfer.whereEqualTo("Umzugsid", id);
        queryHelfer.findInBackground(new FindCallback<Helfer>() {
            @Override
            public void done(List<Helfer> helfer, ParseException e) {
                if (e != null) {
                    Toast.makeText(UmzuganzeigenDetails.this, "Error " + e, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(UmzuganzeigenDetails.this, "liste aktualisiert" , Toast.LENGTH_SHORT).show();
                    for (Helfer H : listeH) {
                        Helfer newH = new Helfer();
                        newH.setHelfername(H.getHelfername());
                        helfer.add(newH);
                    }
                    String search = User;
                    for(Helfer str : helfer) {
                        if(str.getHelfername().trim().contains(search))
                        { mHelfen.setVisibility(View.INVISIBLE);
                            mNHelfen.setVisibility(View.VISIBLE);}
                    }
                }

                ArrayAdapter<Helfer> adapter = new ArrayAdapter<Helfer>(UmzuganzeigenDetails.this, android.R.layout.simple_list_item_1, helfer);
                mHelferliste.setAdapter(adapter);

            }
        });
    }
private void FillKommi(String id)
{ParseQuery<Kommentar> queryKommi =new ParseQuery<Kommentar>("Kommentar");
    queryKommi.whereEqualTo("Umzugsid", id);
    queryKommi.findInBackground(new FindCallback<Kommentar>() {
        @Override
        public void done(List<Kommentar> kommi, ParseException e) {
            if (e != null) {
                Toast.makeText(UmzuganzeigenDetails.this, "Error " + e, Toast.LENGTH_SHORT).show();
            } else {
                //Toast.makeText(Umzuganzeigen.this, "Daten", Toast.LENGTH_SHORT).show();
                for (Kommentar K : listeK) {
                    Kommentar newK = new Kommentar();
                    newK.setKommi(K.getKommi());
                    newK.setCreater(K.getCreater());
                    kommi.add(newK);
                }
            }
            ArrayAdapter<Kommentar> adapter = new ArrayAdapter<Kommentar>(UmzuganzeigenDetails.this, android.R.layout.simple_list_item_1, kommi);
            //setListAdapter(adapter);
            mKommiliste.setAdapter(adapter);


        }
    });}

   /* public void getLocation() {

        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        LocationLI locationListener = new LocationLI();

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);


    }*/


   }