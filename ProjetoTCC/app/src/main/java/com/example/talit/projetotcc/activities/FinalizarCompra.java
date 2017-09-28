package com.example.talit.projetotcc.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.talit.projetotcc.R;

public class FinalizarCompra extends AppCompatActivity {

    private Spinner spMes;
    private ArrayAdapter<String> adp;
    private String[] strMes;
    private Spinner spAno;
    private ArrayAdapter<String> adpAno;
    private String[] strAno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_finalizar_compra);
        spMes = (Spinner) findViewById(R.id.spinMes);
        spAno = (Spinner) findViewById(R.id.spinAno);

        strMes = new String[]{"Mês","01","02","03","04","05","06","07","08","09","10","11","12"};
        adp = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, strMes);
        adp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spMes.setAdapter(adp);

        strAno = new String[]{"Ano","2017","2018","2019","2020","2021","2022","2023","2024","2025","2026","2027","2028","2029","2030","2031"};
        adpAno = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, strAno);
        adpAno.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spAno.setAdapter(adpAno);

    }

}
