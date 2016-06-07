package net.grapesoft.www.telcel;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class acercade extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acercade);

        //Fuentes
        TextView txtGhost = (TextView) findViewById(R.id.textView8);
        TextView txtGhost2 = (TextView) findViewById(R.id.textView9);
        TextView txtGhost3 = (TextView) findViewById(R.id.textView10);

        // Loading Font Face
        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/ligera.otf");

        // Applying font
        txtGhost.setTypeface(tf);
        txtGhost2.setTypeface(tf);
        txtGhost3.setTypeface(tf);



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
       // if (id == R.id.action_favorite) {
       //     return true;
       // }

        return super.onOptionsItemSelected(item);




    }
}
