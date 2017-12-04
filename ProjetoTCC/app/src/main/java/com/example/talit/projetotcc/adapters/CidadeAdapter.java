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
import com.example.talit.projetotcc.logicalView.Cidade;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by talit on 24/05/2017.
 */

public class CidadeAdapter extends BaseAdapter {

    Activity act;
    Context c;
    List<Cidade> cidades;
    LayoutInflater inflater;
    public static String estado;

    public CidadeAdapter(Activity act, Context c, List<Cidade> cidades){
        this.act = act;
        this.c = c;
        this.cidades = cidades;
        this.inflater  = LayoutInflater.from(c);
    }
    @Override
    public int getCount() {
        return cidades.size();
    }

    @Override
    public Object getItem(int position) {

        return cidades.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        CidadeAdapter.ViewHolder holder;


        if(view == null){
            holder = new CidadeAdapter.ViewHolder();
            view = this.inflater.inflate(R.layout.card_view_estados, viewGroup, false);
            holder.descricaoLocal = (TextView)view.findViewById(R.id.txtDescricao);
            view.setTag(holder);

        }
        else{
            holder = (CidadeAdapter.ViewHolder) view.getTag();
        }

        final Cidade listCidade = cidades.get(position);
        holder.descricaoLocal.setText(listCidade.getDescricaoCidade());
        ListarCidades.listas.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                Cidade cid = new Cidade(cidades.get(i).getIdCidade(),
                                        cidades.get(i).getIdEstado(),
                                        cidades.get(i).getDescricaoCidade());
                PaginalnicialConsumidor.msgLocalizacao = cid.getDescricaoCidade() + "-" + estado;
                PaginalnicialConsumidor.idEstado = cid.getIdEstado();
                PaginalnicialConsumidor.idCidade = cid.getIdCidade();
                act.startActivity(new Intent(act,PaginalnicialConsumidor.class));
                act.finish();
                PaginalnicialConsumidor.houveBusca = true;
                Log.i("local api",PaginalnicialConsumidor.msgLocalizacao);
                //ListarSupermercadosPorDescricao conn = new ListarSupermercadosPorDescricao(null);
                //conn.execute(cids.getIdEstado()+"",cids.getIdCidade()+"");
                //conn.execute("26","109");
            }
        });

        return view;
    }
    private class ViewHolder {
        TextView descricaoLocal;
    }
    public void setFilter(ArrayList<Cidade> filtro){
        cidades = new ArrayList<>();
        cidades.addAll(filtro);
        notifyDataSetChanged();

    }
}
