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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.talit.projetotcc.R;
import com.example.talit.projetotcc.activities.PaginaInicialEstabelecimentos;
import com.example.talit.projetotcc.activities.ProdutosFavoritados;
import com.example.talit.projetotcc.connectionAPI.Desfavoritar;
import com.example.talit.projetotcc.connectionAPI.FavoritarProdutos;
import com.example.talit.projetotcc.logicalView.FormasDePagamento;
import com.example.talit.projetotcc.logicalView.ProdutosFavoritos;
import com.example.talit.projetotcc.sqlight.DbConn;

import java.io.ByteArrayOutputStream;
import java.util.List;

/**
 * Created by talit on 02/12/2017.
 */

public class ProdutosFavoritosAdapter extends RecyclerView.Adapter<ProdutosFavoritosAdapter.ProdutosFavoritosAdapterViewHolder> {

    private DbConn dbconn;

    public static class ProdutosFavoritosAdapterViewHolder extends RecyclerView.ViewHolder {
        private TextView nomeProduto;
        private TextView marca;
        private TextView categoria;
        private TextView estabelecimento;
        private ImageView imProduo;
        private ImageButton imDesfavoritar;
        private RelativeLayout relativeDisponivel;
        private View view;

        public ProdutosFavoritosAdapterViewHolder(View v) {
            super(v);
            nomeProduto = (TextView) v.findViewById(R.id.txtNomeProduto);
            marca = (TextView) v.findViewById(R.id.txtMarca);
            categoria = (TextView) v.findViewById(R.id.txtCategoria);
            estabelecimento = (TextView) v.findViewById(R.id.txtLoja);
            imProduo = (ImageView) v.findViewById(R.id.im_logo_produto);
            relativeDisponivel = (RelativeLayout)v.findViewById(R.id.notificacoes);
            imDesfavoritar = (ImageButton)v.findViewById(R.id.btn_desvaforitar);
            view = v;
        }
    }

    private Activity act;
    private Context c;
    private View v;
    private List<ProdutosFavoritos> listarProdFavoritos;
    private LayoutInflater inflater;
    private String idUser;
    private String tpUser;

    public ProdutosFavoritosAdapter(Activity act, Context c, List<ProdutosFavoritos> listarProdFavoritos) {
        this.act = act;
        this.c = c;
        this.listarProdFavoritos = listarProdFavoritos;
        this.inflater = LayoutInflater.from(c);
    }

    public ProdutosFavoritosAdapter.ProdutosFavoritosAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_prod_favoritos, parent, false);
        v = itemView;
        return new  ProdutosFavoritosAdapter.ProdutosFavoritosAdapterViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ProdutosFavoritosAdapter.ProdutosFavoritosAdapterViewHolder holder, int position) {

        final ProdutosFavoritos listaForma = listarProdFavoritos.get(position);
        holder.nomeProduto.setText(listaForma.getProduto_descricao());
        holder.marca.setText(listaForma.getMarca_descricao());
        holder.categoria.setText(listaForma.getSub_categoria_descricao());
        holder.estabelecimento.setText(listaForma.getEstabelecimento_nome_fantasia());
        try{
            holder.imProduo.setImageBitmap(convert(listaForma.getProduto_img_b64()));

        }catch (Exception e){
            e.printStackTrace();
            holder.imProduo.setImageResource(R.drawable.errorcategoria);
        }
        if(listaForma.getDisponivel() == 1){
            holder.relativeDisponivel.setVisibility(View.VISIBLE);
        }

        dbconn = new DbConn(ProdutosFavoritados.act);
        idUser = dbconn.selectConsumidor().getIdCons()+"";
        tpUser = dbconn.selectConsumidor().getTpAcesso()+"";

        holder.imDesfavoritar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Desfavoritar connDesfavoritar = new Desfavoritar(null);
                connDesfavoritar.execute(idUser,tpUser,listaForma.getProduto_id()+"","0");
            }
        });
    }

    @Override
    public int getItemCount() {

        try {
            return listarProdFavoritos.size();
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
