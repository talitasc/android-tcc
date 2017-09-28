package com.example.talit.projetotcc.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.talit.projetotcc.activities.PaginaInicialEstabelecimentos;
import com.example.talit.projetotcc.fragments.TabCategorias;
import com.example.talit.projetotcc.fragments.TabDestaques;

/**
 * Created by talit on 05/06/2017.
 */

public class TabPaginaEstabelecimento extends FragmentPagerAdapter {

    private final int PAGE_CONT = 2;
    private String title_Tabs[] = new String[]{
            PaginaInicialEstabelecimentos.destaques,
            PaginaInicialEstabelecimentos.categorias};

    public TabPaginaEstabelecimento(FragmentManager fm) {
        super(fm);

    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new TabDestaques();
            case 1:
                return new TabCategorias();
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
