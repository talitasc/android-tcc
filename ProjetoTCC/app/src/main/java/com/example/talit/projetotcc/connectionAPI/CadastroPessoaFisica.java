package com.example.talit.projetotcc.connectionAPI;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

import com.example.talit.projetotcc.activities.CadastroConsumidor;
import com.example.talit.projetotcc.activities.LoginCliente;
import com.example.talit.projetotcc.sqlight.DbConn;

import org.json.JSONException;
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
 * Created by talit on 29/03/2017.
 */

public class CadastroPessoaFisica extends AsyncTask<String, String, String> {

    private String id_tipo_acesso;
    private String id_status;
    private String usuario;
    private String senha;
    private String nome;
    private String email;
    private String sobrenome;
    private String tipo_telefone;
    private String telefone_dd;
    private String telefone;
    private String token;
    private Listener listener;
    public String status = "nao";
    private DbConn dbconn;
    private boolean fechou = false;

    public interface Listener {

        public void onLoaded(String string);
    }

    public CadastroPessoaFisica(Listener listener) {
        this.listener = listener;

    }

    @Override
    protected String doInBackground(String... n) {
        String api_url = "http://www.mlprojetos.com/webservice/index.php/consumidor/adicionar";
        id_tipo_acesso = n[0];
        nome = n[1];
        sobrenome = n[2];
        email = n[3];
        tipo_telefone = n[4];
        telefone_dd = n[5];
        telefone = n[6];
        senha = n[7];
        //token = n[8];

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
            /*jsonObject.accumulate("tipo_usuario_id", id_tipo_acesso);
            jsonObject.accumulate("consumidor_nome", nome);
            jsonObject.accumulate("consumidor_sobrenome", sobrenome);
            jsonObject.accumulate("email_descricao", email);
            jsonObject.accumulate("tipo_telefone_id", tipo_telefone);
            jsonObject.accumulate("telefone_ddd", telefone_dd);
            jsonObject.accumulate("telefone_numero", telefone);
            jsonObject.accumulate("usuario_senha", senha);*/

            jsonObject.accumulate("tipo_usuario_id", "2");
            jsonObject.accumulate("consumidor_nome", "Talita");
            jsonObject.accumulate("consumidor_sobrenome", "Castro");
            jsonObject.accumulate("email_descricao", "blabla@gmail.com");
            jsonObject.accumulate("tipo_telefone_id", "1");
            jsonObject.accumulate("telefone_ddd", "19");
            jsonObject.accumulate("telefone_numero", "38654186");
            jsonObject.accumulate("usuario_senha", "talita23");

            //jsonObject.accumulate("token", token);
            String json = jsonObject.toString();
            OutputStream outputStream = new BufferedOutputStream(urlConnection.getOutputStream());
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, "utf-8"));
            writer.write(json);
            writer.flush();
            writer.close();
            outputStream.close();

            InputStream inputStream;
            // get stream
            if (urlConnection.getResponseCode() < HttpURLConnection.HTTP_BAD_REQUEST) {
                inputStream = urlConnection.getInputStream();
            } else {
                inputStream = urlConnection.getErrorStream();
            }
            // parse stream
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String temp, response = "";
            while ((temp = bufferedReader.readLine()) != null) {
                response += temp;
                Log.i("teste_api", response);
                //JSONObject resp = new JSONObject(response);
                //JSONObject object = new JSONObject(resp.getString("objeto"));
                //Log.i("nome", object.getString("consumidor_nome"));
                //Log.i("email", object.getString("email_descricao"));
                //Log.i("senha", object.getString("usuario_senha"));

            }
            return response;


        } catch (IOException | JSONException e) {
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
                if (descricao.equals("Consumidor cadastrado com sucesso!")) {
                    CadastroConsumidor.pb.setVisibility(View.VISIBLE);
                    AlertDialog.Builder builder = new AlertDialog.Builder(CadastroConsumidor.context);
                    builder.setTitle("Conta registrada");
                    builder.setMessage("CadastroPessoaFisica realizado, confirme o acesso via e-mail");
                    builder.setPositiveButton("Fechar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                               dialog.dismiss();
                               listener.onLoaded("true");
                        }
                    });
                    builder.setCancelable(false);
                    builder.show();
                }

            } else if(descricao.equals("Acesso webservice negado!")) {
                CadastroConsumidor.pb.setVisibility(View.INVISIBLE);
                AlertDialog.Builder builder = new AlertDialog.Builder(CadastroConsumidor.context);
                builder.setTitle("");
                builder.setMessage("Erro ao tentar cadastrar");
                builder.setPositiveButton("Fechar",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        listener.onLoaded("true");
                    }
                });
                builder.setCancelable(false);
                builder.show();
            }

        } catch (Exception e) {
            e.printStackTrace();
            e.printStackTrace();
            CadastroConsumidor.pb.setVisibility(View.INVISIBLE);
            AlertDialog.Builder builder = new AlertDialog.Builder(CadastroConsumidor.context);
            builder.setTitle("Erro");
            builder.setMessage("Erro ao cadastrar os Dados");
            builder.setPositiveButton("Fechar",new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    dialog.dismiss();
                    listener.onLoaded("true");
                }
            });
            builder.setCancelable(false);
            builder.show();

        }

    }

}
