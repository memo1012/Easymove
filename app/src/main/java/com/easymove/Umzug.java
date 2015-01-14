package com.easymove;

import com.parse.ParseClassName;
import com.parse.ParseObject;



@ParseClassName("Umzug")
public class Umzug extends ParseObject {



    public String getvon(){
        return getString("von");
    }
    public void setvon(String von){
        put("von",von);
    }

    public String getNach(){
        return getString("Nach");
    }
    public void setNach(String Nach){
        put("Nach",Nach);
    }

    public String getDatum(){
        return getString("Datum");
    }
    public void setDatum(String Datum){
        put("Datum",Datum);
    }

    public String getCreater(){
        return getString("Creater");
    }
    public void setCreater(String Creater){
        put("Creater",Creater);
    }

    public String getBeschreibung(){return getString("Beschreibung");}
    public void setBeschreibung(String Beschreibung){put("Beschreibung",Beschreibung);}


    @Override
    public String toString(){
        return getString("von")+" nach " +getString("Nach")+" am "+getString("Datum");
    }
}
