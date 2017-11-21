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
import android.widget.ImageView;
import android.widget.TextView;

import com.example.talit.projetotcc.R;
import com.example.talit.projetotcc.activities.FinalizarCompra;
import com.example.talit.projetotcc.logicalView.Endereco;

import java.io.ByteArrayOutputStream;
import java.util.List;

/**
 * Created by talit on 21/11/2017.
 */

public class EnderecosFinalizarCompraAdapter extends RecyclerView.Adapter<EnderecosFinalizarCompraAdapter.EnderecosFinalizarCompraHolder> {


    public static class  EnderecosFinalizarCompraHolder extends RecyclerView.ViewHolder {
        private TextView rua;
        private TextView numero;
        private TextView bairro;
        private TextView cep;
        private TextView cidade;
        private TextView sigla;
        private TextView endId;
        private TextView cidId;
        private TextView estId;
        private ImageView imageView;
        private View view;

        public EnderecosFinalizarCompraHolder(View v) {
            super(v);
            rua = (TextView) v.findViewById(R.id.rua);
            numero = (TextView) v.findViewById(R.id.numero);
            bairro = (TextView) v.findViewById(R.id.bairro);
            cep = (TextView) v.findViewById(R.id.cep);
            cidade = (TextView) v.findViewById(R.id.cidade);
            sigla = (TextView) v.findViewById(R.id.sigla);
            endId = (TextView) v.findViewById(R.id.idEnd);
            cidId = (TextView)v.findViewById(R.id.cidade_id);
            estId = (TextView)v.findViewById(R.id.estado_id);
            imageView = (ImageView)v.findViewById(R.id.im_edt);
            view = v;
        }
    }

    private Activity act;
    private Context c;
    private View v;
    private List<Endereco> listaSupermercado;
    private LayoutInflater inflater;

    public EnderecosFinalizarCompraAdapter(Activity act, Context c, List<Endereco> listaSupermercado) {
        this.act = act;
        this.c = c;
        this.listaSupermercado = listaSupermercado;
        this.inflater = LayoutInflater.from(c);
    }

    public EnderecosFinalizarCompraAdapter.EnderecosFinalizarCompraHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_endereco, parent, false);
        v = itemView;
        return new EnderecosFinalizarCompraAdapter.EnderecosFinalizarCompraHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final EnderecosFinalizarCompraAdapter.EnderecosFinalizarCompraHolder holder, int position) {

        try {
            final Endereco listaSuper = listaSupermercado.get(position);

            holder.rua.setText(listaSuper.getRua());
            holder.numero.setText(listaSuper.getNumero());
            holder.bairro.setText(listaSuper.getBairro());
            holder.cep.setText(listaSuper.getCep());
            holder.cidade.setText(listaSuper.getCidade_descricao());
            holder.sigla.setText(listaSuper.getEstado_sigla());
            holder.endId.setText(listaSuper.getEndereco_id() + "");
            holder.cidId.setText(listaSuper.getCidade_id() + "");
            holder.estId.setText(listaSuper.getEstado_id() + "");
            holder.imageView.setImageResource(R.drawable.ic_add_circle_black_24dp);
            holder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FinalizarCompra.idEstabelecimento = listaSuper.getEndereco_id()+"";
                }
            });
        }catch (Exception e){
            e.printStackTrace();
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
