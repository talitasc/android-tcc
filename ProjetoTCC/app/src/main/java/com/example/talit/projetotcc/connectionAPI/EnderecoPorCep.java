package com.example.talit.projetotcc.connectionAPI;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

import com.example.talit.projetotcc.R;
import com.example.talit.projetotcc.activities.FinalizarCompra;
import com.example.talit.projetotcc.logicalView.Cep;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by talit on 04/11/2017.
 */

public class EnderecoPorCep extends AsyncTask<String, String, String> {

    private Listener mListener;
    public static final String ID_ESTABELECIMENTO = "ID";
    public static String idEstab;

    public interface Listener {

        public void onLoaded(String status);
    }

    public EnderecoPorCep(Listener mListener) {

        this.mListener = mListener;
        FinalizarCompra.pbAguardar.setVisibility(View.VISIBLE);
    }

    @Override
    protected String doInBackground(String... params) {

        String uri = "https://viacep.com.br/ws/" + params[0] + "/json/";
        try {
            URL url = new URL(uri);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            BufferedReader r = new BufferedReader(new InputStreamReader(in));

            StringBuilder jsonString = new StringBuilder();
            String line;
            while ((line = r.readLine()) != null) {
                jsonString.append(line);
            }

            urlConnection.disconnect();

            return jsonString.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);

        try {
            JSONObject api_result = new JSONObject(result);
            Log.i("api resulta viacep", api_result.toString());

            if (!result.contains("true")) {
                if (api_result.getString("cep") != null) {
                    FinalizarCompra.pbAguardar.setVisibility(View.INVISIBLE);
                    Cep cep = new Cep(api_result.getString("cep"), api_result.getString("logradouro"), api_result.getString("complemento"),
                            api_result.getString("bairro"), api_result.getString("localidade"), api_result.getString("uf"));
                    Log.i("rua", cep.getLocalidade());
                    FinalizarCompra.relatEndereco.setVisibility(View.VISIBLE);
                    FinalizarCompra.txtErroEndereco.setText("");
                    FinalizarCompra.getValues(cep.getLogradouro(), cep.getLocalidade(),
                            cep.getUf(), cep.getBairro());

                    SharedPreferences prefs = FinalizarCompra.act.getSharedPreferences(ID_ESTABELECIMENTO, MODE_PRIVATE);
                    idEstab = prefs.getString("idEstab", null);
                    Frete connFrete = new Frete();
                    connFrete.execute(idEstab,cep.getLocalidade());
                }
            } else {
                FinalizarCompra.pbAguardar.setVisibility(View.INVISIBLE);
                FinalizarCompra.txtErroEndereco.setText(R.string.ret_valida_end_cep);
                FinalizarCompra.getValues(null, null,
                        null, null);
                Log.i("rua", "nao foi possivel obter endereço");
            }

        } catch (Exception e) {
            e.printStackTrace();
            FinalizarCompra.pbAguardar.setVisibility(View.INVISIBLE);
            FinalizarCompra.txtErroEndereco.setText(R.string.ret_valida_busca);
            FinalizarCompra.getValues(null, null,
                    null, null);
        }
    }

}
