package com.example.talit.projetotcc.activities;

import android.app.AlertDialog;
import android.content.Context;
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
import android.widget.ProgressBar;

import com.example.talit.projetotcc.R;
import com.example.talit.projetotcc.utils.Validacoes;
import com.example.talit.projetotcc.connectionAPI.EstabelecimentoComprador;
import com.example.talit.projetotcc.mascaras.MascaraCpf;

public class CadastroPessoaJuridicaDois extends AppCompatActivity implements EstabelecimentoComprador.Listener {

    private Button btnCadastrar;
    private TextWatcher txCpf;
    private EditText edtNomeFunc;
    private EditText edtSobrenome;
    private EditText edtCpf;
    private EditText edtUsuario;
    private EditText edtSenha;
    private EditText edtConfirmarSenha;
    private boolean haNome;
    private boolean haSobrenome;
    private boolean haCpf;
    private boolean haUsuario;
    private boolean haSenha;
    private boolean haConfirSenha;
    private String strRazaoSocial;
    private String strNomeFant;
    private String strTel;
    private String strDD;
    private String strCnpj;
    public static Context context;
    public static ProgressBar pb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_cadastro_pessoa_juridica_dois);
        context = this;
        btnCadastrar = (Button) findViewById(R.id.btn_cadastrar);
        edtNomeFunc = (EditText) findViewById(R.id.edt_nome_funcionario);
        edtSobrenome = (EditText) findViewById(R.id.edt_sobrenome_funcionario);
        edtCpf = (EditText) findViewById(R.id.ed_cpf_funcionario);
        edtUsuario = (EditText) findViewById(R.id.edt_user_funcionario);
        edtSenha = (EditText) findViewById(R.id.edt_senha_funcionario);
        edtConfirmarSenha = (EditText) findViewById(R.id.edt_senha_confir);
        pb = (ProgressBar) findViewById(R.id.pb_cadastro);
        pb.setVisibility(View.INVISIBLE);

        txCpf = MascaraCpf.insert("###.###.###-##", edtCpf);
        edtCpf.addTextChangedListener(txCpf);

        haNome = false;
        haSobrenome = false;
        haCpf = false;
        haUsuario = false;
        haSenha = false;
        haConfirSenha = false;

        if (getIntent().hasExtra("CNPJ") && getIntent().hasExtra("RAZAO_SOCIAL") &&
                getIntent().hasExtra("NOME_FANTASIA") && getIntent().hasExtra("DD") && getIntent().hasExtra("TELEFONE")) {
            strRazaoSocial = getIntent().getStringExtra("RAZAO_SOCIAL");
            strNomeFant = getIntent().getStringExtra("NOME_FANTASIA");
            strDD = getIntent().getStringExtra("DD");
            strTel = getIntent().getStringExtra("TELEFONE");
            strCnpj = getIntent().getStringExtra("CNPJ");
        }
        edtNomeFunc.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if (TextUtils.isEmpty(charSequence)) {
                    edtNomeFunc.setError("Campo Obrigatorio");
                    Validacoes.requestFocus(edtNomeFunc);
                    haNome = true;

                } else if (charSequence.length() > 150) {
                    edtNomeFunc.setError("Campo Obrigatorio");
                    Validacoes.requestFocus(edtNomeFunc);
                    haNome = true;
                }else{
                    haNome = false;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {


            }
        });
        edtSobrenome.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(s.toString().trim())) {
                    edtSobrenome.setError("Campo Obrigatorio");
                    Validacoes.requestFocus(edtSobrenome);
                    haSobrenome = true;

                } else if (s.length() > 150) {
                    edtSobrenome.setError("Campo Obrigatorio");
                    Validacoes.requestFocus(edtSobrenome);
                    haSobrenome = true;
                }else{
                    haSobrenome = false;
                }
            }
        });
        edtCpf.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if (TextUtils.isEmpty(edtCpf.getText().toString())) {
                    edtCpf.setError("Campo Obrigatorio");
                    Validacoes.requestFocus(edtCpf);
                    haCpf = true;

                } else if (edtCpf.length() != 14) {
                    edtCpf.setError("CPF Invalido");
                    Validacoes.requestFocus(edtCpf);
                    haCpf = true;
                } else {
                    haCpf = false;
                    edtCpf.setError(null);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        edtUsuario.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if (TextUtils.isEmpty(charSequence.toString().trim())) {
                    edtUsuario.setError("Campo Obrigatorio");
                    Validacoes.requestFocus(edtUsuario);
                    haUsuario = true;

                } else if (charSequence.length() > 150) {
                    edtUsuario.setError("Campo Obrigatorio");
                    Validacoes.requestFocus(edtUsuario);
                    haUsuario = true;
                } else if (!Validacoes.validaEmail(charSequence.toString().trim())) {
                    edtUsuario.setError("E-mail digitado incorretamente");
                    Validacoes.requestFocus(edtUsuario);
                    haUsuario = true;
                }else{
                    haUsuario = false;
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        edtSenha.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence chars, int i, int i1, int i2) {

                if (TextUtils.isEmpty(edtSenha.toString().trim())) {
                    edtSenha.setError("Campo Obrigatorio");
                    Validacoes.requestFocus(edtSenha);
                    haSenha = true;

                } else if (chars.length() <= 4) {
                    edtSenha.setError("Tamanho pequeno");
                    Validacoes.requestFocus(edtSenha);
                    haSenha = true;
                } else {
                    haSenha = false;
                    edtSenha.setError(null);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        edtConfirmarSenha.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if (TextUtils.isEmpty(edtConfirmarSenha.getText().toString())) {
                    edtConfirmarSenha.setError("Campo Obrigatorio");
                    Validacoes.requestFocus(edtConfirmarSenha);
                    haConfirSenha = true;

                } else if (edtConfirmarSenha.length() <= 4) {
                    edtConfirmarSenha.setError("Tamanho muito pequeno");
                    Validacoes.requestFocus(edtConfirmarSenha);
                    haConfirSenha = true;

                } else if (!edtConfirmarSenha.getText().toString().equals(edtSenha.getText().toString())) {

                    edtConfirmarSenha.setError("As senhas devem ser identicas");
                    Validacoes.requestFocus(edtConfirmarSenha);
                    haConfirSenha = true;
                } else {
                    haConfirSenha = false;
                    edtConfirmarSenha.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validaEntrada();
            }
        });
    }

    public void validaEntrada() {

        if(!haNome && !haSobrenome && !haCpf && !haUsuario && !haSenha && !haConfirSenha) {

            if (!TextUtils.isEmpty(edtNomeFunc.getText().toString()) &&
                    !TextUtils.isEmpty(edtSobrenome.getText().toString()) &&
                    !TextUtils.isEmpty(edtCpf.getText().toString()) && !TextUtils.isEmpty(edtUsuario.getText().toString()) &&
                    !TextUtils.isEmpty(edtSenha.getText().toString()) && !TextUtils.isEmpty(edtConfirmarSenha.getText().toString())) {
                String cpf = edtCpf.getText().toString().replace(".", "").replace("-", "");

                Log.i("CNPJ", strCnpj);
                Log.i("NOME FANT", strNomeFant);
                Log.i("dd", strDD);
                Log.i("telefone", strTel);
                Log.i("nome fun", edtNomeFunc.getText().toString());
                Log.i("Sobrenome", edtSobrenome.getText().toString());
                Log.i("Cpf", cpf);
                Log.i("Usuario", edtUsuario.getText().toString());
                Log.i("Senha", edtSenha.getText().toString());

                if (Validacoes.verifyConnection(CadastroPessoaJuridicaDois.this)) {
                    pb.setVisibility(View.VISIBLE);
                    EstabelecimentoComprador conn = new EstabelecimentoComprador(CadastroPessoaJuridicaDois.this);
                    conn.execute(strCnpj, strRazaoSocial, strNomeFant, "1", strDD, strTel, "1", edtNomeFunc.getText().toString(),
                            edtSobrenome.getText().toString(), cpf, "2", "3",
                            edtUsuario.getText().toString(), edtSenha.getText().toString());

                } else {
                    android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(CadastroPessoaJuridicaDois.this.context);
                    builder.setTitle(R.string.validacao_login_cinco);
                    builder.setMessage(R.string.validacao_login_seis);
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    builder.setCancelable(false);
                    builder.show();
                }

            } else {
                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(CadastroPessoaJuridicaDois.this.context);
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
            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(CadastroPessoaJuridicaDois.this.context);
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
        AlertDialog.Builder builder = new AlertDialog.Builder(CadastroPessoaJuridicaDois.this);
        builder.setTitle("");
        builder.setMessage(R.string.validacao_login_quinze);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                onBackPressed();
                startActivity(new Intent(CadastroPessoaJuridicaDois.this, CadastroPessoaJuridica.class));
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
            default:
                break;
        }
        return true;
    }

    public void onLoaded(String string) {
        if (string.equalsIgnoreCase("true")) {
            pb.setVisibility(View.VISIBLE);
            WelcomeScreen.act.finish();
            startActivity(new Intent(this, LoginPessoaJuridica.class));
            finish();
        } else {
            pb.setVisibility(View.INVISIBLE);
        }
    }
}
