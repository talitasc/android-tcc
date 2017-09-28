package com.example.talit.projetotcc.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.talit.projetotcc.R;
import com.example.talit.projetotcc.activities.PaginaInicialEstabelecimentos;
import com.example.talit.projetotcc.fragments.DetalhesEstab;
import com.example.talit.projetotcc.logicalView.Estabelecimento;

import java.util.List;

/**
 * Created by talit on 23/04/2017.
 */

public class ListaSupermercadosAdapter extends RecyclerView.Adapter<ListaSupermercadosAdapter.ListarSupermercadoViewHolder> {

    public static class ListarSupermercadoViewHolder extends RecyclerView.ViewHolder {
        private TextView nome_supermercado;
        private TextView nome_cidade;
        private View view;

        public ListarSupermercadoViewHolder(View v) {
            super(v);
            nome_supermercado = (TextView) v.findViewById(R.id.txt_nome_super);
            nome_cidade = (TextView) v.findViewById(R.id.txt_cidade_estab);
            view = v;
        }
    }

    private Activity act;
    private Context c;
    private View v;
    private List<Estabelecimento> listaSupermercado;
    private LayoutInflater inflater;

    public ListaSupermercadosAdapter(Activity act, Context c, List<Estabelecimento> listaSupermercado) {
        this.act = act;
        this.c = c;
        this.listaSupermercado = listaSupermercado;
        this.inflater = LayoutInflater.from(c);
    }

    public ListarSupermercadoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_supermercados, parent, false);
        v = itemView;
        return new ListarSupermercadoViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ListarSupermercadoViewHolder holder, int position) {
        final Estabelecimento listaSuper = listaSupermercado.get(position);
        holder.nome_supermercado.setText(listaSuper.getNomeFantasia());
        holder.nome_cidade.setText(listaSuper.getCidade());
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Estabelecimento estabelecimento = new Estabelecimento(listaSuper.getId(),
                        listaSuper.getNomeFantasia(),
                        listaSuper.getTipoEstabelecimento(),
                        listaSuper.getRua(),
                        listaSuper.getNumero(),
                        listaSuper.getBairro(),
                        listaSuper.getCep(),
                        listaSuper.getCidade(),
                        listaSuper.getEstadoSigla(),
                        listaSuper.getDdd(),
                        listaSuper.getTelefone(),
                        listaSuper.getEmail());
                Toast.makeText(act, estabelecimento.getNomeFantasia(), Toast.LENGTH_SHORT).show();
                PaginaInicialEstabelecimentos.nomeEstab = estabelecimento.getNomeFantasia();
                DetalhesEstab.strNomeFantasia = estabelecimento.getNomeFantasia();
                DetalhesEstab.strRua = estabelecimento.getRua();
                DetalhesEstab.strNumero = estabelecimento.getNumero() + "";
                DetalhesEstab.strbairro = estabelecimento.getBairro();
                DetalhesEstab.strCep = estabelecimento.getCep();
                DetalhesEstab.strCidade = estabelecimento.getCidade();
                DetalhesEstab.strSigla = estabelecimento.getEstadoSigla();
                DetalhesEstab.strDdd = estabelecimento.getDdd();
                DetalhesEstab.strTelefone = estabelecimento.getTelefone();
                DetalhesEstab.strEmail = estabelecimento.getEmail();
                act.startActivity(new Intent(act, PaginaInicialEstabelecimentos.class));
                act.finishActivity(0);
            }
        });

    }

    @Override
    public int getItemCount() {

        try {
            return listaSupermercado.size();
        } catch (Exception e) {
            return 0;
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}
