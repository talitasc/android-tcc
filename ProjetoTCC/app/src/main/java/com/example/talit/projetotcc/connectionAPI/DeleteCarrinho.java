package com.example.talit.projetotcc.connectionAPI;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.util.Log;

import com.example.talit.projetotcc.R;
import com.example.talit.projetotcc.activities.AlteraDadosConsumidor;
import com.example.talit.projetotcc.activities.DetalhesProdutos;

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
 * Created by talit on 24/10/2017.
 */

public class DeleteCarrinho extends AsyncTask<String, String, String> {

    private String carrinho_id;
    private Listener listener;

    public interface Listener {

        public void onLoaded(String string);
    }

    public DeleteCarrinho(Listener listener) {
        this.listener = listener;

    }
    @Override
    protected String doInBackground(String... n) {

        String api_url = "http://www.mlprojetos.com/webservice/index.php/carrinho/deletaCarrinho";

        carrinho_id = n[0];

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
            jsonObject.accumulate("carrinho_id", carrinho_id);
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

        try {
            JSONObject api_result = new JSONObject(result);
            String response = api_result.getString("response");
            JSONObject status = new JSONObject(response);
            Log.i("Começo do Response", response);
            String status_user = status.getString("status");
            String descricao = status.getString("descricao");
            Log.i("Status", status_user);
            if (status_user.equalsIgnoreCase("true")) {
                if (descricao.equals("Carrinho deletado com sucesso!")) {
                    //listener.onLoaded("true");
                    AlertDialog.Builder builder = new AlertDialog.Builder(DetalhesProdutos.c);
                    builder.setTitle(R.string.txt_alterados);
                    builder.setMessage(R.string.delete_carrinho);
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    builder.setCancelable(false);
                    builder.show();
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
            e.printStackTrace();
            AlertDialog.Builder builder = new AlertDialog.Builder(DetalhesProdutos.c);
            builder.setTitle("Erro");
            builder.setMessage("Ocorreu um erro...");
            builder.setPositiveButton("Fechar",new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    listener.onLoaded("true");
                    dialog.dismiss();
                }
            });
            builder.setCancelable(false);
            builder.show();

        }

    }
}
