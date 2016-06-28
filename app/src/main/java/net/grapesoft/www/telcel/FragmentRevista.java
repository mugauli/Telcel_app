package net.grapesoft.www.telcel;

/**
 * Created by memoHack on 14/06/2016.
 */

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import android.os.Environment;
import android.support.v4.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import Utitilies.List_adapted_Revista;
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        final View rootview = inflater.inflate(R.layout.tab_fragment_revista, container, false);

        tokenCTE = getText(R.string.tokenXM).toString();
        ArrayList<String> params = new ArrayList<String>();

        imageHttpAddress = getText(R.string.URL_media).toString();
        session = new SessionManagement(getActivity());

        final HashMap<String, String> user = session.getUserDetails();
        String region = user.get(SessionManagement.KEY_REGION);

        params.add("6");
        params.add("GetMagazine.php");
        params.add(tokenCTE);
        params.add(region);


        new FragmentRevistaAsync(getActivity()).execute(params);



        return rootview;

    }









}