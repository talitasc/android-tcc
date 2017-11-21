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
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.talit.projetotcc.R;
import com.example.talit.projetotcc.activities.AlteraDadosConsumidor;
import com.example.talit.projetotcc.activities.FinalizarCompra;
import com.example.talit.projetotcc.activities.PaginaInicialEstabelecimentos;
import com.example.talit.projetotcc.connectionAPI.AlteraEndereco;
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
        private TextView cidId;
        private TextView estId;
        private ImageView imageView;
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
            holder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    editarEndereco(listaSuper.getRua(), listaSuper.getNumero(), listaSuper.getBairro(), listaSuper.getCep(), listaSuper.getCidade_descricao(), listaSuper.getEstado_sigla(), listaSuper.getEndereco_id() + "", listaSuper.getCidade_id() + "", listaSuper.getEstado_id() + "", listaSuper.getComplemento());
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

    public  void editarEndereco(String rua, String numero, String bairro, String cep, String cidade, String sigla, final String idEnd, final String idCidade, final String idEstado, String complemento) {

        LayoutInflater inflater = act.getLayoutInflater();
        final View alertLayout = inflater.inflate(R.layout.custom_alert_dialog_edit_endereco, null);
        final Button btnAlterar = (Button) alertLayout.findViewById(R.id.btn_alterar);
        final EditText edtRua = (EditText) alertLayout.findViewById(R.id.ed_rua);
        final EditText edtLocalidade = (EditText) alertLayout.findViewById(R.id.ed_localidade);
        final EditText edtCep = (EditText) alertLayout.findViewById(R.id.ed_cep);
        final EditText edtUf = (EditText) alertLayout.findViewById(R.id.ed_uf);
        final EditText edtBairro = (EditText) alertLayout.findViewById(R.id.ed_bairro);
        final EditText edtNumero = (EditText) alertLayout.findViewById(R.id.ed_numero);
        final EditText edtComplemento = (EditText) alertLayout.findViewById(R.id.ed_complemento);
        final Button cancelar = (Button) alertLayout.findViewById(R.id.cancelar);

        edtRua.setText(rua);
        edtLocalidade.setText(cidade);
        edtCep.setText(cep);
        edtUf.setText(sigla);
        edtBairro.setText(bairro);
        edtNumero.setText(numero);
        edtComplemento.setText(complemento);

        AlertDialog.Builder alerta = new AlertDialog.Builder(AlteraDadosConsumidor.act);
        alerta.setView(alertLayout);
        alerta.setCancelable(false);
        final AlertDialog dialogo = alerta.create();
        dialogo.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogo.show();
        btnAlterar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogo.dismiss();
                AlteraEndereco conn = new AlteraEndereco();
                conn.execute(idEnd,edtRua.getText().toString(),edtNumero.getText().toString(),
                        edtComplemento.getText().toString(),edtBairro.getText().toString(),
                        edtCep.getText().toString(),idCidade,idEstado);

                //ListaSupermercadoPoRaio connRaio = new ListaSupermercadoPoRaio(null);
                //connRaio.execute(String.format("%s", latitude), String.format("%s", longitude), raio);


            }
        });

        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogo.dismiss();
            }

        });
    }
}
