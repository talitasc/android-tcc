package com.example.talit.projetotcc.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
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

import com.example.talit.projetotcc.R;
import com.example.talit.projetotcc.utils.Validacoes;
import com.example.talit.projetotcc.connectionAPI.CadastroPessoaFisica;
import com.example.talit.projetotcc.mascaras.MascaraCpf;
import com.example.talit.projetotcc.mascaras.MascaraTelefone;

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
            smp = (Spinner) findViewById(R.id.sp_tp_tel);
            twCpf = MascaraCpf.insert("###.###.###-##", edtCpf);
            tpTel = new String[]{getString(R.string.smp_desc_telefone), getString(R.string.smp_desc_celular)};
            adp = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, tpTel);
            adp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            smp.setAdapter(adp);

            pb.setVisibility(View.INVISIBLE);

            esTel = "";
            haCpf = false;
            haNome = false;
            haSobrenome = false;
            haEmail = false;
            haTelefone = false;
            haSenha = false;
            haConfirmarsenha = false;

            edtNome.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    if (TextUtils.isEmpty(charSequence)) {
                        edtNome.setError("Campo Obrigatório");
                        Validacoes.requestFocus(edtNome);
                        haNome = true;

                    } else if (charSequence.length() > 150) {
                        edtNome.setError("Nome muito grande");
                        Validacoes.requestFocus(edtNome);
                        haNome = true;
                    } else {
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

                    if (TextUtils.isEmpty(charSequence)) {
                        edtSobrenome.setError("Campo Obrigatório");
                        Validacoes.requestFocus(edtSobrenome);
                        haSobrenome = true;
                    } else if (charSequence.length() > 150) {
                        edtSobrenome.setError("Nome muito grande");
                        Validacoes.requestFocus(edtSobrenome);
                        haSobrenome = true;
                    } else {
                        haSobrenome = false;
                        edtSobrenome.setError(null);
                    }
                }
                @Override
                public void afterTextChanged(Editable s) {

                }
            });
            edtEmail.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    if (TextUtils.isEmpty(edtEmail.getText().toString())) {
                        edtEmail.setError("Campo obrigatório");
                        Validacoes.requestFocus(edtEmail);
                        haEmail = true;
                    } else if (!Validacoes.validaEmail(edtEmail.getText().toString())) {
                        edtEmail.setError("E-mail digitado incorretamente");
                        Validacoes.requestFocus(edtEmail);
                        haEmail = true;
                    } else {
                        haEmail = false;
                        edtEmail.setError(null);
                    }

                }

                @Override
                public void afterTextChanged(Editable editable) {



                }
            });
            edtTel.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    if (edtTel.getText().toString().length() > 1 && edtTel.getText().toString().length() >14) {
                        edtTel.setError("Telefone inválido");
                        Validacoes.requestFocus(edtTel);
                        haTelefone = true;

                    } else {
                        haTelefone = false;
                        edtTel.setError(null);

                    }
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
            edtSenha.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    if (TextUtils.isEmpty(edtSenha.getText().toString())) {
                        edtSenha.setError("A senha é necessária");
                        Validacoes.requestFocus(edtSenha);
                        haSenha = true;

                    } else if (!Validacoes.validaSenha(edtSenha.getText().toString())) {
                        edtSenha.setError("Senha muito pequena");
                        Validacoes.requestFocus(edtSenha);
                        haSenha = true;
                    } else {
                        haSenha = false;
                        edtSenha.setError(null);
                    }
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
            edtConfirSenha.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    if (TextUtils.isEmpty(edtConfirSenha.getText().toString())) {
                        edtConfirSenha.setError("Campo Obrigatório");
                        Validacoes.requestFocus(edtConfirSenha);
                        haConfirmarsenha = true;

                    } else if (edtConfirSenha.length() <= 4) {
                        edtConfirSenha.setError("Tamanho muito pequeno");
                        Validacoes.requestFocus(edtConfirSenha);
                        haConfirmarsenha = true;

                    } else if (!edtConfirSenha.getText().toString().equals(edtSenha.getText().toString())) {

                        edtConfirSenha.setError("As senhas devem ser idênticas");
                        Validacoes.requestFocus(edtConfirSenha);
                        haConfirmarsenha = true;
                    } else {
                        haConfirmarsenha = false;
                        edtConfirSenha.setError(null);

                    }
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
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
                        idTelefone = 2;
                        edtTel.setText("");
                        edtTel.removeTextChangedListener(twTel);
                        twTel = MascaraTelefone.insert("(##)#####-####", edtTel);
                        edtTel.addTextChangedListener(twTel);
                    }
                    if (esTel.equals("Teléfono")) {
                        idTelefone = 1;
                        edtTel.setText("");
                        edtTel.removeTextChangedListener(twTel);
                        twTel = MascaraTelefone.insert("(##)####-####", edtTel);
                        edtTel.addTextChangedListener(twTel);
                    }
                    if (esTel.equals("Móviles")) {
                        idTelefone = 2;
                        edtTel.setText("");
                        edtTel.removeTextChangedListener(twTel);
                        twTel = MascaraTelefone.insert("(##)#####-####", edtTel);
                        edtTel.addTextChangedListener(twTel);
                    }
                    //Toast.makeText(CadastroConsumidor.this, esTel, Toast.LENGTH_SHORT).show();
                }

                public void onNothingSelected(AdapterView<?> arg0) {
                    // TODO Auto-generated method stub
                }
            });
            btnCadastrar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    verificaEntradas();
                }

            });

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
    }
    public void verificaEntradas() {
        Log.i("nome", haNome +"");
        Log.i("sobrenome", haSobrenome +"");
        Log.i("email", haEmail +"");
        Log.i("telefone", haTelefone +"");
        Log.i("haSenha", haSenha +"");
        Log.i("haConfirmar", haConfirmarsenha +"");

        if(!haNome && !haSobrenome && !haEmail && !haTelefone && !haSenha && !haConfirmarsenha) {

            if (!TextUtils.isEmpty(edtNome.getText().toString()) &&
                    !TextUtils.isEmpty(edtSobrenome.getText().toString()) && !TextUtils.isEmpty(edtTel.getText().toString()) &&
                    !TextUtils.isEmpty(edtEmail.getText().toString()) && !TextUtils.isEmpty(edtSenha.getText().toString())
                    && !TextUtils.isEmpty(edtConfirSenha.getText().toString())) {
                if (Validacoes.verifyConnection(CadastroConsumidor.this)) {
                    LayoutInflater inflater = getLayoutInflater();
                    final View alertLayout = inflater.inflate(R.layout.custom_alerta_dialog_termos, null);
                    final CheckBox aceitar = (CheckBox) alertLayout.findViewById(R.id.check_aceito);
                    Button cancelar = (Button) alertLayout.findViewById(R.id.cancelar);
                    Button continuar = (Button) alertLayout.findViewById(R.id.btn_continuar);
                    AlertDialog.Builder alerta = new AlertDialog.Builder(CadastroConsumidor.this);
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
                                String telefoneCompleto = edtTel.getText().toString().replace("(", "").replace(")", "").replace("-", "");

                                String dd = telefoneCompleto.substring(0, 2);
                                String telefone = telefoneCompleto.substring(2, telefoneCompleto.length());
                                Log.i("DD", dd);
                                Log.i("telefone", telefone);

                                CadastroPessoaFisica conn = new CadastroPessoaFisica(CadastroConsumidor.this);
                                //conn.execute("1","Murilo","Lourenço","murilo.lfs@gmail.com","1","19","31141333","31141333");
                                conn.execute("2", edtNome.getText().toString().trim(),
                                        edtSobrenome.getText().toString(), edtEmail.getText().toString().trim(), "1", dd, telefone, edtSenha.getText().toString().trim(), "Sw280717");
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

            } else {
                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(CadastroConsumidor.this);
                builder.setTitle("Campos vázios");
                builder.setMessage("Insira seus dados para realizar o login.");
                builder.setPositiveButton("Fechar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.setCancelable(false);
                builder.show();

            }
        }else{
            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(CadastroConsumidor.this.context);
            builder.setTitle("Dados inválidos!");
            builder.setMessage("Verifique se seus dados foram digitados corretamente.");
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

    @Override
    public void onBackPressed() {
        //super.onBackPressed();

        AlertDialog.Builder builder = new AlertDialog.Builder(CadastroConsumidor.this);
        builder.setTitle("");
        builder.setMessage("Você tem Certeza que deseja cancelar esta operação? Ao confirmar seus dados serão perdidos.");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                startActivity(new Intent(CadastroConsumidor.this, LoginCliente.class).setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY));
                dialog.dismiss();
                finish();
                onBackPressed();
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
