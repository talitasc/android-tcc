package com.example.talit.projetotcc.connectionAPI;

import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

import com.example.talit.projetotcc.adapters.AvaliacoesAdapter;
import com.example.talit.projetotcc.adapters.ProdutosAdapter;
import com.example.talit.projetotcc.fragments.TabAvaliacoes;
import com.example.talit.projetotcc.fragments.TabDestaques;
import com.example.talit.projetotcc.logicalView.Avaliacao;
import com.example.talit.projetotcc.logicalView.Estabelecimento;
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
 * Created by talit on 10/11/2017.
 */

public class ListarAvaliacoes extends AsyncTask<String, String, String> {

    private Listener mListener;
    public String status = "false";

    public interface Listener {

        public void onLoaded(List<Avaliacao> avaliacao);
    }
    public ListarAvaliacoes(Listener mListener){

        this.mListener = mListener;
        TabAvaliacoes.pbAvaliacoes.setVisibility(View.VISIBLE);
    }
    @Override
    protected String doInBackground(String... params) {

        String api_url = "http://www.mlprojetos.com/webservice/index.php/estabelecimento/getAvaliacoesByEstabelecimento/" + params[0] + "/";
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
                TabAvaliacoes.relNoAva.setVisibility(View.INVISIBLE);
                JSONArray dados = status.getJSONArray("objeto");
                ArrayList<Avaliacao> avaliacaoes = new ArrayList<>();

                for (int i = 0; i < dados.length(); ++i) {
                    JSONObject dados_result = dados.getJSONObject(i);
                    Avaliacao avaliacao = new Avaliacao(
                            dados_result.getString("nome_avaliador"),
                            dados_result.getInt("nota_avaliacao"),
                            dados_result.getString("descricao_avaliacao"),
                            dados_result.getString("data_avaliacao"));
                    avaliacaoes.add(avaliacao);
                }
                if (avaliacaoes.size() > 0) {
                    Log.i("array", avaliacaoes.toString());
                    TabAvaliacoes.pbAvaliacoes.setVisibility(View.INVISIBLE);
                    AvaliacoesAdapter avaliacoesAdapter = new AvaliacoesAdapter(avaliacaoes,TabAvaliacoes.activity, TabAvaliacoes.context);
                    TabAvaliacoes.recAva.setAdapter(avaliacoesAdapter);
                    avaliacoesAdapter.notifyDataSetChanged();


                } else {
                    TabAvaliacoes.pbAvaliacoes.setVisibility(View.INVISIBLE);
                    TabAvaliacoes.recAva.setAdapter(null);
                    TabAvaliacoes.relNoAva.setVisibility(View.VISIBLE);
                }

            } else if (descricao.contains("Nenhuma")) {
                TabAvaliacoes.pbAvaliacoes.setVisibility(View.INVISIBLE);
                TabAvaliacoes.recAva.setAdapter(null);
                TabAvaliacoes.relNoAva.setVisibility(View.VISIBLE);

            }
        } catch (JSONException e) {
            e.printStackTrace();
            TabAvaliacoes.relNoAva.setVisibility(View.VISIBLE);
            TabAvaliacoes.pbAvaliacoes.setVisibility(View.INVISIBLE);
            TabAvaliacoes.recAva.setAdapter(null);
            if (mListener != null) {
                mListener.onLoaded(null);
            }
        }
    }
}
