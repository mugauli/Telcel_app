package net.grapesoft.www.telcel;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class PageAdapterProductos extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public PageAdapterProductos(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                FragmentPodCast tab1 = new FragmentPodCast();
                return tab1;
            case 1:
                FragmentVideo tab2 = new FragmentVideo();
                return tab2;
            case 2:
                FragmentRevista tab3 = new FragmentRevista();
                return tab3;
            case 3:
                FragmentPublicitaria tab4 = new FragmentPublicitaria();
                return tab4;

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}