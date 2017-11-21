package com.example.talit.projetotcc.connectionAPI;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

import com.example.talit.projetotcc.R;
import com.example.talit.projetotcc.activities.CadastroConsumidor;
import com.example.talit.projetotcc.activities.DetalhesProdutos;
import com.example.talit.projetotcc.activities.LoginCliente;
import com.example.talit.projetotcc.activities.LoginPessoaJuridica;
import com.example.talit.projetotcc.logicalView.CarrinhoItens;
import com.example.talit.projetotcc.logicalView.Sacola;
import com.example.talit.projetotcc.sqlight.DbConn;
import com.example.talit.projetotcc.utils.Validacoes;

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
import java.util.ArrayList;

/**
 * Created by talit on 20/10/2017.
 */

public class CriaCarrinho extends AsyncTask<String, String, String> {

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

    public CriaCarrinho(Listener listener) {
        this.listener = listener;

    }

    @Override
    protected String doInBackground(String... n) {
        String api_url = "http://www.mlprojetos.com/webservice/index.php/carrinho/inicializarCarrinho";
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
            //urlConnection.disconnect();
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
            Log.i("Response", response);

            String status_est = status.getString("status");
            String descricao = status.getString("descricao");
            String objeto = status.getString("objeto");
            Log.i("Status", status_est);

            if (status_est.equals("true")) {
                if (descricao.equals("Carrinho inicializado com sucesso!")) {
                    JSONObject dadosobjeto = new JSONObject(objeto);
                    JSONArray carrinho = dadosobjeto.getJSONArray("carrinho");

                    CarrinhoItens car = null;
                    for (int j = 0; j< carrinho.length(); ++j){
                        JSONObject car_result = carrinho.getJSONObject(j);
                        car = new CarrinhoItens(car_result.getString("carrinho_id"));
                    }
                    int idCarrinho = Integer.parseInt(car.getCarrinho_id());
                    dbconn.insertSacola(idCarrinho, Integer.parseInt(DetalhesProdutos.strIdProd),
                            DetalhesProdutos.strnomeProd.trim(), DetalhesProdutos.strMarca.trim(),
                            Double.parseDouble(DetalhesProdutos.strPreco.replace("R$ ", "")),
                            Double.parseDouble(DetalhesProdutos.strPreco.replace("R$ ", "")),
                            "g",Integer.parseInt(DetalhesProdutos.strQuantidade),
                            DetalhesProdutos.strImagem,DetalhesProdutos.strQtd.trim());

                    Validacoes.showSnackBar(DetalhesProdutos.c,DetalhesProdutos.cord,DetalhesProdutos.act.getResources().getString(R.string.prod_add));

                }
            }else{
                String idUser = dbconn.selectConsumidor().getIdCons()+"";
                String tpUser = dbconn.selectConsumidor().getTpAcesso()+"";
                VerificaCarrinho connVerifica = new VerificaCarrinho();
                connVerifica.execute(idUser,tpUser);
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
