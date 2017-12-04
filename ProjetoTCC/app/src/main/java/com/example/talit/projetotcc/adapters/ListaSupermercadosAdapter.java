package com.example.talit.projetotcc.adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.talit.projetotcc.R;
import com.example.talit.projetotcc.activities.DetalhesProdutos;
import com.example.talit.projetotcc.activities.FinalizarCompra;
import com.example.talit.projetotcc.activities.PaginaInicialEstabelecimentos;
import com.example.talit.projetotcc.activities.PaginalnicialConsumidor;
import com.example.talit.projetotcc.activities.Pedidos;
import com.example.talit.projetotcc.connectionAPI.Avaliar;
import com.example.talit.projetotcc.connectionAPI.FormasPagamento;
import com.example.talit.projetotcc.fragments.DetalhesEstab;
import com.example.talit.projetotcc.logicalView.Estabelecimento;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.DraweeHolder;
import com.facebook.drawee.view.DraweeView;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.google.android.gms.vision.text.Text;

import java.io.ByteArrayOutputStream;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by talit on 23/04/2017.
 */

public class ListaSupermercadosAdapter extends RecyclerView.Adapter<ListaSupermercadosAdapter.ListarSupermercadoViewHolder> {

    public static class ListarSupermercadoViewHolder extends RecyclerView.ViewHolder {
        private TextView nome_supermercado;
        private TextView nome_cidade;
        private ImageView imLogo;
        private ImageButton imPgamento;
        private RatingBar ratingBar;
        private TextView mediaNota;
        private View view;

        public ListarSupermercadoViewHolder(View v) {
            super(v);
            nome_supermercado = (TextView) v.findViewById(R.id.txt_nome_super);
            nome_cidade = (TextView) v.findViewById(R.id.txt_cidade_estab);
            imLogo = (ImageView) v.findViewById(R.id.im_logo_supermercado);
            ratingBar = (RatingBar) v.findViewById(R.id.ratingBarEstab);
            mediaNota = (TextView) v.findViewById(R.id.txt_avaliacao);
            imPgamento = (ImageButton)v.findViewById(R.id.btn_frete);
            view = v;
        }
    }

    public static Activity act;
    public static Context c;
    private View v;
    private List<Estabelecimento> listaSupermercado;
    private LayoutInflater inflater;
    public static  RecyclerView recPagamento;
    public static ProgressBar progressBar;

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
    public void onBindViewHolder(ListarSupermercadoViewHolder holder, int position) {

        final Estabelecimento listaSuper = listaSupermercado.get(position);
        holder.nome_supermercado.setText(listaSuper.getNome_fantasia());
        holder.nome_cidade.setText(listaSuper.getCidade());

        try{
            holder.ratingBar.setRating(Float.parseFloat(listaSuper.getNota()));
        }catch(Exception e){
            e.printStackTrace();
            holder.ratingBar.setRating(4);
        }

        try{
            if(listaSuper.getNota() != null && !listaSuper.getNota().equals("null")) {
                holder.mediaNota.setText(listaSuper.getNota());
            }else{
                holder.mediaNota.setText("4");
            }
        }catch(Exception e){
            e.printStackTrace();
            holder.mediaNota.setText("4");
        }


        try {
            holder.imLogo.setImageBitmap(convert(listaSuper.getImagem()));
        } catch (Exception e) {
            e.printStackTrace();
            holder.imLogo.setImageResource(R.drawable.mercado);
        }

        //holder.nome_cidade.setText(listaSuper.getCidade());
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Estabelecimento estabelecimento = new Estabelecimento(listaSuper.getId(),
                        listaSuper.getCnpj(),
                        listaSuper.getRazao_social(),
                        listaSuper.getNome_fantasia(),
                        listaSuper.getInscricao_estadual(),
                        listaSuper.getInscricao_municipal(),
                        listaSuper.getEstab_vendedor(),
                        listaSuper.getTipo_estab_desc(),
                        listaSuper.getEmail(),
                        listaSuper.getRua(),
                        listaSuper.getNumero(),
                        listaSuper.getBairro(),
                        listaSuper.getComplemento(),
                        listaSuper.getCep(),
                        listaSuper.getCidade(),
                        listaSuper.getEstado_sigla(),
                        listaSuper.getTpTel(),
                        listaSuper.getDd(),
                        listaSuper.getTelefone(), "ajustar", "ajsutar", "ajustar");
                //Toast.makeText(act, estabelecimento.getNomeFantasia(), Toast.LENGTH_SHORT).show();
                PaginaInicialEstabelecimentos.nomeEstab = estabelecimento.getNome_fantasia();

                try {
                    SharedPreferences.Editor editor = act.getSharedPreferences("ID", MODE_PRIVATE).edit();
                    editor.putString("idEstab", String.format("%d", estabelecimento.getId()));
                    editor.commit();
                }catch(Exception e){
                    e.printStackTrace();
                    SharedPreferences.Editor editor = act.getSharedPreferences("ID", MODE_PRIVATE).edit();
                    editor.putString("idEstab", "3");
                    editor.commit();
                }

                DetalhesEstab.strNomeFantasia = estabelecimento.getNome_fantasia();
                DetalhesEstab.strRua = estabelecimento.getRua();
                DetalhesEstab.strNumero = estabelecimento.getNumero() + "";
                DetalhesEstab.strbairro = estabelecimento.getBairro();
                DetalhesEstab.strCep = estabelecimento.getCep();
                DetalhesEstab.strCidade = estabelecimento.getCidade();
                DetalhesEstab.strSigla = estabelecimento.getEstado_sigla();
                DetalhesEstab.strDdd = estabelecimento.getDd();
                DetalhesEstab.strTelefone = estabelecimento.getTelefone();
                DetalhesEstab.strEmail = estabelecimento.getEmail();
                DetalhesEstab.strIdEstab = String.format("%d", estabelecimento.getId());
                FinalizarCompra.strRua = estabelecimento.getRua();
                FinalizarCompra.strBairro = estabelecimento.getBairro();
                FinalizarCompra.strNumero = estabelecimento.getNumero() + "";
                FinalizarCompra.strUf = estabelecimento.getEstado_sigla();
                FinalizarCompra.strCep = estabelecimento.getCep();
                FinalizarCompra.strCidade = estabelecimento.getCidade();

                act.startActivity(new Intent(act, PaginaInicialEstabelecimentos.class));
                act.finishActivity(0);
            }
        });
        holder.imPgamento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                formasDePagamento(listaSuper.getId()+"");
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

    public void formasDePagamento(String idEstab) {

        LayoutInflater inflater = act.getLayoutInflater();
        final View alertLayout = inflater.inflate(R.layout.custom_alerta_dialog_formas_pagamento, null);
        final Button cancelar = (Button) alertLayout.findViewById(R.id.cancelar);
        recPagamento = (RecyclerView)alertLayout.findViewById(R.id.lv_pagamento);
        progressBar = (ProgressBar)alertLayout.findViewById(R.id.pb_pagamento);

        recPagamento.setHasFixedSize(true);
        StaggeredGridLayoutManager llm = new StaggeredGridLayoutManager(1, 1);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recPagamento.setLayoutManager(llm);

        FormasPagamento connFormas = new FormasPagamento(null);
        connFormas.execute(idEstab);
        AlertDialog.Builder alerta = new AlertDialog.Builder(PaginalnicialConsumidor.act);
        alerta.setView(alertLayout);
        alerta.setCancelable(false);
        final AlertDialog dialogo = alerta.create();
        dialogo.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogo.show();


        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogo.dismiss();
            }

        });
    }
}
