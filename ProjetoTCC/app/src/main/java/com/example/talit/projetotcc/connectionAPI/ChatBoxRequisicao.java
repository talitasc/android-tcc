package com.example.talit.projetotcc.connectionAPI;

import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

import com.example.talit.projetotcc.R;
import com.example.talit.projetotcc.activities.ChatBot;
import com.example.talit.projetotcc.activities.FinalizarCompra;
import com.example.talit.projetotcc.adapters.MessageListAdapter;
import com.example.talit.projetotcc.logicalView.Cep;
import com.example.talit.projetotcc.logicalView.Estabelecimento;
import com.example.talit.projetotcc.logicalView.Message;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by talit on 15/11/2017.
 */

public class ChatBoxRequisicao extends AsyncTask<String, String, String> {

    private Listener mListener;
    public interface Listener {

        public void onLoaded(String status);
    }

    public ChatBoxRequisicao(Listener mListener) {

        this.mListener = mListener;
        //FinalizarCompra.pbAguardar.setVisibility(View.VISIBLE);
    }

    @Override
    protected String doInBackground(String... params) {

        String api_url = "http://www.smarketapp.com.br/mensagem?texto=" + params[0];
        try {
            URL url = new URL(api_url);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            BufferedReader r = new BufferedReader(new InputStreamReader(in));

            StringBuilder jsonString = new StringBuilder();
            String line;
            while ((line = r.readLine()) != null) {
                jsonString.append(line);
            }

            urlConnection.disconnect();

            return jsonString.toString();
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
            Log.i("api chat boot", api_result.toString());

            if (api_result.getString("resposta") != null) {

                Log.i("rua", api_result.getString("resposta"));
                ChatBot.msg.add(new Message(api_result.getString("resposta"),"CHAT"));
                ChatBot.mMessageAdapter.notifyDataSetChanged();
            }
        } catch (Exception e) {
            e.printStackTrace();
            /*FinalizarCompra.pbAguardar.setVisibility(View.INVISIBLE);
            FinalizarCompra.txtErroEndereco.setText(R.string.ret_valida_busca);
            FinalizarCompra.getValues(null, null,
                    null, null);*/
        }
    }
}
