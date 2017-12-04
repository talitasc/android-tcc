package com.example.talit.projetotcc.connectionAPI;

import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

import com.example.talit.projetotcc.activities.DetalhesProdutos;
import com.example.talit.projetotcc.activities.PaginalnicialConsumidor;
import com.example.talit.projetotcc.adapters.ListaSupermercadosAdapter;
import com.example.talit.projetotcc.fragments.DetalhesEstab;
import com.example.talit.projetotcc.logicalView.Endereco;
import com.example.talit.projetotcc.logicalView.Estabelecimento;
import com.example.talit.projetotcc.logicalView.Telefone;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by talit on 02/12/2017.
 */

public class EstabelecimentoPorId extends AsyncTask<String, String, String> {

    private Listener mListener;

    public interface Listener {

        public void onLoaded(List<Estabelecimento> estab);
    }
    public EstabelecimentoPorId(Listener mListener){
        this.mListener = mListener;
        DetalhesEstab.progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    protected String doInBackground(String... params) {

        String api_url = "http://www.mlprojetos.com/webservice/index.php/estabelecimento/getEstabelecimentoVendedor/" + params[0] + "/";

        String response = "";

        HttpURLConnection urlConnection;

        try {

            URL url = new URL(api_url);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setDoOutput(true);
            urlConnection.connect();

            InputStream inputStream;
            if (urlConnection.getResponseCode() < HttpURLConnection.HTTP_BAD_REQUEST) {
                inputStream = urlConnection.getInputStream();
            } else {
                inputStream = urlConnection.getErrorStream();
            }
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
        super.onPostExecute(result);

        try {

            JSONObject api_result = new JSONObject(result);
            String response = api_result.getString("response");

            JSONObject status = new JSONObject(response);
            Log.i("Response", response);

            String status_est = status.getString("status");
            String descricao = status.getString("descricao");
            Log.i("Status", status_est);

            if (status_est.equals("true")) {
                DetalhesEstab.progressBar.setVisibility(View.INVISIBLE);
                JSONArray dados = status.getJSONArray("objeto");
                Estabelecimento estabelecimentos = null;
                for (int i = 0; i < dados.length(); ++i) {
                    JSONObject dados_result = dados.getJSONObject(i);
                    String endereco = dados_result.getString("endereco");
                    String telefone = dados_result.getString("telefone");

                    JSONObject end_result = new JSONObject(endereco);
                    Endereco end = new Endereco(end_result.getString("endereco_rua"),
                            end_result.getString("endereco_numero"), end_result.getString("endereco_bairro"),
                            end_result.getString("endereco_complemento"), end_result.getString("endereco_cep"),
                            end_result.getString("cidade_descricao"), end_result.getString("estado_sigla"));


                    JSONObject tel_result = new JSONObject(telefone);
                    Telefone tel = new Telefone(tel_result.getString("telefone_ddd"), tel_result.getString("telefone_numero"), tel_result.getString("tipo_telefone_descricao"));

                    estabelecimentos = new Estabelecimento(dados_result.getInt("estabelecimento_id"),
                            dados_result.getString("estabelecimento_cnpj"),
                            dados_result.getString("estabelecimento_razao_social"),
                            dados_result.getString("estabelecimento_nome_fantasia"),
                            dados_result.getString("estabelecimento_inscricao_estadual"),
                            dados_result.getString("estabelecimento_inscricao_municipal"),
                            dados_result.getString("estabelecimento_vendedor"),
                            dados_result.getString("tipo_estabelecimento_descricao"),
                            dados_result.getString("email_contato"),
                            end.getRua(), end.getNumero(),
                            end.getBairro(), end.getComplemento(),
                            end.getCep(),end.getCidade_descricao(),end.getEstado_sigla(),
                            tel.getIdTf(), tel.getDdd(), tel.getNumeroTelefone(),
                            dados_result.getString("estabelecimento_logo"),dados_result.getString("media_nota"),
                            dados_result.getString("estabelecimento_banner"));
                }

                DetalhesEstab.preencheDados(estabelecimentos.getNome_fantasia(),estabelecimentos.getRua(),estabelecimentos.getNumero(),
                        estabelecimentos.getBairro(),estabelecimentos.getCep(),estabelecimentos.getCidade(),estabelecimentos.getEstado_sigla(),
                        estabelecimentos.getDd(),estabelecimentos.getTelefone(),estabelecimentos.getEmail());

            } else if (descricao.contains("Nenhum")) {
                DetalhesEstab.progressBar.setVisibility(View.INVISIBLE);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            DetalhesEstab.progressBar.setVisibility(View.INVISIBLE);
            if (mListener != null) {
                mListener.onLoaded(null);
            }
        }
    }
}
