package com.easymove;

import com.parse.ParseClassName;
import com.parse.ParseObject;

/**
 * Created by Memo on 04.01.2015.
 */
@ParseClassName("Koordinaten")
public class Koordinaten extends ParseObject {

    public String getOrt() {
        return getString("Ort");
    }
    public void setOrt( String Ort) {
        put("Ort", Ort);
    }

    public String getLangengrad() {
        return getString("Langengrad");
    }
    public void setLangengrad( String Langengrad) {
        put("Langengrad", Langengrad);
    }

    public String getBreitengrad() {return getString("Breitengrad");}
    public void setBreitengrad( String Breitengrad) {
        put("Breitengrad", Breitengrad);
    }



    @Override
    public String toString(){
        return "Ort: "+getOrt()+ "\n"+
                "Längengrad: "+getLangengrad()+ "\n"+
                "Breitengrad: " +getBreitengrad()+"\n";

    }
}
