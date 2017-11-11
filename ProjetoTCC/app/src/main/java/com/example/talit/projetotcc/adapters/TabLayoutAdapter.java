package com.example.talit.projetotcc.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.talit.projetotcc.activities.PaginalnicialConsumidor;
import com.example.talit.projetotcc.fragments.DetalhesEstab;
import com.example.talit.projetotcc.fragments.TabAvaliacoes;
import com.example.talit.projetotcc.fragments.TabDestaques;

/**
 * Created by talit on 09/11/2017.
 */

public class TabLayoutAdapter extends FragmentPagerAdapter {

    private final int PAGE_CONT = 2;
    private String title_Tabs[] = new String[]{PaginalnicialConsumidor.informacoes,PaginalnicialConsumidor.avaliacao};

    public TabLayoutAdapter(FragmentManager fm) {
        super(fm);

    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new DetalhesEstab();
            case 1:
                return new TabAvaliacoes();
            default:
                return null;
        }

    }

    @Override
    public int getCount() {
        return PAGE_CONT;
    }
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return title_Tabs[position];
            case 1:
                return title_Tabs[position];
            default:
                return null;
        }
    }
}
