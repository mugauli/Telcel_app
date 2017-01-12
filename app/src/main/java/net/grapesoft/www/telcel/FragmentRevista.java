package net.grapesoft.www.telcel;

/**
 * Created by memoHack on 14/06/2016.
 */

import android.graphics.Bitmap;
import android.os.Bundle;

import android.support.v4.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import java.util.ArrayList;
import java.util.HashMap;

import Utitilies.SessionManagement;


public class FragmentRevista extends Fragment {
    private static String fileName = "revista-demo.pdf";
    private static final String file_url= "http://internetencaja.com.mx/telcel/revistas/revista-demo.pdf";

    public String tokenCTE = "";
    private ListView lista;
    private ImageView imageView;
    private Bitmap loadedImage;
    private String imageHttpAddress = "";
    SessionManagement session;
    private Tracker mTracker;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        final View rootview = inflater.inflate(R.layout.tab_fragment_revista, container, false);
        AnalyticsApplication app = (AnalyticsApplication) getActivity().getApplication();
        mTracker = app.getDefaultTracker();
        tokenCTE = getText(R.string.tokenXM).toString();
        ArrayList<String> params = new ArrayList<String>();

        imageHttpAddress = getText(R.string.URL_media).toString();
        session = new SessionManagement(getActivity());

        final HashMap<String, String> user = session.getUserDetails();
        String region = user.get(SessionManagement.KEY_PD_REGION);

        params.add("6");
        params.add("GetMagazine");
        params.add(tokenCTE);
        params.add(region);

        try {
            new FragmentRevistaAsync(getActivity()).execute(params);
        } catch (Exception e) {
            Log.e("abc", "");
            e.printStackTrace();
        }


        return rootview;

    }
    @Override
    public void onResume(){
        super.onResume();
        mTracker.setScreenName("Revista");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());

    }








}