package com.example.talit.projetotcc.connectionAPI;

import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

import com.example.talit.projetotcc.activities.PaginalnicialConsumidor;
import com.example.talit.projetotcc.adapters.CategoriasAdapter;
import com.example.talit.projetotcc.adapters.ListaSupermercadosAdapter;
import com.example.talit.projetotcc.fragments.TabCategorias;
import com.example.talit.projetotcc.fragments.TabDestaques;
import com.example.talit.projetotcc.logicalView.CategoriasProdutos;
import com.example.talit.projetotcc.logicalView.Estabelecimento;

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
 * Created by talit on 08/10/2017.
 */

public class Categorias extends AsyncTask<String, String, String> {

    private Listener mListener;
    public String status = "false";

    public interface Listener {

        public void onLoaded(String msg);
    }
    public Categorias(Listener mListener){

        this.mListener = mListener;
        TabDestaques.pb.setVisibility(View.VISIBLE);
    }
    @Override
    protected String doInBackground(String... params) {

        String api_url = "http://www.mlprojetos.com/webservice/index.php/categoria/getCategorias";

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
            Log.i("Status",status_est);

            if(status_est.equals("true")) {
                TabDestaques.no_categoria.setVisibility(View.INVISIBLE);

                JSONArray dados = status.getJSONArray("objeto");
                ArrayList<CategoriasProdutos> listaCateg = new ArrayList<>();

                for (int i = 0; i < dados.length(); ++i) {
                    JSONObject dados_result = dados.getJSONObject(i);
                    CategoriasProdutos categs = new CategoriasProdutos(dados_result.getInt("categoria_id"),
                                            dados_result.getString("categoria_descricao"),
                                            dados_result.getString("categoria_img_b64"));
                    listaCateg.add(categs);
                }

                if(listaCateg.size()> 0) {
                    Log.i("array",listaCateg.toString());
                    TabDestaques.pb.setVisibility(View.INVISIBLE);
                    CategoriasAdapter categoriasAdapter  = new CategoriasAdapter(TabDestaques.activity,listaCateg);
                    TabDestaques.rec.setAdapter(categoriasAdapter);

                    if (mListener != null) {
                        mListener.onLoaded("Categoria");
                    }
                }else{
                    TabDestaques.pb.setVisibility(View.INVISIBLE);
                    TabDestaques.rec.setAdapter(null);
                    TabDestaques.no_categoria.setVisibility(View.VISIBLE);

                    if (mListener != null) {
                        mListener.onLoaded("false");
                    }

                }

            }else if(descricao.equals("Nenhuma categoria encontrada")){
                TabDestaques.no_categoria.setVisibility(View.VISIBLE);
                TabDestaques.pb.setVisibility(View.INVISIBLE);
                TabDestaques.rec.setAdapter(null);
                if (mListener != null) {
                    mListener.onLoaded("false");
                }

            }
        } catch (JSONException e) {
            e.printStackTrace();
            TabDestaques.no_categoria.setVisibility(View.VISIBLE);
            TabDestaques.pb.setVisibility(View.INVISIBLE);
            if (mListener != null) {
                mListener.onLoaded("false");
            }
        }
    }
}
