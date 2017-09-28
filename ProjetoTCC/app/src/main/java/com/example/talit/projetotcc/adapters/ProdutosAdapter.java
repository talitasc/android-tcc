package com.example.talit.projetotcc.adapters;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.talit.projetotcc.R;
import com.example.talit.projetotcc.activities.DetalhesProdutos;
import com.example.talit.projetotcc.logicalView.Produtos;

import java.io.ByteArrayOutputStream;
import java.util.List;

/**
 * Created by talit on 11/06/2017.
 */

public class ProdutosAdapter extends RecyclerView.Adapter<ProdutosAdapter.ProdutosViewHolder> {

    public static class ProdutosViewHolder extends RecyclerView.ViewHolder  {
        private TextView txtNome;
        private TextView txtMarca;
        private TextView txtPreco;
        private ImageView imagem;
        private TextView codRef;
        private View view;

        public ProdutosViewHolder(View v) {
            super(v);
            txtNome = (TextView)v.findViewById(R.id.txtNomeProduto);
            txtMarca = (TextView)v.findViewById(R.id.txt_marca_prod);
            txtPreco = (TextView)v.findViewById(R.id.txt_preco);
            imagem = (ImageView)v.findViewById(R.id.im_logo_produto);
            codRef = (TextView) v.findViewById(R.id.txt_codRef);
            view = v;
        }
    }

    private List<Produtos> prod;
    private View v;
    private Activity act;

    public ProdutosAdapter(List<Produtos> produtos, Activity act){
        this.prod = produtos;
        this.act = act;
    }
    @Override
    public ProdutosViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_produtos, parent, false);
        v = itemView;
        return new ProdutosViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ProdutosViewHolder holder, int position) {
    final Produtos produtos = prod.get(position);
        holder.imagem.setBackgroundResource(produtos.getIdImagem());
        holder.txtNome.setText(produtos.getNome());
        holder.txtMarca.setText(produtos.getMarca());
        holder.txtPreco.setText("R$ "+ produtos.getPreco());
        holder.codRef.setText(produtos.getCodReferencia()+"");
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ByteArrayOutputStream bs = new ByteArrayOutputStream();
                holder.imagem.buildDrawingCache();
                Bitmap bmap = holder.imagem.getDrawingCache();
                bmap.compress(Bitmap.CompressFormat.PNG, 50, bs);
                Intent intent = new Intent();
                intent.setClass(act,DetalhesProdutos.class);
                intent.putExtra("nomeProduto",produtos.getNome());
                intent.putExtra("img", bs.toByteArray());
                intent.putExtra("marcaProduto",produtos.getMarca());
                intent.putExtra("precoProduto",produtos.getPreco() + "");
                intent.putExtra("prazoProduto",produtos.getPrazoValidade());
                intent.putExtra("infosProduto",produtos.getInformacoes());
                intent.putExtra("codRef",produtos.getCodReferencia());
                act.startActivity(intent);
                act.finish();
            }
        });
    }

    @Override
    public int getItemCount() {
        return prod.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}
