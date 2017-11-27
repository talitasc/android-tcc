package com.example.talit.projetotcc.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.talit.projetotcc.R;
import com.example.talit.projetotcc.activities.FinalizarCompra;
import com.example.talit.projetotcc.activities.PaginaInicialEstabelecimentos;
import com.example.talit.projetotcc.fragments.DetalhesEstab;
import com.example.talit.projetotcc.logicalView.Estabelecimento;
import com.example.talit.projetotcc.logicalView.EstabelecimentoFullText;
import com.facebook.drawee.view.SimpleDraweeView;

import java.io.ByteArrayOutputStream;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by talit on 11/11/2017.
 */

public class FulltextEstabelecimentoAdapter extends RecyclerView.Adapter<FulltextEstabelecimentoAdapter.FulltextEstabelecimentoViewHolder>{


    public static class FulltextEstabelecimentoViewHolder extends RecyclerView.ViewHolder {
        private TextView nome_supermercado;
        private TextView nome_cidade;
        private ImageView imLogo;
        private TextView nota;
        private View view;

        public FulltextEstabelecimentoViewHolder(View v) {
            super(v);
            nome_supermercado = (TextView) v.findViewById(R.id.txt_nome_super);
            nome_cidade = (TextView) v.findViewById(R.id.txt_cidade_estab);
            imLogo = (ImageView) v.findViewById(R.id.im_logo_supermercado);
            nota = (TextView)v.findViewById(R.id.txt_avaliacao);
            view = v;
        }
    }

    private Activity act;
    private Context c;
    private View v;
    private List<EstabelecimentoFullText> listaSupermercado;
    private LayoutInflater inflater;

    public FulltextEstabelecimentoAdapter(Activity act, Context c, List<EstabelecimentoFullText> listaSupermercado) {
        this.act = act;
        this.c = c;
        this.listaSupermercado = listaSupermercado;
        this.inflater = LayoutInflater.from(c);
    }

    public FulltextEstabelecimentoAdapter.FulltextEstabelecimentoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_estabeelcimento_busca, parent, false);
        v = itemView;
        return new FulltextEstabelecimentoAdapter.FulltextEstabelecimentoViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final FulltextEstabelecimentoAdapter.FulltextEstabelecimentoViewHolder holder, int position) {
        final EstabelecimentoFullText listaSuper = listaSupermercado.get(position);
        holder.nome_supermercado.setText(listaSuper.getEstabelecimento_nome_fantasia());
        holder.nome_cidade.setText("campinas");
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(act, estabelecimento.getNomeFantasia(), Toast.LENGTH_SHORT).show();
                PaginaInicialEstabelecimentos.nomeEstab = listaSuper.getEstabelecimento_nome_fantasia();

                SharedPreferences.Editor editor = act.getSharedPreferences("ID", MODE_PRIVATE).edit();
                editor.putString("idEstab", String.format("%d", listaSuper.getEstabelecimento_id()));
                editor.commit();

                act.startActivity(new Intent(act, PaginaInicialEstabelecimentos.class));
                act.finishActivity(0);
            }
        });

        try {
            holder.imLogo.setImageBitmap(convert(listaSuper.getImage64()));
        } catch (Exception e) {
            e.printStackTrace();
            holder.imLogo.setImageResource(R.drawable.mercado);
        }

        try{
            if(listaSuper.getNota() != null && !listaSuper.getNota().equals("null")) {
                holder.nota.setText(listaSuper.getNota());
            }else{
                holder.nota.setText("4");
            }
        }catch(Exception e){
            e.printStackTrace();
            holder.nota.setText("4");
        }

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

}
