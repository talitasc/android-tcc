package com.example.talit.projetotcc.connectionAPI;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.util.Log;

import com.example.talit.projetotcc.R;
import com.example.talit.projetotcc.activities.CadastroConsumidor;
import com.example.talit.projetotcc.activities.Carrinho;
import com.example.talit.projetotcc.activities.DetalhesProdutos;
import com.example.talit.projetotcc.logicalView.CarrinhoItens;
import com.example.talit.projetotcc.sqlight.DbConn;
import com.example.talit.projetotcc.utils.Validacoes;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by talit on 20/11/2017.
 */

public class VerificaCarrinho extends AsyncTask<String, String, String> {

    @Override
    protected String doInBackground(String... params) {

        String api_url = "http://www.mlprojetos.com/webservice/index.php/carrinho/getCarrinhoByUsuario/" + params[0] + "/" + params[1];

        String response = "";

        HttpURLConnection urlConnection;

        try {

            URL url = new URL(api_url);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setDoOutput(true);
            urlConnection.connect();

            InputStream inputStream;
            // get stream
            if (urlConnection.getResponseCode() < HttpURLConnection.HTTP_BAD_REQUEST) {
                inputStream = urlConnection.getInputStream();
            } else {
                inputStream = urlConnection.getErrorStream();
            }
            // parse stream
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String temp;
            while ((temp = bufferedReader.readLine()) != null) {
                response += temp;
            }
            Log.i("response", response);
            return response;


        } catch (IOException e) {
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

                if (!descricao.equals("Carrinho nãoo encontrado!")) {
                    JSONArray dados = status.getJSONArray("objeto");
                    //JSONArray dados_result = new JSONObject(dados);

                    CarrinhoItens car = null;
                    for (int j = 0; j < dados.length(); ++j) {
                        JSONObject car_result = dados.getJSONObject(j);
                        car = new CarrinhoItens(car_result.getString("carrinho_id"));
                    }
                    final String idEstab = car.getCarrinho_id() + "";

                    AlertDialog.Builder builder = new AlertDialog.Builder(DetalhesProdutos.c);
                    builder.setTitle(R.string.erro_titulo);
                    builder.setMessage(R.string.erro_carrinho);
                    builder.setPositiveButton(R.string.positive_button, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            DeleteCarrinho connDel = new DeleteCarrinho(null);
                            connDel.execute(idEstab);

                        }
                    });
                    builder.setNegativeButton(R.string.negative_button, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();

                        }
                    });
                    builder.show();
                }
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(DetalhesProdutos.c);
                builder.setTitle("Erro");
                builder.setMessage("Ocorreu um erro...");
                builder.setPositiveButton("Fechar", new DialogInterface.OnClickListener() {
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
            AlertDialog.Builder builder = new AlertDialog.Builder(DetalhesProdutos.c);
            builder.setTitle("Erro");
            builder.setMessage("Ocorreu um erro...");
            builder.setPositiveButton("Fechar", new DialogInterface.OnClickListener() {
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
