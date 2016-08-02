package net.grapesoft.www.telcel;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class PageAdapterPrestaciones extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public PageAdapterPrestaciones(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                FragmentPromociones tab1 = new FragmentPromociones();
                return tab1;
            case 1:
                FragmentDescuentos tab2 = new FragmentDescuentos();
                return tab2;

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}