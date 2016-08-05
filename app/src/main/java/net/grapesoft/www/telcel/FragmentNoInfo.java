package net.grapesoft.www.telcel;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import Utitilies.SessionManagement;

/**
 * Created by memoHack on 05/08/2016.
 */
public class FragmentNoInfo extends Fragment {
    SessionManagement session;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_noinfo, container, false);
    }
}
