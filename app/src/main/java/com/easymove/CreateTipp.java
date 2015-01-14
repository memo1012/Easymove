package com.easymove;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.easymove.R;
import com.easymove.Umzug;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;


public class CreateTipp extends ListActivity {

    protected EditText mTippText;
    protected Button mSaveTipp;
    protected Button mProfil;
    protected Button mUmzüge;
    protected Button mlogout;
    public List<Tipp> list =new ArrayList<Tipp>();
    protected String User;
    protected String Tippp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createtipp);

        mTippText =(EditText) findViewById(R.id.TippText);
        mSaveTipp = (Button) findViewById(R.id.SaveTippButton);


        ParseUser currentUser = ParseUser.getCurrentUser();
        User=currentUser.getUsername();


        tippget();
        //Listener on Click
        mSaveTipp.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
              /*   ParseObject Tipp = new ParseObject("Tipp");
                Tipp.put("Text", Tippp);
                Tipp.put("creater",ausgabeUser);
                Tipp.saveInBackground();*/

                Tippp=mTippText.getText().toString().trim();
                Tipp obj = new Tipp();
               obj.setText(Tippp);
                obj.setcreater(User);
                //obj.setText(Test);
                obj.saveInBackground();
                mTippText.setText("");

            tippget();
            }

        });


        mProfil=(Button) findViewById(R.id.Profil);
        mProfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //take User to Reg
                Intent TakeUserBe = new Intent(CreateTipp.this, UserBearbeiten.class);
                startActivity(TakeUserBe);

            }
        });

        mUmzüge=(Button) findViewById(R.id.Umzüge);
        mUmzüge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //take User to Reg
                Intent TakeUserUm = new Intent(CreateTipp.this, Umzuganzeigen.class);
                startActivity(TakeUserUm);

            }
        });
        mlogout=(Button) findViewById(R.id.Logout);
        mlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //take User to Reg
                Intent TakeUserReg = new Intent(CreateTipp.this, Anmelden.class);
                startActivity(TakeUserReg);

            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_createtipp, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public void tippget()
    {
        ParseQuery<Tipp> query =new ParseQuery<Tipp>("Tipp");
        query.findInBackground(new FindCallback<Tipp>() {
            @Override
            public void done(List<Tipp> tipp,ParseException e) {
                if(e!=null) {
                    Toast.makeText(CreateTipp.this, "Error " + e, Toast.LENGTH_SHORT).show();
                }
                else{
                    //Toast.makeText(CreateTipp.this, "Daten", Toast.LENGTH_SHORT).show();
                    for (Tipp Ti : list){
                        Tipp newTi =new Tipp();
                        newTi.setText(Ti.getText());
                        tipp.add(newTi);
                    }}
                ArrayAdapter<Tipp> adapter=new ArrayAdapter<Tipp>(CreateTipp.this,android.R.layout.simple_list_item_1,tipp);
                setListAdapter(adapter);
            }
        });
    }
}
