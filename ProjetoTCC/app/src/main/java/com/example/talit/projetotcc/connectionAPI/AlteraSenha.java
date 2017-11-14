package com.example.talit.projetotcc.connectionAPI;

import android.app.AlertDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

import com.example.talit.projetotcc.R;
import com.example.talit.projetotcc.activities.AlteraDadosConsumidor;

import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by talit on 14/11/2017.
 */

public class AlteraSenha extends AsyncTask<String, String, String> {

    private String usuario_id;
    private String tipo_usuario_id;
    private String usuario_senha;
    private String usuario_senha_confirma;

    @Override
    protected String doInBackground(String... n) {

        String api_url = "http://www.mlprojetos.com/webservice/index.php/acesso/redefinirSenha";

        usuario_id = n[0];
        tipo_usuario_id = n[1];
        usuario_senha = n[2];
        usuario_senha_confirma = n[3];



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
            jsonObject.accumulate("usuario_id", usuario_id);
            jsonObject.accumulate("tipo_usuario_id", tipo_usuario_id);
            jsonObject.accumulate("usuario_senha", usuario_senha);
            jsonObject.accumulate("usuario_senha_confirma", usuario_senha_confirma);
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
            String response = api_result.getString("response");
            Log.i("Response um ", response);

            JSONObject status = new JSONObject(response);
            String status_user = status.getString("status");
            Log.i("Response dois", status_user);

            JSONObject desc = new JSONObject(response);
            String descricao = desc.getString("descricao");
            Log.i("descricao", descricao);


            if (status_user.equals("true")) {
                AlteraDadosConsumidor.pbAlteraSenha.setVisibility(View.INVISIBLE);
                AlertDialog.Builder builder = new AlertDialog.Builder(AlteraDadosConsumidor.context);
                builder.setTitle(AlteraDadosConsumidor.act.getString(R.string.txt_alterados));
                builder.setMessage(AlteraDadosConsumidor.act.getString(R.string.txt_dados_alterados));
                builder.setPositiveButton("Ok", null);
                builder.setCancelable(false);
                builder.show();

            } else  {
                AlteraDadosConsumidor.pbAlteraSenha.setVisibility(View.INVISIBLE);
                AlertDialog.Builder builder = new AlertDialog.Builder(AlteraDadosConsumidor.context);
                builder.setTitle(AlteraDadosConsumidor.act.getString(R.string.txt_enviado_erro));
                builder.setMessage(AlteraDadosConsumidor.act.getString(R.string.txt_erro_alterados));
                builder.setPositiveButton("Ok", null);
                builder.setCancelable(false);
                builder.show();

            }

        } catch (Exception e) {
            e.printStackTrace();
            AlteraDadosConsumidor.pbAlteraSenha.setVisibility(View.INVISIBLE);
            AlertDialog.Builder builder = new AlertDialog.Builder(AlteraDadosConsumidor.context);
            builder.setTitle(AlteraDadosConsumidor.act.getString(R.string.txt_enviado_erro));
            builder.setMessage(AlteraDadosConsumidor.act.getString(R.string.txt_erro_alterados));
            builder.setPositiveButton("Ok", null);
            builder.setCancelable(false);
            builder.show();

        }

    }
}
