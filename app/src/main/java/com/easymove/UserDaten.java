package com.easymove;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseFile;

/**
 * Created by Memo on 30.11.2014.
 */
@ParseClassName("UserDaten")
public class UserDaten extends ParseObject {

    public String getName() {return getString("Name");}
    public void setName( String Name) {
        put("Name", Name);
    }

    public String getVorname() {
        return getString("Vorname");
    }
    public void setVorname( String Vorname) {
        put("Vorname", Vorname);
    }

    public String getUsername() {
        return getString("Username");
    }
    public void setUsername( String Username) {
        put("Username", Username);
    }

    public String getStrasse() {return getString("Strasse");}
    public void setStrasse( String Strasse) {put("Strasse", Strasse);}

    public String getWohnort(String wohnort) {
        return getString("Wohnort");
    }
    public void setWohnort( String Wohnort) {
        put("Wohnort", Wohnort);
    }

    public String getPLZ() {
        return getString("PLZ");
    }
    public void setPLZ( String PLZ) {
        put("PLZ", PLZ);
    }

    public String getE_Mail() {return getString("E_Mail");}
    public void setE_Mail( String E_Mail) {
        put("E_Mail", E_Mail);
    }

   //public ParseFile getProfilBild() {return new ParseFile("ProfilBild",);}
    public void setProfilBild( ParseFile ProfilBild) {
        put("ProfilBild", ProfilBild);
    }


    @Override
    public String toString(){
        return "Name: "+getName()+ "\n"+
                "Vorname: " +getVorname()+"\n"+
                "Username: "+ getUsername()+"\n"+
                "Strasse: "+getStrasse()+"\n"+
                "Wohnort: "+getWohnort("Wohnort")+"\n"+
                "PLZ: "+getPLZ()+"\n"+
                "E-Mail: "+getE_Mail();
    }
}
