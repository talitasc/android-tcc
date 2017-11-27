package com.example.talit.projetotcc.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.talit.projetotcc.R;
import com.example.talit.projetotcc.connectionAPI.AlteraSenha;
import com.example.talit.projetotcc.connectionAPI.AlterarDadosUsuario;
import com.example.talit.projetotcc.connectionAPI.CadastrarEndereco;
import com.example.talit.projetotcc.connectionAPI.CidadeId;
import com.example.talit.projetotcc.connectionAPI.EnderecoConsumidor;
import com.example.talit.projetotcc.connectionAPI.EnderecoPorCep;
import com.example.talit.projetotcc.connectionAPI.EnderecoPorCepNew;
import com.example.talit.projetotcc.connectionAPI.EstadoId;
import com.example.talit.projetotcc.connectionAPI.ListaSupermercadoPoRaio;
import com.example.talit.projetotcc.connectionAPI.ListarDadosUsuario;
import com.example.talit.projetotcc.mascaras.MascaraTelefone;
import com.example.talit.projetotcc.utils.Validacoes;
import com.example.talit.projetotcc.sqlight.DbConn;
import com.google.android.gms.vision.text.Text;

public class AlteraDadosConsumidor extends AppCompatActivity {

    public static EditText edtNome;
    public static EditText edtSobrenome;
    public static EditText edtNovaSenha;
    public static EditText edtConfirSenha;
    public static EditText edtTel;
    private TextWatcher twTel;
    private Spinner smp;
    private String telStr;
    private String[] tpTel;
    private String esTel;
    private ArrayAdapter<String> adp;
    private boolean haNome;
    private boolean haSobrenome;
    private boolean haEmail;
    private boolean haTelefone;
    private boolean haSenha;
    private boolean haConfirmarsenha;
    private TextView txtDetalhesDados;
    public static RelativeLayout relativeDados;
    public static ProgressBar pbAlteraDados;
    public static Activity act;
    public static Context context;
    private DbConn dbConn;
    private Button alteraDados;
    private Button btnSenha;
    public static TextView txtTpTel;
    private String tpTele;
    private TextView txtSenha;
    private RelativeLayout relativeSenhas;
    public static RelativeLayout relativeEndereco;
    private static String idUser;
    private static String tpUser;
    public static ProgressBar pbAlteraSenha;
    public static ProgressBar pbAltEnd;
    public static RelativeLayout noEnd;
    public static RecyclerView recEnds;
    private TextView endDescr;
    private static EditText edtNomeRua;
    private static EditText edtLocalidade;
    private static EditText edtUf;
    private static EditText edtBairro;
    private static EditText edtNumero;
    private static EditText edtNumerocartão;
    private static EditText edtCodigoSeg;
    private static EditText edtImpresso;
    private static EditText edtCep;
    public static ProgressBar pbEndereco;
    public static TextView txtErroEndereco;
    private Button btnBuscaEnd;
    public static String idEstado = "1";
    private Button btnCadastrarEnd;
    private static EditText edtComplemento;
    public static RelativeLayout relativeListEnds;
    public static RelativeLayout relativeNovoEndereco;
    private Button btnNewEndereco;
    private TextView txtIdCidade;
    private TextView txtIdEstado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        act = this;
        context = this;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle(R.string.txt_aletrar_usuario_app);

