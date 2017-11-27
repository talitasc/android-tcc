package com.example.talit.projetotcc.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.talit.projetotcc.R;
import com.example.talit.projetotcc.connectionAPI.EnderecoPorCep;
import com.example.talit.projetotcc.connectionAPI.EnderecosFinalizarCompra;
import com.example.talit.projetotcc.connectionAPI.GerarPedido;
import com.example.talit.projetotcc.connectionAPI.ListaSupermercadoPoRaio;
import com.example.talit.projetotcc.logicalView.Cep;
import com.example.talit.projetotcc.sqlight.DbConn;
import com.google.android.gms.vision.text.Text;

public class FinalizarCompra extends AppCompatActivity implements  GerarPedido.Listener{

    private Spinner spMes;
    private ArrayAdapter<String> adp;
    private String[] strMes;
    private Spinner spAno;
    private ArrayAdapter<String> adpAno;
    private String[] strAno;
    public static Activity act;
    public static Context context;
    private Button btnCep;
    private Button btnBuscaEnd;
    private Button btnPagar;
    private Button btnMeuEnd;
    public static RelativeLayout relatEndereco;
    public static RelativeLayout relativeFrete;
    public static RelativeLayout relativeDelivery;
    public static RelativeLayout relativePagamento;
    public static RelativeLayout relativeDinheiro;
    private static EditText edtNomeRua;
    private static EditText edtLocalidade;
    private static EditText edtUf;
    private static EditText edtBairro;
    private static EditText edtNumero;
    private static EditText edtNumerocartão;
    private static EditText edtCodigoSeg;
    private static EditText edtImpresso;
    private EditText edtCep;
    private RadioButton rdButtonFrete;
    private RadioButton rdButtonDelivery;
    private RadioButton rdDinheiro;
    private RadioButton rdCartao;
    public static TextView txtValor;
    public static TextView txtRua;
    public static TextView txtNumero;
    public static TextView txtBairro;
    public static TextView txtCep;
    public static TextView txtUf;
    public static TextView txtCidade;
    public static TextView txtErroEndereco;
    public static ProgressBar pbAguardar;
    public static String strRua;
    public static String strNumero;
    public static String strBairro;
    public static String strCidade;
    public static String strCep;
    public static String strUf;
    public boolean houveFrete;
    public boolean houveCartao;
    public boolean houveDelivery;
    public boolean houveDinheiro;
    private DbConn dbConn;
    public static RecyclerView recEnderecos;
    public static RelativeLayout relativeMeusEnds;
    private static String idUser;
    private static String tpUser;
    public static RelativeLayout noEnd;
    public static String tpEntrega = "ENTREGA";
    public static String idEstabelecimento = "ESTABELECIMENTO";
    public static String tpPagamento= "PAGAMENTO";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_finalizar_compra);
        act = this;
        context = this;

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        spMes = (Spinner) findViewById(R.id.spinMes);
        spAno = (Spinner) findViewById(R.id.spinAno);
        btnCep = (Button) findViewById(R.id.btn_consultar);
        btnBuscaEnd = (Button) findViewById(R.id.btn_buscar_cep);
        edtNomeRua = (EditText) findViewById(R.id.ed_rua);
        edtLocalidade = (EditText) findViewById(R.id.ed_localidade);
        edtUf = (EditText) findViewById(R.id.ed_uf);
        edtBairro = (EditText) findViewById(R.id.ed_bairro);
        edtNumero = (EditText) findViewById(R.id.ed_numero);
        edtCep = (EditText) findViewById(R.id.ed_cep);
        edtNumerocartão = (EditText) findViewById(R.id.ed_numero_cartao);
        edtCodigoSeg = (EditText) findViewById(R.id.ed_cod_seg);
        edtImpresso = (EditText) findViewById(R.id.ed_impresso);
        relatEndereco = (RelativeLayout)findViewById(R.id.end_por_cep);
        relativeFrete = (RelativeLayout)findViewById(R.id.calc_frete);
        relativeDelivery = (RelativeLayout) findViewById(R.id.delivery);
        relativePagamento = (RelativeLayout) findViewById(R.id.formas_pagamento);
        relativeDinheiro = (RelativeLayout) findViewById(R.id.dinheiro);
        rdButtonFrete = (RadioButton)findViewById(R.id.rd_frete);
        rdButtonDelivery =(RadioButton) findViewById(R.id.rd_delivery);
        rdCartao = (RadioButton) findViewById(R.id.rd_cartao);
        rdDinheiro = (RadioButton) findViewById(R.id.rd_nota);
        txtValor = (TextView)findViewById(R.id.txt_valor);
        txtErroEndereco = (TextView)findViewById(R.id.txt_valida_busca);
        txtRua = (TextView) findViewById(R.id.txt_rua);
        txtNumero = (TextView) findViewById(R.id.txt_numero);
        txtBairro = (TextView) findViewById(R.id.txt_bairro);
        txtCep = (TextView) findViewById(R.id.txt_cep);
        txtUf = (TextView) findViewById(R.id.txt_sigla);
        txtCidade = (TextView) findViewById(R.id.txt_cidade);
        btnPagar = (Button) findViewById(R.id.btn_pagar);
        btnMeuEnd = (Button)findViewById(R.id.btn_meu_end);
        pbAguardar = (ProgressBar) findViewById(R.id.pb_aguardar);
        pbAguardar.setVisibility(View.INVISIBLE);
        relativeMeusEnds = (RelativeLayout)findViewById(R.id.id_enderecos);
        recEnderecos = (RecyclerView)findViewById(R.id.lv_ends);
        noEnd = (RelativeLayout)findViewById(R.id.rl_noende);
        dbConn = new DbConn(FinalizarCompra.this);

        txtRua.setText(strRua+",");
        txtNumero.setText(strNumero);
        txtBairro.setText(strBairro+",");
        txtCep.setText(strCep);
        txtUf.setText(strUf);
        txtCidade.setText(strCidade+",");

        idUser = dbConn.selectConsumidor().getIdCons()+"";
        tpUser = dbConn.selectConsumidor().getTpAcesso()+"";

        strMes = new String[]{"Mes","01","02","03","04","05","06","07","08","09","10","11","12"};
        adp = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, strMes);
        adp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spMes.setAdapter(adp);

        strAno = new String[]{"Ano","2017","2018","2019","2020","2021","2022","2023","2024","2025","2026","2027","2028","2029","2030","2031"};
        adpAno = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, strAno);
        adpAno.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spAno.setAdapter(adpAno);

        rdButtonDelivery.setChecked(false);
        rdButtonFrete.setChecked(false);
        rdButtonFrete.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                relativeFrete.setVisibility(View.VISIBLE);
                relativeDelivery.setVisibility(View.GONE);
                tpEntrega = "1";
                rdButtonDelivery.setChecked(false);
            }
        });
        rdButtonDelivery.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                relativeFrete.setVisibility(View.GONE);
                relativeDelivery.setVisibility(View.VISIBLE);
                tpEntrega = "2";
                rdButtonFrete.setChecked(false);

            }
        });
        rdDinheiro.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                relativePagamento.setVisibility(View.GONE);
                relativeDinheiro.setVisibility(View.VISIBLE);
                tpPagamento ="3";
                rdCartao.setChecked(false);
            }
        });
        rdCartao.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                relativePagamento.setVisibility(View.VISIBLE);
                relativeDinheiro.setVisibility(View.GONE);
                tpPagamento= "4";
                rdDinheiro.setChecked(false);
            }
        });
        btnCep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!edtCep.getText().toString().isEmpty()) {
                    EnderecoPorCep conn = new EnderecoPorCep(null);
                    conn.execute(edtCep.getText().toString());
                    txtErroEndereco.setText("");
                }else{
                    txtErroEndereco.setText(R.string.ret_cep_vazio);
                }
            }
        });
       /* btnCep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtErroEndereco.setText("");
                if(!edtCep.getText().toString().isEmpty()) {
                    if (edtCep.getText().toString().equals("13188104")) {
                        txtValor.setText("R$ 3,00");
                    } else {
                        txtValor.setText(R.string.ret_no_cep);
                    }
                }else{
                    txtValor.setText(R.string.ret_cep_vazio);
                }
            }
        });*/
       /* txtValor.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!edtCep.getText().toString().isEmpty()){
                    if(!txtValor.getText().toString().equals("Não entregamos neste CEP.") ){
                        relatEndereco.setVisibility(View.VISIBLE);
                    }else{
                        relatEndereco.setVisibility(View.GONE);
                    }
                }else{
                    relatEndereco.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });*/
        btnBuscaEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                relativeMeusEnds.setVisibility(View.GONE);
                relatEndereco.setVisibility(View.VISIBLE);
                /*if(!edtCep.getText().toString().isEmpty()) {
                    EnderecoPorCep conn = new EnderecoPorCep(null);
                    conn.execute(edtCep.getText().toString());
                    txtErroEndereco.setText("");
                }else{
                    txtErroEndereco.setText(R.string.ret_cep_vazio);
                }*/
            }
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recEnderecos.setLayoutManager(layoutManager);
        btnMeuEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                relativeMeusEnds.setVisibility(View.VISIBLE);
                EnderecosFinalizarCompra connEndFinal = new EnderecosFinalizarCompra();
                connEndFinal.execute(idUser,tpUser);
            }
        });
        btnPagar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(rdButtonFrete.isChecked()){
                    if(edtNomeRua.getText().toString().isEmpty()|| edtBairro.getText().toString().isEmpty()||
                            edtUf.getText().toString().isEmpty() || edtNumero.getText().toString().isEmpty() ||
                            edtLocalidade.getText().toString().isEmpty()){
                        houveFrete = true;
                        //Toast.makeText(getBaseContext(),"itens" + "campos cartão do frete vazio", Toast.LENGTH_SHORT).show();
                    }
                }
                if(rdCartao.isChecked()){
                    if(edtNumerocartão.getText().toString().isEmpty() || edtCodigoSeg.getText().toString().isEmpty()||
                            edtImpresso.getText().toString().isEmpty()){
                        houveCartao = true;
                        //Toast.makeText(getBaseContext(),"itens" + "campos do cartao vazio", Toast.LENGTH_SHORT).show();
                    }
                }
                if(rdDinheiro.isChecked()){
                    houveDinheiro = true;
                }else{
                    houveDinheiro = false;
                }
                if(rdButtonDelivery.isChecked()){
                    houveDelivery = true;
                }else{
                    houveDelivery = false;
                }
                idEstabelecimento ="60";
                if(!tpEntrega.equals("ENTREGA") && !idEstabelecimento.equals("ESTABELECIMENTO") && !tpPagamento.equals("PAGAMENTO")){
                    GerarPedido connGerar = new GerarPedido(FinalizarCompra.this);
                    connGerar.execute(dbConn.selectDadosSacola().getIdProduto()+"",tpEntrega,idEstabelecimento,"3.00",tpPagamento);

                }else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(FinalizarCompra.context);
                    builder.setTitle(R.string.txt_enviado_erro);
                    builder.setMessage(R.string.txt_aviso_desc);
                    builder.setPositiveButton("Ok", null);
                    builder.setCancelable(false);
                    builder.show();
                }

            }

        });
    }
    public static final void getValues(String rua, String localidade, String uf, String bairro){
        edtNomeRua.setText(rua);
        edtLocalidade.setText(localidade);
        edtUf.setText(uf);
        edtBairro.setText(bairro);
    }
    public static final void setFrete(String frete){
        txtErroEndereco.setText(frete);
    }
    public void pedidoGerado() {

        LayoutInflater inflater = getLayoutInflater();
        final View alertLayout = inflater.inflate(R.layout.custom_alert_dialog_avisos, null);
        Button cancelar = (Button) alertLayout.findViewById(R.id.cancelar);
        TextView txtTitulo = (TextView) alertLayout.findViewById(R.id.txt_titulo_aviso);
        TextView txtMsg= (TextView) alertLayout.findViewById(R.id.txt_msg);
        ImageView img = (ImageView) alertLayout.findViewById(R.id.im_aviso);
        AlertDialog.Builder alerta = new AlertDialog.Builder(FinalizarCompra.this);
        alerta.setView(alertLayout);
        alerta.setCancelable(false);
        txtTitulo.setText(R.string.txt_compra_fin_tit);
        txtMsg.setText(R.string.txt_compra_fin_msg);
        img.setImageResource(R.drawable.purchase);

        final AlertDialog dialogo = alerta.create();
        dialogo.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogo.show();
        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogo.dismiss();
                dbConn.deleteSacola();
                startActivity(new Intent(FinalizarCompra.this, PaginaInicialEstabelecimentos.class));
                finish();
            }

        });
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
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(FinalizarCompra.this, Carrinho.class));
        finish();
    }

    @Override
    public void onLoaded(String string) {

        if (string.equalsIgnoreCase("finalizado")) {
            pbAguardar.setVisibility(View.VISIBLE);
            FinalizarCompra.act.startActivity(new Intent(FinalizarCompra.this, PaginaInicialEstabelecimentos.class));
            finish();
        } else {
            pbAguardar.setVisibility(View.INVISIBLE);
        }


    }
}
