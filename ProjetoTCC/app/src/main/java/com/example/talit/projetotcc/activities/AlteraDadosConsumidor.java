package com.example.talit.projetotcc.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.talit.projetotcc.R;
import com.example.talit.projetotcc.utils.Validacoes;
import com.example.talit.projetotcc.mascaras.MascaraTelefone;
import com.example.talit.projetotcc.sqlight.DbConn;

public class AlteraDadosConsumidor extends AppCompatActivity {

    private EditText edtNome;
    private EditText edtSobrenome;
    private EditText edtEmail;
    private EditText edtUsuario;
    private EditText edtNovaSenha;
    private EditText edtConfirSenha;
    private EditText edtTel;
    private Button btnAlterar;
    private TextWatcher twTel;
    private String nomeStr;
    private String sobrenomeStr;
    private String emailStr;
    private String novaSenhaStr;
    private String senhaAntiga;
    private String confirmSenhaStr;
    private Spinner smp;
    private String telStr;
    private String[] tpTel;
    private String esTel;
    private ArrayAdapter<String> adp;
    private DbConn dbConn;
    private boolean haNome;
    private boolean haSobrenome;
    private boolean haEmail;
    private boolean haTelefone;
    private boolean haSenha;
    private boolean haConfirmarSenha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        setContentView(R.layout.act_altera_dados_consumidor);
        edtNome = (EditText) findViewById(R.id.ed_nome_alt);
        edtSobrenome = (EditText) findViewById(R.id.ed_sobrenome_alt);
        edtEmail = (EditText) findViewById(R.id.id_emailcons_alt);
        edtUsuario = (EditText) findViewById(R.id.id_usuario_alt);
        edtNovaSenha = (EditText) findViewById(R.id.id_nv_senha_alt);
        edtConfirSenha = (EditText) findViewById(R.id.nv_confisenha_alt);
        edtTel = (EditText) findViewById(R.id.ed_telefones);
        smp = (Spinner) findViewById(R.id.sp_tp_tel);
        btnAlterar = (Button) findViewById(R.id.btn_alt_cadastro);

        dbConn = new DbConn(this);
        dbConn.selectConsumidor();
        edtUsuario.setText(dbConn.selectConsumidor().getUsuario());
        senhaAntiga = dbConn.selectConsumidor().getSenha();
        edtUsuario.setEnabled(false);

        tpTel = new String[]{"Telefone", "Celular"};
        adp = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, tpTel);
        adp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        smp.setAdapter(adp);

        esTel = "";
        smp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View v, int posicao, long id) {
                esTel = parent.getItemAtPosition(posicao).toString();
                if (esTel.equals("Telefone")) {
                    edtTel.removeTextChangedListener(twTel);
                    twTel = MascaraTelefone.insert("(##)####-####", edtTel);
                    edtTel.addTextChangedListener(twTel);
                }
                if (esTel.equals("Celular")) {
                    edtTel.removeTextChangedListener(twTel);
                    twTel = MascaraTelefone.insert("(##)#####-####", edtTel);
                    edtTel.addTextChangedListener(twTel);
                }
                Toast.makeText(AlteraDadosConsumidor.this, esTel, Toast.LENGTH_SHORT).show();
            }

            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });
        btnAlterar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verificaEntradas();
            }

        });

    }
    public void verificaEntradas() {
        edtNome.setError(null);
        edtSobrenome.setError(null);
        edtEmail.setError(null);
        edtNovaSenha.setError(null);
        edtConfirSenha.setError(null);


        haEmail = false;
        haNome = false;
        haSenha = false;
        haSobrenome = false;
        haTelefone = false;
        haConfirmarSenha = false;

        nomeStr = edtNome.getText().toString();
        sobrenomeStr = edtSobrenome.getText().toString();
        emailStr = edtEmail.getText().toString();
        telStr = edtTel.getText().toString();
        confirmSenhaStr = edtConfirSenha.getText().toString();
        novaSenhaStr = edtNovaSenha.getText().toString();

        if (nomeStr.length() > 150) {
            edtNome.setError("Nome muito grande");
            Validacoes.requestFocus(edtNome);
            haNome = true;
        }
        if (sobrenomeStr.length() > 150) {
            edtSobrenome.setError("Sobrenome muito grande");
            Validacoes.requestFocus(edtSobrenome);
            haSobrenome = true;
        }
        if (!Validacoes.validaEmail(emailStr)) {
            edtEmail.setError("E-mail digitado incorretamente");
            Validacoes.requestFocus(edtEmail);
            haEmail = true;
        }


        if (haNome != true && haSobrenome != true && haEmail != true && haTelefone != true) {

            if (TextUtils.isEmpty(novaSenhaStr) && TextUtils.isEmpty(confirmSenhaStr)) {
                dbConn.updateConsumidor(dbConn.selectConsumidor().getIdCons(), senhaAntiga);

                AlertDialog.Builder builder = new AlertDialog.Builder(AlteraDadosConsumidor.this);
                builder.setTitle("Dados alterados");
                builder.setMessage("Dados cadastrados Alterados");
                builder.setPositiveButton("Fechar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(AlteraDadosConsumidor.this, PaginalnicialConsumidor.class));
                        finish();
                    }
                });
                builder.setCancelable(false);
                builder.show();

            } else {


                if (!Validacoes.validaSenha(novaSenhaStr)) {
                    edtNovaSenha.setError("Senha muito pequena");
                    Validacoes.requestFocus(edtNovaSenha);
                    haSenha = true;
                }
                if (!Validacoes.validaSenha(confirmSenhaStr)) {
                    edtConfirSenha.setError("Senha muito pequena");
                    Validacoes.requestFocus(edtConfirSenha);
                    haConfirmarSenha = true;
                }

                if (haSenha != true && haConfirmarSenha != true) {

                    if (novaSenhaStr.equals(confirmSenhaStr)) {

                        if (!senhaAntiga.equals(novaSenhaStr)) {
                            dbConn.updateConsumidor(dbConn.selectConsumidor().getIdCons(), novaSenhaStr);

                            AlertDialog.Builder builder = new AlertDialog.Builder(AlteraDadosConsumidor.this);
                            builder.setTitle("Dados alterados");
                            builder.setMessage("Dados cadastrados Alterados");
                            builder.setPositiveButton("Fechar", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    startActivity(new Intent(AlteraDadosConsumidor.this, PaginalnicialConsumidor.class));
                                    finish();
                                }
                            });
                            builder.setCancelable(false);
                            builder.show();
                        } else {
                            edtNovaSenha.setError("A nova senha não pode ser igual a antiga");
                            edtConfirSenha.setError("A nova senha não pode ser igual a antiga");
                        }
                    } else {
                        edtNovaSenha.setError("A senhas devem ser idênticas");
                        edtConfirSenha.setError("A senhas devem ser idênticas");
                    }
                }

            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
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
