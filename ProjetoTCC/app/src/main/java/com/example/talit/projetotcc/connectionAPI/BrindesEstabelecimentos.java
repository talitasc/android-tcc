package com.example.talit.projetotcc.connectionAPI;

import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

import com.example.talit.projetotcc.adapters.BrindesAdaper;
import com.example.talit.projetotcc.adapters.ProdutosAdapter;
import com.example.talit.projetotcc.fragments.BrindesEstabelecimento;
import com.example.talit.projetotcc.fragments.ListarEstabBuscas;
import com.example.talit.projetotcc.fragments.TabDestaques;
import com.example.talit.projetotcc.logicalView.Produtos;

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
 * Created by talit on 12/11/2017.
 */

public class BrindesEstabelecimentos extends AsyncTask<String, String, String> {

    private Listener mListener;
    public String status = "false";

    public interface Listener {

        public void onLoaded(List<Produtos> prods);
    }

    public BrindesEstabelecimentos(Listener mListener) {

        this.mListener = mListener;
        BrindesEstabelecimento.pbProdutos.setVisibility(View.VISIBLE);
    }

    @Override
    protected String doInBackground(String... params) {

        String api_url = "http://www.mlprojetos.com/webservice/index.php/produto/getLotesByEstabelecimento/" + params[0] + "/";

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
                BrindesEstabelecimento.no_produto.setVisibility(View.INVISIBLE);
                JSONArray dados = status.getJSONArray("objeto");
                ArrayList<Produtos> prods = new ArrayList<>();

                for (int i = 0; i < dados.length(); ++i) {
                    JSONObject dados_result = dados.getJSONObject(i);
                    String lote = dados_result.getString("lote");
                    JSONObject lote_result = new JSONObject(lote);

                    Produtos prod = new Produtos(
                            dados_result.getInt("produto_id"),
                            dados_result.getString("produto_descricao"),
                            dados_result.getString("produto_img_b64"),
                            dados_result.getString("estabelecimento_nome_fantasia"),
                            dados_result.getString("marca_descricao"),
                            dados_result.getString("categoria_descricao"),
                            dados_result.getInt("quantidade"),
                            dados_result.getString("unidade_medida_sigla"),
                            dados_result.getString("sub_categoria_descricao"),
                            lote_result.getInt("lote_id"),
                            lote_result.getString("lote_data_fabricacao"),
                            lote_result.getString("lote_data_vencimento"),
                            lote_result.getString("lote_preco"),
                            lote_result.getString("lote_obs"),
                            lote_result.getString("lote_quantidade"));
                    prods.add(prod);
                }
                if (prods.size() > 0) {
                    Log.i("array", prods.toString());
                    BrindesEstabelecimento.pbProdutos.setVisibility(View.INVISIBLE);
                    BrindesEstabelecimento.recProdutos.setAdapter(null);
                    BrindesAdaper brindesAdaper = new BrindesAdaper(prods, BrindesEstabelecimento.activity, BrindesEstabelecimento.context);
                    BrindesEstabelecimento.recProdutos.setAdapter(brindesAdaper);
                    brindesAdaper.notifyDataSetChanged();



                } else {
                    BrindesEstabelecimento.pbProdutos.setVisibility(View.INVISIBLE);
                    BrindesEstabelecimento.recProdutos.setAdapter(null);
                    BrindesEstabelecimento.no_produto.setVisibility(View.VISIBLE);
                }

            } else if (descricao.equals("Nenhum lote encontrado!")) {
                BrindesEstabelecimento.pbProdutos.setVisibility(View.INVISIBLE);
                BrindesEstabelecimento.recProdutos.setAdapter(null);
                BrindesEstabelecimento.no_produto.setVisibility(View.VISIBLE);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            BrindesEstabelecimento.no_produto.setVisibility(View.VISIBLE);
            BrindesEstabelecimento.pbProdutos.setVisibility(View.INVISIBLE);

            if (mListener != null) {
                mListener.onLoaded(null);
            }
        }
    }
}
