package com.example.talit.projetotcc.activities;

import android.content.Context;
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
import android.widget.Spinner;

import com.example.talit.projetotcc.R;
import com.example.talit.projetotcc.Validacoes.Validacoes;
import com.example.talit.projetotcc.connectionAPI.EstabelecimentoComprador;
import com.example.talit.projetotcc.mascaras.MascaraCep;
import com.example.talit.projetotcc.mascaras.MascaraCnpJ;
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
        btnCadastrar.setVisibility(View.INVISIBLE);

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
                haBilitaBotao();
            }

            @Override
            public void afterTextChanged(Editable s) {

                if (TextUtils.isEmpty(s.toString().trim())) {
                    edtNomeFunc.setError("Campo Obrigatório");
                    Validacoes.requestFocus(edtNomeFunc);
                    haNome = true;

                } else if (s.length() > 150) {
                    edtNomeFunc.setError("Campo Obrigatório");
                    Validacoes.requestFocus(edtNomeFunc);
                    haNome = true;
                }
                haBilitaBotao();
            }
        });
        edtSobrenome.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                haBilitaBotao();
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                haBilitaBotao();
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(s.toString().trim())) {
                    edtSobrenome.setError("Campo Obrigatório");
                    Validacoes.requestFocus(edtSobrenome);
                    haSobrenome = true;

                } else if (s.length() > 150) {
                    edtSobrenome.setError("Campo Obrigatório");
                    Validacoes.requestFocus(edtSobrenome);
                    haSobrenome = true;
                }

                haBilitaBotao();
            }
        });
        edtCpf.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if (TextUtils.isEmpty(edtCpf.getText().toString())) {
                    edtCpf.setError("Campo Obrigatório");
                    Validacoes.requestFocus(edtCpf);
                    haCpf = true;

                } else if (edtCpf.length() != 14) {
                    edtCpf.setError("CPF Inválido");
                    Validacoes.requestFocus(edtCpf);
                    haCpf = true;
                } else {
                    haCpf = false;
                    edtCpf.setError(null);
                }
                haBilitaBotao();
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(s.toString().trim())) {
                    edtCpf.setError("Campo Obrigatório");
                    Validacoes.requestFocus(edtCpf);
                    haCpf = true;

                } else if (s.length() < 14) {
                    edtCpf.setError("Campo Obrigatório");
                    Validacoes.requestFocus(edtCpf);
                    haCpf = true;
                }
                haBilitaBotao();
            }
        });
        edtUsuario.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                haBilitaBotao();
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if (TextUtils.isEmpty(charSequence.toString().trim())) {
                    edtUsuario.setError("Campo Obrigatório");
                    Validacoes.requestFocus(edtUsuario);
                    haUsuario = true;

                } else if (charSequence.length() > 150) {
                    edtUsuario.setError("Campo Obrigatório");
                    Validacoes.requestFocus(edtUsuario);
                    haUsuario = true;
                } else if (!Validacoes.validaEmail(charSequence.toString().trim())) {
                    edtUsuario.setError("E-mail digitado incorretamente");
                    Validacoes.requestFocus(edtUsuario);
                    haUsuario = true;
                }
                haBilitaBotao();
            }

            @Override
            public void afterTextChanged(Editable s) {

                haBilitaBotao();
            }
        });
        edtSenha.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence chars, int i, int i1, int i2) {

                if (TextUtils.isEmpty(edtSenha.toString().trim())) {
                    edtSenha.setError("Campo Obrigatório");
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
                haBilitaBotao();
            }
        });
        edtConfirmarSenha.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(edtConfirmarSenha.getText().toString())) {
                    edtConfirmarSenha.setError("Campo Obrigatório");
                    Validacoes.requestFocus(edtConfirmarSenha);
                    haConfirSenha = true;

                } else if (edtConfirmarSenha.length() <= 4) {
                    edtConfirmarSenha.setError("Tamanho muito pequeno");
                    Validacoes.requestFocus(edtConfirmarSenha);
                    haConfirSenha = true;

                } else if (!edtConfirmarSenha.getText().toString().equals(edtSenha.getText().toString())) {

                    edtConfirmarSenha.setError("As senhas devem ser idênticas");
                    Validacoes.requestFocus(edtConfirmarSenha);
                    haConfirSenha = true;
                } else {
                    haConfirSenha = false;
                    edtConfirmarSenha.setError(null);
                    haBilitaBotao();
                }

            }
        });
        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String cpf = edtCpf.getText().toString().replace(".","").replace("-","");
                Log.i("CNPJ",strCnpj);
                Log.i("NOME FANT",strNomeFant);
                Log.i("dd",strDD);
                Log.i("telefone",strTel);
                Log.i("nome fun",edtNomeFunc.getText().toString());
                Log.i("Sobrenome",edtSobrenome.getText().toString());
                Log.i("Cpf",cpf);
                Log.i("Usuario",edtUsuario.getText().toString());
                Log.i("Senha",edtSenha.getText().toString());
                pb.setVisibility(View.VISIBLE);
               EstabelecimentoComprador conn = new EstabelecimentoComprador(CadastroPessoaJuridicaDois.this);
                conn.execute(strCnpj, strRazaoSocial, strNomeFant, "1", strDD, strTel,"1", edtNomeFunc.getText().toString(),
                        edtSobrenome.getText().toString(), cpf, "2", "3",
                        edtUsuario.getText().toString(), edtSenha.getText().toString());

            }
        });
    }

    public void haBilitaBotao() {

        if (!TextUtils.isEmpty(edtNomeFunc.getText().toString()) &&
                !TextUtils.isEmpty(edtSobrenome.getText().toString()) &&
                !TextUtils.isEmpty(edtCpf.getText().toString()) && !TextUtils.isEmpty(edtUsuario.getText().toString()) &&
                !TextUtils.isEmpty(edtSenha.getText().toString()) && !TextUtils.isEmpty(edtConfirmarSenha.getText().toString())) {
            btnCadastrar.setVisibility(View.VISIBLE);

        } else {
            btnCadastrar.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this, CadastroPessoaJuridica.class));
        finishActivity(0);
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
