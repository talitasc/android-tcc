package com.example.talit.projetotcc.connectionAPI;

import android.os.AsyncTask;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

import com.example.talit.projetotcc.activities.ProdutosEstabelecimento;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 * Created by talit on 08/10/2017.
 */

public class Produtos extends AsyncTask<String, String, String> {

    private Listener mListener;

    public interface Listener {

        public void onLoaded(List<Produtos> estab);
    }
    public Produtos(Listener mListener){

        this.mListener = mListener;
        ProdutosEstabelecimento.pb.setVisibility(View.VISIBLE);
    }

    @Override
    protected String doInBackground(String... params) {

        String api_url = "http://www.mlprojetos.com/webservice/index.php/produto/getProdutosByCategoria/" + params[0] + "/";

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

}
