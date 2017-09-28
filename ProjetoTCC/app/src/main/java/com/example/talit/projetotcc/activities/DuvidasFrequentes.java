package com.example.talit.projetotcc.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ExpandableListView;

import com.example.talit.projetotcc.R;
import com.example.talit.projetotcc.adapters.DuvidasFrequentesAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DuvidasFrequentes extends AppCompatActivity {

    private ExpandableListView expandableListView;
    private DuvidasFrequentesAdapter duvidasFrequentesAdapter;
    private List<String> listaDados;
    private HashMap<String,List<String>> listaHash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_duvidas_frequentes);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.txt_faq));

        expandableListView = (ExpandableListView)findViewById(R.id.expandListview);
        insereDuvidasFrequentes();
        duvidasFrequentesAdapter = new DuvidasFrequentesAdapter(this,listaDados,listaHash);
        expandableListView.setAdapter(duvidasFrequentesAdapter);
    }
    public void insereDuvidasFrequentes(){

        listaDados = new ArrayList<>();
        listaHash = new HashMap<>();

        listaDados.add(getString(R.string.txt_duvida_um));
        listaDados.add(getString(R.string.txt_duvida_dois));
        listaDados.add(getString(R.string.txt_duvida_tres));
        listaDados.add(getString(R.string.txt_duvida_quatro));
        listaDados.add(getString(R.string.txt_duvida_cinco));
        listaDados.add(getString(R.string.txt_duvida_seis));
        listaDados.add(getString(R.string.txt_duvida_sete));
        listaDados.add(getString(R.string.txt_duvida_oito));
        listaDados.add(getString(R.string.txt_duvida_nove));
        listaDados.add(getString(R.string.txt_duvida_dez));
        listaDados.add(getString(R.string.txt_duvida_onze));
        listaDados.add(getString(R.string.txt_duvida_doze));
        listaDados.add(getString(R.string.txt_duvida_treze));
        listaDados.add(getString(R.string.txt_duvida_quatorze));
        listaDados.add(getString(R.string.txt_duvida_quinze));


        List<String> alterarDados = new ArrayList<>();
        alterarDados.add(getString(R.string.txt_duvida_um_solucao));

        List<String> alterarEmail = new ArrayList<>();
        alterarEmail.add(getString(R.string.txt_duvida_dois_solucao));

        List<String> formasPag = new ArrayList<>();
        formasPag.add(getString(R.string.txt_duvida_tres_solucao));

        List<String> retirarCompra = new ArrayList<>();
        retirarCompra.add(getString(R.string.txt_duvida_quatro_solucao));

        List<String> comoRetirarCompra = new ArrayList<>();
        comoRetirarCompra.add(getString(R.string.txt_duvida_cinco_solucao));

        List<String> agendarCompra = new ArrayList<>();
        agendarCompra.add(getString(R.string.txt_duvida_seis_solucao));

        List<String> docsPedido = new ArrayList<>();
        docsPedido.add(getString(R.string.txt_duvida_sete_solucao));

        List<String> quemRetiraPedido = new ArrayList<>();
        quemRetiraPedido.add(getString(R.string.txt_duvida_oito_solucao));

        List<String> naoRetiraPedido = new ArrayList<>();
        naoRetiraPedido.add(getString(R.string.txt_duvida_nove_solucao));

        List<String> frete = new ArrayList<>();
        frete.add(getString(R.string.txt_duvida_dez_solucao));

        List<String> endereco = new ArrayList<>();
        endereco.add(getString(R.string.txt_duvida_onze_solucao));

        List<String> cancelarPedido = new ArrayList<>();
        cancelarPedido.add(getString(R.string.txt_duvida_doze_solucao));

        List<String> devolucao = new ArrayList<>();
        devolucao.add(getString(R.string.txt_duvida_treze_solucao));

        List<String> qtdItens = new ArrayList<>();
        qtdItens.add(getString(R.string.txt_duvida_quatorze_solucao));

        List<String> semPedido = new ArrayList<>();
        semPedido.add(getString(R.string.txt_duvida_quinze_solução));

        listaHash.put(listaDados.get(0),alterarDados);
        listaHash.put(listaDados.get(1),alterarEmail);
        listaHash.put(listaDados.get(2),formasPag);
        listaHash.put(listaDados.get(3),retirarCompra);
        listaHash.put(listaDados.get(4),comoRetirarCompra);
        listaHash.put(listaDados.get(5),agendarCompra);
        listaHash.put(listaDados.get(6),docsPedido);
        listaHash.put(listaDados.get(7),quemRetiraPedido);
        listaHash.put(listaDados.get(8),naoRetiraPedido);
        listaHash.put(listaDados.get(9),frete);
        listaHash.put(listaDados.get(10),endereco);
        listaHash.put(listaDados.get(11),cancelarPedido);
        listaHash.put(listaDados.get(12),devolucao);
        listaHash.put(listaDados.get(13),qtdItens);
        listaHash.put(listaDados.get(14),semPedido);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(DuvidasFrequentes.this, PaginalnicialConsumidor.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
        finish();

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
}
