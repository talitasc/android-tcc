package com.example.talit.projetotcc.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.example.talit.projetotcc.R;
import com.example.talit.projetotcc.activities.PaginalnicialConsumidor;
import com.example.talit.projetotcc.adapters.AvaliacoesAdapter;
import com.example.talit.projetotcc.adapters.ListaSupermercadosAdapter;
import com.example.talit.projetotcc.connectionAPI.ListarAvaliacoes;
import com.example.talit.projetotcc.logicalView.Avaliacao;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;
import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by talit on 10/11/2017.
 */

public class TabAvaliacoes extends Fragment implements ListarAvaliacoes.Listener{

    public static ProgressBar pbAvaliacoes;
    public static RecyclerView recAva;
    public static RelativeLayout relNoAva;
    public static Context context;
    public static Activity activity;
    public static final String ID_ESTABELECIMENTO = "ID";
    public static String idEstab;


    public TabAvaliacoes() {
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        context = container.getContext();
        activity = getActivity();
        View view = inflater.inflate(R.layout.frag_avaliacoes_estabelecimento, container, false);
        pbAvaliacoes = (ProgressBar)view.findViewById(R.id.pb_avaliacoes);
        recAva = (RecyclerView)view.findViewById(R.id.rec_ava);
        relNoAva= (RelativeLayout)view.findViewById(R.id.rl_nolist);
        pbAvaliacoes.setVisibility(View.INVISIBLE);

        SharedPreferences prefs = activity.getSharedPreferences(ID_ESTABELECIMENTO, MODE_PRIVATE);
        idEstab = prefs.getString("idEstab", null);

        recAva.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recAva.setLayoutManager(mLayoutManager);

        ListarAvaliacoes conn = new ListarAvaliacoes(null);
        conn.execute(idEstab);

        return view;
    }

    @Override
    public void onLoaded(List<Avaliacao> avaliacao) {
        AvaliacoesAdapter avaliacoesAdapter = new AvaliacoesAdapter(avaliacao,activity, context);
        this.recAva.setAdapter(avaliacoesAdapter);

    }
}
