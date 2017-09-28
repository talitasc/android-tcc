package com.example.talit.projetotcc.connectionAPI;

import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import com.example.talit.projetotcc.activities.ListarCidades;
import com.example.talit.projetotcc.activities.ListarEstados;
import com.example.talit.projetotcc.activities.PaginalnicialConsumidor;
import com.example.talit.projetotcc.adapters.CidadeAdapter;
import com.example.talit.projetotcc.adapters.EstadoAdapter;
import com.example.talit.projetotcc.logicalView.Cidade;
import com.example.talit.projetotcc.logicalView.Estado;

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
 * Created by talit on 24/05/2017.
 */

public class Cidades extends AsyncTask<String, String, String> {

    public interface Listener {

        public void onLoaded(List<Cidade> listasCidades);
    }

    private Listener mListener;
    public static String estado;

    public Cidades(Listener mListener) {
        this.mListener = mListener;
        ListarCidades.pbLocais.setVisibility(View.VISIBLE);
    }

    @Override
    protected String doInBackground(String... params) {

        String api_url = "http://www.mlprojetos.com/webservice/index.php/localidade/getCidades/"+ params[0];

        String response = "";

        HttpURLConnection urlConnection;

        try {

            URL url = new URL(api_url);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setDoOutput(true);
            urlConnection.connect();

            InputStream inputStream;
            // get stream
            if (urlConnection.getResponseCode() < HttpURLConnection.HTTP_BAD_REQUEST) {
                inputStream = urlConnection.getInputStream();
            } else {
                inputStream = urlConnection.getErrorStream();
            }
            // parse stream
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
            ListarCidades.pbLocais.setVisibility(View.INVISIBLE);
            JSONArray dados = api_result.getJSONArray("cidades");
            ArrayList<Cidade> cidades = new ArrayList<>();

            for (int i = 0; i < dados.length(); ++i) {
                JSONObject dados_result = dados.getJSONObject(i);
                Cidade cidade = new Cidade(dados_result.getInt("cidade_id"),
                        dados_result.getInt("estado_id"),
                        dados_result.getString("cidade_descricao"));
                cidades.add(cidade);
            }
            final CidadeAdapter cidadeAdapter = new CidadeAdapter(ListarCidades.act,ListarCidades.context,cidades);
            ListarCidades.listas.setAdapter(cidadeAdapter);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
