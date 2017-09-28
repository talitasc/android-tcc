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
import com.example.talit.projetotcc.activities.ProdutosEstabelecimento;
import com.example.talit.projetotcc.fragments.TabCategorias;
import com.example.talit.projetotcc.logicalView.CategoriasProdutos;

import java.util.List;

/**
 * Created by talit on 11/06/2017.
 */

public class CategoriasAdapter extends BaseAdapter {

    Activity act;
    Context c;
    List<CategoriasProdutos> categProd;
    LayoutInflater inflater;

    public CategoriasAdapter(Activity act, Context c, List<CategoriasProdutos> categ){
        this.act = act;
        this.c = c;
        this.categProd = categ;
        this.inflater  = LayoutInflater.from(c);
    }
    @Override
    public int getCount() {
        return categProd.size();
    }

    @Override
    public Object getItem(int position) {
        return categProd.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        CategoriasAdapter.ViewHolder holder;

        if(view == null){
            holder = new CategoriasAdapter.ViewHolder();
            view = this.inflater.inflate(R.layout.card_view_categorias, viewGroup, false);
            holder.nomeCategoria = (TextView)view.findViewById(R.id.txtNomeCategoria);
            view.setTag(holder);

        }
        else{
            holder = (CategoriasAdapter.ViewHolder) view.getTag();
        }
        final CategoriasProdutos categs = categProd.get(position);
        holder.nomeCategoria.setText(categs.getDescricaoCategoria());
        TabCategorias.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                act.startActivity(new Intent(act,ProdutosEstabelecimento.class));
                act.finish();
            }
        });

        return view;
    }
    private class ViewHolder {
        TextView nomeCategoria;
    }
}
