package com.example.talit.projetotcc.connectionAPI;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;

import com.example.talit.projetotcc.R;
import com.example.talit.projetotcc.activities.AlteraDadosConsumidor;
import com.example.talit.projetotcc.activities.FinalizarCompra;
import com.example.talit.projetotcc.logicalView.FreteCidades;

import org.json.JSONArray;
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

public class Frete extends AsyncTask<String, String, String> {


    private String estabelecimento_id;
    private String cidade_descricao;


    @Override
    protected String doInBackground(String... n) {

        String api_url = "http://www.mlprojetos.com/webservice/index.php/estabelecimento/getFreteByCidade";

        estabelecimento_id = n[0];
        cidade_descricao = n[1];

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
            jsonObject.accumulate("estabelecimento_id", estabelecimento_id);
            jsonObject.accumulate("cidade_descricao", cidade_descricao);
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

            }
            return response;


        } catch (IOException | JSONException e) {
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

            JSONObject status = new JSONObject(response);
            Log.i("Response", response);

            String status_est = status.getString("status");
            String descricao = status.getString("descricao");
            Log.i("Status", status_est);

            if (status_est.equalsIgnoreCase("true")) {
                //CadastroConsumidor.pb.setVisibility(View.VISIBLE);
                JSONArray dados = status.getJSONArray("objeto");

                FreteCidades fretesCidades = null;
                for (int i = 0; i < dados.length(); ++i) {
                    JSONObject dados_result = dados.getJSONObject(i);

                 fretesCidades = new FreteCidades(dados_result.getString("frete_valor"));
                }
                String valor = fretesCidades.getFrete_valor();

                FinalizarCompra.setFrete(valor);


            } else {

                FinalizarCompra.txtValor.setText(R.string.ret_no_cep);
                FinalizarCompra.txtValor.setTextColor(Color.RED);
                /*CadastroConsumidor.pb.setVisibility(View.INVISIBLE);
                AlertDialog.Builder builder = new AlertDialog.Builder(AlteraDadosConsumidor.context);
                builder.setTitle(AlteraDadosConsumidor.act.getString(R.string.txt_enviado_erro));
                builder.setMessage(AlteraDadosConsumidor.act.getString(R.string.txt_erro_alterados));
                builder.setPositiveButton("Ok", null);
                builder.setCancelable(false);
                builder.show();*/
            }

        } catch (Exception e) {
            e.printStackTrace();
            e.printStackTrace();
            //CadastroConsumidor.pb.setVisibility(View.INVISIBLE);
            AlertDialog.Builder builder = new AlertDialog.Builder(FinalizarCompra.context);
            builder.setTitle(AlteraDadosConsumidor.act.getString(R.string.txt_enviado_erro));
            builder.setMessage(AlteraDadosConsumidor.act.getString(R.string.txt_erro_alterados));
            builder.setPositiveButton("Ok", null);
            builder.setCancelable(false);
            builder.show();

        }
    }
}
