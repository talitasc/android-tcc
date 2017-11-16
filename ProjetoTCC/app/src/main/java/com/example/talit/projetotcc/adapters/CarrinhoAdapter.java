package com.example.talit.projetotcc.adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.talit.projetotcc.R;
import com.example.talit.projetotcc.activities.Carrinho;
import com.example.talit.projetotcc.activities.DetalhesProdutos;
import com.example.talit.projetotcc.logicalView.Produtos;
import com.example.talit.projetotcc.logicalView.Sacola;
import com.example.talit.projetotcc.sqlight.DbConn;

import java.io.ByteArrayOutputStream;
import java.util.List;

/**
 * Created by talit on 12/06/2017.
 */

public class CarrinhoAdapter extends BaseAdapter {
    Activity act;
    Context c;
    List<Sacola> prods;
    LayoutInflater inflater;
    private DbConn dbconn;

    public CarrinhoAdapter(Activity act, Context c, List<Sacola> prods){
        this.act = act;
        this.c = c;
        this.prods = prods;
        this.inflater  = LayoutInflater.from(c);
    }
    @Override
    public int getCount() {
        return prods.size();
    }

    @Override
    public Object getItem(int position) {
        return prods.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        final CarrinhoAdapter.ViewHolder holder;

        if(view == null){
            holder = new CarrinhoAdapter.ViewHolder();
            view = this.inflater.inflate(R.layout.card_view_produto_carrinho, parent, false);
            holder.txtNome = (TextView)view.findViewById(R.id.txtNomeProduto);
            holder.txtMarca = (TextView)view.findViewById(R.id.txt_marca_prod);
            holder.txtPreco = (TextView)view.findViewById(R.id.txt_preco);
            holder.imagem = (ImageView)view.findViewById(R.id.im_logo_produto);
            holder.btnExcluir= (Button)view.findViewById(R.id.btnExcluir);
            holder.txtUmed = (TextView)view.findViewById(R.id.txt_unidade_med);
            holder.txtUnidade = (TextView)view.findViewById(R.id.txt_unidade);
            holder.txtPrecoUni = (TextView)view.findViewById(R.id.txt_preco_un);
            holder.txtQtdAum = (TextView)view.findViewById(R.id.txt_qtd);
            holder.btnAumenta = (ImageButton) view.findViewById(R.id.btn_aumenta);
            holder.btnDiminui = (ImageButton) view.findViewById(R.id.btn_diminui);
            //holder.txtCod = (TextView)view.findViewById(R.id.txt_cod);
            view.setTag(holder);

        }
        else{
            holder = (CarrinhoAdapter.ViewHolder) view.getTag();
        }

        final Sacola produtos = prods.get(position);
        dbconn = new DbConn(Carrinho.act);
        holder.txtNome.setText(produtos.getDescrProduto());
        holder.txtMarca.setText(produtos.getMarca());
        holder.txtPreco.setText(String.format("R$ %s", produtos.getPreco()));
        holder.txtUnidade.setText(String.format("%d", produtos.getQuantidade()));
        holder.txtPrecoUni.setText(String.format("R$ %s", produtos.getPreco()));
        holder.txtUmed.setText(produtos.getUnd_med());
        holder.txtQtdAum.setText(String.format("%d", produtos.getQuantidade()));
       // holder.imagem.setImageBitmap(convert(produtos.getImgBase64()));
        //holder.imagem.setBackgroundResource(produtos.getIdImagem());

        final int qtdLote =Integer.parseInt(dbconn.selectIdProduto(produtos.getIdProduto()).getQtdLote());

        holder.btnDiminui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int qtdCampo = Integer.parseInt(holder.txtUnidade.getText().toString());

                if(qtdCampo >= 2){
                    qtdCampo = qtdCampo - 1;
                    holder.txtQtdAum.setText(String.format("%d", qtdCampo));
                    holder.txtUnidade.setText(String.format("%d", qtdCampo));
                    dbconn.updateQtdEpreco(produtos.getIdProduto(),qtdCampo,Double.parseDouble(holder.txtPreco.getText().toString().replace("R$ ","")));
                    Carrinho.txtValorTotal.setText("R$ " + dbconn.totalCarrinho());
                    Carrinho.txtQtd.setText(""+ dbconn.totalItensCarrinho());
                    //holder.txtPreco.setText(String.format("R$ ", dbconn.totalPorItem(produtos.getIdProduto())));

                }
            }
        });
        holder.btnAumenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int qtdCampo = Integer.parseInt(holder.txtUnidade.getText().toString());
                if(qtdCampo <= qtdLote){
                    qtdCampo = qtdCampo+1;
                    holder.txtQtdAum.setText(String.format("%d", qtdCampo));
                    holder.txtUnidade.setText(String.format("%d", qtdCampo));
                    dbconn.updateQtdEpreco(produtos.getIdProduto(),qtdCampo,Double.parseDouble(holder.txtPreco.getText().toString().replace("R$ ","")));
                    Carrinho.txtValorTotal.setText("R$ " + dbconn.totalCarrinho());
                    Carrinho.txtQtd.setText(""+ dbconn.totalItensCarrinho());
                    //holder.txtPreco.setText(String.format("R$ ", dbconn.totalPorItem(produtos.getIdProduto())));
                }
            }
        });

        holder.btnExcluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LayoutInflater inflater = LayoutInflater.from(c);
                final View alertLayout = inflater.inflate(R.layout.custom_alert_dialog_edit_carrinho, null);
                final Button excluir = (Button) alertLayout.findViewById(R.id.btn_excluir);
                final Button editar = (Button) alertLayout.findViewById(R.id.btn_editar);

                AlertDialog.Builder alerta = new AlertDialog.Builder(Carrinho.context);
                alerta.setView(alertLayout);
                alerta.setCancelable(false);
                final AlertDialog dialogo = alerta.create();
                //dialogo.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialogo.show();

                excluir.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        dbconn.deleteCarrinhoId(produtos.getIdProduto());
                        CarrinhoAdapter carAdapter = new CarrinhoAdapter(Carrinho.act, Carrinho.context, dbconn.selectProutos());
                        Carrinho.listas.setAdapter(carAdapter);
                        Carrinho.listas.deferNotifyDataSetChanged();
                        Carrinho.txtValorTotal.setText("R$ " + dbconn.totalCarrinho());
                        Carrinho.txtQtd.setText(""+ dbconn.totalItensCarrinho());

                        if(Carrinho.listas.getCount() == 0){
                            Carrinho.no_list.setVisibility(View.VISIBLE);
                            Carrinho.cardFinal.setVisibility(View.INVISIBLE);
                            Carrinho.btnFinal.setVisibility(View.INVISIBLE);
                        }

                        dialogo.dismiss();
                    }
                });
                editar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        dialogo.dismiss();
                    }
                });

            }
        });

        return view;
    }
    private class ViewHolder {

        private TextView txtNome;
        private TextView txtMarca;
        private TextView txtPreco;
        private ImageView imagem;
        private Button btnExcluir;
        private TextView txtUmed;
        private TextView txtUnidade;
        private TextView txtPrecoUni;
        private TextView txtQtdAum;
        private ImageButton btnAumenta;
        private ImageButton btnDiminui;
    }

    public void mostarOpcoes() {

        LayoutInflater inflater = LayoutInflater.from(c);
        final View alertLayout = inflater.inflate(R.layout.custom_alert_dialog_edit_carrinho, null);
        final Button excluir = (Button) alertLayout.findViewById(R.id.btn_excluir);
        final Button editar = (Button) alertLayout.findViewById(R.id.btn_editar);

        AlertDialog.Builder alerta = new AlertDialog.Builder(Carrinho.context);
        alerta.setView(alertLayout);
        alerta.setCancelable(false);
        final AlertDialog dialogo = alerta.create();
        dialogo.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogo.show();

        excluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialogo.dismiss();
            }
        });
        editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialogo.dismiss();
            }
        });
    }
    public static Bitmap convert(String base64Str) throws IllegalArgumentException
    {
        byte[] decodedBytes = Base64.decode(
                base64Str.substring(base64Str.indexOf(",") + 1),
                Base64.DEFAULT
        );
        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
    }

    public static String convert(Bitmap bitmap)
    {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);

        return Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT);
    }
}
