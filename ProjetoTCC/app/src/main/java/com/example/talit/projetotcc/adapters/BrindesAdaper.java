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

public class BrindesAdaper extends RecyclerView.Adapter<BrindesAdaper.BrindesAdaperViewHolder>{

    public static class BrindesAdaperViewHolder extends RecyclerView.ViewHolder  {

        private TextView txtNome;
        private TextView txtMarca;
        private TextView txtPreco;
        private SimpleDraweeView imagem;
        private Button btnAdicionar;
        private TextView txtData;
        private View view;

        public BrindesAdaperViewHolder(View v) {
            super(v);
            txtNome = (TextView)v.findViewById(R.id.txtNomeProduto);
            txtMarca = (TextView)v.findViewById(R.id.txt_marca_prod);
            txtPreco = (TextView)v.findViewById(R.id.txt_preco);
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

    public BrindesAdaper(List<Produtos> produtos, Activity act, Context c){
        this.prod = produtos;
        this.act = act;
        this.c = c;
    }
    @Override
    public BrindesAdaper.BrindesAdaperViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_brindes, parent, false);
        v = itemView;
        return new BrindesAdaper.BrindesAdaperViewHolder (itemView);
    }

    @Override
    public void onBindViewHolder(final BrindesAdaper.BrindesAdaperViewHolder  holder, int position) {
        final Produtos produtos = prod.get(position);
        holder.imagem.setImageBitmap(convert(produtos.getImbase64()));
        holder.txtNome.setText(produtos.getDescricao());
        holder.txtMarca.setText(produtos.getMarcaDescricao());
        holder.txtPreco.setText("SmarketPoints: "+ produtos.getLote_preco());



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
