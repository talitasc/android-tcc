package com.example.talit.projetotcc.connectionAPI;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

import com.example.talit.projetotcc.R;
import com.example.talit.projetotcc.activities.CadastroConsumidor;
import com.example.talit.projetotcc.activities.CadastroPessoaJuridica;
import com.example.talit.projetotcc.activities.CadastroPessoaJuridicaDois;
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
 * Created by talit on 28/09/2017.
 */

public class EstabelecimentoComprador extends AsyncTask<String, String, String> {

    private String estabelecimento_cnpj;
    private String estabelecimento_razao_social;
    private String estabelecimento_nome_fantasia;
    private String tipo_estabelecimento_id;
    private String telefone_ddd;
    private String telefone_numero;
    private String tipo_telefone_id;
    private String funcionario_nome;
    private String funcionario_sobrenome;
    private String funcionario_cpf;
    private String cargo_id;
    private String tipo_usuario;
    private String usuario_email;
    private String usuario_senha;
    private Listener listener;

    public interface Listener {

        public void onLoaded(String string);
    }

    public EstabelecimentoComprador(Listener listener) {
        this.listener = listener;

    }

    @Override
    protected String doInBackground(String... n) {
        String api_url = "http://www.mlprojetos.com/webservice/index.php/estabelecimento/adicionarComprador";
        estabelecimento_cnpj = n[0];
        estabelecimento_razao_social = n[1];
        estabelecimento_nome_fantasia = n[2];
        tipo_estabelecimento_id = n[3];
        telefone_ddd = n[4];
        telefone_numero = n[5];
        tipo_telefone_id = n[6];
        funcionario_nome = n[7];
        funcionario_sobrenome = n[8];
        funcionario_cpf = n[9];
        cargo_id = n[10];
        tipo_usuario = n[11];
        usuario_email = n[12];
        usuario_senha = n[13];

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
            jsonObject.accumulate("estabelecimento_cnpj", estabelecimento_cnpj);
            jsonObject.accumulate("estabelecimento_razao_social", estabelecimento_razao_social);
            jsonObject.accumulate("estabelecimento_nome_fantasia", estabelecimento_nome_fantasia);
            jsonObject.accumulate("tipo_estabelecimento_id", tipo_estabelecimento_id);
            jsonObject.accumulate("funcionario_nome", funcionario_nome);
            jsonObject.accumulate("funcionario_sobrenome", funcionario_sobrenome);
            jsonObject.accumulate("funcionario_cpf", funcionario_cpf);
            jsonObject.accumulate("cargo_id", cargo_id);
            jsonObject.accumulate("tipo_telefone_id", tipo_telefone_id);
            jsonObject.accumulate("telefone_ddd", telefone_ddd);
            jsonObject.accumulate("telefone_numero", telefone_numero);
            jsonObject.accumulate("usuario_email", usuario_email);
            jsonObject.accumulate("usuario_senha", usuario_senha);
            jsonObject.accumulate("tipo_usuario", tipo_usuario);

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
                JSONObject resp = new JSONObject(response);
                Log.i("teste_api", response);


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
            Log.i("Response", response);
            String status_user = status.getString("status");
            String descricao = status.getString("descricao");
            Log.i("Status", status_user);
            if (status_user.equalsIgnoreCase("true")) {
                if (descricao.contains("sucesso")) {
                    CadastroPessoaJuridicaDois.pb.setVisibility(View.INVISIBLE);
                    AlertDialog.Builder builder = new AlertDialog.Builder(CadastroPessoaJuridicaDois.context);
                    builder.setTitle("Dados Registrados");
                    builder.setMessage("Cadastro realizado com sucesso!");
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
            }else if(descricao.contains("Estabelecimento")){
                CadastroPessoaJuridicaDois.pb.setVisibility(View.INVISIBLE);
                AlertDialog.Builder builder = new AlertDialog.Builder(CadastroPessoaJuridicaDois.context);
                builder.setTitle("");
                builder.setMessage("usuario ja cadastrado!");
                builder.setPositiveButton("Fechar",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        listener.onLoaded("true");
                    }
                });
                builder.setCancelable(false);
                builder.show();

            }else{
                CadastroPessoaJuridicaDois.pb.setVisibility(View.INVISIBLE);
                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(CadastroPessoaJuridicaDois.context);
                builder.setTitle(R.string.validacao_login_tres);
                builder.setMessage(R.string.validacao_login_quatro);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.setCancelable(false);
                builder.show();
            }

        } catch (Exception e) {
            e.printStackTrace();
            e.printStackTrace();
            CadastroPessoaJuridicaDois.pb.setVisibility(View.INVISIBLE);
            AlertDialog.Builder builder = new AlertDialog.Builder(CadastroPessoaJuridicaDois.context);
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
