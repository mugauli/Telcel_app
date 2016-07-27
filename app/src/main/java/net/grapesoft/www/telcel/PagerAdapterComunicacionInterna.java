package net.grapesoft.www.telcel;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class PagerAdapterComunicacionInterna extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public PagerAdapterComunicacionInterna(FragmentManager fm, int NumOfTabs) {
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
                FragmentNoticias tab4 = new FragmentNoticias();
                return tab4;
            case 4:
                FragmentComunicados tab5 = new FragmentComunicados();
                return tab5;
            case 5:
                FragmentGrupo tab7 = new FragmentGrupo();
                return tab7;
            case 6:
                FragmentCampana tab6 = new FragmentCampana();
                return tab6;
            case 7:
                FragmentGaleria tab8 = new FragmentGaleria();
                return tab8;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}