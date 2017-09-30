package com.example.talit.projetotcc.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.talit.projetotcc.R;
import com.example.talit.projetotcc.Validacoes.Validacoes;
import com.example.talit.projetotcc.mascaras.MascaraCep;
import com.example.talit.projetotcc.mascaras.MascaraCnpJ;

public class CadastroPessoaJuridicaDois extends AppCompatActivity {

    private Button btnContinuar;
    private TextWatcher txCpf;
    private EditText edtNomeFunc;
    private EditText edtSobrenome;
    private EditText edtCpf;
    private EditText edtUsuario;
    private EditText edtSenha;
    private EditText edtConfirmarSenha;
    private String strNomeFunc;
    private String strSobrenome;
    private String strcpf;
    private String strUsuario;
    private String strSenha;
    private boolean haNome;
    private boolean haSobrenome;
    private boolean haCpf;
    private boolean haUsuario;
    private boolean haSenha;
    private boolean haConfirSenha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_cadastro_pessoa_juridica_dois);

        btnContinuar = (Button) findViewById(R.id.btn_cadastrar);
        edtNomeFunc = (EditText) findViewById(R.id.edt_nome_funcionario);
        edtSobrenome = (EditText) findViewById(R.id.edt_sobrenome_funcionario);
        edtCpf = (EditText) findViewById(R.id.ed_cpf_funcionario);
        edtUsuario = (EditText) findViewById(R.id.edt_user_funcionario);
        edtSenha = (EditText) findViewById(R.id.edt_senha_funcionario);
        edtConfirmarSenha = (EditText) findViewById(R.id.edt_senha_confir);

        txCpf = MascaraCep.insert("######-###", edtCpf);
        edtCpf.addTextChangedListener(txCpf);
        btnContinuar.setVisibility(View.INVISIBLE);

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

                if (TextUtils.isEmpty(edtCpf.toString().trim())) {
                    edtCpf.setError("Campo Obrigatório");
                    Validacoes.requestFocus(edtCpf);
                    haCpf = true;

                } else if (edtCpf.length() < 14) {
                    edtCpf.setError("Campo Obrigatório");
                    Validacoes.requestFocus(edtCpf);
                    haCpf = true;
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
                haBilitaBotao();
            }

            @Override
            public void afterTextChanged(Editable s) {

                if (TextUtils.isEmpty(s.toString().trim())) {
                    edtUsuario.setError("Campo Obrigatório");
                    Validacoes.requestFocus(edtUsuario);
                    haUsuario = true;

                } else if (s.length() > 150) {
                    edtUsuario.setError("Campo Obrigatório");
                    Validacoes.requestFocus(edtUsuario);
                    haUsuario = true;
                }
                haBilitaBotao();
            }
        });
        edtSenha.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                haBilitaBotao();
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if (TextUtils.isEmpty(edtSenha.toString().trim())) {
                    edtSenha.setError("Campo Obrigatório");
                    Validacoes.requestFocus(edtSenha);
                    haSenha = true;

                } else if (edtSenha.length() <= 4) {
                    edtSenha.setError("Tamanho pequeno");
                    Validacoes.requestFocus(edtSenha);
                    haSenha = true;
                }

                haBilitaBotao();
            }

            @Override
            public void afterTextChanged(Editable s) {

                if (TextUtils.isEmpty(s.toString().trim())) {
                    edtSenha.setError("Campo Obrigatório");
                    Validacoes.requestFocus(edtSenha);
                    haSenha = true;

                } else if (s.length() <= 4) {
                    edtSenha.setError("Tamanho pequeno");
                    Validacoes.requestFocus(edtSenha);
                    haSenha = true;
                }
                haBilitaBotao();
            }
        });
        edtConfirmarSenha.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                haBilitaBotao();
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (TextUtils.isEmpty(edtConfirmarSenha.getText().toString())) {
                    edtConfirmarSenha.setError("Campo Obrigatório");
                    Validacoes.requestFocus(edtConfirmarSenha);
                    haConfirSenha = true;

                } else if (edtConfirmarSenha.length() <=4) {
                    edtConfirmarSenha.setError("Tamanho muito pequeno");
                    Validacoes.requestFocus(edtConfirmarSenha);
                    haConfirSenha = true;
                }else if (!edtConfirmarSenha.getText().toString().equals(edtSenha.getText().toString())){

                    edtConfirmarSenha.setError("As senhas devem ser idênticas");
                    Validacoes.requestFocus(edtConfirmarSenha);
                    haConfirSenha = true;
                }
                haBilitaBotao();
            }

            @Override
            public void afterTextChanged(Editable s) {

                if (TextUtils.isEmpty(s.toString().trim())) {
                    edtConfirmarSenha.setError("Campo Obrigatório");
                    Validacoes.requestFocus(edtConfirmarSenha);
                    haConfirSenha = true;

                } else if (s.length() > 150) {
                    edtConfirmarSenha.setError("Campo Obrigatório");
                    Validacoes.requestFocus(edtConfirmarSenha);
                    haConfirSenha = true;
                }else if (!s.toString().trim().equals(edtSenha.getText().toString())){

                    edtConfirmarSenha.setError("As senhas devem ser idênticas");
                    Validacoes.requestFocus(edtConfirmarSenha);
                    haConfirSenha = true;
                }
                haBilitaBotao();
            }
        });
    }

    public void haBilitaBotao() {

        if (!TextUtils.isEmpty(edtNomeFunc.getText().toString()) &&
                !TextUtils.isEmpty(edtSobrenome.getText().toString()) &&
                !TextUtils.isEmpty(edtCpf.getText().toString()) && !TextUtils.isEmpty(edtUsuario.getText().toString()) &&
                !TextUtils.isEmpty(edtSenha.getText().toString()) && !TextUtils.isEmpty(edtConfirmarSenha.getText().toString())) {
            btnContinuar.setVisibility(View.VISIBLE);

        } else {
            btnContinuar.setVisibility(View.INVISIBLE);
        }
    }

    public void verifica() {
        haNome = false;
        haSobrenome = false;
        haCpf = false;
        haUsuario = false;
        haSenha = false;
        haConfirSenha = false;

        edtNomeFunc.setError(null);
        edtSobrenome.setError(null);
        edtCpf.setError(null);
        edtUsuario.setError(null);
        edtSenha.setError(null);
        edtConfirmarSenha.setError(null);

        if (TextUtils.isEmpty(edtNomeFunc.getText().toString())) {
            edtNomeFunc.setError("Campo Obrigatório");
            Validacoes.requestFocus(edtNomeFunc);
            haNome = true;
        }
        if (TextUtils.isEmpty(edtSobrenome.getText().toString())) {
            edtSobrenome.setError("Campo Obrigatório");
            Validacoes.requestFocus(edtSobrenome);
            haSobrenome = true;
        }
        if (TextUtils.isEmpty(edtCpf.getText().toString())) {
            edtCpf.setError("Campo Obrigatório");
            Validacoes.requestFocus(edtCpf);
            haCpf = true;
        }
        if (TextUtils.isEmpty(edtUsuario.getText().toString())) {
            edtUsuario.setError("Campo Obrigatório");
            Validacoes.requestFocus(edtUsuario);
            haUsuario = true;
        }
        if (TextUtils.isEmpty(edtSenha.getText().toString())) {
            edtSenha.setError("Campo Obrigatório");
            Validacoes.requestFocus(edtSenha);
            haSenha = true;
        }
        if (TextUtils.isEmpty(edtSenha.getText().toString())) {
            edtConfirmarSenha.setError("Campo Obrigatório");
            Validacoes.requestFocus(edtConfirmarSenha);
            haConfirSenha = true;
        }
        if (!haNome && !haSobrenome && !haCpf && !haUsuario && !haSenha && !haConfirSenha) {
            startActivity(new Intent(this, CadastroPessoaJuridicaTres.class));
            finish();
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
}
