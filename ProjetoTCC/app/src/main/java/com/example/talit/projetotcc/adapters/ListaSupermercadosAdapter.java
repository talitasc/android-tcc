package com.example.talit.projetotcc.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.talit.projetotcc.R;
import com.example.talit.projetotcc.activities.DetalhesProdutos;
import com.example.talit.projetotcc.activities.PaginaInicialEstabelecimentos;
import com.example.talit.projetotcc.fragments.DetalhesEstab;
import com.example.talit.projetotcc.logicalView.Estabelecimento;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.DraweeHolder;
import com.facebook.drawee.view.DraweeView;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

import java.io.ByteArrayOutputStream;
import java.util.List;

/**
 * Created by talit on 23/04/2017.
 */

public class ListaSupermercadosAdapter extends RecyclerView.Adapter<ListaSupermercadosAdapter.ListarSupermercadoViewHolder> {

    public static class ListarSupermercadoViewHolder extends RecyclerView.ViewHolder {
        private TextView nome_supermercado;
        private TextView nome_cidade;
        private SimpleDraweeView imLogo;
        private View view;

        public ListarSupermercadoViewHolder(View v) {
            super(v);
            nome_supermercado = (TextView) v.findViewById(R.id.txt_nome_super);
            nome_cidade = (TextView) v.findViewById(R.id.txt_cidade_estab);
            imLogo = (SimpleDraweeView) v.findViewById(R.id.im_logo_supermercado);
            view = v;
        }
    }

    private Activity act;
    private Context c;
    private View v;
    private List<Estabelecimento> listaSupermercado;
    private LayoutInflater inflater;

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
    public void onBindViewHolder(final ListarSupermercadoViewHolder holder, int position) {
        final Estabelecimento listaSuper = listaSupermercado.get(position);
        holder.nome_supermercado.setText(listaSuper.getNome_fantasia());
        holder.nome_cidade.setText(listaSuper.getCidade());
        //holder.imLogo.setImageBitmap(convert(act.getResources().getString(R.string.teste_base64)));

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
                        listaSuper.getTelefone());
                //Toast.makeText(act, estabelecimento.getNomeFantasia(), Toast.LENGTH_SHORT).show();
                PaginaInicialEstabelecimentos.nomeEstab = estabelecimento.getNome_fantasia();
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
                act.startActivity(new Intent(act, PaginaInicialEstabelecimentos.class));
                act.finishActivity(0);
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

}
