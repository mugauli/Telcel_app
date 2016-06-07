package net.grapesoft.www.telcel;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class recuperar extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recuperar);

        TextView texto_superior_entrada = (TextView) findViewById(R.id.textView13);
        TextView texto_superior = (TextView) findViewById(R.id.textView14);
        Button btn=(Button) findViewById(R.id.button3);

        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/obscura.otf");
        Typeface tfl = Typeface.createFromAsset(getAssets(), "fonts/ligera.otf");
        Typeface tfm = Typeface.createFromAsset(getAssets(), "fonts/media.otf");
        texto_superior_entrada.setTypeface(tfl);
        texto_superior.setTypeface(tf);
        btn.setTypeface(tfm);

        ImageView btnAyuda = (ImageView) findViewById(R.id.imageView3);
        btnAyuda.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(recuperar.this,ayuda.class);
                startActivity(intent);
            }
        });
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
        if (id == R.id.action_favorite) {
            return true;
        }

        return super.onOptionsItemSelected(item);




    }
}
