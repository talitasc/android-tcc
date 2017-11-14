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
import android.widget.TextView;

import com.example.talit.projetotcc.R;
import com.example.talit.projetotcc.activities.DetalhesProdutos;
import com.example.talit.projetotcc.activities.PaginaInicialEstabelecimentos;
import com.example.talit.projetotcc.fragments.TabDestaques;
import com.example.talit.projetotcc.logicalView.Produtos;
import com.example.talit.projetotcc.sqlight.DbConn;
import com.example.talit.projetotcc.utils.Validacoes;
import com.facebook.drawee.view.SimpleDraweeView;

import java.io.ByteArrayOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by talit on 12/11/2017.
 */

public class BuscaProdAdapter extends RecyclerView.Adapter<BuscaProdAdapter.BuscaProdViewHolder> {

    private DbConn dbconn;

    public static class BuscaProdViewHolder extends RecyclerView.ViewHolder  {

        private TextView txtNome;
        private TextView txtMarca;
        private TextView txtPreco;
        private TextView txtCategoria;
        private SimpleDraweeView imagem;
        private Button btnAdicionar;
        private TextView txtData;
        private View view;

        public BuscaProdViewHolder(View v) {
            super(v);
            txtNome = (TextView)v.findViewById(R.id.txtNomeProduto);
            txtMarca = (TextView)v.findViewById(R.id.txt_marca_prod);
            txtPreco = (TextView)v.findViewById(R.id.txt_preco);
            txtCategoria = (TextView)v.findViewById(R.id.txt_categoria);
            imagem = (SimpleDraweeView)v.findViewById(R.id.im_logo_produto);
            txtData= (TextView)v.findViewById(R.id.txt_prazo_validade);
            btnAdicionar = (Button)v.findViewById(R.id.btn_adicionar);
            //codRef = (TextView) v.findViewById(R.id.txt_codRef);
            view = v;
        }
    }

    private List<Produtos> prod;
    private View v;
    private Activity act;
    private Context c;

    public BuscaProdAdapter(List<Produtos> produtos, Activity act, Context c){
        this.prod = produtos;
        this.act = act;
        this.c = c;
    }
    @Override
    public BuscaProdAdapter.BuscaProdViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_prods_busca, parent, false);
        v = itemView;
        return new BuscaProdAdapter.BuscaProdViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final BuscaProdAdapter.BuscaProdViewHolder holder, int position) {
        final Produtos produtos = prod.get(position);
        holder.imagem.setImageBitmap(convert(produtos.getImbase64()));
        holder.txtNome.setText(produtos.getDescricao());
        holder.txtMarca.setText(produtos.getMarcaDescricao());
        holder.txtCategoria.setText(produtos.getCategoria_descr());
        holder.txtPreco.setText("R$ "+ produtos.getLote_preco());

        dbconn = new DbConn(PaginaInicialEstabelecimentos.act);

        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date data = formato.parse(produtos.getLote_data_vencimento());
            formato.applyPattern("dd/MM/yyyy");
            holder.txtData.setText(formato.format(data).replace("-","/"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
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
