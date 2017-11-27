package com.example.talit.projetotcc.adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
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
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.talit.projetotcc.R;
import com.example.talit.projetotcc.activities.AlteraDadosConsumidor;
import com.example.talit.projetotcc.activities.Carrinho;
import com.example.talit.projetotcc.activities.Pedidos;
import com.example.talit.projetotcc.connectionAPI.AlteraEndereco;
import com.example.talit.projetotcc.connectionAPI.Avaliar;
import com.example.talit.projetotcc.logicalView.Endereco;
import com.example.talit.projetotcc.logicalView.Pedido;
import com.example.talit.projetotcc.sqlight.DbConn;

import java.io.ByteArrayOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;
import static com.example.talit.projetotcc.activities.AlteraDadosConsumidor.idEstado;

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
        private TextView idEstabelecimento;
        private View view;

        public PedidoAdapterViewHolder(View v) {
            super(v);
            numero = (TextView) v.findViewById(R.id.numeroPedido);
            valor = (TextView) v.findViewById(R.id.valorPedido);
            data = (TextView) v.findViewById(R.id.data_pedido);
            status = (TextView) v.findViewById(R.id.status);
            imageView = (ImageView) v.findViewById(R.id.im_edt);
            idEstabelecimento = (TextView)v.findViewById(R.id.idEstab);
            view = v;
        }
    }

    private Activity act;
    private Context c;
    private View v;
    private List<Pedido> listaSupermercado;
    private LayoutInflater inflater;
    private DbConn dbconn;


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

        dbconn = new DbConn(Pedidos.act);
        holder.numero.setText("Numero do pedido: "+listaSuper.getPedido_id());
        holder.valor.setText("R$" + listaSuper.getPedido_valor());
        holder.status.setText("Status do pedido: " +listaSuper.getStatus_pedido_descricao());
        holder.idEstabelecimento.setText(listaSuper.getEstabelecimento_id());
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date data = formato.parse(listaSuper.getPedido_data());
            formato.applyPattern("dd/MM/yyyy");
            holder.data.setText("Data do pedido: " + formato.format(data).replace("-", "/"));
        } catch (ParseException e) {
            e.printStackTrace();
            holder.data.setText("21/11/2017");
        }
        try {
            if (Integer.parseInt(listaSuper.getPedido_status_id()) != 6){
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
                enviarAvaliacao(listaSuper.getEstabelecimento_id()+"");
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

    public void enviarAvaliacao(final String idEstab) {

        LayoutInflater inflater = act.getLayoutInflater();
        final View alertLayout = inflater.inflate(R.layout.custom_alerta_dialog_avaliacao, null);
        final Button btnEnviar = (Button) alertLayout.findViewById(R.id.btn_alterar);
        final EditText edtRua = (EditText) alertLayout.findViewById(R.id.ed_rua);
        final EditText edtComentario = (EditText) alertLayout.findViewById(R.id.ed_comentario);
        final RatingBar ratingBar = (RatingBar)alertLayout.findViewById(R.id.ratingBar2);
        final Button cancelar = (Button) alertLayout.findViewById(R.id.cancelar);

        AlertDialog.Builder alerta = new AlertDialog.Builder(Pedidos.act);
        alerta.setView(alertLayout);
        alerta.setCancelable(false);
        final AlertDialog dialogo = alerta.create();
        dialogo.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogo.show();

        final String idUser = dbconn.selectConsumidor().getIdCons()+"";
        final String tpUser = dbconn.selectConsumidor().getTpAcesso()+"";

        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogo.dismiss();
                Avaliar connAva = new Avaliar();
                connAva.execute(idUser,tpUser,idEstab,String.valueOf(ratingBar.getRating()),edtComentario.getText().toString());

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
