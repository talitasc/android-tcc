package com.example.talit.projetotcc.adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
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
import com.example.talit.projetotcc.connectionAPI.AlteraEndereco;
import com.example.talit.projetotcc.logicalView.Endereco;
import com.example.talit.projetotcc.logicalView.Pedido;

import java.io.ByteArrayOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by talit on 21/11/2017.
 */

public class PedidoAdapter extends RecyclerView.Adapter<PedidoAdapter.PedidoAdapterViewHolder> {


    public static class PedidoAdapterViewHolder extends RecyclerView.ViewHolder {
        private TextView numero;
        private TextView valor;
        private TextView data;
        private TextView status;
        private ImageView imageView;
        private View view;

        public PedidoAdapterViewHolder(View v) {
            super(v);
            numero = (TextView) v.findViewById(R.id.numeroPedido);
            valor = (TextView) v.findViewById(R.id.valorPedido);
            data = (TextView) v.findViewById(R.id.data_pedido);
            status = (TextView) v.findViewById(R.id.status);
            imageView = (ImageView) v.findViewById(R.id.im_edt);
            view = v;
        }
    }

    private Activity act;
    private Context c;
    private View v;
    private List<Pedido> listaSupermercado;
    private LayoutInflater inflater;

    public PedidoAdapter(Activity act, Context c, List<Pedido> listaSupermercado) {
        this.act = act;
        this.c = c;
        this.listaSupermercado = listaSupermercado;
        this.inflater = LayoutInflater.from(c);
    }

    public PedidoAdapter.PedidoAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_pedidos, parent, false);
        v = itemView;
        return new PedidoAdapter.PedidoAdapterViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final PedidoAdapter.PedidoAdapterViewHolder holder, int position) {


        final Pedido listaSuper = listaSupermercado.get(position);
        holder.numero.setText(listaSuper.getPedido_id());
        holder.valor.setText(listaSuper.getPedido_valor());
        holder.status.setText(listaSuper.getStatus_pedido_descricao());

        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date data = formato.parse(listaSuper.getPedido_data());
            formato.applyPattern("dd/MM/yyyy");
            holder.data.setText(formato.format(data).replace("-", "/"));
        } catch (ParseException e) {
            e.printStackTrace();
            holder.data.setText("21/11/2017");
        }
        try {
            if (Integer.parseInt(listaSuper.getPedido_status_id()) == 6){
                holder.imageView.setVisibility(View.VISIBLE);
            }else{
                holder.imageView.setVisibility(View.INVISIBLE);
            }
        }catch (Exception e){
            e.printStackTrace();
            holder.imageView.setVisibility(View.INVISIBLE);
        }
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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

    public void editarEndereco(String rua, String numero, String bairro, String cep, String cidade, String sigla, final String idEnd, final String idCidade, final String idEstado, String complemento) {

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
                conn.execute(idEnd, edtRua.getText().toString(), edtNumero.getText().toString(),
                        edtComplemento.getText().toString(), edtBairro.getText().toString(),
                        edtCep.getText().toString(), idCidade, idEstado);

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
