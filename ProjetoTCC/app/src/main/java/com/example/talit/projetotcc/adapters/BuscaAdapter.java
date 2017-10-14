package com.example.talit.projetotcc.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.talit.projetotcc.R;
import com.example.talit.projetotcc.activities.ListarCidades;
import com.example.talit.projetotcc.activities.PaginalnicialConsumidor;
import com.example.talit.projetotcc.activities.SearchViewCustom;
import com.example.talit.projetotcc.logicalView.Busca;
import com.example.talit.projetotcc.logicalView.Cidade;

import java.util.List;

/**
 * Created by talit on 14/10/2017.
 */

public class BuscaAdapter extends BaseAdapter {

    Activity act;
    Context c;
    List<Busca> buscas;
    LayoutInflater inflater;
    public static String estado;

    public BuscaAdapter(Activity act, Context c, List<Busca> buscas){
        this.act = act;
        this.c = c;
        this.buscas = buscas;
        this.inflater  = LayoutInflater.from(c);
    }
    @Override
    public int getCount() {
        return buscas.size();
    }

    @Override
    public Object getItem(int position) {

        return buscas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        BuscaAdapter.ViewHolder holder;


        if(view == null){
            holder = new BuscaAdapter.ViewHolder();
            view = this.inflater.inflate(R.layout.custom_search_history, viewGroup, false);
            holder.txtBusca = (TextView)view.findViewById(R.id.txt_history);
            view.setTag(holder);

        }
        else{
            holder = (BuscaAdapter.ViewHolder) view.getTag();
        }

        final Busca listBusca = buscas.get(position);
        holder.txtBusca.setText(listBusca.getTxtBusca());
        SearchViewCustom.listview.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                Busca bus = new Busca(buscas.get(i).getIdString(),buscas.get(i).getTxtBusca());
                //Log.i("local api",PaginalnicialConsumidor.msgLocalizacao);
                //ListarSupermercadosPorDescricao conn = new ListarSupermercadosPorDescricao(null);
                //conn.execute(cids.getIdEstado()+"",cids.getIdCidade()+"");
                //conn.execute("26","109");
            }
        });

        return view;
    }
    private class ViewHolder {
        TextView txtBusca;
    }
}
