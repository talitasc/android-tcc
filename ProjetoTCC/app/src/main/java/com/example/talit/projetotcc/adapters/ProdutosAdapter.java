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
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.talit.projetotcc.R;
import com.example.talit.projetotcc.activities.Carrinho;
import com.example.talit.projetotcc.activities.DetalhesProdutos;
import com.example.talit.projetotcc.activities.PaginaInicialEstabelecimentos;
import com.example.talit.projetotcc.connectionAPI.FavoritarProdutos;
import com.example.talit.projetotcc.fragments.TabDestaques;
import com.example.talit.projetotcc.logicalView.Produtos;
import com.example.talit.projetotcc.sqlight.DbConn;
import com.example.talit.projetotcc.utils.Validacoes;
import com.facebook.drawee.view.DraweeView;
import com.facebook.drawee.view.SimpleDraweeView;

import java.io.ByteArrayOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by talit on 11/06/2017.
 */

public class ProdutosAdapter extends RecyclerView.Adapter<ProdutosAdapter.ProdutosViewHolder> {

    private DbConn dbconn;
    public static class ProdutosViewHolder extends RecyclerView.ViewHolder  {

        private TextView txtNome;
        private TextView txtMarca;
        private TextView txtPreco;
        private ImageView imagem;
        private Button btnAdicionar;
        private TextView txtData;
        private ImageButton imgFavoritar;
        private View view;

        public ProdutosViewHolder(View v) {
            super(v);
            txtNome = (TextView)v.findViewById(R.id.txtNomeProduto);
            txtMarca = (TextView)v.findViewById(R.id.txt_marca_prod);
            txtPreco = (TextView)v.findViewById(R.id.txt_preco);
            imagem = (ImageView)v.findViewById(R.id.im_logo_produto);
            txtData= (TextView)v.findViewById(R.id.txt_prazo_validade);
            btnAdicionar = (Button)v.findViewById(R.id.btn_adicionar);
            imgFavoritar = (ImageButton)v.findViewById(R.id.btn_gostei);
            //codRef = (TextView) v.findViewById(R.id.txt_codRef);
            view = v;
        }
    }

    private List<Produtos> prod;
    private View v;
    private Activity act;
    private Context c;
    private String idUser;
    private String tpUser;

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

        holder.txtNome.setText(produtos.getDescricao());
        holder.txtMarca.setText(produtos.getMarcaDescricao());
        holder.txtPreco.setText("R$ "+ produtos.getLote_preco());

        dbconn = new DbConn(PaginaInicialEstabelecimentos.act);
        idUser = dbconn.selectConsumidor().getIdCons()+"";
        tpUser = dbconn.selectConsumidor().getTpAcesso()+"";

        try{
            holder.imagem.setImageBitmap(convert(produtos.getImbase64()));

        }catch (Exception e){
            e.printStackTrace();
            holder.imagem.setImageResource(R.drawable.errorcategoria);
        }
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date data = formato.parse(produtos.getLote_data_vencimento());
            formato.applyPattern("dd/MM/yyyy");
            holder.txtData.setText(formato.format(data).replace("-","/"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //holder.codRef.setText(produtos.getIdProduto()+"");
        holder.btnAdicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Validacoes.showSnackBar(PaginaInicialEstabelecimentos.context, TabDestaques.coordinatorLayout,PaginaInicialEstabelecimentos.context.getResources().getString(R.string.prod_add));
                dbconn.insertSacola(Integer.parseInt(produtos.getIdLote()+""), Integer.parseInt(produtos.getIdLote()+""),
                        produtos.getDescricao(), produtos.getMarcaDescricao(), Double.parseDouble(produtos.getLote_preco().toString().replace("R$", "")), Double.parseDouble(produtos.getLote_preco().toString().replace("R$", "")),produtos.getUnidade_medida_sigla(),
                        1, produtos.getImbase64(),produtos.getLote_quantidade());
            }
        });
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(act,DetalhesProdutos.class);
                intent.putExtra("nomeProduto",produtos.getDescricao());
                intent.putExtra("img", produtos.getImbase64());
                intent.putExtra("marcaProduto",produtos.getMarcaDescricao());
                intent.putExtra("precoProduto",produtos.getLote_preco() + "");
                intent.putExtra("prazoProduto",produtos.getLote_data_vencimento());
                intent.putExtra("infosProduto",produtos.getLote_obs());
                intent.putExtra("categoria",produtos.getCategoria_descr());
                intent.putExtra("quantidade",produtos.getLote_quantidade());
                intent.putExtra("unMed",produtos.getUnidade_medida_sigla());
                intent.putExtra("idLote",produtos.getIdLote()+"");
                act.startActivity(intent);
                act.finish();
            }
        });
        holder.imgFavoritar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FavoritarProdutos connFavoritar = new FavoritarProdutos(null);
                connFavoritar.execute(idUser,tpUser,produtos.getIdProduto()+"","1");
            }
        });
        if(!produtos.getProduto_favorito().equals(null)) {
            if (produtos.getProduto_favorito().equals("1")) {
                holder.imgFavoritar.setImageResource(R.drawable.ic_favorite_black_24dp);
            } else {
                holder.imgFavoritar.setImageResource(R.drawable.ic_favorite_border_black_24dp);
            }
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
