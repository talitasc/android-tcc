package com.example.talit.projetotcc.connectionAPI;

import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

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
 * Created by talit on 22/05/2017.
 */

public class Estados extends AsyncTask<String, String, String> {

    public interface Listener {

        public void onLoaded(List<Estado> listasEstado);
    }

    private Listener mListener;
    public static String estado;

    public Estados(Estados.Listener mListener) {
        this.mListener = mListener;
        ListarEstados.pbLocais.setVisibility(View.VISIBLE);
    }

    @Override
    protected String doInBackground(String... params) {

        String api_url = "http://www.mlprojetos.com/webservice/index.php/localidade/getEstados";

        String response = "";

        HttpURLConnection urlConnection;
        String requestBody;

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
        final EstadoAdapter estadoAdapter;

        try {
            JSONObject api_result = new JSONObject(result);
            ListarEstados.pbLocais.setVisibility(View.INVISIBLE);
            JSONArray dados = api_result.getJSONArray("estados");
            //ArrayList<Estado> estados = new ArrayList<>();

            for (int i = 0; i < dados.length(); ++i) {
                JSONObject dados_result = dados.getJSONObject(i);
                Estado estado = new Estado(dados_result.getInt("estado_id"),
                        dados_result.getString("estado_sigla"),
                        dados_result.getString("estado_descricao"));

                //estados.add(estado);
                ListarEstados.ests.add(estado);
            }

            ListarEstados.estadoAdapter = new EstadoAdapter(ListarEstados.act,ListarEstados.context,ListarEstados.ests);
            ListarEstados.listas.setAdapter(ListarEstados.estadoAdapter);
            ListarEstados.estadoAdapter.notifyDataSetChanged();
            /*estadoAdapter = new EstadoAdapter(ListarEstados.act, ListarEstados.context, estados);
            ListarEstados.listas.setAdapter(estadoAdapter);*/

        } catch (JSONException e) {
            e.printStackTrace();
            if (mListener != null) {
                mListener.onLoaded(null);
            }
        }
    }
}
