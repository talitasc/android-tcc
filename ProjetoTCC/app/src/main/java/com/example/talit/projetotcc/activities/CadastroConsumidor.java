package com.example.talit.projetotcc.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.talit.projetotcc.R;
import com.example.talit.projetotcc.Validacoes.Validacoes;
import com.example.talit.projetotcc.connectionAPI.CadastroPessoaFisica;
import com.example.talit.projetotcc.mascaras.MascaraCpf;
import com.example.talit.projetotcc.mascaras.MascaraTelefone;
import com.example.talit.projetotcc.sqlight.DbConn;

import org.w3c.dom.Text;

import java.util.UUID;

public class CadastroConsumidor extends AppCompatActivity implements CadastroPessoaFisica.Listener {

    private TextWatcher twCpf;
    private TextWatcher twTel;
    private EditText edtNome;
    private EditText edtSobrenome;
    private EditText edtEmail;
    private static EditText edtCpf;
    private static EditText edtTel;
    private EditText edtSenha;
    private EditText edtConfirSenha;
    private Button btnCadastrar;
    private Spinner smp;
    private String nomeStr;
    private String sobrenomeStr;
    private String emailStr;
    private String senhaStr;
    private String confirSenhaStr;
    private int idTelefone;
    private String cpfStr;
    private String telStr;
    private String[] tpTel;
    private String esTel;
    private ArrayAdapter<String> adp;
    public static ProgressBar pb;
    private boolean haCpf;
    private boolean haNome;
    private boolean haSobrenome;
    private boolean haEmail;
    private boolean haTelefone;
    private boolean haSenha;
    private boolean haConfirmarsenha;
    public static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_cadastro_consumidor);
        context = this;
        edtNome = (EditText) findViewById(R.id.ed_nome);
        edtSobrenome = (EditText) findViewById(R.id.ed_sobrenome);
        edtEmail = (EditText) findViewById(R.id.ed_email_consumidor);
        edtSenha = (EditText) findViewById(R.id.ed_senha_cadastrar);
        edtConfirSenha = (EditText) findViewById(R.id.ed_senha_confimar_cadastrar);
        edtTel = (EditText) findViewById(R.id.ed_telefones);
        btnCadastrar = (Button) findViewById(R.id.btn_cadastro);
        pb = (ProgressBar) findViewById(R.id.pb_cadastro);
        pb.setVisibility(View.INVISIBLE);
        smp = (Spinner) findViewById(R.id.sp_tp_tel);
        twCpf = MascaraCpf.insert("###.###.###-##", edtCpf);
        tpTel = new String[]{getString(R.string.smp_desc_telefone), getString(R.string.smp_desc_celular)};
        adp = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, tpTel);
        adp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        smp.setAdapter(adp);
        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //CadastroPessoaFisica conn= new CadastroPessoaFisica();
                //conn.execute("1","Talita","Castro","tcastro@gmail.com","1","19","35153329");
                verificaEntradas();
            }

        });
        esTel = "";
        smp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View v, int posicao, long id) {
                esTel = parent.getItemAtPosition(posicao).toString();
                if (esTel.equals("Telefone")) {
                    idTelefone = 1;
                    edtTel.setText("");
                    edtTel.removeTextChangedListener(twTel);
                    twTel = MascaraTelefone.insert("(##)####-####", edtTel);
                    edtTel.addTextChangedListener(twTel);
                }
                if (esTel.equals("Celular")) {
                    idTelefone= 2;
                    edtTel.setText("");
                    edtTel.removeTextChangedListener(twTel);
                    twTel = MascaraTelefone.insert("(##)#####-####", edtTel);
                    edtTel.addTextChangedListener(twTel);
                }
                Toast.makeText(CadastroConsumidor.this, esTel, Toast.LENGTH_SHORT).show();
            }

            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });
    }

    public void verificaEntradas() {

        edtNome.setError(null);
        edtSobrenome.setError(null);
        edtEmail.setError(null);
        //edtCpf.setError(null);
        edtTel.setError(null);
        edtConfirSenha.setError(null);
        edtSenha.setError(null);

        haCpf = false;
        haNome = false;
        haSobrenome = false;
        haEmail = false;
        haTelefone = false;
        haSenha = false;
        haConfirmarsenha = false;

        nomeStr = edtNome.getText().toString();
        sobrenomeStr = edtSobrenome.getText().toString();
        emailStr = edtEmail.getText().toString();
        //cpfStr = edtCpf.getText().toString();
        telStr = edtTel.getText().toString();
        senhaStr = edtSenha.getText().toString();
        confirSenhaStr = edtConfirSenha.getText().toString();

        if (TextUtils.isEmpty(nomeStr)) {
            edtNome.setError("Campo Obrigatório");
            Validacoes.requestFocus(edtNome);
            haNome = true;
        } else if (nomeStr.length() > 150) {
            edtNome.setError("Nome muito grande");
            Validacoes.requestFocus(edtNome);
            haNome = true;
        }

        if (TextUtils.isEmpty(sobrenomeStr)) {
            edtSobrenome.setError("Campo Obrigatório");
            Validacoes.requestFocus(edtSobrenome);
            haSobrenome = true;
        } else if (sobrenomeStr.length() > 150) {
            edtSobrenome.setError("Nome muito grande");
            Validacoes.requestFocus(edtSobrenome);
            haSobrenome = true;
        }

        if (TextUtils.isEmpty(emailStr)) {
            edtEmail.setError("Campo obrigatório");
            Validacoes.requestFocus(edtEmail);
            haEmail = true;
        } else if (!Validacoes.validaEmail(emailStr)) {
            edtEmail.setError("E-mail digitado incorretamente");
            Validacoes.requestFocus(edtEmail);
            haEmail = true;
        }

        if (TextUtils.isEmpty(senhaStr)) {
            edtSenha.setError("A senha é necessária");
            Validacoes.requestFocus(edtSenha);
            haSenha = true;

        } else if (!Validacoes.validaSenha(senhaStr)) {
            edtSenha.setError("Senha muito pequena");
            Validacoes.requestFocus(edtSenha);
            haSenha = true;
        }

        if (TextUtils.isEmpty(confirSenhaStr)) {
            edtConfirSenha.setError("Insira a senha para confirmar");
            Validacoes.requestFocus(edtConfirSenha);
            haConfirmarsenha = true;

        } else if (!Validacoes.validaSenha(confirSenhaStr)) {
            edtConfirSenha.setError("Senha muito pequena");
            Validacoes.requestFocus(edtConfirSenha);
            haConfirmarsenha = true;
        }
      /*  if(TextUtils.isEmpty(cpfStr)){
           // edtCpf.setError("Campo Obrigatório");
            //Validacoes.requestFocus(edtCpf);
            haCpf= true;
        }else if(!Validacoes.validaCPF(cpfStr)){
            //edtCpf.setError("CPF digitado incorretamente");
            //edtCpf.setText("");
            Validacoes.requestFocus(edtCpf);
            haCpf = true;
        }*/

        if (TextUtils.isEmpty(telStr)) {
            edtTel.setError("Campo Obrigatório");
            Validacoes.requestFocus(edtTel);
            haTelefone = true;
        }

        if (haTelefone != true && haEmail != true && haNome != true && haSobrenome != true && haSenha != true && haConfirmarsenha != true) {
            pb.setVisibility(View.VISIBLE);
            if (senhaStr.equals(confirSenhaStr)) {
                LayoutInflater inflater = getLayoutInflater();
                final View alertLayout = inflater.inflate(R.layout.custom_alerta_dialog_termos, null);
                final CheckBox aceitar = (CheckBox) alertLayout.findViewById(R.id.check_aceito);
                Button cancelar = (Button) alertLayout.findViewById(R.id.cancelar);
                Button continuar = (Button) alertLayout.findViewById(R.id.btn_continuar);
                AlertDialog.Builder alerta = new AlertDialog.Builder(this);
                alerta.setView(alertLayout);
                alerta.setCancelable(false);
                final AlertDialog dialogo = alerta.create();
                dialogo.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialogo.show();
                continuar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (aceitar.isChecked()) {
                            pb.setVisibility(View.VISIBLE);
                            String telefoneCompleto = edtTel.getText().toString().replace("(","").replace(")","").replace("-","");

                            String dd = telefoneCompleto.substring(0,2);
                            String telefone = telefoneCompleto.substring(2,telefoneCompleto.length());
                            Log.i("DD",dd);
                            Log.i("telefone",telefone);

                            CadastroPessoaFisica conn = new CadastroPessoaFisica(CadastroConsumidor.this);
                            //conn.execute("1","Murilo","Lourenço","murilo.lfs@gmail.com","1","19","31141333","31141333");
                            conn.execute("2",edtNome.getText().toString().trim(),
                            edtSobrenome.getText().toString(),edtEmail.getText().toString().trim(),"1",dd,telefone,edtSenha.getText().toString().trim(),"Sw280717");
                            dialogo.dismiss();
                        } else {
                            AlertDialog.Builder builder = new AlertDialog.Builder(CadastroConsumidor.this);
                            builder.setTitle("");
                            builder.setMessage("Aceite os termo santes de prosseguir.");
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
                cancelar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(CadastroConsumidor.this);
                        builder.setTitle("");
                        builder.setMessage("Certeza que deseja cancelar esta operação?");
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                startActivity(new Intent(CadastroConsumidor.this, LoginCliente.class));
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


            } else {
                //Toast.makeText(getBaseContext(),"nAO E IGUAL", Toast.LENGTH_SHORT).show();
                edtSenha.setError("A senhas devem ser idênticas");
                edtConfirSenha.setError("A senhas devem ser idênticas");
            }

        }
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();

        AlertDialog.Builder builder = new AlertDialog.Builder(CadastroConsumidor.this);
        builder.setTitle("");
        builder.setMessage("Você tem Certeza que deseja cancelar esta operação? Ao confirmar seus dados serão perdidos.");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                onBackPressed();
                startActivity(new Intent(CadastroConsumidor.this, LoginCliente.class));
                dialog.dismiss();
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
            startActivity(new Intent(this, LoginCliente.class));
            finish();
        } else {
            pb.setVisibility(View.INVISIBLE);
        }
    }
}
