package com.example.talit.projetotcc.connectionAPI;

import android.app.AlertDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

import com.example.talit.projetotcc.activities.LoginCliente;
import com.example.talit.projetotcc.activities.LoginPessoaJuridica;
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
 * Created by talit on 30/09/2017.
 */

public class AutenticaPj extends AsyncTask<String, String, String> {


    private String login;
    private String senha;
    private DbConn dbconn;
    private Listener listener;
    public String status = "nao";


    public interface Listener {

        public void onLoaded(String string);
    }

    public AutenticaPj(Listener listener) {
        this.listener = listener;

    }

    @Override
    protected String doInBackground(String... n) {

        String api_url = "http://www.mlprojetos.com/webservice/index.php/acesso/autenticar";

        login = n[0];
        senha = n[1];

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
            jsonObject.accumulate("usuario_login", login);
            jsonObject.accumulate("usuario_senha", senha);

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

        dbconn = new DbConn(LoginPessoaJuridica.context);
        status = "sim";
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

                MantemConsumidor mac = new MantemConsumidor(Integer.parseInt(dados_result.getString("usuario_id")), dados_result.getString("usuario_login"),
                        dados_result.getString("usuario_senha"), Integer.parseInt(dados_result.getString("status_id")), Integer.parseInt(dados_result.getString("tipo_usuario_id")));

                if (mac.getStatus() == 2) {
                    dbconn.insertConsumidor(mac.getIdCons(), mac.getUsuario(), mac.getSenha(), mac.getStatus(), mac.getTpAcesso());
                    if (listener != null) {
                        listener.onLoaded("true");
                    }
                } else {
                    LoginPessoaJuridica.pb.setVisibility(View.INVISIBLE);
                    AlertDialog.Builder builder = new AlertDialog.Builder(LoginPessoaJuridica.context);
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
                LoginPessoaJuridica.pb.setVisibility(View.INVISIBLE);
                AlertDialog.Builder builder = new AlertDialog.Builder(LoginPessoaJuridica.context);
                builder.setTitle("");
                builder.setMessage("Senha incorreta");
                builder.setPositiveButton("Fechar", null);
                builder.setCancelable(false);
                builder.show();
                if (listener != null) {
                    listener.onLoaded("false");
                }
            }else if(descricao.equals("Usuario não encontrato!")){
                LoginPessoaJuridica.pb.setVisibility(View.INVISIBLE);
                AlertDialog.Builder builder = new AlertDialog.Builder(LoginPessoaJuridica.context);
                builder.setTitle("");
                builder.setMessage("E-mail não cadastrado");
                builder.setPositiveButton("Fechar", null);
                builder.setCancelable(false);
                builder.show();
                if (listener != null) {
                    listener.onLoaded("false");
                }
            }else if(descricao.equals("Requisição invalida!")){
                LoginPessoaJuridica.pb.setVisibility(View.INVISIBLE);
                AlertDialog.Builder builder = new AlertDialog.Builder(LoginPessoaJuridica.context);
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
            LoginPessoaJuridica.pb.setVisibility(View.INVISIBLE);
            AlertDialog.Builder builder = new AlertDialog.Builder(LoginPessoaJuridica.context);
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
