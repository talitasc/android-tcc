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
import com.example.talit.projetotcc.connectionAPI.RecuperarSenha;
import com.example.talit.projetotcc.utils.Validacoes;
import com.example.talit.projetotcc.connectionAPI.AutenticaLogin;
import com.example.talit.projetotcc.connectionAPI.LoginComFacebook;
import com.example.talit.projetotcc.sqlight.DbConn;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONObject;

import java.util.Arrays;

public class LoginCliente extends AppCompatActivity implements AutenticaLogin.Listener, LoginComFacebook.Listener {
    private EditText email;
    private EditText senha;
    private String emailStr;
    private String senhaStr;
    private Button entrar;
    private Button cadastrar;
    private Button btnEsqueceuSenha;
    private boolean haEmail;
    private boolean haSenha;
    private boolean haErro;
    public static ProgressBar pb;
    public static Context context;
    private LoginButton lb;
    private CallbackManager callbackManager;
    private DbConn dbconn;
    public static ProgressBar pbRecuperar;
    public static AlertDialog dialogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_login_cliente);
        context = this;
        email = (EditText) findViewById(R.id.ed_email_cliente);
        senha = (EditText) findViewById(R.id.ed_senha_cliente);
        entrar = (Button) findViewById(R.id.btn_entrar);
        cadastrar = (Button) findViewById(R.id.btn_cadastrar);
        btnEsqueceuSenha = (Button) findViewById(R.id.btn_exqueceu_senha);
        lb = (LoginButton) findViewById(R.id.login_button);
        pb = (ProgressBar) findViewById(R.id.ps_login);

        pb.setVisibility(View.INVISIBLE);

        cadastrar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginCliente.this, CadastroConsumidor.class));
                finish();
            }
        });
        haEmail = false;
        haSenha = false;
        senha.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if (TextUtils.isEmpty(charSequence)) {
                    senha.setError("A senha é necessária");
                    Validacoes.requestFocus(senha);
                    haSenha = true;

                } else if (!Validacoes.validaSenha(senha.getText().toString())) {
                    senha.setError("Senha muito pequena");
                    Validacoes.requestFocus(senha);
                    haSenha = true;

                }else{
                    haSenha = false;
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if (TextUtils.isEmpty(charSequence)) {
                    email.setError("O e-mail é necessário");
                    Validacoes.requestFocus(email);
                    haEmail = true;

                } else if (!Validacoes.validaEmail(email.getText().toString())) {
                    email.setError("E-mail digitado incorretamente");
                    Validacoes.requestFocus(email);
                    haEmail = true;
                }else{
                    haEmail = false;
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        entrar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (Validacoes.verifyConnection(LoginCliente.this)) {
                    if (!haEmail && !haSenha ){
                        if (!TextUtils.isEmpty(email.getText().toString()) && !TextUtils.isEmpty(senha.getText().toString())){
                            pb.setVisibility(View.VISIBLE);
                            AutenticaLogin conn = new AutenticaLogin(LoginCliente.this);
                            //conn.execute("murilo.lfs@gmail.com", "Salerno111");
                            conn.execute(email.getText().toString().trim(), senha.getText().toString(), "Sw280717");

                        }else{
                            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(LoginCliente.this.context);
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
                        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(LoginCliente.this.context);
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

                } else {
                    android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(LoginCliente.this.context);
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

        lb = (LoginButton) findViewById(R.id.login_button);
        callbackManager = CallbackManager.Factory.create();
        lb.setReadPermissions(Arrays.asList("email", "public_profile", "user_friends", "user_birthday"));
        lb.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {

            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.i("erroChatlino",loginResult.toString());
                GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {

                        try{

                            pb.setVisibility(View.VISIBLE);
                            Log.d("Objeto",object.toString());
                            LoginComFacebook conn = new LoginComFacebook(LoginCliente.this);
                            conn.execute(object.getString("email"),"1",object.getString("first_name"),object.getString("last_name"));
                            //Toast.makeText(getApplicationContext(), "ver", Toast.LENGTH_LONG).show();

                        }catch (Exception e){
                            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(LoginCliente.context);
                            builder.setTitle(R.string.validacao_login_sete);
                            builder.setMessage(R.string.validacao_login_oito);
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
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,email,first_name,last_name");
                request.setParameters(parameters);
                request.executeAsync();

            }

            @Override
            public void onCancel() {
                // App code
            }

            @Override
            public void onError(FacebookException exception) {
                Log.i("erroChatlino",exception.toString());
                if (!Validacoes.verifyConnection(LoginCliente.this)) {
                    android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(LoginCliente.context);
                    builder.setTitle("Erro ao tentar conexão!!");
                    builder.setMessage("Verifique se há conexão com a internet em seu aparelho e tente novamente.");
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
        });
        btnEsqueceuSenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Validacoes.verifyConnection(LoginCliente.this)) {
                    alertaDialogoEsqueceuSenha();

                } else {

                    android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(LoginCliente.context);
                    builder.setTitle(R.string.validacao_login_nove);
                    builder.setMessage(R.string.validacao_login_dez);
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
        });

    }

    /* descontinuado o uso do alert dialog, será aplicado apenas pra PJ.
    public void alterarSenha(final int id_cons){

        LayoutInflater inflater = getLayoutInflater();
        final View alertLayout = inflater.inflate(R.layout.alerta_dialog_custom, null);
        Button bntAlt  =(Button)alertLayout.findViewById(R.id.bt_alterar_senha);
        AlertDialog.Builder alerta = new AlertDialog.Builder(this);
        alerta.setView(alertLayout);
        alerta.setCancelable(false);
        bntAlt.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                haSenha = false;
                haConfirmSenha= false;
                EditText senhaNova = (EditText) alertLayout.findViewById(R.id.ed_nova_senha);
                EditText senhaConfirm = (EditText) alertLayout.findViewById(R.id.ed_confirm_senha);


                senhaNovaStr = senhaNova.getText().toString();
                senhaConfirmadaStr = senhaConfirm.getText().toString();

                if(TextUtils.isEmpty(senhaNovaStr)){
                    senhaNova.setError("A senha é necessária");
                    Validacoes.requestFocus(senhaNova);
                    senhaNova.setText("");
                    haSenha = true;

                }else if (!Validacoes.validaSenha(senhaNovaStr)){
                    senhaNova.setError("Senha muito pequena");
                    //Validacoes.requestFocus(senhaNova);
                    senhaNova.setText("");
                    haSenha = true;
                }
                if(TextUtils.isEmpty(senhaConfirmadaStr)){
                    senhaConfirm.setError("A senha é necessária");
                   // Validacoes.requestFocus(senhaConfirm);
                    senhaConfirm.setText("");
                    haConfirmSenha = true;

                }else if (!Validacoes.validaSenha(senhaConfirmadaStr)){
                    senhaConfirm.setError("Senha muito pequena");
                    Validacoes.requestFocus(senhaConfirm);
                    senhaConfirm.setText("");
                    haConfirmSenha = true;
                }
                if(haSenha != true && haConfirmSenha != true) {
                    if (senhaNovaStr.equals(senhaConfirmadaStr)) {
                        dbconn.updateSenha(senhaConfirmadaStr, 2, id_cons);
                        startActivity(new Intent(LoginCliente.this, PaginalnicialConsumidor.class));
                        finish();

                    } else {
                        //Toast.makeText(getBaseContext(),"nAO E IGUAL", Toast.LENGTH_SHORT).show();
                        senhaNova.setError("A senhas devem ser idênticas");
                        senhaConfirm.setError("A senhas devem ser idênticas");
                    }
                }
                Toast.makeText(getBaseContext(), senhaNovaStr + " - " + senhaConfirmadaStr, Toast.LENGTH_SHORT).show();
            }
        });
        AlertDialog dialog = alerta.create();
        dialog.show();
    }*/

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
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
                    RecuperarSenha connRec = new RecuperarSenha();
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
                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(LoginCliente.this);
                builder.setTitle("");
                builder.setMessage(R.string.validacao_login_quatorze);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        startActivity(new Intent(LoginCliente.this, LoginCliente.class));
                        dialog.dismiss();
                        dialogo.dismiss();
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
        });

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
        startActivity(new Intent(LoginCliente.this, RedirecionaPessoaJuridica.class));
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
