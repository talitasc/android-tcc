package com.example.talit.projetotcc.connectionAPI;

import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

import com.example.talit.projetotcc.activities.Carrinho;
import com.example.talit.projetotcc.activities.PaginalnicialConsumidor;
import com.example.talit.projetotcc.adapters.CarrinhoAdapter;
import com.example.talit.projetotcc.adapters.ListaSupermercadosAdapter;
import com.example.talit.projetotcc.logicalView.CarrinhoItens;
import com.example.talit.projetotcc.logicalView.Cidade;
import com.example.talit.projetotcc.logicalView.Endereco;
import com.example.talit.projetotcc.logicalView.Estabelecimento;
import com.example.talit.projetotcc.logicalView.Sacola;
import com.example.talit.projetotcc.logicalView.Telefone;

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
 * Created by talit on 20/11/2017.
 */

public class ListarCarrinho extends AsyncTask<String, String, String> {

    @Override
    protected String doInBackground(String... params) {

        String api_url = "http://www.mlprojetos.com/webservice/index.php/carrinho/listarItensCarrinho/" + params[0] + "/";

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
            JSONObject dadosobjeto = new JSONObject(objeto);

            if (status_est.equals("true")) {
                Carrinho.pbCarrinho.setVisibility(View.INVISIBLE);
                JSONArray dados = dadosobjeto.getJSONArray("produtos");
                JSONArray carrinho = dadosobjeto.getJSONArray("carrinho");
                ArrayList<Sacola> listSacola = new ArrayList<>();

                CarrinhoItens car = null;
                for (int j = 0; j< carrinho.length(); ++j){
                    JSONObject car_result = carrinho.getJSONObject(j);
                    car = new CarrinhoItens(car_result.getString("carrinho_valor_total"),car_result.getString("carrinho_id"));
                }

                for (int i = 0; i < dados.length(); ++i) {
                    JSONObject carrinho_result = dados.getJSONObject(i);
                    Sacola sacola = new Sacola(carrinho_result.getInt("produto_id"),carrinho_result.getInt("lote_id"),
                            carrinho_result.getString("produto_descricao"),carrinho_result.getString("marca_descricao"),
                            Double.parseDouble(carrinho_result.getString("valor_unitario")),Double.parseDouble(carrinho_result.getString("valor_unitario")),
                            carrinho_result.getString("unidade_medida_sigla"),Integer.parseInt(carrinho_result.getString("quantidade")),
                            carrinho_result.getString("produto_img_b64"),carrinho_result.getString("quantidade"),car.getCarrinho_valor_total(),car.getCarrinho_id());
                    listSacola.add(sacola);
                }

                if (listSacola.size() > 0) {
                    Log.i("array", listSacola.toString());
                    Carrinho.pbCarrinho.setVisibility(View.INVISIBLE);
                    Carrinho.listas.setAdapter(null);
                    CarrinhoAdapter carAdapter = new CarrinhoAdapter(Carrinho.act, Carrinho.context,listSacola);
                    Carrinho.listas.setAdapter(carAdapter);
                    Carrinho.listas.deferNotifyDataSetChanged();
                    //TabBuscar.listas.deferNotifyDataSetChanged();
                } else {
                    Carrinho.no_list.setVisibility(View.VISIBLE);
                    Carrinho.cardFinal.setVisibility(View.INVISIBLE);
                    Carrinho.btnFinal.setVisibility(View.INVISIBLE);
                    Carrinho.pbCarrinho.setVisibility(View.INVISIBLE);
                }

            }
        } catch (JSONException e) {
            e.printStackTrace();
            Carrinho.listas.setAdapter(null);
            Carrinho.pbCarrinho.setVisibility(View.INVISIBLE);
            Carrinho.no_list.setVisibility(View.VISIBLE);
            Carrinho.cardFinal.setVisibility(View.INVISIBLE);
            Carrinho.btnFinal.setVisibility(View.INVISIBLE);

        }
    }

}
