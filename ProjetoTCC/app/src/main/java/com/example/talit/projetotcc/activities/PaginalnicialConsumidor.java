package com.example.talit.projetotcc.activities;


import android.Manifest;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.talit.projetotcc.R;
import com.example.talit.projetotcc.adapters.ListaSupermercadosAdapter;
import com.example.talit.projetotcc.connectionAPI.ListarSupermercadosPorDescricao;
import com.example.talit.projetotcc.domain.MessageEB;
import com.example.talit.projetotcc.logicalView.Estabelecimento;
import com.example.talit.projetotcc.service.LocationIntentService;
import com.example.talit.projetotcc.sqlight.DbConn;
import com.example.talit.projetotcc.utils.LocaleLanguage;
import com.facebook.login.LoginManager;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Locale;

import de.greenrobot.event.EventBus;

import static android.content.Context.*;


public class PaginalnicialConsumidor extends AppCompatActivity implements ListarSupermercadosPorDescricao.Listener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, NavigationView.OnNavigationItemSelectedListener {

    private Toolbar toolbar;
    private DrawerLayout dl;
    private ActionBarDrawerToggle at;
    public static TextView txtLocalizacao;
    public static String msgLocalizacao;
    private GoogleApiClient googleApiClient;
    private Location lastLocation;
    private Button btnTrocar;
    public static final String LOCATION = "location";
    public static final String TYPE = "type";
    public static final String ADDRESS = "address";
    public static final String STATUS_LOCALIZACAO = "texto";
    public static final String STATUS_ID_CIDADE = "cidade";
    public static final String STATUS_ID_ESTADO = "estado";
    public static final int REQUEST_PERMISSIONS_CODE = 128;
    private NavigationView navigationView;
    public static ProgressBar pb;
    public static Context context;
    public static Activity act;
    private int type;
    private String address;
    private DbConn dbconn;
    public static boolean houveBusca;
    RelativeLayout notificationCount1;
    public static int idEstado;
    public static int idCidade;
    public static RecyclerView listas;
    public static RelativeLayout no_list;
    private SearchView searchView;
    private RelativeLayout rlLocal;
    private ImageButton imFiltro;
    private TextView txtBusca;
    private Locale locale = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_pagina_inicial_consumidor);
        context = this;
        act = this;

        EventBus.getDefault().register(this);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        txtLocalizacao = (TextView) findViewById(R.id.txt_localizacao);
        dl = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        btnTrocar = (Button) findViewById(R.id.btn_trocar);
        listas = (RecyclerView) findViewById(R.id.lv_supermercado);
        no_list = (RelativeLayout) findViewById(R.id.rl_nolist);
        searchView = (SearchView) findViewById(R.id.search_view);
        imFiltro = (ImageButton) findViewById(R.id.imageButton2);
        pb = (ProgressBar) findViewById(R.id.pb_localizaçao);
        rlLocal = (RelativeLayout) findViewById(R.id.id_local);
        txtBusca = (TextView)findViewById(R.id.txt_busca);

        SearchView.SearchAutoComplete searchAutoComplete = (SearchView.SearchAutoComplete) searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        searchAutoComplete.setHintTextColor(getColor(R.color.botoesPrimarios));
        searchAutoComplete.setTextColor(getColor(R.color.botoesPrimarios));

        pb.setVisibility(View.INVISIBLE);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        listas.setHasFixedSize(true);
        StaggeredGridLayoutManager llm = new StaggeredGridLayoutManager(1, 1);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        listas.setLayoutManager(llm);

        if (idEstado != 0) {
            if (idCidade != 0) {
                ListarSupermercadosPorDescricao conn = new ListarSupermercadosPorDescricao(null);
                //conn.execute(idEstado+"",idCidade+"");
                conn.execute("109", "26");
            }
        }
        at = new ActionBarDrawerToggle(this, dl, R.string.menu_item_um, R.string.menu_item_dois);
        dl.addDrawerListener(at);
        at.syncState();

        navigationView.setNavigationItemSelectedListener(this);
        type = 2;
        address = null;
        callConnection();

        if (houveBusca != true) {
            buscarLocais();
        }
        if (msgLocalizacao == null) {
            msgLocalizacao = " " + getString(R.string.rodape);
        }
        if (savedInstanceState != null) {
            msgLocalizacao = savedInstanceState.getString(STATUS_LOCALIZACAO);
            idCidade = savedInstanceState.getInt(STATUS_ID_ESTADO);
            idEstado = savedInstanceState.getInt(STATUS_ID_CIDADE);
            txtLocalizacao.setText(" " + msgLocalizacao);
        } else {
            txtLocalizacao.setText(getString(R.string.rodape));
            //no_list.setVisibility(View.VISIBLE);

        }
        Log.i("local ver", msgLocalizacao);
        btnTrocar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buscarLocais();
            }
        });
        //searchView.setOnQueryTextListener(listener);
        listas.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (dy > 0) {
                    // Toast.makeText(PaginalnicialConsumidor.this, "baixo", Toast.LENGTH_SHORT).show();
                    rlLocal.setVisibility(View.INVISIBLE);
                } else {
                    rlLocal.setVisibility(View.VISIBLE);
                }

            }
        });
        imFiltro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filtroInicial();
            }
        });


        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtBusca.setVisibility(View.INVISIBLE);
                startActivity(new Intent(PaginalnicialConsumidor.this,SearchViewCustom.class));
                finish();
            }
        });

    }
    /*SearchView.OnQueryTextListener listener = new SearchView.OnQueryTextListener() {
        @Override
        public boolean onQueryTextSubmit(String query) {
            return false;
        }

        @Override
        public boolean onQueryTextChange(String query) {
            query = query.toLowerCase();

            final List filteredList = new ArrayList&lt;&gt;();

            return false;
        }
    };*/

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(STATUS_LOCALIZACAO, msgLocalizacao);
        outState.putInt(STATUS_ID_CIDADE, idCidade);
        outState.putInt(STATUS_ID_ESTADO, idEstado);
    }

    @Override
    public void onRestoreInstanceState(Bundle inState) {
        super.onRestoreInstanceState(inState);
        msgLocalizacao = inState.getString(STATUS_LOCALIZACAO);
        idCidade = inState.getInt(STATUS_ID_CIDADE);
        idEstado = inState.getInt(STATUS_ID_ESTADO);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == REQUEST_PERMISSIONS_CODE) {
            for (int i = 0; i < permissions.length; i++) {

                if (permissions[i].equalsIgnoreCase(Manifest.permission.ACCESS_FINE_LOCATION)
                        && grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    callConnection();
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private synchronized void callConnection() {
        googleApiClient = new GoogleApiClient.Builder(this)
                .addOnConnectionFailedListener(this)
                .addConnectionCallbacks(this)
                .addApi(LocationServices.API)
                .build();
        googleApiClient.connect();
    }

    public void callIntentService(int type, String address) {

        if (lastLocation != null) {
            pb.setVisibility(View.INVISIBLE);
            Intent it = new Intent(this, LocationIntentService.class);
            it.putExtra(TYPE, type);
            it.putExtra(ADDRESS, address);
            it.putExtra(LOCATION, lastLocation);
            startService(it);

        } else {
            callAccessGps();
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.i("LOG", "onConnected(" + bundle + ")");

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            Location l = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);

            if (l != null) {
                lastLocation = l;
                Log.i("Log", "latitude: " + l.getLatitude());
                Log.i("Log", "longitude: " + l.getLongitude());
                Log.i("Log", "address: " + address);
            }
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.i("Log", "onconnectionsuspended(" + i + ")");
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.i("LOG", "onConnectionFailed(" + connectionResult + ")");
    }

    public void getLocationListener(View view) {
        int type;
        String address = null;
        type = 2;
        callConnection();
        callIntentService(type, address);
    }

    public void onEvent(final MessageEB m) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                String subLocalizacao;
                Log.i("LOG", m.getResultMessage());

                subLocalizacao = m.getResultMessage().substring(m.getResultMessage().indexOf("-") + 1);
                //msgLocalizacao = subLocalizacao.substring(subLocalizacao.indexOf("-")+ 1);
                msgLocalizacao = m.getResultMessage();
                Log.i("TESTE SUBSTRING", msgLocalizacao);
                txtLocalizacao.setText(" " + msgLocalizacao);
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // aqui inflar o search
        //Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_scrolling, menu);

        MenuItem item1 = menu.findItem(R.id.action_settings);
        MenuItemCompat.setActionView(item1, R.layout.notificacao_update);
        //MenuItem searchViewItem = menu.findItem(R.id.action_search);
        notificationCount1 = (RelativeLayout) MenuItemCompat.getActionView(item1);

        /*final SearchView searchViewAndroidActionBar = (SearchView) MenuItemCompat.getActionView(searchViewItem);
        searchViewAndroidActionBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchViewAndroidActionBar.clearFocus();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });*/

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (at.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.menu_sobre) {


        } else if (id == R.id.menu_meus_dados) {


        } else if (id == R.id.menu_notificações) {
            startActivity(new Intent(getApplicationContext(), Notificacoes.class));
            finish();

        } else if (id == R.id.menu_sair) {
            dbconn = new DbConn(PaginalnicialConsumidor.this);

            if (dbconn.selectConsumidor().getTpAcesso() == 2) {
                LoginManager.getInstance().logOut();
                dbconn.deleteConsumidor();
                startActivity(new Intent(getApplicationContext(), WelcomeScreen.class));
                finish();
            } else {
                dbconn.deleteConsumidor();
                startActivity(new Intent(getApplicationContext(), WelcomeScreen.class));
                finish();
            }

        } else if (id == R.id.menu_ajuda) {
            startActivity(new Intent(getApplicationContext(), DuvidasFrequentes.class));
            finish();

        } else if (id == R.id.menu_idiomas) {
            escolherIdiomas();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }

    public void callAccessGps() {

        LocationManager service = (LocationManager) getSystemService(LOCATION_SERVICE);
        boolean enabled = service.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (!enabled) {
            AlertDialog.Builder builder = new AlertDialog.Builder(PaginalnicialConsumidor.this);
            builder.setTitle(getString(R.string.msg_titulo_localizacao));
            builder.setMessage(getString(R.string.msg_localizacao));
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivity(intent);
                    dialog.dismiss();
                    finish();
                }
            });
            builder.setNegativeButton(getString(R.string.msg_nao), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    txtLocalizacao.setText(" " + getString(R.string.rodape));
                }
            });
            builder.show();
        } else {
            if (lastLocation == null) {
                callConnection();
                AlertDialog.Builder builder = new AlertDialog.Builder(PaginalnicialConsumidor.this);
                builder.setTitle(getString(R.string.msg_titulo_localizacao));
                builder.setMessage(getString(R.string.msg_erro));
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.show();
            }
        }
    }

    public void callAccessLocation() {
        // Log.i(TAG, "callAccessLocation()");

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                solicitaPermissao("É preciso a permissão para apresentação dos eventos locais.", new String[]{Manifest.permission.ACCESS_FINE_LOCATION});
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_PERMISSIONS_CODE);
            }
        } else {

            callIntentService(type, address);
        }
    }

    private void solicitaPermissao(String message, final String[] permissions) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("");
        builder.setMessage(message);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                ActivityCompat.requestPermissions(PaginalnicialConsumidor.this, permissions, REQUEST_PERMISSIONS_CODE);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }


    public void buscarLocais() {
        LayoutInflater inflater = getLayoutInflater();
        final View alertLayout = inflater.inflate(R.layout.custom_alert_dialog_opcoes_busca, null);
        final Button meuLocal = (Button) alertLayout.findViewById(R.id.minha_loca);
        final Button buscarEstados = (Button) alertLayout.findViewById(R.id.por_estado);
        Button cancelar = (Button) alertLayout.findViewById(R.id.cancelar);
        AlertDialog.Builder alerta = new AlertDialog.Builder(PaginalnicialConsumidor.this);
        alerta.setView(alertLayout);
        alerta.setCancelable(false);
        final AlertDialog dialogo = alerta.create();
        dialogo.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogo.show();
        meuLocal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLocationListener(v);
                dialogo.dismiss();
            }
        });
        buscarEstados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PaginalnicialConsumidor.this, ListarEstados.class));
                finish();
                dialogo.dismiss();
            }
        });
        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (houveBusca != true) {

                    no_list.setVisibility(View.VISIBLE);
                }
                dialogo.dismiss();
            }

        });
    }

    public void escolherIdiomas() {
        LayoutInflater inflater = getLayoutInflater();
        final View alertLayout = inflater.inflate(R.layout.custom_alerta_dialog_idiomas, null);
        final Button btnPortugues = (Button) alertLayout.findViewById(R.id.btn_portugues);
        final Button btnEspanhol = (Button) alertLayout.findViewById(R.id.btn_espanhol);
        final Button btnFrances = (Button) alertLayout.findViewById(R.id.btn_francess);
        final Button btnIngles = (Button) alertLayout.findViewById(R.id.btn_ingles);
        Button cancelar = (Button) alertLayout.findViewById(R.id.cancelar);

        AlertDialog.Builder alerta = new AlertDialog.Builder(PaginalnicialConsumidor.this);
        alerta.setView(alertLayout);
        alerta.setCancelable(false);
        final AlertDialog dialogo = alerta.create();
        dialogo.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogo.show();

        btnPortugues.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Context context = LocaleLanguage.setLocale(PaginalnicialConsumidor.this, "Language");
                Resources resources = context.getResources();
                SharedPreferences.Editor editor = getSharedPreferences("IDIOMA", MODE_PRIVATE).edit();
                editor.putString("lingua", "Language");
                editor.commit();
                backToMain();

                //dialogo.dismiss();
            }
        });
        btnFrances.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialogo.dismiss();
            }
        });
        btnIngles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Context context = LocaleLanguage.setLocale(PaginalnicialConsumidor.this, "en");
                Resources resources = context.getResources();
                SharedPreferences.Editor editor = getSharedPreferences("IDIOMA", MODE_PRIVATE).edit();
                editor.putString("lingua", "en");
                editor.commit();
                dialogo.dismiss();
                backToMain();

            }
        });
        btnEspanhol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Context context = LocaleLanguage.setLocale(PaginalnicialConsumidor.this, "es");
                Resources resources = context.getResources();
                SharedPreferences.Editor editor = getSharedPreferences("IDIOMA", MODE_PRIVATE).edit();
                editor.putString("lingua", "es");
                editor.commit();
                dialogo.dismiss();
                backToMain();


            }
        });

        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogo.dismiss();
            }

        });
    }

    public void filtroInicial() {

        LayoutInflater inflater = getLayoutInflater();
        final View alertLayout = inflater.inflate(R.layout.custom_dialog_filtro_inicial, null);

        final CheckBox checkFrete = (CheckBox) alertLayout.findViewById(R.id.checkFrete);
        final CheckBox checkDelivery = (CheckBox) alertLayout.findViewById(R.id.checkDelivery);
        final CheckBox checkTodos = (CheckBox) alertLayout.findViewById(R.id.checkTodos);

        Button cancelar = (Button) alertLayout.findViewById(R.id.cancelar);

        AlertDialog.Builder alerta = new AlertDialog.Builder(PaginalnicialConsumidor.this);
        alerta.setView(alertLayout);
        alerta.setCancelable(false);
        final AlertDialog dialogo = alerta.create();
        dialogo.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogo.show();
        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialogo.dismiss();
            }

        });
    }

    public void backToMain() {
        //Monta a intent para abrir a aplicação.
        //Bundle params;
        dbconn = new DbConn(PaginalnicialConsumidor.this);
        dbconn.deleteConsumidor();

        /*Se quiser adicionar algum parametro para o inicio da aplicação:
        if (params != null) {
            mStartActivity.putExtras(params);
        }*/

        //Realiza o agendamento da intent de abrir o aplicativo:
        //No caso abaixo o aplicativo vai ser reaberto daqui 500ms (System.currentTimeMillis() + 500);
        Intent mStartActivity = new Intent(this, SplashScreen.class);
        PendingIntent mPendingIntent = PendingIntent.getActivity(this, 123456, mStartActivity, PendingIntent.FLAG_CANCEL_CURRENT);
        AlarmManager mgr = (AlarmManager) this.getSystemService(ALARM_SERVICE);
        mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 90, mPendingIntent);

        //Mata todos processos associados a este aplicativo.
        android.os.Process.killProcess(android.os.Process.myPid());
        //Fecha o aplicativo.
        System.exit(1);

    }

    public static Bitmap convert(String base64Str) throws IllegalArgumentException {
        byte[] decodedBytes = Base64.decode(
                base64Str.substring(base64Str.indexOf(",") + 1),
                Base64.DEFAULT
        );
        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
    }

    public static String convert(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);

        return Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT);
    }

    @Override
    public void onLoaded(List<Estabelecimento> listasSupermerc) {

        ListaSupermercadosAdapter listaSuper = new ListaSupermercadosAdapter(PaginalnicialConsumidor.this, PaginalnicialConsumidor.this, listasSupermerc);
        this.listas.setAdapter(listaSuper);
        listaSuper.notifyDataSetChanged();
    }

    @Override
    public void onResume() {
        super.onResume();
        txtLocalizacao.setText(" " + msgLocalizacao);

        /*List<Estabelecimento> listasSupermerc= new ArrayList<>();
        ListaSupermercadosAdapter listaSuper = new ListaSupermercadosAdapter(PaginalnicialConsumidor.this,PaginalnicialConsumidor.this,listasSupermerc);
        this.listas.setAdapter(listaSuper);
        listaSuper.notifyDataSetChanged();*/

    }


}
