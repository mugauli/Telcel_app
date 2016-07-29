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
                FragmentComunicados tab1 = new FragmentComunicados();
                return tab1;

            case 1:
                FragmentCampana tab2 = new FragmentCampana();
                return tab2;

            case 2:
                FragmentNoticias tab3 = new FragmentNoticias();
                return tab3;

            case 3:
                FragmentRevista tab4 = new FragmentRevista();
                return tab4;
            case 4:
                FragmentVideo tab5 = new FragmentVideo();
                return tab5;

            case 5:
                FragmentPodCast tab6 = new FragmentPodCast();
                return tab6;

            case 6:
                FragmentGaleria tab7 = new FragmentGaleria();
                return tab7;

            case 7:
                FragmentGrupo tab8 = new FragmentGrupo();
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