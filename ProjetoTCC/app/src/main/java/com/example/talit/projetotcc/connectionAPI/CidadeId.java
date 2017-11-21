package com.example.talit.projetotcc.connectionAPI;

import android.app.AlertDialog;
import android.os.AsyncTask;
import android.util.Log;

import com.example.talit.projetotcc.R;
import com.example.talit.projetotcc.activities.AlteraDadosConsumidor;

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
 * Created by talit on 18/11/2017.
 */

public class CidadeId extends AsyncTask<String, String, String> {
    private String cidade_descricao;
    private String estado_sigla;

    @Override
    protected String doInBackground(String... n) {

        String api_url = "http://www.mlprojetos.com/webservice/index.php/localidade/getCidadePorDescricaoEstado/";
        cidade_descricao = n[0];
        estado_sigla = n[1];

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
            jsonObject.accumulate("cidade_descricao", cidade_descricao);
            jsonObject.accumulate("estado_sigla", estado_sigla);
            //jsonObject.accumulate("token", token);
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
                Log.i("teste_api_login", response);
                JSONObject resp = new JSONObject(response);
                //JSONObject object = new JSONObject(resp.getString("objeto"));
                //Log.i("nome", object.getString("usuario_id"));
                //Log.i("email", object.getString("tipo_usuario_id"));
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
            String response = api_result.getString("cidade");
            Log.i("Response cidadexzinha ", response);


            if (response.contains("cidade")) {

                JSONObject dados_result = new JSONObject(response);
                Log.i("id cidade", dados_result.getString("cidade_id"));
                AlteraDadosConsumidor.cadastrarEndereco(dados_result.getString("estado_id"), dados_result.getString("cidade_id"));
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(AlteraDadosConsumidor.context);
                builder.setTitle(AlteraDadosConsumidor.act.getString(R.string.txt_enviado_erro));
                builder.setMessage(AlteraDadosConsumidor.act.getString(R.string.txt_erro_alterados));
                builder.setPositiveButton("Ok", null);
                builder.setCancelable(false);
                builder.show();

            }

        } catch (Exception e) {
            e.printStackTrace();
            AlertDialog.Builder builder = new AlertDialog.Builder(AlteraDadosConsumidor.context);
            builder.setTitle(AlteraDadosConsumidor.act.getString(R.string.txt_enviado_erro));
            builder.setMessage(AlteraDadosConsumidor.act.getString(R.string.txt_erro_alterados));
            builder.setPositiveButton("Ok", null);
            builder.setCancelable(false);
            builder.show();

        }

    }
}