        setContentView(R.layout.act_altera_dados_consumidor);
        edtNome = (EditText) findViewById(R.id.ed_nome_alt);
        edtSobrenome = (EditText) findViewById(R.id.ed_sobrenome_alt);
        edtTel = (EditText) findViewById(R.id.ed_telefones);
        smp = (Spinner) findViewById(R.id.sp_tp_tel);
        txtDetalhesDados = (TextView)findViewById(R.id.txt_users);
        relativeDados = (RelativeLayout)findViewById(R.id.dados_usuario);
        pbAlteraDados = (ProgressBar)findViewById(R.id.pb_altera);
        pbAlteraDados.setVisibility(View.INVISIBLE);
        alteraDados=(Button)findViewById(R.id.btn_altera_dados);
        txtTpTel = (TextView)findViewById(R.id.txttpTel);
        edtNovaSenha = (EditText) findViewById(R.id.id_nv_senha_alt);
        edtConfirSenha = (EditText) findViewById(R.id.nv_confisenha_alt);
        btnSenha = (Button)findViewById(R.id.btn_altera_senhas);
        txtSenha = (TextView)findViewById(R.id.txt_senhas);
        relativeSenhas = (RelativeLayout)findViewById(R.id.senhas_usuario);
        noEnd = (RelativeLayout)findViewById(R.id.rl_noende);
        pbAlteraSenha = (ProgressBar)findViewById(R.id.pb_senhas);
        recEnds  = (RecyclerView)findViewById(R.id.lv_ends);
        endDescr = (TextView)findViewById(R.id.txt_desc_end);
        relativeEndereco = (RelativeLayout)findViewById(R.id.id_enderecos);
        edtNomeRua = (EditText) findViewById(R.id.ed_rua);
        edtLocalidade = (EditText) findViewById(R.id.ed_localidade);
        edtUf = (EditText) findViewById(R.id.ed_uf);
        edtBairro = (EditText) findViewById(R.id.ed_bairro);
        edtNumero = (EditText) findViewById(R.id.ed_numero);
        edtCep = (EditText) findViewById(R.id.ed_cep);
        pbEndereco = (ProgressBar)findViewById(R.id.pb_altera_end_cep);
        pbEndereco.setVisibility(View.INVISIBLE);
        pbAlteraSenha.setVisibility(View.INVISIBLE);
        txtErroEndereco = (TextView)findViewById(R.id.txt_valida_busca);
        btnBuscaEnd = (Button) findViewById(R.id.btn_consultar);
        btnCadastrarEnd = (Button)findViewById(R.id.btn_cadastrarNew);
        edtComplemento = (EditText)findViewById(R.id.ed_complemento);
        relativeListEnds = (RelativeLayout)findViewById(R.id.lis_endereco);
        relativeNovoEndereco = (RelativeLayout)findViewById(R.id.new_end);
        btnNewEndereco = (Button)findViewById(R.id.btn_altera_end);

