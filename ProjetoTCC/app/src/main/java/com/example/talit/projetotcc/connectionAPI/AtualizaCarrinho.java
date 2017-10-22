package com.example.talit.projetotcc.connectionAPI;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.util.Log;

import com.example.talit.projetotcc.activities.CadastroConsumidor;
import com.example.talit.projetotcc.activities.DetalhesProdutos;
import com.example.talit.projetotcc.sqlight.DbConn;
import com.example.talit.projetotcc.utils.Validacoes;

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
 * Created by talit on 20/10/2017.
 */

public class AtualizaCarrinho extends AsyncTask<String, String, String> {

    private String usuario_id;
    private String tipo_usuario_id;
    private String estabelecimento_id;
    private String lote_id;
    private String quantidade;
    private Listener listener;
    private DbConn dbconn;

    public interface Listener {

        public void onLoaded(String string);
    }

    public AtualizaCarrinho(Listener listener) {
        this.listener = listener;

    }

    @Override
    protected String doInBackground(String... n) {
        String api_url = "http://www.mlprojetos.com/webservice/index.php/carrinho/atualizarCarrinho";
        usuario_id = n[0];
        tipo_usuario_id = n[1];
        estabelecimento_id = n[2];
        lote_id = n[3];
        quantidade = n[4];

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
            jsonObject.accumulate("estabelecimento_id", estabelecimento_id);
            jsonObject.accumulate("lote_id", lote_id);
            jsonObject.accumulate("quantidade", quantidade);
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

        dbconn = new DbConn(DetalhesProdutos.c);
        try {
            JSONObject api_result = new JSONObject(result);
            String response = api_result.getString("response");
            JSONObject status = new JSONObject(response);
            Log.i("Começo do Response", response);
            String status_user = status.getString("status");
            String descricao = status.getString("descricao");
            Log.i("Status", status_user);
            if (status_user.equalsIgnoreCase("true")) {
                if (descricao.equals("Carrinho inicializado com sucesso!")) {
                    Validacoes.showSnackBar(DetalhesProdutos.c,DetalhesProdutos.cord,"Produto adicionado ao carrinho");
                    dbconn.insertSacola(Integer.parseInt(DetalhesProdutos.strIdProd), Integer.parseInt(DetalhesProdutos.strIdProd),
                            DetalhesProdutos.strnomeProd, DetalhesProdutos.strMarca, Double.parseDouble(DetalhesProdutos.strPreco.replace("R$", "")), 1, DetalhesProdutos.strImagem);
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
            e.printStackTrace();
            AlertDialog.Builder builder = new AlertDialog.Builder(CadastroConsumidor.context);
            builder.setTitle("Erro");
            builder.setMessage("Ocorreu um erro...");
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
