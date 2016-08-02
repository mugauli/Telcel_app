package net.grapesoft.www.telcel;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.HashMap;

import Utitilies.SessionManagement;

public class activity_promociones extends AppCompatActivity {

    String styledText = "This is <font color='red'>simple</font>.";
    public String tokenCTE = "";
    SessionManagement session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab_fragment_promociones);

        tokenCTE = getText(R.string.tokenXM).toString();
        ArrayList<String> params = new ArrayList<String>();

        String imageHttpAddress = getText(R.string.URL_media).toString();
        session = new SessionManagement(activity_promociones.this);

        final HashMap<String, String> user = session.getUserDetails();
        String region = user.get(SessionManagement.KEY_PD_REGION);

        params.add("6");
        params.add("GetPromo.php");
        params.add(tokenCTE);
        params.add(region);

        new FragmentPromocionesAsync(activity_promociones.this).execute(params);

    }
}
