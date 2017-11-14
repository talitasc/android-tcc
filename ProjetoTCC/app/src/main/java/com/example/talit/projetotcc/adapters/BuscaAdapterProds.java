package com.example.talit.projetotcc.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.talit.projetotcc.R;
import com.example.talit.projetotcc.activities.SearchViewEstabelecimento;
import com.example.talit.projetotcc.activities.SearchViewPaginaInicial;
import com.example.talit.projetotcc.fragments.Buscas;
import com.example.talit.projetotcc.fragments.BuscasProd;
import com.example.talit.projetotcc.logicalView.Busca;
import com.example.talit.projetotcc.sqlight.DbConn;

import java.util.List;

/**
 * Created by talit on 12/11/2017.
 */

public class BuscaAdapterProds extends BaseAdapter {

    Activity act;
    Context c;
    List<Busca> buscas;
    LayoutInflater inflater;
    private DbConn dbconn;

    public BuscaAdapterProds(Activity act, Context c, List<Busca> buscas) {
        this.act = act;
        this.c = c;
        this.buscas = buscas;
        this.inflater = LayoutInflater.from(c);
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

        BuscaAdapterProds.ViewHolder holder;


        if (view == null) {
            holder = new BuscaAdapterProds.ViewHolder();
            view = this.inflater.inflate(R.layout.custom_search_history, viewGroup, false);
            holder.txtBusca = (TextView) view.findViewById(R.id.txt_history);
            view.setTag(holder);

        } else {
            holder = (BuscaAdapterProds.ViewHolder) view.getTag();
        }

        final Busca listBusca = buscas.get(position);
        holder.txtBusca.setText(listBusca.getTxtBusca());
        BuscasProd.listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                Busca bus = new Busca(buscas.get(i).getIdString(), buscas.get(i).getTxtBusca());
                SearchViewEstabelecimento.searchView.setQuery(bus.getTxtBusca(), true);
                Toast.makeText(SearchViewEstabelecimento.act, bus.getTxtBusca(), Toast.LENGTH_SHORT).show();
            }
        });
        dbconn = new DbConn(SearchViewEstabelecimento.act);

        if (BuscasProd.listview.getCount() > 4) {
            dbconn.deleteBusca(buscas.get(4).getIdString());
        }
        return view;
    }

    private class ViewHolder {
        TextView txtBusca;
    }
}
