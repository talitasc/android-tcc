package com.example.talit.projetotcc.connectionAPI;

import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

import com.example.talit.projetotcc.activities.SearchViewEstabelecimento;
import com.example.talit.projetotcc.activities.SearchViewPaginaInicial;
import com.example.talit.projetotcc.adapters.BuscaProdAdapter;
import com.example.talit.projetotcc.adapters.FulltextEstabelecimentoAdapter;
import com.example.talit.projetotcc.fragments.ListarEstabBuscas;
import com.example.talit.projetotcc.fragments.ListarProdBuscas;
import com.example.talit.projetotcc.logicalView.EstabelecimentoFullText;
import com.example.talit.projetotcc.logicalView.Produtos;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by talit on 12/11/2017.
 */

public class BuscaFullTextProdutos  extends AsyncTask<String, String, String> {

    private String busca;
    private String idEsta;
    private Listener mListener;

    public interface Listener {

        public void onLoaded(List<Produtos> prods);
    }
    public BuscaFullTextProdutos(Listener listener) {
        this.mListener = listener;

    }
    @Override
    protected String doInBackground(String... n) {

        String api_url = "https://www.mlprojetos.com/webservice/index.php/produto/fullTextProductSearch/";

        busca = n[0];
        idEsta = n[1];

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
            jsonObject.accumulate("busca", busca);
            jsonObject.accumulate("estabelecimento_id", idEsta);
            String json = jsonObject.toString();
            OutputStream outputStream = new BufferedOutputStream(urlConnection.getOutputStream());
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, "utf-8"));
            writer.write(json);
            writer.flush();
            writer.close();
            outputStream.close();
            Log.i("Json", json);
            InputStream inputStream;
            if (urlConnection.getResponseCode() < HttpURLConnection.HTTP_BAD_REQUEST) {
                inputStream = urlConnection.getInputStream();
            } else {
                inputStream = urlConnection.getErrorStream();
            }
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String temp, response = "";
            while ((temp = bufferedReader.readLine()) != null) {
                response += temp;
                Log.i("teste_api_FULLTEXT", response);
                JSONObject resp = new JSONObject(response);
            }
            return response;

        } catch (Exception e) {
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
                ListarProdBuscas.relativeNoEstabs.setVisibility(View.INVISIBLE);
                JSONArray dados = status.getJSONArray("objeto");
                ArrayList<Produtos> prods = new ArrayList<>();

                for (int i = 0; i < dados.length(); ++i) {
                    JSONObject dados_result = dados.getJSONObject(i);
                    String lote = dados_result.getString("produto");
                    JSONObject lote_result = new JSONObject(lote);

                    String prodt = lote_result.getString("lote");
                    JSONObject prodt_result = new JSONObject(prodt);

                    Produtos prod = new Produtos(
                            lote_result.getInt("produto_id"),
                            lote_result.getString("produto_descricao"),
                            lote_result.getString("produto_img_b64"),
                            lote_result.getString("marca_descricao"),
                            lote_result.getString("categoria_descricao"),
                            lote_result.getInt("quantidade"),
                            lote_result.getString("unidade_medida_sigla"),
                            lote_result.getString("sub_categoria_descricao"),
                            prodt_result.getInt("lote_id"),
                            prodt_result.getString("lote_data_fabricacao"),
                            prodt_result.getString("lote_data_vencimento"),
                            prodt_result.getString("lote_preco"),
                            prodt_result.getString("lote_obs"),
                            prodt_result.getString("lote_quantidade"));

                    prods.add(prod);
                }
                if (prods.size() > 0) {
                    Log.i("array", prods.toString());
                    ListarProdBuscas.pbBuscas.setVisibility(View.INVISIBLE);
                    //Buscas.relativeBuscas.setVisibility(View.GONE);
                    ListarProdBuscas.recProds.setAdapter(null);
                    BuscaProdAdapter buscaProdAdapter  = new BuscaProdAdapter(prods,SearchViewEstabelecimento.act, SearchViewEstabelecimento.context);
                    ListarProdBuscas.recProds.setAdapter(buscaProdAdapter);
                    buscaProdAdapter.notifyDataSetChanged();
                } else {
                    ListarProdBuscas.pbBuscas.setVisibility(View.INVISIBLE);
                    //Buscas.relativeBuscas.setVisibility(View.GONE);
                    ListarProdBuscas.relativeNoEstabs.setVisibility(View.VISIBLE);
                    //SearchViewPaginaInicial.recEstabs.setAdapter(null);


                }

            } else if (descricao.equals("Nenhum produto ou estabelecimento encontrado!")) {
                //Buscas.relativeBuscas.setVisibility(View.GONE);
                ListarProdBuscas.relativeNoEstabs.setVisibility(View.VISIBLE);
                ListarProdBuscas.pbBuscas.setVisibility(View.INVISIBLE);
                ListarProdBuscas.recProds.setAdapter(null);

            }
        } catch (JSONException e) {
            e.printStackTrace();
            //Buscas.relativeBuscas.setVisibility(View.GONE);
            ListarProdBuscas.relativeNoEstabs.setVisibility(View.VISIBLE);
            ListarProdBuscas.pbBuscas.setVisibility(View.INVISIBLE);

            if (mListener != null) {
                mListener.onLoaded(null);
            }
        }
    }
}
