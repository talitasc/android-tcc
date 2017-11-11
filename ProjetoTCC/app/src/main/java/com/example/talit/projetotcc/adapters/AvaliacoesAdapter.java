package com.example.talit.projetotcc.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.talit.projetotcc.R;
import com.example.talit.projetotcc.activities.Avaliacoes;
import com.example.talit.projetotcc.activities.DetalhesProdutos;
import com.example.talit.projetotcc.activities.PaginaInicialEstabelecimentos;
import com.example.talit.projetotcc.fragments.TabDestaques;
import com.example.talit.projetotcc.logicalView.Avaliacao;
import com.example.talit.projetotcc.logicalView.Produtos;
import com.example.talit.projetotcc.sqlight.DbConn;
import com.example.talit.projetotcc.utils.Validacoes;

import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by talit on 10/11/2017.
 */

public class AvaliacoesAdapter extends RecyclerView.Adapter<AvaliacoesAdapter.AvaliacaoViewHolder> {

    private DbConn dbconn;

    public static class AvaliacaoViewHolder extends RecyclerView.ViewHolder  {

        private TextView txtNome;
        private TextView txtDescricao;
        private TextView txtData;
        private RatingBar ratingBar;
        private View view;

        public AvaliacaoViewHolder(View v) {
            super(v);
            txtNome = (TextView)v.findViewById(R.id.txt_nome_avaliador);
            txtData = (TextView)v.findViewById(R.id.txt_data_avaliacao);
            txtDescricao = (TextView)v.findViewById(R.id.txt_comentario_avaliacao);
            ratingBar = (RatingBar)v.findViewById(R.id.ratingBar);
            view = v;
        }
    }

    private List<Avaliacao> prod;
    private View v;
    private Activity act;
    private Context c;

    public AvaliacoesAdapter(List<Avaliacao> produtos, Activity act, Context c){
        this.prod = produtos;
        this.act = act;
        this.c = c;
    }
    @Override
    public AvaliacaoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_avaliacoes, parent, false);
        v = itemView;
        return new AvaliacaoViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final AvaliacaoViewHolder holder, int position) {
        final Avaliacao produtos = prod.get(position);
        holder.txtNome.setText(produtos.getNome_avaliador());
        holder.txtDescricao.setText(produtos.getDescricao_avaliacao());
        holder.ratingBar.setRating(produtos.getNota_avaliacao());

        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date data = formato.parse(produtos.getData_avaliacao());
            formato.applyPattern("dd/MM/yyyy");
            holder.txtData.setText(formato.format(data).replace("-","/"));
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return prod.size();
    }

    public void clear() {

        int size = prod.size();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                prod.remove(0);
            }

            this.notifyItemRangeRemoved(0, size);
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}
