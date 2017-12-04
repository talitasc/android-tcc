package com.example.talit.projetotcc.connectionAPI;

import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

import com.example.talit.projetotcc.adapters.AvaliacoesAdapter;
import com.example.talit.projetotcc.adapters.FormasPagamentoAdapter;
import com.example.talit.projetotcc.adapters.ListaSupermercadosAdapter;
import com.example.talit.projetotcc.fragments.TabAvaliacoes;
import com.example.talit.projetotcc.logicalView.Avaliacao;
import com.example.talit.projetotcc.logicalView.FormasDePagamento;

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
import java.util.List;

/**
 * Created by talit on 02/12/2017.
 */

public class FormasPagamento extends AsyncTask<String, String, String> {

    private Listener mListener;
    public String status = "false";

    public interface Listener {

        public void onLoaded(List<FormasDePagamento> formaPagamento);
    }
    public FormasPagamento(Listener mListener){

        this.mListener = mListener;
    }
    @Override
    protected String doInBackground(String... params) {

        String api_url = "http://www.mlprojetos.com/webservice/index.php/pagamento/getFormasPagamentoByEstabelecimento/" + params[0] + "/";
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
                //ListaSupermercadosAdapter.progressBar.setVisibility(View.INVISIBLE);
                JSONArray dados = status.getJSONArray("objeto");
                ArrayList<FormasDePagamento> formas = new ArrayList<>();

                for (int i = 0; i < dados.length(); ++i) {
                    JSONObject dados_result = dados.getJSONObject(i);
                    FormasDePagamento forma = new FormasDePagamento(
                            dados_result.getInt("forma_pagamento_id"),
                            dados_result.getString("forma_pagamento_descricao"));
                    formas.add(forma);
                }
                if (formas.size() > 0) {
                    Log.i("array", formas.toString());
                    ListaSupermercadosAdapter.progressBar.setVisibility(View.INVISIBLE);
                    FormasPagamentoAdapter formasPagamentoAdapter = new FormasPagamentoAdapter(ListaSupermercadosAdapter.act,ListaSupermercadosAdapter.c,formas);
                    ListaSupermercadosAdapter.recPagamento.setAdapter(formasPagamentoAdapter);
                    formasPagamentoAdapter.notifyDataSetChanged();


                } else {
                    formas.add(new FormasDePagamento(3,"Pagar na loja"));
                    formas.add(new FormasDePagamento(4,"Cartão de Crédito"));
                    ListaSupermercadosAdapter.progressBar.setVisibility(View.INVISIBLE);
                    FormasPagamentoAdapter formasPagamentoAdapter = new FormasPagamentoAdapter(ListaSupermercadosAdapter.act,ListaSupermercadosAdapter.c,formas);
                    ListaSupermercadosAdapter.recPagamento.setAdapter(formasPagamentoAdapter);
                    formasPagamentoAdapter.notifyDataSetChanged();
                }

            } else if (descricao.contains("Nenhuma")) {
                ListaSupermercadosAdapter.progressBar.setVisibility(View.INVISIBLE);

            }
        } catch (JSONException e) {
            e.printStackTrace();
            ListaSupermercadosAdapter.progressBar.setVisibility(View.INVISIBLE);
            if (mListener != null) {
                mListener.onLoaded(null);
            }
        }
    }
}
