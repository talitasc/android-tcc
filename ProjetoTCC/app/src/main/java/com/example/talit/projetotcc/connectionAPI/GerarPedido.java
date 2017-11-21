package com.example.talit.projetotcc.connectionAPI;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.talit.projetotcc.R;
import com.example.talit.projetotcc.activities.AlteraDadosConsumidor;
import com.example.talit.projetotcc.activities.DetalhesProdutos;
import com.example.talit.projetotcc.activities.FinalizarCompra;
import com.example.talit.projetotcc.activities.PaginaInicialEstabelecimentos;
import com.example.talit.projetotcc.sqlight.DbConn;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by talit on 20/11/2017.
 */

public class GerarPedido extends AsyncTask<String, String, String> {

    private String carrinho_id;
    private String tipo_entrega_id;
    private String endereco_id;
    private String valor_frete;
    private String forma_pagamento_id;
    private DbConn dbconn;
    private Listener listener;
    public String status = "nao";

    public GerarPedido(Listener listener) {
        this.listener = listener;

    }

    public interface Listener {

        public void onLoaded(String string);
    }

    @Override
    protected String doInBackground(String... n) {

        String api_url = "http://www.mlprojetos.com/webservice/index.php/pedido/gerarPedido";
        carrinho_id = n[0];
        tipo_entrega_id = n[1];
        endereco_id = n[2];
        valor_frete = n[3];
        forma_pagamento_id = n[4];

        HttpURLConnection urlConnection;
        String requestBody;

        try {
            URL url = new URL(api_url);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoOutput(true);
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setRequestProperty("Accept-Encoding", "application/json");
            JSONObject jsonObject = new JSONObject();
            jsonObject.accumulate("carrinho_id", carrinho_id);
            jsonObject.accumulate("tipo_entrega_id", tipo_entrega_id);
            jsonObject.accumulate("endereco_id", endereco_id);
            jsonObject.accumulate("valor_frete", valor_frete);
            jsonObject.accumulate("forma_pagamento_id", forma_pagamento_id);
            //jsonObject.accumulate("token", token);
            String json = jsonObject.toString();
            OutputStream outputStream = new BufferedOutputStream(urlConnection.getOutputStream());
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, "utf-8"));
            writer.write(json);
            writer.flush();
            writer.close();
            outputStream.close();

            InputStream inputStream;
            // get stream
            if (urlConnection.getResponseCode() < HttpURLConnection.HTTP_BAD_REQUEST) {
                inputStream = urlConnection.getInputStream();
            } else {
                inputStream = urlConnection.getErrorStream();
            }
            // parse stream
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String temp, response = "";
            while ((temp = bufferedReader.readLine()) != null) {
                response += temp;
                Log.i("teste_api", response);

            }
            return response;


        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        dbconn = new DbConn(FinalizarCompra.context);
        try {
            JSONObject api_result = new JSONObject(result);
            String response = api_result.getString("response");
            JSONObject status = new JSONObject(response);
            Log.i("Como do Response", response);
            String status_user = status.getString("status");
            String descricao = status.getString("descricao");
            Log.i("Status", status_user);
            if (status_user.equalsIgnoreCase("true")) {

                pedidoGerado();

            } else {
                FinalizarCompra.pbAguardar.setVisibility(View.VISIBLE);
                AlertDialog.Builder builder = new AlertDialog.Builder(FinalizarCompra.act);
                builder.setTitle(R.string.txt_enviado_erro);
                builder.setMessage(R.string.txt_erro_alterados);
                builder.setPositiveButton("Fechar",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        listener.onLoaded("finalizado");
                    }
                });
                builder.setCancelable(false);
                builder.show();
            }

        } catch (Exception e) {
            e.printStackTrace();
            FinalizarCompra.pbAguardar.setVisibility(View.VISIBLE);
            AlertDialog.Builder builder = new AlertDialog.Builder(FinalizarCompra.act);
            builder.setTitle(R.string.txt_enviado_erro);
            builder.setPositiveButton("Fechar",new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    listener.onLoaded("finalizado");
                }
            });
            builder.setCancelable(false);
            builder.show();

        }
    }

    public void pedidoGerado() {

        LayoutInflater inflater = FinalizarCompra.act.getLayoutInflater();
        final View alertLayout = inflater.inflate(R.layout.custom_alert_dialog_avisos, null);
        Button cancelar = (Button) alertLayout.findViewById(R.id.cancelar);
        TextView txtTitulo = (TextView) alertLayout.findViewById(R.id.txt_titulo_aviso);
        TextView txtMsg = (TextView) alertLayout.findViewById(R.id.txt_msg);
        ImageView img = (ImageView) alertLayout.findViewById(R.id.im_aviso);
        AlertDialog.Builder alerta = new AlertDialog.Builder(FinalizarCompra.act);
        alerta.setView(alertLayout);
        alerta.setCancelable(false);
        txtTitulo.setText(R.string.txt_compra_fin_tit);
        txtMsg.setText(R.string.txt_compra_fin_msg);
        img.setImageResource(R.drawable.purchase);

        final AlertDialog dialogo = alerta.create();
        dialogo.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogo.show();
        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogo.dismiss();
                dbconn.deleteSacola();
                listener.onLoaded("finalizado");
            }

        });
    }

}
