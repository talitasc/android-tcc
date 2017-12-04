package com.example.talit.projetotcc.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.talit.projetotcc.R;
import com.example.talit.projetotcc.activities.ListarCidades;
import com.example.talit.projetotcc.activities.ListarEstados;
import com.example.talit.projetotcc.logicalView.Cidade;
import com.example.talit.projetotcc.logicalView.Estado;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by talit on 23/05/2017.
 */

public class EstadoAdapter extends BaseAdapter {

    Activity act;
    Context c;
    List<Estado> estados;
    LayoutInflater inflater;

    public EstadoAdapter(Activity act, Context c, List<Estado> estados){
        this.act = act;
        this.c = c;
        this.estados = estados;
        this.inflater  = LayoutInflater.from(c);
    }
    @Override
    public int getCount() {
        return estados.size();
    }

    @Override
    public Object getItem(int position) {

        return estados.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        EstadoAdapter.ViewHolder holder;


        if(view == null){
            holder = new EstadoAdapter.ViewHolder();
            view = this.inflater.inflate(R.layout.card_view_estados, viewGroup, false);
            holder.descricaoLocal = (TextView)view.findViewById(R.id.txtDescricao);
            view.setTag(holder);

        }
        else{
            holder = (EstadoAdapter.ViewHolder) view.getTag();
        }

        final Estado listEstado = estados.get(position);
        holder.descricaoLocal.setText(listEstado.getDescricaoEstado());
        ListarEstados.listas.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                Estado estado = new Estado(estados.get(i).getIdEstado(),
                                            estados.get(i).getSiglaEstado(),
                                            estados.get(i).getDescricaoEstado());
                ListarCidades.idEstado = estado.getIdEstado();
                CidadeAdapter.estado = estado.getDescricaoEstado();
                act.startActivity(new Intent(act,ListarCidades.class));
                act.finish();
            }
        });

        return view;
    }
    private class ViewHolder {
        TextView descricaoLocal;
    }

    public void setFilter(ArrayList<Estado> filtro){
        estados = new ArrayList<>();
        estados.addAll(filtro);
        notifyDataSetChanged();

    }
}

