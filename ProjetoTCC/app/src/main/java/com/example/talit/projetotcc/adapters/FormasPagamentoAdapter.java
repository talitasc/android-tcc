package com.example.talit.projetotcc.adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.talit.projetotcc.R;
import com.example.talit.projetotcc.logicalView.FormasDePagamento;

import java.io.ByteArrayOutputStream;
import java.util.List;

/**
 * Created by talit on 02/12/2017.
 */

public class FormasPagamentoAdapter extends RecyclerView.Adapter<FormasPagamentoAdapter.FormasPagamentoViewHolder>{

    public static class FormasPagamentoViewHolder extends RecyclerView.ViewHolder {
        private TextView descricao;
        private ImageView imPag;
        private View view;

        public FormasPagamentoViewHolder(View v) {
            super(v);
            descricao = (TextView) v.findViewById(R.id.txt_pagamento);
            imPag = (ImageView) v.findViewById(R.id.im_pagamento);
            view = v;
        }
    }

    private Activity act;
    private Context c;
    private View v;
    private List<FormasDePagamento> listaFormas;
    private LayoutInflater inflater;

    public FormasPagamentoAdapter(Activity act, Context c, List<FormasDePagamento> listaFormas) {
        this.act = act;
        this.c = c;
        this.listaFormas = listaFormas;
        this.inflater = LayoutInflater.from(c);
    }

    public FormasPagamentoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_formas_pagamento, parent, false);
        v = itemView;
        return new FormasPagamentoViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(FormasPagamentoViewHolder holder, int position) {

        final FormasDePagamento listaForma = listaFormas.get(position);
        holder.descricao.setText(listaForma.getDescricao());

        if(listaForma.getIdPagamento() == 1){
            holder.imPag.setImageResource(R.drawable.ic_boleto);
        }
        if(listaForma.getIdPagamento() == 2){
            holder.imPag.setImageResource(R.drawable.purchase);
        }
        if(listaForma.getIdPagamento() == 3){
            holder.imPag.setImageResource(R.drawable.money);
        }
        if(listaForma.getIdPagamento() == 4){
            holder.imPag.setImageResource(R.drawable.ic_cartao);
        }

    }

    @Override
    public int getItemCount() {

        try {
            return listaFormas.size();
        } catch (Exception e) {
            return 0;
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
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
}
