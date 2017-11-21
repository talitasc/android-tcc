package com.example.talit.projetotcc.connectionAPI;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.util.Log;

import com.example.talit.projetotcc.R;
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
 * Created by talit on 20/11/2017.
 */

public class InsereProdutoCarrinho extends AsyncTask<String, String, String> {


    private String carrinho_id;
    private String lote_id;
    private String quantidade;
    private DbConn dbconn;
    @Override
    protected String doInBackground(String... n) {

        String api_url = "http://www.mlprojetos.com/webservice/index.php/carrinho/inserirProdutoCarrinho";
        carrinho_id = n[0];
        lote_id = n[1];
        quantidade = n[2];


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
            Log.i("C do Response", response);
            String status_user = status.getString("status");
            String descricao = status.getString("descricao");
            Log.i("Status", status_user);

            if (status_user.equalsIgnoreCase("true")) {
                if (descricao.equals("Produto adicionado no carrinho com sucesso!")) {
                    dbconn.insertSacola(Integer.parseInt(carrinho_id), Integer.parseInt(DetalhesProdutos.strIdProd),
                            DetalhesProdutos.strnomeProd, DetalhesProdutos.strMarca, Double.parseDouble(DetalhesProdutos.strPreco.replace("R$", "")), Double.parseDouble(DetalhesProdutos.strPreco.replace("R$", "")),DetalhesProdutos.strUmed,Integer.parseInt(DetalhesProdutos.strQuantidade), DetalhesProdutos.strImagem,DetalhesProdutos.strQtd);
                    Validacoes.showSnackBar(DetalhesProdutos.c,DetalhesProdutos.cord,DetalhesProdutos.act.getResources().getString(R.string.prod_add));

                }
            }else{
                AlertDialog.Builder builder = new AlertDialog.Builder(CadastroConsumidor.context);
                builder.setTitle("Erro");
                builder.setMessage("Ocorreu um erro...");
                builder.setPositiveButton("Fechar",new DialogInterface.OnClickListener() {
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
            AlertDialog.Builder builder = new AlertDialog.Builder(CadastroConsumidor.context);
            builder.setTitle("Erro");
            builder.setMessage("Ocorreu um erro...");
            builder.setPositiveButton("Fechar",new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    dialog.dismiss();

                }
            });
            builder.setCancelable(false);
            builder.show();

        }

    }
}
