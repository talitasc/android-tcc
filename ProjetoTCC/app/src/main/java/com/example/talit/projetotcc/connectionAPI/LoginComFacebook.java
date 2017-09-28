package com.example.talit.projetotcc.connectionAPI;

import android.app.AlertDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

import com.example.talit.projetotcc.activities.LoginCliente;
import com.example.talit.projetotcc.sqlight.DbConn;
import com.example.talit.projetotcc.sqlight.MantemConsumidor;

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
 * Created by talit on 24/09/2017.
 */

public class LoginComFacebook extends AsyncTask<String, String, String> {

    private String usuario_login;
    private String origin;
    private String nome;
    private String sobrenome;
    private DbConn dbconn;
    private AutenticaLogin.Listener listener;
    public String status = "nao";

    @Override
    protected String doInBackground(String... n) {
        String api_url = "http://www.mlprojetos.com/webservice/index.php/acesso/autenticarfacebook";

        usuario_login = n[0];
        origin = n[1];
        nome = n[2];
        sobrenome = n[3];


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
            jsonObject.accumulate("usuario_login", usuario_login);
            jsonObject.accumulate("origin", origin);
            jsonObject.accumulate("nome", nome);
            jsonObject.accumulate("sobrenome", sobrenome);
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
        dbconn = new DbConn(LoginCliente.context);
        status = "sim";
        try {
            JSONObject api_result = new JSONObject(result);
            String response = api_result.getString("response");

            JSONObject status = new JSONObject(response);
            Log.i("Response", response);
            String status_user = status.getString("status");
            String descricao = status.getString("descricao");
            Log.i("Status",status_user);

            if (status_user.equals("true")) {
                String dados = status.getString("objeto");
                JSONObject dados_result = new JSONObject(dados);

                //MantemConsumidor mac = new MantemConsumidor(dados_result.getInt("usuario_id"), dados_result.getString("usuario_login"),
                        //dados_result.getString("usuario_senha"), dados_result.getInt("status_id"), dados_result.getInt("tipo_usuario_id"));

                if (descricao.equals("Login com facebook realizado com sucesso!")) {
                    //dbconn.insertConsumidor(mac.getIdCons(), mac.getUsuario(), mac.getSenha(), mac.getStatus(), mac.getTpAcesso());
                    if (listener != null) {
                        listener.onLoaded("true");
                    }
                } else {
                    LoginCliente.pb.setVisibility(View.INVISIBLE);
                    AlertDialog.Builder builder = new AlertDialog.Builder(LoginCliente.context);
                    builder.setTitle("Ative sua conta");
                    builder.setMessage("É necessário ativar sua conta com o link enviado em seu e-mail");
                    builder.setPositiveButton("Fechar", null);
                    builder.setCancelable(false);
                    builder.show();
                    if (listener != null) {
                        listener.onLoaded("false");
                    }
                }
            } else if(descricao.equals("Senha inválida!")) {
                LoginCliente.pb.setVisibility(View.INVISIBLE);
                AlertDialog.Builder builder = new AlertDialog.Builder(LoginCliente.context);
                builder.setTitle("");
                builder.setMessage("Senha incorreta");
                builder.setPositiveButton("Fechar", null);
                builder.setCancelable(false);
                builder.show();
                if (listener != null) {
                    listener.onLoaded("false");
                }
            }else if(descricao.equals("Usuario não encontrato!")){
                LoginCliente.pb.setVisibility(View.INVISIBLE);
                AlertDialog.Builder builder = new AlertDialog.Builder(LoginCliente.context);
                builder.setTitle("");
                builder.setMessage("E-mail não cadastrado");
                builder.setPositiveButton("Fechar", null);
                builder.setCancelable(false);
                builder.show();
                if (listener != null) {
                    listener.onLoaded("false");
                }
            }else if(descricao.equals("Requisição invalida!")){
                LoginCliente.pb.setVisibility(View.INVISIBLE);
                AlertDialog.Builder builder = new AlertDialog.Builder(LoginCliente.context);
                builder.setTitle("");
                builder.setMessage("Dados não cadastrados!");
                builder.setPositiveButton("Fechar", null);
                builder.setCancelable(false);
                builder.show();
                if (listener != null) {
                    listener.onLoaded("false");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            LoginCliente.pb.setVisibility(View.INVISIBLE);
            AlertDialog.Builder builder = new AlertDialog.Builder(LoginCliente.context);
            builder.setTitle("Erro");
            builder.setMessage("Erro ao Carregar Dados");
            builder.setPositiveButton("Fechar", null);
            builder.setCancelable(false);
            builder.show();
            if (listener != null) {
                listener.onLoaded("false");
            }

        }

    }
}
