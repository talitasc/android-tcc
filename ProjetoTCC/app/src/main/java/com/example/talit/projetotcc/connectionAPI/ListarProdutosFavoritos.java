package com.example.talit.projetotcc.connectionAPI;

import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

import com.example.talit.projetotcc.activities.ProdutosFavoritados;
import com.example.talit.projetotcc.adapters.FormasPagamentoAdapter;
import com.example.talit.projetotcc.adapters.ListaSupermercadosAdapter;
import com.example.talit.projetotcc.adapters.ProdutosFavoritosAdapter;
import com.example.talit.projetotcc.logicalView.FormasDePagamento;
import com.example.talit.projetotcc.logicalView.ProdutosFavoritos;

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
 * Created by talit on 03/12/2017.
 */

public class ListarProdutosFavoritos extends AsyncTask<String, String, String> {

    private Listener mListener;
    public String status = "false";

    public interface Listener {

        public void onLoaded(List<ProdutosFavoritos> produtosFavoritoses);
    }
    public ListarProdutosFavoritos(Listener mListener){
        this.mListener = mListener;
    }
    @Override
    protected String doInBackground(String... params) {

        String api_url = " http://www.mlprojetos.com/webservice/index.php/produto/getFavoritosByUsuario/" + params[0] + "/" + params[1]+"/";
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
                ProdutosFavoritados.progressBar.setVisibility(View.INVISIBLE);
                JSONArray dados = status.getJSONArray("objeto");
                ArrayList<ProdutosFavoritos> favoritos = new ArrayList<>();

                for (int i = 0; i < dados.length(); ++i) {
                    JSONObject dados_result = dados.getJSONObject(i);
                    ProdutosFavoritos favorito = new ProdutosFavoritos(
                            dados_result.getInt("favorito_id"),dados_result.getInt("usuario_id"),
                            dados_result.getInt("tipo_usuario_id"),dados_result.getInt("produto_id"),
                            dados_result.getString("produto_descricao"),dados_result.getString("produto_status"),
                            dados_result.getString("produto_img_b64"),dados_result.getString("estabelecimento_nome_fantasia"),
                            dados_result.getInt("marca_id"),dados_result.getString("marca_descricao"),
                            dados_result.getInt("categoria_id"),dados_result.getString("categoria_descricao"),
                            dados_result.getInt("quantidade"),dados_result.getString("unidade_medida_sigla"),
                            dados_result.getInt("sub_categoria_id"),dados_result.getString("sub_categoria_descricao"),
                            dados_result.getInt("favorito"),dados_result.getInt("disponivel"));

                    if(dados_result.getInt("favorito") == 1) {
                        favoritos.add(favorito);
                    }
                }
                if (favoritos.size() > 0) {
                    ProdutosFavoritados.progressBar.setVisibility(View.INVISIBLE);
                    ProdutosFavoritados.produtosFavoritosAdapter = new ProdutosFavoritosAdapter(ProdutosFavoritados.act,ProdutosFavoritados.c,favoritos);
                    ProdutosFavoritados.listas.setAdapter(ProdutosFavoritados.produtosFavoritosAdapter);
                    ProdutosFavoritados.produtosFavoritosAdapter.notifyDataSetChanged();

                } else {
                    ProdutosFavoritados.progressBar.setVisibility(View.INVISIBLE);
                    ProdutosFavoritados.relativeNoList.setVisibility(View.VISIBLE);
                }

            } else if (descricao.contains("Nenhuma")) {
                ProdutosFavoritados.progressBar.setVisibility(View.INVISIBLE);
                ProdutosFavoritados.relativeNoList.setVisibility(View.VISIBLE);
                ProdutosFavoritados.listas.setAdapter(null);

            }
        } catch (JSONException e) {
            e.printStackTrace();
            ProdutosFavoritados.progressBar.setVisibility(View.INVISIBLE);
            ProdutosFavoritados.relativeNoList.setVisibility(View.VISIBLE);
            ProdutosFavoritados.listas.setAdapter(null);

            if (mListener != null) {
                mListener.onLoaded(null);
            }
        }
    }
}

