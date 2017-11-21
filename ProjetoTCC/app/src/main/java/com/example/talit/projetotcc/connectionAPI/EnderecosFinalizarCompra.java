package com.example.talit.projetotcc.connectionAPI;

import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

import com.example.talit.projetotcc.activities.AlteraDadosConsumidor;
import com.example.talit.projetotcc.activities.FinalizarCompra;
import com.example.talit.projetotcc.adapters.EnderecoAdapter;
import com.example.talit.projetotcc.adapters.EnderecosFinalizarCompraAdapter;
import com.example.talit.projetotcc.logicalView.Endereco;

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

public class EnderecosFinalizarCompra extends AsyncTask<String, String, String> {


    @Override
    protected String doInBackground(String... params) {

        String api_url = "http://www.mlprojetos.com/webservice/index.php/consumidor/getEnderecosConsumidor/" + params[0] + "/" + params[1] +  "/";

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
            Log.i("Status", status_est);

            if (status_est.equals("true")) {

                JSONArray dados = status.getJSONArray("objeto");
                ArrayList<Endereco> listareEst = new ArrayList<>();

                for (int i = 0; i < dados.length(); ++i) {
                    JSONObject dados_result = dados.getJSONObject(i);
                    Endereco end = new Endereco(dados_result.getInt("endereco_id"),
                            dados_result.getString("endereco_rua"), dados_result.getString("endereco_numero"),
                            dados_result.getString("endereco_complemento"), dados_result.getString("endereco_bairro"),
                            dados_result.getString("endereco_cep"), dados_result.getString("estado_sigla"),
                            dados_result.getString("cidade_descricao"),dados_result.getInt("endereco_id"),dados_result.getInt("cidade_id"));
                    listareEst.add(end);
                }
                if (listareEst.size() > 0) {
                    Log.i("array", listareEst.toString());
                    //AlteraDadosConsumidor.pbAltEnd.setVisibility(View.INVISIBLE);
                    FinalizarCompra.recEnderecos.setAdapter(null);
                    EnderecosFinalizarCompraAdapter enderecosFinalizarCompraAdapter = new EnderecosFinalizarCompraAdapter(FinalizarCompra.act, FinalizarCompra.context, listareEst);
                    FinalizarCompra.recEnderecos.setAdapter(enderecosFinalizarCompraAdapter);
                    enderecosFinalizarCompraAdapter.notifyDataSetChanged();
                    //TabBuscar.listas.deferNotifyDataSetChanged();
                } else {
                    FinalizarCompra.noEnd.setVisibility(View.VISIBLE);
                }

            } else if (descricao.equals("Nenhum endereço encontrado para este consumidor!")) {
                FinalizarCompra.noEnd.setVisibility(View.VISIBLE);
                //AlteraDadosConsumidor.pbAltEnd.setVisibility(View.INVISIBLE);
                FinalizarCompra.recEnderecos.setAdapter(null);
                //TabBuscar.listas.deferNotifyDataSetChanged();
            }
        } catch (JSONException e) {
            e.printStackTrace();
            FinalizarCompra.recEnderecos.setAdapter(null);
        }
    }
}
