package net.grapesoft.www.telcel;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import java.util.ArrayList;
import java.util.HashMap;
import Utitilies.SessionManagement;

public class FragmentPodCast extends Fragment {

    public String tokenCTE = "";
    SessionManagement session;
    private Tracker mTracker;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootview = inflater.inflate(R.layout.tab_fragment_podcast, container, false);
        AnalyticsApplication app = (AnalyticsApplication) getActivity().getApplication();
        mTracker = app.getDefaultTracker();
        tokenCTE = getText(R.string.tokenXM).toString();
        ArrayList<String> params = new ArrayList<String>();

        String imageHttpAddress = getText(R.string.URL_media).toString();
        session = new SessionManagement(getActivity());

        final HashMap<String, String> user = session.getUserDetails();
        String region = user.get(SessionManagement.KEY_PD_REGION);

        params.add("6");
        params.add("GetPodcast");
        params.add(tokenCTE);
        params.add(region);
        try{
        new FragmentPodCastAsync(getActivity()).execute(params);
        } catch (Exception e) {
            Log.e("abc","");
            e.printStackTrace();
        }

        return rootview;
    }
    @Override
    public void onResume(){
        super.onResume();
        mTracker.setScreenName("Podcast");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());

    }


}