        dbConn = new DbConn(AlteraDadosConsumidor.this);
        tpTel = new String[]{"Telefone", "Celular"};
        adp = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, tpTel);
        adp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        smp.setAdapter(adp);

        idUser = dbConn.selectConsumidor().getIdCons()+"";
        tpUser = dbConn.selectConsumidor().getTpAcesso()+"";


        esTel = "";
        smp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View v, int posicao, long id) {
                esTel = parent.getItemAtPosition(posicao).toString();
                if (esTel.equals("Telefone")) {
                    edtTel.removeTextChangedListener(twTel);
                    twTel = MascaraTelefone.insert("(##)####-####", edtTel);
                    edtTel.addTextChangedListener(twTel);
                    tpTele = "1";
                }
                if (esTel.equals("Celular")) {
                    edtTel.removeTextChangedListener(twTel);
                    twTel = MascaraTelefone.insert("(##)#####-####", edtTel);
                    edtTel.addTextChangedListener(twTel);
                    tpTele = "2";
                }
                Toast.makeText(AlteraDadosConsumidor.this, esTel, Toast.LENGTH_SHORT).show();
            }

            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });
        haNome = false;
        haSobrenome = false;
        haEmail = false;
        haTelefone = false;
        haSenha = false;
        haConfirmarsenha = false;

        edtNovaSenha.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if (TextUtils.isEmpty(edtNovaSenha.getText().toString())) {
                    edtNovaSenha.setError("A senha é necessária");
                    Validacoes.requestFocus(edtNovaSenha);
                    haSenha = true;

                } else if (!Validacoes.validaSenha(edtNovaSenha.getText().toString())) {
                    edtNovaSenha.setError("Senha muito pequena");
                    Validacoes.requestFocus(edtNovaSenha);
                    haSenha = true;
                } else {
                    haSenha = false;
                    edtNovaSenha.setError(null);
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

                } else if (!edtConfirSenha.getText().toString().equals(edtNovaSenha.getText().toString())) {

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

        txtDetalhesDados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ListarDadosUsuario connListar = new ListarDadosUsuario();
                connListar.execute(idUser,tpUser);

            }
        });
        alteraDados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!haSenha && !haConfirmarsenha) {
                    if(!TextUtils.isEmpty(edtNovaSenha.getText().toString()) && !TextUtils.isEmpty(edtConfirSenha.getText().toString())) {
                        String telefoneCompleto = edtTel.getText().toString().replace("(", "").replace(")", "").replace("-", "");
                        String dd = telefoneCompleto.substring(0, 2);
                        String telefone = telefoneCompleto.substring(2, telefoneCompleto.length());
                        Log.i("DD", dd);
                        Log.i("telefone", telefone);
                        tpTele = txtTpTel.getText().toString();
                        pbAlteraDados.setVisibility(View.VISIBLE);
                        AlterarDadosUsuario connAltDados = new AlterarDadosUsuario();
                        connAltDados.execute(idUser, tpUser, edtNome.getText().toString(), edtSobrenome.getText().toString(), tpTele, dd, telefone);
                    }else{
                        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(AlteraDadosConsumidor.this);
                        builder.setTitle(R.string.txt_campos_vazios);
                        builder.setMessage(R.string.txt_campos_vazios_desc);
                        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        builder.setCancelable(false);
                        builder.show();
                    }
                }else{
                    android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(AlteraDadosConsumidor.this.context);
                    builder.setTitle(R.string.txt_dados_invalidos);
                    builder.setMessage(R.string.txt_dados_invalidos_desc);
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
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
        txtSenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                relativeSenhas.setVisibility(View.VISIBLE);
            }
        });
        btnSenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pbAlteraSenha.setVisibility(View.VISIBLE);
                AlteraSenha connSenha = new AlteraSenha();
                connSenha.execute(idUser,tpUser,edtNovaSenha.getText().toString(),edtConfirSenha.getText().toString());

            }
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recEnds.setLayoutManager(layoutManager);

        endDescr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EnderecoConsumidor endConn = new EnderecoConsumidor();
                endConn.execute(idUser,tpUser);
            }
        });
        btnBuscaEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!edtCep.getText().toString().isEmpty()) {
                    EnderecoPorCepNew connNew = new EnderecoPorCepNew(null);
                    connNew.execute(edtCep.getText().toString());
                    txtErroEndereco.setText("");
                }else{
                    txtErroEndereco.setText(R.string.ret_cep_vazio);
                }
            }
        });
        btnCadastrarEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EstadoId connEStId = new EstadoId();
                connEStId.execute(edtUf.getText().toString());
                CidadeId connCidId = new CidadeId();
                connCidId.execute(edtLocalidade.getText().toString(),edtUf.getText().toString());
            }
        });
        btnNewEndereco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                relativeNovoEndereco.setVisibility(View.VISIBLE);
            }
        });
    }
    public static final void cadastrarEndereco(String idEstado,String idCidade){
        CadastrarEndereco connEndnew = new CadastrarEndereco();
        connEndnew.execute(edtNomeRua.getText().toString(),edtNumero.getText().toString(),
                edtComplemento.getText().toString(),edtBairro.getText().toString(),edtCep.getText().toString(),
                idEstado,idCidade,idUser,tpUser);

    }
    public static final void setDados(String nome, String sobrenome, String dd,String numeroTel, String idTpTel){
        edtNome.setText(nome);
        edtSobrenome.setText(sobrenome);
        edtTel.setText(dd+numeroTel);
        txtTpTel.setText(idTpTel);
    }
    public static final void setEnderecoPorCep(String rua, String localidade, String uf, String bairro){
        edtNomeRua.setText(rua);
        edtLocalidade.setText(localidade);
        edtUf.setText(uf);
        edtBairro.setText(bairro);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(AlteraDadosConsumidor.this,PaginalnicialConsumidor.class));
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
