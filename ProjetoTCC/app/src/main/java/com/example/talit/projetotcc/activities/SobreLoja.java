package com.example.talit.projetotcc.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.example.talit.projetotcc.R;
import com.example.talit.projetotcc.adapters.TabLayoutAdapter;

public class SobreLoja extends AppCompatActivity {

    private ViewPager view;
    private TabLayout tab;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_sobre_loja);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        view = (ViewPager) findViewById(R.id.view_pager);
        tab = (TabLayout) findViewById(R.id.tablayout);
        view.setAdapter(new TabLayoutAdapter(getSupportFragmentManager()));
        tab.setupWithViewPager(view);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            default:break;
        }
        return true;
    }
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(SobreLoja.this, PaginaInicialEstabelecimentos.class));
        finish();
    }
}
