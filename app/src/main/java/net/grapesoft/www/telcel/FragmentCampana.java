package net.grapesoft.www.telcel;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.LayoutInflater;
import android.view.ViewGroup;

/**
 * Created by memoHack on 23/06/2016.
 */
public class FragmentCampana extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootview = inflater.inflate(R.layout.tab_fragment_campana, container, false);
        return rootview;
    }
}
