package com.easymove;

import com.parse.ParseClassName;
import com.parse.ParseObject;

@ParseClassName("Kommentar")
public class Kommentar extends ParseObject {

    public String getKommi(){ return getString("Kommi");}
    public void setKommi(String Kommi){put("Kommi",Kommi);}

    public String getUmzugsid(){ return getString("Umzugsid");}
    public void setUmzugsid(String Umzugsid){
        put("Umzugsid",Umzugsid);
    }

    public String getCreater(){ return getString("Creator");}
    public void setCreater(String Creater){
        put("Creater",Creater);
    }


    @Override
    public String toString(){
        return getString("Creater")+" schreibt: "+getString("Kommi");
    }
}
