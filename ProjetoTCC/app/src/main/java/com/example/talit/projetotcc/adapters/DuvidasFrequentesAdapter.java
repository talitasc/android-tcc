package com.example.talit.projetotcc.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.example.talit.projetotcc.R;

import java.util.HashMap;
import java.util.List;

/**
 * Created by talit on 23/09/2017.
 */

public class DuvidasFrequentesAdapter extends BaseExpandableListAdapter {
    private Context context;
    private List<String> listaDados;
    private HashMap<String, List<String>> listaHashMap;

    public DuvidasFrequentesAdapter(Context context, List<String>listaDados,HashMap<String, List<String>> listaHashMap){

        this.context = context;
        this.listaDados = listaDados;
        this.listaHashMap = listaHashMap;
    }
    @Override
    public int getGroupCount() {

        return listaDados.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return listaHashMap.get(listaDados.get(i)).size();
    }

    @Override
    public Object getGroup(int i) {
        return listaDados.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return listaHashMap.get(listaDados.get(i)).get(i1);
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        String tituloGrupo = (String)getGroup(i);

        if(view == null){
            LayoutInflater inflater = (LayoutInflater)this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.custom_expand_list_view_group,null);
        }
        TextView  listaGrupo = (TextView)view.findViewById(R.id.list_group);
        listaGrupo.setTypeface(null, Typeface.BOLD);
        listaGrupo.setText(tituloGrupo);

        return view;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        String tituloItem = (String)getChild(i,i1);

        if(view == null){
            LayoutInflater inflater = (LayoutInflater)this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.custom_expand_list_view_item,null);
        }
        TextView listaItem = (TextView)view.findViewById(R.id.list_item);
        listaItem.setText(tituloItem);
        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }
}
