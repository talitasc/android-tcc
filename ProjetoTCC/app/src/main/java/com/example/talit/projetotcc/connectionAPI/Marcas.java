package com.example.talit.projetotcc.connectionAPI;

import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

import com.example.talit.projetotcc.activities.PaginaInicialEstabelecimentos;
import com.example.talit.projetotcc.adapters.MarcaAdapter;
import com.example.talit.projetotcc.fragments.TabDestaques;
import com.example.talit.projetotcc.logicalView.Marca;

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
 * Created by talit on 29/10/2017.
 */

public class Marcas extends AsyncTask<String, String, String> {

    private Listener mListener;

    public interface Listener {

        public void onLoaded(String status);
    }
    public Marcas(Listener mListener){

        this.mListener = mListener;
        PaginaInicialEstabelecimentos.pbMarcas.setVisibility(View.VISIBLE);
    }
    @Override
    protected String doInBackground(String... params) {

        String api_url = " http://www.mlprojetos.com/webservice/index.php/marca/getMarcas";

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
               // TabDestaques.no_marcas.setVisibility(View.INVISIBLE);
                JSONArray dados = status.getJSONArray("objeto");
                ArrayList<Marca> marcas = new ArrayList<>();

                for (int i = 0; i < dados.length(); ++i) {
                    JSONObject dados_result = dados.getJSONObject(i);
                    Marca marca = new Marca(
                            dados_result.getInt("marca_id"),
                            dados_result.getString("marca_descricao"),
                            dados_result.getString("marca_path_img"));
                    marcas.add(marca);
                }
                if (marcas.size() > 0) {
                    Log.i("array","teste");
                    PaginaInicialEstabelecimentos.pbMarcas.setVisibility(View.INVISIBLE);
                    PaginaInicialEstabelecimentos.recMarca.setAdapter(null);
                    MarcaAdapter marcaAdapter  = new MarcaAdapter(TabDestaques.activity, marcas);
                    PaginaInicialEstabelecimentos.recMarca.setAdapter(marcaAdapter);
                    //produtosAdapter.notifyDataSetChanged();


                    if (mListener != null) {
                        mListener.onLoaded("Marca");
                    }

                } else {
                    PaginaInicialEstabelecimentos.pbMarcas.setVisibility(View.INVISIBLE);
                    PaginaInicialEstabelecimentos.recMarca.setAdapter(null);
                    PaginaInicialEstabelecimentos.no_marcas.setVisibility(View.VISIBLE);

                    if (mListener != null) {
                        mListener.onLoaded("false");
                    }
                }

            } else if (descricao.equals("Nenhum lote encontrado!")) {
                PaginaInicialEstabelecimentos.pbMarcas.setVisibility(View.INVISIBLE);
                PaginaInicialEstabelecimentos.recMarca.setAdapter(null);
                PaginaInicialEstabelecimentos.no_marcas.setVisibility(View.VISIBLE);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            PaginaInicialEstabelecimentos.no_marcas.setVisibility(View.VISIBLE);
            PaginaInicialEstabelecimentos.pbMarcas.setVisibility(View.INVISIBLE);

            if (mListener != null) {
                mListener.onLoaded("false");
            }
        }
    }
}
