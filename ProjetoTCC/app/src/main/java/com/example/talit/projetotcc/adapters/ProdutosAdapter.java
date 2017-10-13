package com.example.talit.projetotcc.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
import com.example.talit.projetotcc.activities.DetalhesProdutos;
import com.example.talit.projetotcc.logicalView.Produtos;
import com.facebook.drawee.view.DraweeView;
import com.facebook.drawee.view.SimpleDraweeView;

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
        private SimpleDraweeView imagem;
        private TextView codRef;
        private View view;

        public ProdutosViewHolder(View v) {
            super(v);
            txtNome = (TextView)v.findViewById(R.id.txtNomeProduto);
            txtMarca = (TextView)v.findViewById(R.id.txt_marca_prod);
            txtPreco = (TextView)v.findViewById(R.id.txt_preco);
            imagem = (SimpleDraweeView)v.findViewById(R.id.im_logo_produto);
            codRef = (TextView) v.findViewById(R.id.txt_codRef);
            view = v;
        }
    }

    private List<Produtos> prod;
    private View v;
    private Activity act;
    private Context c;

    public ProdutosAdapter(List<Produtos> produtos, Activity act, Context c){
        this.prod = produtos;
        this.act = act;
        this.c = c;
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
        holder.imagem.setImageBitmap(convert(produtos.getImbase64()));
        holder.txtNome.setText(produtos.getDescricao());
        holder.txtMarca.setText(produtos.getMarcaDescricao());
        holder.txtPreco.setText("R$ "+ produtos.getLote_preco());
        holder.codRef.setText(produtos.getIdProduto()+"");
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ByteArrayOutputStream bs = new ByteArrayOutputStream();
                holder.imagem.buildDrawingCache();
                Bitmap bmap = holder.imagem.getDrawingCache();
                bmap.compress(Bitmap.CompressFormat.PNG, 50, bs);
                Intent intent = new Intent();
                intent.setClass(act,DetalhesProdutos.class);
                intent.putExtra("nomeProduto",produtos.getDescricao());
                intent.putExtra("img", produtos.getImbase64());
                intent.putExtra("marcaProduto",produtos.getMarcaDescricao());
                intent.putExtra("precoProduto",produtos.getLote_preco() + "");
                intent.putExtra("prazoProduto",produtos.getLote_data_vencimento());
                intent.putExtra("infosProduto",produtos.getLote_obs());
                intent.putExtra("codRef",produtos.getIdProduto());
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
