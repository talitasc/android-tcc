package com.example.talit.projetotcc.connectionAPI;

import android.app.AlertDialog;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;

import com.example.talit.projetotcc.R;
import com.example.talit.projetotcc.activities.SearchViewPaginaInicial;
import com.example.talit.projetotcc.adapters.FulltextEstabelecimentoAdapter;
import com.example.talit.projetotcc.adapters.ProdutosAdapter;
import com.example.talit.projetotcc.fragments.Buscas;
import com.example.talit.projetotcc.fragments.ListarEstabBuscas;
import com.example.talit.projetotcc.fragments.TabDestaques;
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
 * Created by talit on 11/11/2017.
 */

public class BuscaFullText extends AsyncTask<String, String, String> {

    private String busca;
    private Listener mListener;

    public interface Listener {

        public void onLoaded(List<EstabelecimentoFullText> estab);
    }
    public BuscaFullText(Listener listener) {
        this.mListener = listener;

    }
    @Override
    protected String doInBackground(String... n) {

        String api_url = "https://www.mlprojetos.com/webservice/index.php/produto/fullTextMainSearch/";

        busca = n[0];

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
                ListarEstabBuscas.relativeNoEstabs.setVisibility(View.INVISIBLE);
                JSONArray dados = status.getJSONArray("objeto");
                ArrayList<EstabelecimentoFullText> estabs = new ArrayList<>();

                for (int i = 0; i < dados.length(); ++i) {
                    JSONObject dados_result = dados.getJSONObject(i);
                    String lote = dados_result.getString("estabelecimento");
                    JSONObject estab_result = new JSONObject(lote);

                    EstabelecimentoFullText estabFull  = new EstabelecimentoFullText(estab_result.getInt("estabelecimento_id"),
                            estab_result.getString("estabelecimento_cnpj"),estab_result.getString("estabelecimento_nome_fantasia"),"campinas",estab_result.getString("estabelecimento_logo"), estab_result.getString("estabelecimento_nota"));
                        estabs.add(estabFull);
                }
                if (estabs.size() > 0) {
                    Log.i("array", estabs.toString());
                    ListarEstabBuscas.pbBuscas.setVisibility(View.INVISIBLE);
                    //Buscas.relativeBuscas.setVisibility(View.GONE);
                    ListarEstabBuscas.recEstabs.setAdapter(null);
                    FulltextEstabelecimentoAdapter fullTextEstab  = new FulltextEstabelecimentoAdapter(SearchViewPaginaInicial.act, SearchViewPaginaInicial.context,estabs);
                    ListarEstabBuscas.recEstabs.setAdapter(fullTextEstab);
                    fullTextEstab.notifyDataSetChanged();


                } else {
                    ListarEstabBuscas.pbBuscas.setVisibility(View.INVISIBLE);
                    //Buscas.relativeBuscas.setVisibility(View.GONE);
                    ListarEstabBuscas.relativeNoEstabs.setVisibility(View.VISIBLE);
                    //SearchViewPaginaInicial.recEstabs.setAdapter(null);

                }

            } else if (descricao.equals("Nenhum produto ou estabelecimento encontrado!")) {
                //Buscas.relativeBuscas.setVisibility(View.GONE);
                ListarEstabBuscas.relativeNoEstabs.setVisibility(View.VISIBLE);
                ListarEstabBuscas.pbBuscas.setVisibility(View.INVISIBLE);
                ListarEstabBuscas.recEstabs.setAdapter(null);

            }
        } catch (JSONException e) {
            e.printStackTrace();
            //Buscas.relativeBuscas.setVisibility(View.GONE);
            ListarEstabBuscas.relativeNoEstabs.setVisibility(View.VISIBLE);
            ListarEstabBuscas.pbBuscas.setVisibility(View.INVISIBLE);

            if (mListener != null) {
                mListener.onLoaded(null);
            }
        }
    }
}
