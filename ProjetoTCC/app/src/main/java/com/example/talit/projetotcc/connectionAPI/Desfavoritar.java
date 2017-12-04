package com.example.talit.projetotcc.connectionAPI;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import com.example.talit.projetotcc.R;
import com.example.talit.projetotcc.activities.PaginaInicialEstabelecimentos;
import com.example.talit.projetotcc.activities.ProdutosFavoritados;
import com.example.talit.projetotcc.sqlight.DbConn;

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
 * Created by talit on 03/12/2017.
 */

public class Desfavoritar  extends AsyncTask<String, String, String> {

    private String usuario_id;
    private String tipo_usuario_id;
    private String produto_id;
    private String status;
    private Listener listener;
    private DbConn dbConn;
    private String tpUser;
    private String idUser;

    public interface Listener {

        public void onLoaded(String string);
    }

    public Desfavoritar(Listener listener) {
        this.listener = listener;

    }

    @Override
    protected String doInBackground(String... n) {

        String api_url = "https://www.mlprojetos.com/webservice/index.php/produto/adicionarFavorito";

        usuario_id = n[0];
        tipo_usuario_id = n[1];
        produto_id = n[2];
        status = n[3];

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
            jsonObject.accumulate("produto_id", produto_id);
            jsonObject.accumulate("status", status);
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
                //listener.onLoaded("true");

                ProdutosFavoritados.produtosFavoritosAdapter.notifyDataSetChanged();
                AlertDialog.Builder builder = new AlertDialog.Builder(ProdutosFavoritados.c);
                builder.setTitle(R.string.txt_alterados);
                builder.setMessage(R.string.produtos_desfavoritado);
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ProdutosFavoritados.act.startActivity(new Intent(ProdutosFavoritados.act, ProdutosFavoritados.class));
                        ProdutosFavoritados.act.finishActivity(0);
                    }
                });
                builder.setCancelable(false);
                builder.show();
            }

        } catch (Exception e) {
            e.printStackTrace();
            e.printStackTrace();
            AlertDialog.Builder builder = new AlertDialog.Builder(ProdutosFavoritados.c);
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
