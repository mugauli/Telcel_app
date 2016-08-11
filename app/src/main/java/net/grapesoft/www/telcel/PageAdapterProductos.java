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
                FragmentLanzamientos tab2 = new FragmentLanzamientos();
                return tab2;
            case 1:
                FragmentPublicitaria tab1 = new FragmentPublicitaria();
                return tab1;
            case 2:
                FragmentProductoMes tab3 = new FragmentProductoMes();
                return tab3;
            case 3:
                FragmentSVA tab4 = new FragmentSVA();
               // FragmentPublicitaria tab4 = new FragmentPublicitaria();
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