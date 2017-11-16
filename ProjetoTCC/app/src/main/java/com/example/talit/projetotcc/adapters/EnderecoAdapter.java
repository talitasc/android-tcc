package com.example.talit.projetotcc.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.talit.projetotcc.R;
import com.example.talit.projetotcc.activities.FinalizarCompra;
import com.example.talit.projetotcc.activities.PaginaInicialEstabelecimentos;
import com.example.talit.projetotcc.fragments.DetalhesEstab;
import com.example.talit.projetotcc.logicalView.Endereco;
import com.example.talit.projetotcc.logicalView.Estabelecimento;
import com.facebook.drawee.view.SimpleDraweeView;

import java.io.ByteArrayOutputStream;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by talit on 14/11/2017.
 */

public class EnderecoAdapter extends RecyclerView.Adapter<EnderecoAdapter.EnderecoAdapterViewHolder> {

    public static class EnderecoAdapterViewHolder extends RecyclerView.ViewHolder {
        private TextView rua;
        private TextView numero;
        private TextView bairro;
        private TextView cep;
        private TextView cidade;
        private TextView sigla;
        private TextView endId;
        private View view;

        public EnderecoAdapterViewHolder(View v) {
            super(v);
            rua = (TextView) v.findViewById(R.id.rua);
            numero = (TextView) v.findViewById(R.id.numero);
            bairro = (TextView) v.findViewById(R.id.bairro);
            cep = (TextView) v.findViewById(R.id.cep);
            cidade = (TextView) v.findViewById(R.id.cidade);
            sigla = (TextView) v.findViewById(R.id.sigla);
            endId = (TextView) v.findViewById(R.id.idEnd);
            view = v;
        }
    }

    private Activity act;
    private Context c;
    private View v;
    private List<Endereco> listaSupermercado;
    private LayoutInflater inflater;

    public EnderecoAdapter(Activity act, Context c, List<Endereco> listaSupermercado) {
        this.act = act;
        this.c = c;
        this.listaSupermercado = listaSupermercado;
        this.inflater = LayoutInflater.from(c);
    }

    public EnderecoAdapter.EnderecoAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_endereco, parent, false);
        v = itemView;
        return new EnderecoAdapter.EnderecoAdapterViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final EnderecoAdapter.EnderecoAdapterViewHolder holder, int position) {
        final Endereco listaSuper = listaSupermercado.get(position);

        holder.rua.setText(listaSuper.getRua());
        holder.numero.setText(listaSuper.getNumero());
        holder.bairro.setText(listaSuper.getBairro());
        holder.cep.setText(listaSuper.getCep());
        holder.cidade.setText(listaSuper.getCidade_descricao());
        holder.sigla.setText(listaSuper.getEstado_sigla());
        holder.endId.setText(listaSuper.getEndereco_id());


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
