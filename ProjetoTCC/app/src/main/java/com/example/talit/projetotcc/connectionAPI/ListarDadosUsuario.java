package com.example.talit.projetotcc.connectionAPI;

import android.app.AlertDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

import com.example.talit.projetotcc.R;
import com.example.talit.projetotcc.activities.AlteraDadosConsumidor;
import com.example.talit.projetotcc.logicalView.DadosConsumidor;

import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by talit on 13/11/2017.
 */

public class ListarDadosUsuario extends AsyncTask<String, String, String> {

    @Override
    protected String doInBackground(String... n) {

        String api_url = "http://www.mlprojetos.com/webservice/index.php/consumidor/getConsumidor/"+ n[0] + "/" + n[1] + "/" ;


        HttpURLConnection urlConnection;
        String requestBody;
        String response = "";

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
            Log.i("Response um ", response);

            JSONObject status = new JSONObject(response);
            String status_user = status.getString("status");
            Log.i("Response dois", status_user);

            JSONObject desc = new JSONObject(response);
            String descricao = desc.getString("descricao");
            Log.i("descricao", descricao);


            if (status_user.equals("true")) {
                String dados = desc.getString("objeto");
                JSONObject dados_result = new JSONObject(dados);
                AlteraDadosConsumidor.setDados(dados_result.getString("consumidor_nome"),dados_result.getString("consumidor_sobrenome"),dados_result.getString("telefone_ddd"),dados_result.getString("telefone_numero"),dados_result.getString("tipo_telefone_id"));

            } else  {
                AlteraDadosConsumidor.pbAlteraDados.setVisibility(View.INVISIBLE);
                AlertDialog.Builder builder = new AlertDialog.Builder(AlteraDadosConsumidor.context);
                builder.setTitle(AlteraDadosConsumidor.act.getString(R.string.txt_enviado_erro));
                builder.setMessage(AlteraDadosConsumidor.act.getString(R.string.txt_erro_alterados));
                builder.setPositiveButton("Ok", null);
                builder.setCancelable(false);
                builder.show();

            }

        } catch (Exception e) {
            e.printStackTrace();
            AlteraDadosConsumidor.pbAlteraDados.setVisibility(View.INVISIBLE);
            AlertDialog.Builder builder = new AlertDialog.Builder(AlteraDadosConsumidor.context);
            builder.setTitle(AlteraDadosConsumidor.act.getString(R.string.txt_enviado_erro));
            builder.setMessage(AlteraDadosConsumidor.act.getString(R.string.txt_erro_alterados));
            builder.setPositiveButton("Ok", null);
            builder.setCancelable(false);
            builder.show();

        }

    }
}
