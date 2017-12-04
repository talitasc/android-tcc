package com.example.talit.projetotcc.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.talit.projetotcc.R;
import com.example.talit.projetotcc.mascaras.MascaraCnpJ;
import com.example.talit.projetotcc.mascaras.MascaraTelefone;
import com.example.talit.projetotcc.utils.Validacoes;



public class CadastroPessoaJuridica extends AppCompatActivity {

    private Button btnContinuar;
    private TextWatcher twCpnj;
    private EditText edtNomeFantasia;
    private EditText edtRazaoSocial;
    private EditText edtTelefone;
    private EditText edtCnpj;
    private String strCnpj;
    private boolean haNf;
    private boolean haRs;
    private boolean haIe;
    private boolean haIm;
    private boolean haCnpj;
    private String telStr;
    private String[] tpTel;
    private String esTel;
    private TextWatcher twTel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_cadastro_pessoa_juridica);
        btnContinuar = (Button) findViewById(R.id.btn_proximo);
        edtNomeFantasia = (EditText) findViewById(R.id.edt_nome_fantasia);
        edtRazaoSocial = (EditText) findViewById(R.id.ed_razao_social);
        edtTelefone = (EditText) findViewById(R.id.ed_telefones);
        edtCnpj = (EditText) findViewById(R.id.ed_cnpj);

        twCpnj = MascaraCnpJ.insert("##.###.###/####-##", edtCnpj);
        edtCnpj.addTextChangedListener(twCpnj);
        twTel = MascaraTelefone.insert("(##)####-####", edtTelefone);
        edtTelefone.addTextChangedListener(twTel);

        haNf = false;
        haRs = false;
        haIe = false;
        haIm = false;
        haCnpj = false;
        edtNomeFantasia.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (TextUtils.isEmpty(s)) {
                    edtNomeFantasia.setError("Campo Obrigatorio");
                    Validacoes.requestFocus(edtNomeFantasia);
                    haNf = true;

                } else if (s.length() > 150) {
                    edtNomeFantasia.setError("Nome muito grande");
                    Validacoes.requestFocus(edtNomeFantasia);
                    haNf = true;
                }else{
                    haNf = false;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {



            }
        });
        edtRazaoSocial.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (TextUtils.isEmpty(s)) {
                    edtRazaoSocial.setError("Campo Obrigatorio");
                    Validacoes.requestFocus(edtRazaoSocial);
                    haRs = true;

                } else if (s.length() > 150) {
                    edtRazaoSocial.setError("Campo Obrigatorio");
                    Validacoes.requestFocus(edtRazaoSocial);
                    haRs = true;
                }else{
                    haRs = false;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        edtTelefone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (TextUtils.isEmpty(edtTelefone.getText().toString())) {
                    edtTelefone.setError("Campo Obrigatorio");
                    Validacoes.requestFocus(edtTelefone);
                    haIe = true;


                } else if (edtTelefone.getText().toString().length()!= 13) {
                    edtTelefone.setError("Telefone invalido");
                    Validacoes.requestFocus(edtTelefone);
                    haIe = true;

                }else{
                    haIe=false;
                    edtTelefone.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        edtCnpj.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                strCnpj = edtCnpj.getText().toString();
                if (TextUtils.isEmpty(s)) {
                    edtCnpj.setError("Campo Obrigatorio");
                    Validacoes.requestFocus(edtCnpj);
                    haCnpj = true;

                }else if(strCnpj.length() != 18){
                    edtCnpj.setError("CNPJ invalido");
                    Validacoes.requestFocus(edtCnpj);
                    haCnpj = true;

                } else {
                    haCnpj = false;
                    edtCnpj.setError(null);

                }
            }

            @Override
            public void afterTextChanged(Editable s) {


            }
        });
        btnContinuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validaEntradas();

            }
        });
    }

    public void validaEntradas(){

        Log.i("Nome fantasia:",haNf+"");
        Log.i("razao social:",haRs+ "");
        Log.i("Isc estadual:",haIe + "");
        Log.i("Im:",haIm + "");
        Log.i("cnpj:" ,haCnpj+ "");

        if(!haNf && !haRs && !haIe && !haIm && !haCnpj) {

            if (!TextUtils.isEmpty(edtRazaoSocial.getText().toString()) &&
                    !TextUtils.isEmpty(edtNomeFantasia.getText().toString()) && !TextUtils.isEmpty(edtCnpj.getText().toString()) &&
                    !TextUtils.isEmpty(edtTelefone.getText().toString())) {
                String telefoneCompleto = edtTelefone.getText().toString().replace("(","").replace(")","").replace("-","");
                String dd = telefoneCompleto.substring(0,2);
                String telefone = telefoneCompleto.substring(2,telefoneCompleto.length());
                String cpf = edtCnpj.getText().toString().replace(".","").replace("/","").replace("-","");
                Intent intent = new Intent();
                intent.setClass(CadastroPessoaJuridica.this,CadastroPessoaJuridicaDois.class);
                intent.putExtra("CNPJ",cpf);
                intent.putExtra("RAZAO_SOCIAL",edtRazaoSocial.getText().toString());
                intent.putExtra("NOME_FANTASIA",edtNomeFantasia.getText().toString());
                intent.putExtra("DD",dd);
                intent.putExtra("TELEFONE",telefone);
                CadastroPessoaJuridica.this.startActivity(intent);
                CadastroPessoaJuridica.this.finish();

            } else {
                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(CadastroPessoaJuridica.this);
                builder.setTitle(R.string.validacao_login_um);
                builder.setMessage(R.string.validacao_login_dois);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.setCancelable(false);
                builder.show();

            }
        }else{
            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(CadastroPessoaJuridica.this);
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
    }
    @Override
    public void onBackPressed() {
        //super.onBackPressed();

        AlertDialog.Builder builder = new AlertDialog.Builder(CadastroPessoaJuridica.this);
        builder.setTitle("");
        builder.setMessage(R.string.validacao_login_quinze);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                onBackPressed();
                startActivity(new Intent(CadastroPessoaJuridica.this, RedirecionaPessoaJuridica.class));
                dialog.dismiss();
                finish();
            }
        });
        builder.setNegativeButton("Nao", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

            }
        });
        builder.show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            default:break;
        }
        return true;
    }
}
