package net.grapesoft.www.telcel;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ActualizadosActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actualizados);

        //Fuentes
        TextView txtGhost = (TextView) findViewById(R.id.textView12);


        // Loading Font Face
        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/media.otf");

        // Applying font
        txtGhost.setTypeface(tf);



        ImageView btnAyuda = (ImageView) findViewById(R.id.imgAyudaAC);
        btnAyuda.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(ActualizadosActivity.this,ayuda.class);
                startActivity(intent);
            }
        });
    }
}
