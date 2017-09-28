package com.example.talit.projetotcc.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.talit.projetotcc.R;
import com.example.talit.projetotcc.Validacoes.Validacoes;
import com.example.talit.projetotcc.sqlight.DbConn;

public class LoginPessoaJuridica extends AppCompatActivity {

    private EditText edtUsuario;
    private EditText edtSenha;
    private Button btnEsqueceuSenha;
    private Button btnEntrar;
    private Button btnCadastrar;
    private String usuarioStr;
    private String senhaStr;
    private String loginDb;
    private String senhaDb;
    private boolean haSenha;
    private boolean haUser;
    private boolean haErro;
    private String senhaNovaStr;
    private String senhaConfirmadaStr;
    private boolean haConfirmSenha;
    private DbConn dbconn;
    private String tpUsuario;
    private ProgressBar progressBar;

    //public static ProgressBar pb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_login_pessoa_juridica);

        progressBar = (ProgressBar)findViewById(R.id.ps_login);
        btnEsqueceuSenha = (Button)findViewById(R.id.btn_esqueceu_senha);
        edtUsuario = (EditText)findViewById(R.id.ed_user_pj);
        edtSenha = (EditText)findViewById(R.id.ed_senha_pj);
        btnEntrar = (Button)findViewById(R.id.btn_entrar);

        progressBar.setVisibility(View.INVISIBLE);

        btnEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verificaEntradas();
            }
        });
        btnEsqueceuSenha.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if(Validacoes.verifyConnection(LoginPessoaJuridica.this)) {

                   alertaDialogoEsqueceuSenha();

                }else{

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
    }
    public void alertaDialogoEsqueceuSenha(){
        LayoutInflater inflater = getLayoutInflater();
        final View alertLayout = inflater.inflate(R.layout.custom_alerta_dialog_relembrar_senha, null);
        Button btnEnviar  =(Button)alertLayout.findViewById(R.id.btn_enviar);
        Button btnCancelar  =(Button)alertLayout.findViewById(R.id.btn_cancelar);
        final EditText edtEmail = (EditText) alertLayout.findViewById(R.id.ed_email);
        AlertDialog.Builder alerta = new AlertDialog.Builder(this);
        alerta.setView(alertLayout);
        alerta.setCancelable(false);
        final AlertDialog dialogo = alerta.create();
        dialogo.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogo.show();
        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strEmail;
                strEmail = edtEmail.getText().toString();
                edtEmail.setError(null);

                if(TextUtils.isEmpty(strEmail)){
                    edtEmail.setError("O e-mail é necessário");
                    Validacoes.requestFocus(edtEmail);
                    edtEmail.setText("");
                } else if (!Validacoes.validaEmail(strEmail)) {
                    edtEmail.setError("E-mail digitado incorretamente");
                    Validacoes.requestFocus(edtEmail);
                    edtEmail.setText("");
                }else{
                    android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(LoginCliente.context);
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
                    builder.show();
                }
            }
        });
        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(LoginPessoaJuridica.this);
                builder.setTitle("");
                builder.setMessage("Certeza que deseja cancelar esta operação?");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        //startActivity(new Intent(LoginPessoaJuridica.this, LoginCliente.class));
                        dialog.dismiss();
                        dialogo.dismiss();

                        //finish();
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
    public void verificaEntradas(){
        // o progress bar vai ser tratado depois nas classes de sincronização com a api

        edtUsuario.setError(null);
        edtSenha.setError(null);

        usuarioStr = edtUsuario.getText().toString();
        senhaStr = edtSenha.getText().toString();

        haUser = false;
        haSenha = false;
        haErro = false;

        if(TextUtils.isEmpty(usuarioStr)){
            edtUsuario.setError("O usuário é necessário");
            Validacoes.requestFocus(edtUsuario);
            //pb.setVisibility(View.INVISIBLE);
            edtUsuario.setText("");
            haUser = true;
        }
        if(TextUtils.isEmpty(senhaStr)){
            edtSenha.setError("A senha é necessária");
            Validacoes.requestFocus(edtSenha);
            //pb.setVisibility(View.INVISIBLE);
            edtSenha.setText("");
            haSenha = true;

        }else if (!Validacoes.validaSenha(senhaStr)){
            edtSenha.setError("Senha muito pequena");
            Validacoes.requestFocus(edtSenha);
            //pb.setVisibility(View.INVISIBLE);
            edtSenha.setText("");
            haSenha = true;
        }

        if(haUser != true && haSenha != true) {
            dbconn = new DbConn(this);
            if (dbconn.selectConsumidor() != null) {
                loginDb = dbconn.selectConsumidor().getUsuario();
                senhaDb = dbconn.selectConsumidor().getSenha();
                Toast.makeText(LoginPessoaJuridica.this,loginDb +" "+ senhaDb,Toast.LENGTH_SHORT).show();
                if (senhaStr.equals(senhaDb) && usuarioStr.equals(loginDb)) {
                    //verifica se o id do status é 1 se for é pq ainda nao esta ativo(verificar no banco depois da API)
                    if (dbconn.selectConsumidor().getStatus() == 1) {
                        //pb.setVisibility(View.INVISIBLE);
                        alterarSenha(dbconn.selectConsumidor().getIdCons());
                    }
                }else{
                    haErro = true;
                }
            } else {
                haErro = true;
            }
        }
        if(haErro == true){
           // pb.setVisibility(View.INVISIBLE);
            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(LoginPessoaJuridica.this);
            builder.setTitle("Erro no login");
            builder.setMessage("Senha ou e-mail Incorretos");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                    //finish();
                }
            });
            builder.setCancelable(false);
            builder.show();
        }
    }
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
                        startActivity(new Intent(LoginPessoaJuridica.this, PaginalnicialConsumidor.class));
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
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this, RedirecionaPessoaJuridica.class));
        finishActivity(0);
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
