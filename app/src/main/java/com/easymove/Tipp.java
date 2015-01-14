package com.easymove;

import com.parse.ParseClassName;
import com.parse.ParseObject;

@ParseClassName("Tipp")
public class Tipp extends ParseObject {

    public String getText(){ return getString("Text");}
    public void setText(String Text){put("Text",Text);}

    public String getcreater(){ return getString("creator");}
    public void setcreater(String creater){
        put("creater",creater);
    }

    @Override
    public String toString(){
        return  getString("Text")+"";
    }
}
