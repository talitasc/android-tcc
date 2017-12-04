package com.example.talit.projetotcc.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.talit.projetotcc.R;
import com.example.talit.projetotcc.connectionAPI.LoginComFacebook;
import com.example.talit.projetotcc.connectionAPI.RecuperarSenha;
import com.example.talit.projetotcc.connectionAPI.RecuperarSenhaPJ;
import com.example.talit.projetotcc.utils.Validacoes;
import com.example.talit.projetotcc.connectionAPI.AutenticaPj;

public class LoginPessoaJuridica extends AppCompatActivity implements AutenticaPj.Listener {

    private EditText edtUsuario;
    private EditText edtSenha;
    private Button btnEsqueceuSenha;
    private Button btnEntrar;
    private Button btnCadastrar;
    private String senhaNovaStr;
    private boolean haSenha;
    private boolean haLogin;
    private String senhaConfirmadaStr;
    private boolean haConfirmSenha;
    public static ProgressBar pb;
    public static Context context;
    public static ProgressBar pbRecuperar;
    public static AlertDialog dialogo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_login_pessoa_juridica);
        context = this;
        pb = (ProgressBar) findViewById(R.id.ps_login);
        btnEsqueceuSenha = (Button) findViewById(R.id.btn_esqueceu_senha);
        edtUsuario = (EditText) findViewById(R.id.ed_user_pj);
        edtSenha = (EditText) findViewById(R.id.ed_senha_pj);
        btnEntrar = (Button) findViewById(R.id.btn_entrar);
        btnCadastrar = (Button) findViewById(R.id.btn_cadastrar);
        pb.setVisibility(View.INVISIBLE);

        haSenha = false;
        haLogin = false;
        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(LoginPessoaJuridica.this, CadastroPessoaJuridica.class));
                finish();
            }
        });
        edtSenha.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if (TextUtils.isEmpty(charSequence)) {
                    edtSenha.setError("A senha é necessária");
                    Validacoes.requestFocus(edtSenha);
                    haSenha = true;

                } else if (!Validacoes.validaSenha(edtSenha.getText().toString())) {
                    edtSenha.setError("Senha muito pequena");
                    Validacoes.requestFocus(edtSenha);
                    haSenha = true;

                } else {
                    haSenha = false;
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        edtUsuario.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if (TextUtils.isEmpty(charSequence)) {
                    edtUsuario.setError("O e-mail é necessário");
                    Validacoes.requestFocus(edtUsuario);
                    haLogin = true;

                } else if (!Validacoes.validaEmail(edtUsuario.getText().toString())) {
                    edtUsuario.setError("E-mail digitado incorretamente");
                    Validacoes.requestFocus(edtUsuario);
                    haLogin = true;
                } else {
                    haLogin = false;
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        btnEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("email", haLogin + "");
                Log.i("senha", haSenha + "");

                if (!haSenha && !haLogin) {
                    if (!TextUtils.isEmpty(edtSenha.getText().toString()) && !TextUtils.isEmpty(edtUsuario.getText().toString())) {

                        if (Validacoes.verifyConnection(LoginPessoaJuridica.this)) {
                            pb.setVisibility(View.VISIBLE);
                            AutenticaPj conn = new AutenticaPj(LoginPessoaJuridica.this);
                            //conn.execute("murilo.lfs@gmail.com", "Salerno111");
                            conn.execute(edtUsuario.getText().toString().trim(), edtSenha.getText().toString());

                        } else {

                            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(LoginPessoaJuridica.this.context);
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
                    }
                    android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(LoginPessoaJuridica.this.context);
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

                }else{
                    android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(LoginPessoaJuridica.this.context);
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
        });
        btnEsqueceuSenha.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (Validacoes.verifyConnection(LoginPessoaJuridica.this)) {
                    alertaDialogoEsqueceuSenha();

                } else {

                    android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(LoginPessoaJuridica.this.context);
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
            }
        });
    }

    public void alertaDialogoEsqueceuSenha() {
        LayoutInflater inflater = getLayoutInflater();
        final View alertLayout = inflater.inflate(R.layout.custom_alerta_dialog_relembrar_senha, null);
        Button btnEnviar = (Button) alertLayout.findViewById(R.id.btn_enviar);
        Button btnCancelar = (Button) alertLayout.findViewById(R.id.btn_cancelar);
        final EditText edtEmail = (EditText) alertLayout.findViewById(R.id.ed_email);
        pbRecuperar = (ProgressBar)alertLayout.findViewById(R.id.ps_recuperar);
        pbRecuperar.setVisibility(View.INVISIBLE);
        AlertDialog.Builder alerta = new AlertDialog.Builder(this);
        alerta.setView(alertLayout);
        alerta.setCancelable(false);
        dialogo = alerta.create();
        dialogo.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogo.show();
        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strEmail;
                strEmail = edtEmail.getText().toString();
                edtEmail.setError(null);

                if (TextUtils.isEmpty(strEmail)) {
                    edtEmail.setError("O e-mail é necessário");
                    Validacoes.requestFocus(edtEmail);
                    edtEmail.setText("");
                } else if (!Validacoes.validaEmail(strEmail)) {
                    edtEmail.setError("E-mail digitado incorretamente");
                    Validacoes.requestFocus(edtEmail);
                    edtEmail.setText("");
                } else {
                    RecuperarSenhaPJ connRec = new RecuperarSenhaPJ();
                    connRec.execute(edtEmail.getText().toString());
                   /* android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(LoginCliente.context);
                    builder.setTitle("E-mail enviado!");
                    builder.setMessage("Sua nova senha foi enviada via e-mail.Verifique sua caixa de entrada.");
                    builder.setPositiveButton("Fechar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            dialogo.dismiss();
                        }
                    });
                    builder.setCancelable(false);
                    builder.show();*/
                }
            }
        });
        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(LoginPessoaJuridica.this);
                builder.setTitle("");
                builder.setMessage(R.string.validacao_login_quatorze);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        startActivity(new Intent(LoginPessoaJuridica.this, LoginPessoaJuridica.class));
                        dialog.dismiss();
                        dialogo.dismiss();
                        finish();
                    }
                });
                builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();

                    }
                });
                builder.show();
            }
        });

    }


    public void alterarSenha(final int id_cons) {

        LayoutInflater inflater = getLayoutInflater();
        final View alertLayout = inflater.inflate(R.layout.alerta_dialog_custom, null);
        Button bntAlt = (Button) alertLayout.findViewById(R.id.bt_alterar_senha);
        AlertDialog.Builder alerta = new AlertDialog.Builder(this);
        alerta.setView(alertLayout);
        alerta.setCancelable(false);
        bntAlt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                EditText senhaNova = (EditText) alertLayout.findViewById(R.id.ed_nova_senha);
                EditText senhaConfirm = (EditText) alertLayout.findViewById(R.id.ed_confirm_senha);


                senhaNovaStr = senhaNova.getText().toString();
                senhaConfirmadaStr = senhaConfirm.getText().toString();

                if (TextUtils.isEmpty(senhaNovaStr)) {
                    senhaNova.setError("A senha é necessária");
                    Validacoes.requestFocus(senhaNova);
                    senhaNova.setText("");


                } else if (!Validacoes.validaSenha(senhaNovaStr)) {
                    senhaNova.setError("Senha muito pequena");
                    //Validacoes.requestFocus(senhaNova);
                    senhaNova.setText("");

                }
                if (TextUtils.isEmpty(senhaConfirmadaStr)) {
                    senhaConfirm.setError("A senha é necessária");
                    // Validacoes.requestFocus(senhaConfirm);
                    senhaConfirm.setText("");
                    haConfirmSenha = true;

                } else if (!Validacoes.validaSenha(senhaConfirmadaStr)) {
                    senhaConfirm.setError("Senha muito pequena");
                    Validacoes.requestFocus(senhaConfirm);
                    senhaConfirm.setText("");
                    haConfirmSenha = true;
                }

                Toast.makeText(getBaseContext(), senhaNovaStr + " - " + senhaConfirmadaStr, Toast.LENGTH_SHORT).show();
            }
        });
        AlertDialog dialog = alerta.create();
        dialog.show();
    }

    public void onLoaded(String string) {
        if (string.equalsIgnoreCase("true")) {
            pb.setVisibility(View.VISIBLE);
            WelcomeScreen.act.finish();
            startActivity(new Intent(this, PaginalnicialConsumidor.class));
            finish();
        } else {
            pb.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(LoginPessoaJuridica.this, RedirecionaPessoaJuridica.class));
        finish();
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
