package net.grapesoft.www.telcel;


import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;


import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;

import Utitilies.SessionManagement;

public class FragmentPodCast extends Fragment {
    public String tokenCTE = "";
    private ListView lista;
    private ImageView imageView;
    private Bitmap loadedImage;
    private String imageHttpAddress = "";
    SessionManagement session;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootview = inflater.inflate(R.layout.tab_fragment_1, container, false);


        //JSON DE LECTURA

        tokenCTE = getText(R.string.tokenXM).toString();
        ArrayList<String> params = new ArrayList<String>();

        imageHttpAddress = getText(R.string.URL_media).toString();
        session = new SessionManagement(getActivity());

        final HashMap<String, String> user = session.getUserDetails();
        String region = user.get(SessionManagement.KEY_REGION);

        params.add("6");
        params.add("GetPodcast.php");
        params.add(tokenCTE);
        params.add(region);

        new FragmentPodCastAsync(getActivity()).execute(params);


        return rootview;
    }







}