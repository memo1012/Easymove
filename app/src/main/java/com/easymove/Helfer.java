package com.easymove;

import com.parse.ParseClassName;
import com.parse.ParseObject;

@ParseClassName("Helfer")
public class Helfer extends ParseObject {

    public String getHelfername(){ return getString("Helfername");}
    public void setHelfername(String Helfername){put("Helfername",Helfername);}

    public String getUmzugsid(){ return getString("Umzugsid");}
    public void setUmzugsid(String Umzugsid){
        put("Umzugsid",Umzugsid);
    }

    @Override
    public String toString(){
        return  getString("Helfername")+"";
    }
}