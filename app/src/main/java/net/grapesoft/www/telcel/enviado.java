package net.grapesoft.www.telcel;

import android.graphics.Typeface;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.widget.TextView;

public class enviado extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enviado);

        //Fuentes
        TextView txtGhost = (TextView) findViewById(R.id.textView11);
        TextView txtGhost1 = (TextView) findViewById(R.id.textView12);

        // Loading Font Face
        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/media.otf");

        // Applying font
        txtGhost.setTypeface(tf);
        txtGhost1.setTypeface(tf);
    }
}
