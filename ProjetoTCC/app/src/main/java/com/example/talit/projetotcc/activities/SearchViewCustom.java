package com.example.talit.projetotcc.activities;

import android.app.SearchManager;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.talit.projetotcc.R;
import com.example.talit.projetotcc.adapters.BuscaAdapter;
import com.example.talit.projetotcc.adapters.ProdutosAdapter;
import com.example.talit.projetotcc.logicalView.Busca;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by talit on 12/10/2017.
 */

public class SearchViewCustom extends AppCompatActivity {

    private TextView txtSugestao1;
    private TextView txtSugestao2;
    private TextView txtSugestao3;
    private TextView txtSugestao4;
    private TextView txtSugestao5;
    private TextView txtSugestao6;
    private TextView txtSugestao7;
    private TextView txtSugestao8;
    private TextView txtSugestao9;
    private TextView txtSugestao10;
    private TextView txtSugestao11;
    private TextView txtSugestao12;
    private TextView txtSugestao13;
    private SearchView searchView;
    public static ListView listview;
    private ArrayList<Busca> buscas = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_searchview);
        txtSugestao1 = (TextView) findViewById(R.id.sugestao1);
        txtSugestao2 = (TextView) findViewById(R.id.sugestao2);
        txtSugestao3 = (TextView) findViewById(R.id.sugestao3);
        txtSugestao4 = (TextView) findViewById(R.id.sugestao4);
        txtSugestao5 = (TextView) findViewById(R.id.sugestao5);
        txtSugestao6 = (TextView) findViewById(R.id.sugestao6);
        txtSugestao7 = (TextView) findViewById(R.id.sugestao7);
        txtSugestao8 = (TextView) findViewById(R.id.sugestao8);
        txtSugestao9 = (TextView) findViewById(R.id.sugestao9);
        txtSugestao10 = (TextView) findViewById(R.id.sugestao10);
        txtSugestao11 = (TextView) findViewById(R.id.sugestao11);
        txtSugestao12 = (TextView) findViewById(R.id.sugestao12);
        txtSugestao13 = (TextView) findViewById(R.id.sugestao13);
        listview = (ListView)findViewById(R.id.list_suggestios);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setElevation(2);

        txtSugestao1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchView.setQuery(getResources().getString(R.string.txt_sugestoes_1),false);
            }
        });
        txtSugestao2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchView.setQuery(getResources().getString(R.string.txt_sugestoes_2),false);
            }
        });
        txtSugestao3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchView.setQuery(getResources().getString(R.string.txt_sugestoes_3),false);
            }
        });
        txtSugestao4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchView.setQuery(getResources().getString(R.string.txt_sugestoes_4),false);
            }
        });
        txtSugestao5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchView.setQuery(getResources().getString(R.string.txt_sugestoes_5),false);
            }
        });
        txtSugestao6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchView.setQuery(getResources().getString(R.string.txt_sugestoes_6),false);
            }
        });
        txtSugestao7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchView.setQuery(getResources().getString(R.string.txt_sugestoes_7),false);
            }
        });
        txtSugestao8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchView.setQuery(getResources().getString(R.string.txt_sugestoes_8),false);
            }
        });
        txtSugestao9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchView.setQuery(getResources().getString(R.string.txt_sugestoes_9),false);
            }
        });
        txtSugestao10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchView.setQuery(getResources().getString(R.string.txt_sugestoes_10),false);
            }
        });
        txtSugestao11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchView.setQuery(getResources().getString(R.string.txt_sugestoes_11),false);
            }
        });
        txtSugestao12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchView.setQuery(getResources().getString(R.string.txt_sugestoes_12),false);
            }
        });
        txtSugestao13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchView.setQuery(getResources().getString(R.string.txt_sugestoes_13),false);
            }
        });

        buscas.add(new Busca(1, "ba"));
        BuscaAdapter buscaAdapter  = new BuscaAdapter(SearchViewCustom.this, SearchViewCustom.this,buscas);
        listview.setAdapter(buscaAdapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_searchview_produtos, menu);
        MenuItem searchMenuItem = menu.findItem(R.id.action_search);
        searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.action_search));
        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconified(false);
        searchView.setIconifiedByDefault(true);
        searchView.setQueryHint(getResources().getString(R.string.busca));
        searchMenuItem.expandActionView();

        int id = searchMenuItem.getItemId();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {

                if (query.length() != 0) {

                    // handle search here
                    return true;
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        MenuItem searchVoice = menu.findItem(R.id.search_voice_btn);

        searchVoice.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                //searchView.setQuery("blabla", false);
                promptSpeechInput();
                return false;
            }
        });
        return true;
    }

    public void promptSpeechInput() {

        Intent i = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        i.putExtra(RecognizerIntent.EXTRA_PROMPT, "SAY SOMETHING");
        try {

            startActivityForResult(i, 100);

        } catch (ActivityNotFoundException e) {
            Toast.makeText(SearchViewCustom.this, "no permissions", Toast.LENGTH_SHORT).show();
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case 100:
                if (resultCode == RESULT_OK && data != null) {
                    ArrayList<String> voiceText = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    String voice = voiceText.get(0);
                    searchView.setQuery(voice, false);
                    Toast.makeText(SearchViewCustom.this, voice, Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }


}
