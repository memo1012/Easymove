package com.easymove;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseObject;
import com.parse.ParseUser;


public class Verbindung extends Application {


    @Override
    public void onCreate() {
        super.onCreate();

        ParseObject.registerSubclass(Koordinaten.class);
        ParseObject.registerSubclass(Kommentar.class);
        ParseObject.registerSubclass(Helfer.class);
        ParseObject.registerSubclass(UserDaten.class);
        ParseObject.registerSubclass(Tipp.class);
        ParseObject.registerSubclass(Umzug.class);
        // Add your initialization code here
        Parse.initialize(this, "FwmyJ9h2n2bUZw1EoX2Xk8wgWpnCuCKBPTaakZ3t", "soO7EdFbc8eepPHMBKdMgSqR0XPrw8vekCF0DvGI");


        ParseUser.enableAutomaticUser();
        ParseACL defaultACL = new ParseACL();

        // If you would like all objects to be private by default, remove this line.
        defaultACL.setPublicReadAccess(true);

        ParseACL.setDefaultACL(defaultACL, true);
    }
}