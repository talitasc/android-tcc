package com.example.talit.projetotcc.connectionAPI;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

import com.example.talit.projetotcc.R;
import com.example.talit.projetotcc.activities.LoginCliente;
import com.example.talit.projetotcc.activities.LoginPessoaJuridica;

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
 * Created by talit on 28/11/2017.
 */

public class RecuperarSenhaPJ extends AsyncTask<String, String, String> {


    private String email;

    @Override
    protected String doInBackground(String... n) {

        String api_url = "http://www.mlprojetos.com/webservice/index.php/acesso/recuperarSenha";

        email = n[0];

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
            jsonObject.accumulate("email_descricao", email);
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
                final AlertDialog.Builder builder = new AlertDialog.Builder(LoginPessoaJuridica.context);
                builder.setTitle(LoginPessoaJuridica.context.getString(R.string.txt_enviado));
                builder.setMessage(LoginPessoaJuridica.context.getString(R.string.txt_emailenviado));
                builder.setPositiveButton("Fechar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        LoginPessoaJuridica.dialogo.dismiss();
                    }
                });
                builder.setCancelable(false);
                builder.show();
            }else{
                final AlertDialog.Builder builder = new AlertDialog.Builder(LoginPessoaJuridica.context);
                builder.setTitle(LoginPessoaJuridica.context.getString(R.string.txt_enviado_erro));
                builder.setMessage(LoginPessoaJuridica.context.getString(R.string.txt_noencontrado));
                builder.setPositiveButton("Fechar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        LoginPessoaJuridica.dialogo.dismiss();
                    }
                });
                builder.setCancelable(false);
                builder.show();

            }

        } catch (Exception e) {
            e.printStackTrace();
            LoginPessoaJuridica.pbRecuperar.setVisibility(View.INVISIBLE);
            AlertDialog.Builder builder = new AlertDialog.Builder(LoginPessoaJuridica.context);
            builder.setTitle("Erro");
            builder.setMessage("Erro ao Carregar Dados");
            builder.setPositiveButton("Fechar", null);
            builder.setCancelable(false);
            builder.show();

        }

    }
}
