package com.example.talit.projetotcc.connectionAPI;

import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

import com.example.talit.projetotcc.adapters.SubcategoriaAdapter;
import com.example.talit.projetotcc.fragments.TabDestaques;
import com.example.talit.projetotcc.logicalView.Marca;
import com.example.talit.projetotcc.logicalView.Subcategoria;

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
 * Created by talit on 08/10/2017.
 */

public class Subcategorias extends AsyncTask<String, String, String> {

    private Listener mListener;

    public interface Listener {

        public void onLoaded(String status);
    }
    public Subcategorias(Listener mListener){

        this.mListener = mListener;
        TabDestaques.pbSubcategorias.setVisibility(View.VISIBLE);
    }
    @Override
    protected String doInBackground(String... params) {

        String api_url = "http://www.mlprojetos.com/webservice/index.php/subcategoria/getSubcategorias";

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
                TabDestaques.no_sub.setVisibility(View.INVISIBLE);
                JSONArray dados = status.getJSONArray("objeto");
                ArrayList<Subcategoria> subcategorias = new ArrayList<>();

                for (int i = 0; i < dados.length(); ++i) {
                    JSONObject dados_result = dados.getJSONObject(i);
                    Subcategoria subcategoria = new Subcategoria(dados_result.getInt("sub_categoria_id"),dados_result.getString("sub_categoria_descricao"));
                    subcategorias.add(subcategoria);
                }
                if (subcategorias.size() > 0) {
                    Log.i("array","teste");
                    TabDestaques.pbSubcategorias.setVisibility(View.INVISIBLE);
                    TabDestaques.recSubcategorias.setAdapter(null);
                    SubcategoriaAdapter subcategoriaAdapter  = new SubcategoriaAdapter(TabDestaques.activity, subcategorias);
                    TabDestaques.recSubcategorias.setAdapter(subcategoriaAdapter);
                    //produtosAdapter.notifyDataSetChanged();


                    if (mListener != null) {
                        mListener.onLoaded("Marca");
                    }

                } else {
                    TabDestaques.pbSubcategorias.setVisibility(View.INVISIBLE);
                    TabDestaques.recSubcategorias.setAdapter(null);
                    TabDestaques.no_sub.setVisibility(View.VISIBLE);

                    if (mListener != null) {
                        mListener.onLoaded("false");
                    }
                }

            } else if (descricao.equals("Nenhum lote encontrado!")) {
                TabDestaques.pbSubcategorias.setVisibility(View.INVISIBLE);
                TabDestaques.recSubcategorias.setAdapter(null);
                TabDestaques.no_sub.setVisibility(View.VISIBLE);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            TabDestaques.no_sub.setVisibility(View.VISIBLE);
            TabDestaques.pbSubcategorias.setVisibility(View.INVISIBLE);

            if (mListener != null) {
                mListener.onLoaded("false");
            }
        }
    }
}
