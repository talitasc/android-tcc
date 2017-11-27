package com.example.talit.projetotcc.connectionAPI;

import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

import com.example.talit.projetotcc.activities.Carrinho;
import com.example.talit.projetotcc.activities.Pedidos;
import com.example.talit.projetotcc.adapters.CarrinhoAdapter;
import com.example.talit.projetotcc.adapters.PedidoAdapter;
import com.example.talit.projetotcc.logicalView.CarrinhoItens;
import com.example.talit.projetotcc.logicalView.Pedido;
import com.example.talit.projetotcc.logicalView.Sacola;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by talit on 21/11/2017.
 */

public class ListarPedidos extends AsyncTask<String, String, String> {

    @Override
    protected String doInBackground(String... params) {

        String api_url = "http://www.mlprojetos.com/webservice/index.php/pedido/getPedidosPorUsuario/" + params[0] + "/" + params[1];

        String response = "";

        HttpURLConnection urlConnection;

        try {

            URL url = new URL(api_url);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setDoOutput(true);
            urlConnection.connect();

            InputStream inputStream;
            if (urlConnection.getResponseCode() < HttpURLConnection.HTTP_BAD_REQUEST) {
                inputStream = urlConnection.getInputStream();
            } else {
                inputStream = urlConnection.getErrorStream();
            }
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String temp;
            while ((temp = bufferedReader.readLine()) != null) {
                response += temp;
            }
            Log.i("response", response);
            return response;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);

        try {

            JSONObject api_result = new JSONObject(result);
            String response = api_result.getString("response");

            JSONObject status = new JSONObject(response);
            Log.i("Response", response);

            String status_est = status.getString("status");
            String descricao = status.getString("descricao");
            String objeto = status.getString("objeto");
            Log.i("Status", status_est);
            if (status_est.equals("true")) {
                Pedidos.pbPedidos.setVisibility(View.INVISIBLE);
                JSONArray dados = status.getJSONArray("objeto");
                ArrayList<Pedido> pedidos = new ArrayList<>();

                Pedido pedido = null;
                for (int j = 0; j< dados.length(); ++j){
                    JSONObject dados_result = dados.getJSONObject(j);
                    String lote = dados_result.getString("pedido");
                    JSONObject lote_result = new JSONObject(lote);

                    pedido = new Pedido(lote_result.getString("pedido_id"),lote_result.getString("estabelecimento_id"),
                            lote_result.getString("pedido_valor"),lote_result.getString("pedido_data"),
                            lote_result.getString("pedido_status_id"),lote_result.getString("status_pedido_descricao"));
                    pedidos.add(pedido);
                }


                if (pedidos.size() > 0) {
                    Log.i("array", pedidos.toString());
                    Pedidos.pbPedidos.setVisibility(View.INVISIBLE);
                    Pedidos.recView.setAdapter(null);
                    PedidoAdapter pedAdapter = new PedidoAdapter(Pedidos.act, Pedidos.contex,pedidos);
                    Pedidos.recView.setAdapter(pedAdapter);
                    pedAdapter.notifyDataSetChanged();
                    //TabBuscar.listas.deferNotifyDataSetChanged();
                } else {
                    Pedidos.relativeNoped.setVisibility(View.VISIBLE);
                    Pedidos.pbPedidos.setVisibility(View.INVISIBLE);
                }

            }
        } catch (JSONException e) {
            e.printStackTrace();
            Pedidos.recView.setAdapter(null);
            Pedidos.relativeNoped.setVisibility(View.VISIBLE);
            Carrinho.no_list.setVisibility(View.VISIBLE);

        }
    }
}
